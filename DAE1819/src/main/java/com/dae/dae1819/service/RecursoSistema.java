/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.dae1819.service;

import com.dae.dae1819.DTOs.EventoDTO;
import com.dae.dae1819.DTOs.UsuarioDTO;
import com.dae.dae1819.Excepciones.ListaEventosVacia;
import com.dae.dae1819.pojos.Sistema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author dml y jfaf
 */
@RestController
@RequestMapping("/")
public class RecursoSistema {
    
    Sistema sistema;
    
    @RequestMapping(method=RequestMethod.GET, produces="application/json")
    public Sistema obtenerSistema() {
        return sistema;
    }
    
    @RequestMapping(value="/eventos?Name={nombre}", method=RequestMethod.GET, produces="application/json")
    public List<EventoDTO> obtenerEventosPorNombre(@RequestParam(defaultValue="") String nombre) throws ListaEventosVacia {
        List<EventoDTO> eventos = new ArrayList();
        try {
            eventos = sistema.buscarEventoPorNombre(nombre);
        } catch (ListaEventosVacia e) {
            throw new ListaEventosVacia(e.getMessage());
        }
        return eventos;
    }
    
    @RequestMapping(value="/eventos?Desc={descripcion}", method=RequestMethod.GET, produces="application/json")
    public List<EventoDTO> obtenerEventoDescripcion(@RequestParam(defaultValue="") String descripcion) throws ListaEventosVacia {
        List<EventoDTO> eventos = new ArrayList();
        try {
            eventos = sistema.buscarEventosPorDescripcion(descripcion);
        } catch (ListaEventosVacia e) {
            throw new ListaEventosVacia(e.getMessage());
        }
        return eventos;
    }
    
    @RequestMapping(value="/eventos?Type={tipo}", method=RequestMethod.GET, produces="application/json")
    public List<EventoDTO> obtenerEventoTipo(@RequestParam(defaultValue="") String tipo) throws ListaEventosVacia {
        List<EventoDTO> eventos = new ArrayList();
        try {
            eventos = sistema.buscarEventosPorTipo(tipo);
        } catch (ListaEventosVacia e) {
            throw new ListaEventosVacia(e.getMessage());
        }
        return eventos;
    }
    
    @RequestMapping(value="/eventos/{id}", method=RequestMethod.GET, produces="application/json")
    public EventoDTO obtenerEvento(@PathVariable int id) {
        EventoDTO evento = sistema.buscarEventoPorId(id);
        return evento;
    }
    
    @RequestMapping(value="/eventos/{id}/asistentes", method=RequestMethod.GET, produces="application/json")
    public List<UsuarioDTO> obtenerAsistentes(@PathVariable int id) throws ListaEventosVacia {
        List<String> eventos = sistema.buscarEventoPorId(id).getAsistentes();
        List<UsuarioDTO> usuarios = new ArrayList();
        if (eventos.isEmpty()){
            throw new ListaEventosVacia();
        }
        eventos.forEach((u) -> {
            usuarios.add(sistema.buscarUsuario(u));
        });
        
        return usuarios;
    }
    
    @RequestMapping(value="/eventos/{id}/inscritos", method=RequestMethod.GET, produces="application/json")
    public List<UsuarioDTO> obtenerInscritos(@PathVariable int id) throws ListaEventosVacia {
        List<String> eventos = sistema.buscarEventoPorId(id).getInscritos();
        List<UsuarioDTO> usuarios = new ArrayList();
        if (eventos.isEmpty()){
            throw new ListaEventosVacia();
        }
        eventos.forEach((u) -> {
            usuarios.add(sistema.buscarUsuario(u));
        });
        
        return usuarios;
    }
    
    @RequestMapping(value="/usuario/{username}", method=RequestMethod.GET, produces="application/json")
    public UsuarioDTO obtenerUsuario(@PathVariable String username) {
        UsuarioDTO usuario = sistema.buscarUsuario(username);
        return usuario;
    }
    
    @RequestMapping(value="/usuario/{username}/eventos", method=RequestMethod.GET, produces="application/json")
    public List<EventoDTO> obtenerEventosUsuario(@PathVariable String username) {
        UsuarioDTO usuario = sistema.buscarUsuario(username);
        List<Integer> idEventos = usuario.getEventos();
        List<EventoDTO> eventos = new ArrayList();
        
        idEventos.forEach((u) -> {
            eventos.add(sistema.buscarEventoPorId(u));
        });
        
        return eventos;
    }
    
    @RequestMapping(value="/usuario/{username}/listaEspera", method=RequestMethod.GET, produces="application/json")
    public List<EventoDTO> obtenerEventosListaEspera(@PathVariable String username) {
        UsuarioDTO usuario = sistema.buscarUsuario(username);
        List<Integer> idEventos = usuario.getListaEspera();
        List<EventoDTO> eventos = new ArrayList();
        
        idEventos.forEach((u) -> {
            eventos.add(sistema.buscarEventoPorId(u));
        });
        
        return eventos;
    }
    
    @RequestMapping(value="/usuario/{username}/organizados", method=RequestMethod.GET, produces="application/json")
    public List<EventoDTO> obtenerEventosOrganizados(@PathVariable String username) {
        UsuarioDTO usuario = sistema.buscarUsuario(username);
        List<Integer> idEventos = usuario.getOrganizados();
        List<EventoDTO> eventos = new ArrayList();
        
        idEventos.forEach((u) -> {
            eventos.add(sistema.buscarEventoPorId(u));
        });
        
        return eventos;
    }
}