import React from 'react';
import { h, Component} from 'preact';
import { route } from 'preact-router';
import styles from './style/Play.scss';
import $ from 'jquery'
import ReactDOM from "preact";
import TakeTurnModal from "./TakeTurn";
import PickCard from "./PickCard";
import react from "eslint-config-synacor/src/rules/react";

var room = -1;
var player = -1;
var globalUpdate = undefined;
var placedShips = 0;
var clickObject = {y: -1, x: -1, ownGrid: false, topGrid: false};
var hasClicked = false;
var doingTurn = false;
var currentAction = -1;
var reactInfo = null;
var usingCard = "";
var usingAbility = false;
var abilityInput = -1;

function detectClick(x, y, ownGrid, topGrid) {
    if (placedShips < 3 && ownGrid) {
        let submergedVal = true;
        if (document.getElementById("select-board").value == "surface") {
            submergedVal = false;
        }
        $.ajax({
            type: 'POST',
            contentType: "application/json",
            url: 'http://localhost:8080/set-fleet',
            data: JSON.stringify({
                "room": room,
                "player": parseInt(player),
                "fleet":[
                    {
                        "ship": placedShips,
                        "y": y,
                        "x": x,
                        "orient": document.getElementById("select-orient").value,
                        "submerged": submergedVal
                    }
                ]
            }),
            success: function(response) {
                getUpdate(room, player);
            }
        });
    }   else if (currentAction == 2 && !ownGrid) { //missile
            currentAction = -1;
            $.ajax({
                type: 'POST',
                contentType: "application/json",
                url: 'http://localhost:8080/missile',
                data: JSON.stringify({
                    "room": room,
                    "player": parseInt(player),
                    "y": y,
                    "x": x
                }),
                success: function(response) {
                    getUpdate(room, player);
                }
            });
        }   else if (abilityInput != -1) {
                switch(abilityInput) {
                    case 1: //lazer
                        if (!ownGrid) {
                            $.ajax({
                                type: 'POST',
                                contentType: "application/json",
                                url: 'http://localhost:8080/lazer',
                                data: JSON.stringify({
                                    "room": room,
                                    "player": parseInt(player),
                                    "y": y,
                                    "x": x
                                }),
                                success: function(response) {
                                    getUpdate(room, player);
                                }
                            });
                            abilityInput = -1;
                        }
                        break;
                    case 2: //repair
                        if (ownGrid) {
                            $.ajax({
                                type: 'POST',
                                contentType: "application/json",
                                url: 'http://localhost:8080/repair',
                                data: JSON.stringify({
                                    "room": room,
                                    "player": parseInt(player),
                                    "y": y,
                                    "x": x
                                }),
                                success: function(response) {
                                    getUpdate(room, player);
                                }
                            });
                            abilityInput = -1;
                        }
                        break;
                    case 3: //torpedo
                        if (!ownGrid) {
                            let surfaceVal = true;
                            if (document.getElementById("select-board").value != "surface") {
                                surfaceVal = false;
                            }
                            $.ajax({
                                type: 'POST',
                                contentType: "application/json",
                                url: 'http://localhost:8080/torpedo',
                                data: JSON.stringify({
                                    "room": room,
                                    "player": parseInt(player),
                                    "row": y,
                                    "surface": surfaceVal
                                }),
                                success: function(response) {
                                    getUpdate(room, player);
                                }
                            });
                            abilityInput = -1;
                        }
                        break;
                    case 4: //sonar
                        if (!ownGrid) {
                            $.ajax({
                                type: 'POST',
                                contentType: "application/json",
                                url: 'http://localhost:8080/sonar',
                                data: JSON.stringify({
                                    "room": room,
                                    "player": parseInt(player),
                                    "y": y,
                                    "x": x
                                }),
                                success: function(response) {
                                    getUpdate(room, player);
                                }
                            });
                            abilityInput = -1;
                        }
                        break;
                }
            }
    clickObject.y = y;
    clickObject.x = x;
    clickObject.ownGrid = ownGrid;
    clickObject.topGrid = topGrid;
    console.log(clickObject);
}

