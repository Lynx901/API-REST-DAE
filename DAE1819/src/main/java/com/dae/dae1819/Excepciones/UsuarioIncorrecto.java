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
public class UsuarioIncorrecto extends Exception {
    public UsuarioIncorrecto(String message) {
        super(message);
    }

    public UsuarioIncorrecto() {
        super("Usuario incorrecto. No hay más detalles.");
    }
}
