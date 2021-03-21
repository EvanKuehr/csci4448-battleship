package edu.colorado.group18;

public class MoveSouthCommand implements Command {
    private MoveFleet moveTracker;
    public MoveSouthCommand(MoveFleet m) { moveTracker = m; }

    public void execute() {
        moveTracker.moveFleet('s', moveTracker.getPlayer().getBoard());
        moveTracker.moveFleet('s', moveTracker.getPlayer().getSubBoard());
    }

    public void undo() {
        moveTracker.moveFleet('n', moveTracker.getPlayer().getBoard());
        moveTracker.moveFleet('n', moveTracker.getPlayer().getSubBoard());
    }
}
