package com.mira.homes;

import com.mira.homes.gui.HomeGuiListener;
import com.mira.homes.gui.HomeGuiService;
import com.mira.homes.listener.HomeCommandBridgeListener;
import com.mira.homes.particle.HomeParticleService;
import net.ess3.api.IEssentials;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class MiraHomesPlugin extends JavaPlugin {
    private static final LegacyComponentSerializer LEGACY = LegacyComponentSerializer.legacyAmpersand();
    private static final String CHAT_PREFIX = "&5&lMira &8>> &r";

    @Override
    public void onEnable() {
        saveDefaultConfig();

        Plugin essentialsPlugin = getServer().getPluginManager().getPlugin("Essentials");
        if (!(essentialsPlugin instanceof IEssentials essentials)) {
            getLogger().severe("EssentialsX 2.22.0+ is required. Disabling MiraHomes.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        HomeGuiService gui = new HomeGuiService(this, essentials);
        HomeParticleService particles = new HomeParticleService(this);

        getServer().getPluginManager().registerEvents(new HomeGuiListener(gui, particles), this);
        getServer().getPluginManager().registerEvents(new HomeCommandBridgeListener(gui), this);

        PluginCommand command = getCommand("mhomes");
        if (command != null) {
            command.setExecutor((sender, cmd, label, args) -> {
                if (!(sender instanceof org.bukkit.entity.Player player)) {
                    sender.sendMessage(LEGACY.deserialize(CHAT_PREFIX + "&cMiraHomes can only be opened by a player."));
                    return true;
                }
                gui.open(player);
                return true;
            });
        }

        getLogger().info("MiraHomes v" + getPluginMeta().getVersion() + " enabled.");
    }
}
