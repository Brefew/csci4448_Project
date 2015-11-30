package lib;

public class LevelGenerator {
	private PlatformHandler platform_handler;
	private PowerUpHandler power_up_handler;
	private EnemyHandler enemy_handler;
	private int difficulty_modifier = 1000;
	
	public LevelGenerator(long seed, int max_platform_number, int max_power_up_number, int max_enemy_number, int max_display_width, int max_display_height) {
		int[] platforms;
		this.platform_handler = new PlatformHandler(seed, max_platform_number, max_display_width, difficulty_modifier);
		platforms = this.platform_handler.getPlatforms();
		this.power_up_handler = new PowerUpHandler(seed, max_power_up_number, difficulty_modifier, platforms);
		this.enemy_handler = new EnemyHandler(seed, max_enemy_number, difficulty_modifier, platforms);
		this.createLevel();
	}
	public void update(int[] player_position) {
		this.platform_handler.updatePlatforms(player_position);
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
