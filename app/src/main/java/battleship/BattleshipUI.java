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
                System.out.println("Interfaz de consola próximamente...");
                break;
            case 2:
                System.out.println("Iniciando juego en modo prueba (consola)...\n");

                // Tableros y juego como antes
                Tablero t1 = new Tablero();
                Tablero t2 = new Tablero();

                // Colocar barcos automáticamente
                t1.colocarBarcosAutomaticamente();
                t2.colocarBarcosAutomaticamente();

                // Iniciar juego
                Juego game = new Juego(t1, t2);
                game.iniciar();
                break;
            default:
                System.out.println("Opción inválida. Saliendo...");
        }

        sc.close();
    }
}
