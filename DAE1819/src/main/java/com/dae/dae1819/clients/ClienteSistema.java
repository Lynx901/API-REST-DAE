/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.dae1819.clients;

import com.dae.dae1819.DTOs.EventoDTO;
import com.dae.dae1819.DTOs.UsuarioDTO;
import com.dae.dae1819.Excepciones.ListaEventosVacia;
import com.dae.dae1819.Excepciones.UsuarioExistente;
import com.dae.dae1819.interfaces.SistemaInterface;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author dml y jfaf
 */
public class ClienteSistema {

    ApplicationContext context;
    UsuarioDTO user = null;

    public ClienteSistema(ApplicationContext context) {
        this.context = context;
    }

    /**
     * Pide al usuario un número entero para seleccionar una opción
     *
     * @pre debe haberse mostrado al usuario un menú con opciones
     * @return la opción elegida
     */
    private int seleccionarOpcion() {
        int opt = 0;
        boolean badOption;
        Scanner sc = new Scanner(System.in);

        do {
            System.out.print("|- Opción (0 para salir): ");
            try {
                opt = sc.nextInt();
                badOption = false;
            } catch (InputMismatchException e) {
                System.out.println("|- No es una opción válida, por favor elija una opción del menú.     -|");
                sc.next();
                badOption = true;
            }
        } while (badOption);

        return opt;
    }

    /**
     * Pide al usuario confirmar una acción mediante Sí o No
     *
     * @param pregunta pregunta que se le hace al usuario (sin '¿' ni '?')
     * @return true si la respuesta es Sí, false si la respuesta es No
     */
    private boolean seleccionarSN(String pregunta) {
        boolean ret = false;

        Scanner capt = new Scanner(System.in);
        String elec = "";
        do {
            System.out.print("|- ¿" + pregunta + "? [S/N]: ");
            elec = capt.nextLine();
        } while (!(elec.equalsIgnoreCase("S") || elec.equalsIgnoreCase("N")));

        if (elec.equalsIgnoreCase("s")) {
            ret = true;
        } else if (elec.equalsIgnoreCase("n")) {
            ret = false;
        }

        return ret;
    }

    /**
     * Muestra un menú con un listado de asistentes a un evento
     *
     * [1] Quizá aquí no haría falta pasar la lista de usuarios, ya que solo se
     * usa el nombre y eso está en e.getAsistentes().get(i), pero por futura
     * escalabilidad se ha dejado, por si quisiéramos mostrar también otros
     * datos del usuario.
     *
     * @post debe pedirse al usuario elegir una opción si se desea interactuar
     * con el listado
     * @param e evento del que se muestran los asistentes
     * @param listaUsuarios listado de asistentes a mostrar
     * @return true si se ha mostrado algún asistente, false si la lista está
     * vacía
     */
    private boolean menuListadoAsistentes(EventoDTO e, List<UsuarioDTO> listaUsuarios) {
        if (listaUsuarios.isEmpty()) {
            System.out.println("|- No existen usuarios inscritos actualmente.                        -|");
            return false;
        } else {
            System.out.println("|---------------------------------------------------------------------|");
            System.out.println("|- Lista de usuarios inscritos en " + e.getNombre());
            System.out.println("|- " + listaUsuarios.size() + " inscritos de " + e.getCapacidad() + " posibles. ");
            System.out.println("|- Elija uno para obtener sus datos:                                 -|");
            System.out.println("|-                                                                   -|");
            System.out.println("|- Usuarios admitidos: " + listaUsuarios.size());

            boolean lleno = listaUsuarios.size() > e.getCapacidad();
            int limit = lleno ? e.getCapacidad() : listaUsuarios.size();

            for (int i = 1; i <= limit; i++) {
                // Leer anotación [1] en la documentación de esta función
                System.out.println("|- [" + i + "]. " + listaUsuarios.get(i - 1).getUsername());
            }

            if (lleno) {
                System.out.println("|-                                                                   -|");
                System.out.println("|- Usuarios en la lista de espera: " + (listaUsuarios.size() - e.getCapacidad()));

                for (int i = e.getCapacidad() + 1; i <= listaUsuarios.size(); i++) {
                    System.out.println("|- [" + i + "]. " + listaUsuarios.get(i - 1).getUsername());
                }
            }

            System.out.println("|---------------------------------------------------------------------|");
            System.out.println("|- [0]. Volver al menú                                               -|");
            System.out.println("|---------------------------------------------------------------------|");
        }
        return true;
    }

