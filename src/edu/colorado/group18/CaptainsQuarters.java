package edu.colorado.group18;

public class CaptainsQuarters extends ShipCell {
    boolean captainHit;

    public CaptainsQuarters(Ship ship, int arrayIndex) {
        super(ship, arrayIndex);
        this.captainHit = false;
    }

    public void setHitStatus(boolean newStatus) {
        if (newStatus) {
            if (!super.getHitStatus()) {
                if (this.captainHit) {
                    super.setHitStatus((true));
                } else {
                    this.captainHit = true;
                }
            }
        } else {
            super.setHitStatus(newStatus);
        }
    }
}
