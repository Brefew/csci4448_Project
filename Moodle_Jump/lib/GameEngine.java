package lib;

public class GameEngine {
	private int score;
	private int next_highest_score;
	private double gravity;
	private LevelGenerator level_generator;
	private Player player;
	private Display display;
	private InputListener input_listener;
	private DataReader data_reader;

	public static void main(String[] args) {

	}
	private void startGame() {
		int player_height;
		int player_width;
		
		player_height = this.player.getPlayerHeight();
		player_width = this.player.getPlayerWidth();
		while (true) {
			// These next three arrays hold arrays for each individual object with the format:
			//   {x, y, type, status}
			int[][] platforms = this.level_generator.getPlatforms();
			int[][] enemies   = this.level_generator.getEnemies();
			int[][] power_ups = this.level_generator.getPowerUps();
			int[] player_position = this.player.getPosition();
			int player_x = player_position[0];
			int player_y = player_position[1];
			int[][] player_corners = {{player_x, player_y}, {player_x+player_width, player_y},
				                      {player_x, player_y-player_height}, {player_x+player_width, player_y-player_height}};
		}
	}
	private void setGravity() {
		
	}
}
