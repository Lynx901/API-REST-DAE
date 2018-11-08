/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.dae1819.pojos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.persistence.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author dml y jfaf
 */
@Entity
public class Evento {

    @Id
    private int id;
    private String nombre;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar fecha;

    private enum Tipo {
        CHARLA, CURSO, ACTIVIDAD_DEPORTIVA, VISITA_CULTURAL
    }
    private Tipo tipo;
    private String descripcion;
    private Integer capacidad;
    private String localizacion;
    private boolean cancelado;

    @ManyToMany(mappedBy="eventos", cascade = {CascadeType.ALL})
    @LazyCollection(LazyCollectionOption.FALSE)
    private final Map<Calendar, Usuario> asistentes;

    @ManyToMany(mappedBy="listaEspera", cascade = {CascadeType.ALL})
    @LazyCollection(LazyCollectionOption.FALSE)
    private Map<Calendar, Usuario> inscritos;

    @ManyToOne
    private Usuario organizador;

    public Evento() {
        this.cancelado = false;
        this.asistentes = new HashMap();
        this.inscritos = new HashMap();
        this.organizador = new Usuario();
    }

    public Evento(int id, String nombre, Calendar fecha, String _tipo, String descripcion,
            Integer capacidad, String localizacion, Map<Calendar, Usuario> asistentes, Map<Calendar, Usuario> inscritos, Usuario organizador) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.tipo = Tipo.valueOf(_tipo);
        this.descripcion = descripcion;
        this.capacidad = capacidad;
        this.localizacion = localizacion;
        this.cancelado = false;

        this.asistentes = new HashMap();
        asistentes.forEach((fechaIns, usuario) -> {
            this.asistentes.put(fechaIns, usuario);
        });

        this.inscritos = new HashMap();
        this.inscritos = inscritos;
        inscritos.forEach((fechaIns, usuario) -> {
            this.inscritos.put(fechaIns, usuario);
        });

        this.organizador = organizador;
    }

    public Evento(int id, String nombre, Calendar fecha, String _tipo, String descripcion,
            Integer capacidad, String localizacion, Usuario organizador) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.tipo = Tipo.valueOf(_tipo);
        this.descripcion = descripcion;
        this.capacidad = capacidad;
        this.localizacion = localizacion;
        this.cancelado = false;

        this.asistentes = new HashMap();
        this.inscritos = new HashMap();
        this.organizador = organizador;

    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
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
    public Calendar getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Calendar fecha) {
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
    public Map<Calendar, Usuario> getAsistentes() {
        return asistentes;
    }

    /**
     * @return the asistentes
     */
    public List<Usuario> getAsistentesLista() {
        List<Usuario> Lista = new ArrayList();
        this.asistentes.forEach((username, usuario) -> {
            Lista.add(usuario);
        });
        return Lista;
    }

    /**
     * @param asistentes the asistentes to set
     */
    public void setAsistentes(Map<Calendar, Usuario> asistentes) {
        this.asistentes.clear();
        asistentes.forEach((fechaIns, usuario) -> {
            this.asistentes.put(fechaIns, usuario);
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
     * @return the inscritos
     */
    public Map<Calendar, Usuario> getInscritos() {
        return inscritos;
    }

    /**
     * @param inscritos the inscritos to set
     */
    public void setInscritos(Map<Calendar, Usuario> inscritos) {
        this.inscritos = inscritos;
    }

    /**
     * Inscribe a un usuario en el evento
     *
     * @param u usuario a inscribir
     * @return true si se ha inscrito, false si entra en la lista de espera
     */
    public boolean inscribir(Usuario u) {
        boolean ret = false;
        
        Calendar fechaIns = Calendar.getInstance();
        // Si está lleno, añadimos el usuario a la lista de inscritos
        if (this.asistentes.size() >= this.capacidad) {
            System.out.println("[debug] Evento: El evento está lleno, añadiendo a la lista de espera");
            this.inscritos.put(fechaIns, u);
        } else {
            // Si no está lleno, añadimos el usuario a la lista de asistentes
            this.asistentes.put(fechaIns, u);
            System.out.println("[debug] Evento: Se ha añadido a la lista de asistentes");
            // Si además es el organizador, añadimos el usuario como organizador
            if (this.organizador.getUsername().equals(u.getUsername())) {
                this.setOrganizador(u);
                System.out.println("[debug] Evento: Se ha puesto a " + this.organizador.getUsername() + " como organizador");
            }
            ret = true;
        }

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

        if (this.asistentes.containsValue(u)) {
            this.asistentes.remove(u.getUsername());
            //TODO notificacion de desinscripcion
            if (!this.inscritos.isEmpty()) {
                Entry<Calendar, Usuario> entrada = this.inscritos.entrySet().stream().sorted(Map.Entry.<Calendar, Usuario>comparingByKey()).findFirst().get();
                u = this.inscritos.get(entrada.getKey());
                this.inscribir(u);
                //TODO notificacion de inscripcion
                this.inscritos.remove(entrada.getKey());
            }
            ret = true;
        } else if (this.inscritos.containsValue(u)) {
            List<Calendar> fechas = new ArrayList<>(this.inscritos.keySet());
            for (int i = 0; i < this.inscritos.size(); i++) {
                if (this.inscritos.get(fechas.get(i)).getUsername().equals(u.getUsername())) {
                    this.inscritos.remove(fechas.get(i));
                    return true;
                }
            }
        }
        return ret;
    }

}
