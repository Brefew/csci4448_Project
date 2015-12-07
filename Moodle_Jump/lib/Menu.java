package lib;

public abstract class Menu {
	protected Button[] buttons;
	protected int button_count;
	protected int button_width = 128;
	protected int button_height = 64;
	protected int button_starting_x;
	
	protected class Button {
		private String name;
		private int[] position;
		private int height;
		private int width;
		private boolean shadowed;
		
		public Button(String name, int[] position, int height, int width, boolean shadowed) {
			this.name = name;
			this.position = position;
			this.height = height;
			this.width = width;
			this.shadowed = shadowed;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int[] getPosition() {
			return position;
		}
		public void setPosition(int[] position) {
			this.position = position;
		}
		public int getHeight() {
			return height;
		}
		public void setHeight(int height) {
			this.height = height;
		}
		public int getWidth() {
			return width;
		}
		public void setWidth(int width) {
			this.width = width;
		}
		public boolean isShadowed() {
			return shadowed;
		}
		public void setShadowed(boolean shadowed) {
			this.shadowed = shadowed;
		}
	}

	public abstract String[] getButtonNames();
	public abstract int[][] getButtonPositions();
	public abstract int[][] getButtonHeightsAndWidths();
	public abstract boolean[] isButtonShadowed();
}
