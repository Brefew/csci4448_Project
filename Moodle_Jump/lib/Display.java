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
    
    private static final long serialVersionUID = 1L;
    private static int height;
    private static int width;
    //public static final int SCALE = 3;
    public static final String NAME = "Moodle Jump";

    
    
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
        
        
    }
    synchronized void start() {
        running = true;
        new Thread(this).start();
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
    private void displayMenu(String menu) {
        
    }
    private void displayGame() {
        
        
    }
    private void updateGame(Player player, int score, int next_highest_score,
                            int[] platforms, int power_ups, int[] enemies) {
        
    }
    @Override
    public void update(Observable o, Object arg) {
        // TODO Auto-generated method stub
        
    }
    
    
    public void tick(){
    	tickCount++;
    	
    	for(int i = 0; i < pixels.length; i++){
    		pixels[i] = i*tickCount;
    		
    	}
    }
    public void render(){
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
        
    }
}
