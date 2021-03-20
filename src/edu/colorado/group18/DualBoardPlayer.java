package edu.colorado.group18;

public class DualBoardPlayer extends Player {

	public DualBoardPlayer(Ship[] fleet) { super(fleet); }
	
	private SubBoard SubBoard;
	
	public SubBoard getSubBoard() {
		return SubBoard;
	}
}
