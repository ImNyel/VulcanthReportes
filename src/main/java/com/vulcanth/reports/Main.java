package com.vulcanth.reports;

import com.vulcanth.reports.comandos.Commands;
import com.vulcanth.reports.database.Database;
import dev.vulcanth.pewd.plugin.VulcanthPlugin;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.HashSet;

public class Main extends VulcanthPlugin {
    private static Main instance;
    public static HashSet<ReportsUtil> getReportes(){
        return reportes;
    }
    public static HashMap<ItemStack, ReportsUtil> getItemStorage(){
        return item_storage;
    }
    private static HashSet<ReportsUtil> reportes = new HashSet<>();
    private static HashMap<ItemStack, ReportsUtil> item_storage = new HashMap<>();
    private static Inventory menu = Bukkit.createInventory(null, 54, "Reportes");
    public static Inventory getMenu(){
        return menu;
    }
    @Override
    public void start() {
        instance = this;
    }

    @Override
    public void load() {

    }


    @Override
    public void enable() {
        saveDefaultConfig();
        Commands.setupCommands();
        Database.setupDatabase();
        this.getLogger().info("§aPlugin ativo com sucesso.");
    }

    @Override
    public void disable() {

    }

    public static Main getInstance() {
        return instance;
    }
}
