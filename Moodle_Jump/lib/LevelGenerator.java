package lib;

public class LevelGenerator {
	private PlatformHandler platform_handler;
	private PowerUpHandler power_up_handler;
	private EnemyHandler enemy_handler;
	
	public LevelGenerator() {
		this.platform_handler = new PlatformHandler();
		this.power_up_handler = new PowerUpHandler();
		this.enemy_handler = new EnemyHandler();
		this.createLevel();
	}
	public void update(int[] player_position) {
		
	}
	public int[] getPlatforms() {
		return this.platform_handler.getPlatforms();
	}
	public int[] getPowerUps() {
		return this.power_up_handler.getPowerUps();
	}
	public int[] getEnemies() {
		return this.enemy_handler.getEnemies();
	}
	
	private void createLevel() {
		
	}
}
