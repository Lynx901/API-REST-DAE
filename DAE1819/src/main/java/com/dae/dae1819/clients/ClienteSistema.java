/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.dae1819.clients;

import com.dae.dae1819.interfaces.SistemaInterface;
import java.util.List;
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
        SistemaInterface sistema = (SistemaInterface) context.getBean("sistema");

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
                    + "|- [1]. Crear usuario                                                -|" + "\n"
                    + "|- [2]. Iniciar sesión                                               -|" + "\n"
                    + "|- [3]. Buscar evento por tipo                                       -|" + "\n"
                    + "|- [4]. Buscar evento por palabras                                   -|" + "\n"
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
                    String nombreusuario, contrasena, email;
                    System.out.print("|- Introduzca el correo electronico:");
                    email = capt.next();
                    System.out.print("\n|- Introduzca el nombre de usuario:");
                    nombreusuario = capt.next();
                    System.out.print("\n|- Introduzca la contraseña:");
                    contrasena = capt.next();
                    System.out.print("\n|- Repita la contraseña:");
                    if (!contrasena.equals(capt.next())){
                        System.out.println("\n|- La contraseña introducida no es la misma");
                    } else {
                        System.out.println("\n|- La contraseña introducida es correcta");
                        sistema.nuevoUsuario(nombreusuario, contrasena, email);
                    }
                    break;
                    
                case 2:
                    System.out.print("\n|- Introduzca el nombre de usuario:");
                    nombreusuario = capt.next();
                    System.out.print("\n|- Introduzca la contraseña:");
                    contrasena = capt.next();
                    if (sistema.login(nombreusuario, contrasena)){
                        System.out.print("\n|- El inicio de sesión se creo correctamente");
                    } else {
                        System.out.print("\n|- Compruebe sus credenciales de sesión");
                    }
                    break;
                case 3:
                    String tipoevento;
                    System.out.print("|- Introduzca el tipo de evento que desea buscar (CHARLA, CURSO, ACTIVIDAD_DEPORTIVA, VISITA_CULTURAL):");
                    tipoevento = capt.next();
                    System.out.print("|- Estamos buscando sus eventos... \n");
                    List<String> listaeventos = sistema.buscarEventoPorTipo(tipoevento);
                    if (listaeventos.isEmpty()){
                        System.out.print("|- No existen eventos disponibles de ese tipo\n");
                    } else {
                        System.out.print("|- Lista de eventos disponibles: ");
                        Integer count=0;
                       for (String event : listaeventos) 
                        { 
                            count++;
                            System.out.println("|- " + count + "-" + event );
                        }
                    }
                    break;
                 case 4:
                    String descevento="";
                    System.out.print("|- Introduzca la descripción del evento que desea buscar:");
                    tipoevento = capt.next();
                    System.out.print("|- Estamos buscando sus eventos... \n");
                    List<String> listaeventosdesc = sistema.buscarEventoPorPalabras(descevento);
                    if (listaeventosdesc.isEmpty()){
                        System.out.print("|- No existen eventos disponibles de ese tipo\n");
                    } else {
                        System.out.print("|- Lista de eventos disponibles: ");
                        Integer count=0;
                       for (String event : listaeventosdesc) 
                        { 
                            count++;
                            System.out.println("|- " + count + "-" + event );
                        }
                    }
                    break;
                default:
                break;
            }
            
            if (eleccion != 0) {
                capt = new Scanner(System.in);
                System.out.print("|- ¿Continuar? [Y/N]: ");
                String end = capt.next();

                if (end.equalsIgnoreCase("n") || end.equals("0")) {
                    eleccion = 0;
                } 
            }
            
      
        }while (eleccion != 0);

    }
 }
