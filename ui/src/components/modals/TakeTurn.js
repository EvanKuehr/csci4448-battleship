import React from 'react';
import styles from './style/TakeTurn.scss';
import PropTypes from "prop-types";

export default class TakeTurnModal extends React.Component {
	sendData = data => {
		this.props.parentCallback(data);
	}
	onClose = e => {
		this.props.onClose && this.props.onClose(e);
	};

	render () {
		if (!this.props.show) {
			return null;
		}
		return (
			<div className={styles.takeTurnModal}>
				<p className={styles.message}>YOUR TURN</p>
				<div className={styles.flexWrapperOne}>
					<div id="get-card" className={styles.getCardButton} onclick={data=>{data=1; this.sendData(data);}}>
						<img
							alt=""
							className={styles.getCard1}
							src="https://static.overlay-tech.com/assets/fb8c5bc5-dc37-481d-bd52-33825d83f7b1.png"
						/>
						<p className={styles.name}>GET CARD</p>
					</div>
					<div id="fire" className={styles.fireMissileButton} onclick={data=>{data=2; this.sendData(data);}} >
						<img
							alt=""
							className={styles.missile1}
							src="https://static.overlay-tech.com/assets/aa304e81-e7c7-495d-bddc-9278aad669d5.png"
						/>
						<p className={styles.nameTwo}>FIRE</p>
					</div>
					<div id="use-card" className={styles.useCardButton} onclick={data=>{data=3; this.sendData(data);}}>
						<img
							alt=""
							className={styles.specialAbility1}
							src="https://static.overlay-tech.com/assets/2c54ce17-11cd-4b80-8ff6-cd6867f8cea7.png"
						/>
						<p className={styles.nameThree}>USE CARD</p>
					</div>
				</div>
			</div>
		);
	}
}
TakeTurnModal.propTypes = {
	onClose: PropTypes.func.isRequired,
	show: PropTypes.bool.isRequired
};