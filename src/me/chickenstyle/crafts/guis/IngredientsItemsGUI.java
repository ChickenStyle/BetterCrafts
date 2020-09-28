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

public class IngredientsItemsGUI {
	public IngredientsItemsGUI(Player player,Recipe recipe) {
		Inventory gui = Bukkit.createInventory(new NoClickHolder(), 54, Utils.color("&8Ingredients!"));
		
		for (int i = 0;i < 54;i++) {
			if (!(i > 19 && i < 25) && !(i > 28 && i < 34)) {
				gui.setItem(i, Utils.getGrayVersionGlass());
			}
		}
		
		for (ItemStack item:recipe.getIngredients()) {
			gui.addItem(item);
		}
		
		ItemStack back = new ItemStack(Material.ARROW);
		ItemMeta bMeta = back.getItemMeta();
		bMeta.setDisplayName(Utils.color("&7Go back!"));
		back.setItemMeta(bMeta);
		gui.setItem(49, Main.getNMSHandler().addIDTag(back, recipe.getId()));
		
		
		player.openInventory(gui);
	}
}
