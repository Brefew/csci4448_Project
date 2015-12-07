package lib;

import java.util.Vector;

public class GameEngine {
	private static final int display_height = 780;
	private static final int display_width  = 1280;
    private static int score;
    private static int next_highest_score;
    private static final double gravity = -3.0;
    private static LevelGenerator level_generator;
    private static Player player;
    private static Display display;
    private static InputListener input_listener;
    private static DataReader data_reader;

    public static void main(String[] args) {
    	input_listener = new InputListener();
    	data_reader = new DataReader();
    	display = new Display(display_height, display_width);
    	
    	input_listener.attach(display);
        display.start();
        // Menu will be created here as a member variable of GameEngine if we have enough time to implement it
        startGame();
    }

	private static void startGame() {
		int player_height, player_width, starting_x, starting_y, last_y;
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
    	// Modifier for player.updatePlayer() function which can be changed by a power up
    	double gravity_modifier = 1.0;
    	// Initialized level generator which creates all initial objects
    	level_generator = new LevelGenerator(seed, max_platform_number, max_power_up_number, max_enemy_number, display_height, display_width, difficulty_modifier);
        player = new Player(gravity);
        
		player_height = player.getPlayerHeight();
		player_width = player.getPlayerWidth();
		int[] starting_platform = level_generator.getPlatforms()[0];
		starting_x = starting_platform[0];
		starting_y = starting_platform[1]+player_height+drop_height;
		last_y = starting_y;
		// This function should only be used here really because it moves the player from
		//   whatever the initial position is to above the lowest of the platforms
		player.setPosition(starting_x, starting_y);

        input_listener.attach(player);
		// Flag for when the player does something that would kill itself
		boolean dead = false;
		score = 0;
		while (!dead) {
			// These next three arrays hold arrays for each individual object with the format:
			//   {x, y, type, status}
			int[][] platforms = level_generator.getPlatforms();
			int[][] enemies   = level_generator.getEnemies();
			int[][] power_ups = level_generator.getPowerUps();
			int[] player_position = player.getPosition();
			int player_x = player_position[0];
			int player_y = player_position[1];
			if (player_y > score) {
				score = player_y;
			}
			next_highest_score = findNextHighestScore();
			// Using the above data, update the display
			display.updateGame(score, next_highest_score, player_position, platforms, enemies, power_ups);
			
			// After updating the display, update game data for next iteration
			// Corners for the player
			int[][] player_corners = {{player_x,               player_y}, {player_x+player_width,               player_y},
				                      {player_x, player_y-player_height}, {player_x+player_width, player_y-player_height}};
			// Array holding height and width arrays in the order platforms, power ups, and enemies
			int[][] object_heights_and_widths = level_generator.getHeightsAndWidths();
			int[] platform_height_and_width = object_heights_and_widths[0];
			int[] power_up_height_and_width = object_heights_and_widths[1];
			int[] enemy_height_and_width    = object_heights_and_widths[2];
			
			// These three function calls return arrays with the index/indices of collided objects
			int[] c_p_indices   = getCollisions(player_corners, power_up_height_and_width, power_ups);
			int[] c_e_indices   = getCollisions(player_corners,    enemy_height_and_width,   enemies);
			int[] c_p_u_indices = getCollisions(player_corners, platform_height_and_width, platforms);

			// Check if the player is traveling upwards or not at all
			if (player_y-last_y >= 0) {
				// Platform collisions don't matter if player is moving upwards, so empty that array
				c_p_indices = new int[0];
			} else {
				for (int i=0; i<c_p_indices.length; i++) {
					int platform_max_y = platforms[c_p_indices[i]][1];
					// If the player hit the platform from above, the player jumps, and if the player
					//   has hit a platform like this, the player will jump regardless of the other
					//   collided platforms, if any, so we can break out of the loop
					if (last_y-player.getPlayerHeight() > platform_max_y) {
						player.jump();
						break;
					}
				}
			}
			// Check if player is dead
			for (int i=0; i<c_e_indices.length; i++) {
				// Find the top of the enemy
				int enemy_max_y = enemies[c_e_indices[i]][1];
				// If the player hit the enemy from above, the player jumps instead of dies
				if ((last_y-player_y > 0) && last_y-player.getPlayerHeight() > enemy_max_y) {
					player.jump();
				// Any other case means that the player is dead
				} else {
					dead = true;
					display.updateGame(score, next_highest_score, player_position, platforms, enemies, power_ups);
					break;
				}
			}
			
			if (dead) {
				display.gameOver();
			} else {
				// Updating the level generator, which updates the handlers
				level_generator.update(player_y, c_p_indices, c_e_indices, c_p_u_indices);
				// Update the player
				player.updatePlayer(gravity*gravity_modifier);
				// Update last_y variable for the player
				last_y = player_y;
			}
		}
		
		data_reader.close();
	}
	private static int[] getCollisions(int[][] objects, int[] object_height_and_width, int[][] player_corners) {
		int[] ret;
		Vector<Integer> collided_objects = new Vector<Integer>(0, 1);
		int height = object_height_and_width[0];
		int width  = object_height_and_width[1];
		for (int i=0; i<objects.length; i++) {
			int x = objects[i][0];
			int y = objects[i][1];
			int[][] object_corners = {{x,        y}, {x+width,        y},
					                    {x, y-height}, {x+width, y-height}};
			boolean collision = checkForCollision(player_corners, object_corners);
			
			if (collision) {
				collided_objects.add(i);
			}
		}
		
		// After half an hour, I have determined that Java is pretty gross since the only way to create
		//   a Vector of ints is to create my own Vector class, and the only other option with a
		//   Vector is to make it a Vector of Integers, and the only possible way to get an int[] from
		//   a Vector<Integer> is to step through the Vector one element at a time and add it to a separate array...
		ret = new int[collided_objects.size()];
		for (int i=0; i<collided_objects.size(); i++) {
			ret[i] = collided_objects.elementAt(i).intValue();
		}

		return ret;
	}
	private static boolean checkForCollision(int[][] player_corners, int[][] object_corners) {
		boolean player_x_within = false;
		boolean player_y_within = false;
		boolean collision = false;
		int player_min_x = player_corners[0][0];
		int player_max_x = player_corners[3][0];
		int player_min_y = player_corners[3][1];
		int player_max_y = player_corners[0][1];
		
		int object_min_x = object_corners[0][0];
		int object_max_x = object_corners[3][0];
		int object_min_y = object_corners[3][1];
		int object_max_y = object_corners[0][1];
		
		// True if either the left or right side of the player could be within the object
		if ((player_min_x >= object_min_x && player_min_x <= object_max_x) ||
			(player_max_x >= object_min_x && player_max_x <= object_max_x)) {
			player_x_within = true;
		}
		// True if either the top or bottom of the player could be within the object
		if ((player_min_y >= object_min_y && player_min_y <= object_max_y) ||
			(player_max_y >= object_min_y && player_max_y <= object_max_y)) {
			player_y_within = true;
		}
		if (player_x_within && player_y_within) {
			collision = true;
		}
		return collision;
	}
	private static int findNextHighestScore() {
		int all_scores[];
		int next_score = score;
		
		all_scores = data_reader.getScores();
		// Iterate backwards through the high scores because we are looking for the first lowest score
		//   that is higher than the current score. If the current score is the highest, then
		//   next_highest_score will just stay as the current score
		for (int i=all_scores.length-1; i>=0; i--) {
			if (all_scores[i] > next_score) {
				next_score = all_scores[i];
				break;
			}
		}
		
		return next_score;
	}
}
