/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.dae1819.pojos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.*;
import static javax.persistence.TemporalType.TIMESTAMP;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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

    @Version
    private int version;

    @ManyToMany(mappedBy = "asistentes", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @LazyCollection(LazyCollectionOption.FALSE)
    private final Set<Evento> eventos;

    @ManyToMany(mappedBy = "inscritos", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @LazyCollection(LazyCollectionOption.FALSE)
    @MapKeyTemporal(TIMESTAMP)
    private final Set<Evento> listaEspera;

    @OneToMany(mappedBy = "organizador",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @LazyCollection(LazyCollectionOption.FALSE)
    private final Set<Evento> organizados;

    public Usuario() {
        eventos = new HashSet();

        listaEspera = new HashSet();
        organizados = new HashSet();
    }

    public Usuario(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;

        eventos = new HashSet();
        organizados = new HashSet();
        listaEspera = new HashSet();
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
    public Set<Evento> getEventos() {
        return eventos;
    }

    /**
     * @param eventos the eventos to set
     */
    public void setEventos(Set<Evento> eventos) {
        this.eventos.clear();
        eventos.forEach((evento) -> {
            this.eventos.add(evento);
        });
    }

    /**
     * @return the eventos
     */
    public Set<Evento> getListaEspera() {
        return listaEspera;
    }

    /**
     * @param listaEspera the eventos in the lista de espera to set
     */
    public void setListaEspera(Set<Evento> listaEspera) {
        this.listaEspera.clear();
        listaEspera.forEach((evento) -> {
            this.listaEspera.add(evento);
        });
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
    public Set<Evento> getOrganizados() {
        return organizados;
    }

    /**
     * @return the organizados
     */
    public List<Evento> getOrganizadosLista() {
        List<Evento> Lista = new ArrayList();
        this.organizados.forEach((evento) -> {
            Lista.add(evento);
        });
        return Lista;
    }

    /**
     * @param organizados the organizados to set
     */
    public void setOrganizados(Set<Evento> organizados) {
        this.organizados.clear();
        organizados.forEach((evento) -> {
            this.organizados.add(evento);
        });
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

    /*
    * @return the version of control for Transactional
     */
    public int getVersion() {
        return this.version;
    }

    /*
    * @param version the new version to control transactional
     */
    public void setVersion(int version) {
        this.version = version;
    }
}
