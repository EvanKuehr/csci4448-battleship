package edu.colorado.group18;

public class MoveEastCommand implements Command {
    private MoveFleet moveTracker;
    public MoveEastCommand(MoveFleet m) { moveTracker = m; }

    public void execute() {
        moveTracker.moveFleet('e', moveTracker.getPlayer().getBoard());
        moveTracker.moveFleet('e', moveTracker.getPlayer().getSubBoard());
    }

    public void undo() {
        moveTracker.moveFleet('w', moveTracker.getPlayer().getBoard());
        moveTracker.moveFleet('w', moveTracker.getPlayer().getSubBoard());
    }
}
