/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.dae1819.pojos;

import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author dml y jfaf
 */
public class Sistema {

    String nombre;
    Map<Integer, Usuario> usuarios;
    Map<Integer, Evento> eventos;

    public Sistema() {
        usuarios = new TreeMap();
        eventos = new TreeMap();
    }
    
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
