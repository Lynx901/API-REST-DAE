/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.dae1819.DAOs;


import com.dae.dae1819.pojos.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *
 * @author dani
 */
@Component
public class DatosUsuarios implements UserDetailsService{
	@Autowired
	UsuarioDAO usuarios;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarios.buscar(username);
		
		if(usuario == null) {
			throw new UsernameNotFoundException(username);
		}
                
		return User.withUsername(username).password(new BCryptPasswordEncoder().encode(usuario.getPassword())).roles("USUARIO").build();
	}
}