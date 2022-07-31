package com.vulcanth.reports.bungee.comandos;

import com.vulcanth.reports.bungee.Bungee;
import com.vulcanth.reports.bungee.comandos.cmds.ReportarCommand;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;


public abstract class Commands extends Command {

    public Commands(String name, String... aliases) {
        super(name, null, aliases);
        ProxyServer.getInstance().getPluginManager().registerCommand(Bungee.getInstance(), this);
    }

    public static void setupCommands() {
        new ReportarCommand();
    }
}
