package battleship;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.Random;

public class BattleshipP2PGUI extends JFrame {

    private JButton[][] myBoardButtons = new JButton[10][10];
    private JButton[][] enemyBoardButtons = new JButton[10][10];
    private int[][] myBoard = new int[10][10];
    private int[][] enemyBoard = new int[10][10];

    private boolean myTurn = false;

    private PrintWriter out;
    private BufferedReader in;

    public BattleshipP2PGUI(boolean isHost, String ip) {
        setTitle("Battleship P2P");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1,2));

        JPanel left = new JPanel(new GridLayout(10,10));
        JPanel right = new JPanel(new GridLayout(10,10));

        // Crear mis botones
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                JButton btn = new JButton();
                btn.setEnabled(false);
                myBoardButtons[i][j] = btn;
                left.add(btn);
            }
        }

        // Crear tablero enemigo
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                JButton btn = new JButton();
                int x=i, y=j;

                btn.addActionListener(e -> {
                    if(myTurn && enemyBoard[x][y] == 0){
                        enviar("SHOT " + x + " " + y);
                        myTurn = false;
                    }
                });

                enemyBoardButtons[i][j] = btn;
                right.add(btn);
            }
        }

        add(left);
        add(right);

        colocarBarcosAuto();
        pintarMiTablero();

        conectar(isHost, ip);

        setVisible(true);
    }

    private void colocarBarcosAuto(){
        Random r = new Random();
        int[] ships = {5,4,3,3,2};

        for(int s : ships){
            boolean colocado = false;

            while(!colocado){
                int x = r.nextInt(10);
                int y = r.nextInt(10);
                boolean horizontal = r.nextBoolean();

                if(horizontal){
                    if(y + s <= 10){
                        boolean libre = true;
                        for(int i=0;i<s;i++){
                            if(myBoard[x][y+i] != 0) libre=false;
                        }
                        if(libre){
                            for(int i=0;i<s;i++){
                                myBoard[x][y+i] = 1;
                            }
                            colocado = true;
                        }
                    }
                } else {
                    if(x + s <= 10){
                        boolean libre = true;
                        for(int i=0;i<s;i++){
                            if(myBoard[x+i][y] != 0) libre=false;
                        }
                        if(libre){
                            for(int i=0;i<s;i++){
                                myBoard[x+i][y] = 1;
                            }
                            colocado = true;
                        }
                    }
                }
            }
        }
    }

    private void pintarMiTablero(){
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                if(myBoard[i][j] == 1){
                    myBoardButtons[i][j].setBackground(Color.GRAY); // barco propio
                }
            }
        }
    }

    private void actualizarDisparoEnemigo(int x, int y){
        if(myBoard[x][y] == 1){
            myBoardButtons[x][y].setBackground(Color.RED);
            enviar("HIT " + x + " " + y);
        } else {
            myBoardButtons[x][y].setBackground(Color.WHITE);
            enviar("MISS " + x + " " + y);
        }
        myTurn = true;
    }

    private void actualizarResultado(int x,int y, boolean hit){
        enemyBoard[x][y] = hit ? 2 : 3;
        enemyBoardButtons[x][y].setBackground(hit ? Color.RED : Color.WHITE);
    }

    private void conectar(boolean isHost, String ip){
        new Thread(() -> {
            try{
                Socket s;
                if(isHost){
                    ServerSocket ss = new ServerSocket(5000);
                    System.out.println("Esperando conexión...");
                    s = ss.accept();
                    myTurn = true; // host comienza
                } else {
                    s = new Socket(ip, 5000);
                }

                out = new PrintWriter(s.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(s.getInputStream()));

                String line;
                while((line = in.readLine()) != null){
                    procesar(line);
                }

            } catch(Exception e){
                e.printStackTrace();
            }
        }).start();
    }

    private void procesar(String msg){
        System.out.println("Mensaje recibido: " + msg);

        String[] p = msg.split(" ");

        switch(p[0]){
            case "SHOT":
                actualizarDisparoEnemigo(
                    Integer.parseInt(p[1]),
                    Integer.parseInt(p[2])
                );
                break;

            case "HIT":
                actualizarResultado(
                    Integer.parseInt(p[1]),
                    Integer.parseInt(p[2]),
                    true
                );
                break;

            case "MISS":
                actualizarResultado(
                    Integer.parseInt(p[1]),
                    Integer.parseInt(p[2]),
                    false
                );
                break;
        }
    }

    private void enviar(String msg){
        out.println(msg);
    }
}
