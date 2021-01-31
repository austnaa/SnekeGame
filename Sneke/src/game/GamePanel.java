/**
 * Sneke
 * Austn Attaway
 * January 2021
 */

package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * The GamePanel class is a JPanel that displays the Sneke game graphics
 *
 * @author Austn Attaway
 * @version January 2021
 */
public class GamePanel extends JPanel {

    // colors
    /** The background color */
    public static final Color BACKGROUND_COLOR = new Color(8, 49, 109);

    /** The color of the matrix outline */
    private static final Color MATRIX_OUTLINE = new Color(227, 151, 20);

    /** The color of the sneke */
    private static final Color SNEKE_COLOR = new Color(45, 222, 34);

    /** The color of the cherry */
    private static final Color CHERRY_COLOR = new Color(222, 34, 34);

    // panel settings
    /** The default frame width */
    private static final int PANEL_HEIGHT = 500;

    /** The default frame height */
    private static final int PANEL_WIDTH = 1000;

    // title settings
    /** The x position of the 'Sneke' title */
    private static final int TITLE_X = 650;

    /** The y position of the 'Sneke' title */
    private static final int TITLE_Y = 28;

    /** The width of the 'Sneke' title */
    private static final int TITLE_WIDTH = 165;

    /** The height of the 'Sneke' title */
    private static final int TITLE_HEIGHT = 100;

    /** The font size of the title */
    private static final int TITLE_SIZE = 55;

    // matrix settings
    /** The upper left x position of the matrix */
    private static final int MATRIX_X = 100;

    /** The upper left y position of the matrix */
    private static final int MATRIX_Y = 100;

    /** The width of the matrix */
    private static final int MATRIX_WIDTH = 500;

    /** The height of the matrix */
    private static final int MATRIX_HEIGHT = 300;

    /** The width/height of each square in the matrix */
    private static final int MATRIX_BOX_DIMENSION = 25;

    /** The number of cols the matrix board has */
    public static final int NUM_COLS = MATRIX_WIDTH / MATRIX_BOX_DIMENSION;

    /** The number of rows the matrix board has */
    public static final int NUM_ROWS = MATRIX_HEIGHT / MATRIX_BOX_DIMENSION;

    /**
     * The current Sneke on the matrix
     */
    private final Sneke mySneke;

    /**
     * The Timer that runs when the game is active
     */
    private final Timer myGameTimer;

    /**
     * The JLabel that shows the current score
     */
    private final JLabel scoreLabel;

    /**
     * The JLabel that shows the highscore
     */
    private final JLabel highscoreLabel;

    /**
     * The JLabel that shows the current score when the game is over
     */
    private final JLabel yourScoreLabel;

    /**
     * The JLabel that displays 'GAME OVER' when the game is over
     */
    private final JLabel gameOverLabel;

    /**
     * The JButton that resets the game
     */
    private final JButton myResetButton;

    /**
     * The JButton that starts the game
     */
    private final JButton myStartButton;

    /**
     * The highest score that has been reached
     */
    private long myHighscore;

    /**
     * Constructs a new GamePanel with a default state
     *
     * @param theSneke the Sneke that is used on the panel
     * @throws NullPointerException if theSneke is null
     */
    public GamePanel(final Sneke theSneke) {
        super();

        // JPanel settings
        setLayout(null);
        setBackground(BACKGROUND_COLOR);
        setFocusable(true);
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        // set the sneke and current highscore
        mySneke = Objects.requireNonNull(theSneke, "theSneke can not be null");
        try {
            Scanner sc = new Scanner(new File("./highscore.txt"));
            myHighscore = sc.nextInt();
        } catch (FileNotFoundException theException) {
            theException.printStackTrace();
        }

        myGameTimer = new Timer(300, actionEvent -> update());
        scoreLabel = new JLabel("");
        gameOverLabel = new JLabel("GAME OVER");
        yourScoreLabel = new JLabel("Your score: " + mySneke.getScore());
        highscoreLabel = new JLabel("High score: " + updateHighScore());
        myStartButton = new JButton("Start");
        myResetButton = new JButton("Reset");

        addLabels();
        addButtons();
        addKeyListener();

    }

