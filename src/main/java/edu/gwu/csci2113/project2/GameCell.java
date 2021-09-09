package edu.gwu.csci2113.project2;

import java.util.HashSet;
import java.util.Set;

public class GameCell {

    // directions containing cells connected to this cell
    private final Set<Direction> connectDirections = new HashSet<>();

    private int x;
    private int y;

    public GameCell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean getConnects(Direction direction) {
        return connectDirections.contains(direction);
    }

    public void setConnects(Direction direction, boolean value) {
        if (value) {
            connectDirections.add(direction);
        }
        else {
            connectDirections.remove(direction);
        }
    }

    public Set<Direction> getConnects() {
        return connectDirections;
    }

}
