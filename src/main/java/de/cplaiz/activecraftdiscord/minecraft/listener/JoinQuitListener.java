package de.cplaiz.activecraftdiscord.minecraft.listener;

import de.cplaiz.activecraftdiscord.discord.SendToDiscord;
import de.silencio.activecraftcore.utils.MessageUtils;
import de.silencio.activecraftcore.utils.Profile;
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

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player p = event.getPlayer();
        Profile profile = new Profile(event.getPlayer());

        SendToDiscord sendToDiscord = new SendToDiscord();
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setAuthor(MessageUtils.removeColorAndFormat(profile.getNickname()) + " joined the server", null, "https://crafatar.com/avatars/" + p.getUniqueId());
        embedBuilder.setColor(Color.GREEN);
        embedBuilder.setTimestamp(OffsetDateTime.now());

        sendToDiscord.sendEmbed(embedBuilder);
    }

    @EventHandler (priority = EventPriority.LOW)
    public void onPlayerLeave(PlayerQuitEvent event) {

        Player p = event.getPlayer();
        Profile profile = new Profile(event.getPlayer());

        SendToDiscord sendToDiscord = new SendToDiscord();
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setAuthor(MessageUtils.removeColorAndFormat(profile.getNickname()) + " left the server", null, "https://crafatar.com/avatars/" + p.getUniqueId());
        embedBuilder.setColor(Color.RED);
        embedBuilder.setTimestamp(OffsetDateTime.now());
        if (!profile.isVanished()) {
            sendToDiscord.sendChatEmbed(embedBuilder);
        }
        logMinecraft.log(embedBuilder);

    }
}
