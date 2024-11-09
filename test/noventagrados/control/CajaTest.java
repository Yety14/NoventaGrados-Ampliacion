package noventagrados.control;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Timeout.ThreadMode.SEPARATE_THREAD;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import noventagrados.modelo.Pieza;
import noventagrados.util.Color;
import noventagrados.util.TipoPieza;

/**
 * Tests sobre la caja.
 * 
 * @author <a href="rmartico@ubu.es">Raúl Marticorena</a>
 * @since 1.0
 * @version 1.0
 */
@DisplayName("Tests sobre Caja (depende de las implementaciones reales de Pieza, TipoPieza y Color")
@Tag("IntegrationTest")
@Timeout(value = 2, unit = TimeUnit.SECONDS, threadMode = SEPARATE_THREAD) // Time out global para todos los tests salvo los de ciclo de vida
public class CajaTest {
	
	/** Texto de número de peones incorrectos. */
	private static final String NÚMERO_DE_PEONES_INCORRECTO = "Número de peones incorrecto";
	
	/** Texto de número incorrecto de piezas iniciales en caja. */
	private static final String INCORRECTO_NÚMERO_DE_PIEZAS_INICIALES_EN_LA_CAJA = "Incorrecto número de piezas iniciales en la caja.";
	
	/** Texto de no están todas las piezas en la caja. */
	private static final String NO_ESTÁN_TODAS_LAS_PIEZAS_EN_LA_CAJA = "No están todas las piezas en la caja.";
	
	/** Texto de número de reinas incorrecto. */
	
	private static final String NÚMERO_DE_REINAS_INCORRECTO = "Número de reinas incorrecto";
	/** Texto de color mal inicializado en caja.*/
	private static final String COLOR_MAL_INICIALIZADO_EN_LA_CAJA = "Color mal inicializado en la caja.";

	/**
	 * Comprueba la inicialización correcta de la caja inicialmente vacía.
	 * 
	 * @param color color
	 */
	@ParameterizedTest
	@EnumSource(Color.class)
	@DisplayName("Comprobar inicializaciones.")
	void comprobarInicializacion(Color color) {
		Caja cajaLocal = new Caja(color);
		assertAll("comprobar estado inicial de la caja con color y vacía inicialmente",
				() -> assertThat(NÚMERO_DE_PEONES_INCORRECTO, cajaLocal.contarPiezas(TipoPieza.PEON), is(0)),
				() -> assertThat(NÚMERO_DE_REINAS_INCORRECTO, cajaLocal.contarPiezas(TipoPieza.REINA), is(0)),
				() -> assertThat(COLOR_MAL_INICIALIZADO_EN_LA_CAJA, cajaLocal.consultarColor(), is(color)),
				() -> assertThat(INCORRECTO_NÚMERO_DE_PIEZAS_INICIALES_EN_LA_CAJA,
						cajaLocal.consultarPiezas().length, is(0)));
	}
	
	/**
	 * Comprueba que se llena la caja con peones.
	 * 
	 * @param color color
	 */
	@EnumSource(Color.class)
	@DisplayName("Comprobar que se llena la caja con siete peones.")
	@ParameterizedTest
	void comprobarLlenarCaja(Color color) {
		Caja cajaLocal = new Caja(color);
		for (int i = 0; i < 7; i++) {
			cajaLocal.añadir(new Pieza(TipoPieza.PEON, color));
		}
		assertAll("comprobar estado inicial de la caja con siete peones",
				() -> assertThat(COLOR_MAL_INICIALIZADO_EN_LA_CAJA, cajaLocal.consultarColor(), is(color)),
				() -> assertThat(INCORRECTO_NÚMERO_DE_PIEZAS_INICIALES_EN_LA_CAJA,
						cajaLocal.consultarPiezas().length, is(7)),
				() -> assertThat(NÚMERO_DE_PEONES_INCORRECTO, cajaLocal.contarPiezas(TipoPieza.PEON), is(7)),
				() -> assertThat(NÚMERO_DE_REINAS_INCORRECTO, cajaLocal.contarPiezas(TipoPieza.REINA), is(0)),
				() -> assertThat(NO_ESTÁN_TODAS_LAS_PIEZAS_EN_LA_CAJA, Arrays.asList(cajaLocal.consultarPiezas()),
						containsInAnyOrder(
								new Pieza(TipoPieza.PEON, color),
								new Pieza(TipoPieza.PEON, color),
								new Pieza(TipoPieza.PEON, color),
								new Pieza(TipoPieza.PEON, color),
								new Pieza(TipoPieza.PEON, color),
								new Pieza(TipoPieza.PEON, color),
								new Pieza(TipoPieza.PEON, color)))
				);
	}
	

