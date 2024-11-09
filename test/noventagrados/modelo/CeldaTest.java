package noventagrados.modelo;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Timeout.ThreadMode.SEPARATE_THREAD;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import noventagrados.util.Color;
import noventagrados.util.Coordenada;
import noventagrados.util.TipoPieza;

/**
 * Tests sobre la celda.
 * 
 * @author <a href="rmartico@ubu.es">Raúl Marticorena</a>
 * @since 1.0
 * @version 1.0
 * @see noventagrados.util.Coordenada
 * @see noventagrados.modelo.Pieza
 * @see noventagrados.util.TipoPieza
 */
@DisplayName("Tests sobre Celda (depende en compilación de Pieza).")
@Timeout(value = 1, unit = TimeUnit.SECONDS, threadMode = SEPARATE_THREAD) // Time out global para todos los tests salvo los de ciclo de vida
public class CeldaTest {

	/**
	 * Tamaño de prueba.
	 */
	private static final int TAMAÑO = 7;

	/**
	 * Test del constructor.
	 */
	@DisplayName("Constructor con estado inicial correcto")
	@Test
	void constructor() {
		for (int fila = 0; fila < TAMAÑO; fila++) {
			final int filaAux = fila;
			for (int columna = 0; columna < TAMAÑO; columna++) {
				final int columnaAux = columna;
				Celda celda = new Celda(new Coordenada(filaAux, columnaAux));
				assertAll("estado de la celda",
						() -> assertThat("Coordenadas mal inicializadas.", celda.consultarCoordenada(),
								is(new Coordenada(filaAux, columnaAux))),
						() -> assertTrue("Inicialmente no está vacía.", celda.estaVacia()),
						() -> assertNull("Tiene pieza cuando su pieza asignada tras instanciar debería valer null.",
								celda.consultarPieza()));
			}
		}
	}

	/**
	 * Comprueba la colocación en celda.
	 */
	@DisplayName("Coloca una pieza en una celda")
	@Test
	void colocarEnCelda() {
		Pieza pieza = new Pieza(TipoPieza.PEON, Color.BLANCO);
		Celda celda = new Celda(new Coordenada(0, 0));
		celda.colocar(pieza);
		assertThat("Pieza mal colocada (comprobar además que se han generado métodos equals y hashCode).",
				celda.consultarPieza(), is(pieza));
	}

	/** Elimina pieza en celda ocupada. */
	@DisplayName("Elimina una pieza en una celda ocupada")
	@Test
	void eliminarPieza() {
		Celda celdaDestino = new Celda(new Coordenada(0, 0));
		assertTrue("La celda no está vacía antes de colocar.", celdaDestino.estaVacia());
		celdaDestino.colocar(new Pieza(TipoPieza.REINA, Color.NEGRO));
		assertFalse("La celda está vacía después de colocar.", celdaDestino.estaVacia());
		celdaDestino.eliminarPieza();
		assertAll("eliminar pieza",
				() -> assertNull("No debería contener una pieza la celda destino una vez eliminada.",
						celdaDestino.consultarPieza()),
				() -> assertTrue("La celda no está vacía una vez eliminada.", celdaDestino.estaVacia()));
	}

	/** Obtiene el color de celdas con piezas. */
	@DisplayName("Obtiene el color de la pieza que ocupa la celda")
	@Test
	void obtenerColorDePiezaEnCeldasOcupadas() {
		Celda celda = new Celda(new Coordenada(0, 0));
		for (TipoPieza tipoPieza : TipoPieza.values()) { // por cada tipo de pieza
 			for (Color color : Color.values()) { // por cada color
				celda.colocar(new Pieza(tipoPieza, color));
				assertThat("Color incorrecto.", celda.consultarColorDePieza(), is(color));
			}
		}
	}

	/** Obtiene color de celdas vacías. */
	@DisplayName("Obtiene color nulo de la pieza de una celda vacía")
	@Test
	void obtenerColorDePiezaEnCeldasVacias() {
		Celda celda = new Celda(new Coordenada(0, 0));
		assertNull("Color incorrecto.", celda.consultarColorDePieza());
	}

	/**
	 * Comprueba que la clonación de una celda vacía es correcta.
	 * 
	 * @param fila    fila
	 * @param columna columna
	 */
	@ParameterizedTest
	@MethodSource("noventagrados.modelo.TestUtil#proveerCoordenadas")
	@DisplayName("Comprueba que la clonación de una celda vacía es correcta.")
	void comprobarClonacionDeCeldasVacias(int fila, int columna) {
		// given
		Celda celda = new Celda(new Coordenada(fila, columna));
		// when
		Celda clon = celda.clonar();
		// then
		assertAll("comprueba la clonación de celdas vacías",
				() -> assertNotSame(celda, clon,
						"Ambas referencias apuntan al mismo objeto, no se ha clonado correctamente"),
				() -> assertThat(
						"Los contenidos son iguales superficialmente (revisar además la generación de métodos equals y hashCode).",
						clon, is(celda)),
				() -> assertThat("Las coordenadas del clon son incorrectas.", clon.consultarCoordenada(),
						is(celda.consultarCoordenada())),
				() -> assertThat("El clon debería estar vacío.", clon.estaVacia(), is(true)),
				() -> assertThat("No debería haber una pieza colocada en el clon.", clon.consultarPieza(),
						is(nullValue())));
	}

	/**
	 * Comprueba que la clonación de una celda con pieza es correcta.
	 * 
	 * @param fila    fila
	 * @param columna columna
	 */
	@ParameterizedTest
	@MethodSource("noventagrados.modelo.TestUtil#proveerCoordenadas")
	@DisplayName("Comprueba que la clonación de una celda con pieza es correcta.")
	void comprobarClonacionDeCeldasConPieza(int fila, int columna) {
		// given
		Celda celda = new Celda(new Coordenada(fila, columna));
		Pieza piezaLocal = new Pieza(TipoPieza.PEON, Color.BLANCO);
		// when
		celda.colocar(piezaLocal);
		Celda clon = celda.clonar();
		// then
		assertAll("comprueba la clonación con celdas ocupadas",
				() -> assertNotSame(celda, clon,
						"Ambas referencias apuntan al mismo objeto celda, no se ha clonado correctamente"),
				() -> assertThat(
						"Los contenidos son iguales superficialmente (revisar además implementación de equals y hashCode).",
						clon, is(celda)),
				() -> assertThat("La fila del clon es incorrecta.", clon.consultarCoordenada(),
						is(celda.consultarCoordenada())),
				() -> assertThat("El clon no debería estar vacío.", clon.estaVacia(), is(false)),
				() -> assertNotSame(celda.consultarPieza(), clon.consultarPieza(),
						"Ambas referencias apuntan al mismo objeto pieza, no se ha clonado correctamente."),
				() -> assertThat(
						"Debería haber una pieza equivalente en contenido colocada en el clon (revisar implemetaciones de equals y hashCode).",
						clon.consultarPieza(), is(piezaLocal)));
	}
}
