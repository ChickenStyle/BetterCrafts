package me.chickenstyle.crafts.guis;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.chickenstyle.crafts.Recipe;
import me.chickenstyle.crafts.Utils;
import me.chickenstyle.crafts.holders.TriggerItemHolder;

public class TriggerPageGUI {
	public TriggerPageGUI(Player player,Recipe recipe) {
		Inventory inv = Bukkit.createInventory(new TriggerItemHolder(), 45, Utils.color("&8Set Trigger Item!"));
		for (int i = 0; i < 45;i++) {
			inv.setItem(i, Utils.getGrayVersionGlass());
		}
		
		
		ItemStack back = new ItemStack(Material.ARROW);
		ItemMeta meta = back.getItemMeta();
		meta.setDisplayName(Utils.color("&7Go Back!"));
		back.setItemMeta(meta);
		
		ItemStack save = Utils.getGreenVersionGlass("&aSave Trigger Item!");
		
		inv.setItem(39, back);
		inv.setItem(41, save);
		inv.setItem(22, recipe.getTriggerItem());
		
		player.openInventory(inv);
	}
}
