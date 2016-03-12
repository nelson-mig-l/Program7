/**
 * Frame to display the engine
 * 
 * @author Tim Stoddard
 * @version program007
 */

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

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
	public EngineFrame() {
		super("lmao");
		
		updateMap(4);
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
	 * Returns the player.
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}
	
	private void updateMap(int level) {
		map = new Map(level);
		player = new Player(map);
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
		int scale = 25;
		java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
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
						g2.setColor(Color.white);
					} else {
						g2.setColor(Color.yellow);
					}
				}
				g2.fillRect(i*scale, j*scale, scale, scale);
			}
		}
		g2.setColor(Color.green);
		double angle = Math.toRadians(360-player.getDirection()+90);
		g2.rotate(angle, player.getPos().x*scale, player.getPos().y*scale);
		g2.setColor(Color.red);
		g2.fillPolygon(new int[] {
				(int) (player.getPos().x*scale)-4, (int) (player.getPos().x*scale), (int) (player.getPos().x*scale)+4
		}, new int[] {
				(int) (player.getPos().y*scale)+5, (int) (player.getPos().y*scale)-5, (int) (player.getPos().y*scale)+5
		}, 3);
		g2.rotate(-angle, player.getPos().x*scale, player.getPos().y*scale);
		g2.setColor(Color.black);
		g2.setFont(new Font("Helvetica", Font.PLAIN, 1));
		g2.drawString(player.getDirection()+"", 3, 33);
		g2.drawString("x: "+player.getPos().x, 13, 33);
		g2.drawString("y: "+player.getPos().y, 23, 33);
		/* end of temporary graphics */
	}
}