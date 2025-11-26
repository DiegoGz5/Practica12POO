package battleship;

import java.util.Scanner;

public class Juego {

    private final Tablero jugador1;
    private final Tablero jugador2;

    public Juego(Tablero t1, Tablero t2) {
        this.jugador1 = t1;
        this.jugador2 = t2;
    }

public void iniciar() {
    Scanner sc = new Scanner(System.in);

    System.out.println("Inicia la batalla naval!");

    while (true) {
        turno(sc, jugador2, jugador1, "Jugador 1");
        if (jugador2.todosHundidos()) {
            System.out.println("¡Jugador 1 gana!");
            break;
        }

        turno(sc, jugador1, jugador2, "Jugador 2");
        if (jugador1.todosHundidos()) {
            System.out.println("¡Jugador 2 gana!");
            break;
        }
    }
}


private void turno(Scanner sc, Tablero enemigo, Tablero propio, String nombreJugador) {
    System.out.println("\n=== Tablero de " + nombreJugador + " ===");
    propio.mostrarTableroPropio();

    System.out.println("\n=== Tablero enemigo ===");
    enemigo.mostrarTableroEnemigo();

    System.out.print("Fila: ");
    int fila = sc.nextInt();

    System.out.print("Columna: ");
    int col = sc.nextInt();

    String resultado = enemigo.disparar(fila, col);
    System.out.println("Resultado: " + resultado);
}

}
