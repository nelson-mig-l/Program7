
/**
 * The player of the engine class.
 * 
 * @author Sam Lindbloom-Airey, Tim Stoddard
 * @version program007
 */

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Player {

    public boolean forward, backward, right, left, rotateRight, rotateLeft,
           madeItToFinish, movingFast, canMoveFast, canRotate, cheatMode;
    private static final double moveFastVel = 2.5;
    // these two go from -1, 0, 1 and are set by keyboard input
    public int rotating, speed, sideSpeed;
    // player's direction they are pointing, in radians
    private double direction;
    // speed at which player moves and rotates every tick
    private static final double moveSpeed = 0.05, rotateSpeed = 0.09;
    private Point position;
    private Map map;
    private Timer moveFastTimer, cantMoveFastTimer;

    /**
     * Creates a new player with default position and direction, with the
     * specified map.
     */
    public Player(Map map) {
        this(new Point(1.5, 1.5), 0, map);
    }

    /**
     * Creates a new player with specified position and direction, with the
     * specified map.
     * 
     * @param position
     *           - the player's position
     * @param direction
     *           - the player's direction
     */
    public Player(Point position, double direction, Map map) {
        forward = backward = right = left = rotateRight = rotateLeft = false;
        madeItToFinish = movingFast = false;
        canMoveFast = canRotate = true;
        this.position = new Point(position.x, position.y);
        this.direction = direction;
        this.map = map;

        moveFastTimer = new Timer(501, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                movingFast = false;
                canRotate = true;
                cantMoveFastTimer.start(); // start the recharge timer
            }
        });
        moveFastTimer.setRepeats(false);

        cantMoveFastTimer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canMoveFast = true; // fully recharged!
            }
        });
        cantMoveFastTimer.setRepeats(false);
    }

    /**
     * Allows the player to move at a faster speed for 1/2 a second. Takes 3
     * seconds to recharge.
     */
    public void moveFast() {
        if (canMoveFast) {
            movingFast = true;
            canMoveFast = false;
            canRotate = false;
            moveFastTimer.start();
        }
    }

    public boolean canMoveFast() {
        return canMoveFast;
    }

    /**
     * Returns true if the player has made it to the finish point of the current
     * level.
     * 
     * @return - whether or not the player has finished the current level
     */
    public boolean madeItToFinish() {
        return madeItToFinish;
    }

    /**
     * Moves the player in their current direction, 
     * and handles collision detection.
     * Also handles when the player has made it to the end of the level.
     */
    public void newMove() {

        // this does something maybe
        if (madeItToFinish) {
            return;
        }

        // do any necessary rotation
        // doesn't rotate if we are moving fast
        // because that's the drawback of moving fast!
        direction += canRotate ? rotating * rotateSpeed : 0;
        boundDirection();

        // find out regular movement
        double dx = speed * moveSpeed * Math.cos(direction);
        double dy = speed * moveSpeed * Math.sin(direction);

        // add strafing movement 
        // the swapped sin and cos result from 
        // a pi/2 rotation plugged into the generic | dx |   | cosX -sinX |
        // 2d rotation matrix                       | dy | x | sinX  cosX |
        dx += sideSpeed * moveSpeed * -1 * Math.sin(direction);
        dy += sideSpeed * moveSpeed * Math.cos(direction); 

        // speed limit movement in case 
        // we have both strafing movement and regular
        // movement
        double magnitude = Math.sqrt(dx * dx + dy * dy);
        dx = dx / magnitude * moveSpeed;
        dy = dy / magnitude * moveSpeed;

        // handles moving fast
        // needs to be after speed limit because
        // otherwise we limit the moving fast back
        // down to the speed limit!
        if (movingFast) {
            dx *= moveFastVel;
            dy *= moveFastVel;
        }

        // separately collision checking x and y 
        // makes movement when you hit wall nice
        // so we do it :D
        // bound check x
        if (map.inBounds(position.x + dx, position.y) 
                && map.getTile((int) (position.x + dx), 
                    (int) position.y).getType()
                != Tile.WALL) {
            // do the x movement
            position.x += dx;
        }

        // bound check y
        if (map.inBounds(position.x, position.y + dy) 
                && map.getTile((int) (position.x), 
                    (int) (position.y + dy)).getType()
                != Tile.WALL) {
            // do the y movement
            position.y += dy;
        }

        // handling when you get to the end of the maze thing
        if (map.inBounds(position.x, position.y + dy)
                && map.getTile((int) (position.x + dx), (int) (position.y))
                .getType() == Tile.FINISH) {
            madeItToFinish = true;
        }
    }

    private void boundDirection() {
        direction %= (2 * Math.PI);
        direction += (direction < 0) ? (2 * Math.PI) : 0;
    }

    /**
     * Returns deep copy of the player's position.
     * 
     * @return - the player's position
     */
    public Point getPos() {
        return new Point(position.x, position.y);
    }

    /**
     * Sets the position of the player.
     * 
     * @param newPos
     *           - the player's new position
     */
    public void setPos(Point newPos) {
        position = new Point(newPos.x, newPos.y);
    }

    /**
     * Returns the player's current direction in radians.
     * 
     * @return - the current radian direction of the player
     */
    public double getDirection() {
        return direction;
    }

    /**
     * Sets the player's direction.
     * 
     * @param direction
     *           - the player's new direction
     */
    public void setDirection(double direction) {
        this.direction = direction;
    }

    /**
     * Toggles cheat mode on the minimap.
     */
    public void toggleCheatMode() {
        cheatMode = !cheatMode;
    }

    /**
     * Returns true if cheat mode is currently activated, or false otherwise.
     */
    public boolean getCheatMode() {
        return cheatMode;
    }
}
