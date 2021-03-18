package test;
import edu.colorado.group18.Board;
import edu.colorado.group18.Ship;
import edu.colorado.group18.ShipCell;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class BoardTest {

    private Board B;

    @Before
    public void setUp() {
        int rows = 5; // Y
        int cols = 5; // X
        B = new Board(rows,cols);
    }

    @Test
    public void checkRows() {
        assertEquals(5, B.getY());
    }

    @Test
    public void checkCols() {
        assertEquals(5, B.getX());
    }

    @Test
    public void checkCellInit() {
        for (int i=0; i < B.getY(); i++) {
            for (int j=0; j < B.getX(); j++) {
                assertEquals("edu.colorado.group18.Cell", B.getCell(i,j).getClass().getName());
            }
        }
    }

    @Test
    public void checkPlaceHorizShip() {
        B.placeShip(new Ship("CustomShip",3,-1), 4, 1, 'h');
        for (int j=1; j < 4; j++) {
            assertEquals("edu.colorado.group18.ShipCell", B.getCell(4,j).getClass().getName());
            ShipCell s = (ShipCell)B.getCell(4,j);
            assertEquals(j-1, s.getShipArrayIndex());
        }
    }

    @Test
    public void checkPlaceVertShip() {
        B.placeShip(new Ship("CustomShip",3,-1), 1, 3, 'v');
        for (int i=1; i < 4; i++) {
            assertEquals("edu.colorado.group18.ShipCell", B.getCell(i, 3).getClass().getName());
            ShipCell s = (ShipCell)B.getCell(i,3);
            assertEquals(i-1, s.getShipArrayIndex());
        }
    }

    @Test
    public void checkMissStrike() {
        boolean[] results;
        B.placeShip(new Ship("CustomShip",3,-1), 1, 3, 'v');
        results = B.strike(0, 0);
        assertFalse(results[0]); //hit status
        assertFalse(results[1]); //sink status
    }

    @Test
    public void checkHitStrike() {
        boolean[] results;
        B.placeShip(new Ship("CustomShip",3,-1), 1, 3, 'v');
        results = B.strike(2, 3);
        assertTrue(results[0]); //hit status
        assertFalse(results[1]); //sink status
    }

    @Test
    public void checkSinkShip() {
        boolean[] results;
        B.placeShip(new Ship("CustomShip",3,-1), 1, 3, 'v');

        results = B.strike(1, 3);
        assertTrue(results[0]); //hit status
        assertFalse(results[1]); //sink status

        results = B.strike(2, 3);
        assertTrue(results[0]); //hit status
        assertFalse(results[1]); //sink status

        results = B.strike(3, 3);
        assertTrue(results[0]); //hit status
        assertTrue(results[1]); //sink status
    }

    @Test
    public void checkSinkCaptainShipVert() {
        boolean[] results;
        B.placeShip(new Ship("destroyer",3,1), 1, 3, 'v');

        results = B.strike(1, 3);
        assertTrue(results[0]); //hit status
        assertFalse(results[1]); //sink status

        results = B.strike(2, 3);
        assertTrue(results[0]); //hit status
        assertFalse(results[1]); //sink status

        results = B.strike(3, 3);
        assertTrue(results[0]); //hit status
        assertFalse(results[1]); //sink status

        results = B.strike(2, 3);
        assertTrue(results[0]); //hit status
        assertTrue(results[1]); //sink status
    }

    @Test
    public void checkSinkCaptainShipHoriz() {
        boolean[] results;
        B.placeShip(new Ship("destroyer",3,1), 1, 1, 'h');

        results = B.strike(1, 1);
        assertTrue(results[0]); //hit status
        assertFalse(results[1]); //sink status

        results = B.strike(1, 2);
        assertTrue(results[0]); //hit status
        assertFalse(results[1]); //sink status

        results = B.strike(1, 3);
        assertTrue(results[0]); //hit status
        assertFalse(results[1]); //sink status

        results = B.strike(1, 2);
        assertTrue(results[0]); //hit status
        assertTrue(results[1]); //sink status
    }
}
