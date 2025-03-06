package com.spectrasonic.discglowing.Managers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class TntGlowingManager {

    private boolean enabled = true;
    private static final Material TNT_ITEM = Material.TNT;
    private final String TNT_TEAM_NAME = "tntglowing_red";
    private Team glowingTeam;

    public TntGlowingManager() {
        setupTeam();
    }

    private void setupTeam() {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getMainScoreboard();
        
        // Remove existing team if it exists
        if (scoreboard.getTeam(TNT_TEAM_NAME) != null) {
            scoreboard.getTeam(TNT_TEAM_NAME).unregister();
        }
        
        // Create new team with red color
        glowingTeam = scoreboard.registerNewTeam(TNT_TEAM_NAME);
        glowingTeam.setColor(org.bukkit.ChatColor.RED);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void toggle() {
        enabled = !enabled;
        
        // Update all players when toggling
        for (Player player : Bukkit.getOnlinePlayers()) {
            ItemStack item = player.getInventory().getItemInMainHand();
            boolean shouldGlow = item != null && item.getType() == TNT_ITEM;
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
            
            // Remove TNT items
            ItemStack[] contents = player.getInventory().getContents();
            for (int i = 0; i < contents.length; i++) {
                ItemStack item = contents[i];
                if (item != null && item.getType() == TNT_ITEM) {
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
        
        // Primero, asegurarse de que el jugador no esté en ningún otro equipo de glowing
        // Esto evita conflictos con GlowingManager
        if (Bukkit.getScoreboardManager().getMainScoreboard().getEntryTeam(player.getName()) != null && 
            !Bukkit.getScoreboardManager().getMainScoreboard().getEntryTeam(player.getName()).getName().equals(TNT_TEAM_NAME)) {
            // Si el jugador está en otro equipo y no debería brillar con TNT, no hacemos nada
            if (!shouldGlow) {
                return;
            }
            // Si debería brillar, lo quitamos del otro equipo primero
            Bukkit.getScoreboardManager().getMainScoreboard().getEntryTeam(player.getName()).removeEntry(player.getName());
        }
        
        if (shouldGlow) {
            // Add player to team for red color
            glowingTeam.addEntry(player.getName());
            player.setGlowing(true);
        } else {
            // Solo quitamos el glowing si el jugador está en nuestro equipo
            if (Bukkit.getScoreboardManager().getMainScoreboard().getEntryTeam(player.getName()) != null &&
                Bukkit.getScoreboardManager().getMainScoreboard().getEntryTeam(player.getName()).getName().equals(TNT_TEAM_NAME)) {
                player.setGlowing(false);
                glowingTeam.removeEntry(player.getName());
            }
        }
    }
}