function useCard(cardName) {
    abilityInput = -1;
    if (usingAbility) {
        switch(cardName) {
            case "move":
                $.ajax({
                    type: 'POST',
                    contentType: "application/json",
                    url: 'http://localhost:8080/move',
                    data: JSON.stringify({
                        "room": room,
                        "player": parseInt(player),
                        "direction": document.getElementById("select-dir").value
                    }),
                    success: function(response) {
                        getUpdate(room, player);
                    }
                });
                break;
            case "lazer":
                alert("Select an enemy cell to use the lazer on");
                abilityInput = 1;
                break;
            case "repair":
                alert("Select a damaged ship cell to repair");
                abilityInput = 2;
                break;
            case "torpedo":
                alert("Select a row (surface or underwater) to use the torpedo on");
                abilityInput = 3;
                break;
            case "sonar":
                alert("Select an enemy cell to use the sonar on");
                abilityInput = 4;
                break;
            default:
                console.log("Card not found, can't be used: " + cardName);
        }
    }
    console.log(cardName);
    usingAbility = false;
}

function addGridItems(gridContainer, ownGrid, topGrid) {
    for(let i=0; i<10; i++) {
        for (let j = 0; j < 10; j++) {
            let d = document.createElement("div");
            d.className = "gridItem";
            let clickCell = function () { detectClick(j,i,ownGrid,topGrid); };
            d.addEventListener("click", clickCell, false);
            gridContainer.appendChild(d);
        }
    }
}

function makeGrids() {
    let myTopGrid = document.querySelector("#myTop")
    let enemyTopGrid = document.querySelector("#enemyTop");
    document.styleSheets[0].insertRule(".gridItem{cursor: crosshair; background-color: rgba(255, 255, 255, 0.8);border: 1px solid rgba(0, 0, 0, 0.8);padding: 20px;font-size: 30px;text-align: center;}",0);
    addGridItems(document.querySelector("#myTop"), true, true);
    addGridItems(document.querySelector("#enemyTop"), false, true);
    addGridItems(document.querySelector("#myBottom"), true, false);
    addGridItems(document.querySelector("#enemyBottom"), false, false);
}

function changeBoards() {
    if (document.getElementById("select-board").value == "surface") {
        document.getElementById("myTop").style.display = "grid";
        document.getElementById("myBottom").style.display = "none";
        document.getElementById("enemyTop").style.display = "grid";
        document.getElementById("enemyBottom").style.display = "none";
    }
    else {
        document.getElementById("myTop").style.display = "none";
        document.getElementById("myBottom").style.display = "grid";
        document.getElementById("enemyTop").style.display = "none";
        document.getElementById("enemyBottom").style.display = "grid";
    }
}

function updateBoard(boardData, gridContainer, ownBoard) {
    let index = 0;
    let color = "";
    for (let i=0; i<10; i++) {
        for(let j=0; j<10; j++) {
            if(boardData[i][j].hitStatus == true && boardData[i][j].hasOwnProperty('shipRef')) {
                color = "rgba(219, 20, 50, 1)";
            }
            else if (ownBoard && boardData[i][j].hasOwnProperty('shipRef')) {
                color = "rgba(196, 196, 196, 1)";
            }
            else if (!ownBoard && !boardData[i][j].hasOwnProperty('shipRef') && boardData[i][j].hitStatus == true) {
                color = "rgba(255, 255, 255, 1)";
            }
            else {
                color = "rgba(255, 255, 255, 0.8)";
            }
            gridContainer.childNodes[index].style.backgroundColor = color;
            index += 1;
        }
    }
}

function updateCards(deck) {
    let allCardTags = document.getElementById("deckDiv").children;
    document.getElementById("select-dir").className = "abilityCardNone";
    for (let i=0; i<allCardTags.length; i++) {
        allCardTags[i].className = "abilityCardNone";
    }
    for (let i=0; i<deck.length; i++) {
        let cardName = deck[i].name;
        switch(cardName) {
            case "move":
                document.getElementById("moveAbility").className = "abilityCard";
                break;
            case "lazer":
                document.getElementById("lazerAbility").className = "abilityCard";
                break;
            case "repair":
                document.getElementById("repairAbility").className = "abilityCard";
                break;
            case "torpedo":
                document.getElementById("torpedoAbility").className = "abilityCard";
                break;
            case "sonar":
                document.getElementById("sonarAbility").className = "abilityCard";
                break;
            default:
                console.log("Card not found, can't be added to deck: " + cardName);
        }
    }
    if (document.getElementById("moveAbility").className == "abilityCard") {
        document.getElementById("select-dir").className = "movedropdown";
    }
}

