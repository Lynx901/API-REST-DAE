/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.dae1819.interfaces;

/**
 *
 * @author dani
 */
public interface EmailService {

    /**
     * Envía un mensaje por correo electrónico
     * @param to dirección a la que se enviará el email
     * @param subject asunto del email que se enviará
     * @param text cuerpo del mensaje del email que se enviará
     */
    void sendSimpleMessage(String to, String subject, String text);

}
