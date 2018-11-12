/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.dae1819.DAOs;

import com.dae.dae1819.pojos.Evento;
import com.dae.dae1819.pojos.Usuario;
import com.dae.dae1819.service.EmailService;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable(value="eventos")
    public Evento buscar(int id) {
        return em.find(Evento.class, id, LockModeType.OPTIMISTIC);
    }

    public boolean inscribir(Usuario u, Evento e) {
        boolean ret = false;
        Calendar fechaIns = Calendar.getInstance();
        
        // Si está lleno, añadimos el usuario a la lista de inscritos
        if (e.getAsistentes().size() >= e.getCapacidad()) {
            e.getInscritos().put(fechaIns, u);
        } else {
            // Si no está lleno, añadimos el usuario a la lista de asistentes
            e.getAsistentes().put(fechaIns, u);
            
            // Si además es el organizador, añadimos el usuario como organizador
            if (e.getOrganizador().getUsername().equals(u.getUsername())) {
                e.setOrganizador(u);
            }
            
            ret = true;
        }

        Evento newE = this.actualizar(e);
        
        return ret;
    }

    public boolean desinscribir(Usuario u, Evento e) {
        boolean ret = false;

        // Si está en la lista de asistentes, lo eliminamos
        for (Map.Entry<Calendar, Usuario> entry : e.getAsistentes().entrySet()) {
            if (entry.getValue().getUsername().equals(u.getUsername())) {
                ret = e.getAsistentes().remove(entry.getKey(), entry.getValue());
                break;
            }
        }

        // Comprobamos si hay alguien en la lista de espera
        if(e.getInscritos().size() > 0) {
            Map.Entry<Calendar, Usuario> first = null;
            // Cogemos el par <key, value> que primero se apuntó a la lista de espera
            for (Map.Entry<Calendar, Usuario> entry : e.getInscritos().entrySet()) {
                if(first == null || first.getKey().before(entry.getKey())) {
                    first = entry;
                }
            }
            
            // Lo eliminamos de la lista de espera
            e.getInscritos().remove(first.getKey(), first.getValue());
            
            // Lo inscribimos en la lista de asistentes
            this.inscribir(first.getValue(), e);
            EmailService email = new EmailService();
            String cuerpoEmail = "¡Hola " + u.getUsername() + "! Un usuario se ha desinscrito del evento " + e.getNombre() 
                        + " que se iba a celebrar el " + e.getFecha().get(Calendar.HOUR) + ":" + e.getFecha().get(Calendar.MINUTE)
                        + " del " + e.getFecha().get(Calendar.DATE) + "/" + e.getFecha().get(Calendar.MONTH) + "/" + e.getFecha().get(Calendar.YEAR)
                        + " en " + e.getLocalizacion() + " y tú eras el primero de la lista de espera, así que ¡estás dentro!.\n\n"
                        + "Contacta con el organizador entrando en la aplicación y revisando la información del evento.\n\n"
                        + "Un saludo de todo el equipo.";
                    email.sendSimpleMessage(u.getEmail(), "Te has inscrito a " + e.getNombre(), cuerpoEmail);
        }

        Evento newE = this.actualizar(e);
        
        return ret;
    }

    public void insertar(Evento e) {
        em.lock(e, LockModeType.OPTIMISTIC);
        em.persist(e);
    }

    @CacheEvict(value="eventos" , allEntries=true)
    public Evento actualizar(Evento e) {
        Evento event = em.merge(e);
        return event;
    }

}
