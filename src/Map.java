import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * The map of the engine class.
 * 
 * @author Sam Lindbloom-Airey, Tim Stoddard
 * @version program07
 */

public class Map {

	private int level;
	private Tile[][] map;

	public Map() {
		this(1);
	}

	public Map(int level) {
		this.level = level;
		read(level);
	}
	
	public Tile[][] getTiles() {
		return map;
	}
	
	public int getLevel() {
		return level;
	}

	public void read() {
		read(level);
	}

	public void read(int level) {
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(new File("").getAbsolutePath() + "/src/level" + level + ".txt"));
			String[] dimensions = in.readLine().split("x");
			map = new Tile[Integer.parseInt(dimensions[0])][Integer.parseInt(dimensions[1])];
			for (int i = 0; i < map.length; i++) {
				String[] data = in.readLine().split("");
				for (int j = 0; j < map[i].length; j++) {
					map[i][j] = new Tile(Integer.parseInt(data[j]));
				}
			}
			in.close();
			for (Tile[] sammy : map) {
				for (Tile sam : sammy) {
					System.out.print(sam.getType() == 0 ? ".." : (sam.getType() == 1 ?"XX" : "!!"));
				}
				System.out.println();
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		} catch (IOException e) {
			System.out.println("IO Error occurred.");
		}
	}
	
	public int distanceToWall(Point position, double angle) {
        return 1;
    }

	private class Tile {

		static final int SPACE = 0, WALL = 1, FINISH = 2;
		int type;

		public Tile(int type) {
			this.type = type;
		}

		public int getType() {
			return type;
		}
	}
}