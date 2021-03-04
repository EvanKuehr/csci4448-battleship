package edu.colorado.group18;

import java.util.Arrays;


public class Ship {
    private String name;
    private int length;
    private boolean[] cells;
    private boolean sunk;
    public boolean placed;
    private int captainsLocation;

    public Ship(String name, int length, int captainsLocation) {
        this.captainsLocation = captainsLocation;
        this.placed = false;
        this.sunk = false;
        this.name = name;
        this.length = length;
        this.cells = new boolean[length];
        Arrays.fill(this.cells, false);
    }

    // GET METHODS
    public String getName() {
        return name;
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

    public int getCaptainsLocation() {
        return this.captainsLocation;
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
}
