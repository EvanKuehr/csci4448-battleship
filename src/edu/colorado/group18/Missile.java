package edu.colorado.group18;

public class Missile extends Ability {
    public Board use(Player opponent, int x, int y) {
        opponent.receiveStrike(y, x);
        return opponent.getBoard();
    }
}
