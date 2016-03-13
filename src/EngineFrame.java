
/**
 * Frame to display the engine
 * 
 * @author Tim Stoddard, Sam Lindbloom-Airey
 * @version program007
 */

import java.awt.Graphics;
import java.awt.Graphics2D;
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

   private Image dbImage; // for double buffering
   private Graphics dbg;
   private Map map;
   private Player player;
   private Scores scores;
   private boolean cheatMode;
   private int currLevel;
   private long startTime;

   /**
    * Creates a new EngineFrame to display and render the game.
    */
   public EngineFrame() {
      super("CPE Quest");

      add(new JPanel() {

         public void paint(Graphics window) {

            /* don't edit this */
            dbImage = createImage(getWidth(), getHeight());
            dbg = dbImage.getGraphics();
            paintComponent(dbg);
            window.drawImage(dbImage, 0, 0, this);
            /* end don't edit this */
         }

         public void paintComponent(Graphics g) {
            
            Graphics2D g2 = (Graphics2D) g;

            drawMiniMap(g2, 500, 0);
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

               /* remove this later */
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
         scores.addNewHighScore(scores.calcScore(totalTime),
               stripNonAlpha((String) JOptionPane.showInputDialog(this,
                     "Congrats! You win!\nPlease enter your name.", null,
                     JOptionPane.PLAIN_MESSAGE, null, null, null)));

         // pause for dramatic effect
         try {
            Thread.sleep(500);
         } catch (InterruptedException e) {
            // do nothing #yolo
         }

         Object[] options = { "Play again!", "Quit" };
         if (JOptionPane.showOptionDialog(this,
               "HIGH SCORES\n" + scores.getHighScores(3)
                     + "---------\nWould you like to play again or quit?",
               null, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
               null, options, options[0]) == 0) {
            init();
         } else {
            System.exit(1);
         }
      }

<<<<<<< HEAD
      // pause for dramatic effect
      try {
         Thread.sleep(500);
      } catch (InterruptedException e) {
         // do nothing #yolo
      }
=======
    public void setFieldOfView(double[] fieldOfView) {
        
    }

	/**
	 * Returns the current map.
	 * 
	 * @return The current map
	 */
	public Map getMap() {
		return map;
	}
>>>>>>> origin/master

      // load up the new level
      map = new Map(currLevel);
      player = new Player(map);
   }

<<<<<<< HEAD
   private String stripNonAlpha(String text) {
      char[] chars = text.toCharArray();
      for (int i = 0; i < chars.length; i++) {
         if (!Character.isAlphabetic(chars[i]) && !Character.isDigit(chars[i])
               && chars[i] != 32) {
            chars[i] = '.';
         }
      }
      return new String(chars).replace(".", "");
   }

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
   
   private void drawMiniMap(Graphics2D g2, int dx, int dy) {
      int scale = 10;
      for (int i = 0; i < map.getWidth(); i++) {
         System.out.println("i" + i);
         for (int j = 0; j < map.getWidth(); j++) {
            System.out.print("j" + j + " ");
            int type = map.getTile(i, j).getType();
            if (type == Tile.SPACE) {
               g2.setColor(Color.white);
            } else if (type == Tile.WALL) {
               g2.setColor(Color.black);
            } else if (type == Tile.FINISH) {
               g2.setColor(Color.red);
            } else if (type == Tile.SHOW_PATH) {
               if (!cheatMode) {
                  g2.setColor(Color.white);
               } else {
                  g2.setColor(Color.yellow);
               }
            }
            g2.fillRect(i * scale + dx, j * scale + dy, scale, scale);
         }
      }

      // draw the player
      double angle = Math.PI * 5 / 2 - player.getDirection();
      g2.rotate(angle, player.getPos().x * scale + dx,
            player.getPos().y * scale + dy);
      g2.setColor(Color.red);
      g2.fillPolygon( // replace this with bird's eye raycasting view
            new int[] { (int) (player.getPos().x * scale) - 4 + dx,
                  (int) (player.getPos().x * scale) + dx,
                  (int) (player.getPos().x * scale) + 4 + dx },
            new int[] { (int) (player.getPos().y * scale) + 5 + dy,
                  (int) (player.getPos().y * scale) - 5 + dy,
                  (int) (player.getPos().y * scale) + 5 + dy },
            3);
      g2.rotate(-angle, player.getPos().x * scale + dx,
            player.getPos().y * scale + dy);
      
      // draw stats
      g2.setColor(Color.cyan);
      g2.fillRect(505 + dx, 8 + dy, 105, 100);
      g2.setColor(Color.black);
      g2.setFont(new Font("Helvetica", Font.PLAIN, 30));
      g2.drawString("Level " + currLevel, 510 + dx, 40 + dy);
      g2.drawString(
            new DecimalFormat("##.00").format(
                  (System.currentTimeMillis() - startTime) / 1000.0),
            510 + dx, 80 + dy);
      
      // draw move fast status
      if (player.canMoveFast()) {
         g2.setColor(Color.green);
      } else {
         g2.setColor(Color.red);
      }
      g2.fillRect(510 + dx, 150 + dy, 30, 30);
   }
}
=======
	private void updateMap(int level) {
		map = new Map(level);
		player = new Player(map);
	}
}
>>>>>>> origin/master
