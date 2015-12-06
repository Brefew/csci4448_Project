package lib;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class DataReader {
	private int[] config_data;
	private int[] high_scores;
	private String[] high_score_names;
	
	public DataReader() {
		this.config_data = new int[0];
		this.high_scores = new int[0];
		this.high_score_names = new String[0];
		try {
			this.readFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void close() {
		try {
			this.writeFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public int[] getConfig() {
		return this.config_data;
	}
	public void setConfig(int[] new_conf) {
		this.config_data = new_conf;
	}
	public int[] getScores() {
		return this.high_scores;
	}
	public String[] getNames() {
		return this.high_score_names;
	}
	public void addScore(int new_score, String new_name) {
		int i, j;
		int temp_scores[] = new int[this.high_scores.length+1];
		String temp_names[] = new String[this.high_score_names.length+1];
		
		for (i=0, j=0; i<this.high_scores.length; i++, j++) {
			if (this.high_scores[i] <= new_score) {
				temp_scores[j] = new_score;
				temp_names[j] = new_name;
				j++;
			}
			temp_scores[j] = this.high_scores[i];
			temp_names[j] = this.high_score_names[i];
		}
		temp_scores[i] = new_score;
		temp_names[i] = new_name;
		
		this.high_scores = temp_scores;
		this.high_score_names = temp_names;
	}
	
	private void readFile() throws IOException {
		Path path = FileSystems.getDefault().getPath("data.txt");
		ArrayList<String> file_lines = new ArrayList<String>();
		if (Files.exists(path)) {
			file_lines = (ArrayList<String>) Files.readAllLines(path);
			String file_lines_array[] = new String[file_lines.size()];
			for (int i=0; i<file_lines.size(); i++) {
				if (file_lines_array[i].contains("Scores:")) {
					for (int j=i; j<file_lines.size(); i++, j++) {
						String name;
						int score;
						String line = file_lines_array[j];
						int divider = line.indexOf(":");
						// Check if ":" wasn't found or if it is the last character in the line
						if (divider == -1 || divider == line.length()-1) {
							break;
						}
						
						try {
							name = line.substring(0, divider);
							score = Integer.parseInt(line.substring(divider+1));
							this.addScore(score, name);
						} catch(NumberFormatException e) {
							// Score portion of the line couldn't be read as an integer, so ignore
						}
					}
				}
			}
		} else {
			String line = "Scores:";
			Files.write(path, line.getBytes());
		}
	}
	private void writeFile() throws IOException {
		Path path = FileSystems.getDefault().getPath("data.txt");
		String line = "Scores:\n";
		for (int i=0; i<this.high_scores.length; i++) {
			line += this.high_score_names[i]+":"+Integer.toString(this.high_scores[i])+"\n";
		}
		Files.write(path, line.getBytes());
	}
}
