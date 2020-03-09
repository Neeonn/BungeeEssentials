package net.divinerealms.neon.bungeeessentials.listeners;

import net.divinerealms.neon.bungeeessentials.BungeeEssentials;
import net.divinerealms.neon.bungeeessentials.utilities.Config;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.protocol.ProtocolConstants;

import java.util.Collections;

public class CustomProtocol implements Listener {

    @EventHandler
    public void onPing(ProxyPingEvent proxyPingEvent) {
        BungeeEssentials plugin = BungeeEssentials.getInstance();
        Config configUtil = new Config(plugin);

        Configuration config = configUtil.getConfig("config.yml");

        if (config.getBoolean("Custom_Protocol.Enabled")) {
            if (proxyPingEvent.getConnection().getVersion() > Collections.max(ProtocolConstants.SUPPORTED_VERSION_IDS)
                    || Collections.min(ProtocolConstants.SUPPORTED_VERSION_IDS) > proxyPingEvent.getConnection().getVersion()
                    || ProtocolConstants.MINECRAFT_1_8 > proxyPingEvent.getConnection().getVersion()
                    || proxyPingEvent.getConnection().getVersion() == ProtocolConstants.MINECRAFT_1_15) {
                proxyPingEvent.getResponse().setVersion(new ServerPing.Protocol(config.getString("Custom_Protocol.String"),
                        proxyPingEvent.getConnection().getVersion()));
            }
        }
    }
}
