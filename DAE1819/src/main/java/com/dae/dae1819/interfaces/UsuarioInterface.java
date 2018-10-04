/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.dae1819.interfaces;

import com.dae.dae1819.pojos.Evento;
import java.util.List;

/**
 *
 * @author dml y jfaf
 */
public abstract class UsuarioInterface {
    private String username;
    private String password;
    private String email;
    
    private List<Evento> eventos;
    private List<Evento> organizados;
}
