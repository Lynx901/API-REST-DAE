/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.dae1819.clients;

import com.dae.dae1819.pojos.Evento;
import com.dae.dae1819.interfaces.SistemaInterface;
import com.dae.dae1819.pojos.Usuario;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author dml y jfaf
 */
public class ClienteSistema {

    ApplicationContext context;
    Usuario user;

    public ClienteSistema(ApplicationContext context) {
        this.context = context;
    }

    private int seleccionarOpcion() {
        int opt = 0;
        boolean badOption;
        Scanner sc = new Scanner(System.in);

        do {
            System.out.print("|- Opción: ");
            try {
                opt = sc.nextInt();
                badOption = false;
            } catch (Exception e) {
                System.out.println("|- No es una opción válida, por favor elija una opción del menú.");
                sc.next();
                badOption = true;
            }
        } while (badOption);

        return opt;
    }

    private void menuListadoEvento(List<String> listaEventos) {
        if (listaEventos.isEmpty()) {
            System.out.println("|- No existen eventos disponibles actualmente.");
        } else {
            System.out.println("|- Lista de eventos disponibles: ");

            for (int i = 0; i < listaEventos.size(); i++) {
                System.out.println("|- [" + i + "]. " + listaEventos.get(i));
            }
            System.out.println("|---------------------------------------------------------------------|" + "\n"
                    + "|- [0]. Volver al menú                                               -|" + "\n"
                    + "|---------------------------------------------------------------------|" + "\n");
        }
    }

    private void menuEvento(Evento evento) {
        System.out.print("|---------------------------------------------------------------------|" + "\n"
                + "|- Nombre: \t" + evento.getNombre() + "\n"
                + "|- Descripción: \t" + evento.getDescripcion() + "\n"
                + "|- Lugar: \t" + evento.getLocalizacion() + "\n"
                + "|- Plazas máximas: \t" + evento.getCapacidad() + "\n"
                + "|- Plazas disponibles: \t" + (evento.getCapacidad() - evento.getAsistentes().size()) + "\n"
                + "|---------------------------------------------------------------------|" + "\n");
        if (user != null) {
            if (evento.getAsistentes().size() < evento.getCapacidad()) {
                System.out.print("|- ¿Qué desea hacer?                                                 -|" + "\n"
                        + "|- [1]. Inscribirse                                                  -|" + "\n");
            } else {
                System.out.print("|- ¿Qué desea hacer?                                                 -|" + "\n"
                        + "|- [1]. Entrar en lista de espera                                    -|" + "\n");
            }
        } else {
            System.out.print("|- Debe iniciar sesión para realizar cualquier acción.               -|" + "\n");
        }

        if (user == evento.getOrganizador()) {
            System.out.print("|- [2]. Cancelar evento                                              -|" + "\n"
                    + "|- [3]. Mostrar asistentes                                           -|" + "\n");
        }
        System.out.println("|---------------------------------------------------------------------|" + "\n"
                + "|- [0]. Volver al menú                                               -|" + "\n"
                + "|---------------------------------------------------------------------|" + "\n");
    }

    public void run() {
        SistemaInterface sistema = (SistemaInterface) context.getBean("sistema");
        user = null;
        int eleccion = -1;

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
                    + "|---------------------------------------------------------------------|" + "\n");

            Scanner capt = new Scanner(System.in);

            eleccion = seleccionarOpcion();

            switch (eleccion) {
                case 1:
                    if (user == null) {
                        String nombreusuario, contrasena, email;
                        System.out.print("|- Escriba su correo electrónico: ");
                        email = capt.next();
                        System.out.print("|- Introduzca un nombre de usuario: ");
                        nombreusuario = capt.next();
                        System.out.print("|- Introduzca una contraseña: ");
                        contrasena = capt.next();
                        System.out.print("|- Repita la contraseña: ");
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
                            user = sistema.buscarUsuario(nombreusuario);
                        } else {
                            System.out.print("\n|- Algo ha fallado. Compruebe los datos de inicio de sesión.\n");
                        }
                    } else {
                        System.out.print("|---------------------------------------------------------------------|" + "\n"
                                + "|- Username: \t" + user.getUsername() + "\n"
                                + "|- E-mail: \t" + user.getEmail() + "\n"
                                + "|- Contraseña: \t" + user.getPassword() + "\n");
                    }
                    break;
                case 3:
                    System.out.print("|---------------------------------------------------------------------|" + "\n"
                            + "|- ¿Cómo desea buscar el evento?                                     -|" + "\n"
                            + "|- [1]. Por tipo                                                     -|" + "\n"
                            + "|- [2]. Por su descripción                                           -|" + "\n"
                            + "|---------------------------------------------------------------------|" + "\n"
                            + "|- [0]. Volver al menú                                               -|" + "\n"
                            + "|---------------------------------------------------------------------|" + "\n");

