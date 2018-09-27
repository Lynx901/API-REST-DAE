/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dae.dae1819.pojos;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author dml y jfaf
 */
public class Usuario {
    private String username;
    private String password;
    private String email;
    
    private List<Evento> eventos;
    private List<Evento> organizados;
    
    public Usuario() {
        username = "";
        password = "";
        email = "";
        
        eventos = new ArrayList();
        organizados = new ArrayList();
    }
    
    public Usuario(String username, String password, String email, List<Evento> eventos, List<Evento> organizados) {
        this.username = "";
        this.password = "";
        this.email = "";
        
        this.eventos.clear();
        for (Evento evento : eventos) {
            this.eventos.add(evento);
        }
        this.organizados.clear();
        for (Evento evento : organizados) {
            this.organizados.add(evento);
        }
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the eventos
     */
    public List<Evento> getEventos() {
        return eventos;
    }

    /**
     * @param eventos the eventos to set
     */
    public void setEventos(List<Evento> eventos) {
        this.eventos.clear();
        for (Evento evento : eventos) {
            this.eventos.add(evento);
        }
    }

    /**
     * @return the organizados
     */
    public List<Evento> getOrganizados() {
        return organizados;
    }

    /**
     * @param organizados the organizados to set
     */
    public void setOrganizados(List<Evento> organizados) {
        this.organizados.clear();
        for (Evento evento : organizados) {
            this.organizados.add(evento);
        }
    }
}
