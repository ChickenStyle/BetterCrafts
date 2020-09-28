package me.chickenstyle.crafts.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import me.chickenstyle.crafts.Main;
import me.chickenstyle.crafts.Recipe;
import me.chickenstyle.crafts.Utils;
import me.chickenstyle.crafts.guis.MainPageGUI;

@SuppressWarnings("deprecation")
public class SendMessageEvent implements Listener{

	@EventHandler(priority = EventPriority.MONITOR)
	public void onChat(PlayerChatEvent e) {
		if (Main.typeID.contains(e.getPlayer().getUniqueId())) {
			e.setCancelled(true);
			if (isInt(e.getMessage())) {
				Recipe recipe = Main.creatingRecipe.get(e.getPlayer().getUniqueId());
				recipe.setId(Integer.parseInt(e.getMessage()));
				Main.creatingRecipe.put(e.getPlayer().getUniqueId(), recipe);
				Main.typeID.remove(e.getPlayer().getUniqueId());
				new MainPageGUI(e.getPlayer(), recipe);
			} else {
				e.getPlayer().sendMessage(Utils.color("&cInvalid number, try again!"));
			}
		}
	}
	
	private boolean isInt(String str) {
		try {
			Integer.valueOf(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
