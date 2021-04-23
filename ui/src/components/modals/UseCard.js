import React from 'react';
import styles from './style/UseCard.scss';

const UseCardModal = () => (
	<div className={styles.useCardModal}>
		<p className={styles.pickACard}>PICK A CARD</p>
		<div className={styles.flexWrapperOne}>
			<div className={styles.button}>
				<img
					alt=""
					className={styles.spaceLaser1}
					src="https://static.overlay-tech.com/assets/6b866ca0-5d61-40de-8d00-3cdd369764bc.png"
				/>
				<div className={styles.relativeWrapperOne}>
					<p className={styles.name}>SPACE LASER</p>
					<p className={styles.description}>
                            Damage both surface and subsurface targets
                            along the same coordinate.
					</p>
				</div>
			</div>
			<div className={styles.buttonTwo}>
				<img
					alt=""
					className={styles.repair1}
					src="https://static.overlay-tech.com/assets/6c458408-bd2d-4fab-92cb-ff385d752518.png"
				/>
				<p className={styles.nameTwo}>REPAIR</p>
				<p className={styles.descriptionTwo}>
                        Repair one part of an unsunk ship.
				</p>
			</div>
			<div className={styles.buttonThree}>
				<img
					alt=""
					className={styles.torpedo1}
					src="https://static.overlay-tech.com/assets/7e1efc04-06a3-40e2-9f7d-82bc0a05f3c2.png"
				/>
				<p className={styles.nameTwo}>TORPEDO</p>
				<p className={styles.descriptionTwo}>
                        Fires along the vertical axis, htting the first
                        target in its path.
				</p>
			</div>
		</div>
	</div>
);

export default UseCardModal;