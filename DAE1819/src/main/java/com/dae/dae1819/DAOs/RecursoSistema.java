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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin
@RestController
@RequestMapping("/")
public class RecursoSistema {

    @Autowired
    Sistema sistema;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public Sistema obtenerSistema() {
        return sistema;
    }
    
    /* 
     * Index:
     * 
     * /                            -
     *     /eventos                 - GET
     *         /{id}                - GET POST PUT
     *             /{asistentes}    - GET
     *                /{username}   -          PUT DELETE
     *             /{inscritos}     - GET          
     *                /{username}   -              DELETE
     *             /{organizador}   - GET
     *     /usuario                 -
     *         /{username}          - GET POST
     *             /eventos         - GET
     *             /listaEspera     - GET
     *             /organizados     - GET
     *
     */

    // <editor-fold defaultstate="collapsed" desc="GET Methods">
    @RequestMapping(value = "/eventos", method = RequestMethod.GET, produces = "application/json")
    public List<EventoDTO> obtenerAsistentes(@RequestParam(defaultValue = "") String s) throws ListaEventosVacia {
        List<EventoDTO> eventos = new ArrayList();
        if (s.equals("")) {
            eventos = sistema.buscarEventos();
            if (eventos.isEmpty()) {
                throw new ListaEventosVacia("No hay ningún evento en el sistema");
            }
        } else {
            boolean nombre = true;
            try {
                List<EventoDTO> eventosNombre = sistema.buscarEventoPorNombre(s);
                for (EventoDTO e : eventosNombre) {
                    if(!eventos.contains(e)) {
                        eventos.add(e);
                    }
                }
            } catch (ListaEventosVacia e) {
                nombre = false;
            }
            
            boolean descripcion = true;
            try {
                List<EventoDTO> eventosDescripcion = sistema.buscarEventosPorDescripcion(s);
                for (EventoDTO e : eventosDescripcion) {
                    if(!eventos.contains(e)) {
                        eventos.add(e);
                    }
                }
            } catch (ListaEventosVacia e) {
                descripcion = false;
            }
            
            // Falla porque mi base de datos (dani) está creada con una versión
            // antigua y el tipo era integer. En una bbdd generada posteriormente 
            // debería fucnionar sin problemas
            
//            boolean tipo = true;
//            try {
//                List<EventoDTO> eventosTipo = sistema.buscarEventosPorTipo(s);
//                for (EventoDTO e : eventosTipo) {
//                    if(!eventos.contains(e)) {
//                       eventos.add(e);
//                    }
//                }
//            } catch (ListaEventosVacia e) {
//                tipo = false;
//            }
//
//            if (!nombre && !descripcion && !tipo) {
//                throw new ListaEventosVacia("No hay ningún evento en el sistema que coincida con esa búsqueda");
//            }

            if (!nombre && !descripcion) {
                throw new ListaEventosVacia("No hay ningún evento en el sistema que coincida con esa búsqueda");
            }
        }

        return eventos;
    }

    @RequestMapping(value = "/eventos/{id}", method = RequestMethod.GET, produces = "application/json")
    public EventoDTO obtenerEvento(@PathVariable int id) {
        EventoDTO evento = sistema.buscarEventoPorId(id);
        return evento;
    }
    
    @RequestMapping(value = "/eventos/{id}/asistentes", method = RequestMethod.GET, produces = "application/json")
    public List<UsuarioDTO> obtenerAsistentes(@PathVariable int id) throws ListaUsuariosVacia {
        List<String> asistentes = sistema.buscarEventoPorId(id).getAsistentes();
        if (asistentes.isEmpty()) {
            throw new ListaUsuariosVacia("No hay usuarios inscritos");
        }
        
        List<UsuarioDTO> usuarios = new ArrayList();
        asistentes.forEach((u) -> {
            usuarios.add(sistema.buscarUsuario(u));
        });

        return usuarios;
    }

