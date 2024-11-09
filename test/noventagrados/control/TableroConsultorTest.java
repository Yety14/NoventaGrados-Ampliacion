package noventagrados.control;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Timeout.ThreadMode.SEPARATE_THREAD;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import noventagrados.modelo.Celda;
import noventagrados.modelo.Pieza;
import noventagrados.modelo.Tablero;
import noventagrados.util.Color;
import noventagrados.util.Coordenada;
import noventagrados.util.Sentido;
import noventagrados.util.TipoPieza;

/**
 * Tests sobre el consultor de tablero.
 * 
 * Se ordenan los tests agrupados por subclases (ver uso de anotación @Order).
 * El orden de ejecución sugiere el orden en el que se deberían ir implementando
 * los métodos.
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0 20240717
 * 
 */
@DisplayName("Tests sobre el TableroConsultor objeto que consulta el estado del tablero (depende de implementaciones reales de Tablero, Pieza y Celda)")
@Timeout(value = 2, unit = TimeUnit.SECONDS, threadMode = SEPARATE_THREAD) // Time out global para todos los tests salvo los de ciclo de vida
@TestClassOrder(ClassOrderer.OrderAnnotation.class) // ordenamos la ejecución por @Order
public class TableroConsultorTest {

	/** Tablero consultor de testing. */
	private TableroConsultor tableroConsultor;

	/** Inicializa valores para cada test. */
	@BeforeEach
	@Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
	void inicializar() {
		tableroConsultor = new TableroConsultor(new Tablero());
	}

	/**
	 * Estado inicial básico con un tablero vacío.
	 * 
	 */
	@Nested
	@DisplayName("Tests de estado inicial básico sin piezas en el tablero.")
	@Order(1)
	class EstadoInicialBasicoSinPiezas {

		/**
		 * Comprueba que todas las consultas de piezas devuelven cero sobre un tablero
		 * vacío.
		 */
		@DisplayName("Comprueba que todas las consultas devuelven cero sobre un tablero vacío")
		@Test
		void comprobarObtenciónDeNumeroDePiezasEsCeroSobreTableroVacío() {
			// given
			// when
			// then
			assertAll("no debería haber piezas a contar sobre el tablero vacío",
					() -> assertThat("El número de piezas peon blanco es incorrecto.",
							tableroConsultor.consultarNumeroPiezas(TipoPieza.PEON, Color.BLANCO), is(0)),
					() -> assertThat("El número de piezas peon negro es incorrecto.",
							tableroConsultor.consultarNumeroPiezas(TipoPieza.PEON, Color.NEGRO), is(0)),
					() -> assertThat("El número de reinas negras es incorrecto.",
							tableroConsultor.consultarNumeroPiezas(TipoPieza.REINA, Color.NEGRO), is(0)),
					() -> assertThat("El número de reinas blancas es incorrecto.",
							tableroConsultor.consultarNumeroPiezas(TipoPieza.REINA, Color.BLANCO), is(0)));

		}

		/**
		 * Comprueba que todas las consultas de número de piezas en horizontal y
		 * vertical devuelven cero sobre un tablero vacío.
		 * 
		 * @param fila    fila
		 * @param columna columna
		 */
		@DisplayName("Comprueba que todas las consultas de número de piezas en horizontal y vertical devuelven cero en un tablero vacío")
		@ParameterizedTest
		@MethodSource("noventagrados.modelo.TestUtil#proveerCoordenadas")
		void comprobarObtenciónDeNumeroPiezasEnHorizontalYVerticalEnTableroVacio(int fila, int columna) {
			// given
			Coordenada coordenada = new Coordenada(fila, columna);
			// when
			// then
			assertAll("valores ceros de numero de piezas en horizontal y vertical",
					() -> assertThat("El número de piezas en horizontal no es correcto.",
							tableroConsultor.consultarNumeroPiezasEnHorizontal(coordenada), is(0)),
					() -> assertThat("El número de piezas en vertical no es correcto.",
							tableroConsultor.consultarNumeroPiezasEnVertical(coordenada), is(0)));

		}

