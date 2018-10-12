/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.dae1819.pojos;

import com.dae.dae1819.interfaces.SistemaInterface;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import com.dae.dae1819.DTOs.EventoDTO;
import com.dae.dae1819.DTOs.UsuarioDTO;
import java.util.concurrent.ThreadLocalRandom;


/**
 *
 * @author dml y jfaf
 */
public class Sistema extends SistemaInterface{

    private String nombre;
    private Map<String, Usuario> usuarios;
    private Map<String, Evento> eventos;

    public Sistema() {
        usuarios = new TreeMap();
        eventos = new TreeMap();
    }
    
    public Sistema(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the usuarios
     */
    public Map<String, Usuario> getUsuarios() {
        return usuarios;
    }

    /**
     * @param usuarios the usuarios to set
     */
    public void setUsuarios(Map<String, Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    /**
     * @return the eventos
     */
    public Map<String, Evento> getEventos() {
        return eventos;
    }

    /**
     * @param eventos the eventos to set
     */
    public void setEventos(Map<String, Evento> eventos) {
        this.eventos = eventos;
    }
    
    /**
     * Comprueba si el token de sesión es válido
     * @param token el token a comprobar
     * @return true si el token es válido, false si no
     */
    @Override
    public boolean isTokenValid(Integer token) {
        if(token == -1) {
            return false;
        }
        
        for (Map.Entry<String, Usuario> entry : usuarios.entrySet()) {
            if(entry != null) {
                Usuario u = entry.getValue();
                if (u.getToken() != null && u.getToken().equals(token)){
                    return true;
                }
            }
        }
        return false;
    }
    
    /* ACCIONES USUARIOS SIN LOGEAR */
    /**
     * Registra a un usuario en el sistema
     * @param username el nombre de usuario
     * @param password la contraseña del usuario
     * @param email el email del usuario
     */
    @Override
    public void nuevoUsuario(String username, String password, String email){
        Usuario usuario = new Usuario(username, password, email);
        usuarios.put(username, usuario);
    };
    
    /**
     * Inicia la sesión de un usuario registrado en el sistema
     * @param username el nombre de usuario
     * @param password la contraseña del usuario
     * @return un token válido si se ha iniciado sesión correctamente, 0 si no
     */
    @Override
    public Integer login(String username, String password) {
        Usuario user = usuarios.get(username);
        if (user != null){
            if (user.getPassword().equals(password)) {
                Integer token = ThreadLocalRandom.current().nextInt(10000000, 100000000);
                user.setToken(token);
                return token;
            }
        }
        return 0;
    };
    
    /**
     * Busca un evento por el nombre del mismo
     * @param nombre el nombre del evento a buscar
     * @return un EventoDTO del evento encontrado, o un EventoDTO vacío si no lo encuentra
     */
    @Override
    public EventoDTO buscarEventoPorNombre(String nombre) {
        EventoDTO e = new EventoDTO();
        for (Map.Entry<String, Evento> entry : eventos.entrySet()) {
            if (entry.getValue().getNombre().equalsIgnoreCase(nombre)){
                e = eventoToDTO(entry.getValue());
            }
        }
        return e;
    };
    
    /**
     * Busca un evento por el tipo del mismo
     * @param tipo el tipo del evento a buscar
     * @return una lista de EventoDTO encontrados, o una lista vacía si no encuentra ninguno
     */
    @Override
    public List<EventoDTO> buscarEventosPorTipo(String tipo){
        List<EventoDTO> eventosPorTipo = new ArrayList();
        
        for (Map.Entry<String, Evento> entry : eventos.entrySet()) {
            if (entry.getValue().getTipo().equalsIgnoreCase(tipo)){
                EventoDTO e = eventoToDTO(entry.getValue());
                eventosPorTipo.add(e);
            }
        }
        return eventosPorTipo;
    };
    
    /**
     * Busca un evento por la descripción del mismo
     * @param descripcion la descripción del evento a buscar
     * @return una lista de EventoDTO encontrados, o una lista vacía si no encuentra ninguno
     */
    @Override
    public List<EventoDTO> buscarEventosPorDescripcion(String descripcion){
        List<EventoDTO> eventosPorDescripcion = new ArrayList();
        
        for (Map.Entry<String, Evento> entry : eventos.entrySet()) {
            if (entry.getValue().getDescripcion().contains(descripcion)) {
                
                EventoDTO e = eventoToDTO(entry.getValue());
                eventosPorDescripcion.add(e);
            }
        }
        
        return eventosPorDescripcion;
    };
    
    /**
     * Lista todos los eventos del sistema
     * @return una lista con todos los eventos creados en forma DTO (vacía si no encuentra ninguno)
     */
    @Override
    public List<EventoDTO> buscarEventos() {
        List<EventoDTO> lista = new ArrayList();
        eventos.entrySet().forEach((entry) -> {
            EventoDTO e = eventoToDTO(entry.getValue());
            lista.add(e);
        });
        return lista;
    }
    
    /* ACCIONES USUARIOS LOGEADOS */
    /**
     * Crea un nuevo evento
     * @param nombre el nombre del evento
     * @param fecha la fecha en la que se realiza el evento
     * @param tipo el tipo del evento
     * @param descripcion la descripción del evento
     * @param capacidad la capacidad de asistentes al evento
     * @param localizacion el lugar donde se realiza el evento
     * @param organizador el usuario que ha creado el evento
     */
    @Override
    public void nuevoEvento(String nombre,      Date fecha,         String tipo, 
                            String descripcion, Integer capacidad,  String localizacion,
                            String organizador) {
        
        Usuario u = usuarios.get(organizador);
        Evento evento = new Evento(nombre, fecha, tipo, descripcion, capacidad, localizacion, u);
        
        u.inscribirEnEvento(evento);
        
        eventos.put(nombre, evento);
    };
    
    /**
     * Cancela un evento, borrando en cascada
     * @param nombreEvento el nombre del evento a borrar
     * @return true si se ha cancelado bien, false si ha habido algún error
     */
    @Override
    public boolean cancelarEvento(String nombreEvento) {   
        return eventos.get(nombreEvento).cancelar();
    };
    
    /**
     * Busca un usuario por su nombre de usuario
     * @param username el nombre de usuario a buscar
     * @return un UsuarioDTO del usuario encontrado, o uno vacío si no encuentra nada 
     */
    @Override
    public UsuarioDTO buscarUsuario(String username) {
        Usuario u = new Usuario();
        
        u = usuarios.get(username);
        UsuarioDTO usuarioDTO = usuarioToDTO(u);
        
        return usuarioDTO;        
    }
    
    /**
     * Inscribe a un usuario en un evento
     * @param uDTO el usuario al que se inscribirá en el evento
     * @param eDTO el evento en el que se inscribirá el usuario
     * @return true si se inscribe al usuario, false si entra en la lista de espera
     */
    @Override
    public boolean inscribirse(UsuarioDTO uDTO, EventoDTO eDTO) {
        boolean ret = false;
        
        Usuario u = usuarios.get(uDTO.getUsername());
        Evento e = eventos.get(eDTO.getNombre());
        if(!e.getAsistentes().contains(u)) { // Comprobamos que no esté el usuario ya inscrito previamente
            if(u.inscribirEnEvento(e)) {
                ret = true;
            }
            // Si entra en la lista de espera, igualmente los incluímos en el mapa con las listas actualizadas
            usuarios.replace(uDTO.getUsername(), u);
            eventos.replace(eDTO.getNombre(), e);
        }
        
        return ret;
    }
    
    /**
     * Desinscribe a un usuario de un evento
     * @param uDTO el usuario al que se desinscribirá del evento
     * @param eDTO el evento del que se desinscribirá el usuario
     * @return true si se ha desinscrito correctamente, false si no
     */
    @Override
    public boolean desinscribirse(UsuarioDTO uDTO, EventoDTO eDTO) {
        boolean ret = false;
        Usuario u = usuarios.get(uDTO.getUsername());
        Evento e = eventos.get(eDTO.getNombre());
        
        if(e.getAsistentes().contains(u)) { // Comprobamos que el usuario esté inscrito
            if(u.desinscribir(e)) {
                usuarios.replace(uDTO.getUsername(), u);
                eventos.replace(eDTO.getNombre(), e);
                ret = true;
            }
        }
        
        return ret;
    }
    
    /**
     * Busca los eventos en los que se ha inscrito el usuario
     * @param user usuario del que se comprobará el listado de eventos
     * @return una lista de EventoDTO con los eventos inscritos, o una vacía si no encuentra ninguno
     */
    @Override
    public List<EventoDTO> buscarEventosInscritos(UsuarioDTO user) {
        List<EventoDTO> eventosInscritos = new ArrayList();
        
        for (Evento e : usuarios.get(user.getUsername()).getEventos()) {
            EventoDTO eDTO = this.buscarEventoPorNombre(e.getNombre());
            eventosInscritos.add(eDTO);
        }
        
        return eventosInscritos;
    }
    
    /**
     * Busca los eventos organizados por el usuario
     * @param user usuario del que se comprobará el listado de eventos organizados
     * @return una lista de EventoDTO con los eventos organizados, o una vacía si no encuentra ninguno
     */
    @Override
    public List<EventoDTO> buscarEventosOrganizados(UsuarioDTO user) {
        List<EventoDTO> eventosOrganizados = new ArrayList();
        
        for (Evento e : usuarios.get(user.getUsername()).getOrganizados()) {
            EventoDTO eDTO = this.buscarEventoPorNombre(nombre);
            eventosOrganizados.add(eDTO);
        }
        
        return eventosOrganizados;
    }
    
    /**
     * Mapea un Usuario en un UsuarioDTO
     * @param u el usuario a mapear
     * @return UsuarioDTO mapeado
     */
    public UsuarioDTO usuarioToDTO(Usuario u) {
        UsuarioDTO uDTO = new UsuarioDTO();
        uDTO.setUsername(u.getUsername());
        uDTO.setEmail(u.getEmail());
        
        List<String> e = new ArrayList();
        if(!u.getEventos().isEmpty()) {
            u.getEventos().forEach((evento) -> {
                e.add(evento.getNombre());
            });
            uDTO.setEventos(e);
        }
        
        if(!u.getOrganizados().isEmpty()) {
            List<String> o = new ArrayList();
            u.getOrganizados().forEach((organizado) -> {
                o.add(organizado.getNombre());
            });
            uDTO.setOrganizados(o);
        }
        
        if(!u.getListaEspera().isEmpty()) {
            List<String> l = new ArrayList();
            u.getListaEspera().forEach((listaEspera) -> {
                l.add(listaEspera.getNombre());
            });
            uDTO.setListaEspera(l);
        }
        
        return uDTO;
    }
    
    /**
     * Mapea un Evento en un EventoDTO
     * @param e el evento a mapear
     * @return EventoDTO mapeado
     */
    public EventoDTO eventoToDTO(Evento e) {
        EventoDTO eDTO = new EventoDTO();
        eDTO.setNombre(e.getNombre());
        eDTO.setDescripcion(e.getDescripcion());
        eDTO.setFecha(e.getFecha());
        eDTO.setLocalizacion(e.getLocalizacion());
        eDTO.setCapacidad(e.getCapacidad());
        
        if(!e.getAsistentes().isEmpty()) {
            List<String> asistentes = new ArrayList();
            e.getAsistentes().forEach((asistente) -> {
                asistentes.add(asistente.getUsername());
            });
            eDTO.setAsistentes(asistentes);
        } else {
            eDTO.setAsistentes(new ArrayList());
        }
        
        eDTO.setTipo(e.getTipo());
        eDTO.setOrganizador(e.getOrganizador().getUsername());
        
        return eDTO;
    }
    
    // BORRAR ANTES DE ENVIAR LA PRÁCTICA
    /**
     * Muestra los datos de todas las instancias en memoria con las que se trabaja
     */
    @Override
    public void godMode() {
        for (Map.Entry<String, Usuario> entry : usuarios.entrySet()) {
            if(entry != null) {
                Usuario u = entry.getValue();
                System.out.println("|-------------------------------------------------------------------------|");
                System.out.println("[debug]- Username:\t" + u.getUsername());
                System.out.println("[debug]- Email:\t\t" + u.getEmail());
                System.out.println("[debug]- Contraseña:\t" + u.getPassword());
                System.out.println("[debug]- Eventos Inscritos:\t" + u.getEventos().size());
                List<Evento> eventos = u.getEventos();
                for(Evento evento : eventos) {
                    System.out.println("\t|-----------------------------------------------------------------|");
                    System.out.println("\t[debug]- Nombre: \t\t" + evento.getNombre());
                    System.out.println("\t[debug]- Descripción: \t\t" + evento.getDescripcion());
                    System.out.println("\t[debug]- Fecha: \t\t" + evento.getFecha().toString());
                    System.out.println("\t[debug]- Tipo: \t\t\t" + evento.getTipo());
                    System.out.println("\t[debug]- Lugar: \t\t" + evento.getLocalizacion());
                    System.out.println("\t[debug]- Organizador:\t\t" + evento.getOrganizador().getUsername());
                    if((evento.getCapacidad() - evento.getAsistentes().size()) > 0) {
                        System.out.println("\t[debug]- Plazas disponibles: \t" + (evento.getCapacidad() - evento.getAsistentes().size()) + "/" + evento.getCapacidad());
                    } else {
                        System.out.println("\t[debug]- Plazas disponibles:\t" + 0 + "/" + evento.getCapacidad());
                        System.out.println("\t[debug]- En lista de espera:\t" + (evento.getAsistentes().size() - evento.getCapacidad()));
                    }
                    System.out.println("\t[debug]- Usuarios inscritos:\t");
                    for(Usuario usuario : evento.getAsistentes()) {
                        System.out.println("\t\t[debug]- Username:\t " + usuario.getUsername());
                    }
                }
                
                System.out.println("[debug]- Eventos Organizados:\t" + u.getOrganizados().size());
                List<Evento> eventosOrganizados = u.getOrganizados();
                for(Evento evento : eventosOrganizados) {
                    System.out.println("\t|-----------------------------------------------------------------|");
                    System.out.println("\t[debug]- Nombre: \t\t" + evento.getNombre());
                    System.out.println("\t[debug]- Descripción: \t\t" + evento.getDescripcion());
                    System.out.println("\t[debug]- Fecha: \t\t" + evento.getFecha().toString());
                    System.out.println("\t[debug]- Tipo: \t\t\t" + evento.getTipo());
                    System.out.println("\t[debug]- Lugar: \t\t" + evento.getLocalizacion());
                    System.out.println("\t[debug]- Plazas máximas: \t" + evento.getCapacidad());
                    System.out.println("\t[debug]- Organizador:\t\t" + evento.getOrganizador().getUsername());
                    if((evento.getCapacidad() - evento.getAsistentes().size()) > 0) {
                        System.out.println("\t[debug]- Plazas disponibles:\t" + (evento.getCapacidad() - evento.getAsistentes().size()));
                    } else {
                        System.out.println("\t[debug]- Plazas disponibles:\t" + 0);
                        System.out.println("\t[debug]- En lista de espera:\t" + (evento.getAsistentes().size() - evento.getCapacidad()));
                    }
                    System.out.println("\t[debug]- Usuarios inscritos:\t");
                    for(Usuario usuario : evento.getAsistentes()) {
                        System.out.println("\t\t[debug]- Username:\t " + usuario.getUsername());
                    }
                }
            }
        }
    }
    
}
