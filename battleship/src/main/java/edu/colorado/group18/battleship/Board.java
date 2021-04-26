package edu.colorado.group18.battleship;


public class Board {

    private int rows; // Y-dimension
    private int cols; // X-dimension
    public Cell cells[][]; // 2-D array of Cell objects

    public Board(int y, int x) {
        rows = y;
        cols = x;
        cells = new Cell[rows][cols];
        for (int i = 0; i < rows; i++) { //populate the array of cells with new cell objects
            for (int j = 0; j < cols; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    // GET METHODS
    public int getY() {
        return rows;
    }

    public int getX() {
        return cols;
    }

    public Cell getCell(int row, int col) {
        return cells[row][col];
    } // Return cell at coordinates (row, col)

    public void SetCell(Cell cell, int row, int col) {
        this.cells[row][col] = cell;
    }

    public void removeStrayShipCells() {
        ShipCell currCell = null;
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                checkCellHelper(y, x);
            }
        }
    }

    private void checkCellHelper(int y, int x) {
        ShipCell currCell = null;
        if (cells[y][x] instanceof ShipCell) {
            currCell = (ShipCell) cells[y][x];
            if (currCell.getShipRef().getPlaced() == false) {
                cells[y][x] = new Cell();
            }
        }
    }

    public boolean placeShip(Ship ship, int row, int col, char orientation) { // Place ship at given coordinates
        boolean success = true;
        int captainIndex = ship.getCaptainsLocation();
        if (orientation == 'h') { // Fill cells at and to the right of (col, row)
            for (int j = col; j < ship.getLength() + col; j++) {
                if (cells[row][j] instanceof ShipCell) {
                    success = false; //can't place ships on top of each other
                } else if (j == captainIndex + col && ship.getLength() > 2) {
                    cells[row][j] = new CaptainsQuarters(ship, j - col);
                } else {
                    cells[row][j] = new ShipCell(ship, j - col);
                }
            }
        } else { // Fill cells at and below (row, col)
            for (int i = row; i < ship.getLength() + row; i++) {
                if (cells[i][col] instanceof ShipCell) {
                    success = false; //can't place ships on top of each other
                } else if (i == captainIndex + row && ship.getLength() > 2) {
                    cells[i][col] = new CaptainsQuarters(ship, i - row);
                } else {
                    cells[i][col] = new ShipCell(ship, i - row);
                }
            }
        }
        return success;
    }

    public boolean[] strike(int row, int col) {
        Cell cell = cells[row][col];
        boolean hitShip = false;
        boolean sinkStatus = false;

        cell.setHitStatus(true); //the cell was targeted/hit, if it is a ShipCell, the corresponding Ship will listen for the change and update accordingly
        if (cell instanceof ShipCell) { //cell is occupied with a part of a ship
            ShipCell shipCell = (ShipCell) cell; //cast the Cell to a ShipCell
            sinkStatus = shipCell.getShipRef().getSunk(); //knows if the ship is sunk
            hitShip = true;
        }
        boolean[] retArray = {hitShip, sinkStatus};
        return retArray;
    }
}


