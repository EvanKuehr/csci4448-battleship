package edu.colorado.group18;

import java.util.Arrays;

// This is the  baseclass for your ship.  Modify accordingly
// TODO: practice good OO design
public class Ship {
    private String name;
    private int length;
    private boolean[] cells;
    private boolean sunk;

    public Ship(String name, int length) {
        this.sunk = false;
        this.name = name;
        this.length = length;
        this.cells = new boolean[length];
        Arrays.fill(this.cells, false);
    }

    // helper function for hit()
    private boolean isSunk() {
        for (boolean cell : this.cells) {
            if (!cell) return false;
        }
        return true;
    }

    public boolean hit(int index) {
        this.cells[index] = true;
        if (this.isSunk()) {
            this.sunk = true;
            return true;
        }
        return false;
    }

    public boolean repair(int index) {
        if (!this.sunk) {
            this.cells[index] = false;
            return true;
        }
        return false;
    }

    // GETTERS & SETTERS
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLength() {
        return length;
    }

    public boolean[] getCells() {
        return cells;
    }

    public boolean getSunk() {
        return this.sunk;
    }
}
