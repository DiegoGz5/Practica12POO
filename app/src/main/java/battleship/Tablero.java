package battleship;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tablero {

    private final char[][] grid = new char[10][10];
    private final List<Barco> barcos = new ArrayList<>();

    public Tablero() {
        inicializar();
    }

    private void inicializar() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                grid[i][j] = '~'; // Agua
            }
        }
    }

    // Colocación manual (opcional)
    public boolean colocarBarco(int fila, int col, int tamaño, boolean horizontal) {
        if (horizontal && col + tamaño > 10) return false;
        if (!horizontal && fila + tamaño > 10) return false;

        // Checar colisiones
        for (int i = 0; i < tamaño; i++) {
            int f = fila + (horizontal ? 0 : i);
            int c = col + (horizontal ? i : 0);
            if (grid[f][c] != '~') return false;
        }

        Barco barco = new Barco(tamaño);
        barcos.add(barco);

        for (int i = 0; i < tamaño; i++) {
            int f = fila + (horizontal ? 0 : i);
            int c = col + (horizontal ? i : 0);
            grid[f][c] = 'B';
        }

        return true;
    }

    // Colocación automática de los 5 barcos clásicos
    public void colocarBarcosAutomaticamente() {
        int[] tamaños = {5, 4, 3, 3, 2}; // Portaaviones, Acorazado, Crucero, Submarino, Destructor
        Random rand = new Random();

        for (int tamaño : tamaños) {
            boolean colocado = false;
            while (!colocado) {
                boolean horizontal = rand.nextBoolean();
                int fila = rand.nextInt(10);
                int col = rand.nextInt(10);
                colocado = colocarBarco(fila, col, tamaño, horizontal);
            }
        }
    }

    public String disparar(int fila, int col) {
        char celda = grid[fila][col];

        switch (celda) {
            case '~':
                grid[fila][col] = 'O'; // Agua fallada
                return "agua";

            case 'B':
                grid[fila][col] = 'X'; // Impacto
                registrarImpacto();
                return "impacto";

            case 'O':
            case 'X':
                return "repetido";

            default:
                return "error";
        }
    }

    private void registrarImpacto() {
        for (Barco b : barcos) {
            if (!b.estaHundido()) {
                b.registrarImpacto();
                break;
            }
        }
    }

    public boolean todosHundidos() {
        for (Barco b : barcos) {
            if (!b.estaHundido()) return false;
        }
        return true;
    }

    // Devuelve el tablero propio (con barcos visibles)
    public void mostrarTableroPropio() {
        System.out.println("\n=== TU TABLERO ===");
        mostrarTablero(true);
    }

    // Devuelve el tablero enemigo (sin barcos visibles)
    public void mostrarTableroEnemigo() {
        System.out.println("\n=== TABLERO ENEMIGO ===");
        mostrarTablero(false);
    }

    private void mostrarTablero(boolean mostrarBarcos) {
        System.out.print("  ");
        for (int i = 0; i < 10; i++) System.out.print(i + " ");
        System.out.println();

        for (int i = 0; i < 10; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < 10; j++) {
                char c = grid[i][j];
                if (!mostrarBarcos && c == 'B') c = '~';
                System.out.print(c + " ");
            }
            System.out.println();
        }

        System.out.println("Leyenda: ~=Agua, B=Barco, X=Impacto, O=Fallo");
    }


    public char[][] getGrid() {
    return grid;
    
    }

    public void mostrar() {
    for (int i = 0; i < 10; i++) {
        for (int j = 0; j < 10; j++) {
            System.out.print(grid[i][j] + " ");
        }
        System.out.println();
    }
}

public void mostrarOculto() {
    for (int i = 0; i < 10; i++) {
        for (int j = 0; j < 10; j++) {
            if (grid[i][j] == 'B')
                System.out.print("~ ");
            else
                System.out.print(grid[i][j] + " ");
        }
        System.out.println();
    }
}

public void marcarImpacto(int f, int c) {
    grid[f][c] = 'X';
}

public void marcarAgua(int f, int c) {
    grid[f][c] = 'O';
}

public boolean recibirDisparo(int f, int c) {
    if (grid[f][c] == 'B') {
        grid[f][c] = 'X';
        return true;
    }
    if (grid[f][c] == '~') {
        grid[f][c] = 'O';
    }
    return false;
}

}

