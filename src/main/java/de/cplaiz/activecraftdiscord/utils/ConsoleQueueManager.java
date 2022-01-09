package de.cplaiz.activecraftdiscord.utils;

import de.cplaiz.activecraftdiscord.ActiveCraftDiscord;
import de.cplaiz.activecraftdiscord.discord.SendToDiscord;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class ConsoleQueueManager extends Thread {

    private final Deque<String> queue = ActiveCraftDiscord.getPlugin().getConsoleMessageQueue();

    public ConsoleQueueManager() {
        super("ActiveCraft-Discord - Console Queue Manager");
    }

    @Override
    public void run() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(ActiveCraftDiscord.getPlugin(), () -> {
            StringBuilder stringBuilder = new StringBuilder();
            if (!queue.isEmpty() && ActiveCraftDiscord.getJda().getStatus() == JDA.Status.CONNECTED) {
                while (!queue.isEmpty()) {
                    if (queue.peek() == null) break;
                    if (stringBuilder.toString().length() + queue.peek().length() < Message.MAX_CONTENT_LENGTH) {
                        stringBuilder.append(queue.poll()).append("\n");
                    } else break;
                }
                if (!stringBuilder.toString().equals("")) SendToDiscord.sendToConsole(stringBuilder.toString());
            }
        }, 0, 20 * 2);
    }
}
