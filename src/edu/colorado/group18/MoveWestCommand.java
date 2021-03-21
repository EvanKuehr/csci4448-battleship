package edu.colorado.group18;

public class MoveWestCommand implements Command {
    private MoveFleet moveTracker;
    public MoveWestCommand(MoveFleet m) { moveTracker = m; }

    public void execute() {
        moveTracker.moveFleet('w', moveTracker.getPlayer().getBoard());
        moveTracker.moveFleet('w', moveTracker.getPlayer().getSubBoard());
    }

    public void undo() {
        moveTracker.moveFleet('e', moveTracker.getPlayer().getBoard());
        moveTracker.moveFleet('e', moveTracker.getPlayer().getSubBoard());
    }
}
