
/**
 * A class to handle key input for the engine.
 * 
 * @author Sam Lindbloom-Airey, Tim Stoddard
 * @version program07
 */

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

   private Player player;

   public Keyboard(Player player) {
      this.player = player;
   }

   @Override
   public void keyTyped(KeyEvent e) {
      // nothing here!
   }

   @Override
   public void keyPressed(KeyEvent e) {
      int key = e.getKeyCode();
      switch (key) {
         case KeyEvent.VK_UP:
         case KeyEvent.VK_W:
            player.speed = 1;
            break;
         case KeyEvent.VK_DOWN:
         case KeyEvent.VK_S:
            player.speed = -1;
            break;
         case KeyEvent.VK_RIGHT:
         case KeyEvent.VK_D:
            player.sideSpeed = 1;
            break;
         case KeyEvent.VK_LEFT:
         case KeyEvent.VK_A:
            player.sideSpeed = -1;
            break;
         case KeyEvent.VK_C:
            player.toggleCheatMode();
            break;
         case KeyEvent.VK_Q:
            player.rotating = -1;
            break;
         case KeyEvent.VK_E:
            player.rotating = 1;
            break;
         case KeyEvent.VK_SPACE:
            player.moveFast();
            break;
      }
   }

   @Override
   public void keyReleased(KeyEvent e) {
      int key = e.getKeyCode();
      switch (key) {
         case KeyEvent.VK_UP:
         case KeyEvent.VK_W:
            player.speed = player.speed == 1 ? 0 : player.speed;
            break;
         case KeyEvent.VK_DOWN:
         case KeyEvent.VK_S:
            player.speed = player.speed == -1 ? 0 : player.speed;
            ;
            break;
         case KeyEvent.VK_RIGHT:
         case KeyEvent.VK_D:
            player.sideSpeed = player.sideSpeed == 1 ? 0 : player.sideSpeed;
            break;
         case KeyEvent.VK_LEFT:
         case KeyEvent.VK_A:
            player.sideSpeed = player.sideSpeed == -1 ? 0 : player.sideSpeed;
            break;
         case KeyEvent.VK_Q:
            player.rotating = player.rotating == -1 ? 0 : player.rotating;
            break;
         case KeyEvent.VK_E:
            player.rotating = player.rotating == 1 ? 0 : player.rotating;
            break;
      }
   }
}
