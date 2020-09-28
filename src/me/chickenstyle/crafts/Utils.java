package me.chickenstyle.crafts;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Utils {
	public static String color(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}
	
	public static Material getSignMaterial() {
		Material item;
		if (Bukkit.getVersion().contains("1.16") ||
			Bukkit.getVersion().contains("1.15") ||
			Bukkit.getVersion().contains("1.14")) {
			item = Material.valueOf("OAK_SIGN");
		} else {
			item = Material.valueOf("SIGN");
		}
		
		return item;
		
	}
	
	public static Material getRedstoneTorchMaterial() {
		Material item;
		if (Bukkit.getVersion().contains("1.16") ||
			Bukkit.getVersion().contains("1.15") ||
			Bukkit.getVersion().contains("1.14") ||
			Bukkit.getVersion().contains("1.13")) {
			item = Material.valueOf("REDSTONE_TORCH");
		} else {
			item = Material.valueOf("REDSTONE_TORCH_ON");
		}
		return item;
		
	}
	
	public static Material getCraftTableMaterial() {
		Material item;
		if (Bukkit.getVersion().contains("1.16") ||
			Bukkit.getVersion().contains("1.15") ||
			Bukkit.getVersion().contains("1.14") ||
			Bukkit.getVersion().contains("1.13")) {
			item = Material.valueOf("CRAFTING_TABLE");
		} else {
			item = Material.valueOf("WORKBENCH");
		}
		return item;
		
	}
	
	public static Material getEnchantingTableMaterial() {
		Material item;
		if (Bukkit.getVersion().contains("1.16") ||
			Bukkit.getVersion().contains("1.15") ||
			Bukkit.getVersion().contains("1.14") ||
			Bukkit.getVersion().contains("1.13")) {
			item = Material.valueOf("ENCHANTING_TABLE");
		} else {
			item = Material.valueOf("ENCHANTMENT_TABLE");
		}
		return item;
		
	}
	
	
	
    @SuppressWarnings("deprecation")
	public static ItemStack getGreenVersionGlass(String title) {
    	ItemStack glass = null;
        if (Bukkit.getVersion().contains("1.13") || Bukkit.getVersion().contains("1.14") ||
        		Bukkit.getVersion().contains("1.15") || Bukkit.getVersion().contains("1.16")) {
            glass = new ItemStack(Material.valueOf("GREEN_STAINED_GLASS_PANE"));
        } else {
        	glass = new ItemStack(Material.valueOf("STAINED_GLASS_PANE"), 1, (short) 13);
        }
        ItemMeta meta = glass.getItemMeta();
        meta.setDisplayName(color(title));
        glass.setItemMeta(meta);
        return glass;
    }
    
    @SuppressWarnings("deprecation")
	public static ItemStack getGrayVersionGlass() {
    	ItemStack glass = null;
        if (Bukkit.getVersion().contains("1.13") || Bukkit.getVersion().contains("1.14") ||
        		Bukkit.getVersion().contains("1.15") || Bukkit.getVersion().contains("1.16")) {
            glass = new ItemStack(Material.valueOf("GRAY_STAINED_GLASS_PANE"));
        } else {
        	glass = new ItemStack(Material.valueOf("STAINED_GLASS_PANE"), 1, (short) 7);
        }
        
        ItemMeta meta = glass.getItemMeta();
        meta.setDisplayName(" ");
        glass.setItemMeta(meta);
        return glass;
    }
    
	@SuppressWarnings("deprecation")
	public static ItemStack getRedVersionGlass() {
    	ItemStack glass = null;
        if (Bukkit.getVersion().contains("1.13") || Bukkit.getVersion().contains("1.14") ||
        		Bukkit.getVersion().contains("1.15") || Bukkit.getVersion().contains("1.16")) {
            glass = new ItemStack(Material.valueOf("RED_STAINED_GLASS_PANE"));
        } else {
        	glass = new ItemStack(Material.valueOf("STAINED_GLASS_PANE"), 1, (short) 14);
        }
        
        ItemMeta meta = glass.getItemMeta();
        meta.setDisplayName(color("&cNo available pages!"));
        glass.setItemMeta(meta);
        
        return glass;
    }
    
    
    public static YamlConfiguration getLanguageYML() {
    	File lang = new File(Main.getInstance().getDataFolder()+"/lang.yml");
    	return YamlConfiguration.loadConfiguration(lang);
    }
	
    
    @SuppressWarnings("unchecked")
	public static ItemStack loadItemStack(ItemStack item,String name,Recipe recipe) {
    	YamlConfiguration lang = getLanguageYML();
    	String path = "items." + name + ".";
    	
    	ItemMeta meta = item.getItemMeta();
    	meta.setDisplayName(color(lang.getString(path + ".itemName")));
    	
    	ArrayList<String> lore = new ArrayList<String>();
    	
    	for (String line:(ArrayList<String>) lang.get(path + "itemLore")) {
    		String loreLine = line;
    		
    		loreLine = loreLine.replace("{id}", recipe.getId() + "");
    		
    		loreLine = loreLine.replace("{animation}", recipe.getAnimationType().toString());
    		
    		if (recipe.getResult() != null) {
    			if (recipe.getResult().getItemMeta().hasDisplayName()) {
    				loreLine = loreLine.replace("{resultItem}", recipe.getResult().getItemMeta().getDisplayName());
    			} else {
    				loreLine = loreLine.replace("{resultItem}", getName(recipe.getResult().getType()));
    			}
    			
    			loreLine = loreLine.replace("{resultAmount}", recipe.getResult().getAmount() + "");
    		} else {
    			loreLine = loreLine.replace("{resultItem}", "none");
    			loreLine = loreLine.replace("{resultAmount}", 0 + "");
    		}
    		
    		
    		
    		if (recipe.getTriggerItem() != null) {
    			if (recipe.getTriggerItem().getItemMeta().hasDisplayName()) {
    				loreLine = loreLine.replace("{triggerItem}", recipe.getTriggerItem().getItemMeta().getDisplayName());
    			} else {
    				loreLine = loreLine.replace("{triggerItem}", getName(recipe.getTriggerItem().getType()));
    			}
    			loreLine = loreLine.replace("{triggerAmount}", recipe.getTriggerItem().getAmount() + "");
    		} else {
    			loreLine = loreLine.replace("{triggerItem}", "none");
    			loreLine = loreLine.replace("{triggerAmount}", 0 + "");
    		}
    		
    		lore.add(color(loreLine));
    		
    	} 
    	
    	meta.setLore(lore);
    	
    	item.setItemMeta(meta);
    	return item;
    }
    
    
    public static String getName(Material mat) {
		String name = mat.name().replace('_',' ').toLowerCase();
		String[] data = name.split("");
		
		for (int i = 0;i < data.length;i++) {
			if (i != 0) {
				if (data[i - 1].equals(" ")) {
					data[i] = data[i].toUpperCase();
				}
			} else {
				data[i] = data[i].toUpperCase();
			}
		}
		
		name = arrayToString(data);
		return name;
	}
    
	public static String arrayToString(String[] arr) {
		String str = "";
		for (String chr:arr) {
			str = str + chr;
		}
		return str;
	}
    
}
