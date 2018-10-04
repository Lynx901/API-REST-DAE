/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.dae1819.interfaces;

import com.dae.dae1819.pojos.Evento;
import com.dae.dae1819.pojos.Usuario;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dml y jfaf
 */
public abstract class SistemaInterface {
    private String nombre;
    private Map<String, Usuario> usuarios;
    private Map<Integer, Evento> eventos;
    
    /* ACCIONES USUARIOS SIN LOGEAR */
    public abstract void nuevoUsuario(String username, String password, String email);
    public abstract boolean login(String username, String password);
    public abstract Evento buscarEventoPorNombre(String nombre);
    public abstract List<String> buscarEventosPorTipo(String tipo);
    public abstract List<String> buscarEventosPorPalabras(String descripcion);
    public abstract List<String> listarEventos();
    
    
    /* ACCIONES USUARIO LOGEADOS */
    public abstract void nuevoEvento();
    public abstract void cancelarEvento();
    public abstract Usuario buscarUsuario(String username);
    
}
