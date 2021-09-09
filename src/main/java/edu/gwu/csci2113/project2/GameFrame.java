package edu.gwu.csci2113.project2;

/**
 * CSCI 2113 - Lab 8 Copyright (c) 2019 @author Adnan Shilleh
 */

import javax.swing.*;
import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.*;

public class GameFrame extends JFrame {
    static final long serialVersionUID = 1L;
    private static final int MAX_ENEMIES = 5;
    private static final int MAX_COINS = 5;

    //Declare all buttons and JPanel items
    private GameBoard board;
    private JPanel buttonPanel;
    private JButton leftButton;
    private JButton rightButton;
    private JButton upButton;
    private JButton downButton;
    private JButton endGameButton;
    private JButton startButton;
    private JButton pauseButton;
    private JLabel scoreLabel;
    private GameToken token;
    private Map<Coordinates,EnemyGenerator> enemyGenerators = new HashMap<>();
    private List<Enemy> enemies = new ArrayList<>();
    private Map<Coordinates,Coin> coins = new HashMap<>();
    private Timer timer;
    private int numTicksNoEnemy;
    private int numTicksNoCoin;
    private int score;

    public GameFrame() {
        super();
    }

    public void createAndShowGUI() {

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        board = new GameBoard();
        getContentPane().add(board, BorderLayout.CENTER);

        board.setPaused(true);

        // Create buttons
        leftButton = new JButton("Left");
        rightButton = new JButton("Right");
        upButton = new JButton("Up");
        downButton = new JButton("Down");
        endGameButton = new JButton("End Game");
        startButton = new JButton("Start");
        pauseButton = new JButton("Pause");
        pauseButton.setEnabled(false);
        scoreLabel = new JLabel();

        // Create panel and add buttons
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setPreferredSize(new Dimension(GameBoard.boardSize, 100));
        buttonPanel.add(leftButton);
        buttonPanel.add(rightButton);
        buttonPanel.add(upButton);
        buttonPanel.add(downButton);
        buttonPanel.add(endGameButton);
        buttonPanel.add(startButton);
        buttonPanel.add(pauseButton);
        buttonPanel.add(scoreLabel);

        // Create Listeners
        upButton.addActionListener(e -> moveToken(Direction.NORTH));
        downButton.addActionListener(e -> moveToken(Direction.SOUTH));
        rightButton.addActionListener(e -> moveToken(Direction.EAST));
        leftButton.addActionListener(e -> moveToken(Direction.WEST));

        pauseButton.addActionListener(e -> pause(true));
        startButton.addActionListener(e -> pause(false));
        endGameButton.addActionListener(e -> setGameOver());

        //This allows user to use W, A, S, D as up, down, and side arrows like a real game
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            if (e.getID() == KeyEvent.KEY_PRESSED) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                    case KeyEvent.VK_UP:
                        moveToken(Direction.NORTH);
                        break;
                    case KeyEvent.VK_S:
                    case KeyEvent.VK_DOWN:
                        moveToken(Direction.SOUTH);
                        break;
                    case KeyEvent.VK_D:
                    case KeyEvent.VK_RIGHT:
                        moveToken(Direction.EAST);
                        break;
                    case KeyEvent.VK_A:
                    case KeyEvent.VK_LEFT:
                        moveToken(Direction.WEST);
                        break;
                }
            }
            return false;
        });

        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Create game token
        token = new GameToken(getRandomCoordinates());
        int numEnemyGenerators = 0;
        while (numEnemyGenerators < 5) {
            Coordinates coordinates = getRandomCoordinates();
            if (coordinates.equals(token.getCoordinates())) {
                continue;
            }
            if (enemyGenerators.containsKey(coordinates)) {
                continue;
            }
            EnemyGenerator enemyGenerator = new EnemyGenerator(coordinates);
            enemyGenerators.put(coordinates, enemyGenerator);
            numEnemyGenerators++;
            board.add(enemyGenerator.getComponent());
        }
        board.add(token.getComponent());

        timer = new Timer(500, e -> {
            moveEnemies();
            maybeCreateNewCoin();
            maybeCreateNewEnemy();
        });

        updateScoreLabel();

        disableButtons();

        pack();
        setVisible(true);
    }

    private void moveEnemies() {
        for (Enemy enemy : enemies) {
            Coordinates coordinates = enemy.getCoordinates();
            List<Direction> validDirections = new ArrayList<>(board.getValidDirections(coordinates));
            Direction direction = getRandomItem(validDirections);
            Coordinates newCoordinates = coordinates.move(direction);
            if (!outOfBounds(newCoordinates) && getEnemyAt(newCoordinates) == null) {
                enemy.setCoordinates(newCoordinates);
                if (token.getCoordinates().equals(newCoordinates)) {
                    // you die
                    setGameOver();
                    return;
                }
            }
        }
    }

    private Direction getRandomItem(List<Direction> validDirections) {
        int idx = GameBoard.RANDOM.nextInt(validDirections.size());
        return validDirections.get(idx);
    }

    private void maybeCreateNewEnemy() {
        if (enemies.size() >= MAX_ENEMIES) {
            numTicksNoEnemy = 0;
            return;
        }
        if (numTicksNoEnemy > 5) {
            numTicksNoEnemy = 0;
            List<EnemyGenerator> enemyGenerators = new ArrayList<>(this.enemyGenerators.values());
            int tries = 0;
            while (tries < 3) {
                int enemyGeneratorNumber = GameBoard.RANDOM.nextInt(enemyGenerators.size());
                EnemyGenerator enemyGenerator = enemyGenerators.get(enemyGeneratorNumber);
                Coordinates coords = enemyGenerator.getCoordinates();
                if (getEnemyAt(coords) != null) {
                    // already an enemy sitting at this enemy generator
                    tries++;
                    continue;
                }
                Enemy enemy = new Enemy(coords);
                enemies.add(enemy);
                board.add(enemy.getComponent());
                board.repaint();
                break;
            }
        }
        else {
            numTicksNoEnemy++;
        }
    }

    private void maybeCreateNewCoin() {
        if (enemies.isEmpty() || coins.size() >= MAX_COINS) {
            numTicksNoCoin = 0;
            return;
        }
        if (numTicksNoCoin > 5) {
            numTicksNoCoin = 0;
            int tries = 0;
            while (tries < 3) {
                int enemyNumber = GameBoard.RANDOM.nextInt(enemies.size());
                Enemy enemy = enemies.get(enemyNumber);
                Coordinates coords = enemy.getCoordinates();
                if (coins.containsKey(coords) || enemyGenerators.containsKey(coords)) {
                    // already an object sitting at this enemy
                    tries++;
                    continue;
                }
                Coin coin = new Coin(coords);
                coins.put(coords, coin);
                board.add(coin.getComponent());
                board.repaint();
                break;
            }
        }
        else {
            numTicksNoCoin++;
        }
    }

    private Enemy getEnemyAt(Coordinates coords) {
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            if (enemy.getCoordinates().equals(coords)) {
                return enemy;
            }
        }
        return null;
    }

    private void pause(boolean paused) {
        pauseButton.setEnabled(! paused);
        startButton.setEnabled(paused);
        if (paused) {
            timer.stop();
        }
        else {
            timer.start();
        }
        board.setPaused(paused);
    }

    private void setGameOver() {
        pauseButton.setEnabled(false);
        startButton.setEnabled(false);
        endGameButton.setEnabled(false);
        timer.stop();
        board.setGameOver();
    }

    private Coordinates getRandomCoordinates() {
        return new Coordinates(
                GameBoard.RANDOM.nextInt(GameBoard.numGrids),
                GameBoard.RANDOM.nextInt(GameBoard.numGrids)
        );
    }

    private void moveToken(Direction direction) {
        Coordinates newCoordinates = token.getCoordinates().move(direction);
        if (! outOfBounds(newCoordinates)) {
            token.setCoordinates(newCoordinates);
            Enemy enemy = getEnemyAt(newCoordinates);
            if (enemy != null) {
                if (direction == Direction.WEST) {
                    // you die
                    setGameOver();
                    return;
                }
                else {
                    // enemy defeated
                    enemies.remove(enemy);
                    board.remove(enemy.getComponent());
                    score += 1000;
                    updateScoreLabel();
                }
            }
            Coin coin = coins.remove(newCoordinates);
            if (coin != null) {
                board.remove(coin.getComponent());
                score += 100;
                updateScoreLabel();
            }
            disableButtons();
            repaint();
        }
    }

    private void updateScoreLabel() {
        scoreLabel.setText("Score: " + score);
    }

    private boolean outOfBounds(Coordinates coordinates) {
        return coordinates.getX() < 0 ||
                coordinates.getX() >= GameBoard.numGrids ||
                coordinates.getY() < 0 ||
                coordinates.getY() >= GameBoard.numGrids;
    }

    private void disableButtons() {
        int x = token.getCoordinates().getX();
        int y = token.getCoordinates().getY();

        upButton.setEnabled(!(y == 0));
        downButton.setEnabled(!(y == GameBoard.numGrids - 1));
        leftButton.setEnabled(!(x == 0));
        rightButton.setEnabled(!(x == GameBoard.numGrids - 1));
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        GameFrame gui = new GameFrame();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                gui.createAndShowGUI();
            }
        });
    }

}
