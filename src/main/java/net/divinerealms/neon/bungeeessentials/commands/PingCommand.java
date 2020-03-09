package net.divinerealms.neon.bungeeessentials.commands;

import net.divinerealms.neon.bungeeessentials.BungeeEssentials;
import net.divinerealms.neon.bungeeessentials.utilities.Config;
import net.divinerealms.neon.bungeeessentials.utilities.Messages;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
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
            ProxiedPlayer proxiedPlayer = (ProxiedPlayer) sender;

            if (config.getBoolean("Ping.Enabled")) {
                if (args.length == 0) {
                    if (proxiedPlayer.hasPermission("bess.ping")) {
                        noPerm = messages.getNoPerms();
                        if (!config.getBoolean("MessagingSystem.ActionBar"))
                            proxiedPlayer.sendMessage(new TextComponent(messages.getPrefix() + noPerm));
                        else proxiedPlayer.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(noPerm));
                        return;
                    }

                    if (proxiedPlayer.getPing() < 60) {
                        if (!config.getBoolean("MessagingSystem.ActionBar"))
                            proxiedPlayer.sendMessage(new TextComponent(messages.getPrefix() + messages.getPingSelf().replace("%ping%", "" + proxiedPlayer.getPing()).replace("%color%", ChatColor.GREEN + "")));
                        else proxiedPlayer.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(messages.getPingSelf().replace("%ping%", "" + proxiedPlayer.getPing()).replace("%color%", ChatColor.GREEN + "")));
                    } else if (proxiedPlayer.getPing() < 120) {
                        if (!config.getBoolean("MessagingSystem.ActionBar"))
                            proxiedPlayer.sendMessage(new TextComponent(messages.getPrefix() + messages.getPingSelf().replace("%ping%", "" + proxiedPlayer.getPing()).replace("%color%", ChatColor.YELLOW + "")));
                        else proxiedPlayer.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(messages.getPingSelf().replace("%ping%", "" + proxiedPlayer.getPing()).replace("%color%", ChatColor.YELLOW + "")));
                    } else if (proxiedPlayer.getPing() < 300) {
                        if (!config.getBoolean("MessagingSystem.ActionBar"))
                            proxiedPlayer.sendMessage(new TextComponent(messages.getPrefix() + messages.getPingSelf().replace("%ping%", "" + proxiedPlayer.getPing()).replace("%color%", ChatColor.RED + "")));
                        else proxiedPlayer.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(messages.getPingSelf().replace("%ping%", "" + proxiedPlayer.getPing()).replace("%color%", ChatColor.RED + "")));
                    } else if (proxiedPlayer.getPing() > 300) {
                        if (!config.getBoolean("MessagingSystem.ActionBar"))
                            proxiedPlayer.sendMessage(new TextComponent(messages.getPrefix() + messages.getPingSelf().replace("%ping%", "" + proxiedPlayer.getPing()).replace("%color%", ChatColor.DARK_RED + "")));
                        else proxiedPlayer.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(messages.getPingSelf().replace("%ping%", "" + proxiedPlayer.getPing()).replace("%color%", ChatColor.DARK_RED + "")));
                    }
                } else {
                    if (proxiedPlayer.hasPermission("bcc.ping.others")) {
                        noPerm = messages.getPrefix() + messages.getNoPerms();
                        if (!config.getBoolean("MessagingSystem.ActionBar"))
                            proxiedPlayer.sendMessage(new TextComponent(noPerm));
                        else proxiedPlayer.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(messages.getNoPerms()));
                        return;
                    }

                    //noinspection ConstantConditions
                    noPerm = args.length > 0 ? args[0] : null;
                    ProxiedPlayer targetPlayer = plugin.getProxy().getPlayer(noPerm);

                    if (plugin.getProxy().getPlayers().contains(targetPlayer)) {
                        if (targetPlayer.getPing() < 60) {
                            if (!config.getBoolean("MessagingSystem.ActionBar"))
                                proxiedPlayer.sendMessage(new TextComponent(messages.getPrefix() + messages.getPingOthers().replace("%ping%", "" + targetPlayer.getPing()).replace("%player%", targetPlayer.getName()).replace("%color%", ChatColor.GREEN + "")));
                            else proxiedPlayer.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(messages.getPingOthers().replace("%ping%", "" + targetPlayer.getPing()).replace("%player%", targetPlayer.getName()).replace("%color%", ChatColor.GREEN + "")));
                        } else if (targetPlayer.getPing() < 120) {
                            if (!config.getBoolean("MessagingSystem.ActionBar"))
                                proxiedPlayer.sendMessage(new TextComponent(messages.getPrefix() + messages.getPingOthers().replace("%ping%", "" + targetPlayer.getPing()).replace("%player%", targetPlayer.getName()).replace("%color%", ChatColor.YELLOW + "")));
                            else proxiedPlayer.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(messages.getPingOthers().replace("%ping%", "" + targetPlayer.getPing()).replace("%player%", targetPlayer.getName()).replace("%color%", ChatColor.YELLOW + "")));
                        } else if (targetPlayer.getPing() < 300) {
                            if (!config.getBoolean("MessagingSystem.ActionBar"))
                                proxiedPlayer.sendMessage(new TextComponent(messages.getPrefix() + messages.getPingOthers().replace("%ping%", "" + targetPlayer.getPing()).replace("%player%", targetPlayer.getName()).replace("%color%", ChatColor.RED + "")));
                            else proxiedPlayer.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(messages.getPingOthers().replace("%ping%", "" + targetPlayer.getPing()).replace("%player%", targetPlayer.getName()).replace("%color%", ChatColor.RED + "")));
                        } else if (targetPlayer.getPing() > 300) {
                            if (!config.getBoolean("MessagingSystem.ActionBar"))
                                proxiedPlayer.sendMessage(new TextComponent(messages.getPingOthers().replace("%ping%", "" + targetPlayer.getPing()).replace("%player%", targetPlayer.getName()).replace("%color%", ChatColor.DARK_RED + "")));
                            else proxiedPlayer.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(messages.getPingOthers().replace("%ping%", "" + targetPlayer.getPing()).replace("%player%", targetPlayer.getName()).replace("%color%", ChatColor.DARK_RED + "")));
                        }
                    } else {
                        if (!config.getBoolean("MessagingSystem.ActionBar"))
                            proxiedPlayer.sendMessage(new TextComponent(messages.getPrefix() + messages.getPlayerNotFound()));
                        else proxiedPlayer.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(messages.getPlayerNotFound()));
                    }
                }
            } else {
                if (!config.getBoolean("MessagingSystem.ActionBar"))
                    proxiedPlayer.sendMessage(new TextComponent(messages.getPrefix() + messages.getDisabled()));
                else proxiedPlayer.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(messages.getDisabled()));
            }
        } else {
            if (config.getBoolean("Ping.Enabled")) {
                if (args.length == 0) System.out.println(messages.getPrefix() + messages.getUnknownCommand());
                else {
                    ProxiedPlayer targetPlayer = plugin.getProxy().getPlayer(args[0]);

                    if (plugin.getProxy().getPlayers().contains(targetPlayer)) {
                        if (targetPlayer.getPing() < 60)
                            System.out.println(messages.getPrefix() + messages.getPingOthers().replace("%ping%", "" + targetPlayer.getPing()).replace("%player%", targetPlayer.getName()).replace("%color%", ChatColor.GREEN + ""));
                        else if (targetPlayer.getPing() < 120)
                            System.out.println(messages.getPrefix() + messages.getPingOthers().replace("%ping%", "" + targetPlayer.getPing()).replace("%player%", targetPlayer.getName()).replace("%color%", ChatColor.YELLOW + ""));
                        else if (targetPlayer.getPing() < 300)
                            System.out.println(messages.getPrefix() + messages.getPingOthers().replace("%ping%", "" + targetPlayer.getPing()).replace("%player%", targetPlayer.getName()).replace("%color%", ChatColor.RED + ""));
                        else if (targetPlayer.getPing() > 300)
                            System.out.println(messages.getPrefix() + messages.getPingOthers().replace("%ping%", "" + targetPlayer.getPing()).replace("%player%", targetPlayer.getName()).replace("%color%", ChatColor.DARK_RED + ""));
                    } else System.out.println(messages.getPrefix() + messages.getPlayerNotFound());
                }
            } else System.out.println(messages.getPrefix() + messages.getDisabled());
        }
    }
}
