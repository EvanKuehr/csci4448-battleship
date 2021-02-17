package edu.colorado.group18;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class CellTests {
    private Cell shipCell;

    @Before
    public void setUp() throws Exception {
        shipCell = new ShipCell();
    }

    @Test
    public void checkInitialState() {
        assertFalse(shipCell.getHitStatus());
    }

    @Test
    public void setStatus() {
        shipCell.setHitStatus(true);
        assertTrue(shipCell.getHitStatus());
        shipCell.setHitStatus(false);
        assertFalse(shipCell.getHitStatus());
    }
}
