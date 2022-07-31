package com.vulcanth.reports.bungee;

import com.vulcanth.reports.bungee.comandos.Commands;
import com.vulcanth.reports.bungee.database.Database;
import com.vulcanth.reports.utils.implementscode.ReportDao;
import com.vulcanth.reports.utils.implementscode.ReportThread;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
@Getter
public class Bungee extends Plugin {
    private Configuration config;
    private ReportDao reportDao;
    private ReportThread reportThread;
    private static Bungee instance;


    @Override
    public void onEnable() {
        saveDefaultConfig();
        Commands.setupCommands();
        Database.setupDatabase();
        reportThread = new ReportThread();

        reportDao = new ReportDao();
        reportDao.loadReports();

        this.getLogger().info("§aEste plugin foi ativo com sucesso");
    }

    public void saveDefaultConfig() {
        for (String fileName : new String[] {"config"}) {
            File file = new File("plugins/VulcanthReports/" + fileName + ".yml");
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                copyFile(Bungee.getInstance().getResourceAsStream(fileName + ".yml"), file);
            }

            try {
                this.config = YamlConfiguration.getProvider(YamlConfiguration.class).load(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
            } catch (IOException ex) {
                this.getLogger().log(Level.WARNING, "Cannot load " + fileName + ".yml: ", ex);
            }
        }
    }


    @Override
    public void onDisable() {
        reportThread.shutdown();
        this.getLogger().info("§cDisativado com sucesso, um bom dia.");
    }

    /**
     * Copia um arquivo a partir de um InputStream.
     *
     * @param input O input para ser copiado.
     * @param out   O arquivo destinario.
     */
    public static void copyFile(InputStream input, File out) {
        FileOutputStream ou = null;
        try {
            ou = new FileOutputStream(out);
            byte[] buff = new byte[1024];
            int len;
            while ((len = input.read(buff)) > 0) {
                ou.write(buff, 0, len);
            }
        } catch (IOException ex) {
            Bungee.getInstance().getLogger().log(Level.WARNING, "Failed at copy file " + out.getName() + "!", ex);
        } finally {
            try {
                if (ou != null) {
                    ou.close();
                }
                if (input != null) {
                    input.close();
                }
            } catch (IOException ignore) {}
        }
    }
    public Configuration getConfig(){
        return config;
    }
    public static Bungee getInstance() {
        return instance;
    }
}
