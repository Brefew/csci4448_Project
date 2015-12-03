package lib;

public class PowerUp {
	private double[] position;
	private int type;
	private int status = 0;
	
	public PowerUp(int[] position, int type) {
		this.position = new double[] {(double) position[0], (double)position[1]};
		this.type = type;
		this.status = 0;
	}
	
	public int[] getPosition() {
		return new int[] {(int) this.position[0], (int) this.position[1]};
	}
	public int getType() {
		return this.type;
	}
	public int getStatus() {
		return this.status;
	}
	public void update(boolean jumped_on) {
		if (jumped_on) {
			this.status--;
		}
	}
}
