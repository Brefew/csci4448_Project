package lib;

public class Enemy {
	private double[] position;
	private double[] velocity;
	private int type;
	private int status;
	private int height;
	private int width;
	
	public Enemy(int[] position, int type, int status, int height, int width) {
		this.position = new double[] {(double) position[0], (double) position[1]};
		this.velocity = new double[] {0.0, 0.0};
		this.type = type;
		this.status = status;
		this.height = height;
		this.width = width;
	}
	public int[] getPosition() {
		return new int[] {(int) this.position[0], (int) this.position[1]};
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
	public void update(boolean jumped_on) {
		if (jumped_on) {
			this.status--;
		} else {
			this.position[0] += this.velocity[0];
			this.position[1] += this.velocity[1];
		}
	}
}
