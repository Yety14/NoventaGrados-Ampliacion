package noventagrados.util;

/**
 * Una coordenada del tablero.
 *
 * @author Víctor Vidal Vivanco
 * @author Guillermo Guillermo López de Arechavaleta Zapatero
 * @version 1.0
 * @since 1.0
 */

public record Coordenada(int fila, int columna) {
	
	/**
     * Devuelve la representación de la coordenada como un String en formato "filacolumna".
     * 
     * @return un String que representa la coordenada.
     */
    public String aTexto() {
        return String.format("%d%d", fila, columna);
    }
}

