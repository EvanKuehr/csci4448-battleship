import React from "react";
import styles from "./style/CreateGame.scss";

const CreateGameModal = () => (
    <div className={styles.createGameModal}>
        <p className={styles.welcomeTo}>WELCOME TO</p>
        <img
            alt=""
            className={styles.battleship1}
            src="https://static.overlay-tech.com/assets/a6fecd51-7d2b-4e95-8714-99da91db9017.png"
        />
        <div className={styles.relativeWrapperOne}>
            <p className={styles.yourGameRoomCode}>
                YOUR GAME ROOM CODE
            </p>
            <div className={styles.flexWrapperOne}>
                <p className={styles.gameCode}>
                    &lt;game-code&gt;
                </p>
            </div>
        </div>
        <p className={styles.gameCode}>
            AWAITING OPPONENT...
        </p>
    </div>
);

export default CreateGameModal;