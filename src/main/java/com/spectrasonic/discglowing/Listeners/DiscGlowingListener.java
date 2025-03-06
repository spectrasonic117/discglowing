package com.spectrasonic.discglowing.Listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import java.util.Set;

import com.spectrasonic.discglowing.Managers.GlowingManager;

public class DiscGlowingListener implements Listener {

    private final GlowingManager glowingManager;
    private static final Set<Material> MUSIC_DISC_ITEMS = Set.of(
        Material.MUSIC_DISC_11,
        Material.MUSIC_DISC_13,
        Material.MUSIC_DISC_5,
        Material.MUSIC_DISC_BLOCKS,
        Material.MUSIC_DISC_CAT,
        Material.MUSIC_DISC_CHIRP,
        Material.MUSIC_DISC_CREATOR,
        Material.MUSIC_DISC_CREATOR_MUSIC_BOX,
        Material.MUSIC_DISC_FAR,
        Material.MUSIC_DISC_MALL,
        Material.MUSIC_DISC_MELLOHI,
        Material.MUSIC_DISC_OTHERSIDE,
        Material.MUSIC_DISC_PIGSTEP,
        Material.MUSIC_DISC_PRECIPICE,
        Material.MUSIC_DISC_RELIC,
        Material.MUSIC_DISC_STAL,
        Material.MUSIC_DISC_STRAD,
        Material.MUSIC_DISC_WAIT,
        Material.MUSIC_DISC_WARD
    );

    public DiscGlowingListener(GlowingManager glowingManager) {
        this.glowingManager = glowingManager;
    }

    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItem(event.getNewSlot());
        boolean shouldGlow = item != null && MUSIC_DISC_ITEMS.contains(item.getType());
        glowingManager.updatePlayerGlowing(player, shouldGlow);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        boolean shouldGlow = item != null && MUSIC_DISC_ITEMS.contains(item.getType());
        glowingManager.updatePlayerGlowing(player, shouldGlow);
    }
}
