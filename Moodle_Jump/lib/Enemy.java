package lib;

public class Enemy {
	private double[] velocity;
	private int[] position;
	private int status;
	private int type;
	
	public Enemy(int[] position, int type) {
		this.position = position;
		this.type = type;
		
		switch(type) {
		case 0:
			this.status = 1;
			break;
		}
	}
	
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
	public void update(int[] player_position) {
		
	}
}
