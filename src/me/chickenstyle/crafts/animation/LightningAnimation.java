package me.chickenstyle.crafts.animation;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.chickenstyle.crafts.Recipe;

public class LightningAnimation {
	public LightningAnimation(Recipe recipe,Location loc,Player player) {
		loc.getWorld().strikeLightningEffect(loc);
		loc.getWorld().dropItemNaturally(loc.add(0.5,0.8,0.5), recipe.getResult());
	}
}
