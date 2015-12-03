package lib;

import java.util.Queue;
import java.util.Random;

public class PowerUpHandler {
	private Queue<PowerUp> power_ups;
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
		
		// Generate initial power ups
		for (i=0; i<this.max_number; i++) {
			for (; j<platforms.length; j++) {
				if (platforms[j][1]-this.last_y >= this.initial_power_up_separation && platforms[j][2] == 0) {
					position = new int[] {platforms[j][0], this.power_up_height+platforms[j][1]};
					this.power_ups.add(new PowerUp(position, 0));
					this.last_y = platforms[j][1];
				}
			}
		}
	}
	public int[][] getPowerUps() {
		int[][] ret = new int[this.power_ups.size()][4];
		PowerUp temp_array[] = (PowerUp[]) this.power_ups.toArray();
		for (int i=0; i<temp_array.length; i++) {
			int[] pos = temp_array[i].getPosition();
			ret[i][0] = pos[0];
			ret[i][1] = pos[1];
			ret[i][2] = temp_array[i].getType();
			ret[i][3] = temp_array[i].getStatus();
		}
		return ret;
	}
	public void updatePowerUps(int player_y, int[] indices_of_affected_power_ups, int[][] platforms) {
		int type;
		int i, j = 0;
		int[] position;
		int[] type_list;
		PowerUp[] power_ups = (PowerUp[]) this.power_ups.toArray();
		this.power_ups.clear();
		for (i=0; i<power_ups.length; i++) {
			boolean affected = false;
			for (; j<indices_of_affected_power_ups.length; j++) {
				if (i == indices_of_affected_power_ups[j]) {
					affected = true;
					break;
				}
			}
			power_ups[i].update(affected);
			// Don't add used power ups
			if (power_ups[i].getStatus() != 0) {
				this.power_ups.add(power_ups[i]);
			}
		}
		
		if (this.power_ups.size() >= this.max_number) {
			this.power_ups.remove();
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
			
			for (i=this.power_ups.size(); i<this.max_number; i++) {
				for (; j<platforms.length; j++) {
					if (platforms[j][1]-this.last_y >= this.initial_power_up_separation && platforms[j][2] == 0) {
						position = new int[] {platforms[j][0], this.power_up_height+platforms[j][1]};
						type = type_list[this.generator.nextInt(type_list.length)];
						this.power_ups.add(new PowerUp(position, type));
						this.last_y = platforms[j][1];
					}
				}
			}
		}
	}
}
