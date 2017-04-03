/*
 * Copyright � 2014 - 2017 | Wurst-Imperium | All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.wurstclient.features.mods;

import java.util.List;

import net.minecraft.client.gui.ChatLine;
import net.wurstclient.events.ChatInputEvent;
import net.wurstclient.events.listeners.ChatInputListener;

@Mod.Info(
	description = "Blocks chat spam.\n" + "Example:\n" + "Spam!\n" + "Spam!\n"
		+ "Spam!\n" + "Will be changed to:\n" + "Spam! [x3]",
	name = "AntiSpam",
	tags = "NoSpam, ChatFilter, anti spam, no spam, chat filter",
	help = "Mods/AntiSpam")
@Mod.Bypasses
public class AntiSpamMod extends Mod implements ChatInputListener
{
	@Override
	public void onEnable()
	{
		wurst.events.add(ChatInputListener.class, this);
	}
	
	@Override
	public void onReceivedMessage(ChatInputEvent event)
	{
		final List<ChatLine> chatLines = event.getChatLines();
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					Thread.sleep(50);
					if(chatLines.size() > 1)
						for(int i = chatLines.size() - 1; i >= 1; i--)
							for(int i2 = i - 1; i2 >= 0; i2--)
							{
								// Fixes concurrent modification
								if(chatLines.size() <= i)
									continue;
								
								if(chatLines.get(i).getChatComponent()
									.getUnformattedText().startsWith(
										chatLines.get(i2).getChatComponent()
											.getUnformattedText()))
								{
									if(chatLines.get(i).getChatComponent()
										.getUnformattedText().endsWith("]")
										&& chatLines.get(i).getChatComponent()
											.getUnformattedText()
											.contains(" [x"))
									{
										int numberIndex1 =
											chatLines.get(i).getChatComponent()
												.getUnformattedText()
												.lastIndexOf(" [x") + 3;
										int numberIndex2 =
											chatLines.get(i).getChatComponent()
												.getUnformattedText().length()
												- 1;
										int number = Integer.valueOf(chatLines
											.get(i).getChatComponent()
											.getUnformattedText().substring(
												numberIndex1, numberIndex2));
										chatLines.get(i2).getChatComponent()
											.appendText(
												" [x" + (number + 1) + "]");
									}else
										chatLines.get(i2).getChatComponent()
											.appendText(" [x2]");
									chatLines.remove(i);
								}
							}
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}, "AntiSpam").start();
	}
	
	@Override
	public void onDisable()
	{
		wurst.events.remove(ChatInputListener.class, this);
	}
}
