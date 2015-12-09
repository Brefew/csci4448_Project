package lib;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Observable;
import java.util.Observer;
import javax.imageio.ImageIO;
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

    private int player_highest_y = 0;
    private JFrame frame;
    public boolean running = false;
    
    public int tickCount = 0;
    
    private BufferedImage image;
    private int[] pixels;

    public Display(int display_height, int display_width){
    	height = display_height;
    	width  = display_width;
    	image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
    	pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        setPreferredSize(new Dimension(width,height));
        
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
        Thread ret = new Thread(this);
        ret.start();
        return ret;
    }
    
    synchronized void stop(){
        running = false;
    }

    public void setState(String state) {
        
    }
    public void updateGame(int score, int next_highest_score, int[] player,
                            int[][] platforms, int[][] power_ups, int[][] enemies) {
        int j=0;
        if (player[1] > player_highest_y) {
        	player_highest_y = player[1];
        }
        String[] sprite_names = new String[1+platforms.length+power_ups.length+enemies.length];
        int[][] sprite_positions = new int[1+platforms.length+power_ups.length+enemies.length][2];
        for (int i=0; i<power_ups.length; i++) {
        	if (power_ups[i][3] != 0) {
        		sprite_names[j] = "power_up_"+power_ups[i][2];
        		sprite_positions[j][0] = power_ups[i][0];
        		sprite_positions[j][1] = power_ups[i][1];
        		j++;
        	}
        }
        for (int i=0; i<enemies.length; i++) {
        	if (enemies[i][3] != 0) {
        		sprite_names[j] = "enemy_"+enemies[i][2]+"_"+enemies[i][3];
        		sprite_positions[j][0] = enemies[i][0];
        		sprite_positions[j][1] = enemies[i][1];
        		j++;
        	}
        }
        for (int i=0; i<platforms.length; i++) {
        	if (platforms[i][3] != 0) {
        		sprite_names[j] = "platform_"+platforms[i][2]+"_"+platforms[i][3];
        		sprite_positions[j][0] = platforms[i][0];
        		sprite_positions[j][1] = platforms[i][1];
        		j++;
        	}
        }
        sprite_names[j] = "player";
        sprite_positions[j][0] = player[0];
		sprite_positions[j][1] = player[1];

		this.render(sprite_names, sprite_positions);
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
    }
    public void render(String[] sprite_names, int[][] sprite_positions) {
    	boolean game = false;
    	
    	if (game) {
    		// If game is running, we need to convert the x and y coordinates all from
    		//   world coordinates to screen coordinates
    	} else {
    		// If it's not, a menu is being displayed, so we don't need to convert any coordinates
    	}
    	BufferStrategy bs = getBufferStrategy();
    	if(bs == null) {
    		createBufferStrategy(3);
    		return;
    	}
    	// This sets the background color to white
    	for (int i=0; i<height; i++) {
    		for (int j=0; j<width; j++) {
    			pixels[i*width+j] = Integer.MAX_VALUE;
    		}
    	}
    	for (int i=0; i<sprite_names.length; i++) {
    		if (sprite_names[i] == null) {
    			continue;
    		}
    		BufferedImage sprite;
    		try {
    			int sprite_x = sprite_positions[i][0];
    			int sprite_y = convertWorldYToScreenY(sprite_positions[i][1]);
    			String file_name = "game_sprites/"+sprite_names[i]+".png";
    			file_name = file_name.replace(' ', '_');
    			Path path = FileSystems.getDefault().getPath(file_name);
				sprite = ImageIO.read(path.toFile());
				for (int j=0; j<sprite.getHeight(); j++) {
					for (int k=0; k<sprite.getWidth(); k++) {
						int pixel_index = (sprite_y+j-sprite.getHeight())*width+(sprite_x+k);
						if (pixel_index >=0 && pixel_index < height*width) {
							pixels[pixel_index] = sprite.getRGB(k, j);
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}

    	Graphics g = bs.getDrawGraphics();
    	g.setColor(Color.WHITE);
    	g.fillRect(0, 0, getWidth(), getHeight());
    	g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    	g.dispose();
    	bs.show();
    }
    public void run() {
    	if (display_what == "Game") {
    		running = true;
	    	while(running) {
    		try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
    		}
    	} else if (display_what == "Main Menu") {
    		running = true;
    		String[] button_names = main_menu.getButtonNames();
    		int[][] button_positions = main_menu.getButtonPositions();
    		int[][] button_heights_and_widths = main_menu.getButtonHeightsAndWidths();
//    		boolean[] button_shadowed = main_menu.isButtonShadowed();
    		
    		render(button_names, button_positions);
    		while(running) {
        		render(button_names, button_positions);
    			if (mouse_clicked) {
    				mouse_clicked = false;
    				String clicked_button_name = "";
    				
    				for (int i=0; i<button_names.length; i++) {
    					// Test if mouse click was on a button
    					if (mouse_position[0] < button_positions[i][0]+button_heights_and_widths[i][1] &&
    						mouse_position[0] > button_positions[i][0] &&
    						mouse_position[1] < convertWorldYToScreenY(button_positions[i][1]) &&
    						mouse_position[1] > convertWorldYToScreenY(button_positions[i][1])-button_heights_and_widths[i][0] ) {
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
    					Thread.sleep(200);
    				} catch (InterruptedException e) {
    					e.printStackTrace();
    				}
    			}
    		}
    	} else if (display_what == "Game Over Menu") {
    		running = false;
    		String[] button_names = game_over_menu.getButtonNames();
    		int[][] button_positions = game_over_menu.getButtonPositions();
    		int[][] button_heights_and_widths = game_over_menu.getButtonHeightsAndWidths();
//    		boolean[] button_shadowed = game_over_menu.isButtonShadowed();
    		player_highest_y = 0;
    		
    		render(button_names, button_positions);
    		while(!running) {
    			render(button_names, button_positions);
    			System.out.println("Game over");
    			
    			if (mouse_clicked) {
    				mouse_clicked = false;
    				String clicked_button_name = "";
    				
    				for (int i=0; i<button_names.length; i++) {
    					// Test if mouse click was on a button
    					if (mouse_position[0] < button_positions[i][0] &&
    						mouse_position[0] > button_positions[i][0]-button_heights_and_widths[i][1] &&
    						mouse_position[1] < button_positions[i][1] &&
    						mouse_position[1] > button_positions[i][1]-button_heights_and_widths[i][0]) {
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
    private int convertWorldYToScreenY(int y) {
    	int ret;
    	
    	// If the player's highest y is set to 0, then the game is in a menu
    	if (player_highest_y == 0) {
    		ret = height-1-y;
    	} else {
    		ret = height/2+(player_highest_y-y);
    	}
    	
    	return ret;
    }
}