		/**
		 * Comprueba que la distancia de una celda consigo misma en horizontal es cero.
		 * 
		 * @param fila    fila
		 * @param columna columna
		 */
		@DisplayName("Comprueba que la distancia de una celda consigo mismo en horizontal es cero.")
		@ParameterizedTest
		@MethodSource("noventagrados.modelo.TestUtil#proveerCoordenadas")
		void comprobarDistanciaEnHorizontalDeUnaCeldaConsigoMisma(int fila, int columna) {
			// given
			Coordenada coordenada1 = new Coordenada(fila, columna);
			Coordenada coordenada2 = new Coordenada(fila, columna);
			// when
			// then
			assertThat("La distancia es incorrecta entre una celda y si misma en horizontal",
					tableroConsultor.consultarDistanciaEnHorizontal(coordenada1, coordenada2), is(0));

		}

		/**
		 * Comprueba que la distancia de una celda consigo misma en vertical es cero.
		 * 
		 * @param fila    fila
		 * @param columna columna
		 */
		@DisplayName("Comprueba que la distancia de una celda consigo mismo en vertical es cero.")
		@ParameterizedTest
		@MethodSource("noventagrados.modelo.TestUtil#proveerCoordenadas")
		void comprobarDistanciaEnVerticalDeUnaCeldaConsigoMisma(int fila, int columna) {
			// given
			Coordenada coordenada1 = new Coordenada(fila, columna);
			Coordenada coordenada2 = new Coordenada(fila, columna);
			// when
			// then
			assertThat("La distancia es incorrecta entre una celda y si misma en vertical",
					tableroConsultor.consultarDistanciaEnVertical(coordenada1, coordenada2), is(0));

		}

		/**
		 * Comprueba la distancia entre celdas en horizontal.
		 * 
		 * No debería afectar que las celdas estén vacías o no.
		 * 
		 * @param filaOrigen     fila origen
		 * @param columnaOrigen  columna origen
		 * @param filaDestino    fila destino
		 * @param columnaDestino columna destino
		 * @param distancia      distancia entre celdas
		 */
		@DisplayName("Comprueba la distancia en horizontal entre celdas.")
		@ParameterizedTest
		@CsvSource({ "0, 0, 0, 6, 6", "1, 1, 1, 6, 5", "2, 2, 2, 6, 4", "3, 3, 3, 6, 3", "4, 4, 4, 6, 2",
				"5, 5, 5, 6, 1" })
		void comprobarDistanciaEnHorizontalEntreCeldas(int filaOrigen, int columnaOrigen, int filaDestino,
				int columnaDestino, int distancia) {
			// given
			Coordenada coordenada1 = new Coordenada(filaOrigen, columnaOrigen);
			Coordenada coordenada2 = new Coordenada(filaDestino, columnaDestino);
			// when
			// then
			assertThat(
					"La distancia en horizontal es incorrecta entre celdas con coordenadas " + coordenada1 + " y "
							+ coordenada2 + ".",
					tableroConsultor.consultarDistanciaEnHorizontal(coordenada1, coordenada2), is(distancia));

		}

		/**
		 * Comprueba la distancia entre celdas en vertical.
		 * 
		 * No debería afectar que las celdas estén vacías o no.
		 * 
		 * @param filaOrigen     fila origen
		 * @param columnaOrigen  columna origen
		 * @param filaDestino    fila destino
		 * @param columnaDestino columna destino
		 * @param distancia      distancia entre celdas
		 */
		@DisplayName("Comprueba la distancia en vertical entre celdas.")
		@ParameterizedTest
		@CsvSource({ "0, 0, 6, 0, 6", "1, 1, 6, 1, 5", "2, 2, 6, 2, 4", "3, 3, 6, 3, 3", "4, 4, 6, 4, 2",
				"5, 5, 6, 5, 1" })
		void comprobarDistanciaEnVerticalEntreCeldas(int filaOrigen, int columnaOrigen, int filaDestino,
				int columnaDestino, int distancia) {
			// given
			Coordenada coordenada1 = new Coordenada(filaOrigen, columnaOrigen);
			Coordenada coordenada2 = new Coordenada(filaDestino, columnaDestino);
			// when
			// then
			assertThat(
					"La distancia en vertical es incorrecta entre celdas con coordenadas " + coordenada1 + " y "
							+ coordenada2 + ".",
					tableroConsultor.consultarDistanciaEnVertical(coordenada1, coordenada2), is(distancia));

		}

