/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.dae1819.DAOs;

import com.dae.dae1819.pojos.Evento;
import com.dae.dae1819.pojos.Usuario;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author dml y jfaf
 */
@Repository
@Transactional
public class UsuarioDAO {
    @PersistenceContext
    EntityManager em;
    
    public List<Usuario> listar() {
        List<Usuario> usuarios = new ArrayList();
        
        return usuarios;
    }
   
    public Usuario buscar(String username) {
        Usuario result = em.find(Usuario.class, username);
        return result;
    }
    
    public boolean inscribir(Usuario u, Evento e) {
        boolean ret = false;
        
        Calendar fechaIns = Calendar.getInstance();
        // Si está lleno, añadimos el evento a la lista de espera
        if (e.getAsistentes().size() >= e.getCapacidad()) {
            System.out.println("[debug] UsuarioDAO: e.getAsistentes().size() = " + e.getAsistentes().size());
            System.out.println("[debug] UsuarioDAO: e.getCapacidad() = " + e.getCapacidad());
            System.out.println("[debug] UsuarioDAO: El evento está lleno, añadiendo a la lista de espera");
            u.getListaEspera().put(fechaIns, e);
        } else {
            // Si no está lleno, añadimos el evento a la lista de eventos
            u.getEventos().put(fechaIns, e);
            // Si además es el organizador, añadimos el evento a la lista de organizados
            if (e.getOrganizador().getUsername().equals(u.getUsername())) {
                u.getOrganizados().add(e);
            }
            ret = true;
        }

        return ret;
    }
    
    public void insertar(Usuario u) {
        System.out.println("[debug] ¡Estamos insertando un usuario!");
        em.persist(u);
        System.out.println("[debug] ¿Se ha insertado el usuario? " + this.buscar(u.getUsername()).getUsername());
    }
    
    public void actualizar(Usuario u) {
        em.merge(u);
    }
    
}