package lib;

public class EnemyHandler {
	private Enemy[] enemies;
	
<<<<<<< HEAD
	public int[] getEnemies() {
		int[] ret = new int[this.enemies.length*4];
		for (int i=0; i<this.enemies.length; i++) {
			int[] pos = enemies[i].getPosition();
			int type = enemies[i].getType();
			int status = enemies[i].getStatus();
			ret[4*i+0] = pos[0];
			ret[4*i+1] = pos[1];
			ret[4*i+2] = type;
			ret[4*i+3] = status;
=======
	public EnemyHandler(long seed, int max_enemy_number, int difficulty, int[][] platforms) {
		int i;
		int j=0;
		int[] position;
		this.generator = new Random(seed);
		this.max_number = max_enemy_number;
		this.difficulty = difficulty;
		
		// Generate initial enemies
		for (i=0; i<this.max_number; i++) {
			for (; j<platforms.length; j+=4) {
				if (platforms[j][1]-this.last_y >= this.initial_enemy_separation && platforms[j][2] == 0) {
					position = new int[] {platforms[j][0], this.enemy_height+platforms[j][1]};
					this.enemies.add(new Enemy(position, 0));
					this.last_y = platforms[j][1];
				}
			}
		}
	}
	public int[][] getEnemies() {
		int[][] ret = new int[this.enemies.size()][4];
		Enemy temp_array[] = (Enemy[]) this.enemies.toArray();
		for (int i=0; i<temp_array.length; i++) {
			int[] pos = temp_array[i].getPosition();
			int type = temp_array[i].getType();
			int status = temp_array[i].getStatus();
			ret[i][0] = pos[0];
			ret[i][1] = pos[1];
			ret[i][2] = type;
			ret[i][3] = status;
>>>>>>> 486deb4fc4ef7bde4b80217e638b1d9cbd315587
		}
		return ret;
	}
	public void updateEnemies(int[] player_position) {
		
	}
}
