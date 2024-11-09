package noventagrados.textui;

import java.util.Scanner;

import noventagrados.control.Arbitro;
import noventagrados.modelo.Celda;
import noventagrados.modelo.Jugada;
import noventagrados.modelo.Tablero;
import noventagrados.util.Coordenada;

/**
 * Noventa grados en modo texto.
 * 
 * Se abusa del uso del modificador static tanto en atributos como en métodos
 * para comprobar su similitud a variables globales y funciones globales de
 * otros lenguajes.
 * 
 * La programación en este código sigue más el paradigma de programación
 * estructurada en mayor medida que la orientación a objetos.
 * 
 * En algunos casos los métodos estáticos son meros envoltorios o "wrappers" de
 * invocaciones a métodos del árbitro.
 *
 * @author <a href="rmartico@ubu.es">Raúl Marticorena</a>
 * @author AÑADIR COAUTOR/A
 * @since 1.0
 * @version 1.0
 * @see noventagrados.modelo
 * @see noventagrados.control
 * @see noventagrados.util
 */
public class NoventaGrados {

	/** Tamaño en caracteres de una jugada. */
	private static final int TAMAÑO_JUGADA = 5;

	/** Posición en el texto de una jugada de la coordenada destino. */
	private static final int INICIO_COORDENADA_DESTINO = 3;

	/** Texto para interrumpir la partida. */
	private static final String TEXTO_SALIR = "salir";

	/** Tablero. */
	private static Tablero tablero;

	/** Árbitro. */
	private static Arbitro arbitro;

	/** Lector por teclado. */
	private static Scanner scanner;

	/** Oculta el constructor por defecto. */
	private NoventaGrados() {
	}

	/**
	 * Método raíz con el algoritmo principal en modo texto.
	 * 
	 * @param args argumentos de entrada en línea de comandos
	 */
	public static void main(String[] args) {
	    mostrarMensajeBienvenida();    // Mensaje de bienvenida para el usuario.
	    inicializarPartida();           // Inicialización de los componentes del juego.

	    // Bucle principal de la partida.
	    while (true) {
	        mostrarTablero();           // Muestra el estado actual del tablero.

	        // Recoge la jugada del usuario y verifica si quiere salir.
	        String jugadaTexto = recogerTextoDeJugadaPorTeclado();
	        if (comprobarSalir(jugadaTexto)) {
	            break;                  // Sale del bucle principal si el usuario escribe "salir".
	        }

	        // Verifica si la partida ha finalizado antes de continuar.
	        if (comprobarFinalizacionPartida()) { 
	            finalizarPartida();
	            mostrarGanador();       // Muestra el ganador al finalizar la partida.
	            inicializarPartida();    // Reinicia la partida.
	            continue;               // Comienza una nueva partida.
	        }

	        // Valida el formato de la jugada ingresada.
	        if (!validarFormato(jugadaTexto)) {
	            mostrarErrorEnFormatoDeEntrada();  // Error si el formato de la jugada es incorrecto.
	            continue;               // Pide al usuario que vuelva a introducir la jugada.
	        }

	        // Extrae y valida la jugada.
	        Jugada jugada = extraerJugada(jugadaTexto);
	        if (!esLegal(jugada)) {
	            mostrarErrorPorMovimientoIlegal(jugadaTexto); // Muestra error si la jugada es ilegal.
	            continue;               // Pide una nueva jugada.
	        }

	        // Ejecuta la jugada y actualiza el tablero.
	        realizarEmpujón(jugada);
	        cambiarTurnoPartida();       // Cambia el turno al siguiente jugador.
	    }

	    finalizarPartida();              // Cierra la partida de manera segura.
	}
	/**
	 * Inicializa el estado de los elementos de la partida.
	 */
	private static void inicializarPartida() {
		// Inicializaciones
		tablero = new Tablero();
		arbitro = new Arbitro(tablero);
		// Cargar piezas con la configuración inicial...
		arbitro.colocarPiezasConfiguracionInicial();
		// Abrir la lectura desde teclado...
		scanner = new Scanner(System.in);
	}

