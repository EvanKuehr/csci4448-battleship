package edu.colorado.group18;

//Implementation of opposite directions inspired from: https://stackoverflow.com/questions/1575146/how-can-i-associate-an-enum-with-its-opposite-value-as-in-cardinal-directions
public enum Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public Direction opposite() {
        switch(this) {
            case NORTH: return Direction.SOUTH;
            case SOUTH: return Direction.NORTH;
            case EAST: return Direction.WEST;
            case WEST: return Direction.EAST;
            default: throw new IllegalStateException("Invalid direction, can't find opposite");
        }
    }

}
