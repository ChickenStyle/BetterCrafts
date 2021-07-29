package me.chickenstyle.crafts;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

public class PageUtils {
	
	public static List<ItemStack> getPageItems(List<ItemStack> items,int page,int spaces) {
		int upperBound = page * spaces;
		int lowerBound = upperBound - spaces;
		
		List<ItemStack> newItems = new ArrayList<>();
		for (int i = lowerBound;i < upperBound;i++) {
			try {
				newItems.add(items.get(i));
			} catch (IndexOutOfBoundsException e) {
				continue;
			}
		}
		return newItems;
	}
	
	
	public static boolean isPageValid(List<ItemStack> items,int page,int spaces) {
		if (page <= 0) {
			return false;
		}
		
		int upperBound = page * spaces;
		int lowerBound = upperBound - spaces;
		
		return items.size() > lowerBound;
	}
}
