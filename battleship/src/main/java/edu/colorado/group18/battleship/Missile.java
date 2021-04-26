package edu.colorado.group18.battleship;

public class Missile implements Ability {
    public Board use(Player opponent, int x, int y) {
        opponent.receiveStrike(y, x);
        return opponent.getBoard();
    }
}
