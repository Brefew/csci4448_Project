package lib;

import java.util.Observable;

public class Player implements PlayerInterface {
	private double x_acceleration = 0.0;
	private double max_velocity = 1.0;
	private double[] position;
	private double[] velocity;
	private double    gravity;
	private int         state;
	private String       name;
	private int  player_height = 32;
	private int   player_width = 32;
	private int display_width;
	
	public Player(double initial_gravity, int display_width) {
		this.gravity = initial_gravity;
		this.display_width = display_width;
		this.velocity = new double[2];
		this.velocity[0] = 0.0;
		this.velocity[1] = 0.0;
	}
	public int[] getPosition() {
		return new int[] {(int) this.position[0], (int) this.position[1]};
	}
	public void setPosition(int x, int y) {
		this.position = new double[] {(double) x, (double) y};
	}
	public int getState() {
		return this.state;
	}
	public String getName() {
		return this.name;
	}
	public int getPlayerHeight() {
		return this.player_height;
	}
	public int getPlayerWidth() {
		return this.player_width;
	}
	public void onKeyPressed(char c) {
		
	}
	public void jump() {
		// Set the upward velocity to 5 pixels/frame - the current gravity, which is negative, so
		//   that when updatePlayer() is called shortly after this is called, the velocity will just be
		//   5.
		this.velocity[1] = 1.0;
	}
	public void updatePlayer(double current_gravity) {
		// Updates gravity which can change with power ups
		this.gravity = current_gravity;
		
		if ((this.velocity[0] <= this.max_velocity-this.x_acceleration) ||
			 this.velocity[0] >= (-1*this.max_velocity)-this.x_acceleration) {
			this.velocity[0] += this.x_acceleration;
		}
		// Adds the negative acceleration due to gravity to the vertical portion of the velocity vector
		if (this.velocity[1] >= (-1*this.max_velocity)-this.gravity) {
			this.velocity[1] += this.gravity;
		}
		// Adds the current horizonal velocity in pixels per frame to the x position
		this.position[0] += this.velocity[0];
		// Adds the current vertical velocity in pixels per frame to the y position
		this.position[1] += this.velocity[1];
		
		if (this.position[0] < 0) {
			this.position[0] += this.display_width;
		} else if (this.position[0] >= this.display_width-this.player_width) {
			this.position[0] -= this.display_width;
		}
	}
	@Override
	public void update(Observable o, Object arg) {
		String response[] = (String[]) arg;
		
		if (response[0] == "k") {
			if (((response[1] == "t" && response[2] == "a") ||
				 (response[1] == "f" && response[2] == "d")) &&
				 (this.x_acceleration >= 0.0)) {
				this.x_acceleration -= 0.1;
			} else if (this.x_acceleration <= 0.0){
				this.x_acceleration += 0.1;
			}
		}
		if (this.x_acceleration > 0.1) {
			this.x_acceleration = 0.1;
		} else if (this.x_acceleration < -0.1) {
			this.x_acceleration = -0.1;
		}
	}
}