	/**
	 * Recoge el texto de la jugada por teclado.
	 * 
	 * @return jugada jugada en formato texto
	 */
	private static String recogerTextoDeJugadaPorTeclado() {
		System.out.print("Introduce jugada turno con piezas de color " + arbitro.consultarTurno() + ": ");
		return scanner.next();
	}

	/**
	 * Comprueba si se quiere finalizar la partida por parte de los usuarios.
	 * 
	 * @param jugada jugada en formato texto
	 * @return true si el usuario introduce salir, false en caso contrario
	 */
	private static boolean comprobarSalir(String jugada) {
		return jugada.equalsIgnoreCase(TEXTO_SALIR);
	}

	/**
	 * Valida la corrección del formato de la jugada. Solo comprueba la corrección
	 * del formato de entrada en cuanto al tablero, no la validez de la jugada en
	 * cuanto a las reglas del juego.
	 * 
	 * La jugada tiene que tener cuatro digitos separados por un guion.
	 * 
	 * Otra mejor solución alternativa es el uso de expresiones regulares (se verán
	 * con más detalle en la asignatura de 3º Procesadores del Lenguaje).
	 * 
	 * @param textoJugada a validar
	 * @return true si el formato de la jugada es correcta según las coordenadas
	 *         disponibles del tablero
	 */
	private static boolean validarFormato(String textoJugada) {
		// si la longitud es correcta y a la mitad hay un guion...
		System.out.println(textoJugada.charAt(TAMAÑO_JUGADA / 2));
		if (textoJugada.length() == TAMAÑO_JUGADA && textoJugada.charAt(TAMAÑO_JUGADA / 2) == '-') {
			// acabar de validar dígitos en el resto de valores...
			String origen = textoJugada.substring(0, INICIO_COORDENADA_DESTINO);
			String destino = textoJugada.substring(INICIO_COORDENADA_DESTINO, TAMAÑO_JUGADA);
			// comprobar si ambos textos son correctos
			return esTextoCorrectoParaCoordenada(origen) && esTextoCorrectoParaCoordenada(destino);
		}
		return false;
	}

	/**
	 * Comprueba que el texto de la coordenada son dos dígitos en el rango [0, 6].
	 * 
	 * @param textoCoordenada texto de la coordenada con dos caracteres
	 * @return true si son dos dígitos entre [0, 6], false en caso contrario
	 */
	private static boolean esTextoCorrectoParaCoordenada(String textoCoordenada) {
		char primerCaracter = textoCoordenada.charAt(0);
		char segundoCaracter = textoCoordenada.charAt(1);
		return (primerCaracter >= '0' && primerCaracter <= '6' && segundoCaracter >= '0' && segundoCaracter <= '6');
	}

	/**
	 * Extrae la jugada a partir del texto introducido por teclado.
	 * 
	 * @param jugadaTexto texto con la jugada
	 * @return jugada
	 * @see #extraerCoordenada(String, int, int)
	 */
	private static Jugada extraerJugada(String jugadaTexto) {
		Coordenada coordenadaOrigen = extraerCoordenada(jugadaTexto, 0, INICIO_COORDENADA_DESTINO - 1);
		Coordenada coordenadaDestino = extraerCoordenada(jugadaTexto, INICIO_COORDENADA_DESTINO, TAMAÑO_JUGADA);
		Celda origen = tablero.consultarCelda(coordenadaOrigen);
		Celda destino = tablero.consultarCelda(coordenadaDestino);
		return new Jugada(origen, destino);
	}

