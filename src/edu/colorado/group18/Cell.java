package edu.colorado.group18;

public class Cell {
    private boolean hit; //determines whether or not this cell has been hit before

    public Cell() { //default constructor
        hit = false;
    }

    public boolean getHitStatus() {
        return hit;
    }

    public void setHitStatus(boolean newStatus) {
        hit = newStatus;
        return;
    }
}