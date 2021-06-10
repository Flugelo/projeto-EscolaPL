package me.flugel.escolapl;


import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class Teste extends MapRenderer {
    private BufferedImage image = null;

    private boolean first = true;

    private final int x;

    private final int y;

    private final double scale;

    public Teste(BufferedImage image, int x, int y, double scale) {
        this.x = x;
        this.y = y;
        this.scale = scale;
        recalculateInput(image);
    }

    public void recalculateInput(BufferedImage input) {
        if ((this.x * 128) > Math.round(input.getWidth() * this.scale) || (this.y * 128) > Math.round(input.getHeight() * this.scale))
            return;
        int x1 = (int)Math.round((this.x * 128) / this.scale);
        int y1 = (int)Math.round((this.y * 128) / this.scale);
        int x2 = (int)Math.round(Math.min(input.getWidth(), ((this.x + 1) * 128) / this.scale));
        int y2 = (int)Math.round(Math.min(input.getHeight(), ((this.y + 1) * 128) / this.scale));
        if (x2 - x1 <= 0 || y2 - y1 <= 0)
            return;
        this.image = input.getSubimage(x1, y1, x2 - x1, y2 - y1);
        if (this.scale != 1.0D) {
            BufferedImage resized = new BufferedImage(128, 128, (input.getType() == 0) ? this.image.getType() : input.getType());
            AffineTransform at = new AffineTransform();
            at.scale(this.scale, this.scale);
            AffineTransformOp scaleOp = new AffineTransformOp(at, 2);
            this.image = scaleOp.filter(this.image, resized);
        }
        this.first = true;
    }

    public void render(MapView view, MapCanvas canvas, Player player) {
        if (this.image != null && this.first) {
            canvas.drawImage(0, 0, this.image);
            this.first = false;
        }
    }
}

