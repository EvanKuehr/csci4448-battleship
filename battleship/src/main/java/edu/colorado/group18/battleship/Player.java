package edu.colorado.group18.battleship;

public class Player {
    private Ship[] fleet;
    private Board board;

    public Player() {
        this.fleet = new Ship[]{new Ship("ship0", 2, -1), new Ship("ship1", 3, -1), new Ship("ship2", 4, -1)};
        this.board = new Board(10, 10);
    }

    public Player(Ship[] fleet) {
        this.fleet = fleet;
        this.board = new Board(10, 10);
    }

    // GET METHODS

    public Ship[] getFleet() {
        return fleet;
    }

    public Board getBoard() {
        return board;
    }

    public boolean placeShip(Ship ship, int y, int x, char orient) {
        boolean success = false;
        if (!ship.placed) {
            if (orient == 'v') {
                if (x < board.getX() && y < board.getY() - ship.getLength()) {
                    if (board.placeShip(ship, y, x, orient)) {
                        ship.placed = true;
                        success = true;
                    }
                }
            } else if (orient == 'h') {
                if (x < board.getX() - ship.getLength() && y < board.getY()) {
                    if(board.placeShip(ship, y, x, orient)) {
                        ship.placed = true;
                        success = true;
                    }
                }
            }
        }
        board.removeStrayShipCells();
        return success;
    }

    public boolean[] strike(Player opponent, int y, int x) {
        return opponent.receiveStrike(y, x);
    }

    //returns a list of two booleans:
    //index 0: if a ship was hit
    //index 1: if a ship was sunk as a result of the strike
    public boolean[] receiveStrike(int y, int x) {
        return board.strike(y, x);
    }


    public boolean shouldSurrender() {
        boolean retVal = true;
        for (Ship ship : fleet) { //for every ship in the player's fleet
            if (!ship.getSunk()) {
                retVal = false; //one of their ships isn't sunk so they shouldn't surrender
                break;
            }
        }
        return retVal;
    }

    public boolean hasSunkenShip() {
        boolean retVal = false;
        for (Ship ship : fleet) { //for every ship in the player's fleet
            if (ship.getSunk()) {
                retVal = true; //one of their ships is sunk
                break;
            }
        }
        return retVal;
    }

    public boolean placedAllShips() {
        boolean retVal = true;
        for (Ship ship : fleet) { //for every ship in the player's fleet
            if (!ship.getPlaced()) {
                retVal = false; //one of their ships is sunk
                break;
            }
        }
        return retVal;
    }
}
