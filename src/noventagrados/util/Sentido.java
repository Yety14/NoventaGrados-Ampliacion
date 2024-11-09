package noventagrados.util;

/**
*
* @author Víctor Vidal Vivanco
* @author Guillermo Guillermo López de Arechavaleta Zapatero
* @version 1.0
* @since 1.0
* 
*/

public enum Sentido {
	
	/** Sentido vertical hacia el norte. */
	VERTICAL_N(-1,0), 
	/** Sentido vertical hacia el sur. */
	VERTICAL_S(1,0), 
	/** Sentido horizontal hacia el este. */
	HORIZONTAL_E(0,1), 
	/** Sentido horizontal hacia el oeste. */
	HORIZONTAL_O(0,-1);
	
	/** El desplazamiento en filas. */
	private int despFila;
	
	/** El desplazameiento en columnas. */
	private int despColumna;
	
	/**
	 * Instancia un nuevo sentido.
	 *
	 * @param despFila el desplazamiento de la fila
	 * @param despColumna el desplazamiento de la columna
	 */
	private Sentido(int despFila, int despColumna) {
		this.despFila = despFila;
		this.despColumna = despColumna;
	}
	
	/**
	 * Consultar desplazamiento en filas.
	 *
	 * @return el desplazamiento en filas
	 */
	public int consultarDesplazamientoEnFilas() {
		return despFila;
	}
	
	/**
	 * Consultar desplazamiento en columnas.
	 *
	 * @return el desplazamiento en columnas
	 */
	public int consultarDesplazamientoEnColumnas() {
		return despColumna;
	}
}
