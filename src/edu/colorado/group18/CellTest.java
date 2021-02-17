package edu.colorado.group18;
import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

public class CellTest {
    private Cell shipCell;
    private Ship testShip;

    @Before
    public void setUp() {
        testShip = new Ship("submarine", 3);
        shipCell = new ShipCell(testShip, 0);
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
        assertEquals(0, shipCell.getArrayIndex());
    }

    @Test
    public void testHitShipRef() {
        int index = shipCell.getArrayIndex();
        Ship ship = shipCell.getShipRef();

        assertFalse(ship.getCells()[index]);
        assertTrue(ship.hit(index));
        assertTrue(ship.getCells()[index]);
    }
}
