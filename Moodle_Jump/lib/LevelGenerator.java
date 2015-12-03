package lib;

public class LevelGenerator {
	private PlatformHandler platform_handler;
	private PowerUpHandler power_up_handler;
	private EnemyHandler enemy_handler;

	public LevelGenerator(long seed, int max_platform_number, int max_power_up_number, int max_enemy_number, int max_display_height, int max_display_width, int difficulty_modifier) {
		int[][] platforms;
		this.platform_handler = new PlatformHandler(seed, max_platform_number, max_display_width, difficulty_modifier);
		platforms = this.platform_handler.getPlatforms();
		this.power_up_handler = new PowerUpHandler(seed, max_power_up_number, difficulty_modifier, platforms);
		this.enemy_handler = new EnemyHandler(seed, max_enemy_number, difficulty_modifier, platforms);
	}
	public void update(int player_y, int[] indices_of_affected_platforms, int[] indices_of_affected_enemies, int[] indices_of_affected_power_ups) {
		int[][] platforms;
		this.platform_handler.updatePlatforms(player_y, indices_of_affected_platforms);
		platforms = this.platform_handler.getPlatforms();
		this.power_up_handler.updatePowerUps(player_y, indices_of_affected_power_ups, platforms);
		this.enemy_handler.updateEnemies(player_y, indices_of_affected_enemies, platforms);
	}
	public int[][] getPlatforms() {
		return this.platform_handler.getPlatforms();
	}
	public int[][] getPowerUps() {
		return this.power_up_handler.getPowerUps();
	}
	public int[][] getEnemies() {
		return this.enemy_handler.getEnemies();
	}
	public int[][] getHeightsAndWidths() {
		int[][] heights_and_widths;
		int[] platform_height_and_width;
		int[] power_up_height_and_width;
		int[] enemy_height_and_width;
		
		platform_height_and_width = new int[] {this.platform_handler.getPlatformHeight(), this.platform_handler.getPlatformWidth()};
		power_up_height_and_width = new int[] {this.power_up_handler.getPowerUpHeight(), this.power_up_handler.getPowerUpWidth()};
		enemy_height_and_width    = new int[] {this.enemy_handler.getEnemyHeight(), this.enemy_handler.getEnemyWidth()};
		heights_and_widths = new int[][] {platform_height_and_width,
				                          power_up_height_and_width,
				                          enemy_height_and_width};
		return heights_and_widths;
	}
}
