/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.dae1819.clients;

import com.dae.dae1819.interfaces.SistemaInterface;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
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
        Integer token = null;

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
                    + "|- [1]. Registrarse                                                  -|" + "\n"
                    + "|- [2]. Iniciar sesión                                               -|" + "\n"
                    + "|- [3]. Buscar evento                                                -|" + "\n");
            if (token != null) {
                System.out.print("|- [4]. Mostrar detalles de usuario                              -|" + "\n"
                        + "|- [5]. Cerrar sesión                                                -|" + "\n");
            }
            System.out.print("|---------------------------------------------------------------------|" + "\n"
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
                    if (token == null) {
                        String nombreusuario, contrasena, email;
                        System.out.print("|- Introduzca el correo electronico:");
                        email = capt.next();
                        System.out.print("|- Introduzca el nombre de usuario:");
                        nombreusuario = capt.next();
                        System.out.print("|- Introduzca la contraseña:");
                        contrasena = capt.next();
                        System.out.print("|- Repita la contraseña:");
                        if (!contrasena.equals(capt.next())) {
                            System.out.println("\n|- Las contraseñas no coinciden, vuelva a intentarlo.");
                        } else {
                            System.out.println("\n|- Los datos son correctos, creando usuario...");
                            sistema.nuevoUsuario(nombreusuario, contrasena, email);
                            System.out.println("\n|- Usuario creado con username: " + nombreusuario + " y contraseña: " + contrasena);
                        }
                    }
                    break;

                case 2:
                    String nombreusuario, contrasena, email;
                    System.out.print("\n|- Introduzca el nombre de usuario:");
                    nombreusuario = capt.next();
                    System.out.print("|- Introduzca la contraseña:");
                    contrasena = capt.next();
                    if (sistema.login(nombreusuario, contrasena)) {
                        System.out.print("\n|- Ha iniciado sesión correctamente.\n");
                        token = ThreadLocalRandom.current().nextInt(10000000, 100000000);
                    } else {
                        System.out.print("\n|- Algo ha fallado. Compruebe sus credenciales de sesión.\n");
                    }
                    break;
                case 3:
                    String tipoevento;
                    System.out.print("|- Introduzca el tipo de evento que desea buscar (CHARLA, CURSO, ACTIVIDAD_DEPORTIVA, VISITA_CULTURAL):");
                    tipoevento = capt.next();
                    System.out.print("|- Estamos buscando sus eventos... \n");
                    List<String> listaeventos = sistema.buscarEventoPorTipo(tipoevento);
                    if (listaeventos.isEmpty()) {
                        System.out.print("|- No existen eventos disponibles de ese tipo\n");
                    } else {
                        System.out.print("|- Lista de eventos disponibles: ");
                        Integer count = 0;
                        for (String event : listaeventos) {
                            count++;
                            System.out.println("|- " + count + "-" + event);
                        }
                    }
                    break;
                case 4:
                    String descevento = "";
                    System.out.print("|- Introduzca la descripción del evento que desea buscar:");
                    tipoevento = capt.next();
                    System.out.print("|- Estamos buscando sus eventos... \n");
                    List<String> listaeventosdesc = sistema.buscarEventoPorPalabras(descevento);
                    if (listaeventosdesc.isEmpty()) {
                        System.out.print("|- No existen eventos disponibles de ese tipo\n");
                    } else {
                        System.out.print("|- Lista de eventos disponibles: ");
                        Integer count = 0;
                        for (String event : listaeventosdesc) {
                            count++;
                            System.out.println("|- " + count + "-" + event);
                        }
                    }
                    break;
                default:
                    break;
            }
//            
//            if (eleccion != 0) {
//                capt = new Scanner(System.in);
//                System.out.print("|- ¿Continuar? [Y/N]: ");
//                String end = capt.next();
//
//                if (end.equalsIgnoreCase("n") || end.equals("0")) {
//                    eleccion = 0;
//                } 
//            }

        } while (eleccion != 0);

    }
}
