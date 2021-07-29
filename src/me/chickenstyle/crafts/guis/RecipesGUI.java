package me.chickenstyle.crafts.guis;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.chickenstyle.crafts.Main;
import me.chickenstyle.crafts.PageUtils;
import me.chickenstyle.crafts.Recipe;
import me.chickenstyle.crafts.Utils;
import me.chickenstyle.crafts.holders.RecipesHolder;


public class RecipesGUI {
	@SuppressWarnings("unchecked")
	public RecipesGUI(Player player,ArrayList<Recipe> recipes,int page) {
		Inventory gui = Bukkit.createInventory(new RecipesHolder(), 45, Utils.color("&8Recipes!"));
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		YamlConfiguration lang = Utils.getLanguageYML();
		
		for (Recipe recipe:recipes) {
			ItemStack item = recipe.getResult().clone();
			ItemMeta meta = item.getItemMeta();
			String name = lang.getString("items.recipes.itemName").replace("{amount}", item.getAmount() + "");
			
			if (meta.hasDisplayName()) {
				name = name.replace("{item}", meta.getDisplayName());
			} else {
				name = name.replace("{item}", Utils.getName(item.getType()));
			}
			
			meta.setDisplayName(Utils.color(name));
			
			ArrayList<String> lore = new ArrayList<String>();
			
			for (String line:(ArrayList<String>) lang.get("items.recipes.itemLore")) {
	
				if (recipe.hasPermission() == true) {
					if (!player.hasPermission("BetterCrafts." + recipe.getId())) {
						line = line.replace("{checkPermission}", lang.getString("items.recipes.noPermission"));
					} else {
						line = line.replace("{checkPermission}", lang.getString("items.recipes.hasPermission"));
					}
				} else {
					line = line.replace("{checkPermission}", lang.getString("items.recipes.hasPermission"));
				}
				
				lore.add(Utils.color(line));
			}
			meta.setLore(lore);
			item.setItemMeta(meta);
			items.add(Main.getNMSHandler().addIDTag(item, recipe.getId()));
		}
		
		ItemStack left;
		if (PageUtils.isPageValid(items, page - 1, 36)) {
			left = Utils.getGreenVersionGlass("&aGo Left!");
		} else {
			left = Utils.getRedVersionGlass();
		}
		left = Main.getNMSHandler().addIDTag(left, page);
		
		ItemStack right;

		if (PageUtils.isPageValid(items, page + 1, 36)) {
			right = Utils.getGreenVersionGlass("&aGo Right!");
		} else {
			right = Utils.getRedVersionGlass();
		}
		
		gui.setItem(36, left);
		gui.setItem(44, right);
		
		for (int i = 37;i < 44;i++) {
			gui.setItem(i, Utils.getGrayVersionGlass());
		}
		
		if (!items.isEmpty()) {
			for (ItemStack item:PageUtils.getPageItems(items, page, 36)) {
				gui.addItem(item);
			}
		}
		
		ItemStack disband = new ItemStack(Material.BARRIER);
		ItemMeta meta = disband.getItemMeta();
		meta.setDisplayName(Utils.color("&cClose!"));
		disband.setItemMeta(meta);
		
		gui.setItem(40, disband);
		
		
		player.openInventory(gui);
	}
}
