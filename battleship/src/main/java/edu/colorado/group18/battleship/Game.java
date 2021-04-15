package edu.colorado.group18.battleship;

public class Game {
    public AbilityPlayer P1;
    public AbilityPlayer P2;
    private int turn;


    public Game() {
        this.P1 = new AbilityPlayer();
        this.P1 = new AbilityPlayer();
        this.turn = 1;
    }
    
    public Game(AbilityPlayer P1, AbilityPlayer P2) {
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
