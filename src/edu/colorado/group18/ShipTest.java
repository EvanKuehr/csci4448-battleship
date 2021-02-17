package edu.colorado.group18;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class ShipTest {
    private Ship ship;

    @Before
    public void setUp() {
        this.ship = new Ship("submarine", 3);
    }

    // test constructor
    @ Test
    public void testConstructor() {
        assertEquals(3, this.ship.getCells().length);
    }

    // test hit method
    @ Test
    public void testHit() {
        for (int i = 1; i < this.ship.getLength(); i++) {
            assertFalse(this.ship.getCells()[i]);
            assertFalse(this.ship.hit(i));
            assertTrue(this.ship.getCells()[i]);
        }
        assertFalse(this.ship.getCells()[0]);
        assertTrue(this.ship.hit(0));
        assertTrue(this.ship.getCells()[0]);
    }

    // test repair method
    @ Test
    public void testRepair() {
        this.testHit();
        assertFalse(this.ship.repair(0));
        this.ship = new Ship("submarine", 3);
        this.ship.hit(0);
        assertTrue(this.ship.repair(0));
    }
}
