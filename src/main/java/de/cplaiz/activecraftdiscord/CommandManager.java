package de.cplaiz.activecraftdiscord;

import java.util.concurrent.ConcurrentHashMap;

import de.cplaiz.activecraftdiscord.discord.commands.HugCommand;
import de.cplaiz.activecraftdiscord.discord.commands.types.ServerCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class CommandManager {

	public ConcurrentHashMap<String, ServerCommand> commands;
	
	public CommandManager() {
		this.commands = new ConcurrentHashMap<>();

		this.commands.put("hug", new HugCommand());

	}
	
	public boolean perform(String command, Member m, TextChannel channel, Message message) {
		
		ServerCommand cmd;
		if((cmd = this.commands.get(command.toLowerCase())) != null) {
			
			cmd.performCommand(m, channel, message);
			return true;
		}
		
		return false;
	}
}
