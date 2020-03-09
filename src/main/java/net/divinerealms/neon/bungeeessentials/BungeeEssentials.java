package net.divinerealms.neon.bungeeessentials;

import net.divinerealms.neon.bungeeessentials.commands.AnnouncementCommand;
import net.divinerealms.neon.bungeeessentials.commands.BungeeEssentialsCommand;
import net.divinerealms.neon.bungeeessentials.commands.PingCommand;
import net.divinerealms.neon.bungeeessentials.listeners.CustomProtocol;
import net.divinerealms.neon.bungeeessentials.utilities.Config;
import net.divinerealms.neon.bungeeessentials.utilities.Messages;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class BungeeEssentials extends Plugin {
    private static BungeeEssentials plugin;

    public void onEnable() {
        plugin = this;

        Config configUtil = new Config(this);

        configUtil.createConfig("config.yml");
        configUtil.createConfig("messages.yml");

        PluginManager pluginManager = getProxy().getPluginManager();

        pluginManager.registerCommand(this, new AnnouncementCommand("announce", configUtil));
        pluginManager.registerCommand(this, new BungeeEssentialsCommand("bess", configUtil));
        pluginManager.registerCommand(this, new PingCommand("ping", configUtil));

        pluginManager.registerListener(this, new CustomProtocol());

        reload();
    }

    public void reload() {
        Config configUtil = new Config(this);
        Messages messages = new Messages(configUtil);

        messages.reload();

        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
            ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "messages.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (configUtil.getConfig("config.yml") == null) configUtil.createConfig("config.yml");
        else if (configUtil.getConfig("messages.yml") == null) configUtil.createConfig("messages.yml");

        getProxy().getPluginManager().unregisterListeners(this);
        getProxy().getPluginManager().unregisterCommands(this);

        PluginManager pluginManager = getProxy().getPluginManager();

        pluginManager.registerCommand(this, new AnnouncementCommand("announce", configUtil));
        pluginManager.registerCommand(this, new BungeeEssentialsCommand("bess", configUtil));

        pluginManager.registerListener(this, new CustomProtocol());
    }

    public static BungeeEssentials getInstance() {
        return plugin;
    }
}
