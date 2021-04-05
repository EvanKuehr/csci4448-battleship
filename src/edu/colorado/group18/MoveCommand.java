package edu.colorado.group18;

public class MoveCommand implements Command{
    private MoveFleet moveTracker;
    private Direction dir;
    public MoveCommand(MoveFleet m, Direction d) {
        moveTracker = m;
        dir = d;
    }

    public void execute() {
        moveTracker.moveFleet(dir, moveTracker.getPlayer().getBoard());
        moveTracker.moveFleet(dir, moveTracker.getPlayer().getSubBoard());
    }

    public void undo() {
        moveTracker.moveFleet(dir.opposite(), moveTracker.getPlayer().getBoard());
        moveTracker.moveFleet(dir.opposite(), moveTracker.getPlayer().getSubBoard());
    }
}
