package me.chickenstyle.crafts.events;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.chickenstyle.crafts.AnimationType;
import me.chickenstyle.crafts.Main;
import me.chickenstyle.crafts.Recipe;
import me.chickenstyle.crafts.Sounds;
import me.chickenstyle.crafts.Utils;
import me.chickenstyle.crafts.configs.AltarCrafts;
import me.chickenstyle.crafts.guis.IngredientsGUI;
import me.chickenstyle.crafts.guis.IngredientsItemsGUI;
import me.chickenstyle.crafts.guis.MainPageGUI;
import me.chickenstyle.crafts.guis.RecipeGUI;
import me.chickenstyle.crafts.guis.RecipesGUI;
import me.chickenstyle.crafts.guis.ResultPageGUI;
import me.chickenstyle.crafts.guis.TriggerItemGUI;
import me.chickenstyle.crafts.guis.TriggerPageGUI;
import me.chickenstyle.crafts.holders.IngredientsPageHolder;
import me.chickenstyle.crafts.holders.MainPageHolder;
import me.chickenstyle.crafts.holders.NoClickHolder;
import me.chickenstyle.crafts.holders.RecipeHolder;
import me.chickenstyle.crafts.holders.RecipesHolder;
import me.chickenstyle.crafts.holders.ResultPageHolder;
import me.chickenstyle.crafts.holders.TriggerItemHolder;

