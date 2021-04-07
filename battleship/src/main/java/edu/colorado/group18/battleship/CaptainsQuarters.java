package edu.colorado.group18.battleship;


public class CaptainsQuarters extends ShipCell {
    boolean captainHit;

    public CaptainsQuarters(Ship ship, int arrayIndex) {
        super(ship, arrayIndex);
        this.captainHit = false;
    }

    public void setHitStatus(boolean newStatus) {
        if (newStatus) {
            if (!this.hit) {
                if (this.captainHit) {
                    support.fireIndexedPropertyChange("hit", shipArrayIndex, hit, true);
                    this.hit = true;
                }
                else {
                    this.captainHit = true;
                }
            }
        }
        else {
            this.hit = newStatus;
        }
    }
}
