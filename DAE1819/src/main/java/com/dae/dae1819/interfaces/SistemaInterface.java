/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.dae1819.interfaces;

import com.dae.dae1819.DTOs.EventoDTO;
import com.dae.dae1819.DTOs.UsuarioDTO;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dml y jfaf
 */
public abstract class SistemaInterface {
    private String nombre;
    private Map<String, UsuarioDTO> usuarios;
    private Map<Integer, EventoDTO> eventos;
    
    public abstract boolean isTokenValid(Integer token);
    
    /* ACCIONES USUARIOS SIN LOGEAR */
    public abstract void            nuevoUsuario(String username, String password, String email);
    public abstract Integer         login(String username, String password);
    public abstract EventoDTO       buscarEventoPorNombre(String nombre);
    public abstract List<EventoDTO> buscarEventosPorTipo(String tipo);
    public abstract List<EventoDTO> buscarEventosPorDescripcion(String descripcion);
    public abstract List<EventoDTO> buscarEventos();
    
    
    /* ACCIONES USUARIO LOGEADOS */
    public abstract void nuevoEvento(String nombre,      Date fecha,        String tipo, 
                                     String descripcion, Integer capacidad, String localizacion, 
                                     String organizador);
    
    public abstract boolean         cancelarEvento(EventoDTO eDTO);
    public abstract boolean         reactivarEvento(EventoDTO eDTO);
    public abstract UsuarioDTO      buscarUsuario(String username);
    public abstract List<EventoDTO> buscarEventosInscritos(UsuarioDTO uDTO);
    public abstract List<EventoDTO> buscarEventosOrganizados(UsuarioDTO uDTO);
    public abstract boolean         inscribirse(UsuarioDTO uDTO, EventoDTO eDTO);
    public abstract boolean         desinscribirse(UsuarioDTO uDTO, EventoDTO eDTO); 
    
    // MODO DESARROLLADOR
    public abstract boolean godMode(String pass);
    
}
