package de.cplaiz.activecraftdiscord.utils;

import de.cplaiz.activecraftdiscord.ActiveCraftDiscord;
import de.silencio.activecraftcore.utils.ColorUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.util.Date;

@Plugin(name = "ActiveCraft-Discord-ConsoleChannel", category = "Core", elementType = "appender", printObject = true)
public class ConsoleAppender extends AbstractAppender {

    public ConsoleAppender() {
        super("ActiveCraft-Discord-ConsoleChannel", null, PatternLayout.createDefaultLayout(), true);

        Logger rootLogger = (Logger) LogManager.getRootLogger();
        start();
        rootLogger.addAppender(this);
    }

    public void shutdown() {
        Logger rootLogger = (Logger) LogManager.getRootLogger();
        rootLogger.removeAppender(this);
    }

    @Override
    public void append(LogEvent event) {

        final String eventLevel = event.getLevel().name().toUpperCase();

        String line = event.getMessage().getFormattedMessage();

        line = ColorUtils.removeColorAndFormat(line);

        if (line.equals("")) return;

        line = line.trim();
        String finalLine = "*[" + new Date() + ", " + eventLevel + "]*  " + line;

        ActiveCraftDiscord.getPlugin().getConsoleMessageQueue().add(finalLine);
    }
}
