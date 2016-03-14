/**
 * A single tile on a Map.
 *
 * @author Sam Lindbloom-Airey, Tim Stoddard
 * @version program07
 */

import java.awt.Color;

public class Tile {

    public static final int SPACE = 0, WALL = 1, FINISH = 2, SHOW_PATH = 3;
    private int type;

    public Tile(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public Color getColor(boolean cheatMode) {
        switch (type) {
            case SPACE:
                return Color.white;
            case WALL:
                return Color.black;
            case FINISH:
                return Color.red;
            case SHOW_PATH:
                return cheatMode ? 
                    Color.yellow : Color.white;
        }
        return Color.green;
    }
}
