import React from 'react';
import styles from './style/PickCard.scss';

const PickCardModal = () => (
	<div className={styles.pickCardModal}>
		<div className={styles.button}>
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
		<div className={styles.flexWrapperOne}>
			<p className={styles.pickACard}>PICK A CARD</p>
			<div className={styles.flexWrapperTwo}>
				<div className={styles.buttonTwo}>
					<img
						alt=""
						className={styles.spaceLaser1}
						src="https://static.overlay-tech.com/assets/5c1f5c31-5024-4da9-af15-a0eceb854b30.png"
					/>
					<div className={styles.relativeWrapperTwo}>
						<p className={styles.nameTwo}>SPACE LASER</p>
						<p className={styles.descriptionTwo}>
                Damage both surface and subsurface targets
                along the same coordinate.
						</p>
					</div>
				</div>
				<div className={styles.buttonThree}>
					<img
						alt=""
						className={styles.repair1}
						src="https://static.overlay-tech.com/assets/7ff9d6c2-537f-4208-b94e-06606a8b6b2a.png"
					/>
					<p className={styles.nameThree}>REPAIR</p>
					<p className={styles.descriptionThree}>
              Repair one part of an unsunk ship.
					</p>
				</div>
				<div className={styles.buttonFour}>
					<img
						alt=""
						className={styles.torpedo1}
						src="https://static.overlay-tech.com/assets/09ab0cd6-72e2-44a4-8467-e94524f76e29.png"
					/>
					<p className={styles.nameThree}>TORPEDO</p>
					<p className={styles.descriptionThree}>
              Fires along the vertical axis, htting the
              first target in its path.
					</p>
				</div>
			</div>
		</div>
		<div className={styles.buttonFive}>
			<img
				alt=""
				className={styles.sonar1}
				src="https://static.overlay-tech.com/assets/a88b6982-64cf-4394-aa4d-a5cd1260beef.png"
			/>
			<div className={styles.relativeWrapperTwo}>
				<p className={styles.nameFour}>SONAR</p>
				<p className={styles.descriptionFour}>
            Reveal opponent’s ship in a 2 cell radius, does
            not inflict damage.
				</p>
			</div>
		</div>
	</div>
);

export default PickCardModal;