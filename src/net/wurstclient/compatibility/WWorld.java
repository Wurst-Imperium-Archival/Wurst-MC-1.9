/*
 * Copyright � 2014 - 2018 | Wurst-Imperium | All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.wurstclient.compatibility;

import net.minecraft.util.math.AxisAlignedBB;

public final class WWorld
{
	public static boolean collidesWithAnyBlock(AxisAlignedBB bb)
	{
		return WMinecraft.getWorld().func_184143_b(bb);
	}
}
