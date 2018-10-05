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
    
    /* ACCIONES USUARIOS SIN LOGEAR */
    public abstract void nuevoUsuario(String username, String password, String email);
    public abstract boolean login(String username, String password);
    public abstract EventoDTO buscarEventoPorNombre(String nombre);
    public abstract List<String> buscarEventosPorTipo(String tipo);
    public abstract List<String> buscarEventosPorPalabras(String descripcion);
    public abstract List<String> listarEventos();
    
    
    /* ACCIONES USUARIO LOGEADOS */
    public abstract void nuevoEvento(String nombre, Date fecha, String tipp, String descripcion, 
                   Integer capacidad, String localizacion, UsuarioDTO organizado);
    public abstract boolean cancelarEvento(String nombreEvento);
    public abstract UsuarioDTO buscarUsuario(String username);
    
}
