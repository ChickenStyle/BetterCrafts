package me.chickenstyle.crafts.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

import me.chickenstyle.crafts.Main;

public class ArmorStandInteractEvent implements Listener{
	@EventHandler
	public void onInteract(PlayerArmorStandManipulateEvent e) {
		if (Main.opening.contains(e.getPlayer().getUniqueId())) {
			e.setCancelled(true);
		}
	}
}
