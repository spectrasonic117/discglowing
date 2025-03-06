package com.spectrasonic.discglowing;

import com.spectrasonic.discglowing.Utils.MessageUtils;
import org.bukkit.plugin.java.JavaPlugin;
import com.spectrasonic.discglowing.Managers.GlowingManager;
import com.spectrasonic.discglowing.Commands.DiscGlowingCommand;
import com.spectrasonic.discglowing.Listeners.DiscGlowingListener;
import co.aikar.commands.PaperCommandManager;
import net.kyori.adventure.text.minimessage.MiniMessage;

import com.spectrasonic.discglowing.Managers.TntGlowingManager;
import com.spectrasonic.discglowing.Commands.TntGlowingCommand;
import com.spectrasonic.discglowing.Listeners.TntGlowingListener;

public class Main extends JavaPlugin {

    private GlowingManager glowingManager;
    private MiniMessage miniMessage;
    private TntGlowingManager tntGlowingManager;

    @Override
    public void onEnable() {
        glowingManager = new GlowingManager();
        tntGlowingManager = new TntGlowingManager();
        miniMessage = MiniMessage.miniMessage();

        registerCommands();
        registerEvents();

        MessageUtils.sendStartupMessage(this);
    }

    @Override
    public void onDisable() {
        glowingManager.clearAllGlowing();
        tntGlowingManager.clearAllGlowing();
        MessageUtils.sendShutdownMessage(this);
    }


    public void registerCommands() {
        PaperCommandManager commandManager = new PaperCommandManager(this);
        commandManager.registerCommand(new DiscGlowingCommand(glowingManager, miniMessage));
        commandManager.registerCommand(new TntGlowingCommand(tntGlowingManager, miniMessage));
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new DiscGlowingListener(glowingManager), this);
        getServer().getPluginManager().registerEvents(new TntGlowingListener(tntGlowingManager), this);
    }
}
