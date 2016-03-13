
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;

public class EngineFrame extends JFrame {

	// double buffering
	private Image dbImage;
	private Graphics dbg;
	private Map map;
	private Player player;
	private Scores scores;
	private boolean cheatMode;
	private int currLevel;
	private long startTime;
    private double[] fieldOfView;

	/**
	 * Creates a new EngineFrame to display and render the game.
	 */
	public EngineFrame() {
		super("CPE Quest");

		add(new JPanel() {

			public void paint(Graphics window) {
				// don't edit this
				dbImage = createImage(getWidth(), getHeight());
				dbg = dbImage.getGraphics();
				paintComponent(dbg);
				window.drawImage(dbImage, 0, 0, this);
			}

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
						g2.fillRect(i * scale, j * scale, scale, scale);
					}
				}
				g2.setColor(Color.green);
				double angle = Math.toRadians(360 - player.getDirection() + 90);
				g2.rotate(angle, player.getPos().x * scale, player.getPos().y * scale);
				g2.setColor(Color.red);
				g2.fillPolygon(
						new int[] { (int) (player.getPos().x * scale) - 4, (int) (player.getPos().x * scale),
								(int) (player.getPos().x * scale) + 4 },
						new int[] { (int) (player.getPos().y * scale) + 5, (int) (player.getPos().y * scale) - 5,
								(int) (player.getPos().y * scale) + 5 },
						3);
				g2.rotate(-angle, player.getPos().x * scale, player.getPos().y * scale);
				g2.setColor(Color.cyan);
				g2.fillRect(505, 8, 105, 100);
				g2.setColor(Color.black);
				g2.setFont(new Font("Helvetica", Font.PLAIN, 30));
				g2.drawString("Level " + currLevel, 510, 40);
				long ms = System.currentTimeMillis() - startTime;
				g2.drawString(new DecimalFormat("##.00").format((System.currentTimeMillis() - startTime) / 1000.0), 510,
						80);
				/* end of temporary graphics */
			}
		});

		addKeyListener(new KeyListener() {

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
				/*case KeyEvent.VK_1:
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
					break;*/
				case KeyEvent.VK_Q:
					player.setRotateLeft(true);
					break;
				case KeyEvent.VK_E:
					player.setRotateRight(true);
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
		});

		scores = new Scores();
		init();

		setSize(700, 700);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void init() {
		currLevel = 1;
		updateMap(currLevel);
		cheatMode = false;
		startTime = System.currentTimeMillis();
	}

	public void nextLevel() {
		long totalTime = System.currentTimeMillis() - startTime;
		currLevel++;
		if (currLevel > 4) { // user wins!
			scores.addNewHighScore(scores.calcScore(totalTime), stripNonAlpha((String) JOptionPane.showInputDialog(this,
					"Congrats! You win!\nPlease enter your name.", null, JOptionPane.PLAIN_MESSAGE, null, null, null)));

			// pause for dramatic effect
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// do nothing #yolo
			}

			Object[] options = { "Play again!", "Quit" };
			if (JOptionPane.showOptionDialog(this,
					"HIGH SCORES\n" + scores.getHighScores(3) + "---------\nWould you like to play again or quit?",
					null, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]) == 0) {
				init();
			} else {
				System.exit(1);
			}
		}

		// pause for dramatic effect
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// do nothing #yolo
		}

		// load up the new level
		map = new Map(currLevel);
		player = new Player(map);
	}

	private String stripNonAlpha(String text) {
		char[] chars = text.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (!Character.isAlphabetic(chars[i]) && 
                    !Character.isDigit(chars[i]) && chars[i] != 32) {
				chars[i] = '.';
			}
		}
		return new String(chars).replace(".", "");
	}

    public void setFieldOfView(double[] fieldOfView) {
        this.fieldOfView = Array.copyOf(fieldOfView, fieldOfView.length); 
    }

    public void 

	/**
	 * Returns the current map.
	 * 
	 * @return The current map
	 */
	public Map getMap() {
		return map;
	}

	/**
	 * Returns the player.
	 * 
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	private void updateMap(int level) {
		map = new Map(level);
		player = new Player(map);
	}
}
