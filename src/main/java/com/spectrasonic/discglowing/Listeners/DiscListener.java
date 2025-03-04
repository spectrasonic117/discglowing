package com.spectrasonic.discglowing.Listeners;

import com.spectrasonic.discglowing.Main;
import lombok.RequiredArgsConstructor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Set;

@RequiredArgsConstructor
public class DiscListener implements Listener {
    private static final Set<Material> MUSIC_DISCS = Set.of( 
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
    
    private final Main plugin;

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        
        if (!plugin.isFeatureEnabled() || player.getGameMode() != GameMode.ADVENTURE) return;

        ItemStack item = player.getInventory().getItemInMainHand();
        boolean hasDisc = MUSIC_DISCS.contains(item.getType());

        applyGlowingEffect(player, hasDisc);
    }

    private void applyGlowingEffect(Player player, boolean shouldGlow) {
        if (shouldGlow) {
            player.addPotionEffect(new PotionEffect(
                PotionEffectType.GLOWING,
                Integer.MAX_VALUE,
                1,
                false,
                false,
                true // Green Glowing for players that are holding a music disc
            ));
        } else {
            player.removePotionEffect(PotionEffectType.GLOWING);
        }
    }
}
