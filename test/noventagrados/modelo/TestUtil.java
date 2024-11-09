package noventagrados.modelo;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

/**
 * Clase de utilidad para la generación de datos para los tests parametrizados.
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0 20230607
 * 
 */
public class TestUtil {

	/** Constructor. */
	private TestUtil() {
		// hide implicit public constructor, only can be used with static class methods
	}

	/**
	 * Genera coordenadas correcta del tablero.
	 * 
	 * @return fila y columna correctas en el tablero
	 */
	static Stream<Arguments> proveerCoordenadas() {
		return Stream.of(
				Arguments.of(0, 0), Arguments.of(0, 1), Arguments.of(0, 2), Arguments.of(0, 3),
				Arguments.of(0, 4), Arguments.of(0, 5), Arguments.of(0, 6), 
				Arguments.of(1, 0), Arguments.of(1, 1), Arguments.of(1, 2), Arguments.of(1, 3), 
				Arguments.of(1, 4), Arguments.of(1, 5), Arguments.of(1, 6), 
				Arguments.of(2, 0),	Arguments.of(2, 1), Arguments.of(2, 2), Arguments.of(2, 3),
				Arguments.of(2, 4), Arguments.of(2, 5), Arguments.of(2, 6), 
				Arguments.of(3, 0), Arguments.of(3, 1),	Arguments.of(3, 2), Arguments.of(3, 3),
				Arguments.of(3, 4),	Arguments.of(3, 5), Arguments.of(3, 6),
				
				Arguments.of(4, 0), Arguments.of(4, 1), Arguments.of(4, 2), Arguments.of(4, 3),
				Arguments.of(4, 4), Arguments.of(4, 5), Arguments.of(4, 6), 
				Arguments.of(5, 0), Arguments.of(5, 1), Arguments.of(5, 2), Arguments.of(5, 3), 
				Arguments.of(5, 4), Arguments.of(5, 5), Arguments.of(5, 6), 
				Arguments.of(6, 0),	Arguments.of(6, 1), Arguments.of(6, 2), Arguments.of(6, 3),
				Arguments.of(6, 4), Arguments.of(6, 5), Arguments.of(6, 6)				
				);
	}

	/**
	 * Genera coordenadas incorrectas del tablero.
	 * 
	 * @return fila y columna incorrectas en el tablero
	 */
	static Stream<Arguments> proveerCoordenadasIncorrectas() {
		return Stream.of(Arguments.of(0, -1), Arguments.of(-1, 1), Arguments.of(-1, -1), Arguments.of(7, 7),
				Arguments.of(7, 0), Arguments.of(-1, -3), Arguments.of(-1, 2), Arguments.of(1, -3), Arguments.of(-2, 0),
				Arguments.of(2, -1), Arguments.of(7, 2), Arguments.of(2, 7), Arguments.of(-1, 3), Arguments.of(3, -1),
				Arguments.of(6, 7), Arguments.of(7, 6), Arguments.of(7, 7));
	}
	
	/**
	 * Genera coordenadas correctas de movimiento de piezas.
	 * 
	 * @return filaOrigen,columnaOrigen, filaOrigen, filaDestino
	 */
	static Stream<Arguments> proveerCoordenadasDeMovimiento() {
		return Stream.of(
				Arguments.of(0, 0, 0, 2), 
				Arguments.of(0, 1, 5, 1), 
				Arguments.of(0, 2, 6, 2), 
				Arguments.of(0, 3, 3, 3),
				Arguments.of(5, 0, 5, 6),
				Arguments.of(0, 2, 0, 4),
				Arguments.of(4, 3, 3, 3),
				Arguments.of(4, 6, 4, 0),
				Arguments.of(0, 5, 0, 6),
				Arguments.of(0, 0, 0, 5),
				Arguments.of(6, 5, 3, 5),
				Arguments.of(1, 1, 1, 4),
				Arguments.of(0, 2, 2, 2)
				);
	}
}
