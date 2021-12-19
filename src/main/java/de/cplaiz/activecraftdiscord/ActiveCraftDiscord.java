package de.cplaiz.activecraftdiscord;

import de.cplaiz.activecraftdiscord.discord.SendToDiscord;
import de.cplaiz.activecraftdiscord.minecraft.listener.*;
import de.cplaiz.activecraftdiscord.discord.listener.CommandListener;
import de.cplaiz.activecraftdiscord.utils.ConsoleAppender;
import de.cplaiz.activecraftdiscord.utils.ConsoleQueueWorker;
import de.silencio.activecraftcore.messages.Errors;
import de.silencio.activecraftcore.utils.FileConfig;
import lombok.Getter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;

public final class ActiveCraftDiscord extends JavaPlugin {

    public static String PREFIX = ChatColor.GREEN + "ActiveCraft-Discord " + ChatColor.WHITE;

    public JDA jda = null;

    private TextChannel chatChannel;
    private TextChannel socialSpyChannel;
    private TextChannel eventLogChannel;
    private TextChannel staffChatChannel;
    private TextChannel consoleChannel;

    private boolean errorShutdown = false;

    FileConfig mainConfig;

    @Getter
    private final Deque<String> consoleMessageQueue = new LinkedList<>();

    ConsoleAppender consoleAppender;
    ConsoleQueueWorker consoleQueueWorker;

    private CommandManager cmdMan;
    private StaffChatListener staffChatListener;

    public static ActiveCraftDiscord INSTANCE;

    @Override
    public void onLoad() {
        INSTANCE = this;
        plugin = this;
    }

    public ActiveCraftDiscord() {
        INSTANCE = this;
        plugin = this;
    }

    private static ActiveCraftDiscord plugin;

    @Override
    public void onEnable() {

        mainConfig = new FileConfig("config.yml", ActiveCraftDiscord.getPlugin());

        //check if core is enabled
        if (!Bukkit.getPluginManager().isPluginEnabled("ActiveCraft-Core")) {
            getLogger().severe("*** ActiveCraft-Core is not installed or not enabled! ***");
            getLogger().severe("*** This plugin will be disabled! ***");
            this.setEnabled(false);
            return;
        } else if (!Bukkit.getPluginManager().getPlugin("ActiveCraft-Core").getDescription().getVersion().matches("[1-9]\\.[2-9]\\.[2-9]")) {
            getLogger().severe("*** The version of ActiveCraft-Core you are using is incompatible with ActiveCraft-Discord v1.0.1! ***");
            getLogger().severe("*** Please use ActiveCraft-Core v1.2.2 or higher! ***");
            getLogger().severe("*** This plugin will be disabled! ***");
            this.setEnabled(false);
            return;
        }

        saveDefaultConfig();

        String botToken = mainConfig.getString("bot-token");
        if (botToken == null || botToken.equals("")) {
            getLogger().severe("*** Could not start ActiveCraft-Discord. Empty Token ***");
            getLogger().severe("*** Please enter a token in 'ActiveCraft-Discord/config.yml' ***");
            getLogger().severe("*** This plugin will be disabled! ***");
            this.setEnabled(false);
            return;
        }
        String botStatus = mainConfig.getString("bot-status");
        JDABuilder builder = JDABuilder.createDefault(botToken);
        if (botStatus.contains("%playing%")) {
            builder.setActivity(Activity.playing(botStatus.replace("%playing%", "")));
        } else if (botStatus.contains("%streaming%")) {
            builder.setActivity(Activity.streaming(botStatus.replace("%streaming%", ""), "https://www.twitch.tv/cplaiz"));
        } else if (botStatus.contains("%listening%")) {
            builder.setActivity(Activity.listening(botStatus.replace("%listening%", "")));
        } else if (botStatus.contains("%watching%")) {
            builder.setActivity(Activity.watching(botStatus.replace("%watching%", "")));
        } else if (botStatus.contains("%competing%")) {
            builder.setActivity(Activity.competing(botStatus.replace("%competing%", "")));
        } else log(Errors.WARNING() + "The specified bot status is invalid!");

        this.cmdMan = new CommandManager();

        //discord listeners
        builder.addEventListeners(new CommandListener());
        builder.addEventListeners(new de.cplaiz.activecraftdiscord.discord.listener.ChatListener());

        try {
            jda = builder.build();
            jda.awaitReady();
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }

        if (invalidChannels()) return;

        consoleAppender = new ConsoleAppender();
        consoleQueueWorker = new ConsoleQueueWorker();

        getLogger().log(Level.INFO, "Discord Bot has started.");

        Metrics metrics = new Metrics(this, 12698);

        this.register();
        log("Plugin loaded");

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setAuthor("Server has started", null, "https://img.icons8.com/plasticine/452/play.png");
        embedBuilder.setColor(Color.GREEN);
        embedBuilder.setTimestamp(OffsetDateTime.now());

        SendToDiscord.sendChatEmbed(embedBuilder);
    }

