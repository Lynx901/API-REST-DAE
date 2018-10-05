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
    private String password;
    private String email;
    
    private List<EventoDTO> eventos;
    private List<EventoDTO> organizados;
    
    public UsuarioDTO() {
        eventos = new ArrayList();
        organizados = new ArrayList();
    }
    
     public UsuarioDTO(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
    
    public UsuarioDTO(String username, String password, String email, List<EventoDTO> eventos, List<EventoDTO> organizados) {
        this.username = username;
        this.password = password;
        this.email = email;
        
        this.eventos.clear();
        for (EventoDTO evento : eventos) {
            this.eventos.add(evento);
        }
        this.organizados.clear();
        for (EventoDTO evento : organizados) {
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
    public List<EventoDTO> getEventos() {
        return eventos;
    }

    /**
     * @param eventos the eventos to set
     */
    public void setEventos(List<EventoDTO> eventos) {
        this.eventos.clear();
        for (EventoDTO evento : eventos) {
            this.eventos.add(evento);
        }
    }

    /**
     * @return the organizados
     */
    public List<EventoDTO> getOrganizados() {
        return organizados;
    }

    /**
     * @param organizados the organizados to set
     */
    public void setOrganizados(List<EventoDTO> organizados) {
        this.organizados.clear();
        for (EventoDTO evento : organizados) {
            this.organizados.add(evento);
        }
    }
}
