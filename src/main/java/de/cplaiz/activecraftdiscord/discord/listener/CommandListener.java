package de.cplaiz.activecraftdiscord.discord.listener;

import java.util.concurrent.TimeUnit;


import de.cplaiz.activecraftdiscord.ActiveCraftDiscord;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {

		String message = event.getMessage().getContentDisplay();
		
		Guild guild = event.getGuild();
		Member user = event.getMember();
		
		long guildid = event.getGuild().getIdLong();
		
		if(event.isFromType(ChannelType.TEXT)) {
			TextChannel channel = event.getTextChannel();
			
			//!tut arg0 arg1 arg2 ...
			if(message.startsWith("a!")) {
				String[] args = message.substring(2).split(" ");
					
				if(args.length > 0) {
					if(!ActiveCraftDiscord.INSTANCE.getCmdMan().perform(args[0], event.getMember(), channel, event.getMessage())) {
						channel.sendMessage("`Unknown command!`").complete().delete().queueAfter(2, TimeUnit.SECONDS);
						
					}
				}
			}
		}
	}
}
