/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.dae1819.clients;

import com.dae.dae1819.interfaces.SistemaInterface;
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
                    + "|- [3]. Crear evento                                                 -|" + "\n"
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
                    System.out.print("Introduzca el correo electronico:");
                    email = capt.next();
                    System.out.print("Introduzca el nombre de usuario:");
                    nombreusuario = capt.next();
                    System.out.print("Introduzca la contraseña:");
                    contrasena = capt.next();
                    System.out.print("Repita la contraseña:");
                    if (!contrasena.equals(capt.next())){
                        System.out.println("La contraseña introducida no es la misma");
                    } else {
                        System.out.println("La contraseña introducida es correcta");
                        sistema.nuevoUsuario(nombreusuario, contrasena, email);
                    }
                    break;
                    
                case 2:
                    System.out.println("Introduzca el nombre de usuario:");
                    nombreusuario = capt.next();
                    System.out.println("Introduzca la contraseña:");
                    contrasena = capt.next();
                    if (sistema.login(nombreusuario, contrasena)){
                        System.out.println("El inicio de sesión se creo correctamente");
                    } else {
                        System.out.println("Compruebe sus credenciales de sesión");
                    }
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
