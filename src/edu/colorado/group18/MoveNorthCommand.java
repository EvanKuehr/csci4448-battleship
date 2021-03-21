package edu.colorado.group18;

public class MoveNorthCommand implements Command {
    private MoveFleet moveTracker;
    public MoveNorthCommand(MoveFleet m) { moveTracker = m; }

    public void execute() {
        moveTracker.moveFleet('n', moveTracker.getPlayer().getBoard());
        moveTracker.moveFleet('n', moveTracker.getPlayer().getSubBoard());
    }

    public void undo() {
        moveTracker.moveFleet('s', moveTracker.getPlayer().getBoard());
        moveTracker.moveFleet('s', moveTracker.getPlayer().getSubBoard());
    }
}
