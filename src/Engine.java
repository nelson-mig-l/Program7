/**
 * A simple java game engine that uses raycasting.
 * 
 * @author Sam Lindbloom-Airey, Tim Stoddard
 * @version program007
 */

public class Engine {

	private EngineFrame frame;

	private static final int COLUMNS = 100; // number of vertical columns
	private static final int COLUMN_WIDTH = 10; // width of column in pixels
	private static final int VIEW_DISTANCE = 20;

	/**
	 * Creates a new Engine to run the game.
	 */
	public Engine() {
		frame = new EngineFrame();
	}

	private void render() {
		for (int column = 0; column < COLUMNS; column++) {
			double angle = column / COLUMNS - 0.5;
			cast(angle);

		}
	}

	private void update() {
		frame.getPlayer().move();
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
     * Cast a ray from the player's position till we hit a wall, then return the distance to the wall
     * along the angle.
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
        int xDirection = ((direction > 0 && direction < Math.PI/2.0) 
                || (direction > 3.0*Math.PI/2.0 && direction < 2.0*Math.PI)) ? 1 : -1;
        int yDirection = ((direction >= 0 && direction < Math.PI) 
                || (direction >= Math.PI && direction <= 2.0*Math.PI)) ? 1 : -1;

        // map is tile that the player is in
        int mapX = (int) playerX;
        int mapY = (int) playerY;

        // now map is first intersection for the ray
        mapX += xDirection == 1 ? 1 : 0;
        mapY += yDirection == 1 ? 1 : 0;

        // finding intersections in range
        double currentX, currentY, deltaX, deltaY, hDistance, vDistance;
        boolean hWallFound, vWallFound;

        // finding horizontal intersection point
        currentY = mapY;
        currentX = playerX + (mapY - playerY) / Math.tan(direction);
        deltaY = 1;
        deltaX = deltaY / Math.tan(direction);      
        rWallFound = false;
        hDistance = 0;
        while (!rWallFound && hDistance < VIEW_DISTANCE) {
            Tile tile = frame.getMap().getTile((int) currentX, (int) currentY);
            hDistance = Math.sqrt(Math.pow(currentX - playerX, 2), 
                    Math.pow(currentY - playerY, 2));
            if (tile.getType() == Tile.WALL) {
                rWallFound = true;
            }
            currentY += deltaY;
            currentX += deltaX;
        }

        // finding vertical intersections
        currentX = mapX;
        currentY = playerY + (mapX - playerX) / Math.tan(direction);
        deltaX = 1;
        deltaY = deltaX / Math.tan(direction);
        vWallFound = false;
        while (!vWallFound && vDistance < VIEW_DISTANCE) {
            Tile tile = frame.getMap().getTile((int) currentX, (int) currentY);
            vDistance = Math.sqrt(Math.pow(currentX - playerX, 2), 
                    Math.pow(currentY - playerY, 2));
            if (tile.getType() == Tile.WALL) {
                vWallFound = true;
            }
            currentY += deltaY;
            currentX += deltaX;
        }

        if (vWallFound && hWallFound) {
            return Math.min(vDistance, hDistance);
        }
        
        return vWallFound ? vDistance : (hWallFound ? hDistance : -1);

	}
}
