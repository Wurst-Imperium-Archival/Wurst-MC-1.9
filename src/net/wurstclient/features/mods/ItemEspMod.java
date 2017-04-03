/*
 * Copyright � 2014 - 2017 | Wurst-Imperium | All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.wurstclient.features.mods;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.wurstclient.compatibility.WMinecraft;
import net.wurstclient.events.listeners.RenderListener;
import net.wurstclient.features.Feature;
import net.wurstclient.utils.RenderUtils;

@Mod.Info(description = "Allows you to see items through walls.",
	name = "ItemESP",
	tags = "item esp",
	help = "Mods/ItemESP")
@Mod.Bypasses
public final class ItemEspMod extends Mod implements RenderListener
{
	@Override
	public Feature[] getSeeAlso()
	{
		return new Feature[]{wurst.mods.chestEspMod};
	}
	
	@Override
	public void onEnable()
	{
		wurst.events.add(RenderListener.class, this);
	}
	
	@Override
	public void onRender(float partialTicks)
	{
		for(Object entity : WMinecraft.getWorld().loadedEntityList)
			if(entity instanceof EntityItem)
				RenderUtils.entityESPBox((Entity)entity, 2);
	}
	
	@Override
	public void onDisable()
	{
		wurst.events.remove(RenderListener.class, this);
	}
}
