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
public class EventoIncorrecto extends Exception {
     public EventoIncorrecto(String message) {
        super(message);
    }

    public EventoIncorrecto() {
        super("Evento incorrecto. No hay más detalles.");
    }
}
