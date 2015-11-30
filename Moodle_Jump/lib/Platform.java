package lib;

public class Platform {
	private double[] velocity;
	private int height;
	private int[] position;
	private int status;
	private int type;
	private int width;
	
	public Platform(int height, int[] position, int type, int width) {
		this.height = height;
		this.position = position;
		this.type = type;
		this.width = width;
		
		switch(type) {
			// Initial platform with status of -1 to signify infinite health
			case 0: this.status = -1;
				break;
			// Platform with status of 2 means that it can be jumped off of twice before disappearing
			case 1: this.status = 2;
				break;
		}
	}
	public int[] getPosition() {
		return this.position;
	}
	public int getStatus() {
		return this.status;
	}
	private void setStatus(int new_status) {
		this.status = new_status;
	}
	public int getType() {
		return this.type;
	}
	public void update(int[] player_position) {
		
	}
}
