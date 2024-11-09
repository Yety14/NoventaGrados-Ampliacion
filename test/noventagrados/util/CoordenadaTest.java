package noventagrados.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Timeout.ThreadMode.SEPARATE_THREAD;


import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/**
 * Tests sobre coordenada.
 * 
 * Dado que se genera con un tipo record la realización es casi redundante.
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0 20220803
 * 
 */
@DisplayName("Tests sobre Coordenada (sin dependencias de otras clases).")
@Timeout(value = 1, unit = TimeUnit.SECONDS, threadMode = SEPARATE_THREAD) // Time out global para todos los tests salvo los de ciclo de vida
public class CoordenadaTest {
	
	/** Texto de valor de fila incorrecto. */
	private static final String EL_VALOR_DE_LA_FILA_PARA_LA_COORDENADA_ES_INCORRECTO = "El valor de la fila para la coordenada es incorrecto.";

	/** Texto de valor de coordenada incorrecto. */
	private static final String EL_VALOR_DE_LA_COLUMNA_PARA_LA_COORDENADA_ES_INCORRECTO = "El valor de la columna para la coordenada es incorrecto.";
	
	/** Texto generado  para la coordenada es incorrecto. */
	private static final String EL_TEXTO_GENERADO_PARA_LA_COORDENADA_ES_INCORRECTO = "El texto generado para la coordenada es incorrecto.";
	
	/** Coordenada. */
	private Coordenada coordenada00;
	/** Coordenada. */
	private Coordenada coordenada23;
	/** Coordenada. */
	private Coordenada coordenada66;

	/**
	 * Inicialización.
	 */
	@BeforeEach
	void inicializar() {
		coordenada00 = new Coordenada(0, 0);
		coordenada23 = new Coordenada(2, 3);
		coordenada66 = new Coordenada(6, 6);
	}

	/**
	 * Comprueba la correcta inicialización de fila.
	 */
	@DisplayName("Comprueba la inicialización de fila.")
	@Test
	public void probarInicializacionDeFila() {
		assertAll("comprobando la correcta inicialización de fila ",
				() -> assertThat(EL_VALOR_DE_LA_FILA_PARA_LA_COORDENADA_ES_INCORRECTO, coordenada00.fila(), is(0)),
				() -> assertThat(EL_VALOR_DE_LA_FILA_PARA_LA_COORDENADA_ES_INCORRECTO, coordenada23.fila(), is(2)),
				() -> assertThat(EL_VALOR_DE_LA_FILA_PARA_LA_COORDENADA_ES_INCORRECTO, coordenada66.fila(), is(6)));
	}

	/**
	 * Comprueba la correcta inicialización de columna.
	 */
	@DisplayName("Comprueba la inicialización de columna.")
	@Test
	public void probarInicializacionDeColumna() {
		assertAll("comprobando la correcta inicialización de columna ",
				() -> assertThat(EL_VALOR_DE_LA_COLUMNA_PARA_LA_COORDENADA_ES_INCORRECTO, coordenada00.columna(),
						is(0)),
				() -> assertThat(EL_VALOR_DE_LA_COLUMNA_PARA_LA_COORDENADA_ES_INCORRECTO, coordenada23.columna(),
						is(3)),
				() -> assertThat(EL_VALOR_DE_LA_COLUMNA_PARA_LA_COORDENADA_ES_INCORRECTO, coordenada66.columna(),
						is(6)));
	}

	/**
	 * Comprueba la correcta generación de texto.
	 */
	@DisplayName("Comprueba la generación de texto.")
	@Test
	public void probarGeneracionDeTexto() {
		assertAll("comprobando la correcta generación de texto para distintas coordenadas",
				() -> assertThat(EL_TEXTO_GENERADO_PARA_LA_COORDENADA_ES_INCORRECTO, coordenada00.aTexto(),
						is("00")),
				() -> assertThat(EL_TEXTO_GENERADO_PARA_LA_COORDENADA_ES_INCORRECTO, coordenada23.aTexto(),
						is("23")),
				() -> assertThat(EL_TEXTO_GENERADO_PARA_LA_COORDENADA_ES_INCORRECTO, coordenada66.aTexto(), is("66"))

		);
	}
}
