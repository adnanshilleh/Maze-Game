package edu.gwu.csci2113.project2;

import java.awt.*;
import java.time.Instant;

public class EnemyGenerator extends GameObject {



    public EnemyGenerator(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    protected void draw(Graphics2D g) {
        g.setColor(Color.MAGENTA);
        g.fillOval((GameBoard.gridSize - 10) / 2, (GameBoard.gridSize - 10) / 2, 10, 10);
    }

}
