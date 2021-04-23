import React from 'react';
import styles from './style/Welcome.scss';

const WelcomeModal = () => (
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
                <p className={styles.createGame}>CREATE GAME</p>
            </div>
            <div className={styles.group1}>
                <div className={styles.rectangle2} />
                <p className={styles.createGame}>JOIN GAME</p>
            </div>
        </div>
    </div>
);

export default WelcomeModal;