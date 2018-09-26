/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.dae1819.clients;

import com.dae.dae1819.pojos.Sistema;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author dml y jfaf
 */
public class ClienteSistema {
    
    ApplicationContext context;
    public ClienteSistema(ApplicationContext context) {
        this.context = context;
    }
    
    public void run() {
        Sistema sistema = (Sistema) context.getBean("sistema");
        
        
        System.out.println("Hola, el cliente se ha iniciado con el servidor: " + sistema.getNombre());
    }   
}
