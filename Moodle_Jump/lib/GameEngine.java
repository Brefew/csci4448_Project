package lib;

public class GameEngine {
	private static int display_height = 780;
	private static int display_width  = 1280;
    private static int score;
    private static int next_highest_score;
    private static double gravity;
    private static LevelGenerator level_generator;
    private static Player player;
    private static Display display;
    private static InputListener input_listener;
    private static DataReader data_reader;

    public static void main(String[] args) {
    	input_listener = new InputListener();
    	data_reader = new DataReader();
    	// Can we get display constructor to look like Display(int display_height, int display_width)?
    	//   It'll help having the screen height and width held here so that this game engine can tell
    	//   the level generator what the values are.
    	display = new Display();
        display.start();
    }

	private void startGame() {
		int player_height, player_width, starting_x, starting_y;
		// Height the player will start above the starting platform
		int drop_height = 25;
    	// Seed for pseudo-random number generators in the object handlers
    	long seed = 10;
    	// The maximum number of each type of object that will be allowed at any given time.
    	//   If this limit is reached, the oldest object in its respective handler will be
    	//   destroyed, and a new one will be created and added to the object handler's
    	//   queue.
    	int max_platform_number = 20;
    	int max_power_up_number = 1;
    	int max_enemy_number = 2;
    	// This modifier is the height, in pixels, a player will have to travel in order for
    	//   the difficulty to increase for level creation.
    	int difficulty_modifier = 1000;
    	
    	// Initialized level generator which creates all initial objects
    	level_generator = new LevelGenerator(seed, max_platform_number, max_power_up_number, max_enemy_number, display_height, display_width, difficulty_modifier);
        player = new Player();
		player_height = player.getPlayerHeight();
		player_width = player.getPlayerWidth();
		int[] starting_platform = level_generator.getPlatforms()[0];
		starting_x = starting_platform[0];
		starting_y = starting_platform[1]+player_height+drop_height;
		// This function should only be used here really because it moves the player from
		//   whatever the initial position is to above the lowest of the platforms
		player.setPosition(starting_x, starting_y);
		
		while (true) {
			// These next three arrays hold arrays for each individual object with the format:
			//   {x, y, type, status}
			int[][] platforms = level_generator.getPlatforms();
			int[][] enemies   = level_generator.getEnemies();
			int[][] power_ups = level_generator.getPowerUps();
			int[] player_position = player.getPosition();
			int player_x = player_position[0];
			int player_y = player_position[1];
			int[][] player_corners = {{player_x, player_y}, {player_x+player_width, player_y},
				                      {player_x, player_y-player_height}, {player_x+player_width, player_y-player_height}};
			// Bounding box checks will happen between this comment and the next
			
			// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
			// Updating the level generator, which updates the handlers, will happen below
		}
	}
	private void setGravity() {
		
	}
}
