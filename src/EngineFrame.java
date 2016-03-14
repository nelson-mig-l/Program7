
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
import java.text.DecimalFormat;

import java.util.Arrays;

public class EngineFrame extends JFrame {

    // double buffering
    private Image dbImage;
    private Graphics dbg;
    private Map map;
    private Player player;
    private Scores scores;
    public boolean cheatMode;
    private int currLevel;
    private long startTime;
    private double[] fieldOfVision;
    private double[][] rays;

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
                draw3D(g2);
                drawDebug(g2);
            }
        });

        scores = new Scores();
        init();

        addKeyListener(new Keyboard(player));

        setSize(700, 700);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void drawDebug(Graphics2D g2) {
        g2.drawString("speed: " + player.speed, 20, 20);
        g2.drawString("rotating: " + player.rotating, 20, 70);
        g2.drawString("sideSpeed: " + player.sideSpeed, 20, 120);
    }

    private void draw3D(Graphics2D g2) {
        int scale = 10, dx = 500, dy = 0;
        g2.setColor(Color.black);
        for (int i = 0; i < fieldOfVision.length; i++) {
            fieldOfVision[i] = fieldOfVision[i] == -1 ? 20 : fieldOfVision[i];
            g2.setColor(Color.black);
            g2.fillRect(i, (int) fieldOfVision[i] / 2, 1, 
                    (int) fieldOfVision[i]);
            g2.setColor(Color.red);
            g2.drawLine((int) (player.getPos().x * scale + dx),
                    (int) (player.getPos().y * scale),
                    (int) (player.getPos().x
                        + Math.cos(player.getDirection()) * fieldOfVision[i] 
                        * scale + dx),
                    (int) (player.getPos().y + Math.sin(player.getDirection())
                        * fieldOfVision[i] * scale));
            g2.setColor(Color.green);
            g2.drawLine((int) (player.getPos().x * scale + dx),
                    (int) (player.getPos().y * scale + dy),
                    (int) (10 * Math.cos(player.getDirection()) * scale + dx),
                    (int) (10 * Math.sin(player.getDirection()) * scale + dy));

            //printDraw3DStuff(i);
        }
    }

    private void printDraw3DStuff(int i) {
        System.out.println((int) player.getPos().x + "  "
                + (int) player.getPos().y + "  "
                + (int) (player.getPos().x
                    + Math.cos(player.getDirection()) * fieldOfVision[i])
                + "  " + (int) (player.getPos().y
                    + Math.sin(player.getDirection()) * fieldOfVision[i]));
    }

    private void drawMiniMap(Graphics2D g2, int dx, int dy) {
        int scale = 10;
        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getWidth(); j++) {
                g2.setColor(map.getTile(i, j).getColor(cheatMode));
                g2.fillRect(i * scale + dx, j * scale + dy, scale, scale);
            }
        }

        // draw the player
        double angle = player.getDirection();
        g2.rotate(angle, player.getPos().x * scale + dx,
                player.getPos().y * scale + dy);
        g2.setColor(Color.red);
        g2.fillPolygon(new int[] { (int) (player.getPos().x * scale) - 4 + dx,
            (int) (player.getPos().x * scale) + dx,
            (int) (player.getPos().x * scale) + 4 + dx },
            new int[] { (int) (player.getPos().y * scale) + 5 + dy,
            (int) (player.getPos().y * scale) - 5 + dy,
            (int) (player.getPos().y * scale) + 5 + dy }, 3);
        g2.rotate(-angle, player.getPos().x * scale + dx,
                player.getPos().y * scale + dy);

        // draw stats
        dy += 200;
        g2.setColor(Color.cyan);
        g2.fillRect(5 + dx, dy, 105, 100);
        g2.setColor(Color.black);
        g2.setFont(new Font("Helvetica", Font.PLAIN, 30));
        g2.drawString("Level " + currLevel, 10 + dx, 40 + dy);
        g2.drawString(
                new DecimalFormat("##.00")
                .format((System.currentTimeMillis() - startTime) / 1000.0),
                10 + dx, 80 + dy);

        // draw move fast status
        if (player.canMoveFast()) {
            g2.setColor(Color.green);
        } else {
            g2.setColor(Color.red);
        }
        g2.fillRect(10 + dx, 150 + dy, 30, 30);
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

    public void setFieldOfVision(double[] fieldOfVision) {
        this.fieldOfVision = Arrays.copyOf(fieldOfVision, fieldOfVision.length);
    }

    public void setRays(double[][] rays) {
        this.rays = Arrays.copyOf(rays, rays.length);
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
    }
}
