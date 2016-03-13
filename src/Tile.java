public class Tile {

   public static final int SPACE = 0, WALL = 1, FINISH = 2, SHOW_PATH = 3;
   private int type;

   public Tile(int type) {
      this.type = type;
   }

   public int getType() {
      return type;
   }
}