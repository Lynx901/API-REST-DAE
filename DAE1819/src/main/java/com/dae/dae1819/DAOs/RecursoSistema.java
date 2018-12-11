/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.dae1819.DAOs;

import com.dae.dae1819.DTOs.EventoDTO;
import com.dae.dae1819.DTOs.RegistroUsuarioDTO;
import com.dae.dae1819.DTOs.UsuarioDTO;
import com.dae.dae1819.Excepciones.AtributoVacio;
import com.dae.dae1819.Excepciones.EventoExistente;
import com.dae.dae1819.Excepciones.EventoIncorrecto;
import com.dae.dae1819.Excepciones.ListaEventosVacia;
import com.dae.dae1819.Excepciones.ListaUsuariosVacia;
import com.dae.dae1819.Excepciones.TokenInvalido;
import com.dae.dae1819.Excepciones.UsuarioExistente;
import com.dae.dae1819.Excepciones.UsuarioIncorrecto;
import com.dae.dae1819.pojos.Sistema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author dml y jfaf
 */
@RestController
@RequestMapping("/")
public class RecursoSistema {

    @Autowired
    Sistema sistema;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public Sistema obtenerSistema() {
        return sistema;
    }
    
//    @RequestMapping(value = "/eventos?nombre={nombre}", method = RequestMethod.GET, produces = "application/json")
//    public List<EventoDTO> obtenerEventosPorNombre(@RequestParam(defaultValue = "") String nombre) throws ListaEventosVacia {
//        List<EventoDTO> eventos = new ArrayList();
//        try {
//            eventos = sistema.buscarEventoPorNombre(nombre);
//        } catch (ListaEventosVacia e) {
//            throw new ListaEventosVacia(e.getMessage());
//        }
//        return eventos;
//    }
//
//    @RequestMapping(value = "/eventos?desc={descripcion}", method = RequestMethod.GET, produces = "application/json")
//    public List<EventoDTO> obtenerEventoDescripcion(@RequestParam(defaultValue = "") String descripcion) throws ListaEventosVacia {
//        List<EventoDTO> eventos = new ArrayList();
//        try {
//            eventos = sistema.buscarEventosPorDescripcion(descripcion);
//        } catch (ListaEventosVacia e) {
//            throw new ListaEventosVacia(e.getMessage());
//        }
//        return eventos;
//    }
//
//    @RequestMapping(value = "/eventos?tipo={tipo}", method = RequestMethod.GET, produces = "application/json")
//    public List<EventoDTO> obtenerEventoTipo(@RequestParam(defaultValue = "") String tipo) throws ListaEventosVacia {
//        List<EventoDTO> eventos = new ArrayList();
//        try {
//            eventos = sistema.buscarEventosPorTipo(tipo);
//        } catch (ListaEventosVacia e) {
//            throw new ListaEventosVacia(e.getMessage());
//        }
//        return eventos;
//    }

    @RequestMapping(value = "/evento/{id}", method = RequestMethod.GET, produces = "application/json")
    public EventoDTO obtenerEvento(@PathVariable int id) {
        EventoDTO evento = sistema.buscarEventoPorId(id);
        return evento;
    }

    @RequestMapping(value = "/evento/{id}/asistentes", method = RequestMethod.GET, produces = "application/json")
    public List<UsuarioDTO> obtenerAsistentes(@PathVariable int id) throws ListaUsuariosVacia {
        List<String> asistentes = sistema.buscarEventoPorId(id).getAsistentes();
        if (asistentes.isEmpty()) {
            throw new ListaUsuariosVacia("No hay usuarios inscritos");
        }
        
        List<UsuarioDTO> usuarios = new ArrayList();
        for(u : asistentes) {
            usuarios.add(sistema.buscarUsuario(u));
        }

        return usuarios;
    }

