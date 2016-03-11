/**
 * The player of the engine class.
 * 
 * @author Sam Lindbloom-Airey
 * @version program07
 */

import java.awt.Point;

public class Player {

    public Point position;
    public int direction;

    public Player() {
        this(new Point(0, 0), 0);
    }
    public Player(Point position, int direction) {
        this.position = position;
        this.direction = direction;
    }
}