    /**
     * Add the text labels to the panel
     */
    private void addLabels() {
        // set the 'Sneke' title
        JLabel snekeTitle = new JLabel("Sneke");
        snekeTitle.setFont(new Font("Muna", Font.PLAIN, TITLE_SIZE));
        snekeTitle.setForeground(MATRIX_OUTLINE);
        snekeTitle.setBounds(TITLE_X, TITLE_Y, TITLE_WIDTH, TITLE_HEIGHT);
        add(snekeTitle);

        // set the instructions label
        JLabel instructionsLabel = new JLabel("Press 'a' to turn left and 'd'" +
                " to turn right");
        instructionsLabel.setFont(new Font("Muna", Font.PLAIN, 20));
        instructionsLabel.setForeground(MATRIX_OUTLINE);
        instructionsLabel.setBounds(100, 375, TITLE_WIDTH * 5, TITLE_HEIGHT);
        add(instructionsLabel);

        // set label bounds
        scoreLabel.setBounds(TITLE_X, TITLE_Y + 40, TITLE_WIDTH * 10,
                TITLE_HEIGHT);
        gameOverLabel.setBounds(TITLE_X, TITLE_Y + 100, TITLE_WIDTH,
                TITLE_HEIGHT);
        yourScoreLabel.setBounds(TITLE_X, TITLE_Y + 130, TITLE_WIDTH*3,
                TITLE_HEIGHT);
        highscoreLabel.setBounds(TITLE_X, TITLE_Y + 160, TITLE_WIDTH,
                TITLE_HEIGHT);

        // set the font and color of the smaller labels
        ArrayList<JLabel> labelList = new ArrayList<>();
        labelList.add(scoreLabel);
        labelList.add(gameOverLabel);
        labelList.add(yourScoreLabel);
        labelList.add(highscoreLabel);
        for (JLabel label : labelList) {
            label.setFont(new Font("Muna", Font.PLAIN, 20));
            label.setForeground(MATRIX_OUTLINE);
            add(label);
        }

        gameOverLabel.setVisible(false);
        yourScoreLabel.setVisible(false);
        highscoreLabel.setVisible(false);
    }

    /**
     * Add the buttons to the panel
     */
    private void addButtons() {
        // start button
        myStartButton.setBounds(TITLE_X, TITLE_Y + 100, 100, 30);
        myStartButton.addActionListener(actionEvent -> {

            if (myGameTimer.isRunning()) {
                myGameTimer.stop();
                myStartButton.setText("Start");
            } else {
                myGameTimer.start();
                myStartButton.setText("Pause");
            }
        });

        // reset button
        myResetButton.setBounds(TITLE_X + 105, TITLE_Y + 100, 100, 30);
        myResetButton.addActionListener(actionEvent -> {
            mySneke.setupNewGame();
            highscoreLabel.setVisible(false);
            yourScoreLabel.setVisible(false);
            gameOverLabel.setVisible(false);
            myResetButton.setVisible(false);
            myStartButton.setVisible(true);
            myStartButton.setText("Start");


            repaint();
        });

        myResetButton.setVisible(false);
        add(myStartButton);
        add(myResetButton);
    }

