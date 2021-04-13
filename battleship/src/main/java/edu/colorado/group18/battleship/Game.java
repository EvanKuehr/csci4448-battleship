package edu.colorado.group18.battleship;

public class Game {
    public DualBoardPlayer P1;
    public DualBoardPlayer P2;
    private int turn;


    public Game() {
        this.P1 = DualBoardPlayer.getDefaultPlayer();
        this.P1 = DualBoardPlayer.getDefaultPlayer();
        this.turn = 1;
    }
    
    public Game(DualBoardPlayer P1, DualBoardPlayer P2) {
        this.P1 = P1;
        this.P1 = P2;
        this.turn = 1;
    }

    public boolean isTurn(String p) {
        if (p == "p1") {
            return turn % 2 != 0;
        }
        return turn % 2 == 0;
    }

    public void takeTurn(/* params? */) {
        // TODO: let player take turn
        this.turn += 1;
    }
}
