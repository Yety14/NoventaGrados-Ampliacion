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
 * Tests sobre el color.
 * 
 * La enumeracion es un elemento básico que debería ser implementado y probado 
 * en primer lugar, antes de proseguir con el resto de clases.
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0 20220803
 * 
 */
@DisplayName("Tests sobre Color (sin dependencias de otras clases).")
@Timeout(value = 1, unit = TimeUnit.SECONDS, threadMode = SEPARATE_THREAD) // Time out global para todos los tests salvo los de ciclo de vida
public class ColorTest {
	
	/**
	 * Comprueba el correcto número de valores.
	 */
	@DisplayName("Comprueba el número de valores definidos.")
	@Test
	public void probarNumeroValores() {
		assertThat("La enumeración Color debe tener exactamente dos valores definidos.", Color.values().length, is(2));
	}
		
	/**
	 * Comprueba los textos para cada color.
	 */
	@DisplayName("Comprueba retorno de textos para cada valor.")
	@Test
	public void probarTextos() {
		assertAll("comprobando textos correctos para cada valor del tipo enumerado ",
			() -> assertThat("Texto mal definido para BLANCO.", 
					Color.BLANCO.toChar(), is('B')),
			
			() -> assertThat("Texto mal definido para NEGRO.",
					Color.NEGRO.toChar(), is('N'))

			);			
	} 
	
	/**
	 * Comprueba el retorno del color contrario.
	 */
	@DisplayName("Comprueba el retorno del color contrario.")
	@Test
	void probarRetornoDelContrario( ) {
		assertAll("comprobando retorno del color contrario.",
			() -> assertThat("Debería retornar el color contrario.", Color.BLANCO.consultarContrario(), is(Color.NEGRO)),
			() -> assertThat("Debería retornar el color contrario.", Color.NEGRO.consultarContrario(), is(Color.BLANCO))
				);
		
	}	
}
