package edu.colorado.group18.battleship;

public class Torpedo extends Ability {
    public Board use(AbilityPlayer opponent, int row, boolean surface) {
        Board board;
        if (surface) {
            board = opponent.getBoard();
        } else {
            board = opponent.getSubBoard();
        }
        int col;
        for (col = 0; col < board.getX(); col++) {
            if (board.getCell(row, col) instanceof ShipCell) {
                board.strike(row, col);
                return board;
            }
        }
        board.strike(row, col-1);
        return board;
    }
}
