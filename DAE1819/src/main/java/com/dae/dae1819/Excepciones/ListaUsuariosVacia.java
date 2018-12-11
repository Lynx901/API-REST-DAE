/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.dae1819.Excepciones;

/**
 *
 * @author juanf
 */
public class ListaUsuariosVacia extends Exception {

    public ListaUsuariosVacia(String message) {
        super(message);
    }

    public ListaUsuariosVacia() {
        super("Lista de usuarios vacía. No hay más detalles.");
    }
    
}