		/**
		 * Comprueba el sentido detectado entre dos coordenadas.
		 * 
		 * No debería afectar que las celdas estén vacías o no.
		 * 
		 * @param filaOrigen     fila origen
		 * @param columnaOrigen  columna origen
		 * @param filaDestino    fila destino
		 * @param columnaDestino columna destino
		 * @param sentidoTexto   sentido en formato texto
		 */
		@DisplayName("Comprueba el sentido detectado entre dos coordenadas.")
		@ParameterizedTest
		@CsvSource({ "0, 0, 0, 1, HORIZONTAL_E", "1, 1, 1, 3, HORIZONTAL_E", "2, 2, 2, 5, HORIZONTAL_E",
				"2, 2, 2, 6, HORIZONTAL_E", "0, 1, 0, 0, HORIZONTAL_O", "1, 3, 1, 1, HORIZONTAL_O",
				"2, 5, 2, 2, HORIZONTAL_O", "2, 6, 2, 2, HORIZONTAL_O", "0, 0, 1, 0, VERTICAL_S",
				"1, 1, 3, 1, VERTICAL_S", "2, 2, 4, 2, VERTICAL_S", "3, 3, 6, 3, VERTICAL_S", "1, 0, 0, 0, VERTICAL_N",
				"3, 1, 1, 1, VERTICAL_N", "4, 2, 2, 2, VERTICAL_N", "6, 3, 3, 3, VERTICAL_N", })
		void comprobarSentidoEntreCeldas(int filaOrigen, int columnaOrigen, int filaDestino, int columnaDestino,
				String sentidoTexto) {
			// given
			Coordenada coordenada1 = new Coordenada(filaOrigen, columnaOrigen);
			Coordenada coordenada2 = new Coordenada(filaDestino, columnaDestino);
			Sentido sentido = Sentido.valueOf(sentidoTexto);
			// when
			// then
			assertThat("El sentido detectado entre celdas con coordenadas " + coordenada1 + " y " + coordenada2 + ".",
					tableroConsultor.calcularSentido(coordenada1, coordenada2), is(sentido));

		}

		/**
		 * Comprueba que no está la reina en el centro en un tablero vacío.
		 * 
		 */
		@DisplayName("Comprueba que no está la reina en el centro en un tablero vacío.")
		@Test
		void comprobarQueNoHayReinaEnElCentroDeUnTableroVacío() {
			// given
			// when
			// then
			assertAll("sin reinas en el centro un tablero vacío",
					() -> assertThat("No debería haber reina blanca en el centro.",
							tableroConsultor.estaReinaEnElCentro(Color.BLANCO), is(false)),
					() -> assertThat("No debería haber reina negra en el centro.",
							tableroConsultor.estaReinaEnElCentro(Color.NEGRO), is(false)));

		}

		/**
		 * Comprueba que no hay reina en el tablero vacío.
		 * 
		 */
		@DisplayName("Comprueba que no hay reina en el tablero vacío.")
		@Test
		void comprobarQueNoHayReinaEnElTableroVacío() {
			// given
			// when
			// then
			assertAll("sin reinas en el tablero vacío",
					() -> assertThat("No debería haber reina blanca.", tableroConsultor.hayReina(Color.BLANCO),
							is(false)),
					() -> assertThat("No debería haber reina negra.", tableroConsultor.hayReina(Color.NEGRO),
							is(false)));

		}

	}

	/**
	 * Consultas sobre número de piezas colocadas en un tablero.
	 * 
	 */
	@Nested
	@DisplayName("Tests de consulta de número de piezas colocadas.")
	@Order(2)
	class ConsultaConPiezas {

		/** Texto es incorrecto sobre tablero. */
		private static final String ES_INCORRECTO_SOBRE_UN_TABLERO = " es incorrecto sobre un tablero\n";

