package me.chickenstyle.crafts;

import java.util.Set;

import org.bukkit.inventory.ItemStack;

public class Recipe {
	
	private int id;
	private AnimationType animationType;
	private boolean permission;
	private ItemStack result;
	private ItemStack triggerItem;
	private Set<ItemStack> ingredients;
	
	public Recipe(int id, AnimationType animationType, ItemStack result, ItemStack triggerItem, Set<ItemStack> ingredients,boolean permission) {
		this.id = id;
		this.animationType = animationType;
		this.result = result;
		this.triggerItem = triggerItem;
		this.ingredients = ingredients;
		this.permission = permission;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public AnimationType getAnimationType() {
		return animationType;
	}

	public void setAnimationType(AnimationType animationType) {
		this.animationType = animationType;
	}

	public ItemStack getResult() {
		return result;
	}

	public void setResult(ItemStack result) {
		this.result = result;
	}

	public ItemStack getTriggerItem() {
		return triggerItem;
	}

	public void setTriggerItem(ItemStack triggerItem) {
		this.triggerItem = triggerItem;
	}

	public Set<ItemStack> getIngredients() {
		return ingredients;
	}

	public void setIngredient(Set<ItemStack> ingredients) {
		this.ingredients = ingredients;
	}

	public boolean hasPermission() {
		return permission;
	}

	public void setPermission(boolean permission) {
		this.permission = permission;
	}
	
}
