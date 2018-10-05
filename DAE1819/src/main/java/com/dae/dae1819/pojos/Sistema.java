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
    
    /* ACCIONES USUARIOS SIN LOGEAR */
    
    @Override
    public void nuevoUsuario(String username, String password, String email){
        Usuario usuario = new Usuario(username,password,email);
        usuarios.put(username, usuario);
    };
    
    @Override
    public boolean login(String username, String password ){
        Usuario user = usuarios.get(username);
        if (user != null){
            if (user.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    };
    
    @Override
    public EventoDTO buscarEventoPorNombre(String nombre){
        Evento e = new Evento();
        for (Map.Entry<String, Evento> entry : eventos.entrySet()) {
            if (entry.getValue().getTipoEvento().equals(nombre)){
                e = entry.getValue();
            };
        }
        ModelMapper modelMapper = new ModelMapper();
        EventoDTO evento = modelMapper.map(e, EventoDTO.class);
        return evento;
    };
    
    @Override
    public List<String> buscarEventosPorTipo(String tipo){
        List<String> lista = new ArrayList();
        for (Map.Entry<String, Evento> entry : eventos.entrySet()) {
            if (entry.getValue().getTipoEvento().equals(tipo)){
                lista.add(entry.getValue().getNombre());
            };
        }
        return lista;
    };
    
    @Override
    public List<String> buscarEventosPorPalabras(String descripcion){
        List<String> lista = new ArrayList();
        for (Map.Entry<String, Evento> entry : eventos.entrySet()) {
            if (entry.getValue().getDescripcion().contains(descripcion)){
                lista.add(entry.getValue().getNombre());
            };
        }
        return lista;
    };
    
    @Override
    public List<String> listarEventos() {
        List<String> lista = new ArrayList();
        for (Map.Entry<String, Evento> entry : eventos.entrySet()) {
            lista.add(entry.getValue().getNombre());
        }
        return lista;
    }
    
    /* ACCIONES USUARIOS LOGEADOS */
    
    @Override
    public void nuevoEvento(String nombre, Date fecha, String tipo, String descripcion, 
                   Integer capacidad, String localizacion, UsuarioDTO organizado) {
        ModelMapper modelMapper = new ModelMapper();
        Usuario organizador = modelMapper.map(organizado, Usuario.class);
        Evento evento = new Evento(nombre, fecha, tipo, descripcion, capacidad, localizacion, organizador);
        eventos.put(nombre, evento);
    };
    
    @Override
    public boolean cancelarEvento(String nombreEvento) {
        if (eventos.remove(nombreEvento) != null){
            return true;
        }
        return false;
    };
    
    @Override
    public UsuarioDTO buscarUsuario(String username) {
        Usuario usuario = new Usuario();
        usuario = usuarios.get(username);
        ModelMapper modelMapper = new ModelMapper();
        UsuarioDTO usuarioDTO = modelMapper.map(usuario, UsuarioDTO.class);
        return usuarioDTO;        
    }
    
    
}