		/**
		 * Coloca piezas consecutivas sobre el tablero.
		 * 
		 * Método de utilidad.
		 * 
		 * <pre>
		 *  
		 * 	0 RN -- -- -- -- -- --
		 *	1 PB PN -- -- -- -- --
		 *	2 PN PB PN -- -- -- --
		 *	3 PB PN PB PN -- -- --
		 *	4 PN PB PN PB PN -- --
		 *  5 PB PN PB PN PB PN --
		 *	6 PN PB PN PB PN RB RN
		 *    0 1 2 3 4 5 6
		 * </pre>
		 * 
		 * @param tableroConPiezas tablero con piezas 
		 */
		private void colocarPiezasConsecutivas(Tablero tableroConPiezas) {
			tableroConPiezas.colocar(new Pieza(TipoPieza.REINA, Color.NEGRO), new Coordenada(0, 0));

			tableroConPiezas.colocar(new Pieza(TipoPieza.PEON, Color.BLANCO), new Coordenada(1, 0));
			tableroConPiezas.colocar(new Pieza(TipoPieza.PEON, Color.NEGRO), new Coordenada(1, 1));

			tableroConPiezas.colocar(new Pieza(TipoPieza.PEON, Color.NEGRO), new Coordenada(2, 0));
			tableroConPiezas.colocar(new Pieza(TipoPieza.PEON, Color.BLANCO), new Coordenada(2, 1));
			tableroConPiezas.colocar(new Pieza(TipoPieza.PEON, Color.NEGRO), new Coordenada(2, 2));

			tableroConPiezas.colocar(new Pieza(TipoPieza.PEON, Color.BLANCO), new Coordenada(3, 0));
			tableroConPiezas.colocar(new Pieza(TipoPieza.PEON, Color.NEGRO), new Coordenada(3, 1));
			tableroConPiezas.colocar(new Pieza(TipoPieza.PEON, Color.BLANCO), new Coordenada(3, 2));
			tableroConPiezas.colocar(new Pieza(TipoPieza.PEON, Color.NEGRO), new Coordenada(3, 3));

			tableroConPiezas.colocar(new Pieza(TipoPieza.PEON, Color.NEGRO), new Coordenada(4, 0));
			tableroConPiezas.colocar(new Pieza(TipoPieza.PEON, Color.BLANCO), new Coordenada(4, 1));
			tableroConPiezas.colocar(new Pieza(TipoPieza.PEON, Color.NEGRO), new Coordenada(4, 2));
			tableroConPiezas.colocar(new Pieza(TipoPieza.PEON, Color.BLANCO), new Coordenada(4, 3));
			tableroConPiezas.colocar(new Pieza(TipoPieza.PEON, Color.NEGRO), new Coordenada(4, 4));

			tableroConPiezas.colocar(new Pieza(TipoPieza.PEON, Color.BLANCO), new Coordenada(5, 0));
			tableroConPiezas.colocar(new Pieza(TipoPieza.PEON, Color.NEGRO), new Coordenada(5, 1));
			tableroConPiezas.colocar(new Pieza(TipoPieza.PEON, Color.BLANCO), new Coordenada(5, 2));
			tableroConPiezas.colocar(new Pieza(TipoPieza.PEON, Color.NEGRO), new Coordenada(5, 3));
			tableroConPiezas.colocar(new Pieza(TipoPieza.PEON, Color.BLANCO), new Coordenada(5, 4));
			tableroConPiezas.colocar(new Pieza(TipoPieza.PEON, Color.NEGRO), new Coordenada(5, 5));

			tableroConPiezas.colocar(new Pieza(TipoPieza.PEON, Color.NEGRO), new Coordenada(6, 0));
			tableroConPiezas.colocar(new Pieza(TipoPieza.PEON, Color.BLANCO), new Coordenada(6, 1));
			tableroConPiezas.colocar(new Pieza(TipoPieza.PEON, Color.NEGRO), new Coordenada(6, 2));
			tableroConPiezas.colocar(new Pieza(TipoPieza.PEON, Color.BLANCO), new Coordenada(6, 3));
			tableroConPiezas.colocar(new Pieza(TipoPieza.PEON, Color.NEGRO), new Coordenada(6, 4));
			tableroConPiezas.colocar(new Pieza(TipoPieza.REINA, Color.BLANCO), new Coordenada(6, 5));
			tableroConPiezas.colocar(new Pieza(TipoPieza.PEON, Color.NEGRO), new Coordenada(6, 6));
		}

