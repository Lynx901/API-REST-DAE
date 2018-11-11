/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.dae1819.pojos;

import com.dae.dae1819.DAOs.EventoDAO;
import com.dae.dae1819.DAOs.UsuarioDAO;
import com.dae.dae1819.interfaces.SistemaInterface;
import java.util.ArrayList;
import java.util.List;
import com.dae.dae1819.DTOs.EventoDTO;
import com.dae.dae1819.DTOs.UsuarioDTO;
import com.dae.dae1819.Excepciones.ListaEventosVacia;
import com.dae.dae1819.Excepciones.TokenInvalido;
import com.dae.dae1819.Excepciones.UsuarioExistente;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author dml y jfaf
 */
public class Sistema extends SistemaInterface {

    private String nombre;
    private int lastID;
    
    @Autowired
    private UsuarioDAO usuarios;
    
    @Autowired
    private EventoDAO eventos;
    
    private List<Integer> tokenConectados;

    public Sistema() {
        this.tokenConectados = new ArrayList();
        this.lastID = 0;
    }

    public Sistema(String nombre) {
        this.nombre = nombre;
        this.tokenConectados = new ArrayList();
        this.lastID = 0;
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

   
    @Override
    public boolean isTokenValid(Integer token) {
        boolean ret = false;

        if (token != 0) {
            ret = tokenConectados.contains(token);
        }

        return ret;
    }

    /*
     ***************************************************************************
     ***************************************************************************
     ************ ACCIONES USUARIOS QUE NO HAN INICIADO SESIÓN *****************
     ***************************************************************************
     ***************************************************************************
     */
    
    
    @Override
    public boolean nuevoUsuario(String username, String password, String password2, String email) throws UsuarioExistente {
        boolean ret = false;

        if (password.equals(password2)) {
            Usuario usuario = new Usuario(username, password, email);
                usuarios.insertar(usuario);
                //TODO Controlar excepción

            ret = true;
        }

        return ret;
    }

    
    @Override
    public UsuarioDTO login(String username, String password) {
        UsuarioDTO ret = new UsuarioDTO();
        
        Usuario user = usuarios.buscar(username);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                Integer token = ThreadLocalRandom.current().nextInt(10000000, 100000000);
                tokenConectados.add(token);
                UsuarioDTO uDTO = this.usuarioToDTO(user);
                uDTO.setToken(token);
                ret = uDTO;
            }
        }
        return ret;
    }
    
   
    @Override
    public UsuarioDTO logout(UsuarioDTO uDTO) {
        UsuarioDTO ret;
        
        if(this.isTokenValid(uDTO.getToken())) {
            tokenConectados.remove(uDTO.getToken());
            ret = new UsuarioDTO();
        } else {
            ret = uDTO;
        }
        
        return ret;
    }
    
    @Override
    public EventoDTO buscarEventoPorId(int id) {
        return this.eventoToDTO(eventos.buscar(id));
    }

    
    @Override
    public List<EventoDTO> buscarEventoPorNombre(String nombre) throws ListaEventosVacia {
        List<Evento> eventosBuscados = eventos.buscarPorNombre(nombre);
        if(eventosBuscados.isEmpty()) { throw new ListaEventosVacia("La lista de eventos está vacía\n", new Exception()); }
        
        List<EventoDTO> eventosBuscadosDTO = new ArrayList();
        eventosBuscados.forEach((evento) -> {
            eventosBuscadosDTO.add(this.eventoToDTO(evento));
        }); 
        
        return eventosBuscadosDTO;
    }
    
    @Override
    public List<EventoDTO> buscarEventosPorTipo(String tipo) throws ListaEventosVacia {
        List<Evento> eventosBuscados = eventos.buscarPorTipo(tipo);
        if(eventosBuscados.isEmpty()) { throw new ListaEventosVacia("La lista de eventos está vacía\n", new Exception()); }

        List<EventoDTO> eventosBuscadosDTO = new ArrayList();
        eventosBuscados.forEach((evento) -> {
            eventosBuscadosDTO.add(this.eventoToDTO(evento));
        }); 
        
        return eventosBuscadosDTO;
    }
    
    @Override
    public List<EventoDTO> buscarEventosPorDescripcion(String descripcion) throws ListaEventosVacia {
        List<Evento> eventosBuscados = eventos.buscarPorDescripcion(descripcion);
        if(eventosBuscados.isEmpty()) { throw new ListaEventosVacia("La lista de eventos está vacía\n", new Exception()); }
        
        List<EventoDTO> eventosBuscadosDTO = new ArrayList();
        eventosBuscados.forEach((evento) -> {
            eventosBuscadosDTO.add(this.eventoToDTO(evento));
        }); 
        
        return eventosBuscadosDTO;
    }

    
    @Override
    public List<EventoDTO> buscarEventos() throws ListaEventosVacia {
        List<Evento> eventosBuscados = eventos.listar();
        if(eventosBuscados.isEmpty()) { throw new ListaEventosVacia("La lista de eventos está vacía\n", new Exception()); }
        
        List<EventoDTO> eventosBuscadosDTO = new ArrayList();
        eventosBuscados.forEach((evento) -> {
            eventosBuscadosDTO.add(this.eventoToDTO(evento));
        }); 
        
        return eventosBuscadosDTO;
    }

    /*
     ***************************************************************************
     ***************************************************************************
     *************** ACCIONES USUARIOS QUE HAN INICIADO SESIÓN *****************
     ***************************************************************************
     ***************************************************************************
     */
    
    @Override
    public int nuevoEvento(String nombre, Calendar fecha, String tipo,
            String descripcion, Integer capacidad, String localizacion,
            UsuarioDTO organizador)  throws TokenInvalido {
        int ret = -1;
        
        System.out.println("[debug] Evento: " + organizador.getUsername());
        System.out.println("[debug] El organizadorDTO es: " + organizador.getUsername());
        Usuario u = usuarios.buscar(organizador.getUsername());
        System.out.println("[debug] El organizador es: " + u.getUsername());
        if (!this.isTokenValid(organizador.getToken())) {
            throw new TokenInvalido("El token no es válido, vuelva a iniciar sesión.", new Exception()); 
        } else {
            Evento e = new Evento(lastID++, nombre, fecha, tipo, descripcion, capacidad, localizacion, u);
            System.out.println("[debug] nombre: " + e.getNombre() + " tipo: " + e.getTipo() + " capacidad: " + e.getCapacidad() + " organizador: " + e.getOrganizador().getUsername());
            eventos.insertar(e);
            
            ret = e.getId();
        }
        
        return ret;
    }
    

    
    @Override
    public boolean cancelarEvento(EventoDTO eDTO, UsuarioDTO uDTO) {
        boolean ret;
        //TODO notificacion
        if (!this.isTokenValid(uDTO.getToken())) {
            ret = false;
        } else if(!eDTO.getOrganizador().equals(uDTO.getUsername())){
            ret = false;
        } else {
            Evento e = eventos.buscar(eDTO.getId());
            e.setCancelado(true);
            eDTO.setCancelado(true);
            
            eventos.actualizar(e);
            ret = true;
        }
        
        return ret;
    }

    
    @Override
    public boolean reactivarEvento(EventoDTO eDTO, UsuarioDTO uDTO) {
        boolean ret;
        //TODO notificacion
        if (!this.isTokenValid(uDTO.getToken())) {
            ret = false;
        } else if(!eDTO.getOrganizador().equals(uDTO.getUsername())){
            ret = false;
        } else {
            Evento e = eventos.buscar(eDTO.getId());
            e.setCancelado(false);
            eDTO.setCancelado(false);

            eventos.actualizar(e);
            ret = true;
        }
        return ret;
    }

    
    @Override
    public UsuarioDTO buscarUsuario(String username) {
        return usuarioToDTO(usuarios.buscar(username));
    }

    
    @Override
    public boolean inscribirse(UsuarioDTO uDTO, EventoDTO eDTO) throws TokenInvalido {
        if (!this.isTokenValid(uDTO.getToken())) {
            throw new TokenInvalido("El token no es válido, vuelva a iniciar sesión.", new Exception()); 
        }
        boolean ret = false;

        Usuario u = usuarios.buscar(uDTO.getUsername());
        Evento e = eventos.buscar(eDTO.getId());
        
        if (!e.getAsistentes().containsValue(u)) { // Comprobamos que no esté el usuario ya inscrito previamente
            ret = eventos.inscribir(u, e) && usuarios.inscribir(u, e);
        }
        
        return ret;
        
    }

    
    @Override
    public boolean desinscribirse(UsuarioDTO uDTO, EventoDTO eDTO) throws TokenInvalido {
        if (!this.isTokenValid(uDTO.getToken())) {
            throw new TokenInvalido("El token no es válido, vuelva a iniciar sesión.", new Exception()); 
        }
        
        boolean ret = false;
        boolean exist = false;
        Usuario u = usuarios.buscar(uDTO.getUsername());
        Evento e = eventos.buscar(eDTO.getId());    
        
        for (Map.Entry<Calendar,Usuario> entry : e.getAsistentes().entrySet()){
            if (entry.getValue().getUsername().equals(u.getUsername())){
                exist = true;
                break;
            }
        }
        
       if (exist) {
           ret = eventos.desinscribir(u, e) && usuarios.desinscribir(u, e);
           System.out.println(ret);
        } 
        
        if (ret && e.getInscritos().size() > 0) {
            Calendar fecha = Calendar.getInstance();
            for(Map.Entry<Calendar, Usuario> entry : e.getInscritos().entrySet()) {
                if (entry.getKey().compareTo(fecha) < 0){
                    fecha = entry.getKey();
                }
            }
            u = e.getInscritos().get(fecha);
            System.out.println("El usuario " + u.getUsername() + " se va a desinscribir de " + e.getNombre());
            ret = eventos.inscribir(u, e) && usuarios.inscribir(u, e);
        }
        
        return ret;
    }

    
    @Override
    public List<EventoDTO> buscarEventosInscritos(UsuarioDTO uDTO) throws ListaEventosVacia {
        //TODO if(!this.isTokenValid(uDTO.getToken())) {throw new TokenNoValido() ;}
        Set<Evento> eventosBuscados = usuarios.buscar(uDTO.getUsername()).getEventos();
        if(eventosBuscados.isEmpty()) { throw new ListaEventosVacia("La lista de eventos está vacía", new Exception()); }
        
        List<EventoDTO> eventosBuscadosDTO = new ArrayList();
        eventosBuscados.forEach((evento) -> {
            eventosBuscadosDTO.add(this.eventoToDTO(evento));
        }); 
        
        return eventosBuscadosDTO;
    }

    
    @Override
    public List<EventoDTO> buscarEventosOrganizados(UsuarioDTO uDTO) throws ListaEventosVacia {
        //TODO if(!this.isTokenValid(uDTO.getToken())) {throw new TokenNoValido() ;}
        List<Evento> eventosBuscados = usuarios.buscar(uDTO.getUsername()).getOrganizadosLista();
        if(eventosBuscados.isEmpty()) { throw new ListaEventosVacia("La lista de eventos está vacía", new Exception()); }
        
        List<EventoDTO> eventosBuscadosDTO = new ArrayList();
        eventosBuscados.forEach((evento) -> {
            eventosBuscadosDTO.add(this.eventoToDTO(evento));
        }); 
        
        return eventosBuscadosDTO;
    }

    
    public UsuarioDTO usuarioToDTO(Usuario u) {
        UsuarioDTO uDTO = new UsuarioDTO();
        uDTO.setUsername(u.getUsername());
        uDTO.setEmail(u.getEmail());

        List<Integer> e = new ArrayList();
        if (!u.getEventos().isEmpty()) {
            u.getEventos().forEach((evento) -> {
                e.add(evento.getId());
            });
            uDTO.setEventos(e);
        }

        List<Integer> o = new ArrayList();
        if (!u.getOrganizados().isEmpty()) {
            u.getOrganizadosLista().forEach((organizado) -> {
                o.add(organizado.getId());
            });
            uDTO.setOrganizados(o);
        }

        List<Integer> l = new ArrayList();
        if (!u.getListaEspera().isEmpty()) {
            u.getListaEspera().forEach((listaEspera) -> {
                l.add(listaEspera.getId());
            });
            uDTO.setListaEspera(l);
        }

        return uDTO;
    }

    
    public EventoDTO eventoToDTO(Evento e) {
        EventoDTO eDTO = new EventoDTO();
        eDTO.setId(e.getId());
        eDTO.setNombre(e.getNombre());
        eDTO.setDescripcion(e.getDescripcion());
        eDTO.setFecha(e.getFecha());
        eDTO.setLocalizacion(e.getLocalizacion());
        eDTO.setCapacidad(e.getCapacidad());
        eDTO.setCancelado(e.isCancelado());

        if (!e.getAsistentes().isEmpty()) {
            List<String> asistentes = new ArrayList();
            e.getAsistentesLista().forEach((asistente) -> {
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

    /*
     ***************************************************************************
     ***************************************************************************
     **************************** MODO DESARROLLADO ****************************
     ***************************************************************************
     ***************************************************************************
     */
    
    
    @Override
    public boolean godMode(String pass) {
        boolean ret = true;

        if (!pass.equals("dae1819")) {
            ret = false;
        } else {
            for (Usuario u : usuarios.listar()) {
                if (u != null) {
                    System.out.println("|-------------------------------------------------------------------------|");
                    System.out.println("[debug]- Username:\t" + u.getUsername());
                    System.out.println("[debug]- Email:\t\t" + u.getEmail());
                    System.out.println("[debug]- Contraseña:\t" + u.getPassword());
                    System.out.println("[debug]- Eventos Inscritos:\t" + u.getEventos().size());
                    Set<Evento> leventos = u.getEventos();
                    for (Evento evento : leventos) {
                        System.out.println("\t|-----------------------------------------------------------------|");
                        System.out.println("\t[debug]- Nombre: \t\t" + evento.getNombre());
                        System.out.println("\t[debug]- Descripción: \t\t" + evento.getDescripcion());
                        System.out.println("\t[debug]- Fecha: \t\t" + evento.getFecha().toString());
                        System.out.println("\t[debug]- Tipo: \t\t\t" + evento.getTipo());
                        System.out.println("\t[debug]- Lugar: \t\t" + evento.getLocalizacion());
                        System.out.println("\t[debug]- Organizador:\t\t" + evento.getOrganizador().getUsername());
                        if ((evento.getCapacidad() - evento.getAsistentes().size()) > 0) {
                            System.out.println("\t[debug]- Plazas disponibles: \t" + (evento.getCapacidad() - evento.getAsistentes().size()) + "/" + evento.getCapacidad());
                        } else {
                            System.out.println("\t[debug]- Plazas disponibles:\t" + 0 + "/" + evento.getCapacidad());
                            System.out.println("\t[debug]- En lista de espera:\t" + (evento.getAsistentes().size() - evento.getCapacidad()));
                        }
                        System.out.println("\t[debug]- Usuarios inscritos:\t");
                        evento.getAsistentesLista().forEach((usuario) -> {
                            System.out.println("\t\t[debug]- Username:\t " + usuario.getUsername());
                        });
                    }

                    System.out.println("[debug]- Eventos Organizados:\t" + u.getOrganizados().size());
                    List<Evento> eventosOrganizados = u.getOrganizadosLista();
                    for (Evento evento : eventosOrganizados) {
                        System.out.println("\t|-----------------------------------------------------------------|");
                        System.out.println("\t[debug]- Nombre: \t\t" + evento.getNombre());
                        System.out.println("\t[debug]- Descripción: \t\t" + evento.getDescripcion());
                        System.out.println("|- Fecha: \t\t" + evento.getFecha().get(Calendar.HOUR) + ":" + evento.getFecha().get(Calendar.MINUTE)
                        + " del " + evento.getFecha().get(Calendar.DATE) + "/" + evento.getFecha().get(Calendar.MONTH) + "/" + evento.getFecha().get(Calendar.YEAR));
                        System.out.println("\t[debug]- Tipo: \t\t\t" + evento.getTipo());
                        System.out.println("\t[debug]- Lugar: \t\t" + evento.getLocalizacion());
                        System.out.println("\t[debug]- Plazas máximas: \t" + evento.getCapacidad());
                        System.out.println("\t[debug]- Organizador:\t\t" + evento.getOrganizador().getUsername());
                        if ((evento.getCapacidad() - evento.getAsistentes().size()) > 0) {
                            System.out.println("\t[debug]- Plazas disponibles:\t" + (evento.getCapacidad() - evento.getAsistentes().size()));
                        } else {
                            System.out.println("\t[debug]- Plazas disponibles:\t" + 0);
                            System.out.println("\t[debug]- En lista de espera:\t" + (evento.getAsistentes().size() - evento.getCapacidad()));
                        }
                        System.out.println("\t[debug]- Usuarios inscritos:\t");
                        evento.getAsistentesLista().forEach((usuario) -> {
                            System.out.println("\t\t[debug]- Username:\t " + usuario.getUsername());
                        });
                    }
                }
            }
        }

        return ret;
    }

}
