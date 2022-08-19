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
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Aluno
 */
public class UsuarioImpl {

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

                if (aux.equals(0)) {
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

                if (aux.equals(0)) {
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

                if (aux.equals(0)) {
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

    public void popularTabela(JTable jTable1) {
        ConexaoDB conexao = new ConexaoDB();
        conexao.conectar();

        while (jTable1.getModel().getRowCount() > 0) {
            ((DefaultTableModel) jTable1.getModel()).removeRow(0);
        }

        ResultSet rst = null;

        try {
            String sql = "SELECT * FROM usuarios ORDER BY nome";
            PreparedStatement pst = (PreparedStatement) conexao.conexao.prepareStatement(sql);

            rst = pst.executeQuery();
            while (rst.next()) {
                DefaultTableModel tabelaAlunos = (DefaultTableModel) jTable1.getModel(); //pega modelo da tabela

                String idUsuario = rst.getString("idusuarios");
                String nome = rst.getString("nome");
                String dataNascimento = rst.getString("datanascimento");
                String situacao = rst.getString("situacao");
                String login = rst.getString("login");
                String senha = rst.getString("senha");
                String dataAtivacao = rst.getString("dataativacao");
                String dataDesativacao = rst.getString("datadesativacao");

                Object[] obj = {idUsuario, nome, dataNascimento, situacao, login, senha, dataAtivacao, dataDesativacao};

                tabelaAlunos.addRow(obj);
            };
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao popular tabela!\nErro: " + ex.getMessage());
        }

        conexao.desconectar();
    }

    public void pesquisarTabela(JTable jTable1, Usuario usuario) {
        ConexaoDB conexao = new ConexaoDB();
        conexao.conectar();

        while (jTable1.getModel().getRowCount() > 0) {
            ((DefaultTableModel) jTable1.getModel()).removeRow(0);
        }

        ResultSet rst = null;

        try {
            String sql = "SELECT * FROM usuarios WHERE UPPER(nome) like UPPER(?) ORDER BY nome";
            PreparedStatement pst = (PreparedStatement) conexao.conexao.prepareStatement(sql);
            pst.setString(1, '%' + usuario.getNome() + '%');

            rst = pst.executeQuery();
            while (rst.next()) {
                DefaultTableModel tabelaUsuario = (DefaultTableModel) jTable1.getModel(); //pega modelo da tabela

                String idUsuario = rst.getString("idusuarios");
                String nome = rst.getString("nome");
                String dataNascimento = rst.getString("datanascimento");
                String situacao = rst.getString("situacao");
                String login = rst.getString("login");
                String senha = rst.getString("senha");
                String dataAtivacao = rst.getString("dataativacao");
                String dataDesativacao = rst.getString("datadesativacao");

                Object[] obj = {idUsuario, nome, dataNascimento, situacao, login, senha, dataAtivacao, dataDesativacao};

                tabelaUsuario.addRow(obj);
            };
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao popular tabela!\nErro: " + ex.getMessage());
        }

        conexao.desconectar();
    }

    public void inserirUsuario(Usuario usuario) {
        ConexaoDB conexao = new ConexaoDB();
        conexao.conectar();

        boolean LoginUsuarioExiste = false;
        ResultSet rst = null;
        
        try {
            String sql = "SELECT COUNT(*) existe FROM usuarios WHERE login = ?";
            PreparedStatement pst = (PreparedStatement) conexao.conexao.prepareStatement(sql);
            pst.setString(1, usuario.getLogin());

            rst = pst.executeQuery();

            while (rst.next()) {
                Integer aux = rst.getInt("existe");
                if (aux.equals(0)) {
                    LoginUsuarioExiste = false;
                } else {
                    LoginUsuarioExiste = true;
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar se o usuário existe no banco de dados INSERIR. " + ex.getMessage());
            return;
        }
        
         Integer idUsu = 0;
         try {
            String sql = "SELECT idusuarios FROM usuarios WHERE idusuarios = ?";
            PreparedStatement pst = (PreparedStatement) conexao.conexao.prepareStatement(sql);
            pst.setInt(1, usuario.getIdusuarios());

            rst = pst.executeQuery();

            while (rst.next()) {
                Integer auxIdusuario = rst.getInt("idusuarios");
                if (auxIdusuario.equals(0)) {
                    idUsu = auxIdusuario;
                } else {
                    idUsu = auxIdusuario;
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar se o idUsuario. " + ex.getMessage());
            return;
        }

        if (LoginUsuarioExiste && (!usuario.getIdusuarios().equals(idUsu) && usuario.getIdusuarios() > 0)) {
            JOptionPane.showMessageDialog(null, "Este login já existe não pode ser inserido!");
            return;
        }
        
        
        if (LoginUsuarioExiste) {
            
            try {
                String sql = " UPDATE usuarios "
                        + "    SET    nome = ?, "
                        + "           datanascimento = ?, "
                        + "           login = ?, "
                        + "           senha = ?, "
                        + "           situacao = ?, "
                        + "           dataativacao = ?, "
                        + "           datadesativacao = ? "
                        + " WHERE idusuarios = ?";

                SimpleDateFormat formatoTexto = new SimpleDateFormat("yyyy-MM-dd");
                String dataAniversario = formatoTexto.format(usuario.getDatanascimento());
                String dataInicio = formatoTexto.format(usuario.getDataativacao());

                String dataFim;
                if (usuario.getDatadesativacao()== null) {
                    dataFim = "";
                } else {
                    dataFim = formatoTexto.format(usuario.getDatadesativacao());
                }

                java.sql.Date dataSQLAniver, dataSQLIni, dataSQLFim;
                dataSQLAniver = java.sql.Date.valueOf(dataAniversario);
                dataSQLIni = java.sql.Date.valueOf(dataInicio);

                if (dataFim.equals("")) {
                    dataSQLFim = null;
                } else {
                    dataSQLFim = java.sql.Date.valueOf(dataFim);
                }

                PreparedStatement pst = (PreparedStatement) conexao.conexao.prepareStatement(sql);
                pst.setString(1, usuario.getNome());
                pst.setDate(2, dataSQLAniver);
                pst.setString(3, usuario.getLogin());
                pst.setString(4, usuario.getSenha());
                pst.setString(5, usuario.getSituacao());
                pst.setDate(6, dataSQLIni);
                pst.setDate(7, dataSQLFim);
                pst.setInt(8, usuario.getIdusuarios());

                pst.execute();

                JOptionPane.showMessageDialog(null, "Salvo com Sucesso!");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao alterar os dados do usuario no banco de dados." + ex.getMessage());
            }
        } else {
            try {
                String sql = "INSERT INTO usuarios (nome, datanascimento, login, senha, situacao, dataativacao, datadesativacao) VALUES (?,?,?,?,?,?,?)";
                PreparedStatement pst = (PreparedStatement) conexao.conexao.prepareStatement(sql);

                SimpleDateFormat formatoTexto = new SimpleDateFormat("yyyy-MM-dd");
                String dataAniversario = formatoTexto.format(usuario.getDatanascimento());
                String dataInicio = formatoTexto.format(usuario.getDataativacao());

                String dataFim;
                if (usuario.getDatadesativacao()== null) {
                    dataFim = "";
                } else {
                    dataFim = formatoTexto.format(usuario.getDatadesativacao());
                }

                java.sql.Date dataSQLAniver, dataSQLIni, dataSQLFim;
                dataSQLAniver = java.sql.Date.valueOf(dataAniversario);
                dataSQLIni = java.sql.Date.valueOf(dataInicio);

                if (dataFim.equals("")) {
                    dataSQLFim = null;
                } else {
                    dataSQLFim = java.sql.Date.valueOf(dataFim);
                }

                pst.setString(1, usuario.getNome());
                pst.setDate(2, dataSQLAniver);
                pst.setString(3, usuario.getLogin());
                pst.setString(4, usuario.getSenha());
                pst.setString(5, usuario.getSituacao());
                pst.setDate(6, dataSQLIni);
                pst.setDate(7, dataSQLFim);
                pst.execute();

                JOptionPane.showMessageDialog(null, "Salvo com Sucesso!");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao inserir os dados  do usuario no banco de dados." + ex.getMessage());
            }
        }
        conexao.desconectar();
    }
    
    
    public void pesquisarUsuario(Usuario usuario) {
        ConexaoDB conexao = new ConexaoDB();
        conexao.conectar();

        ResultSet rst = null;

        try {
            String sql = "SELECT * FROM usuarios WHERE idusuarios = ? ";
            PreparedStatement pst = (PreparedStatement) conexao.conexao.prepareStatement(sql);
            pst.setInt(1, usuario.getIdusuarios());

            rst = pst.executeQuery();
            while (rst.next()) {
                usuario.setIdusuarios(rst.getInt("idusuarios"));
                usuario.setNome(rst.getString("nome"));
                usuario.setDatanascimento(rst.getDate("datanascimento"));
                usuario.setSituacao(rst.getString("situacao"));
                usuario.setLogin(rst.getString("login"));
                usuario.setSenha(rst.getString("senha"));
                usuario.setDataativacao(rst.getDate("dataativacao"));
                usuario.setDatadesativacao(rst.getDate("datadesativacao"));
            };
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao pesquisa usuário!\nErro: " + ex.getMessage());
        }

        conexao.desconectar();
    }
    
    public void excluirUsuario(Usuario usuario) {
        ConexaoDB conexao = new ConexaoDB();
        conexao.conectar();

        try {
            String sql = "DELETE FROM usuarios WHERE idusuarios = ? ";
            PreparedStatement pst = (PreparedStatement) conexao.conexao.prepareStatement(sql);
            pst.setInt(1, usuario.getIdusuarios());

            pst.execute();
            
            JOptionPane.showMessageDialog(null, "DELETADO com Sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao DELETAR usuário!\nErro: " + ex.getMessage());
        }

        conexao.desconectar();
    }
}
