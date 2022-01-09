package de.cplaiz.activecraftdiscord.discord.listener;

import de.cplaiz.activecraftdiscord.ActiveCraftDiscord;
import de.cplaiz.activecraftdiscord.minecraft.SendToMinecraft;
import de.silencio.activecraftcore.utils.ColorUtils;
import de.silencio.activecraftcore.utils.FileConfig;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class ChatListener extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        FileConfig mainConfig = new FileConfig("config.yml", ActiveCraftDiscord.getPlugin());

        TextChannel chatChannel = ActiveCraftDiscord.getPlugin().getChatChannel();
        TextChannel staffChatChannel = ActiveCraftDiscord.getPlugin().getStaffChatChannel();
        TextChannel consoleChannel = ActiveCraftDiscord.getPlugin().getConsoleChannel();

        Member member = event.getMember();
        if (member == null) return;
        Message message = event.getMessage();
        String rawMessage = message.getContentRaw();
        String messageFormat = mainConfig.getString("discord-to-minecraft-format");

        if (member.getUser().isBot()) return;
        if (event.getChannel() == chatChannel) {
            if (member.getNickname() != null) {
                messageFormat = messageFormat.replace("%nickname%", event.getMember().getNickname()).replace("%username%", member.getEffectiveName());
            } else {
                messageFormat = messageFormat.replace("%nickname%", member.getEffectiveName()).replace("%username%", member.getEffectiveName());
            }
            String finalMessage = messageFormat.replace("%message%", rawMessage);
            SendToMinecraft.sendChat(finalMessage);
        } else if (event.getChannel() == staffChatChannel) {
            if (member.getNickname() != null) {
                Bukkit.broadcast(ChatColor.GOLD + "[StaffChat] " + ChatColor.DARK_AQUA + "[Discord] " + ChatColor.WHITE + member.getNickname() + ChatColor.RESET + ": " + rawMessage, "activecraft.staffchat");
            } else {
                Bukkit.broadcast(ChatColor.GOLD + "[StaffChat] " + ChatColor.DARK_AQUA + "[Discord] " + ChatColor.WHITE + member.getEffectiveName() + ChatColor.RESET + ": " + rawMessage, "activecraft.staffchat");
            }
        } else if (event.getChannel() == consoleChannel) {
            rawMessage = rawMessage.replace("/", "");
            SendToMinecraft.sendAsConsole(rawMessage);
        }
    }
}
