package com.spectrasonic.discglowing.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;

import com.spectrasonic.discglowing.Managers.GlowingManager;
import com.spectrasonic.discglowing.Utils.MessageUtils;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Subcommand;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.minimessage.MiniMessage;

@RequiredArgsConstructor
@CommandAlias("discglowing|dg")
public class DiscGlowingCommand extends BaseCommand {

    private final GlowingManager glowingManager;
    private final MiniMessage miniMessage;

    @Subcommand("toggle")
    public void onToggle(CommandSender sender) {
        glowingManager.toggle();
        String state = glowingManager.isEnabled() ? "<green>activado" : "<red>desactivado";
        sender.sendMessage(miniMessage.deserialize("<yellow>La funcionalidad DiscGlowing ahora está " + state + "."));
    }

    @Subcommand("clear")
    public void onClear(CommandSender sender) {
        int itemsRemoved = glowingManager.clearAllGlowingAndItems();
        sender.sendMessage(miniMessage.deserialize("<green>Se han eliminado todos los efectos glowing y " + itemsRemoved + " discos de música."));
    }
}