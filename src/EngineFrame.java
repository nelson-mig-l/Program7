/**
 * Frame to display the engine
 * 
 * @author Tim Stoddard
 * @version program007
 */

import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JFrame;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class EngineFrame extends JFrame {
	// double buffering
	private Image dbImage;
	private Graphics dbg;
	private Map map;
	private Player player;
	private boolean cheatMode;

	/**
	 * Creates a new EngineFrame to display and render the game.
	 */
	public EngineFrame(Player player) {
		super("lmao");
		
		map = new Map(4);
		this.player = player;
		cheatMode = false;

		setSize(700, 700);
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
					cheatMode = !cheatMode;
					break;
				case KeyEvent.VK_1:
					map = new Map(1);
					break;
				case KeyEvent.VK_2:
					map = new Map(2);
					break;
				case KeyEvent.VK_3:
					map = new Map(3);
					break;
				case KeyEvent.VK_4:
					map = new Map(4);
					break;
				case KeyEvent.VK_Q:
					player.setRotateLeft(true);
					break;
				case KeyEvent.VK_E:
					player.setRotateRight(true);
					break;
				}
				repaint();
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
				repaint();
			}
		});
	}
	
	/**
	 * Returns the current map.
	 * @return The current map
	 */
	public Map getMap() {
		return map;
	}

	/**
	 * 
	 */
	public void paint(Graphics window) {
		// don't edit this
		dbImage = createImage(getWidth(), getHeight());
		dbg = dbImage.getGraphics();
		paintComponent(dbg);
		window.drawImage(dbImage, 0, 0, this);
	}

	/**
	 * 
	 * @param g
	 */
	public void paintComponent(Graphics g) {
		// actually painting goes here
		
		/* temporary graphics */
		for (int i = 0; i < map.getHeight(); i++) {
			for (int j = 0; j < map.getWidth(); j++) {
				int type = map.getTile(i, j).getType();
				if (type == Tile.SPACE) {
					g.setColor(Color.white);
				} else if (type == Tile.WALL) {
					g.setColor(Color.black);
				} else if (type == Tile.FINISH) {
					g.setColor(Color.red);
				} else if (type == Tile.SHOW_PATH) {
					if (!cheatMode) {
						g.setColor(Color.white);
					} else {
						g.setColor(Color.yellow);
					}
				}
				g.fillRect(i*20, j*20 + 20, 20, 20);
			}
		}
		java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
		g2.setColor(Color.green);
		double angle = Math.toRadians(360-player.getDirection()+90);
		g2.rotate(angle, player.getPos().x*20, player.getPos().y*20);
		g2.fillPolygon(new int[] {
				(int) (player.getPos().x*20)-10, (int) (player.getPos().x*20), (int) (player.getPos().x*20)+10
		}, new int[] {
				(int) (player.getPos().y*20)+10, (int) (player.getPos().y*20)-30, (int) (player.getPos().y*20)+10
		}, 3);
		g2.rotate(-angle, player.getPos().x*20, player.getPos().y*20);
		g.setColor(Color.black);
		g.drawString(player.getDirection()+"", 100, 700);
		g.drawString("x: "+player.getPos().x, 100, 720);
		g.drawString("y: "+player.getPos().y, 100, 740);
		/* end of temporary graphics */
	}
}