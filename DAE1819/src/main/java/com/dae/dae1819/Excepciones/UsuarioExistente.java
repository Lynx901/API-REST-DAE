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
public class UsuarioExistente extends Exception{
    public UsuarioExistente(String message) {
        super(message);
    }

    public UsuarioExistente() {
        super("Usuario existente. No hay más detalles.");
    }
}
