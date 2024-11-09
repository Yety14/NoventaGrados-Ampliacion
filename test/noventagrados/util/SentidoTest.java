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
 * Tests sobre el sentido
 * 
 * La enumeracion es un elemento básico que debería ser implementado y probado 
 * en primer lugar, antes de proseguir con el resto de clases.
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0 20220803
 * 
 */
@DisplayName("Tests sobre Sentido (sin dependencias de otras clases).")
@Timeout(value = 1, unit = TimeUnit.SECONDS, threadMode = SEPARATE_THREAD) // Time out global para todos los tests salvo los de ciclo de vida
public class SentidoTest {
	
	/**
	 * Correctos desplazamientos para cada sentido.
	 */
	@DisplayName("Comprueba desplazamientos para cada sentido.")
	@Test
	public void probarDesplazamientos() {
		assertAll("letras correctas",
			() -> assertThat("Desplazamiento en filas en horizontal este mal definido.", 
					Sentido.HORIZONTAL_E.consultarDesplazamientoEnFilas(), is(0)),
			
			() -> assertThat("Desplazamiento en columnas en horizontal este mal definido.", 
					Sentido.HORIZONTAL_E.consultarDesplazamientoEnColumnas(), is(+1)),

			() -> assertThat("Desplazamiento en filas en horizontal oeste mal definido.", 
					Sentido.HORIZONTAL_O.consultarDesplazamientoEnFilas(), is(0)),
			
			() -> assertThat("Desplazamiento en columnas en horizontal oeste mal definido.",
					Sentido.HORIZONTAL_O.consultarDesplazamientoEnColumnas(), is(-1)),
			
			() -> assertThat("Desplazamiento en filas en vertical norte mal definido.", 
					Sentido.VERTICAL_N.consultarDesplazamientoEnFilas(), is(-1)),
			
			() -> assertThat("Desplazamiento en columnas en vertical norte mal definido.", 
					Sentido.VERTICAL_N.consultarDesplazamientoEnColumnas(), is(0)),
			
			() -> assertThat("Desplazamiento en filas en vertical sur  mal definido.", 
					Sentido.VERTICAL_S.consultarDesplazamientoEnFilas(), is(+1)),
			
			() -> assertThat("Desplazamiento en columnas en vertical sur mal definido.", 
					Sentido.VERTICAL_S.consultarDesplazamientoEnColumnas(), is(0))

			);			
	} 
}

