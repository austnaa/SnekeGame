/**
 * Sneke
 * Austn Attaway
 * January 2021
 */

package game;

import javax.swing.JFrame;

/**
 * Frame is a JFrame the contains the Sneke game
 *
 * @author Austn Attaway
 * @version January 2020
 */
public class Frame extends JFrame {

    /** The title of the JFrame */
    private static final String TITLE = "Sneke";

    /**
     * Creates a game.Frame with the game.Sneke game on it
     */
    public Frame() {
        super(TITLE);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(GamePanel.BACKGROUND_COLOR);

        Sneke sneke = new Sneke(GamePanel.NUM_ROWS, GamePanel.NUM_COLS);
        add(new GamePanel(sneke));

        pack();
        setVisible(true);
    }
}
