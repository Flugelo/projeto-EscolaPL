package me.flugel.escolapl.msql;

import me.flugel.escolapl.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MYSQL {

    private Connection connection;

    private final String usuario;
    private final String senha;
    private final String host;
    private final int porta;
    private final String dataBase;
    private int query;

    public MYSQL(String usuario, String senha, String host, int porta, String dataBase) {
        this.usuario = usuario;
        this.senha = senha;
        this.host = host;
        this.porta = porta;
        this.dataBase = dataBase;
        this.query = 0;
        loadDB();
    }

    //Abrir conexão da MYSQL

    public void openConnection() {
        try {
            query++;
            if ((connection != null) && (!connection.isClosed()))
                return;

            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + porta + "/" + dataBase + "?useSSL=false", usuario, senha);
        } catch (ClassNotFoundException | SQLException e) {
            query--;
            e.printStackTrace();
            System.out.println("Ocorreu um erro ao abrir a conexão!");
        }
    }

    public void closeConnection() {
        query--;
        if (query <= 0) {
            try {
                if (connection != null && !connection.isClosed())
                    connection.close();
            } catch (Exception e) {
                System.out.println("Houve um erro ao fechar a conexão!");
                e.printStackTrace();

            }
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

    private void loadDB() {
        openConnection();
        tabelaPrincipal();
        closeConnection();
    }

    private void criarTabela(String tabela, String colunas) {
        try {
            if ((connection != null) && (!connection.isClosed())) {
                Statement stm = connection.createStatement();
                stm.executeUpdate("CREATE TABLE IF NOT EXISTS " + tabela + " (" + colunas + ");");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ocorreu um erro ao salvar o MYSQL!");
        }
    }

    public void tabelaPrincipal(){
        criarTabela(Main.getInstance().getConfig().getString("Config.dataBase"), "Jogador varchar(32), Nome_real text, Faltas smallint, Nota1S double, Nota2S double, NotaFinal double");
    }

}
