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
    private int halfHeight, halfWidth;
    private Player player;
    private Robot robot;
    private JFrame frame;

    public Mouse(Player player, JFrame frame) {
        this.player = player;
        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            System.out.println("awt exception");
        }
        this.frame = frame;
        halfHeight = frame.getHeight() / 2;
        halfWidth = frame.getWidth() / 2;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // do nothing
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        double turnAmount = e.getX() - frame.getWidth() / 2; 
        if (player.canRotate) {
            turnAmount /= SENSITIVITY;
            player.setDirection(player.getDirection() + turnAmount);
        }
        robot.mouseMove(frame.getWidth() / 2, frame.getHeight() / 2);
    }

}
