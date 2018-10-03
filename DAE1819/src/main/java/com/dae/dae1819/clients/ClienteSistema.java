/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.dae1819.clients;

import com.dae.dae1819.interfaces.SistemaInterface;
import com.dae.dae1819.interfaces.UsuarioInterface;
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
        UsuarioInterface user = null;

        int eleccion = 0;
        do {
            System.out.print("\n|---------------------------------------------------------------------|" + "\n"
                    + "|-                                                                   -|" + "\n"
                    + "|-                            Práctica 1                             -|" + "\n"
                    + "|-                        Gestión de Eventos                         -|" + "\n"
                    + "|-                                                                   -|" + "\n"
                    + "|---------------------------------------------------------------------|" + "\n"
                    + "|- Seleccione una opción:                                            -|" + "\n"
                    + "|-                                                                   -|" + "\n");
            if (user == null) {
                System.out.print("|- [1]. Registrarse                                                  -|" + "\n"
                        + "|- [2]. Iniciar sesión                                               -|" + "\n"
                        + "|- [3]. Buscar evento                                                -|" + "\n"
                        + "|- [4]. Mostrar todos los eventos                                    -|" + "\n");
            } else {
                System.out.print("|- [1]. Cerrar sesión                                                -|" + "\n"
                        + "|- [2]. Mostrar perfil del usuario                                   -|" + "\n"
                        + "|- [3]. Buscar evento                                                -|" + "\n"
                        + "|- [4]. Mostrar todos los eventos                                    -|" + "\n"
                        + "|- [5]. Crear evento                                                 -|" + "\n"
                        + "|- [6]. Mostrar eventos en los que se ha inscrito el usuario         -|" + "\n"
                        + "|- [7]. Mostrar eventos que ha organizado el usuario                 -|" + "\n");
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
                    if (user == null) {
                        String nombreusuario, contrasena, email;
                        System.out.print("|- Escriba su correo electrónico:");
                        email = capt.next();
                        System.out.print("|- Introduzca un nombre de usuario:");
                        nombreusuario = capt.next();
                        System.out.print("|- Introduzca una contraseña:");
                        contrasena = capt.next();
                        System.out.print("|- Repita la contraseña:");
                        if (!contrasena.equals(capt.next())) {
                            System.out.println("\n|- Las contraseñas no coinciden, vuelva a intentarlo.");
                        } else {
                            System.out.println("\n|- Los datos son correctos. Creando usuario...");
                            sistema.nuevoUsuario(nombreusuario, contrasena, email);
                            System.out.print("\n|- Usuario creado correctamente con username: " + nombreusuario + " y contraseña: " + contrasena);
                            System.out.println("\n|- ¡Ya puede iniciar sesión!");
                        }
                    } else {
                        capt = new Scanner(System.in);
                        System.out.print("|- ¿Desea cerrar la sesión? [Y/N]: ");
                        String logoff = capt.next();

                        if (logoff.equalsIgnoreCase("y")) {
                            user = null;
                            System.out.print("|- ¡Vuelva pronto!");
                        }
                    }
                    break;

                case 2:
                    if (user == null) {
                        String nombreusuario, contrasena;
                        System.out.print("\n|- Nombre de usuario: ");
                        nombreusuario = capt.next();
                        System.out.print("|- Contraseña: ");
                        contrasena = capt.next();
                        if (sistema.login(nombreusuario, contrasena)) {
                            System.out.print("\n|- Ha iniciado sesión correctamente.\n");
                            user = (UsuarioInterface) sistema.buscarUsuario(nombreusuario);
                        } else {
                            System.out.print("\n|- Algo ha fallado. Compruebe los datos de inicio de sesión.\n");
                        }
                    } else {
                        System.out.print("|---------------------------------------------------------------------|" + "\n"
                                + "|- " + user.username + "\n"
                                + "|---------------------------------------------------------------------|" + "\n"
                                + "|- E-mail: \t" + user.email + "\n"
                                + "|- Contraseña: \t" + user.password + "\n");
                    }
                    break;
                case 3:
                    String tipoevento;
                    System.out.print("|- Introduzca el tipo de evento que desea buscar (CHARLA, CURSO, ACTIVIDAD_DEPORTIVA, VISITA_CULTURAL): ");
                    tipoevento = capt.next();
                    System.out.print("|- Estamos buscando sus eventos... \n");
                    List<String> listaeventos = sistema.buscarEventoPorTipo(tipoevento);
                    if (listaeventos.isEmpty()) {
                        System.out.print("|- No existen eventos disponibles de ese tipo.\n");
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
