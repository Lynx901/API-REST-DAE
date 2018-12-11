/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *
 *
 * email: desarrolloaplicacionesweb1819@gmail.com
 * pass: daedaedae
 *
 */
package com.dae.dae1819.service;

import com.dae.dae1819.pojos.Sistema;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author dml y jfaf
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
@EnableAutoConfiguration
@EnableConfigurationProperties
@EntityScan(basePackages = "com.dae.dae1819.pojos")
@ComponentScan({"com.dae.dae1819.DAOs", "org.springframework.mail.javamail.JavaMailSenderImpl"})
public class SistemaService {

    @Bean
    Sistema sistema() {
        Sistema sistema = new Sistema();
        sistema.setNombre("sys");
        return sistema;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication servidor = new SpringApplication(SistemaService.class);
        servidor.run(args);

    }

}
