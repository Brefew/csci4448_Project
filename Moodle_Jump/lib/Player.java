package lib;

import java.util.Observable;
import java.util.Observer;

public class Player implements Observer {
	private double[] position;
	private double[] velocity;
	private double    gravity;
	private int         state;
	private String       name;
	private int  player_height = 32;
	private int   player_width = 32;
	
	public Player(double initial_gravity) {
		this.gravity = initial_gravity;
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
		this.velocity[1] = 5 - this.gravity;
	}
	public void updatePlayer(double current_gravity) {
		// Updates gravity which can change with power ups
		this.gravity = current_gravity;
		// Adds the negative acceleration due to gravity to the vertical portion of the velocity vector
		this.velocity[1] += this.gravity;
		// Adds the current horizonal velocity in pixels per frame to the x position
		this.position[0] += this.velocity[0];
		// Adds the current vertical velocity in pixels per frame to the y position
		this.position[1] += this.velocity[1];
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	
}
