package edu.colorado.group18.battleship;

public class DualBoardPlayer extends Player {

	private Board subBoard;
	private Board board;

	public DualBoardPlayer() {
		super();
		this.subBoard = new Board(10,10);
		this.board = new Board(10,10);
	}

	public DualBoardPlayer(Ship[] fleet) {
		super(fleet);
		this.subBoard = new Board(10,10);
		this.board = new Board(10,10);
	}

	public Board getSubBoard() {
		return subBoard;
	}

	public boolean placeShip(Ship ship, int y, int x, char orient, boolean submerged) {
		boolean success = false;
		if (!ship.placed) {
			if (ship instanceof Submarine) { // Check for non-linear ships
				if (orient == 'v') {
					if (x > 0 && x < board.getX() && y < board.getY() - (ship.getLength() - 1)) {
						if (submerged && subBoard.placeShip(ship, y, x, orient)) {
							ship.placed = true;
							success = true;
						}
						else if (!submerged && board.placeShip(ship, y, x, orient)) {
							ship.placed = true;
							success = true;
						}
					}
				} else if (orient == 'h') {
					if (x < board.getX() - (ship.getLength() - 1) && y < board.getY() - 2) {
						if (submerged && subBoard.placeShip(ship, y, x, orient)) {
							ship.placed = true;
							success = true;
						}
						else if (!submerged && board.placeShip(ship, y, x, orient)) {
							ship.placed = true;
							success = true;
						}
					}
				}
			}
			else {
				if (!submerged) {
					success = super.placeShip(ship, y, x, orient);
				}
				else {
					if (orient == 'v') {
						if (x < subBoard.getX() && y < subBoard.getY() - ship.getLength()) {
							if (subBoard.placeShip(ship, y, x, orient)) {
								ship.placed = true;
								success = true;
							}
						}
					} else if (orient == 'h') {
						if (x < subBoard.getX() - ship.getLength() && y < subBoard.getY()) {
							if(subBoard.placeShip(ship, y, x, orient)) {
								ship.placed = true;
								success = true;
							}
						}
					}
				}
			}
		}
		if (submerged) {
			subBoard.removeStrayShipCells();
		}
		else {
			board.removeStrayShipCells();
		}
		return success;
	}
}
