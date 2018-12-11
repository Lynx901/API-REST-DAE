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
public class ListaEventosVacia extends Exception {

    public ListaEventosVacia(String message) {
        super(message);
    }

    public ListaEventosVacia() {
        super("LLista de eventos vacía. No hay más detalles.");
    }
    
}
