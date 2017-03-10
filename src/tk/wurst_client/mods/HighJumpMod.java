/*
 * Copyright � 2014 - 2016 | Wurst-Imperium | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.mods;

import tk.wurst_client.mods.Mod.Bypasses;
import tk.wurst_client.mods.Mod.Category;
import tk.wurst_client.mods.Mod.Info;
import tk.wurst_client.navigator.settings.SliderSetting;
import tk.wurst_client.navigator.settings.SliderSetting.ValueDisplay;

@Info(category = Category.MOVEMENT,
	description = "Makes you jump much higher.",
	name = "HighJump",
	noCheatCompatible = false,
	tags = "high jump",
	help = "Mods/HighJump")
@Bypasses(ghostMode = false,
	latestNCP = false,
	olderNCP = false,
	antiCheat = false,
	mineplexAntiCheat = false)
public class HighJumpMod extends Mod
{
	public int jumpHeight = 6;
	
	@Override
	public void initSettings()
	{
		settings.add(new SliderSetting("Height", jumpHeight, 1, 100, 1,
			ValueDisplay.INTEGER)
		{
			@Override
			public void update()
			{
				jumpHeight = (int)getValue();
			}
		});
	}
}
