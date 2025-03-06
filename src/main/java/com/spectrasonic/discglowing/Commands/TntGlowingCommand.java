package com.spectrasonic.discglowing.Commands;

import org.bukkit.command.CommandSender;

import com.spectrasonic.discglowing.Managers.TntGlowingManager;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Subcommand;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.minimessage.MiniMessage;

@RequiredArgsConstructor
@CommandAlias("tntglowing|tg")
public class TntGlowingCommand extends BaseCommand {

    private final TntGlowingManager tntGlowingManager;
    private final MiniMessage miniMessage;

    @Subcommand("toggle")
    public void onToggle(CommandSender sender) {
        tntGlowingManager.toggle();
        String state = tntGlowingManager.isEnabled() ? "<green>activado" : "<red>desactivado";
        sender.sendMessage(miniMessage.deserialize("<yellow>La funcionalidad TntGlowing ahora está " + state + "."));
    }

    @Subcommand("clear")
    public void onClear(CommandSender sender) {
        int itemsRemoved = tntGlowingManager.clearAllGlowingAndItems();
        sender.sendMessage(miniMessage.deserialize("<green>Se han eliminado todos los efectos glowing y " + itemsRemoved + " TNT."));
    }
}