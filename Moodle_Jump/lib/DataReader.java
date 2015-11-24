package lib;

public class DataReader {
	private int[] config_data;
	private int[] high_scores;
	private String[] high_score_names;
	
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
		
	}
	
	private void readFile() {
		
	}
	private void writeFile() {
		
	}
}
