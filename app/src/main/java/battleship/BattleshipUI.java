package battleship;
public class BattleshipUI {

    public static void main(String[] args) {

        Tablero t1 = new Tablero();
        Tablero t2 = new Tablero();

        // Colocación simple, puede expandirse
        t1.colocarBarco(0, 0, 3, true);
        t2.colocarBarco(5, 5, 3, true);

        Juego game = new Juego(t1, t2);
        game.iniciar();
    }
}
