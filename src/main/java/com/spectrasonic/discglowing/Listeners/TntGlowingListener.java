package com.spectrasonic.discglowing.Listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import com.spectrasonic.discglowing.Managers.TntGlowingManager;

public class TntGlowingListener implements Listener {

    private final TntGlowingManager tntGlowingManager;
    private static final Material TNT_ITEM = Material.TNT;

    public TntGlowingListener(TntGlowingManager tntGlowingManager) {
        this.tntGlowingManager = tntGlowingManager;
    }

    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItem(event.getNewSlot());
        boolean shouldGlow = item != null && item.getType() == TNT_ITEM;
        tntGlowingManager.updatePlayerGlowing(player, shouldGlow);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        boolean shouldGlow = item != null && item.getType() == TNT_ITEM;
        tntGlowingManager.updatePlayerGlowing(player, shouldGlow);
    }
}