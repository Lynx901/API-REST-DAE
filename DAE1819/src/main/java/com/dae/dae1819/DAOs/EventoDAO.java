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
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
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
        TypedQuery<Evento> query = em.createQuery("select e from Evento e where e.nombre like '%" + nombre + "%'", Evento.class);
        query.setLockMode(LockModeType.OPTIMISTIC);
        List<Evento> eventos = query.getResultList();
        
        return eventos;
    }

    public List<Evento> buscarPorTipo(String tipo) {
        TypedQuery<Evento> query = em.createQuery("select e from Evento e where e.tipo like '%" + tipo + "%'", Evento.class);
        query.setLockMode(LockModeType.OPTIMISTIC);
        List<Evento> eventos = query.getResultList();
       
        return eventos;
    }

    public List<Evento> buscarPorDescripcion(String descripcion) {
        TypedQuery<Evento> query = em.createQuery("select e from Evento e where e.descripcion like '%" + descripcion + "%'", Evento.class);
        query.setLockMode(LockModeType.OPTIMISTIC);
        List<Evento> eventos = query.getResultList();

        return eventos;
    }

    public List<Evento> listar() {
        TypedQuery<Evento> query = em.createQuery("select e from Evento e", Evento.class);
        query.setLockMode(LockModeType.OPTIMISTIC);
        List<Evento> eventos = query.getResultList();
        
        return eventos;
    }

    public Evento buscar(int id) {
        return em.find(Evento.class, id, LockModeType.OPTIMISTIC);
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
            //Comprobamos si el usuario pertenece a la lista de inscritos, de ser asi lo quitamos
            if (e.getInscritos().containsValue(u)) {
                for (Map.Entry<Calendar, Usuario> entry : e.getInscritos().entrySet()) {
                    if (entry.getValue().getUsername().equals(u.getUsername())) {
                        fechaIns = entry.getKey();
                    }
                }
                
                ret = e.getInscritos().remove(fechaIns, u);
            }
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
        boolean continuar = true;

        for (Map.Entry<Calendar, Usuario> entry : e.getAsistentes().entrySet()) {
            if (entry.getValue().getUsername().equals(u.getUsername())) {
                
                ret = e.getAsistentes().remove(entry.getKey(), entry.getValue());
                continuar = false;
                break;
            }
        }

        if (continuar) {
            for (Map.Entry<Calendar, Usuario> entry : e.getInscritos().entrySet()) {
                if (entry.getValue().getUsername().equals(u.getUsername())) {
                    
                    ret = e.getInscritos().remove(entry.getKey(), entry.getValue());
                    break;
                }
            }
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
        em.lock(e, LockModeType.OPTIMISTIC);
        System.out.println("[debug] ¿Se ha insertado el evento? " + this.buscar(e.getId()).getNombre());
    }

    public Evento actualizar(Evento e) {
        Evento event = em.merge(e);
        //em.lock(e, LockModeType.OPTIMISTIC);
        return event;
    }

}
