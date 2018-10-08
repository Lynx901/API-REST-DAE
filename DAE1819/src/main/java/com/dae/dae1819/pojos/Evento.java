/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dae.dae1819.pojos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 *
 * @author dml y jfaf
 */
public class Evento {
    private Integer id = 0;
    private String nombre;
    private Date fecha;
    private enum Tipo {
        CHARLA, CURSO, ACTIVIDAD_DEPORTIVA, VISITA_CULTURAL
    }
    private Tipo tipoevento;
    private String descripcion;
    private Integer capacidad;
    private String localizacion;
    
    private List<Usuario> asistentes;
    private Usuario organizador;
    
    public Evento() {       
        this.asistentes = new ArrayList();
        this.organizador = new Usuario();   
    }
    
    public Evento (String nombre, Date fecha, Tipo _tipo, String descripcion, 
                   Integer capacidad, String localizacion, List<Usuario> asistentes, Usuario organizador) {
        this.id++;
        this.nombre = nombre;
        this.fecha = fecha;
        Tipo tipo = _tipo;
        this.descripcion = descripcion;
        this.capacidad = capacidad;
        this.localizacion = localizacion;
        
        this.asistentes.clear();
        for (Usuario usuario : asistentes) {
            this.asistentes.add(usuario);
        }
        this.organizador = organizador;
    }
    
     public Evento (String nombre, Date fecha, String _tipo, String descripcion, 
                   Integer capacidad, String localizacion, Usuario organizador) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.tipoevento = Tipo.valueOf(_tipo);
        this.descripcion = descripcion;
        this.capacidad = capacidad;
        this.localizacion = localizacion;
        this.organizador = organizador;
        
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     * @description Por mi parte creo que esta funcion no deberia existir ya que es un ID automatico dado por el sistema y 
     * es su clave ************************LEEEEEEEEEEEEMEEEEEEEEEEEEEEEEEEEEEEE********************************
     */
    public void setId(Integer id) {
        this.id = id;
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
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

     /**
     * @return the type
     */
    public String getTipoEvento() {
        return tipoevento.name();
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the capacidad
     */
    public Integer getCapacidad() {
        return capacidad;
    }

    /**
     * @param capacidad the capacidad to set
     */
    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    /**
     * @return the localizacion
     */
    public String getLocalizacion() {
        return localizacion;
    }

    /**
     * @param localizacion the localizacion to set
     */
    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    /**
     * @return the asistentes
     */
    public List<Usuario> getAsistentes() {
        return asistentes;
    }

    /**
     * @param asistentes the asistentes to set
     */
    public void setAsistentes(List<Usuario> asistentes) {
        this.asistentes.clear();
        for (Usuario usuario : asistentes) {
            this.asistentes.add(usuario);
        }
    }

    /**
     * @return the organizador
     */
    public Usuario getOrganizador() {
        return organizador;
    }

    /**
     * @param organizador the organizador to set
     */
    public void setOrganizador(Usuario organizador) {
        this.organizador = organizador;
    }
    
    
}
