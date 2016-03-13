import javax.sound.sampled.*;

public class SoundLooper {

   public void loopSound() {
      
      new Thread(new Runnable() {
         
         public void run() {
            try {
               Clip clip = AudioSystem.getClip();
               AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                     Main.class.getResourceAsStream("darude_sandstorm.wav"));
               System.out.println("hi");
               clip.open(inputStream);
               clip.loop(Integer.MAX_VALUE);
            } catch (Exception e) {
               System.err.println(e.getMessage());
            }
         }
      }).start();
   }
}