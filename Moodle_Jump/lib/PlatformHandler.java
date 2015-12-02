package lib;

public class PlatformHandler {
	private Platform[] platforms;
	
<<<<<<< HEAD
	public int[] getPlatforms() {
		int[] ret = new int[this.platforms.length*4];
		for (int i=0; i<this.platforms.length; i++) {
			int[] pos = platforms[i].getPosition();
			int type = platforms[i].getType();
			int status = platforms[i].getStatus();
			ret[4*i+0] = pos[0];
			ret[4*i+1] = pos[1];
			ret[4*i+2] = type;
			ret[4*i+3] = status;
=======
	public int[][] getPlatforms() {
		int[][] ret = new int[this.platforms.size()][4];
		Platform temp_array[] = (Platform[]) this.platforms.toArray();
		for (int i=0; i<temp_array.length; i++) {
			int[] pos = temp_array[i].getPosition();
			int type = temp_array[i].getType();
			int status = temp_array[i].getStatus();
			ret[i][0] = pos[0];
			ret[i][1] = pos[1];
			ret[i][2] = type;
			ret[i][3] = status;
>>>>>>> 486deb4fc4ef7bde4b80217e638b1d9cbd315587
		}
		return ret;
	}
	public void updatePlatforms(int[] player_position) {
		
	}
}
