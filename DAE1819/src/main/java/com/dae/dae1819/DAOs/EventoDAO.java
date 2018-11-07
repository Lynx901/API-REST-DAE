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
        Evento result = em.find(Evento.class, id);
        return result;
    }
    
    public boolean inscribir(Usuario u, Evento e) {
        boolean ret = false;
        
        Calendar fechaIns = Calendar.getInstance();
        // Si está lleno, añadimos el usuario a la lista de inscritos
        if (e.getAsistentes().size() >= e.getCapacidad()) {
            System.out.println("[debug] EventoDAO: El evento está lleno, añadiendo a la lista de espera");
            e.getInscritos().put(fechaIns, u);
        } else {
            // Si no está lleno, añadimos el usuario a la lista de asistentes
            e.getAsistentes().put(u.getUsername(), u);
            // Si además es el organizador, añadimos el usuario como organizador
            if (e.getOrganizador().getUsername().equals(u.getUsername())) {
                e.setOrganizador(u);
            }
            ret = true;
        }

        return ret;
    }
    
    public void insertar(Evento e) {
        System.out.println("[debug] ¡Estamos insertando un evento!");
        em.persist(e);
        System.out.println("[debug] ¿Se ha insertado el evento? " + this.buscar(e.getId()).getNombre());
    }
    
    public void actualizar(Evento e) {
        em.merge(e);
    }
    
}
