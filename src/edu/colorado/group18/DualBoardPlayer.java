package edu.colorado.group18;

public class DualBoardPlayer extends Player {

	private SubBoard subBoard;
	private Board board;

	public DualBoardPlayer(Ship[] fleet) {
		super(fleet);
		this.subBoard = new SubBoard(10,10);
		this.board = new Board(10,10);
	}

	public SubBoard getSubBoard() {
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
				success = super.placeShip(ship, y, x, orient);
			}
		}
		return success;
	}
}
