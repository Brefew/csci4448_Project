package lib;

import java.util.Observable;
import java.util.Observer;

public class Player implements Observer {
	private int[] position;
	private double[] velocity;
	private int state;
	private String name;
	
	public int[] getPosition() {
		return this.position;
	}
	public int getState() {
		return this.state;
	}
	public String getName() {
		return this.name;
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
