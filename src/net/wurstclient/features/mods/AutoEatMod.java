/*
 * Copyright � 2014 - 2017 | Wurst-Imperium | All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.wurstclient.features.mods;

import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.wurstclient.compatibility.WMinecraft;
import net.wurstclient.compatibility.WPlayerController;
import net.wurstclient.events.listeners.UpdateListener;
import net.wurstclient.features.Feature;

@Mod.Info(description = "Automatically eats food when necessary.",
	name = "AutoEat",
	tags = "AutoSoup,auto eat,auto soup",
	help = "Mods/AutoEat")
@Mod.Bypasses
public final class AutoEatMod extends Mod implements UpdateListener
{
	private int oldSlot;
	private int bestSlot;
	
	@Override
	public Feature[] getSeeAlso()
	{
		return new Feature[]{wurst.mods.autoSoupMod};
	}
	
	@Override
	public void onEnable()
	{
		oldSlot = -1;
		wurst.events.add(UpdateListener.class, this);
	}
	
	@Override
	public void onUpdate()
	{
		if(oldSlot != -1 || WMinecraft.getPlayer().capabilities.isCreativeMode
			|| WMinecraft.getPlayer().getFoodStats().getFoodLevel() >= 20)
			return;
		float bestSaturation = 0F;
		bestSlot = -1;
		for(int i = 0; i < 9; i++)
		{
			ItemStack item = WMinecraft.getPlayer().inventory.getStackInSlot(i);
			if(item == null)
				continue;
			float saturation = 0;
			if(item.getItem() instanceof ItemFood)
				saturation =
					((ItemFood)item.getItem()).getSaturationModifier(item);
			if(saturation > bestSaturation)
			{
				bestSaturation = saturation;
				bestSlot = i;
			}
		}
		if(bestSlot == -1)
			return;
		oldSlot = WMinecraft.getPlayer().inventory.currentItem;
		wurst.events.add(UpdateListener.class, new UpdateListener()
		{
			@Override
			public void onUpdate()
			{
				if(!AutoEatMod.this.isActive()
					|| WMinecraft.getPlayer().capabilities.isCreativeMode
					|| WMinecraft.getPlayer().getFoodStats()
						.getFoodLevel() >= 20)
				{
					stop();
					return;
				}
				ItemStack item =
					WMinecraft.getPlayer().inventory.getStackInSlot(bestSlot);
				if(item == null || !(item.getItem() instanceof ItemFood))
				{
					stop();
					return;
				}
				WMinecraft.getPlayer().inventory.currentItem = bestSlot;
				WPlayerController.processRightClick();
				mc.gameSettings.keyBindUseItem.pressed = true;
			}
			
			private void stop()
			{
				mc.gameSettings.keyBindUseItem.pressed = false;
				WMinecraft.getPlayer().inventory.currentItem = oldSlot;
				oldSlot = -1;
				wurst.events.remove(UpdateListener.class, this);
			}
		});
	}
	
	@Override
	public void onDisable()
	{
		wurst.events.remove(UpdateListener.class, this);
	}
	
	public boolean isEating()
	{
		return oldSlot != -1;
	}
}
