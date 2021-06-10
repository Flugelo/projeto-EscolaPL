package me.flugel.escolapl.comandos;

import me.flugel.escolapl.Main;
import me.flugel.escolapl.objetos.ResolutionSave;
import me.flugel.escolapl.util.utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class comandosStaff implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {

        if (!(sender instanceof Player))
            return true;

        Player p = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("staff")) {
            if (p.hasPermission("staff.user")) {
                if (args.length == 0) {
                    p.sendMessage("");
                    p.sendMessage("§fComandos para staffers");
                    p.sendMessage("");
                    p.sendMessage("§7Usar: §f/staff addAluno <Nick do jogador>");
                    p.sendMessage("§7Usar: §f/staff delAluno <Nick do jogador>");
                    p.sendMessage("§7Usar: §f/staff addNota <Nick do jogador>");
//                    File file = new File(Main.getInstance().getDataFolder() + "/image/download.png");
//                    try {
//                        BufferedImage image = ImageIO.read(file);
//                        Teste teste = new Teste(image, image.getWidth(), image.getHeight(), 1.0);
//
//                        Block block = p.getLocation().add(0,-1,0).getBlock();
//                        ImagePlaceEvent event= new ImagePlaceEvent(p, block, BlockFace.WEST, BlockFace.WEST, )
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }

                }
                if (args.length == 1)
                    switch (args[0].toLowerCase(Locale.ROOT)) {
                        case "addaluno":
                            p.sendMessage("§7Usar: §f/staff addAluno <Nick do jogador>");
                            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                            break;

                        case "delaluno":
                            p.sendMessage("§7Usar: §f/staff delAluno <Nick do jogador> <Nome completo do aluno>");
                            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                            break;

                        case "addnota":
                            p.sendMessage("§7Usar: §f/staff addNota <Nick do jogador>");
                            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                            break;
                        case "setimage":
                            p.sendMessage("§7Usar: §f/staff setimage <nome da imagem com a extensão do arquivo>");
                            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                            break;
                        case "setquadro":
                            ItemStack item = new ItemStack(Material.STICK);
                            ItemMeta meta = item.getItemMeta();
                            meta.setDisplayName("§eItem para setar as Resoluções");
                            meta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
                            ArrayList<String> lore = new ArrayList<>();
                            lore.add("");
                            lore.add("§6Clique em uma extremidade com o direito para salvar");
                            lore.add("§6Clique em outra extremidade com o esquerdo para salvar");
                            lore.add("");

                            meta.setLore(lore);
                            item.setItemMeta(meta);

                            p.getInventory().addItem(item);
                            break;


                        default:
                            p.sendMessage("§cEsse comando não existe, utilize o comando /staff para ver as opções ");
                            break;
                    }
                if (args.length > 2) {
                    switch (args[0].toLowerCase(Locale.ROOT)) {
                        case "addaluno":
                            Player target = Bukkit.getPlayerExact(args[1]);
                            if (target != null) {
                                Main.getInstance().getMysql().openConnection();
                                if (utils.containsAlunoSQL(target.getName()) == false) {
                                    p.sendMessage("§aAdicioando o aluno na DB...");
                                    try {
                                        utils.addAlunoMySQL(target.getName(), append(args).replace(args[0], "").replace(args[1], ""));
                                        p.sendMessage("§aAdicionado com sucesso. Jogador: §2" + target.getName() + " §aNome:" + append(args).replace(args[0], "").replace(args[1], ""));
                                        Main.getInstance().getMysql().closeConnection();
                                    } catch (Exception e) {
                                        p.sendMessage("§cErro ao adicionar o aluno na DB");
                                    }
                                } else {
                                    p.sendMessage("§cAluno ja contem um ficha registrada no servidor");
                                    Main.getInstance().getMysql().closeConnection();
                                }
                            } else {
                                p.sendMessage("§cEsse aluno não existe no servidor");
                                p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                            }
                            break;

                        case "delaluno":
                            p.sendMessage("§7Usar: §f/staff delAluno <Nick do jogador>");
                            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                            break;

                        case "addnota":
                            p.sendMessage("§7Usar: §f/staff addNota <Nick do jogador>");
                            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                            break;

                        case "setimage":
                            p.sendMessage("§eColocando imagem...");
                            try {
                                setImage(args[1], p);
                                p.playSound(p.getLocation(), Sound.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, 1, 1);
                            } catch (Exception e) {
                                e.printStackTrace();
                                p.sendMessage("§cErro ao colocar a imagem");
                            }
                        default:
                            p.sendMessage("§cEsse comando não existe, utilize o comando /staff para ver as opções ");
                            break;
                    }
                }
            }
        }
        return false;
    }

    public String append(String[] args) {
        StringBuilder builder = new StringBuilder();
        for (String arg : args) {
            builder.append(arg).append(" ");
        }
        return builder.toString();
    }

    public void setImage(String arquivo, Player player) {
        List<ItemStack> itens = Main.getInstance().getManagerImage().getByImages(arquivo).getImagens();

        if (Main.getInstance().getManagerResolution().getResolution(itens.size()) != null) {
            ResolutionSave resolution = Main.getInstance().getManagerResolution().getResolution(itens.size());
            int Contador = 0;
            for (Block block : resolution.getCuboid().blockList()) {
                if (Main.getInstance().isFrame(block.getLocation()) != null) {
                    ItemFrame frame = Main.getInstance().isFrame(block.getLocation());
                    int finalContador = Contador;
                    Contador++;

                    Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
                        frame.setItem(itens.get(finalContador));
                        MapView map = Bukkit.getMap(((MapMeta) itens.get(finalContador).getItemMeta()).getMapId());
                        MapInitializeEvent event = new MapInitializeEvent(map);
                        Bukkit.getPluginManager().callEvent(event);

                    });
                }
            }
        } else {
            player.sendMessage("§cEssa imagem precisa de exatamente §e" + itens.size() + " §cquadros para ser colocada.");
        }
    }
}
