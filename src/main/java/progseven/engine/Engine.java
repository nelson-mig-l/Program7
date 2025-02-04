package progseven.engine;

/**
 * A simple java game engine that uses raycasting.
 *
 * @author Sam Lindbloom-Airey, Tim Stoddard
 * @version program007
 */

public class Engine {

    private static final int PIXELS = 500;
    private static final int COLUMNS = 500; // number of vertical columns
    public static final int WIDTH = PIXELS / COLUMNS; // column in pixels
    private static final int VIEW_DISTANCE = 20;
    private static final int WALL_HEIGHT = 100; // height of walls in pixels

    private final EngineFrame frame;
    private final SoundLooper looper;

    /**
     * Creates a new progseven.engine.Engine to run the game.
     */
    public Engine() {
        frame = new EngineFrame();
        looper = new SoundLooper();
    }

    private void render() {
        double[] fieldOfVision = new double[COLUMNS];
        for (int column = 0; column < COLUMNS; column++) {
            double deltaAngle = (((double) column / COLUMNS) - .5) * 1.7;
            fieldOfVision[column] = 5 * Math.cos(deltaAngle)
                    + .7 * WALL_HEIGHT / cast(deltaAngle);
        }
        frame.setFieldOfVision(fieldOfVision);
    }

    private void update() {
        frame.getPlayer().newMove();
        if (!frame.getPlayer().madeItToFinish()) {
            frame.repaint();
        } else {
            frame.nextLevel();
        }
    }

    /**
     * Starts the game.
     */
    public void start() { // start the game loop
        looper.loopSound();
        while (true) {
            update();
            render();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                // do nothing #yolo
            }
        }
    }

    /**
     * Cast a ray from the player's position till we hit a wall, then return the
     * distance to the wall along the angle.
     *
     * @param angle the angle of the ray, relative to the player's direction
     * @return distance to the wall along the angle
     */
    private double cast(double angle) {
        // cast a ray from player's position in direction angle relative to
        // the player's direction in radians, drawing a rectangle with height
        // dependent on how soon the ray hits a wall

        // get the player's position and direction for ease of reference
        double direction = frame.getPlayer().getDirection() + angle;
        double playerX = frame.getPlayer().getPos().x;
        double playerY = frame.getPlayer().getPos().y;

        // finds if x, y directions of ray are positive or negative using quadrant
        // of the angle
        int xDirection = ((direction > 0 && direction < Math.PI / 2.0)
                || (direction > 3.0 * Math.PI / 2.0 && direction < 2.0 * Math.PI))
                ? 1 : -1;
        int yDirection = ((direction >= 0 && direction < Math.PI)
                || (direction >= Math.PI && direction <= 2.0 * Math.PI)) ? 1 : -1;

        // map is tile that the player is in
        int mapX = (int) playerX;
        int mapY = (int) playerY;

        // now map is first intersection for the ray
        mapX += xDirection == 1 ? 1 : 0;
        mapY += yDirection == 1 ? 1 : 0;

        // finding intersections in range
        double currentX, currentY, deltaX, deltaY, hDistance, vDistance, slope;
        boolean hWallFound, vWallFound;
        slope = Math.sin(direction) / Math.cos(direction);

        deltaX = Math.cos(direction) / 50;
        deltaY = Math.sin(direction) / 50;
        hWallFound = false;
        hDistance = 0;
        currentX = playerX;
        currentY = playerY;
        while (!hWallFound && hDistance < VIEW_DISTANCE
                && frame.getMap().inBounds(currentX, currentY)) {
            Tile tile = frame.getMap().getTile((int) currentX, (int) currentY);
            hDistance = Math.sqrt(Math.pow(currentX - playerX, 2)
                    + Math.pow(currentY - playerY, 2));
            if (tile.getType() == Tile.WALL) {
                hWallFound = true;
                break;
            }
            currentY += deltaY;
            currentX += deltaX;
        }
        return (hWallFound) ? hDistance : -1;
    }
}
