package me.chickenstyle.crafts.animation;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.chickenstyle.crafts.Main;
import me.chickenstyle.crafts.Recipe;
import me.chickenstyle.crafts.Sounds;

public class ExplosionAnimation {
	public ExplosionAnimation(Recipe recipe,Location loc,Player player) {
		FileConfiguration config = Main.getInstance().getConfig();
		Main.getNMSHandler().playParticles(loc, config.getString("animations.EXPLOSION.particle"), 6);
		Sounds.valueOf(config.getString("animations.EXPLOSION.sound")).play(player, 0.7f, 1f);
		loc.getWorld().dropItemNaturally(loc, recipe.getResult());
		
	}
}
