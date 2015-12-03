package lib;

import java.util.Observable;
import java.util.Observer;

public class Player implements Observer {
	private double[] position;
	private double[] velocity;
	private int state;
	private String name;
	private int player_height = 32;
	private int player_width = 32;
	
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
	
	private void updatePosition() {
		
	}
	private void jump() {
		
	}
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}
