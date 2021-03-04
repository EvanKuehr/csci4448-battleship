package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.colorado.group18.CaptainsQuarters;
import edu.colorado.group18.Cell;
import edu.colorado.group18.Ship;
import edu.colorado.group18.ShipCell;

public class CaptainsQuartersTest {
    private Ship ship;
    private Cell[] board;

    @Before
    public void setUp() {
        int length = 3;
        int captainIndex = 0;
        this.ship = new Ship("submarine", length, captainIndex);
        // simulate Board
        this.board = new Cell[length];
        for(int i = 0; i < length; i++) {
            if (i == captainIndex) {
                this.board[i] = new CaptainsQuarters(ship, captainIndex);
            } else {
                this.board[i] = new ShipCell(ship, i);
            }
        }
    }

    @Test
    public void testSetHitStatus() {
        // same location on the board (ship and board share dimensions)
        int captain = this.ship.getCaptainsLocation();

        assertTrue("CaptainsQuarters not set! Type: " + this.board[captain], this.board[captain] instanceof CaptainsQuarters);
        
        this.board[captain].setHitStatus(true);

        assertFalse("captain hit once, should still be alive", this.board[captain].getHitStatus());

        this.board[captain].setHitStatus(true);

        assertTrue("captain hit twice, should be dead", this.board[captain].getHitStatus());
    }
}
