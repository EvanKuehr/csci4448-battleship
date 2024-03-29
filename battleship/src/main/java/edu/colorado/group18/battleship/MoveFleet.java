package edu.colorado.group18.battleship;

import java.util.HashMap;
import java.util.Stack;

public class MoveFleet implements Ability {
    private Stack <Command> undoStack;
    private Stack <Command> redoStack;
    private AbilityPlayer player;

    public MoveFleet(AbilityPlayer p) {
        undoStack = new Stack <Command>();
        redoStack = new Stack <Command>();
        player = p;
    }

    public AbilityPlayer getPlayer() { return player; }

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

    private boolean canMove(int y, int x, Direction direction, Board board) { //helper for moveFleet()
        boolean result = true;

        switch(direction) {
            case NORTH: // -Y
                if (y-1 < 0) {
                    result = false;
                }
                break;
            case EAST: // +X
                if (x+1 >= board.getX()) {
                    result = false;
                }
                break;
            case SOUTH: // +Y
                if (y+1 >= board.getY()) {
                    result = false;
                }
                break;
            case WEST: // -X
                if (x-1 < 0) {
                    result = false;
                }
                break;
        case INVALID:
            break;
        default:
            break;
        }
        return result;
    }

    public void moveFleet(Direction direction, Board board) {
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
            HashMap<Direction, Tuple<Integer, Integer>> dirMap = new HashMap<>(); // Map between direction <-> delta X/Y
            dirMap.put(Direction.NORTH, new Tuple<>(0, -1));
            dirMap.put(Direction.EAST, new Tuple<>(1, 0));
            dirMap.put(Direction.SOUTH, new Tuple<>(0, 1));
            dirMap.put(Direction.WEST, new Tuple<>(-1, 0));

            int dy = dirMap.get(direction).y;
            int dx = dirMap.get(direction).x;

            Cell[][] cellsCopy = board.cells.clone();
            board.cells = new Cell[board.getY()][board.getX()];
            for (int y = 0; y < board.getY(); y++) {
                for (int x = 0; x < board.getX(); x++) {
                    if (y==0 && direction == Direction.SOUTH) {
                        board.cells[y][x] = new Cell();
                    }
                    else if (x==0 && direction == Direction.EAST) {
                        board.cells[y][x] = new Cell();
                    }
                    else if (y==board.getY()-1 && direction == Direction.NORTH) {
                        board.cells[y][x] = new Cell();
                    }
                    else if (x==board.getX()-1 && direction == Direction.WEST) {
                        board.cells[y][x] = new Cell();
                    }

                    if (canMove(y,x,direction,board)) {
                        board.cells[y+dy][x+dx] = cellsCopy[y][x];
                    }

                }
            }
        }
    }
}

