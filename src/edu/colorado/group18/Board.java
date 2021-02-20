package edu.colorado.group18;

public class Board {

    private int cols; // X-dimension
    private int rows; // Y-dimension
    public ShipCell cells[][] = new ShipCell[rows][cols]; // 2-D array of Cell objects

    public Board(int y, int x) {
        cols = x;
        rows = y;
    }

    public int getX() { return cols; }
    public int getY() { return rows; }
    public Cell getCell(int col, int row) { return cells[row][col]; } // Return cell at coordinates (col, row)

    public void placeShip(Ship ship, int col, int row, String orientation) { // Place ship at given coordinates
        if (col <= cols && row <= rows) { // Initial bounds check
            if (orientation == 'h') { // Fill cells to the right of (col, row)
                if (ship.getLength() <= cols - col) { cells[row][col] = ship; }
            }
            else { // Fill cells above (col, row)
                if (ship.getLength() <= rows - row) { cells[row][col] = ship; }
            }
        }
    }

    public void strike(int col, int row) {
        if (cells[row][col].getShipRef() != null) { cells[row][col].setHitStatus(true); } // Cell is occupied
        else { return; }
    }

}