package de.cplaiz.activecraftdiscord.minecraft.listener;

import de.cplaiz.activecraftdiscord.ActiveCraftDiscord;
import de.cplaiz.activecraftdiscord.discord.SendToDiscord;
import de.silencio.activecraftcore.utils.ColorUtils;
import de.silencio.activecraftcore.utils.FileConfig;
import de.silencio.activecraftcore.playermanagement.Profile;
import net.dv8tion.jda.api.JDA;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChatMessage(AsyncPlayerChatEvent event) {

        String message = event.getMessage();
        Player player = event.getPlayer();
        Profile profile = new Profile(player);
        if (profile.isMuted() || profile.isDefaultmuted()) return;

        FileConfig mainConfig = new FileConfig("config.yml", ActiveCraftDiscord.getPlugin());
        String finalMessage = mainConfig.getString("minecraft-to-discord-format").replace("%displayname%", profile.getNickname()).replace("%playername%", player.getName());
        finalMessage = finalMessage.replace("%message%", message);
        finalMessage = ColorUtils.replaceColor(finalMessage);
        finalMessage = ColorUtils.replaceFormat(finalMessage);
        finalMessage = ColorUtils.removeColorAndFormat(finalMessage);
        SendToDiscord.sendChat(finalMessage);
    }
}