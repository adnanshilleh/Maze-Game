package edu.gwu.csci2113.project2;

import java.awt.*;

public class Coin extends GameObject {

    public static final int DIAMETER = 15;

    public Coin(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    protected void draw(Graphics2D g) {
        g.setColor(Color.ORANGE);
        g.fillOval((GameBoard.gridSize - DIAMETER) / 2, (GameBoard.gridSize - DIAMETER) / 2, DIAMETER, DIAMETER);
    }

}