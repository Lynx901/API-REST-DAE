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
    public String username;
    public String password;
    public String email;
    
    public List<Evento> eventos;
    public List<Evento> organizados;
}
