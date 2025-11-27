package battleship;

import java.util.Scanner;

public class JuegoTerminal {

    public static void run(JuegoP2P juego, Scanner sc) {
        System.out.println("=== Batalla Naval - Terminal ===");
        System.out.println("Tablero inicial generado automáticamente.\n");

        while (true) {
            if (juego.esMiTurno()) {
                mostrarTableros(juego);

                System.out.println("\nTu turno:");
                int fila = leerEntero(sc, "Fila (0-9): ", 0, 9);
                int col = leerEntero(sc, "Columna (0-9): ", 0, 9);

                boolean disparoEnviado = juego.intentarDisparo(fila, col);
                if (!disparoEnviado) {
                    System.out.println("No puedes disparar todavía, espera tu turno o a que llegue el resultado anterior.");
                }
            } else {
                System.out.println("\nEsperando turno del rival...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void mostrarTableros(JuegoP2P juego) {
        // Accedemos a los tableros usando reflexión simple o, más fácil: agregamos getters en JuegoP2P
        char[][] miGrid = juego.getMiTablero().getGrid();
        char[][] enemigoGrid = juego.getTableroEnemigo().getGrid();

        System.out.println("\n=== Tu tablero ===");
        imprimirGrid(miGrid);

        System.out.println("\n=== Tablero enemigo ===");
        imprimirGridEnemigo(enemigoGrid);
    }

    private static void imprimirGrid(char[][] grid) {
        System.out.print("  ");
        for (int i = 0; i < 10; i++) System.out.print(i + " ");
        System.out.println();
        for (int i = 0; i < 10; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < 10; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("Leyenda: ~=Agua, B=Barco, X=Impacto, O=Fallo");
    }

    private static void imprimirGridEnemigo(char[][] grid) {
        System.out.print("  ");
        for (int i = 0; i < 10; i++) System.out.print(i + " ");
        System.out.println();
        for (int i = 0; i < 10; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < 10; j++) {
                char c = grid[i][j];
                if (c == 'X' || c == 'O') System.out.print(c + " ");
                else System.out.print("~ "); // Ocultamos barcos enemigos
            }
            System.out.println();
        }
        System.out.println("Leyenda: ~=Agua, X=Impacto, O=Fallo");
    }

    private static int leerEntero(Scanner sc, String mensaje, int min, int max) {
        int val;
        while (true) {
            System.out.print(mensaje);
            try {
                val = Integer.parseInt(sc.nextLine());
                if (val >= min && val <= max) break;
            } catch (NumberFormatException ignored) {}
            System.out.println("Entrada inválida. Intenta nuevamente.");
        }
        return val;
    }
}
