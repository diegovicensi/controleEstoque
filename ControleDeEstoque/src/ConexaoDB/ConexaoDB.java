package ConexaoDB;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ConexaoDB {
    
    public Statement stm; // Responsavel por preparar e realizar pesquisas no banco de dados;
    public ResultSet rs; // Responsavel por armazenar o resultado de um pesquisa passada para o statement;
    private String driver = "org.postgresql.Driver";
    private String caminho = "jdbc:postgresql://localhost:5432/"; // O "meubanco" representa a minha database 
    private String usuario = "postgres"; // Usuario default
    private String senha = "admin";
    public Connection conexao; // Responsavel por realizar a conexão com o banco de dados;
    
    public void conectar() { // Metodo responsavel por realizar a conexão;
        try {
            System.setProperty("jdbc.Drivers", driver); // Seta a propriedade do driver de conexão;
            conexao = DriverManager.getConnection(caminho, usuario, senha); // Realiza a conexão com o banco;
            //JOptionPane.showMessageDialog(null, "Conectado com sucesso!", "Banco de Dados", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            Logger.getLogger(ConexaoDB.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro de conexão!\nERRO: " + ex.getMessage(), "Banco de Dados", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void desconectar() { // Metodo responsavel por fechar a conexão
        try {
            conexao.close(); // Fechar conexão
           // JOptionPane.showMessageDialog(null, "Conexão fechada com sucesso!", "Banco de Dados", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            Logger.getLogger(ConexaoDB.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao fechar a conexão!\nERRO: " + ex.getMessage(), "Banco de Dados", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}