function checkPlacedShips(fleet) {
    let countNumPlaced = 0;
    let firstFail = false;
    for (let i=0; i<3; i++) {
        if (fleet[i].placed) {
            countNumPlaced += 1;
        }
        else {
            if (!firstFail) {
                document.getElementById("shipNum").innerText = "Placing Ship: " + (i+1)
                document.getElementById("shipSize").innerText = "Length: " + (i+2)
            }
            firstFail = true;
        }
    }
    placedShips = countNumPlaced;
}

function handleUpdate(response) {
    if (response.winStatus == 1) {
        return (route("/winner"));
    }
    else if (response.winStatus == 0) {
        return (route("/loser"));
    }

    if (placedShips < 3) {
        checkPlacedShips(response.yourData.fleet);
    }
    if (placedShips >= 3) {
        document.getElementById("placeShips").style.display = "none";
        if (response.yourTurn && !doingTurn) {
            doingTurn = true;
            reactInfo.showModal(null);
        }
        if (!response.yourTurn && doingTurn) {
            currentAction = -1;
            doingTurn = false;
        }
    }
    updateBoard(response.yourData.board.cells, document.querySelector("#myTop"), true);
    updateBoard(response.yourData.subBoard.cells, document.querySelector("#myBottom"), true);
    updateBoard(response.opponentData.board.cells, document.querySelector("#enemyTop"), false);
    updateBoard(response.opponentData.subBoard.cells, document.querySelector("#enemyBottom"), false);
    updateCards(response.yourData.deck);
}

function getUpdate(room, player) {
    console.log("Checking for update...");
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/update?room=' + room + '&player=' + player,
        success: function(response) {
            response = JSON.parse(response);
            globalUpdate = response;
            handleUpdate(response);
        }
    });
    return;
}

function createInterval(f,room,player,interval) {
    setInterval(function() { f(room,player); }, interval);
}


export default class Play extends Component {
    state = {
        show: false,
        showCards: false
    };

    showModal = e => {
        this.setState({
            show: !this.state.show
        });
    };

    showCards = e => {
        this.setState({
            showCards: !this.state.showCards
        });
    };

    cardsCallbackFunction = (childData) => { //buying a card
        usingCard = childData;
        console.log(usingCard);
        this.showCards(null);
        $.ajax({
            type: 'POST',
            contentType: "application/json",
            url: 'http://localhost:8080/buy',
            data: JSON.stringify({
                "room": room,
                "player": parseInt(player),
                "cardName": usingCard
            }),
            success: function(response) {
                getUpdate(room, player);
            }
        });
        usingCard = "";
    }

    callbackFunction = (childData) => {
       currentAction = childData;
       console.log(currentAction);
       this.showModal(null);
       if (currentAction == 2) {
           alert("Select an enemy cell to fire at the surface");
       }
       else if (currentAction == 1) { //buying a card
           this.showCards(null);
       }
       else if(currentAction == 3) { //using ability
           usingAbility = true;
           alert("Select an ability card to use")
       }
    }

    componentDidMount() {
        room = this.props.room;
        player = this.props.player;
        const intervalID = createInterval(getUpdate, room, player,3000);
        if (placedShips == 0) {
            makeGrids();
        }

        changeBoards();

        document.getElementById("select-board").addEventListener("change", (event)=> {
           changeBoards();
        });

        getUpdate(room, player);
        reactInfo = this;

        document.styleSheets[0].insertRule('.abilityCard {margin-top: 60px;margin-left: 20px;border-radius: 25px;display: flex;flex-direction: column;align-items: center;background-color: rgba(55, 208, 144, 1);border: 1px solid rgba(94, 94, 94, 1);transform: scale(0.8);height: 250px;width: 180px;}');
        document.styleSheets[0].insertRule('.abilityCardNone {display: none;}');
        document.styleSheets[0].insertRule('.movedropdown {width: 100px;height: 30px;margin-top: -5px;margin-left: 55px;background-color: rgba(196, 196, 196, 1);}');
    }

    componentWillUnmount() {
        clearInterval(intervalID);
    }

