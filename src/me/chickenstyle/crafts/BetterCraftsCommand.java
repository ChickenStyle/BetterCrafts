package me.chickenstyle.crafts;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.chickenstyle.crafts.configs.AltarCrafts;
import me.chickenstyle.crafts.guis.MainPageGUI;

public class BetterCraftsCommand implements CommandExecutor {
	
	@SuppressWarnings("static-access")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length >= 1) {
			switch (args[0].toLowerCase()) {
			case "addrecipe":
				if (sender instanceof Player) {
					Player player = (Player) sender;
					if (player.hasPermission("BetterCrafts.Admin") || player.hasPermission("BetterCrafts.addRecipe")) {
						Main.creatingRecipe.put(player.getUniqueId(), new Recipe(-99, AnimationType.EXPLOSION, null, null, null, false));
						Main.animationNumber.put(player.getUniqueId(),0);
						new MainPageGUI(player, new Recipe(-99, AnimationType.EXPLOSION, null, null, null, false));
					} else {
						player.sendMessage(Message.NO_PERM_COMMAND.getMSG());
					}
				} else {
					sender.sendMessage(Utils.color("&cYou cannot use this command in console!"));
				}
			break;
			
			case "help":
				sender.sendMessage(Utils.color("&7--------------------&f<Better&cCrafts&f>&7--------------------"));
				sender.sendMessage(Utils.color("&7> &f/bc help   &7//Help command"));
				sender.sendMessage("");
				sender.sendMessage(Utils.color("&7> &f/bc addrecipe   &7//Command to add new recipe"));
				sender.sendMessage("");
				sender.sendMessage(Utils.color("&7> &f/bc giveitem   &7//Command to give result recipe to player"));
				sender.sendMessage("");
				sender.sendMessage(Utils.color("&7> &f/bc reload   &7//Command to reload the configs"));
				sender.sendMessage("");
				sender.sendMessage(Utils.color("&7> &f/recipes   &7// Shows all the recipe in a GUI!"));
				sender.sendMessage(Utils.color("&7-----------------------------------------------------"));
			break;
			// /bcs giveItem {player} {id}
			case "giveitem":
				if (sender.hasPermission("BetterCrafts.Admin") || sender.hasPermission("BetterCrafts.giveItem")) {
					if (args.length == 3) {
						if (Bukkit.getServer().getPlayer(args[1]) != null) {
							try {
								Player player = Bukkit.getServer().getPlayer(args[1]);
								int id = Integer.valueOf(args[2]);
								if (AltarCrafts.hasRecipe(id)) {
									Recipe recipe = AltarCrafts.getRecipe(id);
									if (player.getInventory().firstEmpty() != -1) {
										player.getInventory().addItem(recipe.getResult());
									} else {
										player.getLocation().getWorld().dropItemNaturally(player.getLocation(), recipe.getResult());
									}
								} else {
									sender.sendMessage(Utils.color("&cNo recipe with this id!"));
								}
								
							} catch(Exception e) {
								sender.sendMessage(Utils.color("&cInvalid ID"));
							}
						} else {
							sender.sendMessage(Utils.color("&cPlayer isn't online!"));
						}
					} else {
						sender.sendMessage(Utils.color("&7Invalid usage!"));
						sender.sendMessage(Utils.color("&7use '/bc giveitem {player} {recipe_id}'"));
					}
					
				}
			break;
			
			case "reload":
				if (sender.hasPermission("BetterCrafts.Admin") || sender.hasPermission("BetterCrafts.reload")) {
					Main.getInstance().saveConfig();
					Main.getInstance().reloadConfig();
					
					AltarCrafts.configReload();
					
					File lang = new File(Main.getInstance().getDataFolder()+"/lang.yml");
					try {
						Utils.getLanguageYML().loadConfiguration(lang);
						Utils.getLanguageYML().save(lang);
					} catch (IOException e) {
						
						e.printStackTrace();
					}
					
					sender.sendMessage(Utils.color("&aConfigs have been reloaded!"));
				} else {
					sender.sendMessage(Message.NO_PERM_COMMAND.getMSG());
				}
			break;
			
			
			default:
				sender.sendMessage(Utils.color("&7Invalid usage!"));
				sender.sendMessage(Utils.color("&7use /bc help"));
			break;
			}
		} else {
			sender.sendMessage(Utils.color("&7Invalid usage!"));
			sender.sendMessage(Utils.color("&7use /bc help"));
		}
		
		return true;
	}

}
