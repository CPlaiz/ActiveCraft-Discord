package de.cplaiz.activecraftdiscord.minecraft;

import de.cplaiz.activecraftdiscord.ActiveCraftDiscord;
import de.silencio.activecraftcore.ActiveCraftCore;
import net.dv8tion.jda.api.entities.Member;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

public class SendToMinecraft {

    public static void sendChat(String string, Member member) {
        Bukkit.broadcastMessage(member.getNickname() + string);
    }

    public static void sendChat(String string) {
        Bukkit.broadcastMessage(string);
    }

    public static void sendAsConsole(String string) {
        Bukkit.getScheduler().runTask(ActiveCraftDiscord.getPlugin(), () -> {
            ConsoleCommandSender consoleCommandSender = Bukkit.getConsoleSender();
            Bukkit.dispatchCommand(consoleCommandSender, string);
        });
    }
}
