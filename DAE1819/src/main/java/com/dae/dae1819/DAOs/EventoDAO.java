/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.dae1819.DAOs;

import com.dae.dae1819.pojos.Evento;
import com.dae.dae1819.pojos.Usuario;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
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
        List<Evento> eventos = em.createQuery("select e from Evento e where e.nombre like '%" + nombre + "%'", Evento.class).getResultList();
        
        return eventos;
    }
    
    public List<Evento> buscarPorTipo(String tipo) {
        List<Evento> eventos = em.createQuery("select e from Evento e where e.tipo like '%" + tipo + "%'", Evento.class).getResultList();
        
        return eventos;
    }
    
    public List<Evento> buscarPorDescripcion(String descripcion) {
        List<Evento> eventos = em.createQuery("select e from Evento e where e.descripcion like '%" + descripcion + "%'", Evento.class).getResultList();
        
        return eventos;
    }
    
    public List<Evento> listar() {
        List<Evento> eventos = em.createQuery("select e from Evento e", Evento.class).getResultList();
        
        return eventos;
    }
    
    public Evento buscar(int id) {
        return em.find(Evento.class, id);
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
            e.getAsistentes().put(fechaIns, u);
            System.out.println("[debug] EventoDAO: Se ha añadido a la lista de asistentes");
            // Si además es el organizador, añadimos el usuario como organizador
            if (e.getOrganizador().getUsername().equals(u.getUsername())) {
                e.setOrganizador(u);
                System.out.println("[debug] EventoDAO: Se ha puesto a " + e.getOrganizador().getUsername() + " como organizador");
            }
            ret = true;
        }
        
        Evento newE = this.actualizar(e);
        System.out.println("[debug] newE = " + newE.getNombre() + " y sus asistentes son: ");
        
        for (Map.Entry<Calendar, Usuario> entry : newE.getAsistentes().entrySet()) {
            DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            System.out.println(sdf.format(entry.getKey().getTime()) + " - " + entry.getValue().getUsername());
        }

        return ret;
    }
    
    public boolean desinscribir(Usuario u, Evento e) {
        boolean ret = true;
        
        e.getAsistentes().forEach((fechaIns, usuario) -> {
            if(usuario == u) {
                e.getAsistentes().remove(fechaIns);
            }
        });
        
        if (!e.getInscritos().isEmpty()) {
            Calendar first = null;
            for(Calendar d : e.getInscritos().keySet()) {
                if(first == null || d.before(first)){
                    first = d;
                }
            }
            Usuario newU = e.getInscritos().get(first);
            e.getInscritos().remove(first);
            e.getAsistentes().put(first, newU);
            //TODO notificar
        }
        
        Evento newE = this.actualizar(e);
        for (Map.Entry<Calendar, Usuario> entry : newE.getAsistentes().entrySet()) {
            DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            System.out.println(sdf.format(entry.getKey().getTime()) + " - " + entry.getValue().getUsername());
        }
        
        return ret;
    }
    
    public void insertar(Evento e) {
        System.out.println("[debug] ¡Estamos insertando un evento!");
        em.persist(e);
        System.out.println("[debug] ¿Se ha insertado el evento? " + this.buscar(e.getId()).getNombre());
    }
    
    public Evento actualizar(Evento e) {
        return em.merge(e);
    }
    
}
