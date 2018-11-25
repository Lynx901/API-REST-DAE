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
    public EventoExistente(String message, Throwable cause) {
        super(message, cause);
    }

    public EventoExistente() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
