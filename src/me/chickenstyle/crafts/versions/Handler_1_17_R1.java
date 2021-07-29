package me.chickenstyle.crafts.versions;

import me.chickenstyle.crafts.NMSHandler;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;

public class Handler_1_17_R1 implements NMSHandler {

	@Override
	public ItemStack addIDTag(ItemStack item, int data) {
		net.minecraft.world.item.ItemStack nmsItem = org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack.asNMSCopy(item);

		try {
			Object itemCompound = (nmsItem.hasTag()) ? nmsItem.getClass().getMethod("getTag").invoke(nmsItem) :
					getNMSClass("net.minecraft.nbt.NBTTagCompound").newInstance();

			itemCompound.getClass().getMethod("setInt",String.class,int.class).invoke(itemCompound,"IDTag",data);

			nmsItem.getClass().getMethod("setTag",itemCompound.getClass()).invoke(nmsItem,itemCompound);
			return org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack.asBukkitCopy(nmsItem);

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		return null;

		//NBTTagCompound itemCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
		//itemCompound.setInt(tag, data);
		//nmsItem.setTag(itemCompound);
		//return CraftItemStack.asBukkitCopy(nmsItem);
	}


	@Override
	public boolean hasIDTag(ItemStack item) {
		net.minecraft.world.item.ItemStack nmsItem = org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack.asNMSCopy(item);

		try {
			Object itemCompound = (nmsItem.hasTag()) ? nmsItem.getClass().getMethod("getTag").invoke(nmsItem) :
					getNMSClass("net.minecraft.nbt.NBTTagCompound").newInstance();

			return (boolean) itemCompound.getClass().getMethod("hasKey",String.class).invoke(itemCompound,"IDTag");

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		return false;
		//NBTTagCompound itemCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
		//return itemCompound.hasKey(tag);
	}

	@Override
	public int getID(ItemStack item) {
		net.minecraft.world.item.ItemStack nmsItem = org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack.asNMSCopy(item);
		try {
			Object itemCompound = (nmsItem.hasTag()) ? nmsItem.getClass().getMethod("getTag").invoke(nmsItem) :
					getNMSClass("net.minecraft.nbt.NBTTagCompound").newInstance();

			return (int) itemCompound.getClass().getMethod("getInt",String.class).invoke(itemCompound,"IDTag");

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return 0;

		//NBTTagCompound itemCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
		//return itemCompound.getInt(tag);
	}
	
	@Override
	public void playParticles(Location loc, String particle, int amount) {
		loc.getWorld().spawnParticle(Particle.valueOf(particle.toUpperCase()),loc,amount,0,0,0,0.2);
	}

	private Class<?> getNMSClass(String name) {
		try {
			return Class.forName(name);
		} catch (ClassNotFoundException e) {
			return null;
		}
	}
	
}
