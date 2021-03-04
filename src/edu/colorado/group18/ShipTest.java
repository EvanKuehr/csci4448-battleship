package edu.colorado.group18;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ShipTest {
    private Ship ship;

    //helper method
    private void sinkShip() {
        for (int i=0; i < 3; i++) {
            ship.hit(i);
        }
    }

    @Before
    public void setUp() {
        ship = new Ship("submarine", 3,0);
    }

    // test constructor
    @Test
    public void testConstructor() {
        assertEquals(3, ship.getCells().length);
    }

    @Test
    public void testGetName() {
        assertEquals("submarine", ship.getName());
    }

    @Test
    public void testGetLength() {
        assertEquals(3, ship.getLength());
    }

    @Test
    public void testGetCells() {
        for (int i = 0; i < 3; i++) {
            assertFalse(ship.getCells()[i]);
        }
    }

    @Test
    public void testGetSunkFalse() {
        assertFalse(ship.getSunk());
    }

    @Test
    public void testGetSunkTrue() {
        sinkShip();
        assertTrue(ship.getSunk());
    }

    // test hit method
    @Test
    public void testHit() {
        assertFalse(ship.getCells()[0]);
        ship.hit(0);
        assertTrue(ship.getCells()[0]);
    }

    // test sinking a ship
    @Test
    public void testSink() {
        for (int i = 1; i < ship.getLength(); i++) {
            assertFalse(ship.getCells()[i]);
            assertFalse(ship.hit(i));
            assertTrue(ship.getCells()[i]);
        }
        assertFalse(ship.getCells()[0]);
        assertTrue(ship.hit(0));
        assertTrue(ship.getCells()[0]);
    }

    // test repair method
    @Test
    public void testRepair() {
        ship.hit(0);
        assertTrue(ship.repair(0));
    }

    // test repair method after ship is sunk
    @Test
    public void testRepairSunken() {
        sinkShip();
        assertFalse(ship.repair(0));
    }


}
