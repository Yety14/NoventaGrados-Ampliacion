package noventagrados.multijugador;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;
    private Scanner scanner;

    public void iniciar() {
        try {
            socket = new Socket("localhost", 12345);  // Se conecta al servidor en localhost
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            scanner = new Scanner(System.in);

            String mensaje;
            while ((mensaje = in.readLine()) != null) {
                System.out.println(mensaje); // Muestra el mensaje del servidor (instrucciones, tablero, etc.)

                if (mensaje.contains("Es tu turno")) {
                    // Aquí deberías pedir al usuario que ingrese una jugada
                    System.out.println("Introduce tu jugada (dd-dd): ");
                    String jugada = scanner.nextLine();
                    out.println(jugada); // Enviar la jugada al servidor
                }

                // Recibir y mostrar la respuesta del servidor (tablero actualizado)
                if (mensaje.equals("Tablero actualizado")) {
                    recibirYMostrarTablero();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mostrarTableroActualizado(String tableroTexto) {
        System.out.println(tableroTexto); // Muestra el tablero actualizado en la consola
    }

    public void recibirYMostrarTablero() {
        try {
            String tableroTexto = in.readLine(); // Leer la representación del tablero desde el servidor
            mostrarTableroActualizado(tableroTexto); // Mostrar el tablero actualizado
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Cliente cliente = new Cliente();
        cliente.iniciar(); // Iniciar el cliente
    }
}