    @RequestMapping(value = "/evento/{id}/inscritos", method = RequestMethod.GET, produces = "application/json")
    public List<UsuarioDTO> obtenerInscritos(@PathVariable int id) throws ListaUsuariosVacia {
        List<String> asistentes = sistema.buscarEventoPorId(id).getAsistentes();
        if (asistentes.isEmpty()) {
            throw new ListaUsuariosVacia("No hay usuarios en la lista de espera");
        }
        
        List<UsuarioDTO> usuarios = new ArrayList();
        for(u : asistentes) {
            usuarios.add(sistema.buscarUsuario(u));
        }

        return usuarios;
    }

    @RequestMapping(value = "/usuario/{username}", method = RequestMethod.GET, produces = "application/json")
    public UsuarioDTO obtenerUsuario(@PathVariable String username) throws UsuarioIncorrecto {
        UsuarioDTO usuario = sistema.buscarUsuario(username);
        if (usuario == null) {
            throw new UsuarioIncorrecto("No existe un usuario con ese username");
        }
        
        return usuario;
    }

    @RequestMapping(value = "/usuario/{username}/eventos", method = RequestMethod.GET, produces = "application/json")
    public List<EventoDTO> obtenerEventosUsuario(@PathVariable String username) {
        UsuarioDTO usuario = sistema.buscarUsuario(username);
        List<Integer> idEventos = usuario.getEventos();
        List<EventoDTO> eventos = new ArrayList();

        idEventos.forEach((u) -> {
            eventos.add(sistema.buscarEventoPorId(u));
        });

        return eventos;
    }

    @RequestMapping(value = "/usuario/{username}/listaEspera", method = RequestMethod.GET, produces = "application/json")
    public List<EventoDTO> obtenerEventosListaEspera(@PathVariable String username) {
        UsuarioDTO usuario = sistema.buscarUsuario(username);
        List<Integer> idEventos = usuario.getListaEspera();
        List<EventoDTO> eventos = new ArrayList();

        idEventos.forEach((u) -> {
            eventos.add(sistema.buscarEventoPorId(u));
        });

        return eventos;
    }

    @RequestMapping(value = "/usuario/{username}/organizados", method = RequestMethod.GET, produces = "application/json")
    public List<EventoDTO> obtenerEventosOrganizados(@PathVariable String username) {
        UsuarioDTO usuario = sistema.buscarUsuario(username);
        List<Integer> idEventos = usuario.getOrganizados();
        List<EventoDTO> eventos = new ArrayList();

        idEventos.forEach((u) -> {
            eventos.add(sistema.buscarEventoPorId(u));
        });

        return eventos;
    }

    @RequestMapping(value = "/evento/{id}", method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void crearEvento(@PathVariable int id, @RequestBody EventoDTO evento) throws EventoIncorrecto, TokenInvalido, EventoExistente {
        if (evento == null) {
            throw new EventoIncorrecto();
        }
        
        EventoDTO eventobuscado = (EventoDTO) sistema.buscarEventoPorId(id);
        if (sistema.buscarEventoPorId(id) != null ) {
            throw new EventoExistente();
        }
        UsuarioDTO usuario = sistema.buscarUsuario(evento.getOrganizador());
        sistema.nuevoEvento(evento.getNombre(), evento.getFecha(), evento.getTipo(), evento.getDescripcion(), id, evento.getLocalizacion(), usuario);
    }

   

    @RequestMapping(value = "/usuario/{username}", method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void crearUsuario(@PathVariable String username, @RequestBody RegistroUsuarioDTO usuario) throws UsuarioIncorrecto, UsuarioExistente, AtributoVacio {
        if (usuario == null) {
            throw new UsuarioIncorrecto();
        }
        
        if (sistema.buscarUsuario(username) != null) {
            throw new UsuarioExistente();
        }
        
        if(usuario.getUsername() == null || usuario.getPassword() == null || usuario.getEmail() == null) {
            throw new AtributoVacio();
        }
        
        sistema.nuevoUsuario(username, usuario.getPassword(), usuario.getPassword(), usuario.getEmail());
        
    }
}
