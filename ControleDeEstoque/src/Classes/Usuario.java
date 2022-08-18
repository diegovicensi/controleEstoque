/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.sql.Date;

/**
 *
 * @author Aluno
 */
public class Usuario {
    private Integer idusuarios;
    private String nome;
    private Date datanascimento;
    private Character situacao;
    private String login;
    private String senha;
    private Date dataativacao;
    private Date datadesativacao;

    /**
     * @return the idusuarios
     */
    public Integer getIdusuarios() {
        return idusuarios;
    }

    /**
     * @param idusuarios the idusuarios to set
     */
    public void setIdusuarios(Integer idusuarios) {
        this.idusuarios = idusuarios;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the datanascimento
     */
    public Date getDatanascimento() {
        return datanascimento;
    }

    /**
     * @param datanascimento the datanascimento to set
     */
    public void setDatanascimento(Date datanascimento) {
        this.datanascimento = datanascimento;
    }

    /**
     * @return the situacao
     */
    public Character getSituacao() {
        return situacao;
    }

    /**
     * @param situacao the situacao to set
     */
    public void setSituacao(Character situacao) {
        this.situacao = situacao;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * @param senha the senha to set
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * @return the dataativacao
     */
    public Date getDataativacao() {
        return dataativacao;
    }

    /**
     * @param dataativacao the dataativacao to set
     */
    public void setDataativacao(Date dataativacao) {
        this.dataativacao = dataativacao;
    }

    /**
     * @return the datadesativacao
     */
    public Date getDatadesativacao() {
        return datadesativacao;
    }

    /**
     * @param datadesativacao the datadesativacao to set
     */
    public void setDatadesativacao(Date datadesativacao) {
        this.datadesativacao = datadesativacao;
    }
}


