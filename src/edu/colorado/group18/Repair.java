package edu.colorado.group18;

public class Repair extends Ability {
    public boolean use(Player self, int y, int x) {
        Cell targetCell = self.getBoard().getCell(y,x);
        if(targetCell instanceof ShipCell) {
            ShipCell shipCell = (ShipCell) targetCell;
            return shipCell.getShipRef().repair(shipCell.getShipArrayIndex()); //return whether or not the repair was successful
        }
        else { //Can't repair a cell that isn't a part of a ship
            return false;
        }
    }
}
