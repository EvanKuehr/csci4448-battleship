import React from "react";
import styles from "./style/Loser.scss";

const LoserModal = () => (
    <div className={styles.loserModal}>
        <p className={styles.youLose}>YOU LOSE</p>
        <img
            alt=""
            className={styles.victoryDefeat2}
            src="https://static.overlay-tech.com/assets/5b5aa5ba-878d-434c-8e92-5f8b0136da32.png"
        />
        <div className={styles.group5}>
            <p className={styles.continue}>CONTINUE</p>
        </div>
    </div>
);

export default LoserModal;