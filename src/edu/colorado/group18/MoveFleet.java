package edu.colorado.group18;

public class MoveFleet extends Ability {
    public void use(Player self, char direction) {
        self.getBoard().moveFleet(self, direction);
        self.getSubBoard().moveFleet(self, direction);
    }
}
