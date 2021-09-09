package edu.gwu.csci2113.project2;

import javax.swing.*;
import java.awt.*;

public abstract class GameObject {

    private Coordinates coordinates;

    public GameObject(Coordinates coordinates) {
        setCoordinates(coordinates);
        component.setSize(GameBoard.gridSize, GameBoard.gridSize);
    }

    private JComponent component = new JComponent() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            draw((Graphics2D) g);
        }
    };

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
        component.setLocation(
                coordinates.getX() * GameBoard.gridSize,
                coordinates.getY() * GameBoard.gridSize
        );
    }

    public JComponent getComponent() {
        return component;
    }

    protected abstract void draw(Graphics2D g);

    public Coordinates getCoordinates() {
        return coordinates;
    }

}