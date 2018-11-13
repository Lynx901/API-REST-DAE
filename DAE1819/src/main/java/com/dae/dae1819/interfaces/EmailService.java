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

    void sendSimpleMessage(String to, String subject, String text);

}
