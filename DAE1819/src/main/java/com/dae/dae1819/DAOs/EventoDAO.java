/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.dae1819.DAOs;

import com.dae.dae1819.pojos.Evento;
import java.util.ArrayList;
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
public class EventoDAO {
    @PersistenceContext
    EntityManager em;
    
    public List<Evento> buscarPorNombre(String nombre) {
        List<Evento> eventos = new ArrayList();
        
        return eventos;
    }
    
    public List<Evento> buscarPorTipo(String nombre) {
        List<Evento> eventos = new ArrayList();
        
        return eventos;
    }
    
    public List<Evento> buscarPorDescripcion(String nombre) {
        List<Evento> eventos = new ArrayList();
        
        return eventos;
    }
    
    public List<Evento> listar() {
        List<Evento> eventos = new ArrayList();
        
        return eventos;
    }
    
    public Evento buscar(int id) {
        return em.find(Evento.class, id);
    }
    
    public void insertar(Evento e) {
        em.persist(e);
    }
    
    public void actualizar(Evento e) {
        em.merge(e);
    }
    
}
