package me.flugel.escolapl.objetos;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ManagerAlunos {
    private ArrayList<Aluno> alunos;

    public ManagerAlunos() {
        this.alunos = alunos;
    }

    public ArrayList<Aluno> getAlunos(){
        return this.alunos;
    }

    public Aluno getByAluno(Player player){
        return alunos.stream().filter(p -> p.getPlayer().equals(player)).findFirst().orElse(null);
    }

    public void addAluno(Aluno aluno){
        this.alunos.add(aluno);
    }

    public void delAluno(Aluno aluno){
        this.alunos.remove(aluno);
    }
}
