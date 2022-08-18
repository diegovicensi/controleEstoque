/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Implementacao;

import Classes.Usuario;
import ConexaoDB.ConexaoDB;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Aluno
 */
public class UsiarioImpl {

    public boolean validaLogon(Usuario usuario) {
        ConexaoDB conexao = new ConexaoDB();
        conexao.conectar();

        ResultSet rst = null;

        try {
            String sql = "select count(*) as existe from usuarios where login = ?";
            PreparedStatement pst = (PreparedStatement) conexao.conexao.prepareStatement(sql);
            pst.setString(1, usuario.getLogin());
            rst = pst.executeQuery();

            while (rst.next()) {
                Integer aux = rst.getInt("existe");
                
                if(aux.equals(0)){
                    JOptionPane.showMessageDialog(null, "Usuário não existe!");
                    return false;
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar login!\nErro: " + ex.getMessage());
            return false;
        }
        
        rst = null;

        try {
            String sql = "select count(*) as existe from usuarios where login = ? and senha = ?";
            PreparedStatement pst = (PreparedStatement) conexao.conexao.prepareStatement(sql);
            pst.setString(1, usuario.getLogin());
            pst.setString(2, usuario.getSenha());
            rst = pst.executeQuery();

            while (rst.next()) {
                Integer aux = rst.getInt("existe");
                
                if(aux.equals(0)){
                    JOptionPane.showMessageDialog(null, "Senha Invalida!");
                    return false;
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar senha!\nErro: " + ex.getMessage());
            return false;
        }

        rst = null;

        try {
            String sql = "select count(*) as existe from usuarios where login = ? and senha = ? and situacao = 'A' and (CURRENT_DATE between dataativacao and datadesativacao or datadesativacao is null)";
            PreparedStatement pst = (PreparedStatement) conexao.conexao.prepareStatement(sql);
            pst.setString(1, usuario.getLogin());
            pst.setString(2, usuario.getSenha());
            rst = pst.executeQuery();

            while (rst.next()) {
                Integer aux = rst.getInt("existe");
                
                if(aux.equals(0)){
                    JOptionPane.showMessageDialog(null, "Usuario Sem Acesso!");
                    return false;
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar Acessos!\nErro: " + ex.getMessage());
            return false;
        }
        conexao.desconectar();
        return true;
    }
        public void popularTabela(JTable jTable) {
        while (jTable.getModel().getRowCount() > 0) {  
                ((DefaultTableModel) jTable.getModel()).removeRow(0);                
     }                                              
        ConexaoDB conexao = new ConexaoDB();
        conexao.conectar();

        ResultSet rst = null;
        
        try {
            String sql = "SELECT id_usuarios, nome, datanascimento, situacao, login, senha, '********' as senhaEscondida, dataativacao, datadesativacao FROM usuarios order by nome;";
            PreparedStatement pst = (PreparedStatement) conexao.conexao.prepareStatement(sql);
            rst = pst.executeQuery();
            while(rst.next()){           
                DefaultTableModel jTable2 = (DefaultTableModel) jTable.getModel();
                 
                String idAluno = rst.getString("id_aluno");
                String nomeAluno = rst.getString("nome");
                
                Object[] obj = {idAluno, nomeAluno};
                
                jTable2.addRow(obj);
            };
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro na inserção!\nErro: " + ex.getMessage());
        }
        
        conexao.desconectar();
    }
    

}