		/**
		 * Comprueba el número de piezas en horizontal a partir de una coordenada.
		 * 
		 * 
		 * @param filaOrigen     fila origen
		 * @param columnaOrigen  columna origen
		 * @param numeroPiezas   número de piezas en el sentido horizontal
		 */
		@DisplayName(" Comprueba el número de piezas en horizontal a partir de una coordenada.")
		@ParameterizedTest
		@CsvSource({ "0, 0, 1", "1, 1, 2", "2, 2, 3", "3, 3, 4", "4, 4, 5", "5, 5, 6", "6, 6, 7" })
		void comprobarNumeroPiezasEnHorizontal(int filaOrigen, int columnaOrigen, int numeroPiezas) {
			// given
			Tablero tableroConPiezas = new Tablero();
			colocarPiezasConsecutivas(tableroConPiezas);
			TableroConsultor tableroConsultorLocal = new TableroConsultor(tableroConPiezas);
			Coordenada coordenada1 = new Coordenada(filaOrigen, columnaOrigen);
			// when
			// then
			assertThat(
					"El numero de piezas en horizontal a partir de una coordenada " + coordenada1
							+ ES_INCORRECTO_SOBRE_UN_TABLERO + tableroConPiezas.aTexto(),
					tableroConsultorLocal.consultarNumeroPiezasEnHorizontal(coordenada1), is(numeroPiezas));

		}

		/**
		 * Comprueba el número de piezas en vertical a partir de una coordenada.
		 * 
		 * 
		 * @param filaOrigen     fila origen
		 * @param columnaOrigen  columna origen
		 * @param numeroPiezas   número de piezas en el sentido vertical
		 */
		@DisplayName(" Comprueba el número de piezas en vertical a partir de una coordenada.")
		@ParameterizedTest
		@CsvSource({ "0, 0, 7", "1, 1, 6", "2, 2, 5", "3, 3, 4", "4, 4, 3", "5, 5, 2", "6, 6, 1" })
		void comprobarNumeroPiezasEnVertical(int filaOrigen, int columnaOrigen, int numeroPiezas) {
			// given
			// given
			Tablero tableroConPiezas = new Tablero();
			colocarPiezasConsecutivas(tableroConPiezas);
			TableroConsultor tableroConsultorLocal = new TableroConsultor(tableroConPiezas);
			Coordenada coordenada1 = new Coordenada(filaOrigen, columnaOrigen);
			// when
			// then
			assertThat(
					"El numero de piezas en vertical a partir de una coordenada " + coordenada1
							+ ES_INCORRECTO_SOBRE_UN_TABLERO + tableroConPiezas.aTexto(),
					tableroConsultorLocal.consultarNumeroPiezasEnVertical(coordenada1), is(numeroPiezas));

		}

		/**
		 * Coloca piezas alternas sobre el tablero.
		 * 
		 * Método de utilidad.
		 * 
		 * <pre>
		 *  
		 * 	0 RN -- -- -- -- -- --
		 *	1 PB -- PN -- -- -- --
		 *	2 PN -- PB -- PN -- --
		 *	3 PB -- PN -- PB -- PN 
		 *	4 -- PN -- PB -- PN --
		 *  5 -- -- PB -- PN -- PB 
		 *	6 -- -- -- PN -- RB --
		 *    0 1 2 3 4 5 6
		 * </pre>
		 * 
		 * @param tableroConPiezas tablero con piezas 
		 */
		private void colocarPiezasAlternas(Tablero tableroConPiezas) {
			tableroConPiezas.colocar(new Pieza(TipoPieza.REINA, Color.NEGRO), new Coordenada(0, 0));

			tableroConPiezas.colocar(new Pieza(TipoPieza.PEON, Color.BLANCO), new Coordenada(1, 0));
			tableroConPiezas.colocar(new Pieza(TipoPieza.PEON, Color.NEGRO), new Coordenada(1, 2));

			tableroConPiezas.colocar(new Pieza(TipoPieza.PEON, Color.NEGRO), new Coordenada(2, 0));
			tableroConPiezas.colocar(new Pieza(TipoPieza.PEON, Color.BLANCO), new Coordenada(2, 2));
			tableroConPiezas.colocar(new Pieza(TipoPieza.PEON, Color.NEGRO), new Coordenada(2, 4));

			tableroConPiezas.colocar(new Pieza(TipoPieza.PEON, Color.BLANCO), new Coordenada(3, 0));
			tableroConPiezas.colocar(new Pieza(TipoPieza.PEON, Color.NEGRO), new Coordenada(3, 2));
			tableroConPiezas.colocar(new Pieza(TipoPieza.PEON, Color.BLANCO), new Coordenada(3, 4));
			tableroConPiezas.colocar(new Pieza(TipoPieza.PEON, Color.NEGRO), new Coordenada(3, 6));

			tableroConPiezas.colocar(new Pieza(TipoPieza.PEON, Color.NEGRO), new Coordenada(4, 1));
			tableroConPiezas.colocar(new Pieza(TipoPieza.PEON, Color.BLANCO), new Coordenada(4, 3));
			tableroConPiezas.colocar(new Pieza(TipoPieza.PEON, Color.NEGRO), new Coordenada(4, 5));

			tableroConPiezas.colocar(new Pieza(TipoPieza.PEON, Color.BLANCO), new Coordenada(5, 2));
			tableroConPiezas.colocar(new Pieza(TipoPieza.PEON, Color.NEGRO), new Coordenada(5, 4));
			tableroConPiezas.colocar(new Pieza(TipoPieza.PEON, Color.BLANCO), new Coordenada(5, 6));

			tableroConPiezas.colocar(new Pieza(TipoPieza.PEON, Color.NEGRO), new Coordenada(6, 3));
			tableroConPiezas.colocar(new Pieza(TipoPieza.REINA, Color.BLANCO), new Coordenada(6, 5));
		}

