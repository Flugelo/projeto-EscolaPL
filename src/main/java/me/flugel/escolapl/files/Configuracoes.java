package me.flugel.escolapl.files;

import me.flugel.escolapl.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Configuracoes {
    private File file;
    private FileConfiguration fileConfiguration;

    public Configuracoes() throws IOException {
        this.file = new File(Main.getInstance().getDataFolder(),"configuracao.yml");
        if(!file.exists())
            file.createNewFile();

        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        this.fileConfiguration = config;
    }

    public void save() {
        try {
            this.fileConfiguration.save(this.file);
        }catch (Exception e){

        }
    }

    public FileConfiguration getFileConfiguration(){
        return this.fileConfiguration;
    }

}
