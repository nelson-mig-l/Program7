/**
 * The player of the engine class.
 * 
 * @author Sam Lindbloom-Airey
 * @version program07
 */

public class Player {

	public boolean forward, backward, right, left, rotateRight, rotateLeft;
	private double direction;
	private Point position;
	private Map map;

	/**
	 * Creates a new player with default position and direction, with the specified map.
	 */
	public Player(Map map) {
		this(new Point(0.5, 0.5), -45, map);
	}

	/**
	 * Creates a new player with specified position and direction, with the specified map.
	 * @param position - the player's position
	 * @param direction - the player's direction
	 */
	public Player(Point position, double direction, Map map) {
		forward = backward = right = left = rotateRight = rotateLeft = false;
		this.position = new Point(position.x, position.y);
		this.direction = direction;
		this.map = map;
	}
	
	/**
	 * Moves the player in its current direction. Detects collisions with walls.
	 */
	public void move() {
		double dirVel = 5;
		if (rotateRight) {
			direction -= dirVel;
			direction %= 360;
		}
		if (rotateLeft) {
			direction += dirVel;
			direction %= 360;
		}
		double radians = Math.toRadians(direction), vel = 0.04;
		if (forward && !backward) {
			double dx = vel * Math.cos(radians), dy = vel * -Math.sin(radians);
			if (map.getTile((int) (position.x + dx), (int) (position.y)).getType() != Tile.WALL) {
				position.x += dx;
			}
			if (map.getTile((int) (position.x), (int) (position.y + dy)).getType() != Tile.WALL) {
				position.y += dy;
			}
		}
		if (backward && !forward) {
			double dx = vel * -Math.cos(radians), dy = vel * Math.sin(radians);
			if (map.getTile((int) (position.x + dx), (int) (position.y)).getType() != Tile.WALL) {
				position.x += dx;
			}
			if (map.getTile((int) (position.x), (int) (position.y + dy)).getType() != Tile.WALL) {
				position.y += dy;
			}
		}
		if (left && ! right) {
			double dx = vel * -Math.sin(radians), dy = vel * -Math.cos(radians);
			if (map.getTile((int) (position.x + dx), (int) (position.y)).getType() != Tile.WALL) {
				position.x += dx;
			}
			if (map.getTile((int) (position.x), (int) (position.y + dy)).getType() != Tile.WALL) {
				position.y += dy;
			}
		}
		if (right && !left) {
			double dx = vel * Math.sin(radians), dy = vel * Math.cos(radians);
			if (map.getTile((int) (position.x + dx), (int) (position.y)).getType() != Tile.WALL) {
				position.x += dx;
			}
			if (map.getTile((int) (position.x), (int) (position.y + dy)).getType() != Tile.WALL) {
				position.y += dy;
			}
		}
	}

	/**
	 * Returns whether or not the player is currently rotating to the right.
	 * @return whether or not the player is currently rotating to the right
	 */
	public boolean isRotatingRight() {
		return rotateRight;
	}

	/**
	 * Sets whether or not the player is currently rotating to the right.
	 * @param rotateRight - whether or not the player is currently rotating to the right
	 */
	public void setRotateRight(boolean rotateRight) {
		this.rotateRight = rotateRight;
	}

	/**
	 * Returns whether or not the player is currently rotating to the left.
	 * @return whether or not the player is currently rotating to the left
	 */
	public boolean isRotatingLeft() {
		return rotateLeft;
	}

	/**
	 * Sets whether or not the player is currently rotating to the left.
	 * @param rotateLeft - whether or not the player is currently rotating to the left
	 */
	public void setRotateLeft(boolean rotateLeft) {
		this.rotateLeft = rotateLeft;
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

	/**
	 * Returns true if the player is moving forward, or false otherwise.
	 * @return true if the player is moving forward, or false otherwise
	 */
	public boolean isForward() {
		return forward;
	}

	/**
	 * @param forward - whether or not the player is currently moving forward
	 */
	public void setForward(boolean forward) {
		this.forward = forward;
	}

	/**
	 * Returns true if the player is moving backwards, or false otherwise.
	 * @return true if the player is moving backwards, or false otherwise
	 */
	public boolean isBackward() {
		return backward;
	}

	/**
	 * @param backward - whether or not the player is currently moving backward
	 */
	public void setBackward(boolean backward) {
		this.backward = backward;
	}

	/**
	 * Returns true if the player is moving to the right, or false otherwise.
	 * @return true if the player is moving to the right, or false otherwise
	 */
	public boolean isRight() {
		return right;
	}

	/**
	 * @param right - whether or not the player is currently moving to the right
	 */
	public void setRight(boolean right) {
		this.right = right;
	}

	/**
	 * Returns true if the player is moving to the left, or false otherwise.
	 * @return true if the player is moving to the left, or false otherwise
	 */
	public boolean isLeft() {
		return left;
	}

	/**
	 * @param left - whether or not the player is currently moving to the left
	 */
	public void setLeft(boolean left) {
		this.left = left;
	}
	
	
}