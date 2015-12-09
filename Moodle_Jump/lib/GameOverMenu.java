package lib;

public class GameOverMenu extends Menu {
	public GameOverMenu(int display_height, int display_width) {
		int button_starting_y;
		int[] position = new int[2];
		this.button_count = 2;
		this.buttons = new Button[this.button_count];
		this.button_starting_x = (display_width/2)-this.button_width/2;
		// Middle button starting y
		button_starting_y = (display_height/2)-this.button_height/2;
		
		position[0] = this.button_starting_x;
		position[1] = button_starting_y+this.button_height;
		buttons[0] = new Button("Play", position, this.button_height, this.button_width, true);
		
		position = new int[2];
		position[0] = this.button_starting_x;
		position[1] = button_starting_y-this.button_height;
		buttons[1] = new Button("Quit", position, this.button_height, this.button_width, true);
	}
	@Override
	public String[] getButtonNames() {
		String[] ret = new String[this.button_count];
		
		for (int i=0; i<this.button_count; i++) {
			ret[i] = this.buttons[i].getName();
		}
		
		return ret;
	}

	@Override
	public int[][] getButtonPositions() {
		int[][] ret = new int[this.button_count][2];
		
		for (int i=0; i<this.button_count; i++) {
			ret[i] = this.buttons[i].getPosition();
		}
		
		return ret;
	}

	@Override
	public int[][] getButtonHeightsAndWidths() {
		int[][] ret = new int[this.button_count][2];
		
		for (int i=0; i<this.button_count; i++) {
			ret[i] = new int[] {this.buttons[i].getHeight(), this.buttons[i].getWidth()};
		}
		
		return ret;
	}

	@Override
	public boolean[] isButtonShadowed() {
		boolean[] ret = new boolean[this.button_count];
		
		for (int i=0; i<this.button_count; i++) {
			ret[i] = this.buttons[i].isShadowed();
		}
		
		return ret;
	}

}
