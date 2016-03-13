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
	private static final int VIEW_DISTANCE = 10;

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

	private void cast(double angle) {
		// cast a ray from player's position in direction angle relative to
		// the player's direction in radians, drawing a rectangle with height
		// dependent on how soon the ray hits a wall
		double someHeightFunction = 1;
        double direction = frame.getPlayer().getDirection() + angle;
        double playerX = frame.getPlayer().
        int xDirection = ((direction > 0 && direction < Math.PI/2.0) 
                || (direction > 3.0*Math.PI/2.0 && direction < 2.0*Math.PI)) ? 1 : -1;
        int yDirection = ((direction >= 0 && direction < Math.PI) 
                || (direction >= Math.PI && direction <= 2.0*Math.PI)) ? 1 : -1;
        int mapX = (int) frame.getPlayer().getPos().x;
        int mapY = (int) frame.getPlayer().getPos().y;

        // finding horizontal intersections
        // finding vertical intersections
		double height = someHeightFunction * frame.getMap().distanceToWall(
                frame.getPlayer().getPos(), angle);
	}
}
