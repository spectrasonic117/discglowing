package com.spectrasonic.discglowing.Managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.Set;

public class GlowingManager {

    private boolean enabled = true;
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
    private final String TEAM_NAME = "discglowing_green";
    private Team glowingTeam;

    public GlowingManager() {
        setupTeam();
    }

    private void setupTeam() {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getMainScoreboard();
        
        // Remove existing team if it exists
        if (scoreboard.getTeam(TEAM_NAME) != null) {
            scoreboard.getTeam(TEAM_NAME).unregister();
        }
        
        // Create new team
        glowingTeam = scoreboard.registerNewTeam(TEAM_NAME);
        glowingTeam.setColor(org.bukkit.ChatColor.GREEN);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void toggle() {
        enabled = !enabled;
        
        // Update all players when toggling
        for (Player player : Bukkit.getOnlinePlayers()) {
            ItemStack item = player.getInventory().getItemInMainHand();
            boolean shouldGlow = item != null && MUSIC_DISC_ITEMS.contains(item.getType());
            updatePlayerGlowing(player, shouldGlow);
        }
    }

    public void clearAllGlowing() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setGlowing(false);
            glowingTeam.removeEntry(player.getName());
        }
    }
    
    public int clearAllGlowingAndItems() {
        int itemsRemoved = 0;
        
        for (Player player : Bukkit.getOnlinePlayers()) {
            // Remove glowing effect
            player.setGlowing(false);
            glowingTeam.removeEntry(player.getName());
            
            // Remove music disc items
            ItemStack[] contents = player.getInventory().getContents();
            for (int i = 0; i < contents.length; i++) {
                ItemStack item = contents[i];
                if (item != null && MUSIC_DISC_ITEMS.contains(item.getType())) {
                    player.getInventory().setItem(i, null);
                    itemsRemoved++;
                }
            }
        }
        
        return itemsRemoved;
    }

    public void updatePlayerGlowing(Player player, boolean shouldGlow) {
        if (!enabled) {
            player.setGlowing(false);
            glowingTeam.removeEntry(player.getName());
            return;
        }
        
        if (shouldGlow) {
            // Add player to team for green color
            glowingTeam.addEntry(player.getName());
            player.setGlowing(true);
        } else {
            player.setGlowing(false);
            glowingTeam.removeEntry(player.getName());
        }
    }
}