	/**
	 * Extrae una coordenada a partir del texto de entrada y de las posiciones
	 * [incio, fin) indicadas.
	 * 
	 * Dada una jugada en texto, extraerá la coordenada origen o destino, en función
	 * de la posición de inicio y fin dada.
	 * 
	 * @param jugada texto en formato notación habitual (e.g. 2013)
	 * @param inicio posición en el texto a partir del cual leer
	 * @param fin    posición final - 1, hasta donde leer el texto
	 * @return coordenada o null, si no es posible extraerla
	 */
	private static Coordenada extraerCoordenada(String jugada, int inicio, int fin) {
		if (jugada.length() != TAMAÑO_JUGADA)
			return null;
		String textoExtraido = jugada.substring(inicio, fin);
		int fila = Integer.parseInt(textoExtraido.substring(0, 1));
		int columna = Integer.parseInt(textoExtraido.substring(1, 2));
		return new Coordenada(fila, columna);
	}

	/**
	 * Comprueba la legalidad de la jugada.
	 * 
	 * @param jugada jugada
	 * @return true si es legal, false en caso contrario
	 */
	private static boolean esLegal(Jugada jugada) {
		return arbitro.esMovimientoLegal(jugada);
	}

	/**
	 * Realizar la jugada completando el movimiento y las capturas correspondientes.
	 * 
	 * @param jugada jugada
	 */
	private static void realizarEmpujón(Jugada jugada) {
		arbitro.empujar(jugada);
	}

	/**
	 * Cambia el turno de la partida.
	 */
	private static void cambiarTurnoPartida() {
		arbitro.cambiarTurno();
	}

	/**
	 * Comprueba si está finalizada la partida.
	 * 
	 * @return true si hay victoria de atacante o defensor, false en caso contrario
	 */
	private static boolean comprobarFinalizacionPartida() {
		return arbitro.estaFinalizadaPartida();
	}

	/**
	 * Finaliza la partida cerrando recursos abiertos como el teclado.
	 */
	private static void finalizarPartida() {
		System.out.println("Partida finalizada.");
		scanner.close();
	}

	/**
	 * Muestra el mensaje de bienvenida con instrucciones para finalizar la partida.
	 */
	private static void mostrarMensajeBienvenida() {
		System.out.println("Bienvenido al juego de Noventa Grados 1.0");
		System.out.println(
				"Introduzca sus jugadas con el formato dd-dd donde d es un dígito en el rango [0, 6] (por ejemplo 00-04 o 65-63).");
		System.out.println("Para interrumpir la partida introduzca \"salir\".");
		System.out.println("Disfrute de la partida...");
	}

	/**
	 * Muestra la información de error en el formato de entrada, mostrando ejemplos.
	 */
	private static void mostrarErrorEnFormatoDeEntrada() {
		System.out.println();
		System.out.println("Error en el formato de entrada.");
		System.out.println(
				"Se requieren cuadro dígitos en parejas separados por un guion, por ejemplo 04-06 o 62-63, o bien introducir la cadena \"salir\" para finalizar la partida.");
		System.out.println("Los números estarán siempre en el rango [0,6].");
	}

	/**
	 * Informa de la ilegalidad de la jugada intentada.
	 * 
	 * @param textoJugada texto de la jugada introducido por teclado
	 */
	private static void mostrarErrorPorMovimientoIlegal(String textoJugada) {
		System.out.printf("%nLa jugada %s es ilegal.%nRevise las reglas del juego.%n", textoJugada);
	}

	/**
	 * Muestra el estado del tablero con sus piezas actuales en pantalla.
	 */
	private static void mostrarTablero() {
		System.out.println();
		System.out.println(tablero.aTexto());
	}

	/**
	 * Muestra el turno ganador de la partida en pantalla.
	 */
	private static void mostrarGanador() {
		if (arbitro.estaFinalizadaPartida() && arbitro.consultarTurnoGanador() != null) {
			System.out.printf("%nHa ganado la partida el turno con piezas de color %s.%n",
					arbitro.consultarTurnoGanador());
		} else {
			System.out.println("\nEmpate con ambas reinas empujadas fuera del tablero.");
		}
	}
}
