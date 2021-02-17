package edu.colorado.group18;

public abstract class Cell {
    private boolean hit; //determines whether or not this cell has been hit before

    public Cell() {
        hit = false;
    }

    public boolean getHitStatus() {
        return hit;
    }

    public void updateHitStatus(boolean newStatus) {
        hit = newStatus;
        return;
    }
}
// test