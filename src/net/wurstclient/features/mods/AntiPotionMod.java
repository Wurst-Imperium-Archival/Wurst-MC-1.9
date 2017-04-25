/*
 * Copyright � 2014 - 2017 | Wurst-Imperium | All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.wurstclient.features.mods;

import net.minecraft.init.MobEffects;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.potion.Potion;
import net.wurstclient.compatibility.WConnection;
import net.wurstclient.compatibility.WMinecraft;
import net.wurstclient.events.listeners.UpdateListener;
import net.wurstclient.features.Mod;

@Mod.Info(tags = "NoPotion, Zoot, anti potions, no potions",
	help = "Mods/AntiPotion")
@Mod.Bypasses(ghostMode = false, latestNCP = false, olderNCP = false)
public final class AntiPotionMod extends Mod implements UpdateListener
{
	private final Potion[] blockedEffects = new Potion[]{MobEffects.hunger,
		MobEffects.moveSlowdown, MobEffects.digSlowdown, MobEffects.harm,
		MobEffects.confusion, MobEffects.blindness, MobEffects.weakness,
		MobEffects.wither, MobEffects.poison};
	
	public AntiPotionMod()
	{
		super("AntiPotion", "Blocks bad potion effects.");
	}
	
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
		// check gamemode
		if(WMinecraft.getPlayer().capabilities.isCreativeMode)
			return;
		
		// check onGround
		if(!WMinecraft.getPlayer().onGround)
			return;
		
		// check effects
		if(!hasBadEffect())
			return;
		
		// send packets
		for(int i = 0; i < 1000; i++)
			WConnection.sendPacket(new CPacketPlayer());
	}
	
	private boolean hasBadEffect()
	{
		if(WMinecraft.getPlayer().getActivePotionEffects().isEmpty())
			return false;
		
		for(Potion effect : blockedEffects)
			if(WMinecraft.getPlayer().isPotionActive(effect))
				return true;
			
		return false;
	}
}
