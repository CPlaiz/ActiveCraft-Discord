package de.cplaiz.activecraftdiscord.minecraft.listener;

import de.cplaiz.activecraftdiscord.discord.SendToDiscord;
import de.cplaiz.activecraftdiscord.utils.MessageCutter;
import net.dv8tion.jda.api.EmbedBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.awt.*;
import java.time.OffsetDateTime;

public class JoinQuitListener implements Listener {

    MessageCutter messageCutter = new MessageCutter();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player p = event.getPlayer();

        SendToDiscord sendToDiscord = new SendToDiscord();
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setAuthor(messageCutter.removeColorAndFormat(p.getDisplayName()) + " joined the server", null, "https://crafatar.com/avatars/" + p.getUniqueId());
        embedBuilder.setColor(Color.GREEN);
        embedBuilder.setTimestamp(OffsetDateTime.now());

        sendToDiscord.sendEmbed(embedBuilder);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {

        Player p = event.getPlayer();

        SendToDiscord sendToDiscord = new SendToDiscord();
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setAuthor(messageCutter.removeColorAndFormat(p.getDisplayName()) + " left the server", null, "https://crafatar.com/avatars/" + p.getUniqueId());
        embedBuilder.setColor(Color.RED);
        embedBuilder.setTimestamp(OffsetDateTime.now());

        sendToDiscord.sendEmbed(embedBuilder);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {


        Player p = event.getEntity();

        SendToDiscord sendToDiscord = new SendToDiscord();
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setAuthor(messageCutter.removeColorAndFormat(p.getDisplayName()) + event.getDeathMessage(), null, "https://crafatar.com/avatars/" + p.getUniqueId());
        embedBuilder.setColor(Color.BLACK);
        embedBuilder.setTimestamp(OffsetDateTime.now());

        sendToDiscord.sendEmbed(embedBuilder);

    }

}
