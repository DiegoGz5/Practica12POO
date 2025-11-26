package battleship;

import java.util.Scanner;

public class BattleshipUI {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== BATTLESHIP ===");

        // Pedir nombre del jugador
        System.out.print("Ingresa tu nombre: ");
        String nombre = sc.nextLine();

        System.out.println("\nHola " + nombre + "! Elige la interfaz de usuario:");
        System.out.println("1. Grafica");
        System.out.println("2. Consola");
        System.out.print("Opción: ");

        int opcion = sc.nextInt();

        switch (opcion) {
             case 1:
                Tablero t1 = new Tablero();
                Tablero t2 = new Tablero();
                t1.colocarBarcosAutomaticamente();
                t2.colocarBarcosAutomaticamente();

                 new BattleshipGUI(t1, t2);
                 break;

            case 2:
                System.out.println("Iniciando juego en modo prueba (consola)...\n");

                // Tableros y juego como antes
                Tablero t3 = new Tablero();
                Tablero t4 = new Tablero();

                // Colocar barcos automáticamente
                t3.colocarBarcosAutomaticamente();
                t4.colocarBarcosAutomaticamente();

                // Iniciar juego
                Juego game = new Juego(t3, t4);
                game.iniciar();
                break;
            default:
                System.out.println("Opción inválida. Saliendo...");
        }

        sc.close();
    }
    
}
