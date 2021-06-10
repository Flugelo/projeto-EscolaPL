package me.flugel.escolapl.objetos;

import org.bukkit.entity.Player;

public class Aluno {
    private Player player;
    private String nome;
    private int faltas;
    private double notaS1;
    private double notaS2;
    private double notaFinal;

    public Aluno(Player player, String nome, int faltas, double notaS1, double notaS2, double notaFinal) {
        this.player = player;
        this.nome = nome;
        this.faltas = faltas;
        this.notaS1 = notaS1;
        this.notaS2 = notaS2;
        this.notaFinal = notaFinal;
    }

    public Aluno(Player player) {
        this.player = player;
        this.faltas = 0;
        this.notaS1 = 0;
        this.notaS2 = 0;
        this.notaFinal = 0;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setFaltas(int faltas) {
        this.faltas = faltas;
    }

    public void setNotaS1(double notaS1) {
        this.notaS1 = notaS1;
    }

    public void setNotaS2(double notaS2) {
        this.notaS2 = notaS2;
    }

    public void setNotaFinal(double notaFinal) {
        this.notaFinal = notaFinal;
    }

    public Player getPlayer() {
        return player;
    }

    public int getFaltas() {
        return faltas;
    }

    public double getNotaS1() {
        return notaS1;
    }

    public double getNotaS2() {
        return notaS2;
    }

    public double getNotaFinal() {
        return notaFinal;
    }
}