    @Override
    public void onDisable() {
        if (jda != null) {

            if (!errorShutdown) {
                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setAuthor("Server has stopped", null, "https://img.icons8.com/plasticine/452/stop.png");
                embedBuilder.setColor(Color.RED);
                embedBuilder.setTimestamp(OffsetDateTime.now());

                SendToDiscord.sendChatEmbed(embedBuilder);
            }

            jda.shutdown();
            log("Discord Bot offline.");
        }
        log("Plugin unloaded");
    }

    public void register() {

        //listener
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new JoinQuitListener(), this);
        pluginManager.registerEvents(new ChatListener(), this);
        pluginManager.registerEvents(new DeathListener(), this);
        pluginManager.registerEvents(new AchievementListener(), this);
        pluginManager.registerEvents(new VanishListener(), this);
        pluginManager.registerEvents(new SocialSpyListener(), this);
        pluginManager.registerEvents(new StaffChatListener(), this);
    }

    public void log(String text) {
        getLogger().log(Level.INFO, text);
    }

    public static ActiveCraftDiscord getPlugin() {
        return plugin;
    }

    public static ActiveCraftDiscord getINSTANCE() {
        return INSTANCE;
    }

    public static void error(String message) {
        getPlugin().getLogger().severe(message);
    }

    public static void error(Throwable throwable) {
        logThrowable(throwable, ActiveCraftDiscord::error);
    }

    public static void error(String message, Throwable throwable) {
        error(message);
        error(throwable);
    }

    private static void logThrowable(Throwable throwable, Consumer<String> logger) {
        StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));

        for (String line : stringWriter.toString().split("\n")) logger.accept(line);
    }


    public CommandManager getCmdMan() {
        return cmdMan;
    }

    public TextChannel getChatChannel() {
        return chatChannel;
    }

    public void setChatChannel(TextChannel chatChannel) {
        this.chatChannel = chatChannel;
    }

    public TextChannel getSocialSpyChannel() {
        return socialSpyChannel;
    }

    public void setSocialSpyChannel(TextChannel socialSpyChannel) {
        this.socialSpyChannel = socialSpyChannel;
    }

    public TextChannel getEventLogChannel() {
        return eventLogChannel;
    }

    public void setEventLogChannel(TextChannel eventLogChannel) {
        this.eventLogChannel = eventLogChannel;
    }

    public TextChannel getStaffChatChannel() {
        return staffChatChannel;
    }

    public void setStaffChatChannel(TextChannel staffChatChannel) {
        this.staffChatChannel = staffChatChannel;
    }

    public TextChannel getConsoleChannel() {
        return consoleChannel;
    }

    public void setConsoleChannel(TextChannel consoleChannel) {
        this.consoleChannel = consoleChannel;
    }

    private boolean invalidChannels() {
        boolean invalid = false;
        List<String> cause = new ArrayList<>();
        try {
            chatChannel = jda.getTextChannelById(Long.parseLong(mainConfig.getString("chat-channelid")));
            if (chatChannel == null) {
                cause.add("chat-channelid");
                invalid = true;
                errorShutdown = true;
                getLogger().severe("*** Could not start ActiveCraft-Discord. Invalid chat-channelid ***");
                getLogger().severe("*** Please check the chat-channelid in 'ActiveCraft-Discord/config.yml' ***");
                getLogger().severe("*** This plugin will be disabled! ***");
                this.setEnabled(false);
            }
        } catch (NumberFormatException e) {
            invalid = true;
            errorShutdown = true;
            getLogger().severe("*** Could not start ActiveCraft-Discord. Invalid chat-channelid ***");
            getLogger().severe("*** Please check the chat-channelid in 'ActiveCraft-Discord/config.yml' ***");
            getLogger().severe("*** This plugin will be disabled! ***");
            this.setEnabled(false);
        }
        try {
            eventLogChannel = jda.getTextChannelById(Long.parseLong(mainConfig.getString("event-log-channelid")));
            if (eventLogChannel == null) cause.add("console-channelid");
        } catch (NumberFormatException e) {
            cause.add("event-log-channelid");
        }
        try{
            socialSpyChannel = jda.getTextChannelById(Long.parseLong(mainConfig.getString("socialspy-channelid")));
            if (socialSpyChannel == null) cause.add("console-channelid");
        } catch (NumberFormatException e) {
            cause.add("socialspy-channelid");
        }
        try {
            staffChatChannel = jda.getTextChannelById(Long.parseLong(mainConfig.getString("staffchat-channelid")));
            if (staffChatChannel == null) cause.add("console-channelid");
        } catch (NumberFormatException e) {
            cause.add("staffchat-channelid");
        }
        try {
            consoleChannel = jda.getTextChannelById(Long.parseLong(mainConfig.getString("console-channelid")));
            if (consoleChannel == null) cause.add("console-channelid");
        } catch (NumberFormatException e) {
            cause.add("console-channelid");
        }

        if (cause.size() != 0) {
            for (String s : cause)
            getLogger().log(Level.WARNING, "The \"" + s + "\" in \"ActiveCraft-Discord/config.yml\" is invalid or empty!");
        }

        return invalid;
    }
}