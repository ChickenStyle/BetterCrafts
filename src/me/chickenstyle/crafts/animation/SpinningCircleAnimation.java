package me.chickenstyle.crafts.animation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.Location;
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

public class SpinningCircleAnimation extends BukkitRunnable {
	Player player;
	Recipe recipe;
	Location loc;
	Location originalLoc;
	Set<ItemStack> ingredients;
	ArrayList<ArmorStand> stands;
	HashMap<ArmorStand,Double> radians;

	
	public SpinningCircleAnimation(Player player,Recipe recipe,Location loc,Set<ItemStack> ingredients) {
		Main.opening.add(player.getUniqueId());
		this.player = player;
		this.recipe = recipe;
		this.loc = loc;
		this.originalLoc = loc.clone();
		this.ingredients = ingredients;
		stands = new ArrayList<ArmorStand>();
		radians = new HashMap<ArmorStand,Double>();
		int i = 0;
		for (ItemStack item:ingredients) {
			double angleInRadian = ((2*Math.PI)/ingredients.size()) * i;
			ArmorStand stand = (ArmorStand) loc.getWorld().spawnEntity(getLocationAroundCircle(loc, 1.5, angleInRadian), EntityType.ARMOR_STAND);
			stand.setRightArmPose(new EulerAngle(-(Math.PI / 2), -0.69, 0));
			stand.setArms(true);
			stand.setVisible(false);
			stand.setGravity(false);
			stand.setSmall(true);
			stand.setItemInHand(item);
			stands.add(stand);
			radians.put(stand, angleInRadian);
			i++;
		}
		
	}
	
	int ticks = 0;
	@Override
	public void run() {
		if (ticks <= 140) {
			loc.subtract(0,0.005,0);
			for (ArmorStand stand:stands) {
				double newRadian = radians.get(stand) + 0.13;
				radians.put(stand, newRadian);
				
				double time = 0.00904285714;
				Location location = getLocationAroundCircle(loc,1.5 - (time * ticks), newRadian);
                stand.setVelocity(new Vector(1, 0, 0));
                stand.teleport(location);
    			if (ticks % 2 == 0) {
    				ParticleEffect.FLAME.display(location.clone().add(0,0.7,0));
    			}
                
    			if (ticks % 4 == 0) {
    				Sounds.ORB_PICKUP.play(player, 0.7f, 0.7f);
    				//Main.getNMSHandler().playParticles(location.clone().add(0,0.7,0), "DRIP_LAVA", 1);
    			}
    			
    			
			}
			ticks++;
		} else {
			Main.getNMSHandler().playParticles(originalLoc.clone().add(0,0.5,0), "EXPLOSION_LARGE", 10);
			Sounds.EXPLODE.play(player, 0.7f, 1f);
			loc.getWorld().dropItemNaturally(originalLoc, recipe.getResult());
			
			
			for (ArmorStand stand:stands) {
				stand.remove();
			}
			Main.opening.remove(player.getUniqueId());
			stands.clear();
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
