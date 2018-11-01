/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.dae1819.DTOs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author juanf
 */
public class EventoDTO {

    private int id;
    private String nombre;
    private Date fecha;
    private String tipo;
    private String descripcion;
    private Integer capacidad;
    private String localizacion;
    private boolean cancelado;

    private final List<String> asistentes;
    private final List<String> inscritos;
    private String organizador;

    public EventoDTO() {
        nombre = "";
        this.cancelado = false;
        asistentes = new ArrayList();
        inscritos = new ArrayList();
    }

    public EventoDTO(int id, String nombre, Date fecha, String _tipo, String descripcion,
            Integer capacidad, String localizacion, List<String> asistentes, List<String> inscritos, String organizador) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.tipo = _tipo;
        this.descripcion = descripcion;
        this.capacidad = capacidad;
        this.localizacion = localizacion;
        this.cancelado = false;

        this.asistentes = new ArrayList();
        asistentes.forEach((usuario) -> {
            this.asistentes.add(usuario);
        });
        
        this.inscritos = new ArrayList();
        inscritos.forEach((usuario) -> {
            this.inscritos.add(usuario);
        });

        this.organizador = organizador;
    }
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }
    
    /**
     * @param id the id
     */
    public void setId(int id) {
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
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
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
    public List<String> getAsistentes() {
        return asistentes;
    }

    /**
     * @param asistentes the asistentes to set
     */
    public void setAsistentes(List<String> asistentes) {
        this.asistentes.clear();
        asistentes.forEach((usuario) -> {
            this.asistentes.add(usuario);
        });
    }

    /**
     * @return the organizador
     */
    public String getOrganizador() {
        return organizador;
    }

    /**
     * @param organizador the organizador to set
     */
    public void setOrganizador(String organizador) {
        this.organizador = organizador;
    }

    /**
     * @return the cancelado
     */
    public boolean isCancelado() {
        return cancelado;
    }

    /**
     * @param cancelado the cancelado to set
     */
    public void setCancelado(boolean cancelado) {
        this.cancelado = cancelado;
    }

    /**
     * @return the inscritos
     */
    public List<String> getInscritos() {
        return inscritos;
    }

    /**
     * @param inscritos the inscritos to set
     */
    public void setInscritos(List<String> inscritos) {
        this.inscritos.clear();
        inscritos.forEach((usuario) -> {
            this.inscritos.add(usuario);
        });
    }
}
