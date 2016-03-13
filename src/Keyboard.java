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
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                player.setBackward(true);
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                player.setRight(true);
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                player.setLeft(true);
                break;
            case KeyEvent.VK_C:
                //cheatMode = !cheatMode;
                break;

                /* remove this later 
            case KeyEvent.VK_1:
                updateMap(1);
                break;
            case KeyEvent.VK_2:
                updateMap(2);
                break;
            case KeyEvent.VK_3:
                updateMap(3);
                break;
            case KeyEvent.VK_4:
                updateMap(4);
                break;
                /* end remove this later */

            case KeyEvent.VK_Q:
                player.setRotateLeft(true);
                break;
            case KeyEvent.VK_E:
                player.setRotateRight(true);
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
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                player.setBackward(false);
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                player.setRight(false);
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                player.setLeft(false);
                break;
            case KeyEvent.VK_Q:
                player.setRotateLeft(false);
                break;
            case KeyEvent.VK_E:
                player.setRotateRight(false);
                break;
        }
    }
}
