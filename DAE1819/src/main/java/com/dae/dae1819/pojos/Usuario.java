/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dae.dae1819.pojos;
import com.dae.dae1819.DTOs.UsuarioDTO;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author dml y jfaf
 */
public class Usuario{
    private String username;
    private String password;
    private String email;
    private Integer token;
    
    private List<Evento> eventos;
    private List<Evento> listaEspera;
    private List<Evento> organizados;
    
    public Usuario() {
        eventos = new ArrayList();
        organizados = new ArrayList();
    }
    
     public Usuario(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        eventos = new ArrayList();
        organizados = new ArrayList();
        listaEspera = new ArrayList();
    }
    
    public Usuario(String username, String password, String email, List<Evento> eventos, List<Evento> organizados) {
        this.username = username;
        this.password = password;
        this.email = email;
        
        this.eventos = new ArrayList();
        for (Evento evento : eventos) {
            this.eventos.add(evento);
        }
        
        this.organizados = new ArrayList();
        for (Evento evento : organizados) {
            this.organizados.add(evento);
        }
        
        this.listaEspera = new ArrayList();
        for (Evento evento : listaEspera) {
            this.listaEspera.add(evento);
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
     * @return the eventos
     */
    public List<Evento> getListaEspera() {
        return listaEspera;
    }

    /**
     * @param listaEspera the eventos in the lista de espera to set
     */
    public void setListaEspera(List<Evento> listaEspera) {
        this.listaEspera.clear();
        for (Evento evento : listaEspera) {
            this.listaEspera.add(evento);
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

    /**
     * @return the token
     */
    public Integer getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(Integer token) {
        this.token = token;
    }
    
    /**
     * Inscribe al usuario en un evento y maneja si es el organizador del evento
     * @param e evento en el que se va a inscribir al usuario
     * @return true si se inscribe, false si entra en la lista de espera
     */
    public boolean inscribirEnEvento(Evento e) {
        boolean ret = false;
        
        if(!e.inscribir(this)) {
            this.listaEspera.add(e);
        } else {
            this.eventos.add(e);
            if(e.getOrganizador().username.equals(this.username)) {
                this.organizados.add(e);
            }
            ret = true;
        }
        
        return ret;
    }
    
    /**
     * Desinscribe al usuario de un evento. Si es el organizador, no se elimina
     * de su listado, ya que el evento no se cancela si el organizador no asiste
     * @param e evento del que se va a desinscribir al usuario
     * @return true si se desinscribe bien, false si no
     */
    public boolean desinscribir(Evento e) {
        boolean ret = false;
        
        if(this.eventos.contains(e)) { // Comprobamos que el usuario asista al evento
            if(e.desinscribir(this)) { // Comprobamos que se desinscribe del evento antes de borrar el evento de la lista de inscritos
                this.eventos.remove(e);
                ret = true;
            }
        }
        
        return ret;
    }
}