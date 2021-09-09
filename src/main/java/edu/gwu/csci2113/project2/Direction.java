package edu.gwu.csci2113.project2;

public enum Direction {

    NORTH(0, -1),
    SOUTH(0, 1),
    EAST(1, 0),
    WEST(-1 , 0);

    private final int deltaX;
    private final int deltaY;

    Direction(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public static Direction getRandomDirection() {
        return values()[GameBoard.RANDOM.nextInt(4)];
    }

    public int getDeltaX() {
        return deltaX;
    }

    public int getDeltaY() {
        return deltaY;
    }

    public Direction getOpposite() {
        switch (this) {
            case NORTH:
                return SOUTH;
            case SOUTH:
                return NORTH;
            case EAST:
                return WEST;
            case WEST:
                return EAST;
            default:
                throw new IllegalArgumentException();
        }
    }
}
