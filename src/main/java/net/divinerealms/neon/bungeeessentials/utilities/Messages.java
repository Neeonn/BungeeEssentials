package net.divinerealms.neon.bungeeessentials.utilities;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.config.Configuration;

public class Messages {
    private Config configUtil;
    private String prefix;
    private String reload;
    private String noPerms;
    private String unknownCommand;
    private String playerNotFound;
    private String help;
    private String info;
    private String disabled;
    private String emptyMessage;
    private String chatMessage;
    private String title;
    private String subtitle;
    private String pingSelf;
    private String pingOthers;

    public Messages(Config configUtil) {
        this.configUtil = configUtil;
        reload();
    }

    public void reload() {
        final Configuration messages = configUtil.getConfig("messages.yml");

        if (messages != null) {
            prefix = getString("Prefix");
            reload = getString("Reload");
            noPerms = getString("NoPerms");
            unknownCommand = getString("UnknownCommand");
            playerNotFound = getString("PlayerOffline");
            help = getString("Help");
            info = getString("Info.Message");
            disabled = getString("Disabled");
            emptyMessage = getString("Announcement.EmptyMessage");
            chatMessage = getString("Announcement.ChatMessage");
            title = getString("Announcement.Title");
            subtitle = getString("Announcement.SubTitle");
            pingSelf = getString("Ping.Self");
            pingOthers = getString("Ping.Other");
        }
    }

    public String getPrefix() {
        return prefix;
    }

    public String getReload() {
        return prefix + reload;
    }

    public String getNoPerms() {
        return prefix + noPerms;
    }

    public String getUnknownCommand() {
        return prefix + unknownCommand;
    }

    public String getPlayerNotFound() {
        return prefix + playerNotFound;
    }

    public String getHelp() {
        return help;
    }

    public String getInfo() {
        return info;
    }

    public String getDisabled() {
        return prefix + disabled;
    }

    public String getEmptyMessage() {
        return prefix + emptyMessage;
    }

    public String getChatMessage() {
        return chatMessage;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subtitle;
    }

    public String getPingSelf() {
        return prefix + pingSelf;
    }

    public String getPingOthers() {
        return prefix + pingOthers;
    }

    public String getString(String path) {
        return colorize(configUtil.getConfig("messages.yml").getString(path));
    }

    public String colorize(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