    render ({room,player}) {
        return (
            <div className={styles.component1}>
                <div className={styles.flexWrapperOne}>
                    <div className={styles.flexWrapperThree}>
                        <p className={styles.yourBoard}>Your Board</p>
                        <select name="select-board" id="select-board" className={styles.dropdown}>
                            <option value="surface">Surface</option>
                            <option value="under">Underwater</option>
                        </select>
                        <p className={styles.enemyBoard}>Enemy Board</p>
                    </div>
                    <div className={styles.flexWrapperFour}>
                        <div id="myTop" className={styles.gridContainer}>
                        </div>
                        <div id="myBottom" className={styles.gridContainer}>
                        </div>
                        <div id="enemyTop" className={styles.gridContainer}>
                        </div>
                        <div id="enemyBottom" className={styles.gridContainer}>
                        </div>
                    </div>
                    <div className={styles.flexWrapperFive}>
                        <img
                            alt=""
                            className={styles.battleship1}
                            src="https://static.overlay-tech.com/assets/8bebc6bc-2de2-4d00-8090-b387b18a3095.png"
                        />
                        <div className={styles.rectangle9}>
                            <div id="placeShips">
                                <p className={styles.placeText1}>CLICK ON YOUR BOARD TO PLACE SHIPS</p>
                                <p id="shipNum" className={styles.placeText1}>Placing Ship: 1</p>
                                <p id="shipSize" className={styles.placeText1}>Length: 2</p>
                                <select name="select-orient" id="select-orient" className={styles.orient}>
                                    <option value="v">Vertical</option>
                                    <option value="h">Horizontal</option>
                                </select>
                            </div>
                            <div id="deckDiv" className={styles.deckDiv}>
                                <div id="moveAbility" onclick={data=>{data = "move"; useCard(data);}}>
                                    <img
                                        alt=""
                                        className={styles.vector}
                                        src="https://static.overlay-tech.com/assets/da19a09b-dda0-4663-a6b1-ccce6dcd65a0.svg"
                                    />
                                    <p className={styles.cardName}>Move</p>
                                </div>
                                <div id="lazerAbility" onclick={data=>{data = "lazer"; useCard(data);}}>
                                    <img
                                        alt=""
                                        className={styles.vector}
                                        src="https://static.overlay-tech.com/assets/5c1f5c31-5024-4da9-af15-a0eceb854b30.png"
                                    />
                                    <p className={styles.cardName}>Space Lazer</p>
                                </div>
                                <div id="repairAbility" onclick={data=>{data = "repair"; useCard(data);}}>
                                    <img
                                        alt=""
                                        className={styles.vector}
                                        src="https://static.overlay-tech.com/assets/7ff9d6c2-537f-4208-b94e-06606a8b6b2a.png"
                                    />
                                    <p className={styles.cardName}>Repair</p>
                                </div>
                                <div id="torpedoAbility" onclick={data=>{data = "torpedo"; useCard(data);}}>
                                    <img
                                        alt=""
                                        className={styles.vector}
                                        src="https://static.overlay-tech.com/assets/09ab0cd6-72e2-44a4-8467-e94524f76e29.png"
                                    />
                                    <p className={styles.cardName}>Torpedo</p>
                                </div>
                                <div id="sonarAbility" onclick={data=>{data = "sonar"; useCard(data);}}>
                                    <img
                                        alt=""
                                        className={styles.vector}
                                        src="https://static.overlay-tech.com/assets/a88b6982-64cf-4394-aa4d-a5cd1260beef.png"
                                        />
                                    <p className={styles.cardName}>Sonar</p>
                                </div>
                            </div>
                            <div>
                                <select name="select-dir" id="select-dir" className={styles.movedropdown}>
                                    <option value="NORTH">Up</option>
                                    <option value="SOUTH">Down</option>
                                    <option value="WEST">Left</option>
                                    <option value="EAST">Right</option>
                                </select>
                            </div>
                        </div>
                        <div id="modals">
                            <div class="toggle-button" id="centered-toggle-button"></div>
                            <TakeTurnModal onClose={this.showModal} show={this.state.show} parentCallback = {this.callbackFunction}>
                            </TakeTurnModal>
                            <PickCard onClose={this.showCards} show={this.state.showCards} parentCallback = {this.cardsCallbackFunction}>
                            </PickCard>
                        </div>
                        <p className={styles.yourGameRoomCode}>
                            YOUR GAME ROOM CODE
                        </p>
                        <div className={styles.flexWrapperTwo}>
                            <p className={styles.gameCode}>
                                {room}
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}