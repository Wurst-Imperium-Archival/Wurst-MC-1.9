/*
 * Copyright � 2016 | Alexander01998 | All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.wurstclient.features.mods;

import net.wurstclient.compatibility.WMinecraft;
import net.wurstclient.events.listeners.UpdateListener;
import net.wurstclient.features.HelpPage;
import net.wurstclient.features.Mod;
import net.wurstclient.features.SearchTags;

@SearchTags({"BoatFlight", "boat fly", "boat flight"})
@HelpPage("Mods/BoatFly")
@Mod.Bypasses(ghostMode = false, latestNCP = false)
public final class BoatFlyMod extends Mod implements UpdateListener
{
	public BoatFlyMod()
	{
		super("BoatFly", "Allows you to fly with boats and rideable entities.\n"
			+ "Bypasses NoCheat+, at least for now.");
	}
	
	@Override
	public void onEnable()
	{
		wurst.events.add(UpdateListener.class, this);
	}
	
	@Override
	public void onUpdate()
	{
		if(!WMinecraft.getPlayer().isRiding())
			return;
		
		WMinecraft.getPlayer().getRidingEntity().motionY =
			mc.gameSettings.keyBindJump.pressed ? 0.3 : 0;
	}
	
	@Override
	public void onDisable()
	{
		wurst.events.remove(UpdateListener.class, this);
	}
}
