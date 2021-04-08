package edu.colorado.group18.battleship;

import java.beans.PropertyChangeSupport;

//Child class of Cell, also tested in CellTest file
public class ShipCell extends Cell {
    private Ship shipRef;
    protected int shipArrayIndex;
    protected PropertyChangeSupport support;

    public ShipCell(Ship ship, int arrayIndex) {
        shipRef = ship;
        shipArrayIndex = arrayIndex;
        support = new PropertyChangeSupport(this);
        support.addPropertyChangeListener(ship);
    }

    public Ship getShipRef() {
        return shipRef;
    }

    public int getShipArrayIndex() {
        return shipArrayIndex;
    }

    public void setHitStatus(boolean newStatus) {
        if (newStatus) {
            support.fireIndexedPropertyChange("hit", shipArrayIndex, hit, true);
        }
        hit = newStatus;
    }
}