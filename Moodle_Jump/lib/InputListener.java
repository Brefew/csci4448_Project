package lib;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;


public class InputListener implements KeyListener, MouseListener{
	private Key left,right;
	private Mouse mouse;
	public void attach(Observer o) {

		left.addObserver(o);
		right.addObserver(o);
		mouse.addObserver(o);
		
	}
	public class Key extends Observable{
		public boolean pressed = false;
		public char letter; 
		public Key(char l) {
			this.letter = l;
		}
		public void toggle(boolean isPressed){
			pressed = isPressed;
		}
		public String name = "key";
	}
	
	public class Mouse extends Observable{
		
		public Point point;
		public String name = "mouse";
	}

	
	public InputListener(){
		left = new Key('a');
		right = new Key('d');
		mouse = new Mouse();
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

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
