/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.dae1819.clients;

import com.dae.dae1819.DTOs.EventoDTO;
import com.dae.dae1819.DTOs.UsuarioDTO;
import com.dae.dae1819.interfaces.SistemaInterface;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author dml y jfaf
 */
public class ClienteSistema {

    ApplicationContext context;
    UsuarioDTO user;
    Integer token = -1;

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

    private boolean menuListadoEvento(List<EventoDTO> listaEventos) {
        if (listaEventos.isEmpty()) {
            System.out.println("|- No existen eventos disponibles actualmente.");
            return false;
        } else {
            System.out.println("|---------------------------------------------------------------------|" + "\n"
                    + "|- Lista de eventos disponibles: ");

            for (int i = 1; i <= listaEventos.size(); i++) {
                System.out.println("|- [" + i + "]. " + listaEventos.get(i-1).getNombre());
            }
            System.out.println("|---------------------------------------------------------------------|" + "\n"
                    + "|- [0]. Volver al menú                                               -|" + "\n"
                    + "|---------------------------------------------------------------------|");
        }
        return true;
    }

    private boolean menuEvento(SistemaInterface sistema, EventoDTO evento) {
        if(evento.getNombre().equals("")) {
            System.out.println("|- Este evento no es válido, revíselo.");
        } else {
            System.out.print("|---------------------------------------------------------------------|" + "\n");
            System.out.print("|- Nombre: \t\t" + evento.getNombre() + "\n");
            System.out.print("|- Descripción: \t" + evento.getDescripcion() + "\n");
            System.out.print("|- Fecha: \t\t" + evento.getFecha().toString() + "\n");
            System.out.print("|- Tipo: \t\t" + evento.getTipo() + "\n");
            System.out.print("|- Lugar: \t\t" + evento.getLocalizacion() + "\n");
            System.out.print("|- Plazas máximas: \t" + evento.getCapacidad() + "\n");
            System.out.print("|- Plazas disponibles: \t" + (evento.getCapacidad() - evento.getAsistentes().size()) + "\n"
                    + "|---------------------------------------------------------------------|" + "\n");
            if (!sistema.isTokenValid(token)) {
                System.out.print("|- Debe iniciar sesión para realizar cualquier acción.               -|" + "\n");
            } else {
                if (evento.getAsistentes().size() < evento.getCapacidad()) {
                    System.out.print("|- ¿Qué desea hacer?                                                 -|" + "\n"
                            + "|- [1]. Inscribirse                                                  -|" + "\n");
                } else {
                    System.out.print("|- ¿Qué desea hacer?                                                 -|" + "\n"
                            + "|- [1]. Entrar en lista de espera                                    -|" + "\n");
                }
                if (user.getUsername().equals(evento.getOrganizador())) {
                    System.out.print("|- [2]. Cancelar evento                                              -|" + "\n"
                        + "|- [3]. Mostrar asistentes                                           -|" + "\n");
                }
                System.out.println("|---------------------------------------------------------------------|" + "\n"
                    + "|- [0]. Volver al menú                                               -|" + "\n"
                    + "|---------------------------------------------------------------------|" + "\n");
                return true;
            }
        }
        return false;
    }

