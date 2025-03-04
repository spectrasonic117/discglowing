package com.spectrasonic.discglowing.Commands;

import com.spectrasonic.discglowing.Main;
import com.spectrasonic.discglowing.Utils.MessageUtils;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Dependency;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

@CommandAlias("discglowing|dg")
@CommandPermission("discglowing.admin")
public class DiscGlowingCommand extends BaseCommand {

    @Dependency private Main plugin;

    @Subcommand("toggle")
    @Description("Activa/desactiva el efecto glowing")
    public void onToggle(Player player, boolean state) {
        plugin.setFeatureEnabled(state);
        
        String message = state ? 
            "<green>Efecto glowing activado" : 
            "<red>Efecto glowing desactivado";
        
        MessageUtils.sendMessage(player, message);
        
        if (!state) {
            plugin.getServer().getOnlinePlayers().forEach(p -> 
                p.removePotionEffect(PotionEffectType.GLOWING));
        }
    }
}