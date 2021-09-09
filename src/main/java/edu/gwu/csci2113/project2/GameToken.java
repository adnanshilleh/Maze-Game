package edu.gwu.csci2113.project2;

/**
 * CSCI 2113 - Lab 8 Copyright (c) 2019 @author Adnan Shilleh
 *
 */
import java.awt.*;

public class GameToken extends GameObject {

   public GameToken(Coordinates coordinates) {
      super(coordinates);
   }

   @Override
   protected void draw(Graphics2D g) {
      g.setColor(Color.BLUE);
      g.fillOval(5, 5, GameBoard.gridSize - 10, GameBoard.gridSize - 10);
   }

}
