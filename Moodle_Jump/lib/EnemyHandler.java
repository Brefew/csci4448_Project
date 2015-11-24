package lib;

public class EnemyHandler {
	private Enemy[] enemies;
	
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
		}
		return ret;
	}
	public void updateEnemies(int[] player_position) {
		
	}
}
