/**
 * Plays a sound file repeatedly.
 * The file happens to be Darude Sandstorm.
 * oops.
 *
 * @author Tim Stoddard
 * @version program07
 */

import javax.sound.sampled.*;

public class SoundLooper {

   /**
    * Starts a thread and runs the Sandstorm.
    */
   public void loopSound() {

      new Thread(new Runnable() {

         public void run() {
            try {
               Clip clip = AudioSystem.getClip();
               AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                     Main.class.getResourceAsStream("darude_sandstorm.wav"));
               clip.open(inputStream);
               clip.loop(Integer.MAX_VALUE);
            } catch (Exception e) {
               System.err.println(e.getMessage());
            }
         }
      }).start();
   }
}
