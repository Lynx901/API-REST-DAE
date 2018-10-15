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

    private String nombre;
    private Date fecha;

    private enum Tipo {
        CHARLA, CURSO, ACTIVIDAD_DEPORTIVA, VISITA_CULTURAL
    }
    private Tipo tipo;
    private String descripcion;
    private Integer capacidad;
    private String localizacion;
    private boolean cancelado;

    private final List<Usuario> asistentes;
    private Usuario organizador;

    public Evento() {
        this.cancelado = false;
        this.asistentes = new ArrayList();
        this.organizador = new Usuario();
    }

    public Evento(String nombre, Date fecha, String _tipo, String descripcion,
            Integer capacidad, String localizacion, List<Usuario> asistentes, Usuario organizador) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.tipo = Tipo.valueOf(_tipo);
        this.descripcion = descripcion;
        this.capacidad = capacidad;
        this.localizacion = localizacion;
        this.cancelado = false;

        this.asistentes = new ArrayList();
        asistentes.forEach((usuario) -> {
            this.asistentes.add(usuario);
        });

        this.organizador = organizador;
    }

    public Evento(String nombre, Date fecha, String _tipo, String descripcion,
            Integer capacidad, String localizacion, Usuario organizador) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.tipo = Tipo.valueOf(_tipo);
        this.descripcion = descripcion;
        this.capacidad = capacidad;
        this.localizacion = localizacion;
        this.cancelado = false;

        this.asistentes = new ArrayList();
        this.organizador = organizador;

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
        return tipo.name();
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = Tipo.valueOf(tipo);
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
        asistentes.forEach((usuario) -> {
            this.asistentes.add(usuario);
        });
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

    /**
     * Inscribe a un usuario en el evento
     *
     * @param u usuario a inscribir
     * @return true si se ha inscrito, false si entra en la lista de espera
     */
    public boolean inscribir(Usuario u) {
        boolean ret = false;

        if (this.asistentes.size() <= this.capacidad) {
            ret = true;
        }
        this.asistentes.add(u);

        return ret;
    }

    /**
     * Desinscribe a un usuario del evento
     *
     * @param u usuario a desinscribir
     * @return
     */
    public boolean desinscribir(Usuario u) {
        boolean ret = false;

        if (this.asistentes.contains(u)) {
            this.asistentes.remove(u);
            ret = true;
        }

        return ret;
    }

}
