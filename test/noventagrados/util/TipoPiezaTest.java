package noventagrados.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Timeout.ThreadMode.SEPARATE_THREAD;


import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/**
 * Tests sobre el tipo de las piezas.
 * 
 * La enumeracion es un elemento básico que debería ser implementado 
 * y probado en primer lugar antes de proseguir con el resto de clases.
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0 20230706
 * @see noventagrados.util.Color
 * 
 */
@DisplayName("Tests sobre Tipos de Pieza (sin dependencia de otras clases).")
@Timeout(value = 1, unit = TimeUnit.SECONDS, threadMode = SEPARATE_THREAD) // Time out global para todos los tests salvo los de ciclo de vida
public class TipoPiezaTest {
	
	/**
	 * Comprobar el correcto número de valores.
	 */
	@DisplayName("Comprueba el número de valores definidos.")
	@Test
	public void probarNumeroValores() {
		assertThat("La enumeración debe tener exactamente DOS valores definidos (no importa el orden).", TipoPieza.values().length, is(2));
	}
		
	/**
	 * Comprueba correctos textos para cada tipo de pieza.
	 */
	@DisplayName("Comprueba los textos literales para cada valor.")
	@Test
	public void probarTextos() {
		assertAll("comprobando textos correctos para cada valor del tipo enumerado ",
			() -> assertThat("Texto mal definido para un atacante.", 
					TipoPieza.REINA.toChar(), is('R')),
			
			() -> assertThat("Texto mal definido para un defensor.",
					TipoPieza.PEON.toChar(), is('P'))

			);			
	}	
}
