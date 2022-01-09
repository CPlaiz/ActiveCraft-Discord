package de.cplaiz.activecraftdiscord.minecraft.listener;

import de.cplaiz.activecraftdiscord.ActiveCraftDiscord;
import de.cplaiz.activecraftdiscord.discord.SendToDiscord;
import de.silencio.activecraftcore.commands.SuicideCommand;
import de.silencio.activecraftcore.messages.CommandMessages;
import de.silencio.activecraftcore.playermanagement.Profile;
import de.silencio.activecraftcore.utils.ColorUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.Date;

public class DeathListener implements Listener {


    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerDeath(PlayerDeathEvent event) {

        Player player = event.getEntity();
        String cleanDeathMessage = event.getDeathMessage().substring(player.getName().length());

        if (SuicideCommand.getSuiciders().contains(player)) cleanDeathMessage = " committed suicide";

        cleanDeathMessage = ColorUtils.removeColorAndFormat(cleanDeathMessage);

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setAuthor(ColorUtils.removeColorAndFormat(player.getDisplayName()) + cleanDeathMessage, null, "https://crafatar.com/avatars/" + player.getUniqueId() + "?overlay=true");
        embedBuilder.setColor(Color.BLACK);
        embedBuilder.setTimestamp(OffsetDateTime.now());

        SendToDiscord.sendChatEmbed(embedBuilder);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss, dd/MM/yyyy z");
        SendToDiscord.sendLog("*[Death] [" + player.getName() + "] [" + sdf.format(new Date()) + "]* " + ColorUtils.removeColorAndFormat(new Profile(player).getNickname()) + cleanDeathMessage);
    }
}
