package de.cplaiz.activecraftdiscord.utils;

import de.cplaiz.activecraftdiscord.ActiveCraftDiscord;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.events.role.update.RoleUpdateNameEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateNameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class DiscordMessageUtils {

    private static Map<String, Pattern> mentionPatternCache = new HashMap<>();
    static {
        // event listener to clear the cache of invalid patterns because of name changes
        if (ActiveCraftDiscord.getJda() != null) {
            ActiveCraftDiscord.getJda().addEventListener(new ListenerAdapter() {
                @Override
                public void onUserUpdateName(UserUpdateNameEvent event) {
                    mentionPatternCache.remove(event.getUser().getId());
                }
                @Override
                public void onGuildMemberUpdateNickname(GuildMemberUpdateNicknameEvent event) {
                    mentionPatternCache.remove(event.getMember().getId());
                }
                @Override
                public void onRoleUpdateName(RoleUpdateNameEvent event) {
                    mentionPatternCache.remove(event.getRole().getId());
                }
            });
        }
    }

    public static String stringToMention(String message, Guild guild) {
        if (!message.contains("@")) return message;

        Map<Pattern, String> patterns = new HashMap<>();
        for (Role role : guild.getRoles()) {
            Pattern pattern = mentionPatternCache.computeIfAbsent(
                    role.getId(),
                    mentionable -> Pattern.compile(
                            "(?<!<)" +
                                    Pattern.quote("@" + role.getName()),
                            Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE
                    )
            );
            patterns.put(pattern, role.getAsMention());
        }

        for (Member member : guild.getMembers()) {
            Pattern pattern = mentionPatternCache.computeIfAbsent(
                    member.getId(),
                    mentionable -> Pattern.compile(
                            "(?<!<)" +
                                    Pattern.quote("@" + member.getEffectiveName()),
                            Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE
                    )
            );
            patterns.put(pattern, member.getAsMention());
        }

        for (Map.Entry<Pattern, String> entry : patterns.entrySet()) {
            message = entry.getKey().matcher(message).replaceAll(entry.getValue());
        }

        return message;
    }
}
