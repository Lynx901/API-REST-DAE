/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.dae1819.DAOs;

import com.dae.dae1819.pojos.Usuario;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author dml y jfaf
 */
@Repository
@Transactional
public class UsuarioDAO {
    @Autowire
    JdbcTemplate jdbcTemplate;
    
    public UsuarioDAO(){
        jdbcTemplate = null;
    }
    
    public Usuario buscar(String username) {
        return jdbcTemplate.queryForObject("select username from Usuario where username = ?", new Usuario(), username);
    }
    
    public void insertar(Usuario u) {
        jdbcTemplate.update("insert into Usuario(username,password,email)" + "values ( ?, ?)", u.getUsername(), u.getPassword());
    }
    
    public void actualizar(Usuario u) {
        jdbcTemplate.update("update Usuario set username = ? where username = ?", u.getUsername(), u.getUsername());
    }
    
    public void borrar(Usuario u) {
        jdbcTemplate.update("delete from Usuario where username = ?", u.getUsername());
    }
    
    public void setJdbcTemplate (JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    
}
