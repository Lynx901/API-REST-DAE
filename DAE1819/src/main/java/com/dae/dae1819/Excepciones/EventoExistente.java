/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.dae1819.Excepciones;

/**
 *
 * @author dml y jfaf
 */
public class EventoExistente extends Exception{
    public EventoExistente(String message) {
        super(message);
    }

    public EventoExistente() {
        super("El evento ya existe. No hay más detalles.");
    }
}
