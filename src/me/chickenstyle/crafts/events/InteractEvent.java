package me.chickenstyle.crafts.events;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import me.chickenstyle.crafts.Main;
import me.chickenstyle.crafts.Message;
import me.chickenstyle.crafts.Recipe;
import me.chickenstyle.crafts.Utils;
import me.chickenstyle.crafts.animation.ExplosionAnimation;
import me.chickenstyle.crafts.animation.FireballAnimation;
import me.chickenstyle.crafts.animation.LightningAnimation;
import me.chickenstyle.crafts.animation.SpinningCircleAnimation;
import me.chickenstyle.crafts.configs.AltarCrafts;

public class InteractEvent implements Listener{

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getClickedBlock() == null) return;
		if (e.getItem() == null) return;
		if (e.getPlayer().isSneaking() == false) return;
		if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;


		if (!Bukkit.getServer().getVersion().contains("1.8")) {
			if (e.getHand().equals(EquipmentSlot.OFF_HAND)) return;
		}
		
		Player player = e.getPlayer();
		Material altar = null;
		if (Main.getInstance().getConfig().getString("altarBlock").toUpperCase().equals("ENCHANTING_TABLE")) {
			altar = Utils.getEnchantingTableMaterial();
		} else {
			altar = Material.valueOf(Main.getInstance().getConfig().getString("altarBlock").toUpperCase());
		}

		if (e.getClickedBlock().getType().equals(altar)) {
			e.setCancelled(true);
			ArrayList<ItemStack> items = new ArrayList<ItemStack>();
			ArrayList<Item> entities = new ArrayList<Item>();
			for(Entity ent:e.getClickedBlock().getWorld().getNearbyEntities(e.getClickedBlock().getLocation().clone().add(0,1,0), 1, 1, 1)) {
				if (ent instanceof Item) {
					entities.add((Item) ent);
					items.add(((Item) ent).getItemStack());
				}
			}
			
			for (Recipe recipe:AltarCrafts.getRecipes()) {
				if (recipe.getTriggerItem().isSimilar(e.getItem()) && e.getItem().getAmount() >= recipe.getTriggerItem().getAmount()) {
					if (recipe.getIngredients().size() == items.size()) {
						int equals = 0;
						for (ItemStack item:recipe.getIngredients()) {
							if (items.contains(item)) {
								equals++;
							}
						}
						if (equals == items.size()) {
							if (recipe.hasPermission() == true) {
								if (!player.hasPermission("BetterCrafts." + recipe.getId()) && !player.hasPermission("BetterCrafts.Admin")) {
									player.sendMessage(Message.NO_PERM_CRAFT.getMSG());
									return;
								}
							}
							
							ItemStack inHand = e.getItem();
							inHand.setAmount(inHand.getAmount() - recipe.getTriggerItem().getAmount());
							player.setItemInHand(inHand);
							
							for (Item item:entities) {
								item.remove();
							}
							
							switch (recipe.getAnimationType()) {
							case EXPLOSION:
								new ExplosionAnimation(recipe, e.getClickedBlock().getLocation().clone().add(0.5,0.8,0.5), player);
							break;
							case CIRCLE_SPINNING:
								new SpinningCircleAnimation(player, recipe, e.getClickedBlock().getLocation().clone().add(0.5,0.7,0.5), recipe.getIngredients()).runTaskTimer(Main.getInstance(),0, 1);
							break;
							case FIREBALL:
								new FireballAnimation(player, recipe, e.getClickedBlock().getLocation().clone().add(0.5,0,0.5), recipe.getIngredients()).runTaskTimer(Main.getInstance(),0, 1);
							break;
							case LIGHTNING:
								new LightningAnimation(recipe, e.getClickedBlock().getLocation().clone(), player);
								break;

							}
							return;
						}
					}
				}
			}
		}
	}
}
