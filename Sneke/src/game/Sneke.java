/**
 * Sneke
 * Austn Attaway
 * January 2021
 */

package game;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/**
 * The Sneke class is dedicated to storing data and functionality for the
 * Sneke sprite on a matrix
 *
 * @author Austn Attaway
 * @version January 2021
 */
public class Sneke {

    /**
     * The number of rows in the matrix
     */
    private final int myRows;

    /**
     * The number of columns in the matrix
     */
    private final int myCols;

    /**
     * The matrix that represents the game board
     */
    private int[][] myMatrix;

    /**
     * The list of points on the matrix where the Sneke is located
     */
    private ArrayList<Point> myPointList;

    /**
     * The current direction the head of the Sneke is moving
     */
    private Direction myCurrentDirection;

    /**
     * The Point on the matrix where a cherry is located
     */
    private Point myCherryPoint;

    /**
     * The current game's score
     */
    private long myScore;

    /**
     * Initializes a new Sneke based on the matrix sizes given
     *
     * @param theRows the number of rows in the matrix
     * @param theCols the number of columns in the matrix
     * @throws IllegalArgumentException if theRows is less than 10
     * @throws IllegalArgumentException if theCols is less than 10
     */
    public Sneke(final int theRows, final int theCols) {
        if (theRows < 10) {
            throw new IllegalArgumentException(
                    "theRows can not be less than 10");
        }
        if (theCols < 10) {
            throw new IllegalArgumentException(
                    "theCols can not be less than 10");
        }

        myRows = theRows;
        myCols = theCols;

        setupNewGame();
    }

    /**
     * Resets the Sneke to a state where a new game can be started
     */
    public void setupNewGame() {
        // setup board
        myMatrix = new int[myRows][myCols];
        myPointList = new ArrayList<>();
        myScore = 0;

        // set the head of the sneke
        final Point startPoint = getRandomPoint();
        myPointList.add(startPoint);

        // set the starting direction depending on where
        // the sneke head is located
        // if the sneke is on the lower half of the matrix, go North
        // if the sneke is on the upper half of the matrix, go South
        myCurrentDirection = startPoint.getY() > (myRows / 2) ?
                Direction.NORTH : Direction.SOUTH;

        // place the first cherry
        spawnCherry();
    }

    /**
     * Returns the current game's score
     * @return the current game's score
     */
    public long getScore() {
        return myScore;
    }

    /**
     * Returns the list of points where the Sneke is located on the matrix
     * @return the list of points where the Sneke is located on the matrix
     */
    public ArrayList<Point> getPointList() {
        return myPointList;
    }

    /**
     * Returns the Point where the cherry is located on the matrix
     * @return the Point where the cherry is located on the matrix
     */
    public Point getCherryPoint() {
        return myCherryPoint;
    }

    /**
     * Returns the current Direction that the Sneke is moving
     * @return the current Direction that the Sneke is moving
     */
    public Direction getCurrentDirection() {
        return myCurrentDirection;
    }

    /**
     * Sets the current direction the Sneke is moving
     *
     * @param theNewDirection the new direction to move towards
     * @throws NullPointerException if theNewDirection is null
     */
    public void setMyCurrentDirection(final Direction theNewDirection) {
        myCurrentDirection = Objects.requireNonNull(theNewDirection,
                "theNewDirection can not be null");
    }

    /**
     * Updates the sneke while the game is running (is invoked by a timer)
     * @return whether or not the game should continue
     */
    public boolean update() {

        // get the Point the Sneke would move to next
        final Point newPoint = getNextPoint();

        // we are able to move normally
        if (isValidMove(newPoint, myCurrentDirection)) {
            // check to see if our move makes us eat a cherry
            final boolean ateCherry =
                    myMatrix[(int)newPoint.getY()][(int)newPoint.getX()] == 2;

            addPoint(newPoint, ateCherry);
            increaseScore(ateCherry);
            updateMatrix();
            return true;

        }
        return false;
    }

