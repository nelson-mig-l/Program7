/**
 * Frame to display the engine.
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
import java.text.DecimalFormat;

import java.util.Arrays;

public class EngineFrame extends JFrame {

   // double buffering
   private Image dbImage;
   private Graphics dbg;
   private Map map;
   private Player player;
   private Keyboard keyboard;
   private Mouse mouse;
   private Scores scores;
   private int currLevel;
   private long startTime;
   private double[] fieldOfVision;

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
            if (fieldOfVision != null) {
               draw3D(g2);
            }
            drawMiniMap(g2, 520, 0);
         }
      });

      scores = new Scores();
      init();

      setSize(865, 480);
      setVisible(true);
      setResizable(false);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }

   private void draw3D(Graphics2D g2) {
      int dx = 10, dy = 180;
      for (int i = 0; i < fieldOfVision.length; i++) {
         fieldOfVision[i] = fieldOfVision[i] == -1 ? 20 : fieldOfVision[i];
         int colorVal = (int) (2.0
               * (fieldOfVision[i] > 100 ? 100 : fieldOfVision[i]));
         g2.setColor(new Color(colorVal, colorVal, colorVal));
         g2.fillRect(i * Engine.WIDTH + dx, (int) fieldOfVision[i] / 2 + dy,
               Engine.WIDTH, (int) fieldOfVision[i]);
         g2.fillRect(i * Engine.WIDTH + dx, (int) fieldOfVision[i] / -2 + dy,
               Engine.WIDTH, (int) fieldOfVision[i]);
      }
   }

   private void drawMiniMap(Graphics2D g2, int dx, int dy) {
      int scale = 10;
      for (int i = 0; i < map.getHeight(); i++) {
         for (int j = 0; j < map.getWidth(); j++) {
            g2.setColor(map.getTile(i, j).getColor(player.getCheatMode()));
            g2.fillRect(i * scale + dx, j * scale + dy, scale, scale);
         }
      }

      // draw the player
      double angle = player.getDirection();
      g2.rotate(angle, player.getPos().x * scale + dx,
            player.getPos().y * scale + dy);
      g2.setColor(Color.red);
      g2.fillPolygon(
            new int[] { (int) (player.getPos().x * scale) + dx,
                  (int) (player.getPos().x * scale) + 10 + dx,
                  (int) (player.getPos().x * scale) + dx },
            new int[] { (int) (player.getPos().y * scale) + 3 + dy,
                  (int) (player.getPos().y * scale) + dy,
                  (int) (player.getPos().y * scale) - 3 + dy },
            3);
      g2.rotate(-angle, player.getPos().x * scale + dx,
            player.getPos().y * scale + dy);

      // draw stats
      dy += scale * map.getHeight();
      g2.setColor(Color.cyan);
      g2.fillRect(dx, dy, 330, 62);
      g2.setColor(Color.black);
      g2.setFont(new Font("Helvetica", Font.PLAIN, 30));
      g2.drawString("Level " + currLevel, 10 + dx, 40 + dy);
      g2.drawString(
            "Time: " + new DecimalFormat("##.00")
                  .format((System.currentTimeMillis() - startTime) / 1000.0),
            145 + dx, 40 + dy);

      // draw move fast status
      g2.setFont(new Font("Helvetica", Font.PLAIN, 18));
      g2.drawString("Speed boost status:", 20 + dx, 80 + dy);
      if (player.canMoveFast()) {
         g2.setColor(Color.green);
      } else {
         g2.setColor(Color.red);
      }
      g2.fillRect(185 + dx, 65 + dy, 30, 18);
   }

   /**
    * Starts the game at level1.
    */
   public void init() {
      currLevel = 1;
      updateMap(currLevel);
      startTime = System.currentTimeMillis();
   }

   /**
    * Move to next level/map and record scores if you win.
    */
   public void nextLevel() {
      long totalTime = System.currentTimeMillis() - startTime;
      currLevel++;
      if (1 <= currLevel && currLevel <= 4) {
         updateMap(currLevel);
      }
      if (currLevel > 4) { // user wins!
         String name = (String) JOptionPane.showInputDialog(this,
               "Congrats! You win!\nPlease enter your name "
                     + "(letters/numbers only).\nA max of 20 "
                     + "characters will be saved.",
               null, JOptionPane.PLAIN_MESSAGE, null, null, null);
         scores.addNewHighScore(scores.calcScore(totalTime),
               name == null ? "null" : stripNonAlpha(name));

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
   }

   /**
    * Setter method for the field of vision, set by Engine.
    *
    * @param fieldOfVision a double array of rectangle heights that
    * has length equal to number of rays cast by the engine.
    */
   public void setFieldOfVision(double[] fieldOfVision) {
      this.fieldOfVision = Arrays.copyOf(fieldOfVision, fieldOfVision.length);
   }

   private String stripNonAlpha(String text) {
      char[] chars = text.toCharArray();
      for (int i = 0; i < chars.length; i++) {
         if (!Character.isAlphabetic(chars[i]) && !Character.isDigit(chars[i])
               && chars[i] != 32) {
            chars[i] = '.';
         }
      }
      String temp = new String(chars).replace(".", "");
      return temp.length() > 20 ? temp.substring(0, 20) : temp;
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
      keyboard = new Keyboard(player);
      mouse = new Mouse(player);
      addKeyListener(keyboard);
      addMouseMotionListener(mouse);
   }
}
