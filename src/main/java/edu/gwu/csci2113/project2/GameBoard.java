package edu.gwu.csci2113.project2;

/**
 * CSCI 2113 - Lab 8 Copyright (c) 2019 @author Adnan Shilleh
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.swing.*;



public class GameBoard extends JPanel {
    static final long serialVersionUID = 1L;

    public static final Random RANDOM = new Random();

    public static int gridSize = 30;
    public static int numGrids = 25;
    public static int boardSize = gridSize * numGrids;

    private final GameCell[][] gameCell = new GameCell[numGrids][numGrids];

    private boolean isPaused;
    private boolean isGameOver;

    /**
     *
     */
    public GameBoard() {
        super();
        for (int x = 0; x < numGrids; x++) {
            for (int y = 0; y < numGrids; y++) {
                gameCell[x][y] = new GameCell(x, y);
            }
        }

        setOpaque(false);
        runAldousBroderMazeGeneration();
        setBorder(BorderFactory.createLineBorder(Color.black));
        setBackground(new Color(197, 2, 81, 1));
        setLayout(null);
        setSize(boardSize, boardSize);
    }

    private void runAldousBroderMazeGeneration() {
        Set<GameCell> alreadyVisited = new HashSet<>();
        GameCell currentCell = gameCell[RANDOM.nextInt(numGrids)][RANDOM.nextInt(numGrids)];
        while (alreadyVisited.size() < numGrids * numGrids) {
            Direction randomDirection = Direction.getRandomDirection();
            GameCell targetCell = getCell(currentCell.getX(), currentCell.getY(), randomDirection);
            if (targetCell != null) {
                if (!alreadyVisited.contains(targetCell)) {
                    alreadyVisited.add(targetCell);
                    currentCell.setConnects(randomDirection, true);
                    targetCell.setConnects(randomDirection.getOpposite(), true);
                }
                currentCell = targetCell;
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(boardSize, boardSize);
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(boardSize, boardSize);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // Draw grid
        g2d.setColor(Color.BLACK);
        // Draw grid lines every grid
        for (int x = 0; x < numGrids; x++) {
            for (int y = 0; y < numGrids; y++) {
                int gridX = x * gridSize;
                int gridY = y * gridSize;
                GameCell gameCell = this.gameCell[x][y];
                // draw cell walls
                if (!gameCell.getConnects(Direction.NORTH)) {
                    g2d.drawLine(gridX, gridY, gridX + gridSize, gridY);
                }
                if (!gameCell.getConnects(Direction.SOUTH)) {
                    g2d.drawLine(gridX, gridY + gridSize, gridX + gridSize, gridY + gridSize);
                }
                if (!gameCell.getConnects(Direction.EAST)) {
                    g2d.drawLine(gridX + gridSize, gridY, gridX + gridSize, gridY + gridSize);
                }
                if (!gameCell.getConnects(Direction.WEST)) {
                    g2d.drawLine(gridX, gridY, gridX, gridY + gridSize);
                }
            }
        }
        if (isGameOver) {
            g2d.setColor(Color.GREEN);
            g2d.setFont(getFont().deriveFont(40.0f));
            FontMetrics fm = g2d.getFontMetrics();
            int stringWidth = fm.stringWidth("Game Over");
            int stringHeight = fm.getHeight();
            g2d.drawString(
                    "Game Over",
                    (getWidth() - stringWidth) / 2,
                    (getHeight() - stringHeight) / 2
            );
        }
        else if (isPaused) {
            g2d.setColor(Color.RED);
            g2d.setFont(getFont().deriveFont(40.0f));
            FontMetrics fm = g2d.getFontMetrics();
            int stringWidth = fm.stringWidth("Paused");
            int stringHeight = fm.getHeight();
            g2d.drawString(
                    "Paused",
                    (getWidth() - stringWidth) / 2,
                    (getHeight() - stringHeight) / 2
            );
        }
    }

    private GameCell getCell(int x, int y, Direction direction) {
        x = x + direction.getDeltaX();
        y = y + direction.getDeltaY();
        if (x < 0 || y < 0 || x >= numGrids || y >= numGrids) {
            return null;
        }
        return gameCell[x][y];
    }

    public void setPaused(boolean isPaused) {
        this.isPaused = isPaused;
        repaint();
    }

    public void setGameOver() {
        this.isGameOver = true;
        repaint();
    }

    public Set<Direction> getValidDirections(Coordinates coordinates) {
        GameCell cell = this.gameCell[coordinates.getX()][coordinates.getY()];
        return cell.getConnects();
    }

}
