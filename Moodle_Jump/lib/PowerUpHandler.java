package lib;

public class PowerUpHandler {
	private PowerUp[] power_ups;
	
	public int[] getPowerUps() {
		int[] ret = new int[this.power_ups.length*2];
		for (int i=0; i<this.power_ups.length; i++) {
			int[] pos = this.power_ups[i].getPosition();
			ret[2*i+0] = pos[0];
			ret[2*i+1] = pos[1];
		}
		return ret;
	}
	public void updatePowerUps(int[] player_position) {
		
	}
}
