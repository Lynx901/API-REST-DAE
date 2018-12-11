/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.dae1819.Excepciones;

/**
 *
 * @author macosx
 */
public class AtributoVacio extends Exception{
    public AtributoVacio(String message) {
        super(message);
    }

    public AtributoVacio() {
        super("Algún atributo está vacío. No hay más detalles.");
    }
}
