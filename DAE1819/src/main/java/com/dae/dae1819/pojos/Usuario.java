/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.dae1819.pojos;


import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.stereotype.Repository;


/**
 *
 * @author dml y jfaf
 */
@Entity
public class Usuario {

    @Id
    private String username;
    
    private String password;
    
    private String email;

    @ManyToMany(mappedBy="asistentes")
    @LazyCollection(LazyCollectionOption.FALSE)
    private final List<Evento> eventos;
    
    @ManyToMany(mappedBy="inscritos")
    @LazyCollection(LazyCollectionOption.FALSE)
    private final List<Evento> listaEspera;
    
    @OneToMany(mappedBy = "organizador")
    @LazyCollection(LazyCollectionOption.FALSE)
    private final List<Evento> organizados;

    public Usuario() {
        eventos = new ArrayList();

        listaEspera = new ArrayList();
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
        eventos.forEach((evento) -> {
            this.eventos.add(evento);
        });

        this.organizados = new ArrayList();
        organizados.forEach((evento) -> {
            this.organizados.add(evento);
        });

        this.listaEspera = new ArrayList();
        listaEspera.forEach((evento) -> {
            this.listaEspera.add(evento);
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
        eventos.forEach((evento) -> {
            this.eventos.add(evento);
        });
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
        listaEspera.forEach((evento) -> {
            this.listaEspera.add(evento);
        });
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
        organizados.forEach((evento) -> {
            this.organizados.add(evento);
        });
    }

    /**
     * Inscribe al usuario en un evento y maneja si es el organizador del evento
     *
     * @param e evento en el que se va a inscribir al usuario
     * @return true si se inscribe, false si entra en la lista de espera
     */
    public boolean inscribirEnEvento(Evento e) {
        boolean ret = false;

        if (!e.inscribir(this)) {
            this.listaEspera.add(e);
        } else {
            this.eventos.add(e);
            if (e.getOrganizador().username.equals(this.username)) {
                this.organizados.add(e);
            }
            ret = true;
        }

        return ret;
    }

    /**
     * Desinscribe al usuario de un evento. Si es el organizador, no se elimina
     * de su listado, ya que el evento no se cancela si el organizador no asiste
     *
     * @param e evento del que se va a desinscribir al usuario
     * @return true si se desinscribe bien, false si no
     */
    public boolean desinscribir(Evento e) {
        boolean ret = false;

        if (this.eventos.contains(e)) { // Comprobamos que el usuario asista al evento
            this.eventos.remove(e);
            ret = true;
        } else if (this.listaEspera.contains(e)) {
            this.listaEspera.remove(e);
            ret = true;
        }

        return ret;
    }
}