		/**
		 * Comprueba el número de piezas en horizontal a partir de una coordenada con
		 * huecos.
		 * 
		 * 
		 * @param filaOrigen     fila origen
		 * @param columnaOrigen  columna origen
		 * @param numeroPiezas   número de piezas en el sentido horizontal
		 */
		@DisplayName(" Comprueba el número de piezas en horizontal a partir de una coordenada con huecos.")
		@ParameterizedTest
		@CsvSource({ "0, 0, 1", "1, 1, 2", "2, 4, 3", "3, 6, 4", "4, 5, 3", "5, 6, 3", "6, 5, 2" })
		void comprobarNumeroPiezasEnHorizontalConHuecos(int filaOrigen, int columnaOrigen, int numeroPiezas) {
			// given
			Tablero tableroConPiezas = new Tablero();
			colocarPiezasAlternas(tableroConPiezas);
			TableroConsultor tableroConsultorLocal = new TableroConsultor(tableroConPiezas);
			Coordenada coordenada1 = new Coordenada(filaOrigen, columnaOrigen);
			// when
			// then
			assertThat(
					"El numero de piezas en horizontal a partir de una coordenada " + coordenada1
							+ ES_INCORRECTO_SOBRE_UN_TABLERO + tableroConPiezas.aTexto(),
					tableroConsultorLocal.consultarNumeroPiezasEnHorizontal(coordenada1), is(numeroPiezas));

		}

		/**
		 * Comprueba el número de piezas en vertical a partir de una coordenada con
		 * huecos.
		 * 
		 * 
		 * @param filaOrigen     fila origen
		 * @param columnaOrigen  columna origen
		 * @param numeroPiezas   número de piezas en el sentido vertical
		 */
		@DisplayName(" Comprueba el número de piezas en vertical a partir de una coordenada con huecos.")
		@ParameterizedTest
		@CsvSource({ "0, 0, 4", "1, 1, 1", "2, 4, 3", "3, 6, 2", "4, 5, 2", "5, 6, 2", "6, 6, 2" })
		void comprobarNumeroPiezasEnVerticalConHuecos(int filaOrigen, int columnaOrigen, int numeroPiezas) {
			// given
			Tablero tableroConPiezas = new Tablero();
			colocarPiezasAlternas(tableroConPiezas);
			TableroConsultor tableroConsultorLocal = new TableroConsultor(tableroConPiezas);
			Coordenada coordenada1 = new Coordenada(filaOrigen, columnaOrigen);
			// when
			// then
			assertThat(
					"El numero de piezas en vertical a partir de una coordenada " + coordenada1
							+ ES_INCORRECTO_SOBRE_UN_TABLERO + tableroConPiezas.aTexto(),
					tableroConsultorLocal.consultarNumeroPiezasEnVertical(coordenada1), is(numeroPiezas));

		}

