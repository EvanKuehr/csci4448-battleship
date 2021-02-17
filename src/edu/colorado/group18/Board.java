package edu.colorado.group18;

public class Board {

    private int cols; // X-dimension
    private int rows; // Y-dimension
    public Cell cells[][] = new Cell[rows][cols]; // 2-D array of Cell objects

    public Board(int y, int x) {
        cols = x;
        rows = y;
    }

    public int getX() { return cols; }
    public int getY() { return rows; }
    public Cell getCell(int col, int row) { return cells[row][col]; } // Return cell at coordinates (x, y)

}