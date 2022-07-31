package com.vulcanth.reports.comandos.cmds;

import com.vulcanth.reports.Main;
import com.vulcanth.reports.comandos.Commands;
import com.vulcanth.reports.database.Database;
import com.vulcanth.reports.database.MySQL;
import com.vulcanth.reports.utils.implementscode.ReportDao;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Getter
public class ReportesCommand extends Commands {


    public ReportesCommand() {
        super("reports");
    }

    public static ReportDao reportDao;
    String id;
    @Override
    public void perform(CommandSender sender, String label, String[] args) {

    }

    @Override
    public boolean execute(CommandSender sender, String commandlabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem utilizar este comando.");
        }
        Player player = (Player) sender;
        if (!player.hasPermission("vulcanth.cmd.reports")) {
            player.sendMessage("§cSomente Ajudante ou superior podem executar este comando.");
        }
        if (args.length != 0 && args.length != 1 && args.length <= 2 ) {
            if(args.length == 2) {
                if (args[0].equalsIgnoreCase("limpar")) {
                    if (!player.hasPermission("role.coord")) {
                        player.sendMessage("§cSomente Coordenador ou superior podem executar este comando.");
                        return true;
                    }
                    String nick = args[1];
                    if (!MySQL.checarExistePlayer(nick)) {
                        player.sendMessage("§cEste usuário não existe.");
                        return true;
                    }
                    player.sendMessage("\n§eVocê limpou o histórico de reports do jogador " + args[1] + ".\n");
                    Database.getInstance().execute("DELETE FROM VulcanthReports WHERE id = ?", String.valueOf(id));
                }
                return true;
            } else {
                return false;
            }
        } {
            Inventory inv = Bukkit.createInventory((InventoryHolder)null, 54, "§8Reports");
            p.openInventory(inv);

            try {
                PreparedStatement ps = Database.getInstance().getConnection().prepareStatement("SELECT * FROM Reports VulcanthReports BY Reports.Total DESC;");
                ResultSet rs = ps.executeQuery();

                while(rs.next()) {
                    String name = rs.getString("Nome");
                    String nick = rs.getString("Nick");
                    HashMap<String, String> map = new HashMap();
                    map.put(name, nick);
                    Iterator iterator = map.keySet().iterator();

                    while(iterator.hasNext()) {
                        String jogadores = (String)iterator.next();
                        int min = Integer.valueOf(Main.getInstance().getConfig().getString("Config.Minimo_Para_Aparecer"));
                        int total = Manager.getReportPlayer(jogadores, "Total");
                        if (total >= min && Bukkit.getPlayer(jogadores) != null) {
                            String nomereal = Bukkit.getPlayer(jogadores).getName();
                            ItemStack skull = new ItemStack(Material.SKULL_ITEM);
                            skull.setDurability((short)3);
                            SkullMeta sm = (SkullMeta)skull.getItemMeta();
                            sm.setOwner(nomereal);
                            sm.setDisplayName("§7" + nomereal);
                            List<String> l = new ArrayList();
                            l.add("§1");
                            l.add("§7 • §7Aimbot: §f" + Manager.getReportPlayer(jogadores, "Aimbot"));
                            l.add("§7 • §7Auto Armor: §f" + Manager.getReportPlayer(jogadores, "AutoArmor"));
                            l.add("§7 • §7Chest Finder: §f" + Manager.getReportPlayer(jogadores, "ChestFinder"));
                            l.add("§7 • §7Cliente Alternativo: §f" + Manager.getReportPlayer(jogadores, "ClienteAlternativo"));
                            l.add("§7 • §7Critical: §f" + Manager.getReportPlayer(jogadores, "Critical"));
                            l.add("§7 • §7Fast Place: §f" + Manager.getReportPlayer(jogadores, "FastPlace"));
                            l.add("§7 • §7Fly: §f" + Manager.getReportPlayer(jogadores, "Fly"));
                            l.add("§7 • §7Force Field: §f" + Manager.getReportPlayer(jogadores, "ForceField"));
                            l.add("§7 • §7No Fall: §f" + Manager.getReportPlayer(jogadores, "NoFall"));
                            l.add("§7 • §7NoSlow: §f" + Manager.getReportPlayer(jogadores, "NoSlow"));
                            l.add("§7 • §7Regen: §f" + Manager.getReportPlayer(jogadores, "Regen"));
                            l.add("§7 • §7Repulsão: §f" + Manager.getReportPlayer(jogadores, "Repulsao"));
                            l.add("§7 • §7Speed: §f" + Manager.getReportPlayer(jogadores, "Speed"));
                            l.add("§7 • §7Wall: §f" + Manager.getReportPlayer(jogadores, "Wall"));
                            l.add("§7 • §7XRay: §f" + Manager.getReportPlayer(jogadores, "XRay"));
                            l.add("§2");
                            l.add("§7Total: §f" + total);
                            l.remove("§7 • §7Aimbot: §f0");
                            l.remove("§7 • §7Aliança: §f0");
                            l.remove("§7 • §7Auto Armor: §f0");
                            l.remove("§7 • §7Chest Finder: §f0");
                            l.remove("§7 • §7Cliente Alternativo: §f0");
                            l.remove("§7 • §7Critical: §f0");
                            l.remove("§7 • §7Fast Place: §f0");
                            l.remove("§7 • §7Fly: §f0");
                            l.remove("§7 • §7Force Field: §f0");
                            l.remove("§7 • §7No Fall: §f0");
                            l.remove("§7 • §7NoSlow: §f0");
                            l.remove("§7 • §7Regen: §f0");
                            l.remove("§7 • §7Repulsão: §f0");
                            l.remove("§7 • §7Speed: §f0");
                            l.remove("§7 • §7Wall: §f0");
                            l.remove("§7 • §7XRay: §f0");
                            sm.setLore(l);
                            skull.setItemMeta(sm);
                            inv.addItem(new ItemStack[]{skull});
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return true;
        }
            sender.sendMessage("§eBuscando reports...\n§aSucesso! Abrindo inventário...");
            player.openInventory();
        }
        return true;
    }

}
