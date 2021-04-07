package edu.colorado.group18.battleship;

public class Cell {
    protected boolean hit; //determines whether or not this cell has been hit before

    public Cell() { //default constructor
        hit = false;
    }

    public boolean getHitStatus() {
        return hit;
    }

    public void setHitStatus(boolean newStatus) {
        hit = newStatus;
    }
}