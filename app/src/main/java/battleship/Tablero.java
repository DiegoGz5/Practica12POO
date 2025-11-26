package battleship;

import java.util.ArrayList;
import java.util.List;

public class Tablero {

    private final char[][] grid = new char[10][10];
    private final List<Barco> barcos = new ArrayList<>();

    public Tablero() {
        inicializar();
    }

    private void inicializar() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                grid[i][j] = '~';   // agua
            }
        }
    }

    public boolean colocarBarco(int fila, int col, int tamaño, boolean horizontal) {
        // Validaciones
        if (horizontal && col + tamaño > 10) return false;
        if (!horizontal && fila + tamaño > 10) return false;

        // Checar colisiones
        for (int i = 0; i < tamaño; i++) {
            int f = fila + (horizontal ? 0 : i);
            int c = col + (horizontal ? i : 0);

            if (grid[f][c] != '~') return false;
        }

        // Colocar barco
        Barco barco = new Barco(tamaño);
        barcos.add(barco);

        for (int i = 0; i < tamaño; i++) {
            int f = fila + (horizontal ? 0 : i);
            int c = col + (horizontal ? i : 0);
            grid[f][c] = 'B';
        }

        return true;
    }

    public String disparar(int fila, int col) {
        char celda = grid[fila][col];

        switch (celda) {
            case '~':
                grid[fila][col] = 'O'; // agua fallada
                return "agua";

            case 'B':
                grid[fila][col] = 'X'; // impacto
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

    public char[][] getGrid() {
        return grid;
    }

public void mostrarTablero() {
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
    System.out.println("\nLeyenda: ~=Agua, B=Barco, X=Impacto, O=Fallo");
    }

    public void mostrarTableroPropio() {
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
    System.out.println("\nLeyenda: ~=Agua, B=Barco, X=Impacto, O=Fallo");
}

public void mostrarTableroEnemigo() {
    System.out.print("  ");
    for (int i = 0; i < 10; i++) System.out.print(i + " ");
    System.out.println();
    
    for (int i = 0; i < 10; i++) {
        System.out.print(i + " ");
        for (int j = 0; j < 10; j++) {
            char c = grid[i][j];
            if (c == 'X' || c == 'O') {
                System.out.print(c + " ");
            } else {
                System.out.print("? ");
            }
        }
        System.out.println();
    }
    System.out.println("\nLeyenda: ?=Desconocido, X=Impacto, O=Fallo");
}


}


