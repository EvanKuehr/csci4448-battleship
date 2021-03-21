package edu.colorado.group18;

import java.util.HashMap;
import java.util.Stack;

public class MoveFleet extends Ability {
    private Stack <Command> undoStack;
    private Stack <Command> redoStack;
    private DualBoardPlayer player;

    public MoveFleet(DualBoardPlayer p) {
        undoStack = new Stack <Command>();
        redoStack = new Stack <Command>();
        player = p;
    }

    public DualBoardPlayer getPlayer() { return player; }

    public Board[] use(Command moveCommand) {
        moveCommand.execute();
        undoStack.push(moveCommand);
        return new Board[]{ player.getBoard(), player.getSubBoard() };
    }

    public void undoMove() {
        if (!undoStack.empty()) {
            Command lastMove = undoStack.pop();
            lastMove.undo();
            redoStack.push(lastMove);
        }
    }

    public void redoMove() {
        if (!redoStack.empty()) {
            Command lastUndoneMove = redoStack.pop();
            lastUndoneMove.execute();
            undoStack.push(lastUndoneMove);
        }
    }

    class Tuple<X, Y> {
        public X x;
        public Y y;
        public Tuple(X x, Y y) {
            this.x = x;
            this.y = y;
        }
    }

    private boolean canMove(int y, int x, char direction, Board board) { //helper for moveFleet()
        boolean result = true;

        switch(direction) {
            case 'n': // -Y
                if (y-1 < 0) {
                    result = false;
                }
                break;
            case 'e': // +X
                if (x+1 >= board.getX()) {
                    result = false;
                }
                break;
            case 's': // +Y
                if (y+1 >= board.getY()) {
                    result = false;
                }
                break;
            case 'w': // -X
                if (x-1 < 0) {
                    result = false;
                }
                break;
        }
        return result;
    }

    public void moveFleet(char direction, Board board) {
        boolean canMoveFleet = true;
        for (int y = 0; y < board.getY(); y++) {
            for (int x = 0; x < board.getX(); x++) {
                if (board.cells[y][x] instanceof ShipCell) {
                    if (!canMove(y,x,direction, board)) {
                        canMoveFleet = false;
                    }
                }
            }
        }
        if (canMoveFleet) { //can only move fleet if all ships/ship cells can move in a direction
            HashMap<Character, Tuple<Integer, Integer>> dirMap = new HashMap<>(); // Map between direction <-> delta X/Y
            dirMap.put('n', new Tuple<>(0, -1));
            dirMap.put('e', new Tuple<>(1, 0));
            dirMap.put('s', new Tuple<>(0, 1));
            dirMap.put('w', new Tuple<>(-1, 0));

            int dy = dirMap.get(direction).y;
            int dx = dirMap.get(direction).x;

            Cell[][] cellsCopy = board.cells;
            board.cells = new Cell[board.getY()][board.getX()];
            for (int y = 0; y < board.getY(); y++) {
                for (int x = 0; x < board.getX(); x++) {
                    if (cellsCopy[y][x] instanceof ShipCell) {
                        board.cells[y+dy][x+dx] = cellsCopy[y][x];
                    }
                }
            }
        }
    }
}

