package edu.colorado.group18;

public class Board {

    private int rows; // Y-dimension
    private int cols; // X-dimension
    public Cell cells[][]; // 2-D array of Cell objects

    public Board(int y, int x) {
        rows = y;
        cols = x;
        cells = new Cell[rows][cols];
        for (int i=0; i < rows; i++) { //populate the array of cells with new cell objects
            for (int j=0; j < cols; j++) {
                cells[i][j] = new Cell();
            }
        }
    }
    public int getY() {
        return rows;
    }

    public int getX() {
        return cols;
    }

    public Cell getCell(int row, int col) { // Return cell at coordinates (row, col)
        return cells[row][col];
    }

    public void placeShip(Ship ship, int row, int col, char orientation) { // Place ship at given coordinates
            if (orientation == 'h') { // Fill cells at and to the right of (col, row)
                for (int j=col; j < ship.getLength()+col; j++) {
                    cells[row][j] = new ShipCell(ship,j-col);
                }
            }
            else { // Fill cells at and below (row, col)
                for (int i=row; i < ship.getLength()+row; i++) {
                    cells[i][col] = new ShipCell(ship,i-row);
                }
            }
    }

    public boolean[] strike(int row, int col) {
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