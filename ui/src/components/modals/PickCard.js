import React from 'react';
import styles from './style/PickCard.scss';
import PropTypes from "prop-types";

export default class PickCard extends React.Component {
	sendData = data => {
		this.props.parentCallback(data);
	}
	onClose = e => {
		this.props.onClose && this.props.onClose(e);
	};

	render() {
		if (!this.props.show) {
			return null;
		}
		return (
			<div className={styles.pickCardModal}>
				<p className={styles.pickACard}>PICK A CARD</p>
				<div className={styles.flexWrapperTwo}>
					<div className={styles.button} onclick={data=>{data="move"; this.sendData(data);}}>
						<img
							alt=""
							className={styles.vector}
							src="https://static.overlay-tech.com/assets/da19a09b-dda0-4663-a6b1-ccce6dcd65a0.svg"
						/>
						<p className={styles.name}>Move</p>
						<p className={styles.description}>
							Move entire fleet one cell in any direction.
						</p>
					</div>
					<div className={styles.button} onclick={data=>{data="lazer"; this.sendData(data);}}>
						<img
							alt=""
							className={styles.spaceLaser1}
							src="https://static.overlay-tech.com/assets/5c1f5c31-5024-4da9-af15-a0eceb854b30.png"
						/>
							<p className={styles.name}>SPACE LASER</p>
							<p className={styles.descriptionTwo}>
								Damage both surface and subsurface targets
								along the same coordinate.
							</p>
					</div>
					<div className={styles.button} onclick={data=>{data="repair"; this.sendData(data);}}>
						<img
							alt=""
							className={styles.repair1}
							src="https://static.overlay-tech.com/assets/7ff9d6c2-537f-4208-b94e-06606a8b6b2a.png"
						/>
						<p className={styles.name}>REPAIR</p>
						<p className={styles.descriptionThree}>
							Repair one part of an unsunk ship.
						</p>
					</div>
					<div className={styles.button} onclick={data=>{data="torpedo"; this.sendData(data);}}>
						<img
							alt=""
							className={styles.torpedo1}
							src="https://static.overlay-tech.com/assets/09ab0cd6-72e2-44a4-8467-e94524f76e29.png"
						/>
						<p className={styles.name}>TORPEDO</p>
						<p className={styles.descriptionThree}>
							Fires along the vertical axis, htting the
							first target in its path.
						</p>
					</div>
					<div className={styles.button} onclick={data=>{data="sonar"; this.sendData(data);}}>
					<img
						alt=""
						className={styles.sonar1}
						src="https://static.overlay-tech.com/assets/a88b6982-64cf-4394-aa4d-a5cd1260beef.png"
					/>
						<p className={styles.name}>SONAR</p>
						<p className={styles.descriptionFour}>
							Reveal opponent’s ship in a 2 cell radius, does not inflict damage.
						</p>
					</div>
				</div>
			</div>
		);
	}
}
PickCard.propTypes = {
	onClose: PropTypes.func.isRequired,
	show: PropTypes.bool.isRequired
};