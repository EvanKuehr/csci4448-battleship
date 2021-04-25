import React from 'react';
import { h, Component, Link } from 'preact';
import { route } from 'preact-router';
import styles from './Welcome.scss';
import $ from "jquery";

function createRedirect() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/create',
        success: function(response) {
            response = JSON.parse(response);
            return (route("/play?room=" +response.room + "&player=" +  response.player));
        }
    });
    return false;
}

function joinRedirect() {
    return (route("/join"));
}


export default class WelcomeModal extends Component {
    render() {
        return (
            <div className={styles.welcomeModal}>
                <p className={styles.welcomeTo}>WELCOME TO</p>
                <img
                    alt=""
                    className={styles.battleship1}
                    src="https://static.overlay-tech.com/assets/4ea1e74f-d3b4-489e-80f6-a88290d5bc39.png"
                />
                <div className={styles.flexWrapperOne}>
                    <div className={styles.group1}>
                        <div className={styles.rectangle2} />
                        <button onClick={createRedirect} className={styles.createGame}>CREATE GAME</button>
                    </div>
                    <div className={styles.group1}>
                        <div className={styles.rectangle2} />
                        <button onClick={joinRedirect}className={styles.createGame}>JOIN GAME</button>
                    </div>
                </div>
            </div>
        );
    }
}