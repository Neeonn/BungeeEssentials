package net.divinerealms.neon.bungeeessentials.commands;

import net.divinerealms.neon.bungeeessentials.BungeeEssentials;
import net.divinerealms.neon.bungeeessentials.utilities.Config;
import net.divinerealms.neon.bungeeessentials.utilities.Messages;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;

public class PingCommand extends Command {
    private Messages messages;
    
    public PingCommand(String name, Config configUtil) {
        super(name);
        this.messages = new Messages(configUtil);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        final BungeeEssentials plugin = BungeeEssentials.getInstance();
        final Config configUtil = new Config(plugin);

        String noPerm;
        Configuration config = configUtil.getConfig("config.yml");

        if (sender instanceof ProxiedPlayer) {
            if (config.getBoolean("Ping.Enabled")) {
                if (args.length == 0) {
                    if (sender.hasPermission("bess.ping")) {
                        noPerm = messages.getNoPerms();
                        sender.sendMessage(new TextComponent(noPerm));
                        return;
                    }

                    if (((ProxiedPlayer) sender).getPing() < 60) {
                        sender.sendMessage(new TextComponent(messages.getPingSelf().replace("%ping%", "" + ((ProxiedPlayer) sender).getPing()).replace("%color%", ChatColor.GREEN + "")));
                    } else if (((ProxiedPlayer) sender).getPing() < 120) {
                        sender.sendMessage(new TextComponent(messages.getPingSelf().replace("%ping%", "" + ((ProxiedPlayer) sender).getPing()).replace("%color%", ChatColor.YELLOW + "")));
                    } else if (((ProxiedPlayer) sender).getPing() < 300) {
                        sender.sendMessage(new TextComponent(messages.getPingSelf().replace("%ping%", "" + ((ProxiedPlayer) sender).getPing()).replace("%color%", ChatColor.RED + "")));
                    } else if (((ProxiedPlayer) sender).getPing() > 300) {
                        sender.sendMessage(new TextComponent(messages.getPingSelf().replace("%ping%", "" + ((ProxiedPlayer) sender).getPing()).replace("%color%", ChatColor.DARK_RED + "")));
                    }
                } else {
                    if (sender.hasPermission("bcc.ping.others")) {
                        noPerm = messages.getNoPerms();
                        sender.sendMessage(new TextComponent(noPerm));
                        return;
                    }

                    //noinspection ConstantConditions
                    noPerm = args.length > 0 ? args[0] : null;
                    ProxiedPlayer targetPlayer = plugin.getProxy().getPlayer(noPerm);

                    if (plugin.getProxy().getPlayers().contains(targetPlayer)) {
                        if (targetPlayer.getPing() < 60) {
                            sender.sendMessage(new TextComponent(messages.getPingOthers().replace("%ping%", "" + targetPlayer.getPing()).replace("%player%", targetPlayer.getName()).replace("%color%", ChatColor.GREEN + "")));
                        } else if (targetPlayer.getPing() < 120) {
                            sender.sendMessage(new TextComponent(messages.getPingOthers().replace("%ping%", "" + targetPlayer.getPing()).replace("%player%", targetPlayer.getName()).replace("%color%", ChatColor.YELLOW + "")));
                        } else if (targetPlayer.getPing() < 300) {
                            sender.sendMessage(new TextComponent(messages.getPingOthers().replace("%ping%", "" + targetPlayer.getPing()).replace("%player%", targetPlayer.getName()).replace("%color%", ChatColor.RED + "")));
                        } else if (targetPlayer.getPing() > 300) {
                            sender.sendMessage(new TextComponent(messages.getPingOthers().replace("%ping%", "" + targetPlayer.getPing()).replace("%player%", targetPlayer.getName()).replace("%color%", ChatColor.DARK_RED + "")));
                        }
                    } else sender.sendMessage(new TextComponent(messages.getPlayerNotFound()));
                }
            } else sender.sendMessage(new TextComponent(messages.getDisabled()));
        } else {
            if (config.getBoolean("Ping.Enabled")) {
                if (args.length == 0) System.out.println(messages.getUnknownCommand());
                else {
                    ProxiedPlayer targetPlayer = plugin.getProxy().getPlayer(args[0]);

                    if (plugin.getProxy().getPlayers().contains(targetPlayer)) {
                        if (targetPlayer.getPing() < 60)
                            System.out.println(messages.getPingOthers().replace("%ping%", "" + targetPlayer.getPing()).replace("%player%", targetPlayer.getName()).replace("%color%", ChatColor.GREEN + ""));
                        else if (targetPlayer.getPing() < 120)
                            System.out.println(messages.getPingOthers().replace("%ping%", "" + targetPlayer.getPing()).replace("%player%", targetPlayer.getName()).replace("%color%", ChatColor.YELLOW + ""));
                        else if (targetPlayer.getPing() < 300)
                            System.out.println(messages.getPingOthers().replace("%ping%", "" + targetPlayer.getPing()).replace("%player%", targetPlayer.getName()).replace("%color%", ChatColor.RED + ""));
                        else if (targetPlayer.getPing() > 300)
                            System.out.println(messages.getPingOthers().replace("%ping%", "" + targetPlayer.getPing()).replace("%player%", targetPlayer.getName()).replace("%color%", ChatColor.DARK_RED + ""));
                    } else System.out.println(messages.getPlayerNotFound());
                }
            } else System.out.println(messages.getDisabled());
        }
    }
}
