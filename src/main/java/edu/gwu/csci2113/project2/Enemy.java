package edu.gwu.csci2113.project2;

import java.awt.*;

public class Enemy extends GameObject {

    public Enemy(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    protected void draw(Graphics2D g) {

        g.setColor(Color.GREEN);
        g.fillRect(5, 5, GameBoard.gridSize - 20, GameBoard.gridSize - 10);
        g.setColor(Color.RED);
        g.fillPolygon(
                new int[] {
                        GameBoard.gridSize - 15,
                        GameBoard.gridSize - 15,
                        GameBoard.gridSize - 5,
                },
                new int[] {
                        5,
                        GameBoard.gridSize - 5,
                        GameBoard.gridSize / 2
                },
                3
        );
//        g.fillOval(5, 5, GameBoard.gridSize - 10, GameBoard.gridSize - 10);
    }
}