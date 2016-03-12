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

	/**
	 * Creates a new map of level 1.
	 */
	public Map() {
		this(1);
	}

	/**
	 * Creates a new map with the specified level.
	 * @param level
	 */
	public Map(int level) {
		this.level = level;
		read(level);
	}
	
	/**
	 * Returns the width of the map.
	 * @return - the width of the map
	 */
	public int getWidth() {
		return map[0].length;
	}
	
	/**
	 * Returns the height of the map.
	 * @return - the height of the map
	 */
	public int getHeight() {
		return map.length;
	}
	
	/**
	 * Returns the tile at the requested row and column on the map.
	 * @return - the tile at the requested row and column on the map
	 */
	public Tile getTile(int row, int col) {
		return map[col][row];
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
			/*
			for (Tile[] sammy : map) {
				for (Tile sam : sammy) {
					System.out.print(
							sam.getType() == 0 ? "  " : // empty space
							(sam.getType() == 1 ?"##" : // wall
							(sam.getType() == 2 ? "!!" : // endpoint
							"^^")));					// solution
				}
				System.out.println();
			}//*/
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		} catch (IOException e) {
			System.out.println("IO Error occurred.");
		}
	}
	
	public int distanceToWall(Point position, double angle) {
        return 1;
    }
}