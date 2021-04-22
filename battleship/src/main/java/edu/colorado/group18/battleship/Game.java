package edu.colorado.group18.battleship;

public class Game {
    public AbilityPlayer P1;
    public AbilityPlayer P2;
    private int turn;
    private int winner;


    public Game() {
        this.P1 = new AbilityPlayer();
        this.P2 = new AbilityPlayer();
        this.turn = 1;
        this.winner= -1;
    }
    
    public Game(AbilityPlayer P1, AbilityPlayer P2) {
        this.P1 = P1;
        this.P2 = P2;
        this.turn = 1;
        this.winner = -1;
    }

    public int getWinStatus(int playerID) {
        if (winner == -1) {
            return -1;
        }
        else {
            if (winner == playerID) {
                return 1;
            }
            else {
                return 0;
            }
        }
    }

    public boolean isTurn(int p) {
        if (p == 1) {
            return this.turn % 2 != 0;
        }
        return this.turn % 2 == 0;
    }

    public void takeTurn(/* params? */) {
        // TODO: let player take turn
        this.turn += 1;
    }
}
