package lib;

import java.util.Observable;
import java.util.Observer;

public class Player implements Observer {
	private int[] position;
	private double[] velocity;
	private int state;
	private String name;
	private int player_height = 32;
	private int player_width = 32;
	
	public int[] getPosition() {
		return this.position;
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
