/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.dae1819.interfaces;

import com.dae.dae1819.pojos.Evento;
import com.dae.dae1819.pojos.Usuario;
import java.util.Date;
import java.util.List;

/**
 *
 * @author dml y jfaf
 */
public abstract class EventoInterface {
    private Integer id;
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
}
