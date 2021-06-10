package me.flugel.escolapl.objetos;

import java.util.ArrayList;

public class ManagerResolution {
    private ArrayList<ResolutionSave> resolutionSaves;

    public ManagerResolution() {
        this.resolutionSaves = new ArrayList<>();
    }

    public ArrayList<ResolutionSave> getListResolution(){
        return this.resolutionSaves;
    }

    public ResolutionSave getResolution(int quadros){
        return resolutionSaves.stream().filter(r -> r.getQuadros() == quadros).findFirst().orElse(null);
    }

}
