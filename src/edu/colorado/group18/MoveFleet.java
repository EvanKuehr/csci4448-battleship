package edu.colorado.group18;

public class MoveFleet extends Ability {
    public Board[] use(Player self, char direction) {
        self.getBoard().moveFleet(self, direction);
        self.getSubBoard().moveFleet(self, direction);
        return new Board[]{self.getBoard(), self.getSubBoard()};
    }
}
