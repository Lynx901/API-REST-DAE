/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.dae1819.beans;

import com.dae.dae1819.clients.ClienteSistema;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author dml y jfaf
 */

@SpringBootApplication
public class SistemaService {

    public static void main(String[] args) throws Exception {
        SpringApplication servidor = new SpringApplication(SistemaService.class);
        ApplicationContext context = servidor.run(args);
        
        ClienteSistema cliente = new ClienteSistema(context);

        cliente.run();

    }
}
