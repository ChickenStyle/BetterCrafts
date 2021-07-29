package me.chickenstyle.crafts.animation;

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import me.chickenstyle.crafts.Main;
import me.chickenstyle.crafts.Recipe;
import me.chickenstyle.crafts.Sounds;
import xyz.xenondevs.particle.ParticleEffect;

public class FireballAnimation extends BukkitRunnable {

	Player player;
	Recipe recipe;
	Location loc;
	Location particleLoc;
	Set<ItemStack> ingredients;
	ArmorStand stand;
	FileConfiguration config = Main.getInstance().getConfig();
	
	public FireballAnimation(Player player,Recipe recipe,Location loc,Set<ItemStack> ingredients) {
		Main.opening.add(player.getUniqueId());
		this.player = player;
		this.recipe = recipe;
		this.loc = loc;
		this.particleLoc = loc;
		this.ingredients = ingredients;
	}
	
	int ticks = 0;
	int newTicks = 0;
	double phi = 0;
	
	@Override
	public void run() {
		phi += Math.PI/10;
		if (ticks <= 80) {
			
			for (double theta = 0; theta <= 2*Math.PI; theta +=Math.PI/40) {
				double r = 0.7 - (0.007 * ticks);
				double x = r*Math.cos(theta)*Math.sin(phi);
				double y = r*Math.cos(phi) + 1.5;
				double z = r*Math.sin(theta)*Math.sin(phi);
				particleLoc.add(x, y, z);
				ParticleEffect.valueOf(config.getString("animations.FIREBALL.fireballParticle")).display(particleLoc);
				particleLoc.subtract(x,y,z);
				
			}
			
		if (ticks % 4 == 0) {	
			Sounds.valueOf(config.getString("animations.FIREBALL.spinningSound")).play(player, 0.7f, 1f);
		}
			
			
		ticks++;
		} else {

			new BukkitRunnable() {
				
				@Override
				public void run() {
					Sounds.valueOf(config.getString("animations.FIREBALL.explodeSound")).play(player, 0.7f,1f);
					stand = (ArmorStand) loc.getWorld().spawnEntity(loc.clone().add(0.5,0,0), EntityType.ARMOR_STAND);
					stand.setRightArmPose(new EulerAngle(-(Math.PI / 2), -0.69, 0));
					stand.setArms(true);
					stand.setVisible(false);
					stand.setGravity(false);
					stand.setLastDamage(0);
					stand.setRemoveWhenFarAway(false);
					stand.setMaximumNoDamageTicks(100000);
					stand.setItemInHand(recipe.getResult());
					
					new BukkitRunnable() {
						
						@Override
						public void run() {
							if (newTicks <= 80) {
								double t = ((double) newTicks%40) * Math.PI / 20;	            
					            stand.teleport(getLocationAroundCircle(loc.clone().add(0.5,0,0), 0.5, t));
					            newTicks++;
							} else {
								loc.getWorld().dropItem(loc.add(0,1,0), recipe.getResult());
								Sounds.valueOf(config.getString("animations.FIREBALL.itemDropSound")).play(player,0.7f,1f);
								Main.opening.remove(player.getUniqueId());
								stand.remove();
								cancel();
							}
						}
					}.runTaskTimer(Main.getInstance(), 0, 1);
					
				
				
				Main.getNMSHandler().playParticles(loc.clone().add(0,1.5,0), config.getString("animations.FIREBALL.explodeParticle"), 15);
				cancel();
				}
			}.runTaskLater(Main.getInstance(), 38);
			cancel();
		}
		
	}
	
	
	public Location getLocationAroundCircle(Location center, double radius, double angleInRadian) {
        double x = center.getX() + radius * Math.cos(angleInRadian);
        double z = center.getZ() + radius * Math.sin(angleInRadian);
        double y = center.getY();

        Location loc = new Location(center.getWorld(), x, y, z);
        Vector difference = center.toVector().clone().subtract(loc.toVector()); // this sets the returned location's direction toward the center of the circle
        loc.setDirection(difference);

        return loc;
    }
}
