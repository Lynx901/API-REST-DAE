/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.dae1819.pojos;

import com.dae.dae1819.interfaces.SistemaInterface;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author dml y jfaf
 */
public class Sistema extends SistemaInterface{

    private String nombre;
    private Map<String, Usuario> usuarios;
    private Map<Integer, Evento> eventos;

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
    public Map<Integer, Evento> getEventos() {
        return eventos;
    }

    /**
     * @param eventos the eventos to set
     */
    public void setEventos(Map<Integer, Evento> eventos) {
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
    public Evento buscarEventoPorNombre(String nombre){
        Evento e = new Evento();
        for (Map.Entry<Integer, Evento> entry : eventos.entrySet()) {
            if (entry.getValue().getTipoEvento().equals(nombre)){
                e = entry.getValue();
            };
        }
        return e;
    };
    
    @Override
    public List<String> buscarEventosPorTipo(String tipo){
        List<String> lista = new ArrayList();
        for (Map.Entry<Integer, Evento> entry : eventos.entrySet()) {
            if (entry.getValue().getTipoEvento().equals(tipo)){
                lista.add(entry.getValue().getNombre());
            };
        }
        return lista;
    };
    
    @Override
    public List<String> buscarEventosPorPalabras(String descripcion){
        List<String> lista = new ArrayList();
        for (Map.Entry<Integer, Evento> entry : eventos.entrySet()) {
            if (entry.getValue().getDescripcion().contains(descripcion)){
                lista.add(entry.getValue().getNombre());
            };
        }
        return lista;
    };
    
    @Override
    public List<String> listarEventos() {
        List<String> lista = new ArrayList();
        for (Map.Entry<Integer, Evento> entry : eventos.entrySet()) {
            lista.add(entry.getValue().getNombre());
        }
        return lista;
    }
    
    /* ACCIONES USUARIOS LOGEADOS */
    
    @Override
    public void nuevoEvento() {
        
    };
    
    @Override
    public void cancelarEvento() {
        
    };
    
    @Override
    public Usuario buscarUsuario(String username) {
        Usuario user = new Usuario();
        usuarios.get(username);
        return user;        
    }
    
    
}
