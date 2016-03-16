
/**
 * Listens to mouse input and locks mouse in place
 * for engine.
 * 
 * @author Sam Lindbloom-Airey
 * @version program07
 */

import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.Robot;
import java.awt.AWTException;
import javax.swing.JFrame;

public class Mouse implements MouseMotionListener {

   public static final double SENSITIVITY = Math.PI * 70;
   private Player player;
   private Robot robot;

   public Mouse(Player player) {
      this.player = player;
      try {
         this.robot = new Robot();
      } catch (AWTException e) {
         System.out.println("awt exception");
      }
   }

   @Override
   public void mouseDragged(MouseEvent e) {
      // do nothing
   }

   @Override
   public void mouseMoved(MouseEvent e) {
      int halfRaycastingWid = 260;
      double turnAmount = e.getX() - halfRaycastingWid;
      if (player.canRotate) {
         turnAmount /= SENSITIVITY;
         player.setDirection(player.getDirection() + turnAmount);
      }
      robot.mouseMove(halfRaycastingWid, 200);
   }

}
