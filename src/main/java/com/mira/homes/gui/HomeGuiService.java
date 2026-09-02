package com.mira.homes.gui;

import net.ess3.api.IEssentials;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class HomeGuiService {
    private static final int MAX_ROWS = 6;
    private static final int MAX_HOMES_PER_PAGE = 28;

    private final JavaPlugin plugin;
    private final IEssentials essentials;
    private final NamespacedKey homeKey;

    public HomeGuiService(JavaPlugin plugin, IEssentials essentials) {
        this.plugin = plugin;
        this.essentials = essentials;
        this.homeKey = new NamespacedKey(plugin, "home_name");
    }

    public void open(Player player) {
        open(player, 0);
    }

    public void open(Player player, int page) {
        if (!player.hasPermission("mirahomes.use")) {
            player.sendMessage(Component.text("You do not have permission to use homes.", NamedTextColor.RED));
            return;
        }

        List<String> homes = homeNames(player);
        int maxPage = Math.max(0, (homes.size() - 1) / MAX_HOMES_PER_PAGE);
        int safePage = Math.max(0, Math.min(page, maxPage));
        int start = safePage * MAX_HOMES_PER_PAGE;
        int shown = Math.min(MAX_HOMES_PER_PAGE, Math.max(0, homes.size() - start));

        int rows = rowsFor(shown, maxPage > 0);
        int size = rows * 9;
        String title = plugin.getConfig().getString("gui.title", "Homes");
        Inventory inventory = Bukkit.createInventory(new Holder(safePage), size,
                Component.text(title, NamedTextColor.DARK_PURPLE));

        ItemStack filler = glowingFiller();
        for (int slot = 0; slot < size; slot++) inventory.setItem(slot, filler.clone());

        List<Integer> contentSlots = contentSlots(rows);
        for (int i = 0; i < shown && i < contentSlots.size(); i++) {
            String home = homes.get(start + i);
            inventory.setItem(contentSlots.get(i), homeItem(home));
        }

        int bottomStart = size - 9;
        if (safePage > 0) inventory.setItem(bottomStart + 1, control(Material.ARROW, "Previous Page", NamedTextColor.YELLOW));
        inventory.setItem(bottomStart + 4, control(configMaterial("gui.close-material", Material.BARRIER), "Close", NamedTextColor.RED));
        if (safePage < maxPage) inventory.setItem(bottomStart + 7, control(Material.ARROW, "Next Page", NamedTextColor.YELLOW));

        player.openInventory(inventory);
    }

    public String getHome(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return null;
        return item.getItemMeta().getPersistentDataContainer().get(homeKey, PersistentDataType.STRING);
    }

    public List<String> homeNames(Player player) {
        List<String> homes = new ArrayList<>(essentials.getUser(player).getHomes());
        homes.sort(String.CASE_INSENSITIVE_ORDER);
        return homes;
    }

    private int rowsFor(int count, boolean paged) {
        int contentRows = Math.max(1, (count + 6) / 7);
        int rows = contentRows + 2;
        if (paged) rows = MAX_ROWS;
        return Math.max(3, Math.min(MAX_ROWS, rows));
    }

    private List<Integer> contentSlots(int rows) {
        List<Integer> slots = new ArrayList<>();
        for (int row = 1; row < rows - 1; row++) {
            for (int col = 1; col <= 7; col++) slots.add(row * 9 + col);
        }
        return slots;
    }

    private ItemStack homeItem(String home) {
        ItemStack item = new ItemStack(configMaterial("gui.home-material", Material.WHITE_BED));
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(home, NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));
        meta.getPersistentDataContainer().set(homeKey, PersistentDataType.STRING, home);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack glowingFiller() {
        ItemStack item = new ItemStack(configMaterial("gui.filler-material", Material.GRAY_STAINED_GLASS_PANE));
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(" ").decoration(TextDecoration.ITALIC, false));
        meta.setEnchantmentGlintOverride(true);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack control(Material material, String name, NamedTextColor color) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(name, color).decoration(TextDecoration.ITALIC, false));
        item.setItemMeta(meta);
        return item;
    }

    private Material configMaterial(String path, Material fallback) {
        String configured = plugin.getConfig().getString(path, fallback.name());
        Material material = Material.matchMaterial(configured == null ? fallback.name() : configured);
        return material == null ? fallback : material;
    }

    public static final class Holder implements InventoryHolder {
        private final int page;
        public Holder(int page) { this.page = page; }
        public int page() { return page; }
        @Override public Inventory getInventory() { return null; }
    }
}