    @RequestMapping(value = "/eventos/{id}/inscritos", method = RequestMethod.GET, produces = "application/json")
    public List<UsuarioDTO> obtenerInscritos(@PathVariable int id) throws ListaUsuariosVacia {
        List<String> asistentes = sistema.buscarEventoPorId(id).getInscritos();
        if (asistentes.isEmpty()) {
            throw new ListaUsuariosVacia("No hay usuarios en la lista de espera");
        }
        
        List<UsuarioDTO> usuarios = new ArrayList();
        asistentes.forEach((u) -> {
            usuarios.add(sistema.buscarUsuario(u));
        });

        return usuarios;
    }
    
    @RequestMapping(value = "/eventos/{id}/organizador", method = RequestMethod.GET, produces = "application/json")
    public UsuarioDTO obtenerOrganizador(@PathVariable int id) {
        UsuarioDTO usuario = sistema.buscarUsuario(sistema.buscarEventoPorId(id).getOrganizador());
        return usuario;
    }
//    
//    @RequestMapping(value = "/usuarios", method = RequestMethod.GET, produces = "application/json")
//    public UsuarioDTO obtenerPerfilUsuario() throws UsuarioIncorrecto {
//        Authentication auth = SecurityContextHolder
//            .getContext()
//            .getAuthentication();
//        UserDetails userDetail = (UserDetails) auth.getPrincipal();
//        UsuarioDTO usuario = sistema.buscarUsuario(userDetail.getUsername());
////        UsuarioDTO usuario = sistema.buscarUsuario(username);
////        
//        if (usuario == null) {
//            throw new UsuarioIncorrecto("No ha iniciado sesión");
//        }
//        
//        return usuario;
//    }

    @RequestMapping(value = "/usuarios/{username}", method = RequestMethod.GET, produces = "application/json")
    public UsuarioDTO obtenerUsuario(@PathVariable String username) throws UsuarioIncorrecto {
        UsuarioDTO usuario = sistema.buscarUsuario(username);
        if (usuario == null) {
            throw new UsuarioIncorrecto("No existe un usuario con ese username");
        }
        
        return usuario;
    }

    @RequestMapping(value = "/usuarios/{username}/eventos", method = RequestMethod.GET, produces = "application/json")
    public List<EventoDTO> obtenerEventosUsuario(@PathVariable String username) {
        UsuarioDTO usuario = sistema.buscarUsuario(username);
        List<Integer> idEventos = usuario.getEventos();
        List<EventoDTO> eventos = new ArrayList();

        idEventos.forEach((u) -> {
            eventos.add(sistema.buscarEventoPorId(u));
        });

        return eventos;
    }

    @RequestMapping(value = "/usuarios/{username}/listaEspera", method = RequestMethod.GET, produces = "application/json")
    public List<EventoDTO> obtenerEventosListaEspera(@PathVariable String username) {
        UsuarioDTO usuario = sistema.buscarUsuario(username);
        List<Integer> idEventos = usuario.getListaEspera();
        List<EventoDTO> eventos = new ArrayList();

        idEventos.forEach((u) -> {
            eventos.add(sistema.buscarEventoPorId(u));
        });

        return eventos;
    }

