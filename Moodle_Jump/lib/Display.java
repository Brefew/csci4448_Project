package lib;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

public class Display extends Canvas implements Runnable, Observer {
	private static boolean mouse_clicked = false;
    private static final long serialVersionUID = 1L;
    private static int height;
    private static int width;
    private static int[] mouse_position;
    private static String display_what;
    private static MainMenu main_menu;
    private static GameOverMenu game_over_menu;
    //public static final int SCALE = 3;
    public static final String NAME = "Moodle Jump";
    public boolean play = false;

    private JFrame frame;
    public boolean running = false;
    
    public int tickCount = 0;
    
    private BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

    public Display(int display_height, int display_width){
    	height = display_height;
    	width  = display_width;
        setPreferredSize(new Dimension(width/2,height));
        
        frame = new JFrame(NAME);
        frame.setLayout(new BorderLayout());
        
        frame.add(this,BorderLayout.CENTER);
        frame.pack();
        
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        main_menu = new MainMenu(display_height, display_width);
        game_over_menu = new GameOverMenu(display_height, display_width);
    }
    synchronized Thread start(String what) {
    	display_what = what;
        running = true;
        Thread ret = new Thread(this);
        ret.start();
        return ret;
    }
    
    synchronized void stop(){
        running = false;
    }

    public void setState(String state) {
        
    }
    public void onKeyPressed(char c) {
        
    }
    public void onMousePressed(MouseEvent e) {
        
    }
    public void updateGame(int score, int next_highest_score, int[] player,
                            int[][] platforms, int[][] power_ups, int[][] enemies) {
        
    }
    public void gameOver() {
    	
    }
    @Override
    public void update(Observable o, Object arg) {
        String[] response = (String[]) arg;
        if (response[0] == "m") {
        	mouse_position = new int[] {Integer.parseInt(response[1]), Integer.parseInt(response[2])};
        	mouse_clicked = true;
        }
    }
    public void tick(){
    	tickCount++;
    	
    	for(int i = 0; i < pixels.length; i++){
    		pixels[i] = i*tickCount;
    		
    	}
    }
    public void render() {
    	BufferStrategy bs = getBufferStrategy();
    	if(bs == null){
    		createBufferStrategy(3);
    		return;
    	}
    	
    	Graphics g = bs.getDrawGraphics();
    	
    	g.setColor(Color.BLACK);
    	g.fillRect(0, 0, getWidth(), getHeight());
    	
    	g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    	g.dispose();
    	bs.show();
    }
    public void run() {
    	if (display_what == "Game") {
	        long lastTime = System.nanoTime();
	        double nsPerTick = 1000000000D/60D;
	        
	        int ticks = 0;
	        int frames = 0;
	        
	        long lastTimer = System.currentTimeMillis();
	        double delta = 0;
	    	while(running){
	    		long now = System.nanoTime();
	    		delta +=(now-lastTime)/nsPerTick;
	    		lastTime = now;
	    		boolean shouldRender = true;
	    		while(delta >= 1){
	    			ticks++;
	    			tick();
	    			delta -= 1;
	    			shouldRender = true;
	    		}
	    		
	    		try {
					Thread.sleep(2);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		if(shouldRender){
	    		frames++;
	        	render();
	    		}
	    		
	        	if(System.currentTimeMillis()-lastTimer>=1000){
	        		lastTimer +=1000;
	        		System.out.println(frames+","+ticks);
	        		frames = 0;
	        		ticks = 0;
	        	}
	            //System.out.println("test");
	        }
    	} else if (display_what == "Main Menu") {
    		String[] button_names = main_menu.getButtonNames();
    		int[][] button_positions = main_menu.getButtonPositions();
    		int[][] button_heights_and_widths = main_menu.getButtonHeightsAndWidths();
    		boolean[] button_shadowed = main_menu.isButtonShadowed();
    		
    		while(running) {
    			if (mouse_clicked) {
    				mouse_clicked = false;
    				String clicked_button_name = "";
    				
    				for (int i=0; i<button_names.length; i++) {
    					// Test if mouse click was on a button
    					if (mouse_position[0] < button_positions[i][0]+button_heights_and_widths[i][1] &&
    						mouse_position[0] > button_positions[i][0] &&
    						mouse_position[1] < button_positions[i][1]+button_heights_and_widths[i][0] &&
    						mouse_position[1] > button_positions[i][1]) {
    						clicked_button_name = button_names[i];
    						break;
    					}
    				}
    				
    				if (clicked_button_name == "") {
    					continue;
    				} else if (clicked_button_name == "Play") {
    					play = true;
    				} else if (clicked_button_name == "High Scores") {
    					// Need to implement
    				} else if (clicked_button_name == "Quit") {
    					play = false;
    				}
    				
    				stop();
    			} else {
    				try {
    					Thread.sleep(100);
    				} catch (InterruptedException e) {
    					e.printStackTrace();
    				}
    			}
    		}
    	} else if (display_what == "Game Over Menu") {
    		String[] button_names = game_over_menu.getButtonNames();
    		int[][] button_positions = game_over_menu.getButtonPositions();
    		int[][] button_heights_and_widths = game_over_menu.getButtonHeightsAndWidths();
    		boolean[] button_shadowed = game_over_menu.isButtonShadowed();
    		
    		while(running) {
    			if (mouse_clicked) {
    				mouse_clicked = false;
    				String clicked_button_name = "";
    				
    				for (int i=0; i<button_names.length; i++) {
    					// Test if mouse click was on a button
    					if (mouse_position[0] < button_positions[i][0]+button_heights_and_widths[i][1] &&
    						mouse_position[0] > button_positions[i][0] &&
    						mouse_position[1] < button_positions[i][1]+button_heights_and_widths[i][0] &&
    						mouse_position[1] > button_positions[i][1]) {
    						clicked_button_name = button_names[i];
    						break;
    					}
    				}
    				
    				if (clicked_button_name == "") {
    					continue;
    				} else if (clicked_button_name == "Retry") {
    					play = true;
    				} else if (clicked_button_name == "Quit") {
    					play = false;
    				}
    				
    				stop();
    			} else {
    				try {
    					Thread.sleep(100);
    				} catch (InterruptedException e) {
    					e.printStackTrace();
    				}
    			}
    		}
    	}
    }
}
