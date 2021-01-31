/**
 * Sneke
 * Austn Attaway
 * January 2021
 */

package game;

/**
 * The game.Main class includes the entry point for the game.Sneke program
 *
 * @author Austn Attaway
 * @version December 2020
 */
public class Main {

    /**
     * The entry point for the game.Sneke program
     * @param theArgs the command line arguments (unused)
     */
    public static void main(final String[] theArgs) {

        // create the GUI
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Frame();
            }
        });
    }
}
