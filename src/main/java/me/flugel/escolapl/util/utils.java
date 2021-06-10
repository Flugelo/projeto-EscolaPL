package me.flugel.escolapl.util;


import me.flugel.escolapl.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class utils {

    public static void addAlunoMySQL(String player, String nome) {
        try {
            Main.getInstance().getMysql().openConnection();
            Connection connection = Main.getInstance().getMysql().getConnection();

            PreparedStatement stm = connection.prepareCall("INSERT INTO alunos VALUES ('" + player + "', '"+nome+"', '0', '0', '0', '0')");
            stm.execute();
            Main.getInstance().getMysql().closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static boolean containsAlunoSQL(String player){
        PreparedStatement stm = null;
        String tabela = Main.getInstance().getConfig().getString("Config.dataBase");
        try {
            Connection connection = Main.getInstance().getMysql().getConnection();
            stm = connection.prepareStatement("SELECT * FROM `"+tabela+"` WHERE `Jogador` = ?");
            stm.setString(1, player);
            ResultSet rs = stm.executeQuery();
            return rs.next();
        }catch (SQLException e){
            e.printStackTrace();
           return false;
        }
    }

    public static File getImage(String arquivo){
        File image = new File(Main.getInstance().getDataFolder()+ "/Image/" + arquivo);
        if(image.exists())
            return image;

        return null;
    }

    public static void createMapRender(File file, Player player){
        try {

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static String serialize(Location loc) {
        return loc.getWorld().getName() + ":" + loc.getX() + ":" + loc.getY() + ":" + loc.getZ() + ":" + loc.getYaw() + ":" + loc.getPitch();
    }

    public static Location unserialize(String location) {
        String[] parts = location.split(":");

        org.bukkit.World w = Bukkit.getServer().getWorld(parts[0]);
        double x = Double.parseDouble(parts[1]);
        double y = Double.parseDouble(parts[2]);
        double z = Double.parseDouble(parts[3]);
        float yaw = Float.parseFloat(parts[4]);
        float pitch = Float.parseFloat(parts[5]);
        return new Location((org.bukkit.World) w, x, y, z, yaw, pitch);
    }
}
