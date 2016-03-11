
/*
Replace "<Program Name>" with the name of your program.
Replace "<Window Name>" with the name of the window.
*/

import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JFrame;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Font;

public class EngineFrame extends JFrame {
	// double buffering
	private Image dbImage;
	private Graphics dbg;

	public EngineFrame() {
		super("lmao");

		setSize(1000, 800);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.addKeyListener(new KeyListener() {

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
					System.out.println("forward");
					break;
				case KeyEvent.VK_DOWN:
				case KeyEvent.VK_S:
					System.out.println("backward");
					break;
				case KeyEvent.VK_LEFT:
				case KeyEvent.VK_A:
					System.out.println("left");
					break;
				case KeyEvent.VK_RIGHT:
				case KeyEvent.VK_D:
					System.out.println("right");
					break;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				int key = e.getKeyCode();
				switch (key) {
				case KeyEvent.VK_UP:
				case KeyEvent.VK_W:
					System.out.println("STOP forward");
					break;
				case KeyEvent.VK_DOWN:
				case KeyEvent.VK_S:
					System.out.println("STOP backward");
					break;
				case KeyEvent.VK_LEFT:
				case KeyEvent.VK_A:
					System.out.println("STOP left");
					break;
				case KeyEvent.VK_RIGHT:
				case KeyEvent.VK_D:
					System.out.println("STOP right");
					break;
				}
			}
		});
	}

	public void paint(Graphics window) {
		// don't edit this
		dbImage = createImage(getWidth(), getHeight());
		dbg = dbImage.getGraphics();
		paintComponent(dbg);
		window.drawImage(dbImage, 0, 0, this);
	}

	public void paintComponent(Graphics window) {
		// actually painting goes here
	}

}
