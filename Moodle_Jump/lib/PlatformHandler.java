package lib;

import java.util.Queue;
import java.util.Random;

public class PlatformHandler {
	private Queue<Platform> platforms;
	private int last_x, last_y, max_number, max_width;
	private int platform_width = 32;
	private int platform_height = 8;
	private int initial_platform_separation = 256;
	private int generate_distance = 2056;
	private int difficulty;
	private Random generator;
	
	public PlatformHandler(long seed, int max_platform_number, int max_display_width, int difficulty) {
		this.last_x = 0;
		this.last_y = 0;
		this.max_number = max_platform_number;
		this.max_width = max_display_width;
		this.generator = new Random(seed);
		this.difficulty = difficulty;

		// Generate initial platforms
		for (int i=0; i<this.max_number; i++) {
			int x, y;
			int[] position;
			x = this.generator.nextInt(this.max_width-this.platform_width);
			if (this.last_x == 0 && this.last_y == 0) {
				y = this.platform_height;
			} else if (i%2 == 1) {
				// Ensures no overlapping platforms
				x = this.checkOverlap(x);
				y = this.last_y;
			} else {
				y = this.last_y + this.initial_platform_separation;
			}

			position = new int[]{x, y};
			this.platforms.add(new Platform(this.platform_height, position, 0, this.platform_width));
			
			this.last_x = x;
			this.last_y = y;
		}
	}
	
	public int[] getPlatforms() {
		int[] ret = new int[this.platforms.size()*4];
		Platform temp_array[] = (Platform[]) this.platforms.toArray();
		for (int i=0; i<temp_array.length; i++) {
			int[] pos = temp_array[i].getPosition();
			int type = temp_array[i].getType();
			int status = temp_array[i].getStatus();
			ret[4*i+0] = pos[0];
			ret[4*i+1] = pos[1];
			ret[4*i+2] = type;
			ret[4*i+3] = status;
		}
		return ret;
	}
	public void updatePlatforms(int[] player_position) {
		Platform[] platforms;
		int x, y, type;
		int[] position;
		int[] type_list;
		
		if (this.last_y-player_position[1] <= this.generate_distance) {
			if (this.platforms.size() >= this.max_number) {
				this.platforms.remove();
			}
			// This switch basically checks which multiple of the difficulty modifier the player is at.
			//   If this.difficulty = 1000, then player_y/this.difficulty will be 0 if the player is
			//     below 1000, 1 if the player is below 2000, and so on.  The default just catches anything
			//     more difficult than we want to define individually, so it's the hardest difficulty level.
			switch(player_position[1]/this.difficulty) {
				case 0: type_list = new int[]{0};
					break;
				case 1: type_list = new int[]{0, 1};
					break;
				default: type_list = new int[]{1, 2};
					break;
			}
			x = this.generator.nextInt(this.max_width-this.platform_width);
			if (this.generator.nextBoolean()) {
				x = this.checkOverlap(x);
				y = this.last_y;
			} else {
				y = this.last_y+this.initial_platform_separation;
			}
			
			position = new int[] {x, y};
			type = type_list[this.generator.nextInt(type_list.length)];
			this.platforms.add(new Platform(this.platform_height, position, type, this.platform_width));
		}
		
		platforms = (Platform[]) this.platforms.toArray();
		for (int i=0; i<platforms.length; i++) {
			platforms[i].update(player_position);
		}
	}
	
	private int checkOverlap(int x) {
		// If statement true if platforms would overlap
		if (Math.abs(x - this.last_x) < this.platform_width) {
			if (this.last_x+this.platform_width < (this.max_width-this.platform_width)) {
				x = this.last_x+this.platform_width;
			} else {
				x = this.last_x-this.platform_width;
			}
		}
		return x;
	}
}
