package lib;

public class Enemy {
	private int[] position;
	private double[] velocity;
	private int type;
	private int status;
	
	public int[] getPosition() {
		return this.position;
	}
	public int getStatus() {
		return this.status;
	}
	public void setStatus(int new_status) {
		this.status = new_status;
	}
	public int getType() {
		return this.type;
	}
	public void update(int player_position) {
		
	}
}
