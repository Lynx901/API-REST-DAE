/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.dae1819.clients;

import com.dae.dae1819.pojos.Sistema;
import java.util.Scanner;
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

        int eleccion = 0;
        do {
            System.out.print("\n|---------------------------------------------------------------------|" + "\n"
                    + "|-                                                                   -|" + "\n"
                    + "|-                            Práctica 1                             -|" + "\n"
                    + "|-                        Gestión de Eventos                         -|" + "\n"
                    + "|-                                                                   -|" + "\n"
                    + "|---------------------------------------------------------------------|" + "\n"
                    + "|- Seleccione una opción:                                            -|" + "\n"
                    + "|-                                                                   -|" + "\n"
                    + "|- [1]. Decir nombre del servidor                sistema.getNombre() -|" + "\n"
                    + "|- [2]. Mostrar eventos disponibles                                  -|" + "\n"
                    + "|---------------------------------------------------------------------|" + "\n"
                    + "|- [0].Finalizar programa                                            -|" + "\n"
                    + "|---------------------------------------------------------------------|" + "\n"
                    + "|-                       (c) 2018 dml y jfaf                         -|" + "\n"
                    + "|---------------------------------------------------------------------|" + "\n"
                    + "|- ");
                      

            Scanner capt = new Scanner(System.in);
            System.out.print("Opción: ");
            eleccion = capt.nextInt();

            switch (eleccion) {
                case 1:
                    System.out.print("|- El servidor se llama " + sistema.getNombre() + ".\n");
                    break;
                    
                case 2:
                    System.out.print("|- Listado de eventos:                                                |\n");
                    break;
                    
                default:
                break;
            }
            
            if (eleccion != 0) {
                capt = new Scanner(System.in);
                System.out.print("|- ¿Continuar? [Y/N]: ");
                String end = capt.next();

                if (end.equalsIgnoreCase("n")) {
                    eleccion = 0;
                } 
            }
            
      
        }while (eleccion != 0);

    }
 }
