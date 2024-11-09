package noventagrados.util;

/**
 * Representa una coordenada en el tablero del juego. Una coordenada consiste en
 * una fila y una columna.
 *
 * @author Víctor Vidal Vivanco
 * @author Guillermo López de Arechavaleta Zapatero
 * @version 1.0
 * @since 1.0
 */

/**
 * Constructor que crea una nueva instancia de una Coordenada con los valores
 * especificados para fila y columna.
 *
 * @param fila    La fila de la coordenada.
 * @param columna La columna de la coordenada.
 */
public record Coordenada(int fila, int columna) {

	/**
	 * Devuelve la representación de la coordenada como un String en formato
	 * "filacolumna".
	 * 
	 * @return Un String que representa la coordenada
	 */
	public String aTexto() {
		return String.format("%d%d", fila, columna);
	}
}
