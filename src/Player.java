/**
 * The player of the engine class.
 * 
 * @author Sam Lindbloom-Airey
 * @version program07
 */

import java.awt.Point;

public class Player {

    public double x, y, direction;

    public Player() {
        this(new Point(Map.DEFAULT_SIZE, 0), 0);
    }
    public Player(double x, double y, double direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }
}
