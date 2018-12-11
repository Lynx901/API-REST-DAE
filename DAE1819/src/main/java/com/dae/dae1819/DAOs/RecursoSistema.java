/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.dae1819.DAOs;

import com.dae.dae1819.DTOs.EventoDTO;
import com.dae.dae1819.DTOs.UsuarioDTO;
import com.dae.dae1819.Excepciones.EventoExistente;
import com.dae.dae1819.Excepciones.EventoIncorrecto;
import com.dae.dae1819.Excepciones.ListaEventosVacia;
import com.dae.dae1819.Excepciones.TokenInvalido;
import com.dae.dae1819.Excepciones.UsuarioExistente;
import com.dae.dae1819.Excepciones.UsuarioIncorrecto;
import com.dae.dae1819.pojos.Sistema;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author dml y jfaf
 */
@RestController
@RequestMapping("/")
public class RecursoSistema {
    
    private static final Logger LOG = (Logger) LoggerFactory.getLogger(RecursoSistema.class);
    
    @Autowired
    Sistema sistema;
    
    @RequestMapping(method=RequestMethod.GET, produces="application/json")
    @ResponseStatus(value=HttpStatus.OK)
    public Sistema obtenerSistema() {
        return sistema;
    }
    
    @RequestMapping(value="/eventos?name={nombre}", method=RequestMethod.GET, produces="application/json")
    @ResponseStatus(value=HttpStatus.OK)
    public List<EventoDTO> obtenerEventosPorNombre(@AuthenticationPrincipal Principal principal, @RequestParam(defaultValue="") String nombre) throws ListaEventosVacia {
        LOG.info(new StringBuilder("User: ").append(principal.getName()).append(" requesting for person: ").append(nombre).toString());
        List<EventoDTO> eventos = new ArrayList();
        try {
            eventos = sistema.buscarEventoPorNombre(nombre);
        } catch (ListaEventosVacia e) {
            throw new ListaEventosVacia(e.getMessage());
        }
        return eventos;
    }
    
    @RequestMapping(value="/eventos?desc={descripcion}", method=RequestMethod.GET, produces="application/json")
    @ResponseStatus(value=HttpStatus.OK)
    public List<EventoDTO> obtenerEventoDescripcion(@AuthenticationPrincipal Principal principal, @RequestParam(defaultValue="") String descripcion) throws ListaEventosVacia {
        LOG.info(new StringBuilder("User: ").append(principal.getName()).append(" requesting for person: ").append(descripcion).toString());
        List<EventoDTO> eventos = new ArrayList();
        try {
            eventos = sistema.buscarEventosPorDescripcion(descripcion);
        } catch (ListaEventosVacia e) {
            throw new ListaEventosVacia(e.getMessage());
        }
        return eventos;
    }
    
    
    @RequestMapping(value="/eventos?type={tipo}", method=RequestMethod.GET, produces="application/json")
    @ResponseStatus(value=HttpStatus.OK)
    public List<EventoDTO> obtenerEventoTipo(@AuthenticationPrincipal Principal principal, @RequestParam(defaultValue="") String tipo) throws ListaEventosVacia {
        LOG.info(new StringBuilder("User: ").append(principal.getName()).append(" requesting for person: ").append(tipo).toString());
        List<EventoDTO> eventos = new ArrayList();
        try {
            eventos = sistema.buscarEventosPorTipo(tipo);
        } catch (ListaEventosVacia e) {
            throw new ListaEventosVacia(e.getMessage());
        }
        return eventos;
    }
    
    @RequestMapping(value="/eventos/{id}", method=RequestMethod.GET, produces="application/json")
    @ResponseStatus(value=HttpStatus.OK)
    public EventoDTO obtenerEvento(@AuthenticationPrincipal Principal principal, @PathVariable int id) {
        LOG.info(new StringBuilder("User: ").append(principal.getName()).append(" requesting for person: ").append(id).toString());
        EventoDTO evento = sistema.buscarEventoPorId(id);
        return evento;
    }
    