	/**
	 * Comprueba que no deja añadir piezas de color contrario al de la caja.
	 * 
	 * @param color color
	 */
	@EnumSource(Color.class)
	@DisplayName("Comprobar que no deja añadir piezas de color contrario al de la caja.")
	@ParameterizedTest
	void comprobarNoAñadePiezasDeColorContrarioAlDeLaCaja(Color color) {
		Caja cajaLocal = new Caja(color);
		Color colorContrario = color == Color.NEGRO ? Color.BLANCO : Color.NEGRO;
		for (int i = 0; i < 7; i++) {
			cajaLocal.añadir(new Pieza(TipoPieza.PEON, colorContrario));
		}
		assertAll("comprobar estado inicial de la caja vací",
				() -> assertThat(COLOR_MAL_INICIALIZADO_EN_LA_CAJA, cajaLocal.consultarColor(), is(color)),
				() -> assertThat(INCORRECTO_NÚMERO_DE_PIEZAS_INICIALES_EN_LA_CAJA,
						cajaLocal.consultarPiezas().length, is(0)),
				() -> assertThat(NÚMERO_DE_PEONES_INCORRECTO, cajaLocal.contarPiezas(TipoPieza.PEON), is(0)),
				() -> assertThat(NÚMERO_DE_REINAS_INCORRECTO, cajaLocal.contarPiezas(TipoPieza.REINA), is(0))
				);
	}
	
	/**
	 * Comprueba que se añaden a la caja tres peones y una reina.
	 * 
	 * @param color color
	 */
	@EnumSource(Color.class)
	@DisplayName("Comprobar que se añaden a la caja tres peones y una reina.")
	@ParameterizedTest
	void comprobarLlenarCajaConTresPeonesYUnaReina(Color color) {
		Caja cajaLocal = new Caja(color);
		for (int i = 0; i < 3; i++) {
			cajaLocal.añadir(new Pieza(TipoPieza.PEON, color));
		}
		cajaLocal.añadir(new Pieza(TipoPieza.REINA, color));
		assertAll("comprobar estado inicial de la caja con cuatro piezas",
				() -> assertThat(COLOR_MAL_INICIALIZADO_EN_LA_CAJA, cajaLocal.consultarColor(), is(color)),
				() -> assertThat(INCORRECTO_NÚMERO_DE_PIEZAS_INICIALES_EN_LA_CAJA,
						cajaLocal.consultarPiezas().length, is(4)),
				() -> assertThat(NÚMERO_DE_PEONES_INCORRECTO, cajaLocal.contarPiezas(TipoPieza.PEON), is(3)),
				() -> assertThat(NÚMERO_DE_REINAS_INCORRECTO, cajaLocal.contarPiezas(TipoPieza.REINA), is(1)),
				() -> assertThat(NO_ESTÁN_TODAS_LAS_PIEZAS_EN_LA_CAJA, Arrays.asList(cajaLocal.consultarPiezas()),
						containsInAnyOrder(
								new Pieza(TipoPieza.PEON, color),
								new Pieza(TipoPieza.PEON, color),
								new Pieza(TipoPieza.PEON, color),
								new Pieza(TipoPieza.REINA, color)))
				);
	}
	
