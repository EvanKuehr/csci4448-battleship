import React from 'react';
import { h, Component} from 'preact';
import { route } from 'preact-router';
import styles from './style/Play.scss';
import $ from 'jquery'
import ReactDOM from "preact";

var globalUpdate = undefined
var placedShips = false
var clickObject = {x: -1, y: -1, ownGrid: false, topGrid: false}
var hasClicked = false

function detectClick(x, y, ownGrid, topGrid) {
    clickObject.x = x;
    clickObject.y = y;
    clickObject.ownGrid = ownGrid;
    clickObject.topGrid = topGrid;
    console.log(clickObject);
}

function addGridItems(gridContainer, ownGrid, topGrid) {
    for(let i=0; i<10; i++) {
        for (let j = 0; j < 10; j++) {
            let d = document.createElement("div");
            d.className = "gridItem";
            let clickCell = function () { detectClick(i,j,ownGrid,topGrid); };
            d.addEventListener("click", clickCell, false);
            gridContainer.appendChild(d);
        }
    }
}

function makeGrids() {
    let myTopGrid = document.querySelector("#myTop")
    let enemyTopGrid = document.querySelector("#enemyTop");
    document.styleSheets[0].insertRule(".gridItem{background-color: rgba(255, 255, 255, 0.8);border: 1px solid rgba(0, 0, 0, 0.8);padding: 20px;font-size: 30px;text-align: center;}",0);
    addGridItems(document.querySelector("#myTop"), true, true);
    addGridItems(document.querySelector("#enemyTop"), false, true);
}

function getUpdate(room, player) {
    console.log("Checking for update...");
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/update?room=' + room + '&player=' + player,
        success: function(response) {
            response = JSON.parse(response);
            globalUpdate = response
        }
    });
    return;
}

function createInterval(f,room,player,interval) {
    setInterval(function() { f(room,player); }, interval);
}

export default class Play extends Component {
    componentDidMount() {
        const intervalID = createInterval(getUpdate, this.props.room, this.props.player,3000);
        makeGrids();
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
                        <div id="enemyTop" className={styles.gridContainer}>
                        </div>
                    </div>
                    <div className={styles.flexWrapperFive}>
                        <img
                            alt=""
                            className={styles.battleship1}
                            src="https://static.overlay-tech.com/assets/8bebc6bc-2de2-4d00-8090-b387b18a3095.png"
                        />
                        <div className={styles.rectangle9} />
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