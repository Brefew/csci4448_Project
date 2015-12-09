package lib;

import java.util.Random;

public class EnemyHandler {
	private Enemy[] enemies;
	private int max_number;
	private int last_y = 0;
	private int enemy_width = 32;
	private int enemy_height = 32;
	private int initial_enemy_separation = 1024;
	private int generate_distance = 2056;
	private int difficulty;
	private Random generator;
	
	public EnemyHandler(long seed, int max_enemy_number, int difficulty, int[][] platforms) {
		int i;
		int j=0;
		int[] position;
		this.generator = new Random(seed);
		this.max_number = max_enemy_number;
		this.difficulty = difficulty;
		this.enemies = new Enemy[this.max_number];
		
		// Generate initial enemies
		for (i=0; i<this.max_number; i++) {
			for (; j<platforms.length; j++) {
				if (platforms[j][1]-this.last_y >= this.initial_enemy_separation && platforms[j][2] == 0) {
					position = new int[] {platforms[j][0], this.enemy_height+platforms[j][1]};
					this.enemies[i] = new Enemy(position, 0, 1, enemy_height, enemy_width);
					this.last_y = platforms[j][1];
				}
			}
		}
	}
	public int[][] getEnemies() {
		int[][] ret = new int[this.enemies.length][4];
		for (int i=0; i<this.enemies.length; i++) {
			if (this.enemies[i] == null) {
				continue;
			}
			int[] pos = this.enemies[i].getPosition();
			int type = this.enemies[i].getType();
			int status = this.enemies[i].getStatus();
			ret[i][0] = pos[0];
			ret[i][1] = pos[1];
			ret[i][2] = type;
			ret[i][3] = status;
		}
		return ret;
	}
	public void updateEnemies(int player_y, int[] indices_of_affected_enemies, int[][] platforms) {
		int i, j=0, h=0, type, status=0;
		int[] position, type_list;
		Enemy[] temp_enemies = this.enemies;
		for (i=0; i<temp_enemies.length; i++) {
			boolean affected = false;
			for (; j<indices_of_affected_enemies.length; j++) {
				if (i == indices_of_affected_enemies[j]) {
					affected = true;
					break;
				}
			}
			if (temp_enemies[i] == null) {
				continue;
			}
			temp_enemies[i].update(affected);
			// Don't add destroyed enemies
			if (enemies[i].getStatus() >= 0) {
				this.enemies[h] = temp_enemies[i];
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
			j=0;
			// Generate new enemies
			for (i=h; i<this.max_number; i++) {
				for (; j<platforms.length; j++) {
					if (platforms[j][1]-this.last_y >= this.initial_enemy_separation && platforms[j][2] == 0) {
						type = type_list[this.generator.nextInt(type_list.length)];
						switch(type) {
							case 0: status = 1;
								break;
							case 1: status = 2;
						}
						position = new int[] {platforms[j][0], this.enemy_height+platforms[j][1]};
						this.enemies[i] = new Enemy(position, type, status, enemy_height, enemy_width);
						this.last_y = platforms[j][1];
					}
				}
			}
		}
	}
	public int getEnemyHeight() {
		return this.enemy_height;
	}
	public int getEnemyWidth() {
		return this.enemy_width;
	}
}
