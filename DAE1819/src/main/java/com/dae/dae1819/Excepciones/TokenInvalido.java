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
public class TokenInvalido extends Exception{
    public TokenInvalido(String message) {
        super(message);
    }

    public TokenInvalido() {
        super("Token inválido. No hay más detalles.");
    }
}
