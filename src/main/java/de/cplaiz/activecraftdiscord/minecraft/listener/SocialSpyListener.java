package de.cplaiz.activecraftdiscord.minecraft.listener;

import de.cplaiz.activecraftdiscord.discord.SendToDiscord;
import de.silencio.activecraftcore.events.MsgEvent;
import de.silencio.activecraftcore.utils.ColorUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SocialSpyListener implements Listener {

    @EventHandler
    public void onMsg(MsgEvent event) {
        CommandSender sender = event.getSender();
        Player target = event.getTarget();
        String message = event.getMessage();
        String finalMessage;
        if (sender instanceof Player) {
            finalMessage = "**[" + ColorUtils.removeColorAndFormat(((Player)sender).getDisplayName()) + " -> " + ColorUtils.removeColorAndFormat(target.getDisplayName()) + "]** " + message;
        } else finalMessage = sender.getName() + " -> " + ColorUtils.removeColorAndFormat(target.getDisplayName()) + "] " + ColorUtils.removeColorAndFormat(message);
        SendToDiscord.sendSocialSpy(finalMessage);
    }
}
