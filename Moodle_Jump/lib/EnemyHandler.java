package lib;

import java.util.Queue;
import java.util.Random;

public class EnemyHandler {
	private Queue<Enemy> enemies;
	private int max_number;
	private int last_y = 0;
	//private int enemy_width = 32;
	private int enemy_height = 32;
	private int initial_enemy_separation = 512;
	private int generate_distance = 2056;
	private int difficulty;
	private Random generator;
	
	public EnemyHandler(long seed, int max_enemy_number, int difficulty, int[] platforms) {
		int i;
		// Starting j at one for the nested for loop below because that is the y for the first platform,
		//   and j+=4 to get the y from every platform after that
		int j=1;
		int[] position;
		this.generator = new Random(seed);
		this.max_number = max_enemy_number;
		this.difficulty = difficulty;
		
		// Generate initial enemies
		for (i=0; i<this.max_number; i++) {
			for (; j<platforms.length; j+=4) {
				if (platforms[j]-this.last_y >= this.initial_enemy_separation && platforms[j+1] == 0) {
					position = new int[] {platforms[j-1], this.enemy_height+platforms[j]};
					this.enemies.add(new Enemy(position, 0));
					this.last_y = platforms[j];
				}
			}
		}
	}
	public int[] getEnemies() {
		int[] ret = new int[this.enemies.size()*4];
		Enemy temp_array[] = (Enemy[]) this.enemies.toArray();
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
	public void updateEnemies(int[] player_position, int[] platforms) {
		Enemy[] enemies;
		int type;
		int j = 1;
		int[] position;
		int[] type_list;
		
		if (last_y-player_position[1] <= this.generate_distance) {
			if (this.enemies.size() >= this.max_number) {
				this.enemies.remove();
			}
			// This switch basically checks which multiple of the difficulty modifier the player is at.
			//   If this.difficulty = 1000, then player_y/this.difficulty will be 0 if the player is
			//     below 1000, 1 if the player is below 2000, and so on.  The default just catches anything
			//     more difficult than we want to define individually, so it's the hardest difficulty level.
			switch(player_position[1]/this.difficulty) {
				default: type_list = new int[]{0};
					break;
			}
			
			for (int i=this.enemies.size(); i<this.max_number; i++) {
				for (; j<platforms.length; j+=4) {
					if (platforms[j]-this.last_y >= this.initial_enemy_separation && platforms[j+1] == 0) {
						position = new int[] {platforms[j-1], this.enemy_height+platforms[j]};
						type = type_list[this.generator.nextInt(type_list.length)];
						this.enemies.add(new Enemy(position, type));
						this.last_y = platforms[j];
					}
				}
			}
		}
		
		enemies = (Enemy[]) this.enemies.toArray();
		for (int i=0; i<enemies.length; i++) {
			enemies[i].update(player_position);
		}
	}
}