public class InventoryEvents implements Listener {
	@EventHandler
	public void onClickInventory(InventoryClickEvent e) {
		if (e.getClickedInventory() == null) return;
		if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
		Player player = (Player) e.getWhoClicked();
		Recipe recipe = Main.creatingRecipe.get(player.getUniqueId());
		Inventory inv = e.getView().getTopInventory();
		int slot = e.getSlot();
		if (inv.getHolder() instanceof MainPageHolder) {
			e.setCancelled(true);
			
			if (e.getClickedInventory().equals(inv)) {
				
				if (slot == 48) {
					player.sendMessage(Utils.color("&cRecipe has been disbanded!"));
					Main.animationNumber.remove(player.getUniqueId());
					Main.creatingRecipe.remove(player.getUniqueId());
					player.closeInventory();
					Sounds.NOTE_PLING.play(player, 0.5f, 1f);
				}
				
				if (slot == 11) {
					Main.typeID.add(player.getUniqueId());
					player.closeInventory();
					player.sendMessage(Utils.color("&7Enter an unique id (number) for your recipe!"));
					Sounds.NOTE_PLING.play(player, 0.5f, 1f);
				}
				
				
				if (slot == 29) {
					ItemStack block;
					if (recipe.hasPermission()) {
						recipe.setPermission(false);
						block= Utils.loadItemStack(new ItemStack(Material.REDSTONE_BLOCK), "permission", recipe);
					} else {
						recipe.setPermission(true);
						block = Utils.loadItemStack(new ItemStack(Material.EMERALD_BLOCK), "permission", recipe);
					}
					Main.creatingRecipe.put(player.getUniqueId(), recipe);
					inv.setItem(slot, block);
					Sounds.NOTE_PLING.play(player, 0.5f, 1f);
				}
				
				if (slot == 13) {
					int number = Main.animationNumber.get(player.getUniqueId()) + 1;
					if (number == AnimationType.values().length) number = 0;
					recipe.setAnimationType(AnimationType.values()[number]);
					Main.creatingRecipe.put(player.getUniqueId(), recipe);
					inv.setItem(slot, Utils.loadItemStack(new ItemStack(Material.ARMOR_STAND), "animation", recipe));
					Main.animationNumber.put(player.getUniqueId(), number);
					Sounds.NOTE_PLING.play(player, 0.5f, 1f);
				}
				
				if (slot == 31) {
					Main.typeID.add(player.getUniqueId());
					player.closeInventory();
					Main.typeID.remove(player.getUniqueId());
					new ResultPageGUI(player,recipe);
				}
				
				if (slot == 15) {
					Main.typeID.add(player.getUniqueId());
					player.closeInventory();
					Main.typeID.remove(player.getUniqueId());
					new TriggerPageGUI(player, recipe);
				}
				
				if (slot == 33) {
					Main.typeID.add(player.getUniqueId());
					player.closeInventory();
					Main.typeID.remove(player.getUniqueId());
					new IngredientsGUI(player, recipe);
				}
				
				if (slot == 50) {
					if (recipe.getId() != -99) {
						if (recipe.getResult() != null) {
							if (recipe.getTriggerItem() != null) {
								if (recipe.getIngredients() != null && !recipe.getIngredients().isEmpty()) {
									Main.animationNumber.remove(player.getUniqueId());
									Main.creatingRecipe.remove(player.getUniqueId());
									player.closeInventory();
									player.sendMessage(Utils.color("&aRecipe has been added!"));
									player.sendMessage(Utils.color("&aUse '/bettercrafts reload' to load the recipe!"));
									
									AltarCrafts.addRecipe(recipe);
								} else {
									player.sendMessage(Utils.color("&cNo ingredients!"));
								}
							} else {
								player.sendMessage(Utils.color("&cNo trigger item!"));
							}
						} else {
							player.sendMessage(Utils.color("&cNo Result Item!"));
						}
					} else {
						player.sendMessage(Utils.color("&cInvalid ID"));
					}
				}
				
				
			}
		}
		
		if (inv.getHolder() instanceof ResultPageHolder) {
			if (e.getClickedInventory().equals(inv)) {
				if (slot != 22) {
					e.setCancelled(true);
					
					if (slot == 39) {
						if (inv.getItem(22) != null) {
							if (!inv.getItem(22).equals(recipe.getResult())) {
								player.getInventory().addItem(inv.getItem(22));
							}
						}
						Main.typeID.add(player.getUniqueId());
						player.closeInventory();
						Main.typeID.remove(player.getUniqueId());
						new MainPageGUI(player, recipe);
					}
					
					if (slot == 41) {
						recipe.setResult(inv.getItem(22));
						Main.creatingRecipe.put(player.getUniqueId(),recipe);
						Main.typeID.add(player.getUniqueId());
						player.closeInventory();
						Main.typeID.remove(player.getUniqueId());
						new MainPageGUI(player, recipe);
						
					}
					
				}
			}
		}
		
		if (inv.getHolder() instanceof TriggerItemHolder) {
			if (e.getClickedInventory().equals(inv)) {
				if (slot != 22) {
					e.setCancelled(true);
					
					if (slot == 39) {
						if (inv.getItem(22) != null) {
							if (!inv.getItem(22).equals(recipe.getTriggerItem())) {
								player.getInventory().addItem(inv.getItem(22));
							}
						}
						Main.typeID.add(player.getUniqueId());
						player.closeInventory();
						Main.typeID.remove(player.getUniqueId());
						new MainPageGUI(player, recipe);
					}
					
					if (slot == 41) {
						recipe.setTriggerItem(inv.getItem(22));
						Main.creatingRecipe.put(player.getUniqueId(),recipe);
						Main.typeID.add(player.getUniqueId());
						player.closeInventory();
						Main.typeID.remove(player.getUniqueId());
						new MainPageGUI(player, recipe);
						
					}
					
				}
			}
		}
		
		if (inv.getHolder() instanceof IngredientsPageHolder) {
			
			if (e.getClickedInventory().equals(inv)) {
				if (!(slot > 19 && slot < 25) && !(slot > 28 && slot < 34)) {
					e.setCancelled(true);
					
					if (slot == 48) {
						Main.typeID.add(player.getUniqueId());
						player.closeInventory();
						Main.typeID.remove(player.getUniqueId());
						new MainPageGUI(player, recipe);
					}
					
					if (slot == 50) {
						Set<ItemStack> items = new HashSet<ItemStack>();
						
						for (int i = 0;i < 54;i++) {
							if ((i > 19 && i < 25) || (i > 28 && i < 34)) {
								if (inv.getItem(i) != null) {
									items.add(inv.getItem(i));
								}
							}
						}
						recipe.setIngredient(items);
						Main.creatingRecipe.put(player.getUniqueId(),recipe);
						Main.typeID.add(player.getUniqueId());
						player.closeInventory();
						Main.typeID.remove(player.getUniqueId());
						new MainPageGUI(player, recipe);
						
					}
				}
			}
		}
		
		
		
		if (inv.getHolder() instanceof RecipesHolder) {
			e.setCancelled(true);
			if (e.getClickedInventory().equals(inv)) {
				ItemStack item = e.getCurrentItem();
				if (slot >= 36) {
					if (slot != 40) {
						int page = Main.getNMSHandler().getID(inv.getItem(36));
						if (item.getItemMeta().getDisplayName().equals(Utils.color("&aGo Right!"))) {
							new RecipesGUI(player, AltarCrafts.getRecipes(), page + 1);
						}
						
						if (item.getItemMeta().getDisplayName().equals(Utils.color("&aGo Left!"))) {
							new RecipesGUI(player, AltarCrafts.getRecipes(), page - 1);
						}
					} else {
						player.closeInventory();
					}
				} else {
					if (e.getCurrentItem() != null) {
						if (Main.getNMSHandler().hasIDTag(item)) {
							int id = Main.getNMSHandler().getID(item);
							new RecipeGUI(player, AltarCrafts.getRecipe(id));
						}
					}
				}
			}
		}
		
		if (inv.getHolder() instanceof RecipeHolder) {
			e.setCancelled(true);
			if (slot == 40) {
				new RecipesGUI(player, AltarCrafts.getRecipes(), 1);
			}
			
			if (slot == 21) {
				new TriggerItemGUI(player, AltarCrafts.getRecipe(Main.getNMSHandler().getID(inv.getItem(4))));
			}
			
			if (slot == 23) {
				new IngredientsItemsGUI(player, AltarCrafts.getRecipe(Main.getNMSHandler().getID(inv.getItem(4))));
			}
		}
		
		if (inv.getHolder() instanceof NoClickHolder) {
			e.setCancelled(true);
			if (inv.getSize() == 45) {
				if (slot == 40) {
					new RecipeGUI(player, AltarCrafts.getRecipe(Main.getNMSHandler().getID(inv.getItem(40))));
				}
			} else {
				if (slot == 49) {
					new RecipeGUI(player, AltarCrafts.getRecipe(Main.getNMSHandler().getID(inv.getItem(49))));
				}
			}
		}
		
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		Player player = (Player) e.getPlayer();
		
		if (!Main.typeID.contains(player.getUniqueId()) && Main.creatingRecipe.containsKey(player.getUniqueId())) {
			new BukkitRunnable() {
				
				@Override
				public void run() {
					player.openInventory(e.getInventory());
					
				}
			}.runTaskLater(Main.getInstance(), 2);
		}
		
	}
}
