package me.chickenstyle.crafts;

import org.bukkit.configuration.file.YamlConfiguration;

public enum Message {
	NO_PERM_COMMAND(getString("noPermCommand")),
	NO_PERM_CRAFT(getString("noPermCraft"));
	
	
	private String message;
	Message(String message){
		this.message = message;
	}
	
	
	private static String getString(String path) {
		YamlConfiguration lang = Utils.getLanguageYML();
		
		return lang.getString("messages." + path);
		
	}
	
	public String getMSG() {
		return Utils.color(message);
	}
	
}
