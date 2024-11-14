package noventagrados.multijugador;

import java.io.*;
import java.net.*;
import noventagrados.modelo.Tablero;
import noventagrados.control.Arbitro;
import noventagrados.modelo.Jugada;
import noventagrados.util.Color;
import noventagrados.modelo.Celda;
import noventagrados.util.Coordenada;

public class Servidor {
    private ServerSocket serverSocket;
    private Socket clientSocket1, clientSocket2;
    private BufferedReader in1, in2;
    private PrintWriter out1, out2;
    private Tablero tablero;  // Instancia del tablero
    private static Arbitro arbitro;

    public void iniciar() {
        try {
            serverSocket = new ServerSocket(12345);
            System.out.println("Servidor iniciado. Esperando jugadores...");

            // Aceptar la conexión de dos clientes
            clientSocket1 = serverSocket.accept();
            System.out.println("Primer cliente conectado desde " + clientSocket1.getInetAddress());
            clientSocket2 = serverSocket.accept();
            System.out.println("Segundo cliente conectado desde " + clientSocket2.getInetAddress());

            // Crear flujos para ambos clientes
            in1 = new BufferedReader(new InputStreamReader(clientSocket1.getInputStream()));
            out1 = new PrintWriter(clientSocket1.getOutputStream(), true);
            in2 = new BufferedReader(new InputStreamReader(clientSocket2.getInputStream()));
            out2 = new PrintWriter(clientSocket2.getOutputStream(), true);

            // Inicializar el tablero y el árbitro
            tablero = new Tablero();
            arbitro = new Arbitro(tablero); // Asegúrate de que el árbitro reciba el tablero

            // Enviar instrucciones e inicio del juego
            out1.println("Bienvenido al juego de Noventa Grados 1.0");
            out1.println("Introduce tus jugadas con el formato dd-dd donde d es un dígito en el rango [0, 6].");

            out2.println("Bienvenido al juego de Noventa Grados 1.0");
            out2.println("Introduce tus jugadas con el formato dd-dd donde d es un dígito en el rango [0, 6].");

            // Enviar el tablero inicial a ambos jugadores
            mostrarTableroServidor(out1);
            mostrarTableroServidor(out2);

            // Iniciar la partida, alternando turnos
            boolean turnoJugador1 = true;
            String jugada;
            while (true) {
                if (turnoJugador1) {
                    out1.println("Es tu turno");
                    jugada = in1.readLine();
                    if (jugada.equals("salir")) {
                        break;
                    }
                    // Procesar la jugada del jugador 1
                    procesarJugada(jugada, 1);
                    out2.println("Es el turno del jugador 1: " + jugada);
                } else {
                    out2.println("Es tu turno");
                    jugada = in2.readLine();
                    if (jugada.equals("salir")) {
                        break;
                    }
                    // Procesar la jugada del jugador 2
                    procesarJugada(jugada, 2);
                    out1.println("Es el turno del jugador 2: " + jugada);
                }

                // Enviar el tablero actualizado a ambos jugadores
                mostrarTableroServidor(out1);
                mostrarTableroServidor(out2);

                // Verificar si la partida ha terminado
                if (arbitro.estaFinalizadaPartida()) {
                    Color ganador = arbitro.consultarTurnoGanador();
                    if (ganador != null) {
                        out1.println("El ganador es: " + ganador);
                        out2.println("El ganador es: " + ganador);
                        break;
                    }
                }

                turnoJugador1 = !turnoJugador1; // Cambiar turno
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mostrarTableroServidor(PrintWriter out) {
        out.println(tablero.aTexto());  // Enviar la representación del tablero al cliente
    }

    public void procesarJugada(String jugada, int jugador) {
        // Convertir la cadena de jugada en un objeto Jugada

        // Suponiendo que la jugada está en el formato "dd-dd" donde 'd' es un dígito.
        // Primero, extraemos las coordenadas del origen y destino
        String[] partes = jugada.split("-");
        String origenStr = partes[0];
        String destinoStr = partes[1];

        // Convertir las coordenadas de origen
        int filaOrigen = Integer.parseInt(String.valueOf(origenStr.charAt(0)));
        int columnaOrigen = Integer.parseInt(String.valueOf(origenStr.charAt(1)));
        
        // Convertir las coordenadas de destino
        int filaDestino = Integer.parseInt(String.valueOf(destinoStr.charAt(0)));
        int columnaDestino = Integer.parseInt(String.valueOf(destinoStr.charAt(1)));

        // Crear las celdas de origen y destino
        Celda origen = new Celda(new Coordenada(filaOrigen, columnaOrigen));
        Celda destino = new Celda(new Coordenada(filaDestino, columnaDestino));

        // Crear la jugada
        Jugada jugadaObj = new Jugada(origen, destino);

        // Aquí debes pedirle al árbitro que valide y procese la jugada
        if (arbitro.esMovimientoLegal(jugadaObj)) {
            arbitro.empujar(jugadaObj);  // Actualiza el tablero con la jugada
        } else {
            System.out.println("Jugada no válida");
        }
    }


    public static void main(String[] args) {
        Servidor servidor = new Servidor();
        servidor.iniciar();
    }
}