    /**
     * Add the key listener
     */
    private void addKeyListener() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent theEvent) {
                switch (theEvent.getKeyChar()) {
                    case 'a':
                        mySneke.setMyCurrentDirection(
                                Direction.getLeftDirection(mySneke.getCurrentDirection()));
                        break;
                    case 'd':
                        mySneke.setMyCurrentDirection(
                                Direction.getRightDirection(mySneke.getCurrentDirection()));
                        break;
                }
            }
        });
    }

    /**
     * Paints the GamePanel
     *
     * @param theGraphics the Graphics object used to paint
     * @throws NullPointerException if theGraphics is null
     */
    @Override
    public void paintComponent(final Graphics theGraphics) {
        final Graphics2D g2d = (Graphics2D)
                Objects.requireNonNull(theGraphics, "theGraphics is null");
        requestFocus();

        g2d.setBackground(BACKGROUND_COLOR);

        // draw the Sneke
        g2d.setColor(SNEKE_COLOR);
        ArrayList<Point> pointList = mySneke.getPointList();
        for (Point p : pointList) {
            final int xPos = MATRIX_X + ((int) p.getX() * MATRIX_BOX_DIMENSION);
            final int yPos = MATRIX_Y + ((int) p.getY() * MATRIX_BOX_DIMENSION);
            g2d.fillRect(xPos, yPos,
                    MATRIX_BOX_DIMENSION, MATRIX_BOX_DIMENSION);
        }

        // draw the Sneke eye
        g2d.setColor(Color.BLACK);
        g2d.fillRect(MATRIX_X + 1 + ((int) pointList.get(pointList.size() - 1).getX() * MATRIX_BOX_DIMENSION),
                MATRIX_Y + 1 + ((int) pointList.get(pointList.size() - 1).getY() * MATRIX_BOX_DIMENSION),
                 10, 10);

        // draw the cherry
        final Point cherryPoint = mySneke.getCherryPoint();
        g2d.setColor(CHERRY_COLOR);
        final int xPos = MATRIX_X + ((int) cherryPoint.getX() * MATRIX_BOX_DIMENSION);
        final int yPos = MATRIX_Y + ((int) cherryPoint.getY() * MATRIX_BOX_DIMENSION);
        g2d.fillRect(xPos, yPos,
                MATRIX_BOX_DIMENSION, MATRIX_BOX_DIMENSION);

        // draw the matrix grid
        g2d.setColor(MATRIX_OUTLINE);
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                final int x = MATRIX_X + (col * MATRIX_BOX_DIMENSION);
                final int y = MATRIX_Y + (row * MATRIX_BOX_DIMENSION);
                g2d.drawRect(x, y, MATRIX_BOX_DIMENSION, MATRIX_BOX_DIMENSION);
            }
        }

        // draw the line under the title label
        g2d.drawLine(TITLE_X, TITLE_Y + 69, TITLE_X + TITLE_WIDTH,
         TITLE_Y + 69);

        // update the score text
        scoreLabel.setText("Score: " + mySneke.getScore() + "\t\tHigh " +
                "Score: " + updateHighScore());
    }

    /**
     * Updates the matrix or ends the game
     */
    private void update() {
        boolean gameShouldContinue = mySneke.update();

        if (!gameShouldContinue) {
            endGame();
        }

        repaint();
    }

    /**
     * Ends the current game being played
     */
    private void endGame() {
        myGameTimer.stop();

        highscoreLabel.setText("High Score: " + updateHighScore());
        yourScoreLabel.setText("Your Score: " + mySneke.getScore() +
                "   Sneke size: " + mySneke.getPointList().size());

        myStartButton.setVisible(false);
        highscoreLabel.setVisible(true);
        yourScoreLabel.setVisible(true);
        gameOverLabel.setVisible(true);
        myResetButton.setVisible(true);

        repaint();
    }

    /**
     * Updates the highscore and returns it, if it is the end of a game,
     * store the new highscore if there is one
     *
     * @return the highscore
     */
    private long updateHighScore() {
        // get the high score
        final long highestScore = Math.max(myHighscore, mySneke.getScore());

        // update the high score if the current score
        // is higher and the game is over
        if (!myGameTimer.isRunning()) {
            try {
                Scanner sc = new Scanner(new File("./highscore.txt"));
                final int storedScore = sc.nextInt();
                // enter the new high score into the file if it is larger
                // than the previous score
                if (storedScore < highestScore) {
                    myHighscore = highestScore;
                    PrintWriter writer = new PrintWriter("./highscore.txt");
                    writer.print("");
                    writer.print(highestScore);
                    writer.close();
                }
            } catch (FileNotFoundException theException) {
                theException.printStackTrace();
            }
        }

        return highestScore;
    }

}
