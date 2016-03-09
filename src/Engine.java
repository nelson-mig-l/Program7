/**
 * A simple java game engine that uses raycasting.
 * 
 * @author Sam Lindbloom-Airey
 * @version program07
 */

import java.awt.Point;
import javax.swing.JFrame;

public class Engine {

    private EngineFrame frame;
    private Player player;
    private Map map;

    private static final int COLUMNS = 100; // number of vertical columns
    private static final int COLUMN_WIDTH = 10; // width of column in pixels
    private static final int VIEW_DISTANCE = 10;

    public static void main(String[] args) {
        Engine engine = new Engine();
    }

    public Engine() {
        frame = new EngineFrame();
        player = new Player();
        map = new Map(Map.DEFAULT_SIZE);
    }

    private void render() {
        for (int column = 0; column < COLUMNS; column++) {
            int angle = column / COLUMNS - 0.5;
            cast(angle);

        }
    
    }

    private void update() {
    }
    
    public void loop() { // start the game loop
        while(true) {
            update();
            render();
        }
    }

    private void cast(int angle) {
        // cast a ray from player's position in direction angle relative to
        // the player's direction in radians, drawing a rectangle with height
        // dependent on how soon the ray hits a wall
        angle = player.direction + angle;
    }
}
