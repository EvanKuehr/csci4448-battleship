import React from 'react';
import { h, Component, Link } from 'preact';
import { route } from 'preact-router';
import styles from './style/JoinGame.scss';
import $ from 'jquery'

$(function() {
    $('#joinForm').submit(function(e) {
        e.preventDefault();
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/join?room=' + $('#room').val(),
            success: function(response) {
                response = JSON.parse(response);
                return (route("/play?room=" +response.room + "&player=" +  response.player));
            }
        });
        return false;
    });
})

export default class JoinGameModal extends Component {
    render () {
        return (
            <div className={styles.joinGameModal}>
                <p className={styles.welcomeTo}>WELCOME TO</p>
                <img
                    alt=""
                    className={styles.battleship1}
                    src="https://static.overlay-tech.com/assets/3ef93b7e-4290-409d-b88e-313a64ba4e8f.png"
                />
                <p className={styles.enterGameRoomCode}>
                    ENTER GAME ROOM CODE
                </p>
                <form id="joinForm">
                    <input type="text" name="room" id="room" className={styles.textbox}/ >
                    <input type="submit" value="Submit" className={styles.rectangle5}/>
                </form>
            </div>
        );
    }
}