package battleship;

import javax.swing.*;
import java.io.IOException;
import java.util.Scanner;

public class BattleshipLauncher {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== BATTLESHIP ===");
        System.out.println("Selecciona tipo de interfaz:");
        System.out.println("1) Terminal");
        System.out.println("2) Gráfica");
        System.out.print("Opción: ");
        int interfaz = sc.nextInt();
        sc.nextLine();

        System.out.println("\nS2elecciona modo P2P:");
        System.out.println("1) Host (servidor)");
        System.out.println("2) Cliente");
        System.out.print("Opción: ");
        int modo = sc.nextInt();
        sc.nextLine();

        final int puerto = 5000;
        Tablero miTablero = new Tablero();
        miTablero.colocarBarcosAutomaticamente();

        if (interfaz == 1) {
            // ---------------- INTERFAZ TERMINAL ----------------
            if (modo == 1) {
                try {
                    System.out.println("Iniciando servidor en puerto " + puerto + "...");
                    ServidorP2P servidor = new ServidorP2P(puerto);
                    servidor.start();
                    System.out.println("Esperando conexión del cliente...");

                    JuegoP2P juego = new JuegoP2P(miTablero, servidor, true);
                    // loop simple de terminal
                    JuegoTerminal.run(juego, sc);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (modo == 2) {
                System.out.print("IP del host: ");
                String ip = sc.nextLine().trim();
                try {
                    ClienteP2P cliente = new ClienteP2P(ip, puerto);
                    cliente.start();

                    JuegoP2P juego = new JuegoP2P(miTablero, cliente, false);
                    JuegoTerminal.run(juego, sc);

                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("No se pudo conectar al host.");
                }
            }
        } else if (interfaz == 2) {
            // ---------------- INTERFAZ GRÁFICA ----------------
            if (modo == 1) {
                try {
                    ServidorP2P servidor = new ServidorP2P(puerto);
                    servidor.start();
                    JuegoP2P juego = new JuegoP2P(miTablero, servidor, true);
                    SwingUtilities.invokeLater(() -> new BattleshipGUIOnline(miTablero, juego));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (modo == 2) {
                System.out.print("IP del host: ");
                String ip = sc.nextLine().trim();
                try {
                    ClienteP2P cliente = new ClienteP2P(ip, puerto);
                    cliente.start();
                    JuegoP2P juego = new JuegoP2P(miTablero, cliente, false);
                    SwingUtilities.invokeLater(() -> new BattleshipGUIOnline(miTablero, juego));
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("No se pudo conectar al host.");
                }
            }
        } else {
            System.out.println("Opción de interfaz inválida. Saliendo.");
        }
    }
}
