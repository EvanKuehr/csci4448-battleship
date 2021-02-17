package edu.colorado.group18;

import org.junit.Test;
import static org.junit.Assert.*;

public class ShipTest {

    // test constructor
    @ Test
    public void testConstructor() {
        Ship ship = new Ship("submarine", 3);
        assertEquals(3, ship.getCells().length);
    }

    // test hit method
    @ Test
    public void testHit() {
        Ship ship = new Ship("submarine", 3);
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
    @ Test
    public void testRepair() {
        Ship ship = new Ship("submarine", 3);
        assertFalse(ship.repair(0));
        ship = new Ship("submarine", 3);
        ship.hit(0);
        assertTrue(ship.repair(0));
    }
}