	/**
	 * Comprueba que una vez llena la caja con siete peones no tiene efecto añadir una reina.
	 * 
	 * @param color color
	 */
	@EnumSource(Color.class)
	@DisplayName("Comprobar que una vez llena la caja con siete peones no tiene efecto añadir una reina.")
	@ParameterizedTest
	void comprobarLlenarCajaIntentandoAñadirUnaPiezaAdicional(Color color) {
		Caja cajaLocal = new Caja(color);
		for (int i = 0; i < 7; i++) {
			cajaLocal.añadir(new Pieza(TipoPieza.PEON, color));
		}
		// añadimos una reina que no cabe y debería ser ignorada esta petición
		cajaLocal.añadir(new Pieza(TipoPieza.REINA, color));
		assertAll("comprobar estado inicial de la caja solo con siete peones",
				() -> assertThat(COLOR_MAL_INICIALIZADO_EN_LA_CAJA, cajaLocal.consultarColor(), is(color)),
				() -> assertThat(INCORRECTO_NÚMERO_DE_PIEZAS_INICIALES_EN_LA_CAJA,
						cajaLocal.consultarPiezas().length, is(7)),
				() -> assertThat(NÚMERO_DE_PEONES_INCORRECTO, cajaLocal.contarPiezas(TipoPieza.PEON), is(7)),
				() -> assertThat(NÚMERO_DE_REINAS_INCORRECTO, cajaLocal.contarPiezas(TipoPieza.REINA), is(0)),

				() -> assertThat(NO_ESTÁN_TODAS_LAS_PIEZAS_EN_LA_CAJA, Arrays.asList(cajaLocal.consultarPiezas()),
						containsInAnyOrder(
								new Pieza(TipoPieza.PEON, color),
								new Pieza(TipoPieza.PEON, color),
								new Pieza(TipoPieza.PEON, color),
								new Pieza(TipoPieza.PEON, color),
								new Pieza(TipoPieza.PEON, color),
								new Pieza(TipoPieza.PEON, color),
								new Pieza(TipoPieza.PEON, color)))
				);
	}
	
	/**
	 * Comprueba que la clonación de una caja vaíca recién inicializada es correcta.
	 * 
	 * @param color color
	 */
	@ParameterizedTest
	@EnumSource(Color.class)
	@DisplayName("Comprobar la clonación de una caja recién inicializada.")
	void comprobarClonacionConCajaInicialmenteVacía(Color color) {
		Caja cajaLocal = new Caja(color);
		Caja cajaClon = cajaLocal.clonar();
		Pieza[] disponiblesEnCajaLocal = cajaLocal.consultarPiezas();
		Pieza[] disponiblesEnCajaClon = cajaClon.consultarPiezas();
		assertAll("comprobando clonación de caja vacia",
			() -> assertNotSame(cajaLocal, cajaClon, "No deberían tener la misma referencia el original y el clon"),
			() -> assertThat("El contenido de las cajas debería ser equivalente", cajaLocal, is(cajaClon)),
			() -> assertThat("El número de piezas disponibles en el clon es incorrecto.", disponiblesEnCajaClon.length,is(0)),
			() -> assertNotSame(disponiblesEnCajaLocal, disponiblesEnCajaClon, "No deberían tener la misma referencia."),
			() -> assertArrayEquals(disponiblesEnCajaLocal, disponiblesEnCajaClon,"Los arrays de piezas disponibles no son iguales en contenido.")
			);
	}
	
	/**
	 * Comprueba la clonación de una caja llena de peones y una reina.
	 * 
	 * @param color color
	 */
	@ParameterizedTest
	@EnumSource(Color.class)
	@DisplayName("Comprobar la clonación de una caja llena de peones y una reina.")
	void comprobarClonacionConCajaLlenaDePeonesYUnaReina(Color color) {
		Caja cajaLocal = new Caja(color);
		for (int i = 0; i < 6; i++) {
			cajaLocal.añadir(new Pieza(TipoPieza.PEON, color));
		}
		cajaLocal.añadir(new Pieza(TipoPieza.REINA, color));
		
		Caja cajaClon = cajaLocal.clonar();
		Pieza[] disponiblesEnCajaLocal = cajaLocal.consultarPiezas();
		Pieza[] disponiblesEnCajaClon = cajaClon.consultarPiezas();
		assertAll("comprobando clonación de caja llena",
			() -> assertNotSame(cajaLocal, cajaClon, "No deberían tener la misma referencia el original y el clon"),
			() -> assertThat("El contenido de las cajas debería ser equivalente", cajaLocal, is(cajaClon)),
			() -> assertThat("El número de piezas disponibles en el clon es incorrecto.", disponiblesEnCajaClon.length,is(7)),
			() -> assertNotSame(disponiblesEnCajaLocal, disponiblesEnCajaClon, "No deberían tener la misma referencia."),
			() -> assertArrayEquals(disponiblesEnCajaLocal, disponiblesEnCajaClon,"Los arrays de piezas disponibles no son iguales en contenido.")
			);
		// comprobando adicionalmente la clonación en profundidad de las piezas
		for(int i = 0; i < disponiblesEnCajaLocal.length; i++) {
			assertNotSame(disponiblesEnCajaLocal[i], disponiblesEnCajaClon[i], "No se han clonado en profundidad las piezas disponibles en la caja.");
		}
	}

}

