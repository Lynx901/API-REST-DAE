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
        if(token == -1) {
            return false;
        }
        
        for (Map.Entry<String, Usuario> entry : usuarios.entrySet()) {
            if(entry != null) {
                Usuario u = entry.getValue();
                if (u.getToken() != null && u.getToken().equals(token)){
                    return true;
                }
            }
        }
        return false;
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
                user.setToken(token);
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
                e = eventoToDTO(entry.getValue());
            }
        }
        return e;
    };
    
    @Override
    public List<EventoDTO> buscarEventosPorTipo(String tipo){
        List<EventoDTO> eventosPorTipo = new ArrayList();
        
        for (Map.Entry<String, Evento> entry : eventos.entrySet()) {
            if (entry.getValue().getTipo().equals(tipo)){
                EventoDTO e = eventoToDTO(entry.getValue());
                eventosPorTipo.add(e);
            }
        }
        return eventosPorTipo;
    };
    
    @Override
    public List<EventoDTO> buscarEventosPorDescripcion(String descripcion){
        List<EventoDTO> eventosPorDescripcion = new ArrayList();
        
        for (Map.Entry<String, Evento> entry : eventos.entrySet()) {
            if (entry.getValue().getDescripcion().contains(descripcion)){
                EventoDTO e = eventoToDTO(entry.getValue());
                eventosPorDescripcion.add(e);
            }
        }
        
        return eventosPorDescripcion;
    };
    
    @Override
    public List<EventoDTO> listarEventos() {
        List<EventoDTO> lista = new ArrayList();
        eventos.entrySet().forEach((entry) -> {
            EventoDTO e = eventoToDTO(entry.getValue());
            lista.add(e);
        });
        return lista;
    }
    
    private Usuario buscarUserPorUsername(String username) {
        Usuario u = usuarios.get(username);
        return (u == null) ? new Usuario() : u;
    }
    
    /* ACCIONES USUARIOS LOGEADOS */
    @Override
    public void nuevoEvento(String nombre,      Date fecha,         String tipo, 
                            String descripcion, Integer capacidad,  String localizacion,
                            String organizador) {
        Evento evento = new Evento(nombre, fecha, tipo, descripcion, capacidad, localizacion, buscarUserPorUsername(organizador));
        
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
        UsuarioDTO usuarioDTO = usuarioToDTO(u);
        
        return usuarioDTO;        
    }
    
    
    public UsuarioDTO usuarioToDTO(Usuario u) {
        UsuarioDTO uDTO = new UsuarioDTO();
        uDTO.setUsername(u.getUsername());
        uDTO.setEmail(u.getEmail());
        
        List<String> e = new ArrayList();
        if(!u.getEventos().isEmpty()) {
            u.getEventos().forEach((evento) -> {
                e.add(evento.getNombre());
            });
            uDTO.setEventos(e);
            
            List<String> o = new ArrayList();
            u.getOrganizados().forEach((organizado) -> {
                o.add(organizado.getNombre());
            });
            uDTO.setOrganizados(o);
        }
        
        return uDTO;
    }
    
    public EventoDTO eventoToDTO(Evento e) {
        EventoDTO eDTO = new EventoDTO();
        eDTO.setNombre(e.getNombre());
        eDTO.setDescripcion(e.getDescripcion());
        eDTO.setFecha(e.getFecha());
        eDTO.setLocalizacion(e.getLocalizacion());
        eDTO.setCapacidad(e.getCapacidad());
        
        if(!e.getAsistentes().isEmpty()) {
            List<String> asistentes = new ArrayList();
            e.getAsistentes().forEach((asistente) -> {
                asistentes.add(asistente.getUsername());
            });
            eDTO.setAsistentes(asistentes);
        } else {
            eDTO.setAsistentes(new ArrayList());
        }
        
        eDTO.setTipo(e.getTipo());
        eDTO.setOrganizador(e.getOrganizador().getUsername());
        
        return eDTO;
    }
    
    
    
}
