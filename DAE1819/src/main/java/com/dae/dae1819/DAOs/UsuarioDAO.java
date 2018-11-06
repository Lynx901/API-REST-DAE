/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.dae1819.DAOs;

import com.dae.dae1819.pojos.Evento;
import com.dae.dae1819.pojos.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
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
    
    private void saveEventos(List<Evento> eventos, String tipo) {
        switch(tipo) {
            case "eventos":
                eventos.forEach((e) -> {
                    em.persist(e);
                });
                break;
            case "listaEspera":
                eventos.forEach((e) -> {
                    em.persist(e);
                });
                break;
            case "organizados":
                eventos.forEach((e) -> {
                    em.persist(e);
                });
                break;
            default:
                //TODO lanzar excepcion
                break;
        }
    }

    private void saveUsuario(Usuario u) {
        System.out.println("[debug] ¡Estamos insertando eventos!");
        saveEventos(u.getEventos(), "eventos");
        System.out.println("[debug] ¡Estamos insertando listaEspera!");
        saveEventos(u.getListaEspera(), "listaEspera");
        System.out.println("[debug] ¡Estamos insertando organizados!");
        saveEventos(u.getOrganizados(), "organizados");
        System.out.println("[debug] ¡Todo insertado!");
        em.persist(u);
    }
        
    public Usuario buscar(String username) {
        Usuario result = em.find(Usuario.class, username);
        return result;
    }
    
    public void insertar(Usuario u) {
        System.out.println("[debug] ¡Estamos insertando!");
        em.persist(u);
        System.out.println("[debug] ¿Se ha insertado? " + this.buscar(u.getUsername()).toString());
    }
    
    public void actualizar(Usuario u) {
        em.merge(u);
    }
    
}