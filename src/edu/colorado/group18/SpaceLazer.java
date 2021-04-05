package edu.colorado.group18;

public class SpaceLazer extends Ability {
    public Board[] use(AbilityPlayer opponent, int y, int x) {
        Board board = opponent.getBoard();
        Board subBoard = opponent.getSubBoard();
        board.strike(y, x);
        subBoard.strike(y, x);
        return new Board[]{board, subBoard};
    }
}
