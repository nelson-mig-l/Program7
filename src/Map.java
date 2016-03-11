/**
 * The map of the engine class.
 * 
 * @author Sam Lindbloom-Airey
 * @version program07
 */

import java.awt.Point;

public class Map {

    public static final int DEFAULT_SIZE = 5;
    public Tile[][] map;

    public Map(int size) {
        map = new Tile[size][size];
    }

    public class Tile {
    
        public int type;
        public static final int WALL = 1;
        public static final int SPACE = 0;

        public Tile(int type) {
            this.type = type;
        }
    }

    public int distanceToWall(Point position, double angle) {
        return 1;
    }
}
