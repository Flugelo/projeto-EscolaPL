package me.flugel.escolapl;

import me.flugel.escolapl.comandos.comandosStaff;
import me.flugel.escolapl.files.Configuracoes;
import me.flugel.escolapl.listeners.Entrar;
import me.flugel.escolapl.listeners.SaveResolution;
import me.flugel.escolapl.msql.MYSQL;
import me.flugel.escolapl.objetos.*;
import me.flugel.escolapl.util.Cuboid;
import me.flugel.escolapl.util.utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Main extends JavaPlugin    {

    public static Main instance;

    private MYSQL mysql;
    private ManagerAlunos managerAlunos;
    private HashMap<String, Location> locationHashMap = new HashMap<>();
    private ManagerResolution managerResolution;
    private ManagerImage managerImage;
    private Configuracoes configuracoes;


    //Iniciador do servidor, tudo que é evento/comandos são registrado aqui
    @Override

    public void onEnable() {

        //Instancia da main
        instance = this;

        //metodo que salva a config.yml do plugin
        saveDefaultConfig();


        managerAlunos = new ManagerAlunos();
        managerResolution = new ManagerResolution();
        managerImage = new ManagerImage();
        try {
            configuracoes = new Configuracoes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        connectSQL();

        //Registrar os comandos do plugins:
        getCommand("staff").setExecutor(new comandosStaff());

        mkdir();

        Bukkit.getPluginManager().registerEvents(new Entrar(), this);
        Bukkit.getPluginManager().registerEvents(new SaveResolution(), this);

        try {
            addImage();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Imagens imagens : getManagerImage().getImagensList()) {
            System.out.println(imagens.getName());
        }

        pegarQuadros();


    }

    public static Main getInstance() {
        return instance;
    }


    public MYSQL getMysql() {
        return mysql;
    }

    public ManagerAlunos getManagerAlunos() {
        return managerAlunos;
    }

    public HashMap<String, Location> getLocationHashMap() {
        return locationHashMap;
    }

    public ManagerResolution getManagerResolution() {
        return managerResolution;
    }

    public ManagerImage getManagerImage() {
        return managerImage;
    }

    public Configuracoes getConfiguracoes() {
        return configuracoes;
    }

    public void connectSQL() {
        String user, senha, host, db;
        int porta = getConfig().getInt("Config.porta");
        user = getConfig().getString("Config.user");
        senha = getConfig().getString("Config.senha");
        host = getConfig().getString("Config.host");
        db = getConfig().getString("Config.dataBase");

        System.out.println(user + " " + senha + " " + db + " " + host);
        mysql = new MYSQL(user, senha, host, porta, db);
    }

    public void mkdir() {
        File arquivo = new File(getDataFolder() + "/Image");
        if (!arquivo.exists())
            arquivo.mkdir();
    }


    public ItemFrame isFrame(Location location) {
        for (Entity entity : location.getWorld().getEntities()) {
            if (entity != null && entity.getType() == EntityType.ITEM_FRAME) {
                if (entity.getLocation().getBlock().getLocation().equals(location.getBlock().getLocation()))
                    return (ItemFrame) entity;
            }
        }
        return null;
    }

    public void addImage() throws IOException {
        File file = new File(getDataFolder() + "/Image");
        for (File listFile : file.listFiles()) {
            new Imagens(listFile.getName());
        }
    }

    public  void pegarQuadros(){
        FileConfiguration config = getConfiguracoes().getFileConfiguration();
        for (String key : config.getKeys(false)) {
            Location loc = utils.unserialize(config.getString(key+".loc1"));
            Location loc2 = utils.unserialize(config.getString(key+".loc2"));

            Cuboid cuboid = new Cuboid(loc, loc2);
            new ResolutionSave(cuboid);

        }
    }
}