    @RequestMapping(value = "/usuarios/{username}/organizados", method = RequestMethod.GET, produces = "application/json")
    public List<EventoDTO> obtenerEventosOrganizados(@PathVariable String username) {
        UsuarioDTO usuario = sistema.buscarUsuario(username);
        List<Integer> idEventos = usuario.getOrganizados();
        List<EventoDTO> eventos = new ArrayList();

        idEventos.forEach((u) -> {
            eventos.add(sistema.buscarEventoPorId(u));
        });

        return eventos;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="POST Methods">
    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "/eventos/{id}", method = RequestMethod.POST, produces = "application/json")
    public void crearEvento(@PathVariable int id, @RequestBody EventoDTO evento) throws EventoIncorrecto, TokenInvalido, EventoExistente, AtributoVacio {
        if (evento == null) {
            throw new EventoIncorrecto();
        }
        
        if (sistema.buscarEventoPorId(id) != null ) {
            throw new EventoExistente();
        }
        
        if(evento.getNombre() == null || evento.getFecha() == null || evento.getTipo() == null ||
           evento.getDescripcion() == null || evento.getLocalizacion() == null || evento.getOrganizador() == null) {
            throw new AtributoVacio();
        }
        
        UsuarioDTO organizador = sistema.buscarUsuario(evento.getOrganizador());
        sistema.nuevoEvento(evento.getNombre(), evento.getFecha(), evento.getTipo(), evento.getDescripcion(), id, evento.getLocalizacion(), organizador);
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "/usuarios/{username}", method = RequestMethod.POST, produces = "application/json")
    public void crearUsuario(@PathVariable String username, @RequestBody RegistroUsuarioDTO usuario) throws UsuarioIncorrecto, UsuarioExistente, AtributoVacio {
        if (usuario == null) {
            throw new UsuarioIncorrecto();
        }
        UsuarioDTO uDTO = new UsuarioDTO();
        uDTO.setUsername(username);
        uDTO.setEmail(usuario.getEmail());
        if (sistema.buscarUsuario(uDTO.getUsername()) != null) {
            throw new UsuarioExistente();
        }
        
        if(usuario.getUsername() == null || usuario.getPassword() == null || usuario.getEmail() == null) {
            throw new AtributoVacio();
        }
        String pass = new BCryptPasswordEncoder().encode(usuario.getPassword());
        sistema.nuevoUsuario(username, pass, pass, usuario.getEmail());
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="PUT Methods">
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    @RequestMapping(value = "/eventos/{id}/asistentes/{username}", method=RequestMethod.PUT, produces="application/json")
    public void inscribirEnEvento(@PathVariable int id, @PathVariable String username) throws UsuarioIncorrecto, EventoIncorrecto {
        UsuarioDTO uDTO = sistema.buscarUsuario(username);
        if(uDTO == null) {
            throw new UsuarioIncorrecto();
        }
        
        EventoDTO eDTO = sistema.buscarEventoPorId(id);
        if(eDTO == null) {
            throw new EventoIncorrecto();
        }
        
        sistema.inscribirse(uDTO, eDTO);
    }
    
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    @RequestMapping(value = "/eventos/{id}", method=RequestMethod.PUT, produces="application/json")
    public void reactivarEvento(@PathVariable int id) throws EventoIncorrecto {
        EventoDTO eDTO = sistema.buscarEventoPorId(id);
        if(eDTO == null) {
            throw new EventoIncorrecto();
        }
        
        if(eDTO.isCancelado()) {
            sistema.reactivarEvento(eDTO, sistema.buscarUsuario(eDTO.getOrganizador()));
        } else {
            sistema.cancelarEvento(eDTO, sistema.buscarUsuario(eDTO.getOrganizador()));
        }
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="DELETE Methods">
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    @RequestMapping(value = "/eventos/{id}/asistentes/{username}", method=RequestMethod.DELETE, produces="application/json")
    public void desinscribirDeEvento(@PathVariable int id, @PathVariable String username) throws UsuarioIncorrecto, EventoIncorrecto {
        UsuarioDTO uDTO = sistema.buscarUsuario(username);
        if(uDTO == null) {
            throw new UsuarioIncorrecto();
        }
        
        EventoDTO eDTO = sistema.buscarEventoPorId(id);
        if(eDTO == null) {
            throw new EventoIncorrecto();
        }
        
        sistema.desinscribirse(uDTO, eDTO);
    }
    
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    @RequestMapping(value = "/eventos/{id}/inscritos/{username}", method=RequestMethod.DELETE, produces="application/json")
    public void desinscribirDeListaEspera(@PathVariable int id, @PathVariable String username) throws UsuarioIncorrecto, EventoIncorrecto {
        UsuarioDTO uDTO = sistema.buscarUsuario(username);
        if(uDTO == null) {
            throw new UsuarioIncorrecto();
        }
        
        EventoDTO eDTO = sistema.buscarEventoPorId(id);
        if(eDTO == null) {
            throw new EventoIncorrecto();
        }
        
        sistema.desinscribirse(uDTO, eDTO);
    }
    // </editor-fold>
}
