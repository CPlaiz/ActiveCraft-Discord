package de.cplaiz.activecraftdiscord.discord;

import de.cplaiz.activecraftdiscord.ActiveCraftDiscord;
import de.cplaiz.activecraftdiscord.utils.DiscordMessageUtils;
import de.silencio.activecraftcore.utils.ColorUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;

public class SendToDiscord {


    public static void sendLogEmbed(EmbedBuilder embedBuilder) {
        if (ActiveCraftDiscord.getJda().getStatus() != JDA.Status.CONNECTED) return;
        TextChannel channel = ActiveCraftDiscord.getPlugin().getEventLogChannel();
        if (channel != null) channel.sendMessageEmbeds(embedBuilder.build()).queue();
    }

    public static void sendLog(String message) {
        if (ActiveCraftDiscord.getJda().getStatus() != JDA.Status.CONNECTED) return;
        TextChannel channel = ActiveCraftDiscord.getPlugin().getEventLogChannel();
        if (channel != null) channel.sendMessage(message).queue();
    }

    public static void sendChat(String message) {
        if (ActiveCraftDiscord.getJda().getStatus() != JDA.Status.CONNECTED) return;
        TextChannel channel = ActiveCraftDiscord.getPlugin().getChatChannel();
        message = ColorUtils.removeColorAndFormat(message);
        if (channel != null) channel.sendMessage(DiscordMessageUtils.stringToMention(message, channel.getGuild())).queue();
    }

    public static void sendToConsole(String message) {
        if (ActiveCraftDiscord.getJda().getStatus() != JDA.Status.CONNECTED) return;
        TextChannel channel = ActiveCraftDiscord.getPlugin().getConsoleChannel();
        message = ColorUtils.removeColorAndFormat(message);
        if (channel != null) channel.sendMessage(DiscordMessageUtils.stringToMention(message, channel.getGuild())).queue();
    }

    public static void sendStaffChat(String message) {
        if (ActiveCraftDiscord.getJda().getStatus() != JDA.Status.CONNECTED) return;
        TextChannel channel = ActiveCraftDiscord.getPlugin().getStaffChatChannel();
        message = ColorUtils.removeColorAndFormat(message);
        if (channel != null) channel.sendMessage(DiscordMessageUtils.stringToMention(message, channel.getGuild())).queue();
    }

    public static void sendStaffChatEmbed(EmbedBuilder embedBuilder) {
        if (ActiveCraftDiscord.getJda().getStatus() != JDA.Status.CONNECTED) return;
        TextChannel channel = ActiveCraftDiscord.getPlugin().getStaffChatChannel();
        if (channel != null) channel.sendMessageEmbeds(embedBuilder.build()).queue();
    }

    public static void sendChatEmbed(EmbedBuilder embedBuilder) {
        if (ActiveCraftDiscord.getJda().getStatus() != JDA.Status.CONNECTED) return;
        TextChannel channel = ActiveCraftDiscord.getPlugin().getChatChannel();
        if (channel != null) channel.sendMessageEmbeds(embedBuilder.build()).queue();
    }
    public static void sendSocialSpy(String message) {
        if (ActiveCraftDiscord.getJda().getStatus() != JDA.Status.CONNECTED) return;
        TextChannel channel = ActiveCraftDiscord.getPlugin().getSocialSpyChannel();
        if (channel != null) channel.sendMessage(message).queue();
    }

}
