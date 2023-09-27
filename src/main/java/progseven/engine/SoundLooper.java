package progseven.engine;

import javax.sound.sampled.*;

/**
 * Plays a sound file repeatedly.
 * The file happens to be Darude Sandstorm.
 * #best #song #ever
 *
 * @author Tim Stoddard
 * @version program07
 */


public class SoundLooper {

    /**
     * Starts a thread and runs the Sandstorm.
     */
    public void loopSound() {

        new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                        Main.class.getResourceAsStream("darude_sandstorm.wav"));
                clip.open(inputStream);
                clip.loop(Integer.MAX_VALUE);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }).start();
    }
}
