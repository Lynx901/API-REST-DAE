/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.dae1819.pojos;

import com.dae.dae1819.interfaces.SistemaInterface;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import com.dae.dae1819.DTOs.EventoDTO;
import com.dae.dae1819.DTOs.UsuarioDTO;
import java.util.concurrent.ThreadLocalRandom;
import org.modelmapper.ModelMapper;


/**
 *
 * @author dml y jfaf
 */
public class Sistema extends SistemaInterface{

    private String nombre;
    private Map<String, Usuario> usuarios;
    private Map<String, Evento> eventos;

    public Sistema() {
        usuarios = new TreeMap();
        eventos = new TreeMap();
    }
    
    public Sistema(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the usuarios
     */
    public Map<String, Usuario> getUsuarios() {
        return usuarios;
    }

    /**
     * @param usuarios the usuarios to set
     */
    public void setUsuarios(Map<String, Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    /**
     * @return the eventos
     */
    public Map<String, Evento> getEventos() {
        return eventos;
    }

    /**
     * @param eventos the eventos to set
     */
    public void setEventos(Map<String, Evento> eventos) {
        this.eventos = eventos;
    }
    
    @Override
    public boolean isTokenValid(Integer token) {
        return (usuarios.entrySet().stream().anyMatch((entry) -> (entry.getValue().getToken().equals(token)))) ;
    }
    
    /* ACCIONES USUARIOS SIN LOGEAR */
    
    @Override
    public void nuevoUsuario(String username, String password, String email){
        Usuario usuario = new Usuario(username, password, email);
        usuarios.put(username, usuario);
    };
    
    @Override
    public Integer login(String username, String password) {
        Usuario user = usuarios.get(username);
        if (user != null){
            if (user.getPassword().equals(password)) {
                Integer token = ThreadLocalRandom.current().nextInt(10000000, 100000000);
                return token;
            }
        }
        return 0;
    };
    
    @Override
    public EventoDTO buscarEventoPorNombre(String nombre) {
        EventoDTO e = new EventoDTO();
        for (Map.Entry<String, Evento> entry : eventos.entrySet()) {
            if (entry.getValue().getNombre().equals(nombre)){
                e = entry.getValue().toDTO();
            }
        }
        return e;
    };
    
    @Override
    public List<EventoDTO> buscarEventosPorTipo(String tipo){
        List<EventoDTO> eventosPorTipo = new ArrayList();
        
        EventoDTO e = new EventoDTO();
        for (Map.Entry<String, Evento> entry : eventos.entrySet()) {
            if (entry.getValue().getTipo().equals(tipo)){
                e = entry.getValue().toDTO();
                eventosPorTipo.add(e);
            }
        }
        return eventosPorTipo;
    };
    
    @Override
    public List<EventoDTO> buscarEventosPorDescripcion(String descripcion){
        List<EventoDTO> eventosPorDescripcion = new ArrayList();
        
        EventoDTO e = new EventoDTO();
        for (Map.Entry<String, Evento> entry : eventos.entrySet()) {
            if (entry.getValue().getDescripcion().contains(descripcion)){
                e = entry.getValue().toDTO();
                eventosPorDescripcion.add(e);
            }
        }
        
        return eventosPorDescripcion;
    };
    
    @Override
    public List<EventoDTO> listarEventos() {
        List<EventoDTO> lista = new ArrayList();
        eventos.entrySet().forEach((entry) -> {
            lista.add(entry.getValue().toDTO());
        });
        return lista;
    }
    
    /* ACCIONES USUARIOS LOGEADOS */
    @Override
    public void nuevoEvento(String nombre,      Date fecha,         String tipo, 
                            String descripcion, Integer capacidad,  String localizacion,
                            String organizador) {
        // TO-DO
        Evento evento = new Evento(nombre, fecha, tipo, descripcion, capacidad, localizacion, organizador);
        eventos.put(nombre, evento);
    };
    
    @Override
    public boolean cancelarEvento(String nombreEvento) {
        return (eventos.remove(nombreEvento) != null);
    };
    
    @Override
    public UsuarioDTO buscarUsuario(String username) {
        Usuario u = new Usuario();
        
        u = usuarios.get(username);
        UsuarioDTO usuarioDTO = u.toDTO();
        
        return usuarioDTO;        
    }
    
    
}
