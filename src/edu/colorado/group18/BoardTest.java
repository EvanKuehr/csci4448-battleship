package edu.colorado.group18;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

public class BoardTest {

    private Board B;

    @Before
    public void setUp() throws Exception {
        int cols = 5; // X
        int rows = 5; // Y
        B = new Board(rows, cols);
    }

    @Test
    public void checkRows() {
        assertEquals(5, B.getY());
    }

    @Test
    public void checkCols() {
        assertEquals(5, B.getX());
    }

}
