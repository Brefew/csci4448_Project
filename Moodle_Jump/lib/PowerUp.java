package lib;

public class PowerUp {
	private int[] position;
	private int type;
	
	public PowerUp(int[] position, int type) {
		this.position = position;
		this.type = type;
	}
	
	public int[] getPosition() {
		return this.position;
	}
	public int getType() {
		return this.type;
	}
	public void update() {
		
	}
}
