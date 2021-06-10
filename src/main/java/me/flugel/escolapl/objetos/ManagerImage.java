package me.flugel.escolapl.objetos;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ManagerImage {
    private List<Imagens> imagensList;

    public ManagerImage() {
        this.imagensList = new ArrayList<>();
    }

    public List<Imagens> getImagensList() {
        return imagensList;
    }

    public Imagens getByImages(String nome){
        return this.imagensList.stream().filter(i -> i.getName().equalsIgnoreCase(nome)).findFirst().orElse(null);
    }
}
