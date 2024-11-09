package noventagrados.modelo;

/**
 * Describe una jugada en el juego. Una jugada consiste en un movimiento desde
 * una celda de origen a una celda de destino.
 * 
 * @author Víctor Vidal Vivanco
 * @author Guillermo López de Arechavaleta Zapatero
 * @version 1.0
 * @since 1.0
 */

/**
 * Constructor que crea una nueva instancia de una Jugada con las celdas de
 * origen y destino especificadas.
 *
 * @param origen  La celda de origen desde donde se realiza la jugada.
 * @param destino La celda de destino hacia donde se mueve.
 */
public record Jugada(Celda origen, Celda destino) {

	/**
	 * Convierte la jugada a una representación textual. El formato es "filaorigen
	 * columna origen-fila destino columna destino".
	 *
	 * @return Una cadena que representa la jugada en formato texto
	 */
	public String aTexto() {
		return String.format("%d%d-%d%d", origen.consultarCoordenada().fila(), origen.consultarCoordenada().columna(),
				destino.consultarCoordenada().fila(), destino.consultarCoordenada().columna());
	}

}