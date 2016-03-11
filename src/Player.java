/**
 * The player of the engine class.
 * 
 * @author Sam Lindbloom-Airey
 * @version program07
 */

import java.awt.Point;

public class Player {

    public Point position;
    public double direction;

    public Player() {
        this(new Point(Map.DEFAULT_SIZE, 0), 0);
    }
    public Player(Point position, double direction) {
        this.position = position;
        this.direction = direction;
    }
}
