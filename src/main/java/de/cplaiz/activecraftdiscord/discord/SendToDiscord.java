package de.cplaiz.activecraftdiscord.discord;

import de.cplaiz.activecraftdiscord.ActiveCraftDiscord;
import de.cplaiz.activecraftdiscord.utils.DiscordMessageUtils;
import de.silencio.activecraftcore.utils.FileConfig;
import de.silencio.activecraftcore.utils.MessageUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

public class SendToDiscord {

    static FileConfig mainConfig = new FileConfig("config.yml",ActiveCraftDiscord.getPlugin());
    static String channelidstring = mainConfig.getString("chat-channelid");
    static String staffchatChannelIdString = mainConfig.getString("staffchat-channelid");
    static String socialSpyChannelIdString = mainConfig.getString("socialspy-channelid");
    static String consoleChannelIdString = mainConfig.getString("console-channelid");
    static Long tcid = Long.parseLong(channelidstring);
    static Long scid = Long.parseLong(staffchatChannelIdString);
    static Long coid = Long.parseLong(consoleChannelIdString);
    static Long ssid = Long.parseLong(socialSpyChannelIdString);

    public static void reload() {
        mainConfig = new FileConfig("config.yml",ActiveCraftDiscord.getPlugin());
        channelidstring = mainConfig.getString("chat-channelid");
        staffchatChannelIdString = mainConfig.getString("staffchat-channelid");
        socialSpyChannelIdString = mainConfig.getString("socialspy-channelid");
        consoleChannelIdString = mainConfig.getString("console-channelid");
        tcid = Long.parseLong(channelidstring);
        scid = Long.parseLong(staffchatChannelIdString);
        coid = Long.parseLong(consoleChannelIdString);
        ssid = Long.parseLong(socialSpyChannelIdString);
    }

    public static void sendChat(String message) {
        TextChannel channel = ActiveCraftDiscord.getPlugin().jda.getTextChannelById(tcid);
        message = MessageUtils.removeColorAndFormat(message);
        channel.sendMessage(DiscordMessageUtils.stringToMention(message, channel.getGuild())).queue();
    }

    public static void sendToConsole(String message) {
        TextChannel channel = ActiveCraftDiscord.getPlugin().jda.getTextChannelById(coid);
        message = MessageUtils.removeColorAndFormat(message);
        channel.sendMessage(DiscordMessageUtils.stringToMention(message, channel.getGuild())).queue();
    }

    public static void sendStaffChat(String message) {
        TextChannel channel = ActiveCraftDiscord.getPlugin().jda.getTextChannelById(scid);
        message = MessageUtils.removeColorAndFormat(message);
        channel.sendMessage(DiscordMessageUtils.stringToMention(message, channel.getGuild())).queue();
    }

    public static void sendStaffChatEmbed(EmbedBuilder embedBuilder) {
        TextChannel channel = ActiveCraftDiscord.getPlugin().jda.getTextChannelById(scid);
        channel.sendMessageEmbeds(embedBuilder.build()).queue();
    }

    public static void sendChatEmbed(EmbedBuilder embedBuilder) {
        TextChannel channel = ActiveCraftDiscord.getPlugin().jda.getTextChannelById(tcid);
        channel.sendMessageEmbeds(embedBuilder.build()).queue();
    }
    public static void sendSocialSpy(String message) {
        TextChannel channel = ActiveCraftDiscord.getPlugin().jda.getTextChannelById(ssid);
        channel.sendMessage(message).queue();
    }

}
