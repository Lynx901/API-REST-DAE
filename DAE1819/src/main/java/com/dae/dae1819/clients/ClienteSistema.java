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
                System.out.println("|- No es una opción válida, por favor elija una opción del menú.     -|");
                sc.next();
                badOption = true;
            }
        } while (badOption);

        return opt;
    }

    private boolean menuListadoAsistentes(EventoDTO e, List<UsuarioDTO> listaUsuarios) {
        if (listaUsuarios.isEmpty()) {
            System.out.println("|- No existen usuarios inscritos actualmente.                        -|");
            return false;
        } else {
            System.out.println("|---------------------------------------------------------------------|");
            System.out.println("|- Lista de usuarios inscritos en " + e.getNombre());
            System.out.println("|- " + listaUsuarios.size() + " inscritos de " + e.getCapacidad() + " posibles. ");
            System.out.println("|- Elija uno para obtener sus datos:                                 -|");

            for (int i = 1; i <= e.getCapacidad(); i++) {
                System.out.println("|- [" + i + "]. " + listaUsuarios.get(i - 1).getUsername());
            }
            
            if(e.getAsistentes().size() > e.getCapacidad()) {
                System.out.println("|-                                                                   -|");
                System.out.println("|- Lista de usuarios inscritos en la lista de espera:                -|");
                System.out.println("|- " + (listaUsuarios.size() - e.getCapacidad()) + " usuarios en la lista de espera ");

                for (int i = e.getCapacidad(); i <= listaUsuarios.size(); i++) {
                    System.out.println("|- [" + i + "]. " + listaUsuarios.get(i - 1).getUsername());
                }
            }
            
            System.out.println("|---------------------------------------------------------------------|");
            System.out.println("|- [0]. Volver al menú                                               -|");
            System.out.println("|---------------------------------------------------------------------|");
        }
        return true;
    }

    private boolean menuListadoEvento(List<EventoDTO> listaEventos, String nombreListado) {
        if (listaEventos.isEmpty()) {
            System.out.println("|- No existen eventos disponibles actualmente.                       -|");
            return false;
        } else {
            System.out.println("|---------------------------------------------------------------------|");
            System.out.println("|- Lista de eventos " + nombreListado + ": ");

            for (int i = 1; i <= listaEventos.size(); i++) {
                System.out.println("|- [" + i + "]. " + listaEventos.get(i - 1).getNombre());
            }
            System.out.println("|---------------------------------------------------------------------|");
            System.out.println("|- [0]. Volver al menú                                               -|");
            System.out.println("|---------------------------------------------------------------------|");
        }
        return true;
    }

    private boolean menuEvento(SistemaInterface sistema, EventoDTO evento) {
        if (evento.getNombre().equals("")) {
            System.out.println("|- Este evento no es válido, revíselo.                               -|");
        } else {
            System.out.println("|---------------------------------------------------------------------|");
            System.out.println("|- Nombre: \t\t" + evento.getNombre());
            System.out.println("|- Descripción: \t" + evento.getDescripcion());
            System.out.println("|- Fecha: \t\t" + evento.getFecha().toString());
            System.out.println("|- Tipo: \t\t" + evento.getTipo());
            System.out.println("|- Lugar: \t\t" + evento.getLocalizacion());
            System.out.println("|- Organizador:\t\t" + evento.getOrganizador());
            if((evento.getCapacidad() - evento.getAsistentes().size()) > 0) {
                System.out.println("|- Plazas disponibles:\t" + (evento.getCapacidad() - evento.getAsistentes().size()) + "/" + evento.getCapacidad());
            } else {
                System.out.println("|- Plazas disponibles:\t" + 0 + "/" + evento.getCapacidad());
                System.out.println("|- En lista de espera:\t" + (evento.getAsistentes().size() - evento.getCapacidad()));
            }
            System.out.println("|---------------------------------------------------------------------|" + "\n");
            if (!sistema.isTokenValid(token)) {
                System.out.print("|- Debe iniciar sesión para realizar cualquier acción.               -|" + "\n");
            } else {
                if (evento.getAsistentes().contains(user.getUsername())) {
                    System.out.println("|- ¿Qué desea hacer?                                                 -|");
                    System.out.println("|- [1]. Desinscribirse                                               -|");
                } else {
                    if (evento.getAsistentes().size() < evento.getCapacidad()) {
                        System.out.println("|- ¿Qué desea hacer?                                                 -|");
                        System.out.println("|- [1]. Inscribirse                                                  -|");
                    } else {
                        System.out.println("|- ¿Qué desea hacer?                                                 -|");
                        System.out.println("|- [1]. Entrar en lista de espera                                    -|");
                    }
                }
                if (user.getUsername().equals(evento.getOrganizador())) {
                    System.out.println("|- [2]. Cancelar evento                                              -|");
                    System.out.println("|- [3]. Mostrar asistentes                                           -|");
                }

                System.out.println("|---------------------------------------------------------------------|");
                System.out.println("|- [0]. Volver al menú                                               -|");
                System.out.println("|---------------------------------------------------------------------|");
                int eleccion = seleccionarOpcion();

                switch (eleccion) {
                    case 1:
                        if (evento.getAsistentes().contains(user.getUsername())) {
                            Scanner capt = new Scanner(System.in);
                            String elec = "";
                            do {
                                System.out.print("|- ¿Está seguro que desea desinscribirse? [S/N]: ");
                                elec = capt.nextLine();
                            } while (!(elec.equalsIgnoreCase("S") || elec.equalsIgnoreCase("N")));

                            if (elec.equalsIgnoreCase("s")) {
                                if(sistema.desinscribirse(user, evento)) {
                                    System.out.println("|- Se ha desinscrito correctamente.                                  -|");
                                } else {
                                    System.out.println("|- Algo ha ido mal, inténtelo de nuevo.                              -|");
                                }
                            }
                        } else {
                            if (sistema.inscribirse(user, evento)) {
                                sistema.inscribirse(user, evento);
                                System.out.println("|- Se le ha inscrito en la posición: " + (evento.getAsistentes().size() + 1));
                            } else {
                                System.out.println("|- Se le ha añadido a la lista de espera en la posición: " + (evento.getAsistentes().size() - evento.getCapacidad() + 1));
                            }
                        }
                        break;
                    case 2:
                        if (!user.getUsername().equals(evento.getOrganizador())) {
                            System.out.println("|- Debe ser el organizador para acceder a esta sección.                  -|");
                        } else {
                            if (sistema.cancelarEvento(evento.getNombre())) {
                                System.out.println("|- Se ha cancelado el evento correctamente.                          -|");
                            } else {
                                System.out.println("|- Se ha producido un error al cancelar el evento.                   -|");
                            }
                        }
                        break;
                    case 3:
                        if (!user.getUsername().equals(evento.getOrganizador())) {
                            System.out.println("|- Debe ser el organizador para acceder a esta sección.                  -|");
                        } else {

                            UsuarioDTO usuario = null;
                            List<UsuarioDTO> listaUsuarios = new ArrayList();

                            evento.getAsistentes().forEach((u) -> {
                                listaUsuarios.add(sistema.buscarUsuario(u));
                            });
                            
                            do {
                                if (menuListadoAsistentes(evento, listaUsuarios)) {
                                    int elecUsuario = seleccionarOpcion();
                                    if (elecUsuario <= 0) {
                                        break;
                                    }
                                    usuario = listaUsuarios.get(elecUsuario - 1);
                                } else {
                                    break;
                                }
                            } while (usuario == null);

                            if (usuario != null) {
                                System.out.println("|---------------------------------------------------------------------|");
                                System.out.println("|- Username: \t" + usuario.getUsername());
                                System.out.println("|- E-mail: \t" + usuario.getEmail());
                                System.out.println("|---------------------------------------------------------------------|");
                            }
                        }
                        break;

                    case 0:
                        break;

                    default:
                        System.out.println("|- Esta acción no está disponible.                                   -|");
                        if (eleccion != 0) {
                            eleccion = -1;
                        }
                        break;
                }

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
        String testData = "";

        System.out.println("|---------------------------------------------------------------------|");
        System.out.println("|---------------------------------------------------------------------|");
        System.out.println("|-                                                                   -|");
        System.out.println("|-                            Práctica 1                             -|");
        System.out.println("|-                        Gestión de Eventos                         -|");
        System.out.println("|-                                                                   -|");
        System.out.println("|---------------------------------------------------------------------|");

        do {
            System.out.print("|- ¿Desea comenzar la ejecución con algunos datos de prueba? [S/N]: ");
            testData = capt.nextLine();
        } while (!(testData.equalsIgnoreCase("S") || testData.equalsIgnoreCase("N")));

        if (testData.equalsIgnoreCase("s")) {
            List<UsuarioDTO> uTest = new ArrayList();
            sistema.nuevoUsuario("admin", "admin", "admin@ujaen.es");
            uTest.add(sistema.buscarUsuario("admin"));
            sistema.nuevoUsuario("user1", "asdf", "user@uja.es");
            uTest.add(sistema.buscarUsuario("user1"));
            sistema.nuevoUsuario("user2", "1234", "usuario@gmail.com");
            uTest.add(sistema.buscarUsuario("user2"));
            sistema.nuevoUsuario("USER3", "1a2b", "el3@yo.com");
            uTest.add(sistema.buscarUsuario("USER3"));

            List<EventoDTO> eTest = new ArrayList();
            sistema.nuevoEvento("Clase1", new Date(), "CHARLA", "Clase de DAE", (Integer) 15, "Edificio A3", "admin");
            eTest.add(sistema.buscarEventoPorNombre("Clase1"));
            sistema.nuevoEvento("Partido1", new Date(), "ACTIVIDAD_DEPORTIVA", "Partido de 2ª división", (Integer) 2, "Pabellón", "user1");
            eTest.add(sistema.buscarEventoPorNombre("Partido1"));
            sistema.nuevoEvento("Partido2", new Date(), "ACTIVIDAD_DEPORTIVA", "Partido de 1ª división", (Integer) 5, "Campo de fútbol", "user1");
            eTest.add(sistema.buscarEventoPorNombre("Partido2"));
            sistema.nuevoEvento("Lista de Espera", new Date(), "CURSO", "Evento con lista de espera", (Integer) 2, "Ejemplo", "USER3");            
            eTest.add(sistema.buscarEventoPorNombre("Lista de Espera"));
            
            for(int i = 0; i < uTest.size(); i++) {
                sistema.inscribirse(uTest.get(i), sistema.buscarEventoPorNombre("Lista de Espera"));
                System.out.println("Se ha inscrito a " + uTest.get(i).getUsername() + " en Lista de Espera");
            }
            
            System.out.println("|- Creados 4 usuarios y " + sistema.buscarEventos().size() + " eventos                                    -|");
        }

        do {
            System.out.println("|---------------------------------------------------------------------|");
            System.out.println("|- Seleccione una opción:                                            -|");
            System.out.println("|-                                                                   -|");

            if (!sistema.isTokenValid(token)) {
                System.out.println("|- [1]. Registrarse                                                  -|");
                System.out.println("|- [2]. Iniciar sesión                                               -|");
                System.out.println("|- [3]. Buscar evento                                                -|");
                System.out.println("|- [4]. Mostrar todos los eventos                                    -|");
            } else {
                System.out.println("|- [1]. Cerrar sesión                                                -|");
                System.out.println("|- [2]. Mostrar perfil del usuario                                   -|");
                System.out.println("|- [3]. Buscar evento                                                -|");
                System.out.println("|- [4]. Mostrar todos los eventos                                    -|");
                System.out.println("|- [5]. Crear evento                                                 -|");
                System.out.println("|- [6]. Mostrar eventos en los que se ha inscrito el usuario         -|");
                System.out.println("|- [7]. Mostrar eventos que ha organizado el usuario                 -|");
            }
            System.out.println("|- [8]. God mode                                                     -|");

            System.out.println("|---------------------------------------------------------------------|");
            System.out.println("|- [0]. Finalizar programa                                           -|");
            System.out.println("|---------------------------------------------------------------------|");
            System.out.println("|-                       (c) 2018 dml y jfaf                         -|");
            System.out.println("|---------------------------------------------------------------------|");

            eleccion = seleccionarOpcion();

            switch (eleccion) {
                case 1:
                    if (!sistema.isTokenValid(token)) {
                        String nombreUsuario, contrasena, email;
                        System.out.print("|- Escriba su correo electrónico: ");
                        email = capt.nextLine();

                        System.out.print("|- Introduzca un nombre de usuario: ");
                        nombreUsuario = capt.nextLine();

                        System.out.print("|- Introduzca una contraseña: ");
                        contrasena = capt.nextLine();

                        System.out.print("|- Repita la contraseña: ");
                        if (!contrasena.equals(capt.nextLine())) {
                            System.out.println("|- Las contraseñas no coinciden, vuelva a intentarlo.                -|");
                        } else {
                            sistema.nuevoUsuario(nombreUsuario, contrasena, email);
                            System.out.println("|- Usuario creado correctamente con username: " + nombreUsuario + " y contraseña: " + contrasena);
                            System.out.println("|- ¡Ya puede iniciar sesión!                                         -|");
                        }
                    } else {
                        String logoff;
                        do {
                            System.out.print("|- ¿Desea cerrar la sesión? [S/N]: ");
                            logoff = capt.nextLine();
                        } while (!(testData.equalsIgnoreCase("S") || testData.equalsIgnoreCase("N")));

                        if (logoff.equalsIgnoreCase("s")) {
                            user = null;
                            token = -1;
                            System.out.println("|- ¡Vuelva pronto!                                                   -|");
                        }
                    }
                    break;

                case 2:
                    if (!sistema.isTokenValid(token)) {
                        String nombreusuario, contrasena;

                        System.out.print("|- Nombre de usuario: ");
                        nombreusuario = capt.nextLine();

                        System.out.print("|- Contraseña: ");
                        contrasena = capt.nextLine();
                        token = sistema.login(nombreusuario, contrasena);

                        if (sistema.isTokenValid(token)) {
                            System.out.println("|- Ha iniciado sesión correctamente.                                 -|");
                            user = sistema.buscarUsuario(nombreusuario);
                        } else {
                            System.out.println("|- Algo ha fallado. Compruebe los datos de inicio de sesión.         -|");
                        }

                    } else {
                        System.out.println("|---------------------------------------------------------------------|");
                        System.out.println("|- Username: \t" + user.getUsername());
                        System.out.println("|- E-mail: \t" + user.getEmail());
                        System.out.println("|---------------------------------------------------------------------|");
                    }
                    break;

                case 3:
                    System.out.println("|---------------------------------------------------------------------|");
                    System.out.println("|- ¿Cómo desea buscar el evento?                                     -|");
                    System.out.println("|- [1]. Por tipo                                                     -|");
                    System.out.println("|- [2]. Por su descripción                                           -|");
                    System.out.println("|---------------------------------------------------------------------|");
                    System.out.println("|- [0]. Volver al menú                                               -|");
                    System.out.println("|---------------------------------------------------------------------|");

                    int tipoBusq = seleccionarOpcion();

                    switch (tipoBusq) {
                        case 1:
                            System.out.println("|---------------------------------------------------------------------|");
                            System.out.println("|- Tipos posibles de eventos:                                        -|");
                            System.out.println("|- [1]. Charla                                                       -|");
                            System.out.println("|- [2]. Curso                                                        -|");
                            System.out.println("|- [3]. Actividad Deportiva                                          -|");
                            System.out.println("|- [4]. Visita Cultural                                              -|");
                            System.out.println("|---------------------------------------------------------------------|");
                            System.out.println("|- [0]. Volver al menú                                               -|");
                            System.out.println("|---------------------------------------------------------------------|");

                            int elecTipo = seleccionarOpcion();
                            List<EventoDTO> listaEventosTipo = new ArrayList();
                            String tipo = "";
                            switch (elecTipo) {
                                case 1:
                                    listaEventosTipo = sistema.buscarEventosPorTipo("CHARLA");
                                    tipo = "CHARLA";
                                    break;
                                case 2:
                                    listaEventosTipo = sistema.buscarEventosPorTipo("CURSO");
                                    tipo = "CURSO";
                                    break;
                                case 3:
                                    listaEventosTipo = sistema.buscarEventosPorTipo("ACTIVIDAD_DEPORTIVA");
                                    tipo = "ACTIVIDAD_DEPORTIVA";
                                    break;
                                case 4:
                                    listaEventosTipo = sistema.buscarEventosPorTipo("VISITA_CULTURAL");
                                    tipo = "VISITA_CULTURAL";
                                    break;
                                default:
                                    break;
                            }

                            tipo = "del tipo " + tipo;

                            EventoDTO eventoTipo = new EventoDTO();
                            do {
                                if (menuListadoEvento(listaEventosTipo, tipo)) {
                                    int elecEventoTipo = seleccionarOpcion();
                                    if (elecEventoTipo <= 0  || elecEventoTipo > listaEventosTipo.size()) {
                                        break;
                                    }
                                    eventoTipo = listaEventosTipo.get(elecEventoTipo - 1);
                                } else {
                                    break;
                                }
                            } while (!menuEvento(sistema, eventoTipo));

                            break;

                        case 2:

                            String descEvento;

                            System.out.println("|---------------------------------------------------------------------|");
                            System.out.print("|- Introduzca la descripción del evento que desea buscar:");
                            descEvento = capt.nextLine();

                            List<EventoDTO> listaEventosDesc = sistema.buscarEventosPorDescripcion(descEvento);

                            EventoDTO eventoDesc = new EventoDTO();
                            do {
                                if (menuListadoEvento(listaEventosDesc, "con '" + descEvento + "' en la descripción")) {
                                    int elecEventoDesc = seleccionarOpcion();
                                    if (elecEventoDesc <= 0 || elecEventoDesc > listaEventosDesc.size()) {
                                        break;
                                    }
                                    eventoDesc = listaEventosDesc.get(elecEventoDesc - 1);
                                } else {
                                    break;
                                }
                            } while (!menuEvento(sistema, eventoDesc));

                            break;
                    }

                    break;

                case 4:
                    List<EventoDTO> listaEventos = sistema.buscarEventos();

                    EventoDTO evento = new EventoDTO();
                    do {
                        if (menuListadoEvento(listaEventos, "disponibles actualmente")) {
                            int elecEvento = seleccionarOpcion();
                            if (elecEvento <= 0 || elecEvento > listaEventos.size()) {
                                break;
                            }
                            evento = listaEventos.get(elecEvento - 1);
                        } else {
                            break;
                        }
                    } while (!menuEvento(sistema, evento));

                    break;

                case 5:
                    if (!sistema.isTokenValid(token)) {
                        System.out.println("|- Esta acción no está disponible, seleccione una del menú.          -|");
                    } else {
                        String nombre, descripcion, localizacion;
                        Integer capacidad = 0;
                        Integer dia = 0;
                        Integer mes = 0;
                        Integer anio = 0;

                        System.out.print("|- Introduzca el nombre del evento: ");
                        nombre = capt.nextLine();

                        System.out.print("|- Introduzca una descripción: ");
                        descripcion = capt.nextLine();

                        System.out.print("|- Introduzca el lugar donde se realizará: ");
                        localizacion = capt.nextLine();

                        boolean badOption;
                        do {
                            System.out.print("|- Introduzca el día del evento: ");
                            try {
                                dia = capt.nextInt();
                                badOption = (dia < 0 || dia > 32);
                            } catch (Exception e) {
                                System.out.println("|- No es un día válido, elija un día entre 1 y 31.                   -|");
                                capt.nextInt();
                                badOption = false;
                            }
                        } while (badOption);

                        do {
                            System.out.print("|- Introduzca el mes del evento (en número): ");
                            try {
                                mes = capt.nextInt();
                                badOption = (mes < 0 || mes > 13);
                            } catch (Exception e) {
                                System.out.println("|- No es un día válido, elija un mes entre 1 y 12.                   -|");
                                capt.nextInt();
                                badOption = false;
                            }
                        } while (badOption);

                        do {
                            System.out.print("|- Introduzca el año del evento: ");
                            try {
                                anio = capt.nextInt();
                                badOption = (anio < 2018);
                            } catch (Exception e) {
                                System.out.println("|- No es un año válido, elija un año después de 2017                 -|");
                                capt.nextInt();
                                badOption = false;
                            }
                        } while (badOption);

                        do {
                            System.out.print("|- ¿Qué capacidad máxima tendrá?: ");
                            try {
                                capacidad = capt.nextInt();
                                badOption = false;
                            } catch (Exception e) {
                                System.out.println("|- No es un número válido, elija un número entero.                   -|");
                                capt.nextInt();
                                badOption = true;
                            }
                        } while (badOption);

                        System.out.println("|- Seleccione uno de los siguiente tipos de evento                   -|");
                        System.out.println("|- [1]. Charla                                                       -|");
                        System.out.println("|- [2]. Curso                                                        -|");
                        System.out.println("|- [3]. Actividad Deportiva                                          -|");
                        System.out.println("|- [4]. Visita Cultural                                              -|");
                        System.out.println("|---------------------------------------------------------------------|");
                        System.out.println("|- [0]. Volver al menú                                               -|");
                        System.out.println("|---------------------------------------------------------------------|");

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
                        sistema.nuevoEvento(nombre, fecha, tipo, descripcion, capacidad, localizacion, user.getUsername());

                        EventoDTO newEvento = sistema.buscarEventoPorNombre(nombre);
                        System.out.println("|- Evento creado correctamente.                                      -|");
                        menuEvento(sistema, newEvento);

                    }
                    break;

                case 6:
                    if (!sistema.isTokenValid(token)) {
                        System.out.println("|- Esta acción no está disponible, seleccione una del menú.          -|");
                    } else {
                        List<EventoDTO> listaEventosInscritos = sistema.buscarEventosInscritos(user);

                        EventoDTO eventoUsuario = new EventoDTO();
                        do {
                            if (menuListadoEvento(listaEventosInscritos, "en los que se ha inscrito")) {
                                int elecEvento = seleccionarOpcion();
                                if (elecEvento <= 0  || elecEvento > listaEventosInscritos.size()) {
                                    break;
                                }
                                eventoUsuario = listaEventosInscritos.get(elecEvento - 1);
                            } else {
                                break;
                            }
                        } while (!menuEvento(sistema, eventoUsuario));
                    }

                    break;

                case 7:
                    if (!sistema.isTokenValid(token)) {
                        System.out.println("|- Esta acción no está disponible, seleccione una del menú.          -|");

                    } else {
                        List<EventoDTO> listaEventosOrganizados = sistema.buscarEventosOrganizados(user);

                        EventoDTO eventoUsuario = new EventoDTO();
                        do {
                            if (menuListadoEvento(listaEventosOrganizados, "organizados")) {
                                int elecEvento = seleccionarOpcion();
                                if (elecEvento <= 0  || elecEvento > listaEventosOrganizados.size()) {
                                    break;
                                }
                                eventoUsuario = listaEventosOrganizados.get(elecEvento - 1);
                            } else {
                                break;
                            }
                        } while (!menuEvento(sistema, eventoUsuario));
                    }
                    break;
                    
                // BORRAR ANTES DE ENVIAR LA PRÁCTICA
                case 8:
                        sistema.godMode();
                    break;

                case 0:
                    break;

                default:
                    System.out.println("|- Esta acción no está disponible.                                   -|");
                    if (eleccion != 0) {
                        eleccion = -1;
                    }
                    break;
            }

            if (eleccion != 0) {

                String end;
                do {
                    System.out.print("|- ¿Ir de nuevo al menú? [S/N]: ");
                    end = capt.nextLine();
                } while (!(testData.equalsIgnoreCase("S") || testData.equalsIgnoreCase("N")));

                if (end.equalsIgnoreCase("n")) {
                    eleccion = 0;
                }

            }

        } while (eleccion != 0);

    }
}
