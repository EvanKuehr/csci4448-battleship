package edu.colorado.group18;

public class ShipCell extends Cell {
    private Ship shipRef;
    private int shipArrayIndex;

    public ShipCell(Ship ship, int arrayIndex) {
        shipRef = ship;
        shipArrayIndex = arrayIndex;
    }

    public Ship getShipRef() {
        return shipRef;
    }

    public int getShipArrayIndex() {
        return shipArrayIndex;
    }
}