    @RequestMapping(value="/eventos/{id}/asistentes", method=RequestMethod.GET, produces="application/json")
    @ResponseStatus(value=HttpStatus.OK)
    public List<UsuarioDTO> obtenerAsistentes(@AuthenticationPrincipal Principal principal, @PathVariable int id) throws ListaEventosVacia {
        LOG.info(new StringBuilder("User: ").append(principal.getName()).append(" requesting for person: ").append(id).toString());
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
    @ResponseStatus(value=HttpStatus.OK)
    public List<UsuarioDTO> obtenerInscritos(@AuthenticationPrincipal Principal principal, @PathVariable int id) throws ListaEventosVacia {
        LOG.info(new StringBuilder("User: ").append(principal.getName()).append(" requesting for person: ").append(id).toString());
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
    @ResponseStatus(value=HttpStatus.OK)
    public UsuarioDTO obtenerUsuario(@AuthenticationPrincipal Principal principal, @PathVariable String username) {
        LOG.info(new StringBuilder("User: ").append(principal.getName()).append(" requesting for person: ").append(username).toString());
        UsuarioDTO usuario = sistema.buscarUsuario(username);
        return usuario;
    }
    
    @RequestMapping(value="/usuario/{username}/eventos", method=RequestMethod.GET, produces="application/json")
    @ResponseStatus(value=HttpStatus.OK)
    public List<EventoDTO> obtenerEventosUsuario(@AuthenticationPrincipal Principal principal, @PathVariable String username) {
        LOG.info(new StringBuilder("User: ").append(principal.getName()).append(" requesting for person: ").append(username).toString());
        UsuarioDTO usuario = sistema.buscarUsuario(username);
        List<Integer> idEventos = usuario.getEventos();
        List<EventoDTO> eventos = new ArrayList();
        
        idEventos.forEach((u) -> {
            eventos.add(sistema.buscarEventoPorId(u));
        });
        
        return eventos;
    }
    
    @RequestMapping(value="/usuario/{username}/listaEspera", method=RequestMethod.GET, produces="application/json")
    @ResponseStatus(value=HttpStatus.OK)
    public List<EventoDTO> obtenerEventosListaEspera(@AuthenticationPrincipal Principal principal, @PathVariable String username) {
        LOG.info(new StringBuilder("User: ").append(principal.getName()).append(" requesting for person: ").append(username).toString());
        UsuarioDTO usuario = sistema.buscarUsuario(username);
        List<Integer> idEventos = usuario.getListaEspera();
        List<EventoDTO> eventos = new ArrayList();
        
        idEventos.forEach((u) -> {
            eventos.add(sistema.buscarEventoPorId(u));
        });
        
        return eventos;
    }
    
    @RequestMapping(value="/usuario/{username}/organizados", method=RequestMethod.GET, produces="application/json")
    @ResponseStatus(value=HttpStatus.OK)
    public List<EventoDTO> obtenerEventosOrganizados(@AuthenticationPrincipal Principal principal, @PathVariable String username) {
        LOG.info(new StringBuilder("User: ").append(principal.getName()).append(" requesting for person: ").append(username).toString());
        UsuarioDTO usuario = sistema.buscarUsuario(username);
        List<Integer> idEventos = usuario.getOrganizados();
        List<EventoDTO> eventos = new ArrayList();
        
        idEventos.forEach((u) -> {
            eventos.add(sistema.buscarEventoPorId(u));
        });
        
        return eventos;
    }
    
    @RequestMapping(value="/eventos/{id}", method=RequestMethod.POST, produces="application/json")
    @ResponseStatus(value=HttpStatus.CREATED)
    public void crearEvento(@AuthenticationPrincipal Principal principal, @PathVariable int id, @RequestBody EventoDTO evento) throws EventoIncorrecto, TokenInvalido, EventoExistente {
        LOG.info(new StringBuilder("User: ").append(principal.getName()).append(" requesting for person: ").append(id).toString());
        if (evento == null){
            throw new EventoIncorrecto();
        }
        EventoDTO eventobuscado = (EventoDTO) sistema.buscarEventoPorId(id);
        if (eventobuscado == evento){
            throw new EventoExistente();
        }
        UsuarioDTO usuario = sistema.buscarUsuario(evento.getOrganizador());
        sistema.nuevoEvento(evento.getNombre(), evento.getFecha(), evento.getTipo(), evento.getDescripcion(), id, evento.getLocalizacion(), usuario);
    }
    
    @RequestMapping(value="/usuario/{username}", method=RequestMethod.POST, produces="application/json")
    @ResponseStatus(value=HttpStatus.CREATED)
    // TO-DO Crear DTO específico para el registro
     public void crearUsuario(@AuthenticationPrincipal Principal principal, @PathVariable String username, @RequestBody UsuarioDTO usuario) throws TokenInvalido, UsuarioIncorrecto, UsuarioExistente {
         LOG.info(new StringBuilder("User: ").append(principal.getName()).append(" requesting for person: ").append(username).toString());
        if (usuario == null){ 
             throw new UsuarioIncorrecto();
         }
         UsuarioDTO usuariobuscado = sistema.buscarUsuario(username);
         if (usuariobuscado == usuario){
             throw new UsuarioExistente();
         }
         sistema.nuevoUsuario(username, usuario.getPassword(), usuario.getPassword(),usuario.getEmail());
    }
     
    /* HANDLER Preguntar a Antonio si esto podriamos tenerlo en otra clase o archivo*/
     
    @ExceptionHandler(ListaEventosVacia.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public void listaEventosVaciaHandler(final Exception exception) {
        LOG.log(Level.WARNING, "REST Service exception: {0}", exception.getMessage());
    }
    
    @ExceptionHandler(EventoIncorrecto.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public void eventoIncorrectoHandler(final Exception exception) {
        LOG.log(Level.WARNING, "REST Service exception: {0}", exception.getMessage());
    }
    
    @ExceptionHandler(EventoExistente.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public void eventoExistenteHandler(final Exception exception) {
        LOG.log(Level.WARNING, "REST Service exception: {0}", exception.getMessage());
    }
    
     @ExceptionHandler(UsuarioIncorrecto.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public void usuarioIncorrectoHandler(final Exception exception) {
        LOG.log(Level.WARNING, "REST Service exception: {0}", exception.getMessage());
    }
    
    @ExceptionHandler(UsuarioExistente.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public void usuarioExistenteHandler(final Exception exception) {
        LOG.log(Level.WARNING, "REST Service exception: {0}", exception.getMessage());
    }
}