		/**
		 * Comprueba el número de piezas de cada tipo sobre el tablero.
		 * 
		 */
		@DisplayName(" Comprueba el número de piezas de cada tipo sobre el tablero.")
		@Test
		void comprobarNumeroPiezasDeCadaTipoSobreElTablero() {
			// given
			Tablero tableroConPiezas = new Tablero();
			colocarPiezasAlternas(tableroConPiezas);
			TableroConsultor tableroConsultorLocal = new TableroConsultor(tableroConPiezas);
			// when
			// then
			assertAll("reina blanca en el centro y la negra no",
					() -> assertThat(
							"El numero de piezas de tipo peon negro es incorrecto sobre un tablero\n"
									+ tableroConPiezas.aTexto(),
							tableroConsultorLocal.consultarNumeroPiezas(TipoPieza.PEON, Color.NEGRO), is(9)),
					() -> assertThat(
							"El numero de piezas de tipo peon blanco es incorrecto sobre un tablero\n"
									+ tableroConPiezas.aTexto(),
							tableroConsultorLocal.consultarNumeroPiezas(TipoPieza.PEON, Color.BLANCO), is(7)),
					() -> assertThat(
							"El numero de piezas de tipo reina negra es incorrecto sobre un tablero\n"
									+ tableroConPiezas.aTexto(),
							tableroConsultorLocal.consultarNumeroPiezas(TipoPieza.REINA, Color.NEGRO), is(1)),
					() -> assertThat(
							"El numero de piezas de tipo reina blanca es incorrecto sobre un tablero\n"
									+ tableroConPiezas.aTexto(),
							tableroConsultorLocal.consultarNumeroPiezas(TipoPieza.REINA, Color.BLANCO), is(1)));
		}

		/**
		 * Rellena el tablero de distintos tipos de piezas hasta ver que está completo.
		 */
		@DisplayName("Comprueba el rellenado del tablero de piezas hasta completarlo")
		@Test
		void comprobarRellenadoDelTableroConPiezas() {			
			// En cada iteración lo completamos con tipos de piezas distintas
			for (TipoPieza tipoPieza : TipoPieza.values()) {
				// y de distintos colores
				for (Color color : Color.values()) {
					final Tablero tableroLocal = new Tablero();
					final int TOTAL_CELDAS = tableroLocal.consultarNumeroFilas() * tableroLocal.consultarNumeroColumnas();
					final TableroConsultor tableroConsultorLocal = new TableroConsultor(tableroLocal);					
					// Rellenamos el tablero de instancias del mismo tipo de pieza y color
					for (int fila = 0; fila < tableroLocal.consultarNumeroFilas(); fila++) {
						for (int columna = 0; columna < tableroLocal.consultarNumeroColumnas(); columna++) {
							Coordenada coordenada = new Coordenada(fila, columna);
							Pieza pieza = new Pieza(tipoPieza, color);
							tableroLocal.colocar(pieza, coordenada);
							Celda celda = tableroLocal.consultarCelda(coordenada);
							// comprobaciones locales al bucle (redundantes si tablero funciona correctamente)
							assertThat("Pieza mal asignada.", celda.consultarPieza(), is(pieza));
							assertFalse("La celda está vacía", celda.estaVacia());
						}
					}
					assertThat("Número de piezas incorrecto para tipo de pieza " + tipoPieza + " de color"
							+ color
							+ " en un tablero relleno:\n" +
							tableroLocal.aTexto(),
							tableroConsultorLocal.consultarNumeroPiezas(tipoPieza, color), is(TOTAL_CELDAS));
				}
			}
		}

	}

	/**
	 * Consultas sobre reinas colocadas en un tablero.
	 * 
	 */
	@Nested
	@DisplayName("Tests de consulta de reinas.")
	@Order(2)
	class ConsultaConReinas {

		/**
		 * Coloca dos reinas, la blanca en el centro y la negra fuera del centro
		 * 
		 * Método de utilidad.
		 * 
		 * <pre>
		 *  
		 * 	0 -- -- -- -- -- -- --
		 *	1 -- -- -- -- -- -- --
		 *	2 -- -- -- -- -- -- --
		 *	3 -- -- -- RB -- -- --
		 *	4 -- -- -- -- -- -- --
		 *  5 -- -- RN -- -- -- --
		 *	6 -- -- -- -- -- -- --
		 *    0  1  2  3  4  5  6
		 * </pre>
		 * 
		 * @param tableroConPiezas tablero con piezas 
		 */
		private void colocarReinaBlancaEnCentro(Tablero tableroConPiezas) {
			tableroConPiezas.colocar(new Pieza(TipoPieza.REINA, Color.BLANCO), new Coordenada(3, 3));
			tableroConPiezas.colocar(new Pieza(TipoPieza.REINA, Color.NEGRO), new Coordenada(5, 2));
		}

