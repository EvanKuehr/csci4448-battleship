package edu.colorado.group18;

public class SpaceLazer extends Ability {
    public Board[] use(DualBoardPlayer opponent, int x, int y) {
        Board board = opponent.getBoard();
        Board subBoard = opponent.getSubBoard();
        board.strike(y, x);
        subBoard.strike(y, x);
        return new Board[]{board, subBoard};
    }
}
