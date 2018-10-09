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
    
    private List<Integer> idEventos;
    private List<Integer> idOrganizados;
    
    public UsuarioDTO() {
        idEventos = new ArrayList();
        idOrganizados = new ArrayList();
    }
    
    public UsuarioDTO(String username, String email) {
        this.username = username;
        this.email = email;
    }
    
    public UsuarioDTO(String username, String email, List<Integer> eventos, List<Integer> organizados) {
        this.username = username;
        this.email = email;
        
        this.idEventos.clear();
        eventos.forEach((evento) -> {
            this.idEventos.add(evento);
        });
        
        this.idOrganizados.clear();
        organizados.forEach((evento) -> {
            this.idOrganizados.add(evento);
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
    public List<Integer> getIdEventos() {
        return idEventos;
    }

    /**
     * @param eventos the eventos to set
     */
    public void setIdEventos(List<Integer> eventos) {
        this.idEventos.clear();
        eventos.forEach((evento) -> {
            this.idEventos.add(evento);
        });
    }

    /**
     * @return the organizados
     */
    public List<Integer> getIdOrganizados() {
        return idOrganizados;
    }

    /**
     * @param organizados the organizados to set
     */
    public void setIdOrganizados(List<Integer> organizados) {
        this.idOrganizados.clear();
        organizados.forEach((evento) -> {
            this.idOrganizados.add(evento);
        });
    }
}