		/**
		 * Coloca dos reinas, la negra en el centro y la blanca fuera del centro
		 * 
		 * Método de utilidad.
		 * 
		 * <pre>
		 *  
		 * 	0 -- -- -- -- -- -- --
		 *	1 -- -- -- -- -- RB --
		 *	2 -- -- -- -- -- -- --
		 *	3 -- -- -- RN -- -- --
		 *	4 -- -- -- -- -- -- --
		 *  5 -- -- -- -- -- -- --
		 *	6 -- -- -- -- -- -- --
		 *    0  1  2  3  4  5  6
		 * </pre>
		 * 
 		 * @param tableroConPiezas tablero con piezas 
		 */
		private void colocarReinaNegraEnCentro(Tablero tableroConPiezas) {
			tableroConPiezas.colocar(new Pieza(TipoPieza.REINA, Color.NEGRO), new Coordenada(3, 3));
			tableroConPiezas.colocar(new Pieza(TipoPieza.REINA, Color.BLANCO), new Coordenada(1, 5));
		}

		/**
		 * Comprueba que la reina blanca está en el centro.
		 * 
		 */
		@DisplayName(" Comprueba que la reina blanca está en el centro y la negra no.")
		@Test
		void comprobarReinaBlancaEnElCentro() {
			// given
			Tablero tableroConReinaBlancaEnElCentro = new Tablero();
			colocarReinaBlancaEnCentro(tableroConReinaBlancaEnElCentro);
			TableroConsultor tableroConsultorLocal = new TableroConsultor(tableroConReinaBlancaEnElCentro);
			// when
			// then
			assertAll("reina blanca en el centro y la negra no",
					() -> assertThat("No encuentra reina blanca", tableroConsultorLocal.hayReina(Color.BLANCO), is(true)),
					() -> assertThat("No encuentra reina negra", tableroConsultorLocal.hayReina(Color.NEGRO), is(true)),
					() -> assertThat(
							"La reina blanca no está en el centro en un tablero \n"
									+ tableroConReinaBlancaEnElCentro.aTexto(),
							tableroConsultorLocal.estaReinaEnElCentro(Color.BLANCO), is(true)),
					() -> assertThat(
							"La reina negra está en el centro en un tablero \n"
									+ tableroConReinaBlancaEnElCentro.aTexto(),
							tableroConsultorLocal.estaReinaEnElCentro(Color.NEGRO), is(false)));
		}

		/**
		 * Comprueba que la reina negra está en el centro.
		 * 
		 */
		@DisplayName(" Comprueba que la reina negra está en el centro y la blanca no.")
		@Test
		void comprobarReinaNegraEnElCentro() {
			// given
			Tablero tableroConReinaNegraEnElCentro = new Tablero();
			colocarReinaNegraEnCentro(tableroConReinaNegraEnElCentro);
			TableroConsultor tableroConsultorLocal = new TableroConsultor(tableroConReinaNegraEnElCentro);
			// when
			// then
			assertAll("reina negra en el centro y la blanca no",
					() -> assertThat("No encuentra reina blanca", tableroConsultorLocal.hayReina(Color.BLANCO), is(true)),
					() -> assertThat("No encuentra reina negra", tableroConsultorLocal.hayReina(Color.NEGRO), is(true)),
					() -> assertThat(
							"La reina negra no está en el centro en un tablero \n"
									+ tableroConReinaNegraEnElCentro.aTexto(),
							tableroConsultorLocal.estaReinaEnElCentro(Color.NEGRO), is(true)),
					() -> assertThat(
							"La reina blanca está en el centro en un tablero \n"
									+ tableroConReinaNegraEnElCentro.aTexto(),
							tableroConsultorLocal.estaReinaEnElCentro(Color.BLANCO), is(false)));
		}
	}

}
