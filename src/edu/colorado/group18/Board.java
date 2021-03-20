package edu.colorado.group18;

import java.util.ArrayList;
import java.util.HashMap;

class Tuple<X, Y> {
    public X x;
    public Y y;
    public Tuple(X x, Y y) {
        this.x = x;
        this.y = y;
    }
}

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

    public boolean placeShip(Ship ship, int row, int col, char orientation) { // Place ship at given coordinates
        boolean success = true;
        int captainIndex = ship.getCaptainsLocation();
        if (ship instanceof Submarine) { // Check for non-linear ships
            if (orientation == 'h') {
                for (int j = col; j < col + ship.getLength(); j++) {
                    if (cells[row][j] instanceof ShipCell) {
                        success = false; //can't place ships on top of each other
                    } else if (j == col + ship.getLength() - 2 && ship.getLength() > 2) {
                        cells[row][j] = new CaptainsQuarters(ship, j - col);
                    } else {
                        cells[row][j] = new ShipCell(ship, j - col);
                    }
                    cells[row - 1][j - 1] = new ShipCell(ship, j + 1 - col);
                }
            } else {
                for (int i = row; i < row + ship.getLength(); i++) {
                    if (cells[i][col] instanceof ShipCell) {
                        success = false; //can't place ships on top of each other
                    } else if (i == row + ship.getLength() - 2 && ship.getLength() > 2) {
                        cells[i][col] = new CaptainsQuarters(ship, i - row);
                    } else {
                        cells[i][col] = new ShipCell(ship, i - row);
                    }
                    cells[row - 1][col - 1] = new ShipCell(ship, i + 1 - row);
                }
            }
        } else {
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

    public boolean canMove(Tuple<Integer, Integer> coord, char direction, Player player, HashMap<Ship, ArrayList<Tuple<Integer, Integer>>> shipMap) { // moveFleet() helper
        boolean res = true;

        switch (direction) {
            case 'n': // -Y
                if (coord.y > getY() - 2 || (cells[coord.y - 1][coord.x] instanceof ShipCell && !shipMap.get(((ShipCell) cells[coord.y][coord.x]).getShipRef()).contains(new Tuple<>(coord.x, coord.y - 1)))) { res = false; }
                break;
            case 'e': // +X
                if (coord.x > getX() - 2 || (cells[coord.y][coord.x + 1] instanceof ShipCell && !shipMap.get(((ShipCell) cells[coord.y][coord.x]).getShipRef()).contains(new Tuple<>(coord.x + 1, coord.y)))) { res = false; }
                break;
            case 's': // +Y
                if (coord.y < 1 || (cells[coord.y + 1][coord.x] instanceof ShipCell && !shipMap.get(((ShipCell) cells[coord.y][coord.x]).getShipRef()).contains(new Tuple<>(coord.x, coord.y + 1)))) { res = false; }
                break;
            case 'w': // -X
                if (coord.x < 1|| (cells[coord.y][coord.x - 1] instanceof ShipCell && !shipMap.get(((ShipCell) cells[coord.y][coord.x]).getShipRef()).contains(new Tuple<>(coord.x - 1, coord.y)))) { res = false; }
                break;
            default: break;
        }
        return res;
    }

    public void moveFleet(Player player, char direction) {

        HashMap<Ship, ArrayList<Tuple<Integer, Integer>>> shipMap = new HashMap<>(); // Map between each ship and its cells' coordinates

        HashMap<Character, Tuple<Integer, Integer>> dirMap = new HashMap<>(); // Map between direction <-> delta X/Y
        dirMap.put('n', new Tuple<>(0, 1));
        dirMap.put('e', new Tuple<>(1, 0));
        dirMap.put('s', new Tuple<>(0, -1));
        dirMap.put('w', new Tuple<>(-1, 0));

        for (int row = 0; row < cells.length; row++) { // Build shipMap
            for (int col = 0; col < cells[row].length; col++) {
                Cell curCell = cells[row][col];
                if (curCell instanceof ShipCell) {
                    Ship ship = ((ShipCell) curCell).getShipRef();
                    if (!shipMap.containsKey(ship)) {
                        shipMap.put(ship, new ArrayList<>());
                    }
                    shipMap.get(ship).add(new Tuple<>(col, row));
                }
            }
        }

        for (Ship ship : player.getFleet()) { // Verify all ShipCells can move
            boolean flag = true;
            for (Tuple<Integer, Integer> coord : shipMap.get(ship)) {
                if (!canMove(coord, direction, player, shipMap)) {
                    flag = false;
                    break;
                }
            }
            if (flag) { // Move cells + update coordinates
                for (Tuple<Integer, Integer> coord : shipMap.get(ship)) {
                    Tuple<Integer, Integer> old_coords = new Tuple<>(coord.x, coord.y);
                    coord.x += dirMap.get(direction).x;
                    coord.y += dirMap.get(direction).y;
                    SetCell(cells[coord.y][coord.x], coord.y, coord.x);
                    SetCell(new Cell(), old_coords.y, old_coords.x);
                }
            }
        }

    }
}