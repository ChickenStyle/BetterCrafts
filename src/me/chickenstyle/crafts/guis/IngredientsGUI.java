package me.chickenstyle.crafts.guis;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.chickenstyle.crafts.Recipe;
import me.chickenstyle.crafts.Utils;
import me.chickenstyle.crafts.holders.IngredientsPageHolder;

public class IngredientsGUI {
	public IngredientsGUI(Player player,Recipe recipe) {
		Inventory inv = Bukkit.createInventory(new IngredientsPageHolder(), 54, Utils.color("&8Set Ingredients!"));
		for (int i = 0; i < 54;i++) {
			if (!(i > 19 && i < 25) && !(i > 28 && i < 34)) {
				inv.setItem(i, Utils.getGrayVersionGlass());
			}
		}
		
		if (recipe.getIngredients() != null && !recipe.getIngredients().isEmpty()) {
			for (ItemStack item:recipe.getIngredients()) {
				inv.addItem(item);
			}
		}
		
		ItemStack back = new ItemStack(Material.ARROW);
		ItemMeta meta = back.getItemMeta();
		meta.setDisplayName(Utils.color("&7Go Back!"));
		back.setItemMeta(meta);
		
		ItemStack save = Utils.getGreenVersionGlass("&aSave Ingredients!");
		
		inv.setItem(48, back);
		inv.setItem(50, save);
		
		player.openInventory(inv);
	}
}
