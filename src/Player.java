/**
 * The player of the engine class.
 * 
 * @author Sam Lindbloom-Airey
 * @version program07
 */

public class Player {

	public double direction;
	private Point position;

	/**
	 * Creates a new player with default position and direction.
	 */
	public Player() {
		this(new Point(0, 0), -45);
	}

	/**
	 * Creates a new player with specified position and direction.
	 * @param position - the player's position
	 * @param direction - the player's direction
	 */
	public Player(Point position, double direction) {
		this.position = new Point(position.x, position.y);
		this.direction = direction;
	}

	/**
	 * Returns deep copy of the player's position.
	 * @return - the player's position
	 */
	public Point getPos() {
		return new Point(position.x, position.y);
	}

	/**
	 * Sets the position of the player.
	 * @param newPos - the player's new position
	 */
	public void setPos(Point newPos) {
		position = new Point(newPos.x, newPos.y);
	}

	/**
	 * Returns the player's current direction.
	 * @return - the current direction of the player
	 */
	public double getDirection() {
		return direction;
	}

	/**
	 * Sets the player's direction.
	 * @param direction - the player's new direction
	 */
	public void setDirection(double direction) {
		this.direction = direction;
	}
}