package edu.colorado.group18;

public class MoveFleet extends Ability {
    public void use(Player self, char direction) {
        self.getBoard().moveFleet(direction);
        if (self instanceof DualBoardPlayer)
            self.getSubBoard().moveFleet(direction);
    }
}
