package com.mira.homes.gui;

import com.mira.homes.particle.HomeParticleService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

public final class HomeGuiListener implements Listener {
    private final HomeGuiService gui;
    private final HomeParticleService particles;

    public HomeGuiListener(HomeGuiService gui, HomeParticleService particles) {
        this.gui = gui;
        this.particles = particles;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        if (!(event.getView().getTopInventory().getHolder() instanceof HomeGuiService.Holder holder)) return;

        event.setCancelled(true);
        int raw = event.getRawSlot();
        if (raw < 0 || raw >= event.getView().getTopInventory().getSize()) return;

        ItemStack clicked = event.getCurrentItem();
        String home = gui.getHome(clicked);
        if (home != null) {
            player.closeInventory();
            boolean accepted = Bukkit.dispatchCommand(player, "essentials:home " + home);
            if (accepted) particles.play(player);
            return;
        }

        int size = event.getView().getTopInventory().getSize();
        int bottomStart = size - 9;
        if (raw == bottomStart + 1 && holder.page() > 0) gui.open(player, holder.page() - 1);
        else if (raw == bottomStart + 4) player.closeInventory();
        else if (raw == bottomStart + 7) gui.open(player, holder.page() + 1);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDrag(InventoryDragEvent event) {
        if (event.getView().getTopInventory().getHolder() instanceof HomeGuiService.Holder) event.setCancelled(true);
    }
}
