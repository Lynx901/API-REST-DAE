/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.dae1819.DAOs;

import com.dae.dae1819.pojos.Usuario;
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
public class UsuarioDAO {
    @PersistenceContext
    EntityManager em;
    
    public List<Usuario> listar() {
        List<Usuario> usuarios = new ArrayList();
        
        return usuarios;
    }
    
    public Usuario buscar(String username) {
        return em.find(Usuario.class, username);
    }
    
    public void insertar(Usuario u) {
        em.persist(u);
    }
    
    public void actualizar(Usuario u) {
        em.merge(u);
    }
    
}