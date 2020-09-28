package me.chickenstyle.crafts.guis;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.chickenstyle.crafts.Main;
import me.chickenstyle.crafts.Recipe;
import me.chickenstyle.crafts.Utils;
import me.chickenstyle.crafts.holders.RecipeHolder;


public class RecipeGUI {
	
	public RecipeGUI(Player player,Recipe recipe) {
		
		Inventory gui;
		if (recipe.getResult().getItemMeta().hasDisplayName()) {
			gui = Bukkit.createInventory(new RecipeHolder(), 45, Utils.color(recipe.getResult().getItemMeta().getDisplayName() + "'s &8Recipe"));
		} else {
			gui = Bukkit.createInventory(new RecipeHolder(), 45, Utils.color(Utils.getName(recipe.getResult().getType()) + "'s &8Recipe"));
		}
		
		for (int i = 0; i < 45;i++) {
			gui.setItem(i, Utils.getGrayVersionGlass());
		}
		
		gui.setItem(4, null);
		gui.setItem(21, null);
		gui.setItem(23, null);
		gui.setItem(40, null);
		
		gui.addItem(Main.getNMSHandler().addIDTag(recipe.getResult().clone(), recipe.getId()));
		
		ItemStack triggerItem = Utils.loadItemStack(new ItemStack(Utils.getRedstoneTorchMaterial()), "triggerItem",recipe);
		ItemMeta meta = triggerItem.getItemMeta();
		meta.setDisplayName(Utils.color("&7Click to see the trigger item!"));
		triggerItem.setItemMeta(meta);
		
		ItemStack ingredients = Utils.loadItemStack(new ItemStack(Utils.getCraftTableMaterial()), "ingredients",recipe);
		ItemMeta iMeta = ingredients.getItemMeta();
		iMeta.setDisplayName(Utils.color("&7Click to see the ingredients!"));
		iMeta.setLore(new ArrayList<String>());
		ingredients.setItemMeta(iMeta);
		
		gui.addItem(triggerItem);
		gui.addItem(ingredients);
		
		ItemStack back = new ItemStack(Material.ARROW);
		ItemMeta bMeta = back.getItemMeta();
		bMeta.setDisplayName(Utils.color("&7Go back!"));
		back.setItemMeta(bMeta);
		
		gui.addItem(back);
		
		
		
		player.openInventory(gui);
	}
}
