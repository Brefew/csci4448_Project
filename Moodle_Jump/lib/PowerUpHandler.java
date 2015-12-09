package lib;

import java.util.Random;

public class PowerUpHandler {
	private PowerUp[] power_ups;
	private int max_number;
	private int last_y = 0;
	private int power_up_width = 32;
	private int power_up_height = 32;
	private int initial_power_up_separation = 1024;
	private int generate_distance = 2056;
	private int difficulty;
	private Random generator;
	
	public PowerUpHandler(long seed, int max_power_up_number, int difficulty, int[][] platforms) {
		int i, j=0;
		int[] position;
		this.generator = new Random(seed);
		this.max_number = max_power_up_number;
		this.difficulty = difficulty;
		this.power_ups = new PowerUp[this.max_number];
		
		// Generate initial power ups
		for (i=0; i<this.max_number; i++) {
			for (; j<platforms.length; j++) {
				if (platforms[j][1]-this.last_y >= this.initial_power_up_separation && platforms[j][2] == 0) {
					position = new int[] {platforms[j][0], this.power_up_height+platforms[j][1]};
					this.power_ups[i] = new PowerUp(position, 0);
					this.last_y = platforms[j][1];
				}
			}
		}
	}
	public int[][] getPowerUps() {
		int[][] ret = new int[this.power_ups.length][4];
		for (int i=0; i<this.power_ups.length; i++) {
			if (this.power_ups[i] == null) {
				continue;
			}
			int[] pos = this.power_ups[i].getPosition();
			ret[i][0] = pos[0];
			ret[i][1] = pos[1];
			ret[i][2] = this.power_ups[i].getType();
			ret[i][3] = this.power_ups[i].getStatus();
		}
		return ret;
	}
	public void updatePowerUps(int player_y, int[] indices_of_affected_power_ups, int[][] platforms) {
		int type;
		int i, j = 0, h=0;
		int[] position;
		int[] type_list;
		PowerUp[] temp_power_ups = this.power_ups;
		for (i=0; i<temp_power_ups.length; i++) {
			boolean affected = false;
			for (; j<indices_of_affected_power_ups.length; j++) {
				if (i == indices_of_affected_power_ups[j]) {
					affected = true;
					break;
				}
			}
			if (temp_power_ups[i] == null) {
				continue;
			}
			temp_power_ups[i].update(affected);
			// Don't add used power ups
			if (temp_power_ups[i].getStatus() > 0) {
				this.power_ups[h] = power_ups[i];
				h++;
			}
		}

		if (last_y-player_y <= this.generate_distance) {
			// This switch basically checks which multiple of the difficulty modifier the player is at.
			//   If this.difficulty = 1000, then player_y/this.difficulty will be 0 if the player is
			//     below 1000, 1 if the player is below 2000, and so on.  The default just catches anything
			//     more difficult than we want to define individually, so it's the hardest difficulty level.
			switch(player_y/this.difficulty) {
				case 0: type_list = new int[]{0};
					break;
				default: type_list = new int[]{0, 1};
					break;
			}
			
			for (i=h; i<this.max_number; i++) {
				for (; j<platforms.length; j++) {
					if (platforms[j][1]-this.last_y >= this.initial_power_up_separation && platforms[j][2] == 0) {
						position = new int[] {platforms[j][0], this.power_up_height+platforms[j][1]};
						type = type_list[this.generator.nextInt(type_list.length)];
						this.power_ups[i] = new PowerUp(position, type);
						this.last_y = platforms[j][1];
					}
				}
			}
		}
	}
	public int getPowerUpHeight() {
		return this.power_up_height;
	}
	public int getPowerUpWidth() {
		return this.power_up_width;
	}
}
