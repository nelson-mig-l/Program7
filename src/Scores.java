import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Scores {

	private ArrayList<Score> scores;

	/**
	 * Constructs an object in which to hold all the scores.
	 */
	public Scores() {
		scores = new ArrayList<Score>();
		read();
	}
	
	/**
	 * Gets the top 5 high scores, or all high scores if there are less than 5.
	 * @return the top 5 high scores
	 */
	public Score[] getHighScores() {
		return this.getHighScores(5);
	}
	
	/**
	 * Gets the specified number of high scores, or all high scores if there are less than the specified number.
	 * @return the specified number of top high scores
	 */
	public Score[] getHighScores(int howMany) {
		Score[] temp = new Score[Math.min(5, scores.size())];
		for (int i = 0; i < temp.length; i++) {
			temp[i] = scores.get(i);
		}
		return temp;
	}
	
	/**
	 * Calculates a score based on the level and time parameters.
	 * @param level - The current level
	 * @param ms - The time to complete the level, in milliseconds
	 * @return The score of the level
	 */
	public int calcScore(int level, long ms) {
		double coefficient =
				level == 1 ? 1337.0 :
				(level == 2 ? 2016.0 :
				(level == 3 ? 4242.42 :
				(level == 4 ? 6969.69 : 1)));
		return Math.max((int) (coefficient / (ms / 1000.0 + (1.0 / Integer.MAX_VALUE))), 0);
	}

	/**
	 * Adds a new high score to the list. The score must be between
	 * 
	 * @param newScore
	 *            - score that was earned.
	 * @param name
	 *            - name associated with the score
	 */
	public void addNewHighScore(int newScore, String name) {
		scores.add(new Score(newScore, name));

		// selection sort
		for (int i = 0; i < scores.size(); i++) {
			int maxPos = i;
			for (int j = i + 1; j < scores.size(); j++) {
				if (scores.get(j).compareTo(scores.get(maxPos)) < 0) {
					maxPos = j;
				}
			}
			Score temp = scores.get(i);
			scores.set(i, scores.get(maxPos));
			scores.set(maxPos, temp);
		}

		// write to file
		BufferedWriter out;
		try {
			out = new BufferedWriter(new FileWriter(new File("").getAbsolutePath() + "/src/high_scores.txt"));
			for (Score score : scores) {
				out.write(score.score + "," + score.name + "\n");
			}
			out.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		} catch (IOException e) {
			System.out.println("IO Error occurred.");
		}
	}

	/**
	 * Reads the score data from the text file.
	 */
	private void read() {
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(new File("").getAbsolutePath() + "/src/high_scores.txt"));
			ArrayList<String> data = new ArrayList<String>();
			String text;
			while ((text = in.readLine()) != null) {
				data.add(text);
			}
			in.close();
			for (int i = 0; i < data.size(); i++) {
				String[] tempData = data.get(i).split(",");
				scores.add(new Score(Integer.parseInt(tempData[0]), tempData[1]));
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		} catch (IOException e) {
			System.out.println("IO Error occurred.");
		} catch (java.lang.NumberFormatException e) {
			System.out.println("Score was too large.");
		}
	}

	/**
	 * Represents a score object. Has a score and a name associated with that
	 * score.
	 * 
	 * @author timstoddard
	 *
	 */
	private class Score implements Comparable<Score> {

		int score;
		String name;

		/**
		 * Creates a new Score.
		 * 
		 * @param score
		 * @param name
		 */
		public Score(int score, String name) {
			this.score = score;
			this.name = name;
		}

		/**
		 * Compares this score to another. If scores are different, the larger
		 * one comes first; if they are the same, the one whose name
		 * lexicographically precedes the other comes first.
		 */
		@Override
		public int compareTo(Score o) {
			if (o == null) {
				return -1;
			}
			if (o.getClass() != this.getClass()) {
				return -1;
			}
			if (this.score > ((Score) o).score) {
				return -1;
			} else if (this.score > ((Score) o).score) {
				return 1;
			}
			return this.name.compareTo(((Score) o).name);
		}
	}
}