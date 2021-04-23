import React from 'react';
import styles from './style/TakeTurn.scss';

const TakeTurnModal = () => (
	<div className={styles.takeTurnModal}>
		<div className={styles.getCardButton}>
			<img
				alt=""
				className={styles.getCard1}
				src="https://static.overlay-tech.com/assets/fb8c5bc5-dc37-481d-bd52-33825d83f7b1.png"
			/>
			<p className={styles.name}>GET CARD</p>
		</div>
		<div className={styles.flexWrapperOne}>
			<p className={styles.message}>YOUR TURN</p>
			<div className={styles.fireMissileButton}>
				<img
					alt=""
					className={styles.missile1}
					src="https://static.overlay-tech.com/assets/aa304e81-e7c7-495d-bddc-9278aad669d5.png"
				/>
				<p className={styles.nameTwo}>FIRE</p>
			</div>
		</div>
		<div className={styles.useCardButton}>
			<img
				alt=""
				className={styles.specialAbility1}
				src="https://static.overlay-tech.com/assets/2c54ce17-11cd-4b80-8ff6-cd6867f8cea7.png"
			/>
			<p className={styles.nameThree}>USE CARD</p>
		</div>
	</div>
);

export default TakeTurnModal;