package de.cplaiz.activecraftdiscord.minecraft.listener;

import de.cplaiz.activecraftdiscord.ActiveCraftDiscord;
import de.cplaiz.activecraftdiscord.discord.SendToDiscord;
import de.silencio.activecraftcore.events.StaffChatMessageEvent;
import de.silencio.activecraftcore.utils.ColorUtils;
import de.silencio.activecraftcore.utils.FileConfig;
import de.silencio.activecraftcore.playermanagement.Profile;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class StaffChatListener implements Listener {

    @EventHandler
    public void onStaffChatMessage(StaffChatMessageEvent event) {

        CommandSender sender = event.getSender();
        FileConfig mainConfig = new FileConfig("config.yml", ActiveCraftDiscord.getPlugin());
        String finalMessage;
        if (sender instanceof Player) {
            Profile profile = new Profile((Player) sender);
            finalMessage = mainConfig.getString("minecraft-to-discord-format").replace("%displayname%", profile.getNickname()).replace("%playername%", sender.getName());
        } else finalMessage = mainConfig.getString("minecraft-to-discord-format").replace("%displayname%", sender.getName()).replace("%playername%", sender.getName());
        finalMessage = finalMessage.replace("%message%", event.getMessage());
        finalMessage = ColorUtils.removeColorAndFormat(finalMessage);
        SendToDiscord.sendStaffChat(finalMessage);
    }
}
