package me.chickenstyle.crafts.guis;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.chickenstyle.crafts.Main;
import me.chickenstyle.crafts.Recipe;
import me.chickenstyle.crafts.Utils;
import me.chickenstyle.crafts.holders.NoClickHolder;

public class TriggerItemGUI {
	public TriggerItemGUI(Player player,Recipe recipe) {
		Inventory gui = Bukkit.createInventory(new NoClickHolder(), 45, Utils.color("&8Trigger Item!"));
		
		for (int i = 0;i < 45;i++) {
			gui.setItem(i, Utils.getGrayVersionGlass());
		}
		
		gui.setItem(22, recipe.getTriggerItem());
		
		ItemStack back = new ItemStack(Material.ARROW);
		ItemMeta bMeta = back.getItemMeta();
		bMeta.setDisplayName(Utils.color("&7Go back!"));
		back.setItemMeta(bMeta);
		gui.setItem(40, Main.getNMSHandler().addIDTag(back, recipe.getId()));
		
		player.openInventory(gui);
	}
}
