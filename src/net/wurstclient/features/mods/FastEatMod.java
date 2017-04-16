/*
 * Copyright � 2014 - 2017 | Wurst-Imperium | All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.wurstclient.features.mods;

import net.minecraft.item.ItemFood;
import net.minecraft.network.play.client.CPacketPlayer;
import net.wurstclient.compatibility.WConnection;
import net.wurstclient.compatibility.WMinecraft;
import net.wurstclient.events.listeners.UpdateListener;
import net.wurstclient.utils.InventoryUtils;

@Mod.Info(
	description = "Allows you to eat food much faster.\n" + "OM! NOM! NOM!",
	name = "FastEat",
	tags = "FastNom, fast eat, fast nom",
	help = "Mods/FastEat")
@Mod.Bypasses(ghostMode = false, latestNCP = false, olderNCP = false)
public final class FastEatMod extends Mod implements UpdateListener
{
	@Override
	public void onEnable()
	{
		wurst.events.add(UpdateListener.class, this);
	}
	
	@Override
	public void onDisable()
	{
		wurst.events.remove(UpdateListener.class, this);
	}
	
	@Override
	public void onUpdate()
	{
		// check if alive
		if(WMinecraft.getPlayer().getHealth() <= 0)
			return;
		
		// check onGround
		if(!WMinecraft.getPlayer().onGround)
			return;
		
		// check if eating
		if(!mc.gameSettings.keyBindUseItem.pressed)
			return;
		
		// check hunger level
		if(!WMinecraft.getPlayer().getFoodStats().needFood())
			return;
		
		// check held item
		if(!InventoryUtils.checkHeldItem((item) -> item instanceof ItemFood))
			return;
		
		// send packets
		for(int i = 0; i < 100; i++)
			WConnection.sendPacket(new CPacketPlayer(false));
	}
}
