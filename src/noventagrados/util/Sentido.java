package noventagrados.util;

/**
 * Enumeración que representa los sentidos de movimiento en el tablero. Cada
 * sentido tiene un desplazamiento asociado en filas y columnas.
 *
 * @author Víctor Vidal Vivanco
 * @author Guillermo López de Arechavaleta Zapatero
 * @version 1.0
 * @since 1.0
 */

public enum Sentido {

	/** Sentido vertical hacia el norte (arriba). */
	VERTICAL_N(-1, 0),

	/** Sentido vertical hacia el sur (abajo). */
	VERTICAL_S(1, 0),

	/** Sentido horizontal hacia el este (derecha). */
	HORIZONTAL_E(0, 1),

	/** Sentido horizontal hacia el oeste (izquierda). */
	HORIZONTAL_O(0, -1);

	/** El desplazamiento en filas. Negativo para norte, positivo para sur. */
	private int despFila;

	/** El desplazamiento en columnas. Positivo para este, negativo para oeste. */
	private int despColumna;

	/**
	 * Constructor privado para la enumeración Sentido.
	 *
	 * @param despFila    el desplazamiento en filas (-1, 0, o 1)
	 * @param despColumna el desplazamiento en columnas (-1, 0, o 1)
	 */
	private Sentido(int despFila, int despColumna) {
		this.despFila = despFila;
		this.despColumna = despColumna;
	}

	/**
	 * Consulta el desplazamiento en filas para este sentido.
	 *
	 * @return el desplazamiento en filas (-1 para norte, 1 para sur, 0 para
	 *         este/oeste)
	 */
	public int consultarDesplazamientoEnFilas() {
		return despFila;
	}

	/**
	 * Consulta el desplazamiento en columnas para este sentido.
	 *
	 * @return el desplazamiento en columnas (1 para este, -1 para oeste, 0 para
	 *         norte/sur)
	 */
	public int consultarDesplazamientoEnColumnas() {
		return despColumna;
	}
}