    /**
     * Returns the next point that the Sneke wants to move to, depending on
     * the Sneke's head location and the current Direction
     * @return the next point the Sneke wants to move to
     */
    private Point getNextPoint() {
        // get the change in x or y from the
        // current head Point to the next Point
        int dx = 0;
        int dy = 0;
        switch (myCurrentDirection) {
            case NORTH:
                dy = -1;
                break;
            case SOUTH:
                dy = 1;
                break;
            case EAST:
                dx = 1;
                break;
            case WEST:
                dx = -1;
                break;
        }

        final int newX =
                (int) myPointList.get(myPointList.size() - 1).getX() + dx;
        final int newY =
                (int) myPointList.get(myPointList.size() - 1).getY() + dy;
        return new Point(newX, newY);
    }

    /**
     * Spawns a cherry on the matrix at a random valid location
     */
    private void spawnCherry() {
        Point cherryPoint;
        int row;
        int col;

        do {
            cherryPoint = getRandomPoint();
            row = (int) cherryPoint.getY();
            col = (int) cherryPoint.getX();

        } while (myMatrix[row][col] != 0);

        myMatrix[row][col] = 2;
        myCherryPoint = new Point(col, row);
    }

    /**
     * Updates the values in the matrix
     */
    private void updateMatrix() {
        // put the 1s that represent the sneke
        for (Point p : myPointList) {
            myMatrix[(int)p.getY()][(int)p.getX()] = 1;
        }
    }

    /**
     * Returns a random Point in the matrix
     * @return a random Point in the matrix
     */
    private Point getRandomPoint() {

        final Random random = new Random();
        int xPos = random.nextInt(myCols - 2) + 1;
        int yPos = random.nextInt(myRows - 2) + 1;
        return new Point(xPos, yPos);

    }

    /**
     * Adds the given Point to the Sneke
     * @param thePoint the Point to add to the Sneke
     * @param theSnekeAteCherry whether or not the Sneke eat a cherry when it
     *         moves onto the new point
     * @throws NullPointerException if thePoint is null
     */
    private void addPoint(final Point thePoint, boolean theSnekeAteCherry) {
        // add the new point
        myPointList.add(Objects.requireNonNull(thePoint,
                "thePoint can not be null"));

        // the sneke ate a cherry, spawn a new one and DONT remove the
        // last part of the sneke because the Sneke length should increase
        if (theSnekeAteCherry) {
            spawnCherry();
        }
        // the sneke did not eat a cherry, so remove the last point of the
        // sneke to keep the sneke the same size
        else {
            final Point removedPoint = myPointList.get(0);
            myPointList.remove(0);
            final int row = (int) removedPoint.getY();
            final int col = (int) removedPoint.getX();
            myMatrix[row][col] = 0;
        }
        updateMatrix();
    }

    /**
     * Increments the current score depending on the state of the game and
     * whether or not the current move resulted in eating a cherry
     * @param theSnekeAteCherry whether or not the current move
     *         resulted in eating a cherry
     */
    private void increaseScore(boolean theSnekeAteCherry) {
        myScore += theSnekeAteCherry ? myPointList.size() * 10 :
                myPointList.size();
    }

    /**
     * Returns whether or not the given point and direction will be a valid
     * move.
     *
     * Checks whether or not the new Point will be out of bounds or is a part
     * of the Sneke.
     *
     * @param thePoint the current head of the Sneke before the move
     * @param theDirection the current direction the Sneke will move
     * @throws NullPointerException if thePoint is null
     * @return whether or not the given point and direction will be a valid
     *         move.
     */
    private boolean isValidMove(final Point thePoint,
            final Direction theDirection) {

        if (thePoint == null) {
            throw new NullPointerException("thePoint can not be null");
        }
        boolean result = true;

        // test for out of bounds north
        if (theDirection == Direction.NORTH && thePoint.getY() < 0) {
            result = false;
        }
        // test out of bounds south
        else if (theDirection == Direction.SOUTH && thePoint.getY() >= myRows) {
            result = false;
        }
        // test out of bounds east
        else if (theDirection == Direction.EAST && thePoint.getX() >= myCols) {
            result = false;
        }
        // test out of bounds west
        else if (theDirection == Direction.WEST && thePoint.getX() < 0) {
            result = false;
        }
        // test if the next move is part of a sneke
        else if (myMatrix[(int)thePoint.getY()][(int)thePoint.getX()] == 1) {
            result = false;
        }

        return result;
    }

} // end of Sneke class