    public void run() {
        SistemaInterface sistema = (SistemaInterface) context.getBean("sistema");
        user = null;
        Scanner capt = new Scanner(System.in);
        int eleccion;
        
        System.out.print("\n|---------------------------------------------------------------------|" + "\n"
                        + "|-                                                                   -|" + "\n"
                        + "|-                            Práctica 1                             -|" + "\n"
                        + "|-                        Gestión de Eventos                         -|" + "\n"
                        + "|-                                                                   -|" + "\n"
                        + "|---------------------------------------------------------------------|" + "\n");

        String testData;
        do {
            System.out.print("|- ¿Desea comenzar la ejecución con algunos datos de prueba? [S/N]: ");
            testData = capt.next();
            boolean test = (testData.equalsIgnoreCase("S") || testData.equalsIgnoreCase("N"));
        } while(!(testData.equalsIgnoreCase("S") || testData.equalsIgnoreCase("N")));
            
        if (testData.equalsIgnoreCase("s")) {
            sistema.nuevoUsuario("admin", "admin", "admin@ujaen.es");
            sistema.nuevoUsuario("user1", "asdf", "user@uja.es");
            sistema.nuevoUsuario("user2", "1234", "usuario@gmail.com");
            sistema.nuevoUsuario("USER3", "1a2b", "el3@yo.com");

            sistema.nuevoEvento("Clase1", new Date(), "CHARLA", "Clase de DAE", (Integer) 15, "Edificio A3", "admin");
            sistema.nuevoEvento("Partido1", new Date(), "ACTIVIDAD_DEPORTIVA", "Partido de 2ª división", (Integer) 2, "Pabellón", "user1");
            sistema.nuevoEvento("Partido2", new Date(), "ACTIVIDAD_DEPORTIVA", "Partido de 1ª división", (Integer) 5, "Campo de fútbol", "user1");
            System.out.println("|- Creados 4 usuarios y 3 eventos                                    -|");
        }

        do {
            System.out.print("|---------------------------------------------------------------------|" + "\n"
                    + "|- Seleccione una opción:                                            -|" + "\n"
                    + "|-                                                                   -|" + "\n");
            if (!sistema.isTokenValid(token)) {
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


            eleccion = seleccionarOpcion();

            switch (eleccion) {
                case 1:
                    if (!sistema.isTokenValid(token)) {
                        String nombreUsuario, contrasena, email;
                        System.out.print("|- Escriba su correo electrónico: ");
                        email = capt.next();

                        System.out.print("|- Introduzca un nombre de usuario: ");
                        nombreUsuario = capt.next();

                        System.out.print("|- Introduzca una contraseña: ");
                        contrasena = capt.next();

                        System.out.print("|- Repita la contraseña: ");
                        if (!contrasena.equals(capt.next())) {
                            System.out.println("\n|- Las contraseñas no coinciden, vuelva a intentarlo.");
                        } else {
                            System.out.println("\n|- Los datos son correctos. Creando usuario...");
                            sistema.nuevoUsuario(nombreUsuario, contrasena, email);

                            System.out.print("\n|- Usuario creado correctamente con username: " + nombreUsuario + " y contraseña: " + contrasena);
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
                    if (!sistema.isTokenValid(token)) {
                        String nombreusuario, contrasena;

                        System.out.print("\n|- Nombre de usuario: ");
                        nombreusuario = capt.next();

                        System.out.print("|- Contraseña: ");
                        contrasena = capt.next();
                        token = sistema.login(nombreusuario, contrasena);

                        if (sistema.isTokenValid(token)) {
                            System.out.print("\n|- Ha iniciado sesión correctamente.\n");
                            user = sistema.buscarUsuario(nombreusuario);
                        } else {
                            System.out.print("\n|- Algo ha fallado. Compruebe los datos de inicio de sesión.\n");
                        }

                    } else {
                        System.out.print("|---------------------------------------------------------------------|" + "\n"
                                + "|- Username: \t" + user.getUsername() + "\n"
                                + "|- E-mail: \t" + user.getEmail() + "\n");
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
                                    + "|- [4]. Visita Cultural                                              -|" + "\n"
                                    + "|---------------------------------------------------------------------|" + "\n"
                                    + "|- [0]. Volver al menú                                               -|" + "\n"
                                    + "|---------------------------------------------------------------------|" + "\n");

                            int elecTipo = seleccionarOpcion();
                            List<EventoDTO> listaEventosTipo = new ArrayList();
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

                            EventoDTO eventoTipo = new EventoDTO();
                            do {
                                if(menuListadoEvento(listaEventosTipo)) {
                                    int elecEventoTipo = seleccionarOpcion();
                                    if(elecEventoTipo <= 0) {
                                        break;
                                    }
                                    eventoTipo = listaEventosTipo.get(elecEventoTipo-1);
                                } else {
                                    break;
                                }
                            } while(!menuEvento(sistema, eventoTipo));

                            break;

                        case 2:

                            String descEvento;

                            System.out.print("|---------------------------------------------------------------------|" + "\n"
                                    + "|- Introduzca la descripción del evento que desea buscar:");
                            descEvento = capt.next();

                            List<EventoDTO> listaEventosDesc = sistema.buscarEventosPorDescripcion(descEvento);

                            EventoDTO eventoDesc = new EventoDTO();
                            do {
                                if(menuListadoEvento(listaEventosDesc)) {
                                    int elecEventoDesc = seleccionarOpcion();
                                    if(elecEventoDesc <= 0) {
                                        break;
                                    }
                                    eventoDesc = listaEventosDesc.get(elecEventoDesc-1);
                                }
                            } while(!menuEvento(sistema, eventoDesc));

                            break;
                    }

                    break;

                case 4:
                    List<EventoDTO> listaEventos = sistema.listarEventos();

                    EventoDTO evento = new EventoDTO();
                            do {
                                if(menuListadoEvento(listaEventos)) {
                                    int elecEvento = seleccionarOpcion();
                                    if(elecEvento <= 0) {
                                        break;
                                    }
                                    evento = listaEventos.get(elecEvento-1);
                                }
                            } while(!menuEvento(sistema, evento));

                    break;

                case 5:
                    if (!sistema.isTokenValid(token)) {
                        System.out.print("|- Esta acción no está disponible, seleccione una del menú. ");
                    } else {
                        String nombre, descripcion, localizacion;
                        Integer capacidad = 0;
                        Integer dia = 0;
                        Integer mes = 0;
                        Integer anio = 0;

                        System.out.print("|- Introduzca el nombre del evento: ");
                        nombre = capt.next();

                        System.out.print("|- Introduzca una descripción: ");
                        descripcion = capt.next();

                        System.out.print("|- Introduzca el lugar donde se realizará: ");
                        localizacion = capt.next();

                        boolean badOption;
                        do {
                            System.out.print("|- Introduzca el día del evento: ");
                            try {
                                dia = capt.nextInt();
                                badOption = (dia > 0 && dia < 32);
                            } catch (Exception e) {
                                System.out.println("|- No es un día válido, elija un día entre 1 y 31.");
                                capt.next();
                                badOption = true;
                            }
                        } while (badOption);

                        do {
                            System.out.print("|- Introduzca el mes del evento (en número): ");
                            try {
                                mes = capt.nextInt();
                                badOption = (mes > 0 && mes < 13);
                            } catch (Exception e) {
                                System.out.println("|- No es un día válido, elija un mes entre 1 y 12.");
                                capt.next();
                                badOption = true;
                            }
                        } while (badOption);

                        do {
                            System.out.print("|- Introduzca el año del evento: ");
                            try {
                                anio = capt.nextInt();
                                badOption = (anio > 2017);
                            } catch (Exception e) {
                                System.out.println("|- No es un año válido, elija un año después de 2017");
                                capt.next();
                                badOption = true;
                            }
                        } while (badOption);

                        do {
                            System.out.print("|- ¿Qué capacidad máxima tendrá?: ");
                            try {
                                capacidad = capt.nextInt();
                                badOption = false;
                            } catch (Exception e) {
                                System.out.println("|- No es un número válido, elija un número entero.");
                                capt.next();
                                badOption = true;
                            }
                        } while (badOption);

                        System.out.print("|- Seleccione uno de los siguiente tipos de evento                   -|" + "\n"
                                + "|- [1]. Charla                                                       -|" + "\n"
                                + "|- [2]. Curso                                                        -|" + "\n"
                                + "|- [3]. Actividad Deportiva                                          -|" + "\n"
                                + "|- [4]. Visita Cultural                                              -|" + "\n"
                                + "|---------------------------------------------------------------------|" + "\n"
                                + "|- [0]. Volver al menú                                               -|" + "\n"
                                + "|---------------------------------------------------------------------|" + "\n");
                        int elecTipo = seleccionarOpcion();
                        String tipo;
                        switch (elecTipo) {
                            case 1:
                                tipo = "CHARLA";
                                break;
                            case 2:
                                tipo = "CURSO";
                                break;
                            case 3:
                                tipo = "ACTIVIDAD_DEPORTIVA";
                                break;
                            case 4:
                                tipo = "VISITA_CULTURAL";
                                break;
                            default:
                                tipo = "";
                                break;
                        }

                        Date fecha = new Date(anio, mes, dia);

                        System.out.println("\n|- Los datos son correctos. Creando evento...");
                        sistema.nuevoEvento(nombre, fecha, tipo, descripcion, capacidad, localizacion, user.getUsername());

                        EventoDTO newEvento = sistema.buscarEventoPorNombre(nombre);
                        System.out.print("\n|- Evento creado correctamente. ");
                        menuEvento(sistema, newEvento);

                    }
                    break;

                case 6:
                    if (!sistema.isTokenValid(token)) {
                        System.out.print("|- Esta acción no está disponible, seleccione una del menú. ");

                    } else {
                        System.out.print("|- Próximamente (Opción 6) ");
                    }
                    break;

                case 7:
                    if (!sistema.isTokenValid(token)) {
                        System.out.print("|- Esta acción no está disponible, seleccione una del menú. ");

                    } else {
                        System.out.print("|- Próximamente (Opción 7) ");
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
