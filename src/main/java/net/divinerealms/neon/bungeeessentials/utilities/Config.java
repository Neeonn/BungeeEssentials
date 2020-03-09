package net.divinerealms.neon.bungeeessentials.utilities;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class Config {
    final private Plugin plugin;

    public Config(final Plugin plugin) {
        this.plugin = plugin;
    }

    public Configuration getConfig(String file) {
        try {
            return ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(plugin.getDataFolder(), file));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void createConfig(String file) {
        try {
            final File config = new File(plugin.getDataFolder(), file);

            if (!config.exists()) {
                final String[] files = file.split("/");
                final InputStream inputStream = plugin.getClass().getClassLoader().getResourceAsStream(files[files.length - 1]);

                if (config.getParentFile() != null) config.getParentFile().mkdirs();

                if (inputStream != null) {
                    Files.copy(inputStream, config.toPath());
                } else config.createNewFile();
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

}