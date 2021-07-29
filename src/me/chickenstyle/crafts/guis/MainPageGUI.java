package me.chickenstyle.crafts.guis;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.chickenstyle.crafts.Recipe;
import me.chickenstyle.crafts.Utils;
import me.chickenstyle.crafts.holders.MainPageHolder;

public class MainPageGUI {
	public MainPageGUI(Player player,Recipe recipe) {
		Inventory inv = Bukkit.createInventory(new MainPageHolder(), 54, Utils.color("&8Create new recipe!"));
		for (int i = 0; i < 54;i++) {
			inv.setItem(i, Utils.getGrayVersionGlass());
		}
		
		ItemStack id = Utils.loadItemStack(new ItemStack(Material.PAPER), "id",recipe);
		
		ItemStack permission;
		
		if (recipe.hasPermission() == false) {
			permission = Utils.loadItemStack(new ItemStack(Material.REDSTONE_BLOCK), "permission",recipe);
		} else {
			permission = Utils.loadItemStack(new ItemStack(Material.EMERALD_BLOCK), "permission",recipe);
		}
		
		ItemStack animation = Utils.loadItemStack(new ItemStack(Material.ARMOR_STAND), "animation",recipe);
		ItemStack result = Utils.loadItemStack(new ItemStack(Material.DIAMOND), "result",recipe);
		ItemStack triggerItem = Utils.loadItemStack(new ItemStack(Utils.getRedstoneTorchMaterial()), "triggerItem",recipe);
		ItemStack ingredients = Utils.loadItemStack(new ItemStack(Utils.getCraftTableMaterial()), "ingredients",recipe);
		
		ItemStack disband = new ItemStack(Material.BARRIER);
		ItemMeta meta = disband.getItemMeta();
		meta.setDisplayName(Utils.color("&cDisband the recipe!"));
		disband.setItemMeta(meta);
		ItemStack saveRecipe = Utils.getGreenVersionGlass("&aSave Recipe!");
		
		inv.setItem(11, id);
		inv.setItem(29, permission);
		inv.setItem(13, animation);
		inv.setItem(31, result);
		inv.setItem(15, triggerItem);
		inv.setItem(33, ingredients);
		
		inv.setItem(48, disband);
		inv.setItem(50, saveRecipe);
		
		player.openInventory(inv);
	}
}
