/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.dae1819.service;

import com.dae.dae1819.clients.ClienteSistema;
import com.dae.dae1819.pojos.Sistema;
import javax.sql.DataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

/**
 *
 * @author dml y jfaf
 */
@SpringBootApplication
@EntityScan(basePackages="com.dae.dae1819.pojos")
@ComponentScan({"com.dae.dae1819.pojos"})
public class SistemaService {

    @Bean
    Sistema sistema() {
        Sistema sistema = new Sistema();
        sistema.setNombre("sys");
        return sistema;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication servidor = new SpringApplication(SistemaService.class);
        ApplicationContext context = servidor.run(args);

        ClienteSistema cliente = new ClienteSistema(context);

        cliente.run();
    }

}
