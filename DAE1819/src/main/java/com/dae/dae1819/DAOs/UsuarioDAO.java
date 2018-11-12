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
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
        TypedQuery<Usuario> query = em.createQuery("select u from Usuario u", Usuario.class);
        query.setLockMode(LockModeType.OPTIMISTIC);
        List<Usuario> usuarios = query.getResultList();

        return usuarios;
    }

    public Usuario buscar(String username) {
        Usuario result = em.find(Usuario.class, username, LockModeType.OPTIMISTIC);
        return result;
    }

    public boolean inscribir(Usuario u, Evento e) {
        boolean ret = false;

        // Si está lleno, añadimos el evento a la lista de espera
        if (e.getAsistentes().size() >= e.getCapacidad()) {
            System.out.println("[debug] UsuarioDAO: El evento está lleno, añadiendo a la lista de espera");

            u.getListaEspera().add(e);
        } else {
            // Si no está lleno, añadimos el evento a la lista de eventos

            u.getEventos().add(e);
            System.out.println("[debug] UsuarioDAO: Se ha añadido a la lista de eventos");
            // Si además es el organizador, añadimos el evento a la lista de organizados
            if (e.getOrganizador().getUsername().equals(u.getUsername())) {

                u.getOrganizados().add(e);
                System.out.println("[debug] UsuarioDAO: Se ha añadido a la lista de organizados");
            }
            ret = true;
        }

        Usuario newU = this.actualizar(u);

        System.out.println("[debug] newU = " + newU.getUsername() + " y sus eventos son: ");

        for (Evento entry : newU.getEventos()) {
            DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            System.out.println(entry.getNombre());
        }

        return ret;
    }

    public boolean desinscribir(Usuario u, Evento e) {
        boolean ret = true;

        if (u.getEventos().remove(e)) {
            ret = true;
        }

        Usuario newU = this.actualizar(u);

        System.out.println("[debug] newU = " + newU.getUsername() + " y sus eventos son: ");

        newU.getEventos().forEach((evento) -> {
            System.out.println(evento.getNombre());
        });

        return ret;
    }

    public void insertar(Usuario u) {
        System.out.println("[debug] ¡Estamos insertando un usuario!");
        em.persist(u);
         em.lock(u, LockModeType.OPTIMISTIC);
        System.out.println("[debug] ¿Se ha insertado el usuario? " + this.buscar(u.getUsername()).getUsername());
    }

    public Usuario actualizar(Usuario u) {
        Usuario usu = em.merge(u);
        //em.lock(u, LockModeType.OPTIMISTIC);
        return usu;
    }

}
