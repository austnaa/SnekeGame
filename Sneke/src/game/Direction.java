/**
 * Sneke
 * Austn Attaway
 * January 2021
 */

package game;

import java.util.Objects;

/**
 * The Direction Enum is an Enum dedicated to describing the direction a
 * Sneke can move, either North, South, East, or West, while also providing
 * useful functionality for getting the Direction to the left or right of
 * another
 *
 * @author Austn Attaway
 * @version January 2021
 */
public enum Direction {
    /**
     * The types
     */
    NORTH, SOUTH, EAST, WEST;

    /**
     * Returns the Direction that results from a 90 degree left turn from
     * theOldDirection
     *
     * @param theOldDirection the old Direction that provides perspective for
     *         the returned Direction
     * @throws NullPointerException if theOldDirection is null
     * @return the Direction that results from a 90 degree left turn from
     *         theOldDirection
     */
    public static Direction getLeftDirection(final Direction theOldDirection) {
        Direction newDirection = null;
        switch (Objects.requireNonNull(theOldDirection,
                "theOldDirection can not be null")) {
            case NORTH:
                newDirection = Direction.WEST;
                break;
            case SOUTH:
                newDirection = Direction.EAST;
                break;
            case EAST:
                newDirection = Direction.NORTH;
                break;
            case WEST:
                newDirection = Direction.SOUTH;
                break;
        }
        return newDirection;
    }

    /**
     * Returns the Direction that results from a 90 degree right turn from
     * theOldDirection
     *
     * @param theOldDirection the old Direction that provides perspective for
     *         the returned Direction
     * @throws NullPointerException if theOldDirection is null
     * @return the Direction that results from a 90 degree right turn from
     *         theOldDirection
     */
    public static Direction getRightDirection(final Direction theOldDirection) {
        Direction newDirection = null;
        switch (Objects.requireNonNull(theOldDirection,
                "theOldDirection can not be null")) {
            case NORTH:
                newDirection = Direction.EAST;
                break;
            case SOUTH:
                newDirection = Direction.WEST;
                break;
            case EAST:
                newDirection = Direction.SOUTH;
                break;
            case WEST:
                newDirection = Direction.NORTH;
                break;
        }
        return newDirection;
    }

}
