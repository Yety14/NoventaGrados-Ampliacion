package noventagrados.modelo;

import noventagrados.util.Coordenada;

import java.util.Arrays;

/**
 * Clase que representa el tablero del juego. El tablero es una cuadrícula de
 * 7x7 celdas.
 * 
 * @author Víctor Vidal Vivanco
 * @author Guillermo López de Arechavaleta Zapatero
 * @version 1.0
 * @since 1.0
 */

public class Tablero {

	/** Número de filas en el tablero. */
	private static final int FILAS = 7;

	/** Número de columnas en el tablero. */
	private static final int COLUMNAS = 7;

	/**
	 * Matriz que representa las celdas del tablero.
	 * 
	 * Cada celda puede contener una pieza del juego o estar vacía. La matriz es de
	 * tamaño 7 x 7.
	 */

	private final Celda[][] celdas;

	/**
	 * Constructor que inicializa el tablero con celdas vacías.
	 */
	public Tablero() {
		celdas = new Celda[FILAS][COLUMNAS];
		for (int fila = 0; fila < FILAS; fila++) {
			for (int columna = 0; columna < COLUMNAS; columna++) {
				celdas[fila][columna] = new Celda(new Coordenada(fila, columna));
			}
		}
	}

	/**
	 * Devuelve el número de filas del tablero.
	 * 
	 * @return el número de filas
	 */
	public int consultarNumeroFilas() {
		return FILAS;
	}

	/**
	 * Devuelve el número de columnas del tablero.
	 * 
	 * @return el número de columnas
	 */
	public int consultarNumeroColumnas() {
		return COLUMNAS;
	}

	/**
	 * Coloca una pieza en la coordenada especificada del tablero.
	 * 
	 * @param pieza      la pieza a colocar
	 * @param coordenada la coordenada donde colocar la pieza
	 */
	public void colocar(Pieza pieza, Coordenada coordenada) {
		if (pieza != null && coordenada != null && estanEnTablero(coordenada)) {
			celdas[coordenada.fila()][coordenada.columna()].colocar(pieza);
		}
	}

	/**
	 * Devuelve un array con todas las celdas del tablero.
	 * 
	 * @return un array de Celda con clones de todas las celdas
	 */
	public Celda[] consultarCeldas() {
		Celda[] celdasArray = new Celda[FILAS * COLUMNAS];
		int indice = 0;
		for (int fila = 0; fila < FILAS; fila++) {
			for (int columna = 0; columna < COLUMNAS; columna++) {
				celdasArray[indice++] = celdas[fila][columna].clonar(); // Clona cada celda antes de añadir
			}
		}
		return celdasArray;
	}

	/**
	 * Consulta la celda en la coordenada especificada.
	 * 
	 * @param coordenada la coordenada de la celda a consultar
	 * @return un clon de la celda en la coordenada especificada, o null si la
	 *         coordenada es inválida
	 */
	public Celda consultarCelda(Coordenada coordenada) {
		if (estanEnTablero(coordenada)) {
			return celdas[coordenada.fila()][coordenada.columna()].clonar(); // devuelve un clon en profundidad
		}
		return null;
	}

	/**
	 * Elimina la pieza de la celda en la coordenada especificada.
	 * 
	 * @param coordenada la coordenada de la celda de la que eliminar la pieza
	 */
	public void eliminarPieza(Coordenada coordenada) {
		if (coordenada != null && estanEnTablero(coordenada)) {
			obtenerCelda(coordenada).eliminarPieza();
		}
	}

	/**
	 * Verifica si una coordenada está dentro del tablero.
	 * 
	 * @param coordenada la coordenada a verificar
	 * @return true si la coordenada está dentro del tablero, false en caso
	 *         contrario
	 */
	public boolean estanEnTablero(Coordenada coordenada) {
		int fila = coordenada.fila();
		int columna = coordenada.columna();
		return fila >= 0 && fila < FILAS && columna >= 0 && columna < COLUMNAS;
	}

	/**
	 * Obtiene la celda en la coordenada especificada sin clonarla.
	 * 
	 * @param coordenada la coordenada de la celda a obtener
	 * @return la celda en la coordenada especificada, o null si la coordenada es
	 *         inválida
	 */
	Celda obtenerCelda(Coordenada coordenada) {
		return estanEnTablero(coordenada) ? celdas[coordenada.fila()][coordenada.columna()] : null;
	}

	/**
	 * Genera una representación textual del tablero.
	 * 
	 * @return una cadena que representa el estado actual del tablero
	 */
	public String aTexto() {
		StringBuilder sb = new StringBuilder();
		String tipoPieza;
		char colorPieza = ' ';
		sb.append("\n");
		for (int fila = 0; fila < FILAS; fila++) {
			sb.append(fila + " ");
			for (int columna = 0; columna < COLUMNAS; columna++) {
				Celda celda = celdas[fila][columna];
				// En vez de mostrar todos los datos correspondientes de la pieza, hay que
				// mostrar
				// la concatenación de los chars correspondientes
				tipoPieza = celda.estaVacia() ? " -- " : " " + celda.consultarPieza().consultarTipoPieza().toChar();
				colorPieza = celda.estaVacia() ? ' ' : celda.consultarColorDePieza().toChar();
				sb.append(celda.estaVacia() ? " -- " : tipoPieza + colorPieza + " ");
			}
			sb.append("\n");
			// Imprimir el número de las columnas por debajo de la ultima fila
			if (fila == FILAS - 1) {
				for (int i = 0; i < FILAS; i++) {
					sb.append("   " + i);
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	/**
	 * Crea y devuelve una copia profunda del tablero.
	 * 
	 * @return un nuevo Tablero con el mismo estado que este
	 */
	public Tablero clonar() {
		Tablero clon = new Tablero();
		for (int fila = 0; fila < FILAS; fila++) {
			for (int columna = 0; columna < COLUMNAS; columna++) {
				clon.celdas[fila][columna] = this.celdas[fila][columna].clonar();
			}
		}
		return clon;
	}

	/**
	 * Compara este tablero con otro objeto para determinar si son iguales.
	 * 
	 * @param obj el objeto a comparar con este tablero
	 * @return true si son iguales, false en caso contrario
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Tablero otroTablero = (Tablero) obj;
		return Arrays.deepEquals(celdas, otroTablero.celdas);
	}

	/**
	 * Genera un código hash para el tablero.
	 * 
	 * @return el código hash del tablero
	 */
	@Override
	public int hashCode() {
		return Arrays.deepHashCode(celdas);
	}

	/**
	 * Devuelve una representación en cadena del tablero.
	 * 
	 * @return una cadena que representa el estado del tablero
	 */
	@Override
	public String toString() {
		return aTexto();
	}

}