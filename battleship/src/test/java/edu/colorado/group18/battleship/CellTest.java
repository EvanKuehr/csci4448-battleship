package edu.colorado.group18.battleship;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

public class CellTest {
    private ShipCell shipCell;
    private Ship testShip;

    @Before
    public void setUp() {
        testShip = new Ship("CustomShip", 3,-1);
        Cell cell = new ShipCell(testShip, 0);
        shipCell = (ShipCell)cell;
    }

    @Test
    public void testGetStatus() {
        assertFalse(shipCell.getHitStatus());
    }

    @Test
    public void testSetStatus() {
        shipCell.setHitStatus(true);
        assertTrue(shipCell.getHitStatus());

        shipCell.setHitStatus(false);
        assertFalse(shipCell.getHitStatus());
    }

    @Test
    public void testGetShip() {
        assertEquals(testShip, shipCell.getShipRef());
    }

    @Test
    public void testGetIndex() {
        assertEquals(0, shipCell.getShipArrayIndex());
    }

    @Test
    public void testHitShipRef() {
        int index = shipCell.getShipArrayIndex();
        Ship ship = shipCell.getShipRef();

        assertFalse(ship.getCells()[index]);
        ship.hit(index);
        assertTrue(ship.getCells()[index]);
    }
}
