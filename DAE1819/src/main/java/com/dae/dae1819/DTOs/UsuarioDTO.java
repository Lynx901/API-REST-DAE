/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.dae1819.DTOs;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author juanf
 */
public class UsuarioDTO {
    private String username;
    private String email;
    
    private List<String> eventos;
    private List<String> organizados;
    
    public UsuarioDTO() {
        eventos = new ArrayList();
        organizados = new ArrayList();
    }
    
    public UsuarioDTO(String username, String email) {
        this.username = username;
        this.email = email;
    }
    
    public UsuarioDTO(String username, String email, List<String> eventos, List<String> organizados) {
        this.username = username;
        this.email = email;
        
        this.eventos.clear();
        eventos.forEach((evento) -> {
            this.eventos.add(evento);
        });
        
        this.organizados.clear();
        organizados.forEach((evento) -> {
            this.organizados.add(evento);
        });
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
    public List<String> getEventos() {
        return eventos;
    }

    /**
     * @param eventos the eventos to set
     */
    public void setEventos(List<String> eventos) {
        this.eventos.clear();
        eventos.forEach((evento) -> {
            this.eventos.add(evento);
        });
    }

    /**
     * @return the organizados
     */
    public List<String> getOrganizados() {
        return organizados;
    }

    /**
     * @param organizados the organizados to set
     */
    public void setOrganizados(List<String> organizados) {
        this.organizados.clear();
        organizados.forEach((evento) -> {
            this.organizados.add(evento);
        });
    }
}
