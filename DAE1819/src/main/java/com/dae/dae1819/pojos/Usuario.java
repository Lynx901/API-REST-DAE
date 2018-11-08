/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.dae1819.pojos;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.*;
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

    @ManyToMany(cascade = {CascadeType.ALL})
    @LazyCollection(LazyCollectionOption.FALSE)
    private final Map<Calendar, Evento> eventos;
    
    @ManyToMany(cascade = {CascadeType.ALL})
    @LazyCollection(LazyCollectionOption.FALSE)
    private final Map<Calendar, Evento> listaEspera;
    
    @OneToMany(mappedBy = "organizador",
               cascade = {CascadeType.ALL})
    @LazyCollection(LazyCollectionOption.FALSE)
    private final Set<Evento> organizados;

    public Usuario() {
        eventos = new HashMap();

        listaEspera = new HashMap();
        organizados = new HashSet();
    }

    public Usuario(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;

        eventos = new HashMap();
        organizados = new HashSet();
        listaEspera = new HashMap();
    }

    public Usuario(String username, String password, String email, Map<Calendar,Evento> eventos, Set<Evento> organizados) {
        this.username = username;
        this.password = password;
        this.email = email;

        this.eventos = new HashMap();
        eventos.forEach((fecha,evento) -> {
            this.eventos.put(fecha,evento);
        });

        this.organizados = new HashSet();
        organizados.forEach((evento) -> {
            this.organizados.add(evento);
        });

        this.listaEspera = new HashMap();
        listaEspera.forEach((fecha,evento) -> {
            this.listaEspera.put(fecha,evento);
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
    public Map<Calendar,Evento> getEventos() {
        return eventos;
    }
    
    /**
     * @return the eventos
     */
    public List<Evento> getEventosLista() {
        List<Evento> lista = new ArrayList();
        this.eventos.forEach((fecha,evento) -> {
            lista.add(evento);
        });
        return lista;
    }

    /**
     * @param eventos the eventos to set
     */
    public void setEventos(Map<Calendar,Evento> eventos) {
        this.eventos.clear();
        eventos.forEach((fecha,evento) -> {
            this.eventos.put(fecha,evento);
        });
    }
    
    
    /**
     * @param eventos the eventos to set
     */
    public void setEventosLista(List<Evento> eventos) {
        this.eventos.clear();
        eventos.forEach((evento) -> {
            this.eventos.put(evento.getFecha(),evento);
        });
    }

    /**
     * @return the eventos
     */
    public Map<Calendar,Evento> getListaEspera() {
        return listaEspera;
    }
    
     /**
     * @return the eventos
     */
    public List<Evento> getListaEsperaLista() {
        List<Evento> Lista = new ArrayList();
        this.listaEspera.forEach((fecha,evento) -> {
            Lista.add(evento);
        });
        return Lista;
    }

    /**
     * @param listaEspera the eventos in the lista de espera to set
     */
    public void setListaEspera(Map<Calendar,Evento> listaEspera) {
        this.listaEspera.clear();
        listaEspera.forEach((fecha,evento) -> {
            this.listaEspera.put(fecha,evento);
        });
    }
    
       /**
     * @param listaEspera the eventos in the lista de espera to set
     */
    public void setListaEspera(List<Evento> listaEspera) {
        this.listaEspera.clear();
        listaEspera.forEach((evento) -> {
            this.listaEspera.put(evento.getFecha(),evento);
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
    public void setOrganizados(Map<Calendar,Evento> organizados) {
        this.organizados.clear();
        organizados.forEach((fecha,evento) -> {
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

    /**
     * Inscribe al usuario en un evento y maneja si es el organizador del evento
     *
     * @param e evento en el que se va a inscribir al usuario
     * @return true si se inscribe, false si entra en la lista de espera
     */
    public boolean inscribirEnEvento(Evento e) {
        boolean ret = false;
        
        Calendar fechaIns = Calendar.getInstance();
        // Si está lleno, añadimos el evento a la lista de espera
        if (e.getAsistentes().size() >= e.getCapacidad()) {
            System.out.println("[debug] Usuario: El evento está lleno, añadiendo a la lista de espera");
            this.listaEspera.put(fechaIns, e);
        } else {
            // Si no está lleno, añadimos el evento a la lista de eventos
            this.eventos.put(fechaIns, e);
            System.out.println("[debug] Usuario: Se ha añadido a la lista de eventos");
            // Si además es el organizador, añadimos el evento a la lista de organizados
            if (e.getOrganizador().getUsername().equals(this.username)) {
                this.organizados.add(e);
                System.out.println("[debug] Usuario: Se ha añadido a la lista de organizados");
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

        if (this.eventos.containsValue(e)) { // Comprobamos que el usuario asista al evento
            this.eventos.remove(e.getFecha());
            ret = true;
        } else if (this.listaEspera.containsValue(e)) {
            this.listaEspera.remove(e.getFecha());
            ret = true;
        }

        return ret;
    }
}
