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
                player.setForward(true);
                player.speed = 1;
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                player.setBackward(true);
                player.speed = -1;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                player.setRight(true);
                player.sideSpeed = 1;
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                player.setLeft(true);
                player.sideSpeed = -1;
                break;
            case KeyEvent.VK_C:
                //cheatMode = !cheatMode;
                break;
            case KeyEvent.VK_Q:
                player.setRotateLeft(true);
                player.rotating = -1;
                break;
            case KeyEvent.VK_E:
                player.setRotateRight(true);
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
                player.setForward(false);
                player.speed = 0;
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                player.setBackward(false);
                player.speed = 0;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                player.setRight(false);
                player.sideSpeed = 0;
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                player.setLeft(false);
                player.sideSpeed = 0;
                break;
            case KeyEvent.VK_Q:
                player.setRotateLeft(false);
                player.rotating = 0;
                break;
            case KeyEvent.VK_E:
                player.setRotateRight(false);
                player.rotating = 0;
                break;
        }
    }
}
