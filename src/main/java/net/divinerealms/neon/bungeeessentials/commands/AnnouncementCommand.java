package net.divinerealms.neon.bungeeessentials.commands;

import net.divinerealms.neon.bungeeessentials.BungeeEssentials;
import net.divinerealms.neon.bungeeessentials.utilities.Config;
import net.divinerealms.neon.bungeeessentials.utilities.Messages;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.Title;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;

public class AnnouncementCommand extends Command {
    private Messages messages;

    public AnnouncementCommand(String name, Config configUtil) {
        super(name);
        this.messages = new Messages(configUtil);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        final BungeeEssentials plugin = BungeeEssentials.getInstance();
        final Config configUtil = new Config(plugin);

        Configuration config = configUtil.getConfig("config.yml");

        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer proxiedPlayer = (ProxiedPlayer) sender;

            if (config.getBoolean("Announcement.Enabled")) {
                if (args.length < 1 || args[0].equalsIgnoreCase("help")) {
                    if (!config.getBoolean("MessagingSystem.ActionBar"))
                        proxiedPlayer.sendMessage(new TextComponent(messages.getPrefix() + messages.getEmptyMessage()));
                    else proxiedPlayer.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(messages.getEmptyMessage()));
                } else sendAnnouncement(args, plugin);
            } else proxiedPlayer.sendMessage(new TextComponent(messages.getDisabled()));
        } else {
            if (config.getBoolean("Announcement.Enabled")) {
                if (args.length < 1 || args[0].equalsIgnoreCase("help")) {
                    System.out.println(messages.getPrefix() + messages.getEmptyMessage());
                } else sendAnnouncement(args, plugin);
            } else System.out.println(messages.getPrefix() + messages.getDisabled());
        }
    }

    private void sendAnnouncement(String[] args, BungeeEssentials plugin) {
        final Config configUtil = new Config(plugin);

        Configuration config = configUtil.getConfig("config.yml");
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < args.length; i++) {
            if (i > 0) stringBuilder.append(" ");

            stringBuilder.append(args[i]);
        }

        Title title = plugin.getProxy().createTitle();
        title.title(new TextComponent(messages.getTitle()));
        title.subTitle(new TextComponent(messages.getSubTitle().replace("%message%", stringBuilder.toString())));
        title.stay(config.getInt("Announcement.Duration"));

        for (ProxiedPlayer targetPlayer : plugin.getProxy().getPlayers()) {
            targetPlayer.sendTitle(title);
            targetPlayer.sendMessage(new TextComponent(messages.getChatMessage()
                    .replace("%message%", stringBuilder.toString())));
        }
    }
}
