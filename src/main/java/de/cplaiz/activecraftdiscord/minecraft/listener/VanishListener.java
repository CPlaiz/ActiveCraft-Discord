package de.cplaiz.activecraftdiscord.minecraft.listener;

import de.cplaiz.activecraftdiscord.ActiveCraftDiscord;
import de.cplaiz.activecraftdiscord.discord.SendToDiscord;
import de.silencio.activecraftcore.events.PlayerUnvanishEvent;
import de.silencio.activecraftcore.events.PlayerVanishEvent;
import de.silencio.activecraftcore.utils.ColorUtils;
import de.silencio.activecraftcore.playermanagement.Profile;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.awt.*;
import java.time.OffsetDateTime;

public class VanishListener implements Listener {

    @EventHandler
    public void onVanish(PlayerVanishEvent event) {

        if (ActiveCraftDiscord.getJda().getStatus() != JDA.Status.CONNECTED) return;

        Player player = event.getPlayer();
        Profile profile = new Profile(player);
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setAuthor(ColorUtils.removeColorAndFormat(profile.getNickname()) + " left the server", null, "https://crafatar.com/avatars/" + player.getUniqueId());
        embedBuilder.setColor(Color.RED);
        embedBuilder.setTimestamp(OffsetDateTime.now());

        SendToDiscord.sendChatEmbed(embedBuilder);
    }

    @EventHandler
    public void onUnvanish(PlayerUnvanishEvent event) {

        Player player = event.getPlayer();
        Profile profile = new Profile(player);
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setAuthor(ColorUtils.removeColorAndFormat(profile.getNickname()) + " joined the server", null, "https://crafatar.com/avatars/" + player.getUniqueId());
        embedBuilder.setColor(Color.GREEN);
        embedBuilder.setTimestamp(OffsetDateTime.now());

        SendToDiscord.sendChatEmbed(embedBuilder);
    }
}
