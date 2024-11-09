package noventagrados.modelo;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Timeout.ThreadMode.SEPARATE_THREAD;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import noventagrados.util.Color;
import noventagrados.util.TipoPieza;


/**
 * Tests sobre la pieza. 
 * 
 * Depende en compilación de Coordenada, TipoPieza y Color (tipo record y enumerados).
 * 
 * El uso de la biblioteca mockito-inline-4.6.1.jar permitiría "mockear"
 * tipos enumerados pero dados los problemas que genera con OpenJDK 17 en 
 * GNU/Linux se trabaja directamente con el tipo enumerado. 
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0 20230706
 * 
 */
@DisplayName("Tests sobre Pieza (depende de Coordenada, TipoPieza y Color).")
@Timeout(value = 1, unit = TimeUnit.SECONDS, threadMode = SEPARATE_THREAD) // Time out global para todos los tests salvo los de ciclo de vida
public class PiezaTest {

	/**
	 * Test del constructor con peon negro.
	 */
	@DisplayName("Constructor con estado inicial correcto para un peon")
	@Test
	void constructorPeon() {
		Pieza pieza = new Pieza(TipoPieza.PEON, Color.NEGRO);
		assertAll("peon mal inicializado",
				() -> assertThat("Color mal inicializado.", pieza.consultarColor(), is(Color.NEGRO)),
				() -> assertThat("Tipo mal inicializado.", pieza.consultarTipoPieza(), is(TipoPieza.PEON)));
	}
	
	/**
	 * Test del constructor con reina blanca.
	 */
	@DisplayName("Constructor con estado inicial correcto para una reina")
	@Test
	void constructorReina() {
		Pieza pieza = new Pieza(TipoPieza.REINA, Color.BLANCO);
		assertAll("reina mal inicializada",
				() -> assertThat("Color mal inicializado.", pieza.consultarColor(), is(Color.BLANCO)),
				() -> assertThat("Tipo mal inicializado.", pieza.consultarTipoPieza(), is(TipoPieza.REINA)));
	}
	
		
	/**
	 * Comprueba que la clonación de una pieza es correcta.
	 * 
	 * @param tipoPieza tipo de pieza
	 */
	@DisplayName("Comprobar que el clon generado de una pieza es correcto.")
	@ParameterizedTest
	@MethodSource("proveerTipoPieza")
	void comprobarClonacion(TipoPieza tipoPieza) {
		// given
		Pieza pieza = new Pieza(tipoPieza, Color.BLANCO);
		// when
		Pieza clon = pieza.clonar();
		// then
		assertAll("comprobando estado del clon",
				() -> assertNotSame(pieza, clon, "No deberían ser iguales las referencias."),
				() -> assertThat("El contenido del clon y la pieza original debería ser igual (revisar además que se han añadido métodos auxiliares equals y hashCode).", clon, is(pieza))
			);		
	}
	
	// Métodos de utilidad para los tests...
	
	/**
	 * Provee de todas las combinaciones de tipo de pieza.
	 * 
	 * @return todas las combinaciones de figura y color
	 */
	private static Stream<Arguments> proveerTipoPieza() {
		return Stream.of(
				arguments(TipoPieza.PEON),
				arguments(TipoPieza.REINA)
				);
	}	
}
