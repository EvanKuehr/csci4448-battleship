import React from 'react';
import { h, Component} from 'preact';
import { route } from 'preact-router';
import styles from './style/Play.scss';
import $ from 'jquery'
import ReactDOM from "preact";

var room = -1;
var player = -1;
var globalUpdate = undefined;
var placedShips = 0;
var clickObject = {y: -1, x: -1, ownGrid: false, topGrid: false};
var hasClicked = false;
var doingTurn = false;

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
    }
    clickObject.y = y;
    clickObject.x = x;
    clickObject.ownGrid = ownGrid;
    clickObject.topGrid = topGrid;
    console.log(clickObject);
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
                color = "rgba(255, 255, 255, 0)";
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
    return;
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
    console.log(placedShips)
    if (placedShips >= 3) {
        document.getElementById("placeShips").style.display = "none";
        if (response.yourTurn && !doingTurn) {
            doingTurn = true;
            //TO DO: PROMPT USER TO DO TURN
            alert("It's your turn!");
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