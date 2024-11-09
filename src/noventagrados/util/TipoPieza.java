package noventagrados.util;

import noventagrados.util.TipoPieza;

/**
*
* @author Víctor Vidal Vivanco
* @author Guillermo Guillermo López de Arechavaleta Zapatero
* @version 1.0
* @since 1.0
*/


public enum TipoPieza {
	
	/** El defensor. */
	PEON('P'),  
	/** El reina. */
	REINA('R');
		
	/** Caracter que guarda la letra de la pieza. */
	private char caracter;
	
	/**
	 * Instancia un nuevo tipo pieza.
	 *
	 * @param caracter de la pieza
	 * @param color de la pieza
	 */
	private TipoPieza(char caracter) {
		this.caracter = caracter;
	}

	
	/**
	 * To char.
	 *
	 * @return el caracter de la pieza
	 */
	public char toChar() {
		return caracter;
	}
}