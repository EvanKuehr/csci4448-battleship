import React from 'react';
import styles from './style/Winner.scss';

const WinnerModal = () => (
    <div className={styles.winnerModal}>
        <p className={styles.youWin}>YOU WIN</p>
        <img
            alt=""
            className={styles.victoryDefeat1}
            src="https://static.overlay-tech.com/assets/ecc35279-75f9-4f7a-bd66-94fb51d3d92f.png"
        />
        <div className={styles.group4}>
            <p className={styles.continue}>CONTINUE</p>
        </div>
    </div>
);

export default WinnerModal;