package edu.colorado.group18.battleship;

import java.beans.IndexedPropertyChangeEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;


public class Ship implements PropertyChangeListener {
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

    public boolean getPlaced() { return this.placed; }

    // helper function for hit propertyChange function
    private boolean isSunk() {
        for (boolean cell : this.cells) {
            if (!cell) return false;
        }
        return true;
    }

    //used for testing the ship class independently of the board
    public boolean hit(int index) {
        IndexedPropertyChangeEvent indexedEvt = new IndexedPropertyChangeEvent(new ShipCell(this,index),"hit", false, true, index);
        propertyChange(indexedEvt);
        return this.isSunk();
    }

    //runs when a corresponding ship cell is hit and updates the ship accordingly
    public void propertyChange(PropertyChangeEvent evt) {
        IndexedPropertyChangeEvent indexedEvt = (IndexedPropertyChangeEvent)evt;

        this.cells[indexedEvt.getIndex()] = (boolean)indexedEvt.getNewValue();
        if (this.isSunk()) {
            this.sunk = true;
        }
    }

    public boolean repair(int index) {
        if (!this.sunk) {
            this.cells[index] = false;
            return true;
        }
        return false;
    }
}
