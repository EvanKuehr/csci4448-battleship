import React from 'react';
import styles from './style/JoinGame.scss';

const JoinGameModal = () => (
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
        <div className={styles.flexWrapperOne}>
            <p className={styles.pipe}>|</p>
        </div>
        <div className={styles.group3}>
            <div className={styles.rectangle5} />
            <p className={styles.startGame}>START GAME</p>
        </div>
    </div>
);

export default JoinGameModal;