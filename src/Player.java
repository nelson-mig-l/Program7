
/**
 * The player of the engine class.
 * 
 * @author Sam Lindbloom-Airey, Tim Stoddard
 * @version program007
 */

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Player {

   public boolean forward, backward, right, left, rotateRight, rotateLeft,
         madeItToFinish, movingFast, canMoveFast, canRotate;
   private static final double moveFastVel = 2.5;
   private double direction;
   private Point position;
   private Map map;
   private Timer moveFastTimer, cantMoveFastTimer;

   /**
    * Creates a new player with default position and direction, with the
    * specified map.
    */
   public Player(Map map) {
      this(new Point(0.5, 0.5), Math.PI/4, map);
   }

   /**
    * Creates a new player with specified position and direction, with the
    * specified map.
    * 
    * @param position
    *           - the player's position
    * @param direction
    *           - the player's direction
    */
   public Player(Point position, double direction, Map map) {
      forward = backward = right = left = rotateRight = rotateLeft = false;
      madeItToFinish = movingFast = false;
      canMoveFast = canRotate = true;
      this.position = new Point(position.x, position.y);
      this.direction = direction;
      this.map = map;

      moveFastTimer = new Timer(501, new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            movingFast = false;
            canRotate = true;
            cantMoveFastTimer.start(); // start the recharge timer
         }
      });
      moveFastTimer.setRepeats(false);

      cantMoveFastTimer = new Timer(3000, new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            canMoveFast = true; // fully recharged!
         }
      });
      cantMoveFastTimer.setRepeats(false);
   }

   /**
    * Allows the player to move at a faster speed for 1/2 a second. Takes 3
    * seconds to recharge.
    */
   public void moveFast() {
      if (canMoveFast) {
         movingFast = true;
         canMoveFast = false;
         canRotate = false;
         moveFastTimer.start();
      }
   }

   public boolean canMoveFast() {
      return canMoveFast;
   }

   /**
    * Returns true if the player has made it to the finish point of the current
    * level.
    * 
    * @return - whether or not the player has finished the current level
    */
   public boolean madeItToFinish() {
      return madeItToFinish;
   }

   /**
    * Moves the player in its current direction. Detects collisions with walls.
    */
   public void move() {
      if (madeItToFinish) {
         return;
      }
      double dirVel = 5 * Math.PI / 180;
      if (rotateRight) {
         direction -= dirVel;
         direction %= (Math.PI * 2);
      }
      if (rotateLeft) {
         direction += dirVel;
         direction %= (Math.PI * 2);
      }
      double vel = 0.05 * (movingFast ? moveFastVel : 1);
      if (forward && !backward) {
         double dx = vel * Math.cos(direction),
               dy = vel * -Math.sin(direction);
         if (position.x + dx > 0 && position.x + dx < map.getWidth()
               && map.getTile((int) (position.x + dx), (int) (position.y))
                     .getType() != Tile.WALL) {
            if (map.getTile((int) (position.x + dx), (int) (position.y))
                  .getType() == Tile.FINISH) {
               madeItToFinish = true;
            }
            position.x += dx;
         }
         if (position.y + dy > 0 && position.y + dy < map.getHeight()
               && map.getTile((int) (position.x), (int) (position.y + dy))
                     .getType() != Tile.WALL) {
            if (map.getTile((int) (position.x), (int) (position.y + dy))
                  .getType() == Tile.FINISH) {
               madeItToFinish = true;
            }
            position.y += dy;
         }
      }
      if (backward && !forward) {
         double dx = vel * -Math.cos(direction),
               dy = vel * Math.sin(direction);
         if (position.x + dx > 0 && position.x + dx < map.getWidth()
               && map.getTile((int) (position.x + dx), (int) (position.y))
                     .getType() != Tile.WALL) {
            if (map.getTile((int) (position.x + dx), (int) (position.y))
                  .getType() == Tile.FINISH) {
               madeItToFinish = true;
            }
            position.x += dx;
         }
         if (position.y + dy > 0 && position.y + dy < map.getHeight()
               && map.getTile((int) (position.x), (int) (position.y + dy))
                     .getType() != Tile.WALL) {
            if (map.getTile((int) (position.x), (int) (position.y + dy))
                  .getType() == Tile.FINISH) {
               madeItToFinish = true;
            }
            position.y += dy;
         }
      }
      if (left && !right) {
         double dx = vel * -Math.sin(direction),
               dy = vel * -Math.cos(direction);
         if (position.x + dx > 0 && position.x + dx < map.getWidth()
               && map.getTile((int) (position.x + dx), (int) (position.y))
                     .getType() != Tile.WALL) {
            if (map.getTile((int) (position.x + dx), (int) (position.y))
                  .getType() == Tile.FINISH) {
               madeItToFinish = true;
            }
            position.x += dx;
         }
         if (position.y + dy > 0 && position.y + dy < map.getHeight()
               && map.getTile((int) (position.x), (int) (position.y + dy))
                     .getType() != Tile.WALL) {
            if (map.getTile((int) (position.x), (int) (position.y + dy))
                  .getType() == Tile.FINISH) {
               madeItToFinish = true;
            }
            position.y += dy;
         }
      }
      if (right && !left) {
         double dx = vel * Math.sin(direction), dy = vel * Math.cos(direction);
         if (position.x + dx > 0 && position.x + dx < map.getWidth()
               && map.getTile((int) (position.x + dx), (int) (position.y))
                     .getType() != Tile.WALL) {
            if (map.getTile((int) (position.x + dx), (int) (position.y))
                  .getType() == Tile.FINISH) {
               madeItToFinish = true;
            }
            position.x += dx;
         }
         if (position.y + dy > 0 && position.y + dy < map.getHeight()
               && map.getTile((int) (position.x), (int) (position.y + dy))
                     .getType() != Tile.WALL) {
            if (map.getTile((int) (position.x), (int) (position.y + dy))
                  .getType() == Tile.FINISH) {
               madeItToFinish = true;
            }
            position.y += dy;
         }
      }
   }

   /**
    * Returns whether or not the player is currently rotating to the right.
    * 
    * @return whether or not the player is currently rotating to the right
    */
   public boolean isRotatingRight() {
      return rotateRight;
   }

   /**
    * Sets whether or not the player is currently rotating to the right.
    * 
    * @param rotateRight
    *           - whether or not the player will now rotate to the right
    */
   public void setRotateRight(boolean rotateRight) {
      if (canRotate) {
         this.rotateRight = rotateRight;
      }
   }

   /**
    * Returns whether or not the player is currently rotating to the left.
    * 
    * @return whether or not the player is currently rotating to the left
    */
   public boolean isRotatingLeft() {
      return rotateLeft;
   }

   /**
    * Sets whether or not the player is currently rotating to the left.
    * 
    * @param rotateLeft
    *           - whether or not the player will now rotate to the left
    */
   public void setRotateLeft(boolean rotateLeft) {
      if (canRotate) {
         this.rotateLeft = rotateLeft;
      }
   }

   /**
    * Returns deep copy of the player's position.
    * 
    * @return - the player's position
    */
   public Point getPos() {
      return new Point(position.x, position.y);
   }

   /**
    * Sets the position of the player.
    * 
    * @param newPos
    *           - the player's new position
    */
   public void setPos(Point newPos) {
      position = new Point(newPos.x, newPos.y);
   }

   /**
    * Returns the player's current direction in radians.
    * 
    * @return - the current radian direction of the player
    */
   public double getDirection() {
      return direction;
   }

   /**
    * Sets the player's direction.
    * 
    * @param direction
    *           - the player's new direction
    */
   public void setDirection(double direction) {
      this.direction = direction;
   }

   /**
    * Returns true if the player is moving forward, or false otherwise.
    * 
    * @return true if the player is moving forward, or false otherwise
    */
   public boolean isForward() {
      return forward;
   }

   /**
    * @param forward
    *           - whether or not the player is currently moving forward
    */
   public void setForward(boolean forward) {
      this.forward = forward;
   }

   /**
    * Returns true if the player is moving backwards, or false otherwise.
    * 
    * @return true if the player is moving backwards, or false otherwise
    */
   public boolean isBackward() {
      return backward;
   }

   /**
    * @param backward
    *           - whether or not the player is currently moving backward
    */
   public void setBackward(boolean backward) {
      this.backward = backward;
   }

   /**
    * Returns true if the player is moving to the right, or false otherwise.
    * 
    * @return true if the player is moving to the right, or false otherwise
    */
   public boolean isRight() {
      return right;
   }

   /**
    * @param right
    *           - whether or not the player is currently moving to the right
    */
   public void setRight(boolean right) {
      this.right = right;
   }

   /**
    * Returns true if the player is moving to the left, or false otherwise.
    * 
    * @return true if the player is moving to the left, or false otherwise
    */
   public boolean isLeft() {
      return left;
   }

   /**
    * @param left
    *           - whether or not the player is currently moving to the left
    */
   public void setLeft(boolean left) {
      this.left = left;
   }
}
