package com.spectrasonic.discglowing;

import co.aikar.commands.PaperCommandManager;
import com.spectrasonic.discglowing.Commands.DiscGlowingCommand;
import com.spectrasonic.discglowing.Listeners.DiscListener;
import com.spectrasonic.discglowing.Utils.MessageUtils;
import org.bukkit.plugin.java.JavaPlugin;
import lombok.Getter;

@Getter
public final class Main extends JavaPlugin {
    
    private boolean featureEnabled = true;

    @Override
    public void onEnable() {
        registerCommands();
        registerEvents();
        MessageUtils.sendStartupMessage(this);

    }

    @Override
    public void onDisable() {
        MessageUtils.sendShutdownMessage(this);
    }

    public void registerCommands() {
        PaperCommandManager commandManager = new PaperCommandManager(this);
        commandManager.registerCommand(new DiscGlowingCommand());
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new DiscListener(this), this);
    }

    public void setFeatureEnabled(boolean state) {
        this.featureEnabled = state;
    }
}
