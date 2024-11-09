package noventagrados.modelo;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Timeout.ThreadMode.SEPARATE_THREAD;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import noventagrados.util.Coordenada;

/**
 * Tests sobre Jugada.
 * 
 * Al ser un tipo record se minimizan los tests.
 * 
 * @author <a href="rmartico@ubu.es">Raúl Marticorena</a>
 * @since 1.0
 * @version 1.0
 */
@DisplayName("Tests sobre Jugada.")
@Timeout(value = 1, unit = TimeUnit.SECONDS, threadMode = SEPARATE_THREAD) // Time out global para todos los tests salvo los de ciclo de vida
public class JugadaTest {

	/**
	 * Comprueba el texto correspondiente a una jugada.
	 * 
	 * @param filaOrigen fila origen
	 * @param columnaOrigen columna origen
	 * @param filaDestino fila destino
	 * @param columnaDestino columna destino
	 * @param texto texto a generar
	 */
	@DisplayName("Comprueba el texto correspondiente a la jugada.")
	@ParameterizedTest
	@CsvSource({ "1, 0, 0, 6, 10-06", "0, 1, 3, 6, 01-36", "2, 4, 2, 6, 24-26", 
			"6, 3, 3, 6, 63-36", "4, 2, 4, 6, 42-46",
			"5, 5, 5, 6, 55-56" })
	void comprobarATexto(int filaOrigen, int columnaOrigen, int filaDestino,
			int columnaDestino, String texto) {
		Jugada jugada = new Jugada(new Celda(new Coordenada(filaOrigen,columnaOrigen)),
				new Celda(new Coordenada(filaDestino,columnaDestino)));
		assertThat("El texto generado para la jugada es incorrecto.", jugada.aTexto(), is(texto));
	}
	
}
