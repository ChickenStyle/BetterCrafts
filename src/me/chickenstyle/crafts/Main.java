package me.chickenstyle.crafts;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import me.chickenstyle.crafts.versions.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import me.chickenstyle.crafts.configs.AltarCrafts;
import me.chickenstyle.crafts.events.ArmorStandInteractEvent;
import me.chickenstyle.crafts.events.InteractEvent;
import me.chickenstyle.crafts.events.InventoryEvents;
import me.chickenstyle.crafts.events.SendMessageEvent;

public class Main extends JavaPlugin{
	public static HashMap<UUID,Recipe> creatingRecipe;
	public static HashMap<UUID,Integer> animationNumber;
	public static Set<UUID> typeID;
	public static Set<UUID> opening;
	private static NMSHandler versionHandler;
	private static Main instance;
	@Override
	public void onEnable() {
		
		//Load Stuff
		instance = this;
		creatingRecipe = new HashMap<UUID, Recipe>();
		animationNumber = new HashMap<UUID, Integer>();
		typeID = new HashSet<UUID>();
		opening = new HashSet<UUID>();
		
		//Load configs
		this.getConfig().options().copyDefaults();
	    saveDefaultConfig();
		new AltarCrafts(this);
		saveResource("lang.yml", false);
		
		
		//Check server version
		if (getServerVersion() == false) {
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		
		loadListeners();
		
		//Load commands
		getCommand("bettercrafts").setExecutor(new BetterCraftsCommand());
		getCommand("bettercrafts").setTabCompleter(new CraftsTab());
		getCommand("recipes").setExecutor(new RecipesCommand());
		
		//Getting data
        int pluginId = 8954;
        Metrics metrics = new Metrics(this, pluginId);
        metrics.addCustomChart(new Metrics.SimplePie("chart_id", () -> "My value"));
		
		System.out.println(Utils.color("&aBetterCraft has been enabled!"));
	} 
	
	
	public boolean getServerVersion() {
		String version = Bukkit.getServer().getClass().getPackage().getName();
		version = version.substring(version.lastIndexOf(".") + 1);
		boolean isValid = true;
		switch (version) {
		case "v1_8_R1":
			versionHandler = new Handler_1_8_R1();
		break;
		
		case "v1_8_R2":
			versionHandler = new Handler_1_8_R2();
		break;
		
		case "v1_8_R3":
			versionHandler = new Handler_1_8_R3();
		break;
		
		case "v1_9_R1":
			versionHandler = new Handler_1_9_R1();
		break;
		
		case "v1_9_R2":
			versionHandler = new Handler_1_9_R2();
		break;
		
		case "v1_10_R1":
			versionHandler = new Handler_1_10_R1();
		break;
		
		case "v1_11_R1":
			versionHandler = new Handler_1_11_R1();
		break;
		
		case "v1_12_R1":
			versionHandler = new Handler_1_12_R1();
		break;
		
		case "v1_13_R1":
			versionHandler = new Handler_1_13_R1();
		break;
		
		case "v1_13_R2":
			versionHandler = new Handler_1_13_R2();
		break;
		
		case "v1_14_R1":
			versionHandler = new Handler_1_14_R1();
		break;
		
		case "v1_15_R1":
			versionHandler = new Handler_1_15_R1();
		break;
		
		case "v1_16_R1":
			versionHandler = new Handler_1_16_R1();
		break;
		
		case "v1_16_R2":
			versionHandler = new Handler_1_16_R2();
		break;
		
		case "v1_16_R3":
			versionHandler = new Handler_1_16_R3();
		break;

		case "v1_17_R1":
			versionHandler = new Handler_1_17_R1();
		break;
		
		default:
			isValid = false;
			getServer().getConsoleSender().sendMessage(ChatColor.RED + "BetterCrafts >>> This version isn't supported!");
			getServer().getConsoleSender().sendMessage(ChatColor.RED + "BetterCrafts >>> Plugin will be disabled!");
		}
		if (isValid) {
			getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "BetterCrafts >>> NMS Version Detected: " + version);
		}
		return isValid;
		
	}
	
	public void loadListeners() {
		getServer().getPluginManager().registerEvents(new InventoryEvents(), this);
		getServer().getPluginManager().registerEvents(new SendMessageEvent(), this);
		getServer().getPluginManager().registerEvents(new InteractEvent(), this);
		getServer().getPluginManager().registerEvents(new ArmorStandInteractEvent(), this);
	}
	
	public static NMSHandler getNMSHandler() {
		return versionHandler;
	}


	public static Main getInstance() {
		return instance;
	}
	
}
