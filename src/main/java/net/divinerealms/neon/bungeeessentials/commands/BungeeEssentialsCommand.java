package net.divinerealms.neon.bungeeessentials.commands;

import net.divinerealms.neon.bungeeessentials.BungeeEssentials;
import net.divinerealms.neon.bungeeessentials.utilities.Config;
import net.divinerealms.neon.bungeeessentials.utilities.Messages;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;

@SuppressWarnings("deprecation")
public class BungeeEssentialsCommand extends Command {
    private Messages messages;

    public BungeeEssentialsCommand(String name, Config configUtil) {
        super(name);
        this.messages = new Messages(configUtil);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        final BungeeEssentials plugin = BungeeEssentials.getInstance();
        final Config configUtil = new Config(plugin);

        Configuration config = configUtil.getConfig("config.yml");

        if (sender instanceof ProxiedPlayer) {
            if (sender.hasPermission("bess.admin")) {
                if (args.length < 1 || args[0].equalsIgnoreCase("help")) {
                    sender.sendMessage(new TextComponent(messages.getHelp()
                            .replace("%author%", plugin.getDescription().getAuthor())
                            .replace("%version%", plugin.getDescription().getVersion())));
                } else if (args[0].equalsIgnoreCase("reload")) {
                    plugin.reload();
                    sender.sendMessage(new TextComponent(messages.getReload()));
                } else if (args[0].equalsIgnoreCase("info")) {
                    if (config.getBoolean("Info.Enabled")) {
                        if (args.length < 2) {
                            sender.sendMessage(new TextComponent(messages.getUnknownCommand()));
                        } else {
                            final ProxiedPlayer target = plugin.getProxy().getPlayer(args[1]);
                            if (plugin.getProxy().getPlayers().contains(target)) {
                                sender.sendMessage(new TextComponent(messages.getInfo()
                                        .replace("%username%", target.getName())
                                        .replace("%uuid%", target.getUniqueId().toString())
                                        .replace("%ip%", target.getAddress().getHostName())
                                        .replace("%server%", target.getServer().getInfo().getName())));
                            } else sender.sendMessage(new TextComponent(messages.getPlayerNotFound()));
                        }
                    } else sender.sendMessage(new TextComponent(messages.getDisabled()));
                } else sender.sendMessage(new TextComponent(messages.getUnknownCommand()));
            } else sender.sendMessage(new TextComponent(messages.getNoPerms()));
        } else {
            if (args.length < 1 || args[0].equalsIgnoreCase("help")) System.out.println(messages.getHelp()
                    .replace("%author%", plugin.getDescription().getAuthor())
                    .replace("%version%", plugin.getDescription().getVersion()));
            else if (args[0].equalsIgnoreCase("reload")) {
                plugin.reload();
                System.out.println(messages.getReload());
            } else if (args[0].equalsIgnoreCase("info")) {
                if (config.getBoolean("Info.Enabled")) {
                    if (args.length < 2) {
                        System.out.println(messages.getUnknownCommand());
                    } else {
                        final ProxiedPlayer target = plugin.getProxy().getPlayer(args[1]);
                        if (plugin.getProxy().getPlayers().contains(target)) {
                            System.out.println(messages.getInfo()
                                    .replace("%username%", target.getName())
                                    .replace("%uuid%", target.getUniqueId().toString())
                                    .replace("%ip%", target.getAddress().getHostName())
                                    .replace("%server%", target.getServer().getInfo().getName()));
                        } else System.out.println(messages.getPlayerNotFound());
                    }
                } else sender.sendMessage(new TextComponent(messages.getDisabled()));
            } else System.out.println(messages.getUnknownCommand());
        }
    }
}
