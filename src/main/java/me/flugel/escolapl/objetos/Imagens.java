package me.flugel.escolapl.objetos;

import me.flugel.escolapl.Main;
import me.flugel.escolapl.MapStorage;
import me.flugel.escolapl.RenderedMap;
import me.flugel.escolapl.rendering.ImageRenderer;
import me.flugel.escolapl.util.ImageTools;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Imagens {
    private String name;
    private List<ItemStack> imagens;

    public Imagens(String name) throws IOException {
        this.imagens = new ArrayList<>();
        this.name = name;
        BufferedImage image = null;
        List<BufferedImage> bufferedImages = new ArrayList<>();

        List<ItemStack> peneira = new ArrayList<>();
        File file = new File(Main.getInstance().getDataFolder()+"/Image/" + name);

        image = ImageIO.read(file);
        bufferedImages.addAll(ImageTools.divideIntoMapSizedParts(image, false));

        for (BufferedImage bufferedImage : bufferedImages) {
            ImageRenderer renderer = ImageRenderer.create(bufferedImage);
            RenderedMap renderedMap = RenderedMap.create(renderer);
            peneira.add(renderedMap.createItemStack());


        }

        this.imagens.addAll(peneira);
        Main.getInstance().getManagerImage().getImagensList().add(this);
    }

    public String getName() {
        return name;
    }

    public List<ItemStack> getImagens() {
        return imagens;
    }
}
