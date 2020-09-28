package me.chickenstyle.crafts;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public interface NMSHandler {
	public ItemStack addIDTag(ItemStack item,int id);
	public boolean hasIDTag(ItemStack item);
	public int getID(ItemStack item);
	public void playParticles(Location loc,String particle,int amount);
	
}