                    int tipoBusq = seleccionarOpcion();

                    switch (tipoBusq) {
                        case 1:
                            System.out.print("|---------------------------------------------------------------------|" + "\n"
                                    + "|- Tipos posibles de eventos:                                        -|" + "\n"
                                    + "|- [1]. Charla                                                       -|" + "\n"
                                    + "|- [2]. Curso                                                        -|" + "\n"
                                    + "|- [3]. Actividad Deportiva                                          -|" + "\n"
                                    + "|- [4]. Visita Cultural                                              -|" + "\n");

                            System.out.print("|---------------------------------------------------------------------|" + "\n"
                                    + "|- [0]. Volver al menú                                               -|" + "\n"
                                    + "|---------------------------------------------------------------------|" + "\n");

                            int elecTipo = seleccionarOpcion();
                            List<String> listaEventosTipo = null;
                            switch (elecTipo) {
                                case 1:
                                    listaEventosTipo = sistema.buscarEventosPorTipo("CHARLA");
                                    break;
                                case 2:
                                    listaEventosTipo = sistema.buscarEventosPorTipo("CURSO");
                                    break;
                                case 3:
                                    listaEventosTipo = sistema.buscarEventosPorTipo("ACTIVIDAD_DEPORTIVA");
                                    break;
                                case 4:
                                    listaEventosTipo = sistema.buscarEventosPorTipo("VISITA_CULTURAL");
                                    break;
                                default:
                                    break;
                            }

                            menuListadoEvento(listaEventosTipo);
                            int elecEventoTipo = seleccionarOpcion();

                            Evento eventoTipo = sistema.buscarEventoPorNombre(listaEventosTipo.get(elecEventoTipo));
                            menuEvento(eventoTipo);

                            break;

                        case 2:

                            String descEvento = "";

                            System.out.print("|---------------------------------------------------------------------|" + "\n"
                                    + "|- Introduzca la descripción del evento que desea buscar:");
                            descEvento = capt.next();

                            System.out.print("|- Estamos buscando sus eventos... \n");

                            List<String> listaEventosDesc = sistema.buscarEventosPorPalabras(descEvento);
                            menuListadoEvento(listaEventosDesc);
                            int elecEventoDesc = seleccionarOpcion();

                            Evento eventoDesc = sistema.buscarEventoPorNombre(listaEventosDesc.get(elecEventoDesc));
                            menuEvento(eventoDesc);
                            break;
                    }

                    break;

                case 4:
                    List<String> listaEventos = sistema.listarEventos();

                    menuListadoEvento(listaEventos);
                    int elecEvento = seleccionarOpcion();

                    Evento evento = sistema.buscarEventoPorNombre(listaEventos.get(elecEvento));
                    menuEvento(evento);

                    break;

                case 5:
                    if (user == null) {
                        System.out.print("|- Esta acción no está disponible, seleccione una del menú. ");

                    } else {

                    }
                    break;

                case 6:
                    if (user == null) {
                        System.out.print("|- Esta acción no está disponible, seleccione una del menú. ");

                    } else {

                    }
                    break;

                case 7:
                    if (user == null) {
                        System.out.print("|- Esta acción no está disponible, seleccione una del menú. ");

                    } else {

                    }
                    break;

                default:
                    System.out.print("|- Esta acción no está disponible. \n");
                    if (eleccion != 0) {
                        eleccion = -1;
                    }
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
