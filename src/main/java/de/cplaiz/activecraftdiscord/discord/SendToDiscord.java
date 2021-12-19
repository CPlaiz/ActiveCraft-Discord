package de.cplaiz.activecraftdiscord.discord;

import de.cplaiz.activecraftdiscord.ActiveCraftDiscord;
import de.cplaiz.activecraftdiscord.utils.DiscordMessageUtils;
import de.silencio.activecraftcore.utils.ColorUtils;
import de.silencio.activecraftcore.utils.FileConfig;
import de.silencio.activecraftcore.utils.MessageUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

public class SendToDiscord {


    public static void sendLog(EmbedBuilder embedBuilder) {
        TextChannel channel = ActiveCraftDiscord.getPlugin().getEventLogChannel();
        if (channel != null) channel.sendMessageEmbeds(embedBuilder.build()).queue();
    }

    public static void sendChat(String message) {
        TextChannel channel = ActiveCraftDiscord.getPlugin().getChatChannel();
        message = ColorUtils.removeColorAndFormat(message);
        if (channel != null) channel.sendMessage(DiscordMessageUtils.stringToMention(message, channel.getGuild())).queue();
    }

    public static void sendToConsole(String message) {
        TextChannel channel = ActiveCraftDiscord.getPlugin().getConsoleChannel();
        message = ColorUtils.removeColorAndFormat(message);
        if (channel != null) channel.sendMessage(DiscordMessageUtils.stringToMention(message, channel.getGuild())).queue();
    }

    public static void sendStaffChat(String message) {
        TextChannel channel = ActiveCraftDiscord.getPlugin().getStaffChatChannel();
        message = ColorUtils.removeColorAndFormat(message);
        if (channel != null) channel.sendMessage(DiscordMessageUtils.stringToMention(message, channel.getGuild())).queue();
    }

    public static void sendStaffChatEmbed(EmbedBuilder embedBuilder) {
        TextChannel channel = ActiveCraftDiscord.getPlugin().getStaffChatChannel();
        if (channel != null) channel.sendMessageEmbeds(embedBuilder.build()).queue();
    }

    public static void sendChatEmbed(EmbedBuilder embedBuilder) {
        TextChannel channel = ActiveCraftDiscord.getPlugin().getChatChannel();
        if (channel != null) channel.sendMessageEmbeds(embedBuilder.build()).queue();
    }
    public static void sendSocialSpy(String message) {
        TextChannel channel = ActiveCraftDiscord.getPlugin().getSocialSpyChannel();
        if (channel != null) channel.sendMessage(message).queue();
    }

}
