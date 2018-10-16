/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.dae1819.interfaces;

import com.dae.dae1819.DTOs.EventoDTO;
import com.dae.dae1819.DTOs.UsuarioDTO;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dml y jfaf
 */
public abstract class SistemaInterface {

    private String nombre;
    private Map<String, UsuarioDTO> usuarios;
    private Map<Integer, EventoDTO> eventos;

    /**
     * Comprueba si el token de sesión es válido
     *
     * @param token el token a comprobar
     * @return true si el token es válido, false si no
     */
    public abstract boolean isTokenValid(Integer token);

    /*
     ***************************************************************************
     ***************************************************************************
     ************ ACCIONES USUARIOS QUE NO HAN INICIADO SESIÓN *****************
     ***************************************************************************
     ***************************************************************************
     */
    /**
     * Registra a un usuario en el sistema
     *
     * @param username el nombre de usuario
     * @param password la contraseña del usuario
     * @param password2 la contraseña para comprobar
     * @param email el email del usuario
     * @return true si los datos son correctos, false si no
     */
    public abstract boolean nuevoUsuario(String username, String password, String password2, String email);

    /**
     * Inicia la sesión de un usuario registrado en el sistema
     *
     * @param username el nombre de usuario
     * @param password la contraseña del usuario
     * @return un UsuarioDTO válido si se ha iniciado sesión correctamente
     */
    public abstract UsuarioDTO login(String username, String password);
    
    /**
     * Inicia la sesión de un usuario registrado en el sistema
     *
     * @param uDTO el usuario que saldrá del sistema
     * @return null si se ha salido de la sesión correctamente
     */
    public abstract UsuarioDTO logout(UsuarioDTO uDTO);

    /**
     * Busca un evento por el nombre del mismo
     *
     * @param nombre el nombre del evento a buscar
     * @return un EventoDTO del evento encontrado, o null si no lo encuentra
     */
    public abstract EventoDTO buscarEventoPorNombre(String nombre);

    /**
     * Busca un evento por el tipo del mismo
     *
     * @param tipo el tipo del evento a buscar
     * @return una lista de EventoDTO encontrados, o una lista vacía si no
     * encuentra ninguno
     */
    public abstract List<EventoDTO> buscarEventosPorTipo(String tipo);

    /**
     * Busca un evento por la descripción del mismo
     *
     * @param descripcion la descripción del evento a buscar
     * @return una lista de EventoDTO encontrados, o una lista vacía si no
     * encuentra ninguno
     */
    public abstract List<EventoDTO> buscarEventosPorDescripcion(String descripcion);

    /**
     * Lista todos los eventos del sistema
     *
     * @return una lista con todos los eventos creados en forma DTO (vacía si no
     * encuentra ninguno)
     */
    public abstract List<EventoDTO> buscarEventos();

    /*
     ***************************************************************************
     ***************************************************************************
     *************** ACCIONES USUARIOS QUE HAN INICIADO SESIÓN *****************
     ***************************************************************************
     ***************************************************************************
     */
    /**
     * Crea un nuevo evento
     *
     * @param nombre el nombre del evento
     * @param fecha la fecha en la que se realiza el evento
     * @param tipo el tipo del evento
     * @param descripcion la descripción del evento
     * @param capacidad la capacidad de asistentes al evento
     * @param localizacion el lugar donde se realiza el evento
     * @param organizador el usuario que ha creado el evento
     * @return true si se ha creado bien, false si no
     */
    public abstract boolean nuevoEvento(String nombre, Date fecha, String tipo,
            String descripcion, Integer capacidad, String localizacion,
            String organizador);

    /**
     * Cancela un evento, borrando en cascada
     *
     * @param eDTO el evento a cancelar
     * @param uDTO el usuario que cancela el evento
     * @return true si se cancela, false si no
     */
    public abstract boolean cancelarEvento(EventoDTO eDTO, UsuarioDTO uDTO);

    /**
     * Reactiva un evento
     *
     * @param eDTO el evento a reactivar
     * @param uDTO el usuario que reactiva el evento
     * @return true si se cancela, false si no
     */
    public abstract boolean reactivarEvento(EventoDTO eDTO, UsuarioDTO uDTO);

    /**
     * Busca un usuario por su nombre de usuario
     *
     * @param username el nombre de usuario a buscar
     * @return un UsuarioDTO del usuario encontrado, o null si no lo encuentra
     */
    public abstract UsuarioDTO buscarUsuario(String username);

    /**
     * Busca los eventos en los que se ha inscrito el usuario
     *
     * @param uDTO usuario del que se comprobará el listado de eventos
     * @return una lista de EventoDTO con los eventos inscritos, o una vacía si
     * no encuentra ninguno
     */
    public abstract List<EventoDTO> buscarEventosInscritos(UsuarioDTO uDTO);

    /**
     * Busca los eventos organizados por el usuario
     *
     * @param uDTO usuario del que se comprobará el listado de eventos
     * organizados
     * @return una lista de EventoDTO con los eventos organizados, o una vacía
     * si no encuentra ninguno
     */
    public abstract List<EventoDTO> buscarEventosOrganizados(UsuarioDTO uDTO);

    /**
     * Inscribe a un usuario en un evento
     *
     * @param uDTO el usuario al que se inscribirá en el evento
     * @param eDTO el evento en el que se inscribirá el usuario
     * @return true si se inscribe al usuario, false si entra en la lista de
     * espera
     */
    public abstract boolean inscribirse(UsuarioDTO uDTO, EventoDTO eDTO);

    /**
     * Desinscribe a un usuario de un evento
     *
     * @param uDTO el usuario al que se desinscribirá del evento
     * @param eDTO el evento del que se desinscribirá el usuario
     * @return true si se ha desinscrito correctamente, false si no
     */
    public abstract boolean desinscribirse(UsuarioDTO uDTO, EventoDTO eDTO);

    /*
     ***************************************************************************
     ***************************************************************************
     **************************** MODO DESARROLLADO ****************************
     ***************************************************************************
     ***************************************************************************
     */
    /**
     * Muestra los datos de todas las instancias en memoria con las que se
     * trabaja
     *
     * @param pass la contraseña necesaria para acceder al God Mode
     * @return true si el acceso es válido, false si no
     */
    public abstract boolean godMode(String pass);

}
