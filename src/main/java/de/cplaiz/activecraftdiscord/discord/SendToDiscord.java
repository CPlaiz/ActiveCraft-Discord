package de.cplaiz.activecraftdiscord.discord;

import de.cplaiz.activecraftdiscord.ActiveCraftDiscord;
import de.cplaiz.activecraftdiscord.utils.FileConfig;
import de.cplaiz.activecraftdiscord.utils.MessageCutter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.entity.Player;

public class SendToDiscord {

    FileConfig fileConfig = new FileConfig("config.yml");
    String channelidstring = fileConfig.getString("discord-chat-channelid");
    Long tcid = Long.parseLong(channelidstring);

    MessageCutter messageCutter = new MessageCutter();


    public void sendChat(String message, Player p) {
        TextChannel channel = ActiveCraftDiscord.getPlugin().jda.getTextChannelById(tcid);

        channel.sendMessage(messageCutter.removeColorAndFormat(p.getDisplayName()) + ": " + message).queue();
    }

    public void sendEmbed(EmbedBuilder embedBuilder) {
        TextChannel channel = ActiveCraftDiscord.getPlugin().jda.getTextChannelById(tcid);
        System.out.println(channelidstring);
        channel.sendMessageEmbeds(embedBuilder.build()).queue();
    }

}