    /**
     * Muestra un menú con un listado de eventos
     *
     * @post debe pedirse al usuario elegir una opción si se desea interactuar
     * con el listado
     * @param listaEventos listado de eventos a mostrar
     * @param nombreListado nombre del listado de eventos
     * @return true si se ha mostrado algún evento, false si la lista está vacía
     */
    private boolean menuListadoEvento(List<EventoDTO> listaEventos, String nombreListado) {
        if (listaEventos.isEmpty()) {
            System.out.println("|- No existen eventos " + nombreListado + " disponibles actualmente.");
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

    /**
     * Muestra un menú con los tipos posibles de eventos para que se seleccione
     * uno
     *
     * @return tipo de evento seleccionado en formato String
     */
    private String menuTipoEvento() {
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

        return tipo;
    }

    /**
     * Muestra un menú con la información de un evento
     *
     * @param sistema el servidor con el que interactúa el cliente
     * @param evento el evento del que se muestra la información
     * @param procedencia para comprobar desde dónde se accede al menú
     * @return true si el evento es válido, false si no
     */
    private boolean menuEvento(SistemaInterface sistema, EventoDTO evento, String procedencia) {
        boolean ret = false;

        if (evento.getNombre().equals("")) {
            System.out.println("|- Este evento no es válido, revíselo.                               -|");
        } else {
            System.out.println("|---------------------------------------------------------------------|");

            if (evento.isCancelado()) {
                System.out.println("|- Nombre: \t\t" + evento.getNombre());
                System.out.println("|- Este evento ha sido cancelado.                                    -|");
                System.out.println("|- Habla con el organizador para más información:                    -|");
                System.out.println("|- Organizador:\t\t" + evento.getOrganizador());
                System.out.println("|---------------------------------------------------------------------|");

            } else {
                System.out.println("|- Nombre: \t\t" + evento.getNombre());
                System.out.println("|- Descripción: \t" + evento.getDescripcion());
                System.out.println("|- Fecha: \t\t" + evento.getFecha().getHours() + ":" + evento.getFecha().getMinutes()
                        + " del " + evento.getFecha().getDate() + "/" + evento.getFecha().getMonth() + "/" + evento.getFecha().getYear());
                System.out.println("|- Tipo: \t\t" + evento.getTipo());
                System.out.println("|- Lugar: \t\t" + evento.getLocalizacion());
                System.out.println("|- Organizador:\t\t" + evento.getOrganizador());
                if ((evento.getCapacidad() - evento.getAsistentes().size()) > 0) {
                    System.out.println("|- Plazas disponibles:\t" + (evento.getCapacidad() - evento.getAsistentes().size()) + "/" + evento.getCapacidad());
                } else {
                    System.out.println("|- Plazas disponibles:\t" + 0 + "/" + evento.getCapacidad());
                    System.out.println("|- En lista de espera:\t" + (evento.getAsistentes().size() - evento.getCapacidad()));
                }
                System.out.println("|---------------------------------------------------------------------|");
            }

            if (!sistema.isTokenValid(user.getToken())) {
                System.out.println("|- Debe iniciar sesión para realizar cualquier acción.               -|");
            } else if (!procedencia.equals("crear")) {

                System.out.println("|- ¿Qué desea hacer?                                                 -|");
                if (!evento.isCancelado()) {

                    if (evento.getAsistentes().contains(user.getUsername())) {
                        System.out.println("|- [1]. Desinscribirse                                               -|");
                    } else {

                        if (evento.getAsistentes().size() < evento.getCapacidad()) {
                            System.out.println("|- [1]. Inscribirse                                                  -|");
                        } else {
                            System.out.println("|- [1]. Entrar en lista de espera                                    -|");
                        }

                    }

                    if (user.getUsername().equals(evento.getOrganizador())) {
                        System.out.println("|- [2]. Mostrar asistentes                                           -|");
                        System.out.println("|- [3]. Cancelar el evento                                           -|");
                    }

                } else if (evento.getOrganizador().equals(user.getUsername())) {
                    System.out.println("|- [1]. Reactivar el evento                                          -|");
                }

                System.out.println("|---------------------------------------------------------------------|");
                System.out.println("|- [0]. Volver al listado de eventos                                 -|");
                System.out.println("|---------------------------------------------------------------------|");
                ret = true;
            }
        }

        return ret;
    }

    /**
     * Permite interactuar con un evento, se ejecutará hasta que el usuario
     * salga
     *
     * @pre debe haberse mostrado al usuario un listado de eventos
     * @post debe pedirse al usuario elegir una opción si se desea interactuar
     * con el listado
     * @param sistema el servidor con el que interactúa el cliente
     * @param evento el evento del que se muestra la información
     */
    private void gestionarEvento(SistemaInterface sistema, EventoDTO evento) {
        int eleccion;

        do {
            eleccion = this.seleccionarOpcion();

            switch (eleccion) {
                case 1: // Reactivar el evento, inscribirse o desinscribirse
                    // Si el evento esta cancelado y el usuario es organizador se puede reactivar
                    if (evento.isCancelado()) {

                        if (evento.getOrganizador().equals(user.getUsername())) {
                            sistema.reactivarEvento(evento, user);
                            System.out.println("|- Se ha reactivado correctamente.                                      -|");
                        } else {
                            System.out.println("|- Ha habido un error al reactivar el evento.                           -|");
                        }

                    } else {
                        //Inscribirse o desinscribirse en el evento
                        if (evento.getAsistentes().contains(user.getUsername())) {
                            if (this.seleccionarSN("Está seguro de que desea desinscribirse")) {
                                if (sistema.desinscribirse(user, evento)) {
                                    System.out.println("|- Se ha desinscrito correctamente.                                  -|");
                                } else {
                                    System.out.println("|- Ha habido un error al desinscribirse del evento.                  -|");
                                }
                            }
                        } else {
                            if (sistema.inscribirse(user, evento)) {
                                System.out.println("|- Se le ha inscrito en la posición: " + (evento.getAsistentes().size() + 1));
                            } else {
                                System.out.println("|- Se le ha añadido a la lista de espera en la posición: " + (evento.getAsistentes().size() - evento.getCapacidad() + 1));
                            }
                        }
                    }
                    break;

                case 2: // Mostrar los asistentes al evento, así como la lista de espera

                    if (!user.getUsername().equals(evento.getOrganizador())) {
                        System.out.println("|- Debe ser el organizador para acceder a esta sección.                  -|");
                    } else {

                        UsuarioDTO usuario;
                        List<UsuarioDTO> listaUsuarios = new ArrayList();

                        evento.getAsistentes().forEach((u) -> {
                            listaUsuarios.add(sistema.buscarUsuario(u));
                        });

                        int elecUsuario;
                        do {
                            if (this.menuListadoAsistentes(evento, listaUsuarios)) {
                                elecUsuario = this.seleccionarOpcion();
                                if (elecUsuario <= 0 || elecUsuario > listaUsuarios.size()) {
                                    break;
                                }
                                usuario = listaUsuarios.get(elecUsuario - 1);
                            } else {
                                break;
                            }
                        } while (!this.menuUsuario(sistema, usuario));

                        // Cuando sea necesario, se podrá gestionar el usuario desde aquí
//                        if (usuario != null) {
//                            if (elecUsuario != 0) {
//                                this.gestionarUsuario(sistema, usuario);
//                            }
//                        } else {
//                            System.out.println("|- El usuario no es válido.                                           -|");
//                        }
                        eleccion = 0;
                    }
                    break;

                case 3: // Cancelar el evento

                    if (!user.getUsername().equals(evento.getOrganizador())) {
                        System.out.println("|- Debe ser el organizador para acceder a esta sección.                  -|");
                    } else {
                        sistema.cancelarEvento(evento, user);

                        if (evento.isCancelado()) {
                            System.out.println("|- Se ha cancelado el evento correctamente.                          -|");
                        } else {
                            System.out.println("|- Se ha producido un error al cancelar el evento.                   -|");
                        }
                    }

                    eleccion = 0;
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
        } while (eleccion != 0);
    }

    /**
     * Muestra un menú con la información de un evento
     *
     * @post debe pedirse al usuario elegir una opción si se desea interactuar
     * con el listado
     * @param sistema el servidor con el que interactúa el cliente
     * @param usuario el evento del que se muestra la información
     * @return true si el usuario es válido, false si no
     */
    private boolean menuUsuario(SistemaInterface sistema, UsuarioDTO usuario) {
        boolean ret = false;

        System.out.println("|---------------------------------------------------------------------|");
        System.out.println("|- Nombre de usuario: \t\t" + usuario.getUsername());
        System.out.println("|- Correo electrónico: \t\t" + usuario.getEmail());

        System.out.println("|- Eventos en los que se ha inscrito:\t" + usuario.getEventos().size());
        List<Integer> eventos = usuario.getEventos();
        System.out.println("|-\t|-------------------------------------------------------------|");
        for (Integer eventoID : eventos) {
            try {
                EventoDTO e = sistema.buscarEventoPorId(eventoID);
            
                System.out.println("|-\t|- Nombre: \t\t" + e.getNombre());
                if (e.isCancelado()) {
                    System.out.println("|-\t|- Este evento está cancelado.                           -|");
                } else {
                    System.out.println("|-\t|- Fecha: \t\t" + e.getFecha().toString());
                    if (!usuario.getListaEspera().contains(e.getNombre())) {
                        System.out.println("|-\t|- ¡Estás inscrito!");
                    } else {
                        System.out.println("|-\t|- Estás en la lista de espera");
                    }
                }
            } catch (Exception e) {//TODO
                    System.err.print(e.getMessage());
            }
            System.out.println("|-\t|-------------------------------------------------------------|");
        }

        System.out.println("|- Eventos que ha organizado:\t" + usuario.getOrganizados().size());
        List<Integer> eventosOrganizados = usuario.getOrganizados();
        System.out.println("|-\t|-------------------------------------------------------------|");
        try {
            for (Integer eventoID : eventosOrganizados) {
                EventoDTO e = sistema.buscarEventoPorId(eventoID);
                System.out.println("|-\t|- Nombre: \t\t" + e.getNombre());
                System.out.println("|-\t|- Fecha: \t\t" + e.getFecha().toString());
                System.out.println("|-\t|- Asistentes: \t\t" + e.getAsistentes());
                System.out.println("|-\t|-------------------------------------------------------------|");
            }
        } catch (Exception e) {//TODO
            System.err.print(e.getMessage());
        }
        System.out.println("|---------------------------------------------------------------------|" + "\n");
        return ret;
    }

    public void run() {
        SistemaInterface sistema = (SistemaInterface) context.getBean("sistema");
        user = new UsuarioDTO();
        Scanner capt = new Scanner(System.in);
        int eleccion;

        System.out.println("|---------------------------------------------------------------------|");
        System.out.println("|---------------------------------------------------------------------|");
        System.out.println("|-                                                                   -|");
        System.out.println("|-                            Práctica 1                             -|");
        System.out.println("|-                        Gestión de Eventos                         -|");
        System.out.println("|-                                                                   -|");
        System.out.println("|---------------------------------------------------------------------|");

        if (this.seleccionarSN("Desea comenzar la ejecución con algunos datos de prueba")) {
            List<UsuarioDTO> uTest = new ArrayList();
            try {
                sistema.nuevoUsuario("admin", "admin", "admin", "admin@ujaen.es");
                uTest.add(sistema.buscarUsuario("admin"));
                sistema.nuevoUsuario("user1", "asdf", "asdf", "user@uja.es");
                uTest.add(sistema.buscarUsuario("user1"));
                sistema.nuevoUsuario("user2", "1234", "1234", "usuario@gmail.com");
                uTest.add(sistema.buscarUsuario("user2"));
                sistema.nuevoUsuario("USER3", "1a2b", "1a2b", "el3@yo.com");
                uTest.add(sistema.buscarUsuario("USER3"));
            } catch (UsuarioExistente e){
                System.err.print(e);
            }

//            List<EventoDTO> eTest = new ArrayList();
//            sistema.nuevoEvento("Clase1", new Date(), "CHARLA", "Clase de DAE", (Integer) 15, "Edificio A3", "admin");
//            eTest.add(sistema.buscarEventoPorNombre("Clase1"));
//            sistema.nuevoEvento("Partido1", new Date(), "ACTIVIDAD_DEPORTIVA", "Partido de 2ª división", (Integer) 2, "Pabellón", "user1");
//            eTest.add(sistema.buscarEventoPorNombre("Partido1"));
//            sistema.nuevoEvento("Partido2", new Date(), "ACTIVIDAD_DEPORTIVA", "Partido de 1ª división", (Integer) 5, "Campo de fútbol", "user1");
//            eTest.add(sistema.buscarEventoPorNombre("Partido2"));
//            sistema.nuevoEvento("Lista de Espera", new Date(), "CURSO", "Evento con lista de espera", (Integer) 2, "Ejemplo", "USER3");
//            eTest.add(sistema.buscarEventoPorNombre("Lista de Espera"));
//
//            for (int i = 0; i < uTest.size(); i++) {
//                sistema.inscribirse(uTest.get(i), sistema.buscarEventoPorNombre("Lista de Espera"));
//            }
//
//            System.out.println("|- Creados 4 usuarios y " + sistema.buscarEventos().size() + " eventos                                    -|");
        }

        do {
            System.out.println("|---------------------------------------------------------------------|");
            System.out.println("|- Seleccione una opción:                                            -|");
            System.out.println("|-                                                                   -|");

            if (!sistema.isTokenValid(user.getToken())) {
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
            //System.out.println("|- [8]. God mode                                                     -|");

            System.out.println("|---------------------------------------------------------------------|");
            System.out.println("|- [0]. Finalizar programa                                           -|");
            System.out.println("|---------------------------------------------------------------------|");
            System.out.println("|-                       Práctica 1: Backend                         -|");
            System.out.println("|-                       (c) 2018 dml y jfaf                         -|");
            System.out.println("|---------------------------------------------------------------------|");

            eleccion = this.seleccionarOpcion();

            switch (eleccion) {
                case 1: // Registrarse o cerrar sesión
                    if (!sistema.isTokenValid(user.getToken())) {

                        String email, nombreUsuario, pass, pass2;

                        System.out.print("|- Escriba su correo electrónico: ");
                        email = capt.nextLine();
                        while (!email.contains("@") && !email.contains(".") && !email.contains(" ")) {
                            System.out.println("|- El correo electronico debe tener el formato \"nombre@servidor.com\" -|");
                            System.out.print("|- Escriba su correo electrónico: ");
                            email = capt.nextLine();
                        }
                        System.out.print("|- Introduzca un nombre de usuario: ");
                        nombreUsuario = capt.nextLine();

                        System.out.print("|- Introduzca una contraseña: ");
                        pass = capt.nextLine();

                        System.out.print("|- Repita la contraseña: ");
                        pass2 = capt.nextLine();

                        // Comprobación de datos introducidos
                        try {
                            if (!sistema.nuevoUsuario(nombreUsuario, pass, pass2, email)) {
                                System.out.println("|- Las contraseñas no coinciden, vuelva a intentarlo.                -|");
                            } else {
                                System.out.println("|- Usuario creado correctamente con username: " + nombreUsuario + " y contraseña: " + pass);
                            }
                        } catch (UsuarioExistente e) {
                            System.err.print(e);
                        }
                            user = sistema.login(nombreUsuario, pass);

                        if (sistema.isTokenValid(user.getToken())) {
                            System.out.println("|- Ha iniciado sesión correctamente.                                 -|");
                            user = sistema.buscarUsuario(nombreUsuario);
                        } else {
                            System.out.println("|- Algo ha fallado. Compruebe los datos de inicio de sesión.         -|");
                        }

                    } else {

                        if (this.seleccionarSN("Desea cerrar la sesión")) {
                            user = sistema.logout(user);
                            System.out.println("|- ¡Vuelva pronto!                                                   -|");
                        }

                    }
                    break;

                case 2: // Iniciar sesión o mostrar el perfil
                    if (!sistema.isTokenValid(user.getToken())) {

                        String nombreUsuario, pass;

                        System.out.print("|- Nombre de usuario: ");
                        nombreUsuario = capt.nextLine();

                        System.out.print("|- Contraseña: ");
                        pass = capt.nextLine();
                        
                        user = sistema.login(nombreUsuario, pass);

                        if (sistema.isTokenValid(user.getToken())) {
                            System.out.println("|- Ha iniciado sesión correctamente.                                 -|");
                            user = sistema.buscarUsuario(nombreUsuario);
                        } else {
                            System.out.println("|- Algo ha fallado. Compruebe los datos de inicio de sesión.         -|");
                        }

                    } else {

                        this.menuUsuario(sistema, user);

                    }
                    break;

                case 3: // Buscar eventos por tipo o descripción
                    System.out.println("|---------------------------------------------------------------------|");
                    System.out.println("|- ¿Cómo desea buscar el evento?                                     -|");
                    System.out.println("|- [1]. Por tipo                                                     -|");
                    System.out.println("|- [2]. Por su descripción                                           -|");
                    System.out.println("|---------------------------------------------------------------------|");
                    System.out.println("|- [0]. Volver al menú                                               -|");
                    System.out.println("|---------------------------------------------------------------------|");

                    int tipoBusq = this.seleccionarOpcion();

                    switch (tipoBusq) {
                        case 1:
                            String tipo = this.menuTipoEvento();
                            try {
                                List<EventoDTO> listaEventosTipo = sistema.buscarEventosPorTipo(tipo);

                                EventoDTO eventoTipo = new EventoDTO();
                                int elecEventoTipo = 0;
                                do {
                                    if (this.menuListadoEvento(listaEventosTipo, ("del tipo " + tipo))) {
                                        elecEventoTipo = this.seleccionarOpcion();
                                        if (elecEventoTipo <= 0 || elecEventoTipo > listaEventosTipo.size()) {
                                            break;
                                        }
                                        eventoTipo = listaEventosTipo.get(elecEventoTipo - 1);
                                    } else {
                                        break;
                                    }
                                } while (!this.menuEvento(sistema, eventoTipo, "busqueda"));

                                if (eventoTipo != null) {
                                    if (elecEventoTipo != 0) {
                                        this.gestionarEvento(sistema, eventoTipo);
                                    }
                                } else {
                                    System.out.println("|- El evento no es válido.                                            -|");
                                }
                            } catch(ListaEventosVacia e) {
                                System.err.print(e.getMessage());
                            }
                            break;

                        case 2:

                            String descEvento;

                            System.out.println("|---------------------------------------------------------------------|");
                            System.out.print("|- Introduzca la descripción del evento que desea buscar:");
                            descEvento = capt.nextLine();

                            if (descEvento.replace(" ", "").equals("")) {
                                System.out.println("|---------------------------------------------------------------------|");
                                System.out.print("|- La descripcion introducida no es correcta.");
                                System.out.println();
                            } else {
                                try {
                                    List<EventoDTO> listaEventosDesc = sistema.buscarEventosPorDescripcion(descEvento);

                                    EventoDTO eventoDesc = new EventoDTO();
                                    int elecEventoDesc = 0;
                                    do {
                                        if (this.menuListadoEvento(listaEventosDesc, "con '" + descEvento + "' en la descripción")) {
                                            elecEventoDesc = this.seleccionarOpcion();
                                            if (elecEventoDesc <= 0 || elecEventoDesc > listaEventosDesc.size()) {
                                                break;
                                            }
                                            eventoDesc = listaEventosDesc.get(elecEventoDesc - 1);
                                        } else {
                                            break;
                                        }
                                    } while (!this.menuEvento(sistema, eventoDesc, "busqueda"));

                                    if (eventoDesc != null) {
                                        if (elecEventoDesc != 0) {
                                            this.gestionarEvento(sistema, eventoDesc);
                                        }
                                    } else {
                                        System.out.println("|- El evento no es válido.                                            -|");
                                    }
                                } catch(ListaEventosVacia e) {
                                    System.err.print(e.getMessage());
                                }
                            }
                            break;
                        default:
                            System.out.println("|----------------Esa opción no existe-----------------------------|");
                    }

                    break;

                case 4:
                    try {
                        List<EventoDTO> listaEventos = sistema.buscarEventos();

                        EventoDTO evento = new EventoDTO();
                        int elecEvento = 0;
                        do {
                            if (this.menuListadoEvento(listaEventos, "disponibles actualmente")) {
                                elecEvento = this.seleccionarOpcion();
                                if (elecEvento <= 0 || elecEvento > listaEventos.size()) {
                                    break;
                                } else {
                                    evento = listaEventos.get(elecEvento - 1);
                                }
                            } else {
                                break;
                            }
                        } while (!this.menuEvento(sistema, evento, "busqueda"));

                        if (evento != null) {
                            if (elecEvento != 0) {
                                this.gestionarEvento(sistema, evento);
                            }
                        } else {
                            System.out.println("|- El evento no es válido.                                            -|");
                        }
                    } catch(ListaEventosVacia e) {
                        System.err.print(e.getMessage());
                    }
                    break;

                case 5:
                    if (!sistema.isTokenValid(user.getToken())) {
                        System.out.println("|- Esta acción no está disponible, seleccione una del menú.          -|");
                    } else {

                        String nombre, descripcion, localizacion, tipo;
                        Integer capacidad, dia, mes, anio, hora, minutos;
                        capacidad = dia = mes = anio = hora = minutos = 0;

                        System.out.print("|- Introduzca el nombre del evento: ");
                        nombre = capt.nextLine();

                        System.out.print("|- Introduzca una descripción: ");
                        descripcion = capt.nextLine();

                        System.out.print("|- Introduzca el lugar donde se realizará: ");
                        localizacion = capt.nextLine();

                        boolean correcto = true;
                        do {
                            if (!correcto) {
                                System.out.println("|- No es un día válido, elija un número entre 1 y 30.                  -|");
                            }
                            try {
                                System.out.print("|- Introduzca el día del evento: ");
                                dia = capt.nextInt();
                                correcto = (dia > 0 && dia < 31);
                            } catch (InputMismatchException e) {
                                System.out.println("|- No es un día válido, elija un número entre 1 y 30.                  -|");
                                correcto = false;
                            }
                        } while (!correcto);

                        correcto = true;
                        do {
                            if (!correcto) {
                                System.out.println("|- No es un mes válido, elija un número entre 1 y 12.                  -|");
                            }
                            try {
                                System.out.print("|- Introduzca el mes del evento (en número): ");
                                mes = capt.nextInt();
                                correcto = (mes > 0 && mes < 13);
                            } catch (InputMismatchException e) {
                                System.out.println("|- No es un mes válido, elija un número entre 1 y 12.                  -|");
                                correcto = false;
                            }
                        } while (correcto);

                        correcto = true;
                        do {
                            if (!correcto) {
                                System.out.println("|- No es un año válido, elija un número después de 2017                -|");
                            }
                            try {
                                System.out.print("|- Introduzca el año del evento: ");
                                anio = capt.nextInt();
                                correcto = (anio > 2017);
                            } catch (InputMismatchException e) {
                                System.out.println("|- No es un año válido, elija un número después de 2017                -|");
                                correcto = false;
                            }
                        } while (correcto);

                        correcto = true;
                        do {
                            if (!correcto) {
                                System.out.println("|- No es una hora válida, elija un número entre 0 y 23               -|");
                            }
                            try {
                                System.out.print("|- Introduzca la hora del evento (formato 24h): ");
                                hora = capt.nextInt();
                                correcto = (hora > -1 && hora < 24);
                            } catch (InputMismatchException e) {
                                System.out.println("|- No es una hora válida, elija un número entre 0 y 23               -|");
                                correcto = false;
                            }
                        } while (correcto);

                        correcto = true;
                        do {
                            if (!correcto) {
                                System.out.println("|- No es una cantidad válida, elija una cantidad entre 0 y 59        -|");
                            }
                            try {
                                System.out.print("|- Introduzca los minutos del evento: ");
                                minutos = capt.nextInt();
                                correcto = (minutos > -1 && minutos < 60);
                            } catch (InputMismatchException e) {
                                System.out.println("|- No es una cantidad válida, elija una cantidad entre 0 y 59        -|");
                                correcto = false;
                            }
                        } while (correcto);

                        correcto = true;
                        do {
                            if (!correcto) {
                                System.out.println("|- No es un número válido, elija un número entero mayor de 0.        -|");
                            }
                            try {
                                System.out.print("|- ¿Qué capacidad máxima tendrá?: ");
                                capacidad = capt.nextInt();
                                correcto = (capacidad > -1);
                            } catch (InputMismatchException e) {
                                System.out.println("|- No es un número válido, elija un número entero mayor de 0.        -|");
                                correcto = false;
                            }
                        } while (correcto);

                        tipo = this.menuTipoEvento();
                        Date fecha = new Date(anio, mes, dia, hora, minutos);

                        int id = sistema.nuevoEvento(nombre, fecha, tipo, descripcion, capacidad, localizacion, user);
                        try {
                            EventoDTO newEvento = sistema.buscarEventoPorId(id);
                            System.out.println("|- Evento creado correctamente.                                      -|");
                            this.menuEvento(sistema, newEvento, "crear");
                        } catch (Exception e) {//TODO
                            System.err.print(e.getMessage());
                        }

                    }
                    break;

                case 6:
                    if (!sistema.isTokenValid(user.getToken())) {
                        System.out.println("|- Esta acción no está disponible, seleccione una del menú.          -|");
                    } else {
                        try {
                            List<EventoDTO> listaEventosInscritos = sistema.buscarEventosInscritos(user);

                            EventoDTO eventoUsuario = new EventoDTO();
                            int elecEventoInscrito = 0;
                            do {
                                if (this.menuListadoEvento(listaEventosInscritos, "en los que se ha inscrito")) {
                                    elecEventoInscrito = this.seleccionarOpcion();
                                    if (elecEventoInscrito <= 0 || elecEventoInscrito > listaEventosInscritos.size()) {
                                        break;
                                    }
                                    eventoUsuario = listaEventosInscritos.get(elecEventoInscrito - 1);
                                } else {
                                    break;
                                }
                            } while (!this.menuEvento(sistema, eventoUsuario, "busqueda"));

                            if (eventoUsuario != null) {
                                if (elecEventoInscrito != 0) {
                                    this.gestionarEvento(sistema, eventoUsuario);
                                }
                            } else {
                                System.out.println("|- El evento no es válido.                                            -|");
                            }
                        } catch(ListaEventosVacia e) {
                            System.err.print(e.getMessage());
                        }
                    }

                    break;

                case 7:
                    if (!sistema.isTokenValid(user.getToken())) {
                        System.out.println("|- Esta acción no está disponible, seleccione una del menú.          -|");

                    } else {
                        try {
                            List<EventoDTO> listaEventosOrganizados = sistema.buscarEventosOrganizados(user);

                            EventoDTO eventoUsuario = new EventoDTO();
                            int elecEventoOrganizado = 0;
                            do {
                                if (this.menuListadoEvento(listaEventosOrganizados, ("organizados por " + user.getUsername()))) {
                                    elecEventoOrganizado = this.seleccionarOpcion();
                                    if (elecEventoOrganizado <= 0 || elecEventoOrganizado > listaEventosOrganizados.size()) {
                                        break;
                                    }
                                    eventoUsuario = listaEventosOrganizados.get(elecEventoOrganizado - 1);
                                } else {
                                    break;
                                }
                            } while (!this.menuEvento(sistema, eventoUsuario, "busqueda"));

                            if (eventoUsuario != null) {
                                if (elecEventoOrganizado != 0) {
                                    this.gestionarEvento(sistema, eventoUsuario);
                                }
                            } else {
                                System.out.println("|- El evento no es válido.                                            -|");
                            }
                        } catch(ListaEventosVacia e) {
                                System.err.print(e.getMessage());
                        }

                    }
                    break;

                // BORRAR ANTES DE ENVIAR LA PRÁCTICA
                case 8:
                    String pass = "";
                    System.out.print("|- Introduzca la contraseña de administrador: ");
                    pass = capt.nextLine();
                    if (!sistema.godMode(pass)) {
                        System.out.println("|- No tiene permiso para acceder. ");
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

            if (eleccion != 0) {
                if (!this.seleccionarSN("Ir de nuevo al menú principal")) {
                    eleccion = 0;
                }
            }

        } while (eleccion != 0);

    }
}
