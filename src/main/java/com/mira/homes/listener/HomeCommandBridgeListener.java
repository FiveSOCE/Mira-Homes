package com.mira.homes.listener;

import com.mira.homes.gui.HomeGuiService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Locale;

public final class HomeCommandBridgeListener implements Listener {
    private final HomeGuiService gui;

    public HomeCommandBridgeListener(HomeGuiService gui) {
        this.gui = gui;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        String raw = event.getMessage().trim();
        String normalized = raw.toLowerCase(Locale.ROOT);
        if (!normalized.equals("/home") && !normalized.equals("/homes")) return;

        Player player = event.getPlayer();
        event.setCancelled(true);
        gui.open(player);
    }
}
