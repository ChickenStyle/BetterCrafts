package me.chickenstyle.crafts.configs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import me.chickenstyle.crafts.AnimationType;
import me.chickenstyle.crafts.Main;
import me.chickenstyle.crafts.Recipe;

public class AltarCrafts {
	
	
	/*  Recipes:
	 *  	ID:
	 *     		AnimationType: Spinning
	 *     		HasPermission: fales/true
	 *     		Result: {itemstack}
	 *     		TriggerItem: {itemstack}
	 *     		Ingredients: itemstacks
	 *
	 * 
	 * 
	 */
	
	private static File file;
	private static YamlConfiguration config;
	public AltarCrafts(Main main) {
  	  file = new File(main.getDataFolder(), "AltarCrafts.yml");
  	 if (!file.exists()) {
  		 try {
				 file.createNewFile();
		    	 config = YamlConfiguration.loadConfiguration(file);
		    	  	try {
		    				config.save(file);
		    		    	config = YamlConfiguration.loadConfiguration(file);
		    			} catch (IOException e) {
		    				e.printStackTrace();
		    			}
			} catch (IOException e) {
				e.printStackTrace();
			}
  		 
  	 }
  	config = YamlConfiguration.loadConfiguration(file);
   }
    
	static public ArrayList<Recipe> getRecipes(){
    	ArrayList<Recipe> list = new ArrayList<Recipe>();
        	if (config.getConfigurationSection("Recipes") == null) return new ArrayList<Recipe>();
        	for (String id:config.getConfigurationSection("Recipes").getKeys(false)) {
        		list.add(getRecipe(Integer.valueOf(id)));
        	}
        	return list;
    }
    
	static public void addRecipe(Recipe recipe) {
    		String path = "Recipes." + recipe.getId() + ".";
    		
    		config.set(path + "AnimationType", recipe.getAnimationType().toString());
    		config.set(path + "HasPermission", recipe.hasPermission());
    		config.set(path + "Result", recipe.getResult());
    		config.set(path + "TriggerItem", recipe.getTriggerItem());
    		config.set(path + "Ingredients", recipe.getIngredients());
    		
    	  	try {
    			config.save(file);
    	    	config = YamlConfiguration.loadConfiguration(file);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    }
    

	@SuppressWarnings("unchecked")
	static public Recipe getRecipe(int id) {
		String path = "Recipes." + id + ".";
		
		AnimationType animationType = AnimationType.valueOf(config.getString(path + "AnimationType"));
		boolean permission = config.getBoolean(path + "HasPermission");
		ItemStack result = (ItemStack) config.get(path + "Result");
		ItemStack triggerItem = (ItemStack) config.get(path + "TriggerItem");
		Set<ItemStack> ingredients = (Set<ItemStack>) config.get(path + "Ingredients");
		
		return new Recipe(id, animationType, result, triggerItem, ingredients, permission);
    }
    	
    static public boolean hasRecipe(int id) {
    	if (config.get("Recipes." + id) != null) {
    		return true;
    	}
    	return false;
    }
	
	static public void configReload() {
   	 config = YamlConfiguration.loadConfiguration(file);
		try {
			config.save(file);
			config = YamlConfiguration.loadConfiguration(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
