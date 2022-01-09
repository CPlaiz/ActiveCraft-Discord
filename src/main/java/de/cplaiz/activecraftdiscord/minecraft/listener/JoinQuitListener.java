package de.cplaiz.activecraftdiscord.minecraft.listener;

import de.cplaiz.activecraftdiscord.discord.SendToDiscord;
import de.silencio.activecraftcore.utils.ColorUtils;
import de.silencio.activecraftcore.playermanagement.Profile;
import net.dv8tion.jda.api.EmbedBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.Date;

public class JoinQuitListener implements Listener {

    @EventHandler (priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        Profile profile = new Profile(event.getPlayer());

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setAuthor(ColorUtils.removeColorAndFormat(profile.getNickname()) + " joined the server", null, "https://crafatar.com/avatars/" + player.getUniqueId() + "?overlay=true");
        embedBuilder.setColor(Color.GREEN);
        embedBuilder.setTimestamp(OffsetDateTime.now());
        if (!profile.isVanished()) SendToDiscord.sendChatEmbed(embedBuilder);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss, dd/MM/yyyy z");
        SendToDiscord.sendLog("*[Joined] [" + player.getName() + "] [" + sdf.format(new Date()) + "]* " + ColorUtils.removeColorAndFormat(new Profile(player).getNickname()) + " joined the server");
    }

    @EventHandler (priority = EventPriority.MONITOR)
    public void onPlayerLeave(PlayerQuitEvent event) {

        Player player = event.getPlayer();
        Profile profile = new Profile(event.getPlayer());

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setAuthor(ColorUtils.removeColorAndFormat(profile.getNickname()) + " left the server", null, "https://crafatar.com/avatars/" + player.getUniqueId() + "?overlay=true");
        embedBuilder.setColor(Color.RED);
        embedBuilder.setTimestamp(OffsetDateTime.now());
        if (!profile.isVanished()) SendToDiscord.sendChatEmbed(embedBuilder);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss, dd/MM/yyyy z");
        SendToDiscord.sendLog("*[Left] [" + player.getName() + "] [" + sdf.format(new Date()) + "]* " + ColorUtils.removeColorAndFormat(new Profile(player).getNickname()) + " left the server");
    }
}
