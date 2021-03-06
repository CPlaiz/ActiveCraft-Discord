package de.cplaiz.activecraftdiscord.discord.commands;

import de.cplaiz.activecraftdiscord.discord.commands.types.ServerCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.text.DecimalFormat;
import java.util.concurrent.ConcurrentHashMap;

public class HugCommand implements ServerCommand {


    private ConcurrentHashMap<Long, Long> timestamps;

    public HugCommand() {
        this.timestamps = new ConcurrentHashMap<>();
    }


    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {

        long id = m.getIdLong();
        if(timestamps.containsKey(id)) {
            long time = timestamps.get(id);

            if((System.currentTimeMillis() - time) >= 30000) {
                this.timestamps.put(id, System.currentTimeMillis());
                send(m, channel, message);
            }
            else {
                DecimalFormat df = new DecimalFormat("0.00");
                channel.sendMessage("You've to wait " + df.format((30000.0d - (System.currentTimeMillis() - time))/1000.0d) + " more seconds").queue();
            }
        }
        else {
            this.timestamps.put(id, System.currentTimeMillis());
            send(m, channel, message);
        }
    }

    public void send(Member m, TextChannel channel, Message message) {
        channel.sendMessage(m.getAsMention() + " hugs himself.").queue();
    }

}
