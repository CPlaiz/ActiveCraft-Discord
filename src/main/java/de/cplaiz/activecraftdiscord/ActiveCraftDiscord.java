package de.cplaiz.activecraftdiscord;

import de.cplaiz.activecraftdiscord.discord.listener.CommandListener;
import de.cplaiz.activecraftdiscord.minecraft.listener.JoinQuitListener;
import de.cplaiz.activecraftdiscord.utils.Config;
import de.cplaiz.activecraftdiscord.utils.FileConfig;
import de.silencio.activecraftcore.messages.Errors;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;

public final class ActiveCraftDiscord extends JavaPlugin {

    private Config config;
    public static String PREFIX = ChatColor.GREEN + "ActiveCraftDiscord §f";

    public JDA jda = null;

    private CommandManager cmdMan;

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

        //check if core is enabled
        if (!Bukkit.getPluginManager().isPluginEnabled("ActiveCraft-Core")) {
            getLogger().severe("*** ActiveCraftCore is not installed or not enabled! ***");
            getLogger().severe("*** This plugin will be disabled! ***");
            this.setEnabled(false);
            return;
        }

        saveDefaultConfig();



        config = new Config("config.yml" , getDataFolder());

        String BotToken = getConfig().getString("bot-token");
        JDABuilder builder = JDABuilder.createDefault(BotToken);
        builder.setActivity(Activity.playing("mit deinem pepega"));

        this.cmdMan = new CommandManager();

        //discord listeners
        builder.addEventListeners(new CommandListener());

        try {
            jda = builder.build();
            jda.awaitReady();
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Discord Bot has started.");



        this.register();
        log("§7Plugin loaded");
    }

    @Override
    public void onDisable() {


        log("§7Plugin unloaded");
        //bot.shutdown();
    }

    public void register() {

        //listener
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new JoinQuitListener(), this);



        //commands



    }

    public void log(String text) {
        Bukkit.getConsoleSender().sendMessage(PREFIX + text);
    }

    public static ActiveCraftDiscord getPlugin() {
        return plugin;
    }

    public static ActiveCraftDiscord getINSTANCE() {
        return INSTANCE;
    }

    public CommandManager getCmdMan() {
        return cmdMan;
    }
}
