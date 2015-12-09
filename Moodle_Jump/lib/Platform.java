package lib;

public class Platform {
	private double[] position;
	private double[] velocity;
	private int status;
	private int type;
	private int height;
	private int width;
	private int display_width;
	
	public Platform(int[] position, int type, int status, int height, int width, int display_width) {
		this.position = new double[] {position[0], position[1]};
		this.type = type;
		this.status = status;
		this.height = height;
		this.width = width;
		this.display_width = display_width;
		
		switch(type) {
			case 0: velocity = new double[] {0, 0};
				break;
		}
	}
	public int[] getPosition() {
		return new int[] {(int) this.position[0], (int) this.position[1]};
	}
	public int getStatus() {
		return this.status;
	}
	public int getType() {
		return this.type;
	}
	public void update(boolean jumped_on) {
		if (jumped_on) {
			switch(this.type){
				// Platform of type 0 cannot be destroyed, so keep status at -1
				case 0: this.status = -1;
					break;
				default: this.status--;
					break;
			}
		} else {
			double x = this.position[0];
			double y = this.position[1];
			x += this.velocity[0];
			y += this.velocity[1];
			if (x < 0) {
				x *= -1;
				this.velocity[0] *= -1;
			} else if (x > this.display_width-this.width) {
				x = 2*(this.display_width-this.width) - x;
				this.velocity[0] *= -1;
			}
			
			this.position = new double[] {x, y};
		}
	}
}
