package edu.colorado.group18;

public class Board {

    private int cols; // X-dimension
    private int rows; // Y-dimension
    public Cell cells[][]; // 2-D array of Cell objects

    public Board(int y, int x) {
        cols = x;
        rows = y;
        cells = new Cell[rows][cols];
        for (int i=0; i < cols; i++) { //populate the array of cells with new cell objects
            for (int j=0; j < rows; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    public int getX() {
        return cols;
    }
    public int getY() {
        return rows;
    }

    public Cell getCell(int col, int row) { // Return cell at coordinates (col, row)
        return cells[row][col];
    }

    public void placeShip(Ship ship, int col, int row, char orientation) { // Place ship at given coordinates
            if (orientation == 'h') { // Fill cells at and to the right of (col, row)
                for (int i=col; i < ship.getLength(); i++) {
                    cells[i][row] = new ShipCell(ship,i);
                }
            }
            else { // Fill cells at and above (col, row)
                for (int j=row; j < ship.getLength(); j++) {
                    cells[col][j] = new ShipCell(ship,j);
                }
            }
    }

    public boolean[] strike(int col, int row) {
        Cell cell = cells[row][col];
        boolean hitShip = false;
        boolean sinkStatus = false;

        cell.setHitStatus(true); //the cell was targeted/hit
        if (cell instanceof ShipCell) { //cell is occupied with a part of a ship
            ShipCell shipCell = (ShipCell)cell; //cast the Cell to a ShipCell
            sinkStatus = shipCell.getShipRef().hit(shipCell.getShipArrayIndex()); //hit the ship cell, sink status is true if the ship sunk
            hitShip = true;
        }
        boolean[] retArray = {hitShip,sinkStatus};
        return retArray;
    }

}