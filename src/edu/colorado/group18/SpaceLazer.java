package edu.colorado.group18;

public class SpaceLazer extends Ability {
    public Board[] use(Player opponent, int x, int y) {
        Board board = opponent.getBoard();
        SubBoard subBoard = opponent.getSubBoard();
        board.strike(x, y);
        subBoard.strike(x, y);
        return new Board[]{board, subBoard};
    }
}
