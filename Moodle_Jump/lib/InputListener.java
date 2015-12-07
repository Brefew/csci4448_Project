package lib;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;


public class InputListener implements KeyListener, MouseListener{
	private Key left, right;
	private Mouse mouse;

	public class Key extends Observable {
		public boolean pressed = false;
		public char letter; 
		public Key(char l) {
			this.letter = l;
		}
		public void toggle(boolean isPressed){
			String response[] = new String[3];
			pressed = isPressed;
			
			response[0] = "k";
			if (pressed) {
				response[1] = "t";
			} else {
				response[1] = "f";
			}
			response[2] = Character.toString(letter);

			setChanged();
			notifyObservers(response);
		}
	}
	
	public class Mouse extends Observable{
		public Point point;
		public String name = "mouse";
		
		public void setPoint(Point p) {
			String response[] = new String[3];
			point = p;
			
			response[0] = "m";
			response[1] = Integer.toString(point.x);
			response[2] = Integer.toString(point.y);
			
			setChanged();
			notifyObservers(response);
		}
		@Override
		public void notifyObservers() {
			
		}
	}
	
	public InputListener(){
		left = new Key('a');
		right = new Key('d');
		mouse = new Mouse();
	}
	public void attach(Observer o) {

		left.addObserver(o);
		right.addObserver(o);
		mouse.addObserver(o);
		
	}
	public void keyPressed(KeyEvent e) {
		toggleKey(e.getKeyCode(),true);
		
	}
	public void keyReleased(KeyEvent e) {
		toggleKey(e.getKeyCode(),false);
	}
	public void toggleKey(int keyCode, boolean isPressed){
		if(keyCode == (KeyEvent.VK_LEFT | KeyEvent.VK_A)){
			left.toggle(isPressed);			
		}
		
		if(keyCode == (KeyEvent.VK_RIGHT | KeyEvent.VK_D)){
			right.toggle(isPressed);
		}
	}
	public void mouseClicked(MouseEvent e) {
		
		if(e.getButton() == MouseEvent.BUTTON1){
			mouse.point = e.getPoint();
		}
	}

	// Not needed by us
	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}

}
