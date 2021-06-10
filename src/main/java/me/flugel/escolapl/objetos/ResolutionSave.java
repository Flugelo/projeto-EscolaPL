package me.flugel.escolapl.objetos;

import me.flugel.escolapl.Main;
import me.flugel.escolapl.util.Cuboid;
import org.bukkit.Location;

public class ResolutionSave {

    private Cuboid cuboid;
    private int quadros;

    public ResolutionSave(Cuboid cuboid) {
        this.cuboid = cuboid;
        this.quadros = cuboid.blockListPOO().size();

        Main.getInstance().getManagerResolution().getListResolution().add(this);
    }

    public Cuboid getCuboid() {
        return cuboid;
    }

    public void setCuboid(Cuboid cuboid) {
        this.cuboid = cuboid;
    }

    public int getQuadros() {
        return quadros;
    }

}
