package noventagrados.modelo;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import org.junit.jupiter.params.provider.MethodSource;

import noventagrados.util.Color;
import noventagrados.util.Coordenada;
import noventagrados.util.TipoPieza;

/**
 * Tests sobre el Tablero.
 * 
 * Se ordenan los tests agrupados por subclases (ver uso de anotación @Order).
 * El orden de ejecución sugiere el orden en el que se deberían ir implementando
 * los métodos.
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0 20230707
 * 
 */
@DisplayName("Tests sobre Tablero (depende de implementaciones reales de Pieza, Celda)")
@Timeout(value = 2, unit = TimeUnit.SECONDS, threadMode = SEPARATE_THREAD) // Time out global para todos los tests salvo los de ciclo de vida
@TestClassOrder(ClassOrderer.OrderAnnotation.class) // ordenamos la ejecución por @Order
public class TableroTest {

	/** Número de celdas. */
	private static final int TOTAL_CELDAS = 49;

	/** Tablero de testing. */
	private Tablero tablero;

	/** Inicializa valores para cada test. */
	@BeforeEach
	@Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
	void inicializar() {
		tablero = new Tablero();
	}

	/**
	 * Coloca nueve piezas en aspa sobre el tablero.
	 * 
	 * Método de utilida utilizado en varios tests.
	 * 
	 * <pre>
	 *  
	 * 	0 PN -- -- -- -- -- PN
	 *	1 -- PN -- -- -- PN --
	 *	2 -- -- -- -- -- -- --
	 *	3 -- -- -- RN -- -- --
	 *	4 -- -- -- -- -- -- --
	 *  5 -- PN -- -- -- PN --
	 *	6 PN -- -- -- -- -- PN
	 *    0 1 2 3 4 5 6
	 * </pre>
	 * 
	 */
	private void colocarNuevePiezasEnAspa() {
		tablero.colocar(new Pieza(TipoPieza.PEON, Color.NEGRO), new Coordenada(0, 0));
		tablero.colocar(new Pieza(TipoPieza.PEON, Color.NEGRO), new Coordenada(0, 6));
		tablero.colocar(new Pieza(TipoPieza.PEON, Color.NEGRO), new Coordenada(6, 0));
		tablero.colocar(new Pieza(TipoPieza.PEON, Color.NEGRO), new Coordenada(6, 6));
		tablero.colocar(new Pieza(TipoPieza.PEON, Color.NEGRO), new Coordenada(1, 1));
		tablero.colocar(new Pieza(TipoPieza.PEON, Color.NEGRO), new Coordenada(1, 5));
		tablero.colocar(new Pieza(TipoPieza.PEON, Color.NEGRO), new Coordenada(5, 1));
		tablero.colocar(new Pieza(TipoPieza.PEON, Color.NEGRO), new Coordenada(5, 5));
		tablero.colocar(new Pieza(TipoPieza.REINA, Color.NEGRO), new Coordenada(3, 3));
	}

	/**
	 * Estado inicial básico.
	 * 
	 * @see noventagrados.modelo.Tablero#consultarNumeroFilas()
	 * @see noventagrados.modelo.Tablero#consultarNumeroColumnas()
	 */
	@Nested
	@DisplayName("Tests de estado inicial básico.")
	@Order(1)
	class EstadoInicialBasico {

		/**
		 * Comprueba que el tablero se inicializa con el número de filas y columnas
		 * correcto.
		 */
		@Test
		@DisplayName("Comprueba estado inicial básico.")
		void comprobarEstadoInicial() {
			assertAll("estado inicial básico",
					() -> assertThat("Número de filas incorrecto.", tablero.consultarNumeroFilas(), is(7)),
					() -> assertThat("Número de columnas incorrecto.", tablero.consultarNumeroFilas(), is(7)));
		}
	} // EstadoInicialBasico

	/**
	 * Consulta de celdas.
	 * 
	 * @see noventagrados.modelo.Tablero#obtenerCelda(Coordenada)
	 * @see noventagrados.modelo.Tablero#consultarCelda(Coordenada)
	 */
	@Nested
	@DisplayName("Tests de consulta de celdas.")
	@Order(2)
	class ConsultaCeldas {

		/**
		 * Comprueba la obtención de celda en posiciones correctas del tablero vacío.
		 * 
		 * @param fila    fila
		 * @param columna columna
		 */
		@DisplayName("Comprueba la obtención de celda en coordenadass correctas del tablero")
		@ParameterizedTest
		@MethodSource("noventagrados.modelo.TestUtil#proveerCoordenadas")
		void comprobarObtenciónDeCeldaEnPosicionesCorrectas(int fila, int columna) {
			// given
			Coordenada coordenada = new Coordenada(fila, columna);
			// when
			// then
			assertAll("valores en la celda obtenida",
					() -> assertNotNull("La celda sí debería estar contenida en el tablero, no debe devolver un nulo",
							tablero.obtenerCelda(coordenada)),
					() -> assertThat("Las coordenadas no son correctas.",
							tablero.obtenerCelda(coordenada).consultarCoordenada(), is(coordenada)),
					() -> assertThat("La celda debería estar vacía.", tablero.obtenerCelda(coordenada).estaVacia(),
							is(true)));
		}

		/**
		 * Comprueba la consulta de celda en posiciones correctas del tablero vacío.
		 * 
		 * @param fila    fila
		 * @param columna columna
		 */
		@DisplayName("Comprueba la consulta de celda en coordenadass correctas del tablero")
		@ParameterizedTest
		@MethodSource("noventagrados.modelo.TestUtil#proveerCoordenadas")
		void comprobarConsultaDeCeldaEnPosicionesCorrectas(int fila, int columna) {
			// given
			Coordenada coordenada = new Coordenada(fila, columna);
			// when
			// then
			assertAll("valores en la celda obtenida",
					() -> assertNotNull("La celda sí debería estar contenida en el tablero, no debe devolver un nulo",
							tablero.consultarCelda(coordenada)),
					() -> assertThat("Las coordenadas no son correctas.",
							tablero.consultarCelda(coordenada).consultarCoordenada(), is(coordenada)),
					() -> assertThat("La celda debería estar vacía.", tablero.consultarCelda(coordenada).estaVacia(),
							is(true)));
		}

		/**
		 * Comprueba que la obtención y consulta de celda en posiciones correctas del
		 * tablero vacío devuelve celdas iguales en contenido pero con diferente
		 * identidad.
		 * 
		 * @param fila    fila
		 * @param columna columna
		 */
		@DisplayName("Comprueba que la obtención y consulta de celda en coordenadas correctas devuelve celdas iguales con diferente identidad")
		@ParameterizedTest
		@MethodSource("noventagrados.modelo.TestUtil#proveerCoordenadas")
		void comprobarObtencionYConsultaDeCeldaEnPosicionesCorrectasDevuelveCeldasIgualesEnContenidoYDiferenteIdentidad(
				int fila, int columna) {
			// given
			Coordenada coordenada = new Coordenada(fila, columna);
			// when
			// then
			assertAll("valores en la celda obtenida y consultada",
					() -> assertNotSame(tablero.obtenerCelda(coordenada), tablero.consultarCelda(coordenada),
							"La celda obtenida y consultada son el mismo objeto."),
					() -> assertThat("Las celdas deberían ser iguales en contenido.", tablero.obtenerCelda(coordenada),
							is(tablero.consultarCelda(coordenada))));
		}

		/**
		 * Comprueba que devuelve todas las celdas con independencia del orden.
		 */
		@DisplayName("Comprueba que la consulta de todas las celdas devuelve efectivamente todas (con independencia del orden)")
		@Test
		void comprobarConsultarCeldas() {
			Celda[] todas = tablero.consultarCeldas();
			int encontrada = 0;
			for (int i = 0; i < tablero.consultarNumeroFilas(); i++) {
				for (int j = 0; j < tablero.consultarNumeroColumnas(); j++) {
					Celda celda = tablero.consultarCelda(new Coordenada(i, j));
					for (Celda celdaAux : todas) {
						if (celdaAux.equals(celda)) {
							encontrada++;
							break;
						}
					}
				}
			}
			assertThat("No devuelve todas las celdas.", encontrada, is(TOTAL_CELDAS));
		}
	} // ConsultaCeldas

	/**
	 * Colocación y consulta del número de piezas del tablero.
	 * 
	 * @see noventagrados.modelo.Tablero#colocar(Pieza, Coordenada)
	 */
	@Nested
	@DisplayName("Tests de colocación de piezas en el tablero.")
	@Order(3)
	class ColocarPiezasEnTablero {

		/**
		 * Comprueba la colocación de una única pieza el tablero.
		 * 
		 * @param fila    fila
		 * @param columna columna
		 */
		@DisplayName("Coloca correctamente una pieza.")
		@ParameterizedTest
		@MethodSource("noventagrados.modelo.TestUtil#proveerCoordenadas")
		void comprobarColocarUnaPiezaEnElTablero(int fila, int columna) {
			// given tablero...
			Coordenada coordenada = new Coordenada(fila, columna);
			Pieza pieza = new Pieza(TipoPieza.PEON, Color.BLANCO);
			// no debería afectar en el test el tipo de pieza colocada ni su color...

			// when
			tablero.colocar(pieza, coordenada);

			// then
			Celda celda = tablero.obtenerCelda(coordenada);
			assertAll("comprobación de de pieza colocada",
					() -> assertThat("Pieza mal asignada.", celda.consultarPieza(), is(pieza)),
					() -> assertFalse("La celda está vacía", celda.estaVacia()));
		}
	} // ColocarPiezasEnTablero

	/**
	 * Eliminación de piezas.
	 * 
	 * @see noventagrados.modelo.Tablero#eliminarPieza(Coordenada)
	 */
	@Nested
	@DisplayName("Tests de eliminación de piezas.")
	@Order(4)
	class Eliminar {

		/**
		 * Comprueba la eliminaciónde una única pieza el tablero.
		 * 
		 * @param fila    fila
		 * @param columna columna
		 */
		@DisplayName("Elimina correctamente una pieza.")
		@ParameterizedTest
		@MethodSource("noventagrados.modelo.TestUtil#proveerCoordenadas")
		void comprobarEliminacionUnaPiezaEnElTablero(int fila, int columna) {
			// given tablero...
			tablero.colocar(new Pieza(TipoPieza.PEON, Color.NEGRO), new Coordenada(fila, columna)); // no debería
																									// afectar en el
			// test el tipo ni el color de la pieza
			// colocada...

			// when eliminamos la pieza recién colocada...
			tablero.eliminarPieza(new Coordenada(fila, columna));

			// then
			assertAll("la celda debería estar vacía tras eliminar la pieza", () -> assertThat("Debería estar vacía",
					tablero.consultarCelda(new Coordenada(fila, columna)).estaVacia(), is(true)));

		}
	} // Eliminar

	/**
	 * Clonación.
	 */
	@Nested
	@DisplayName("Tests de clonación.")
	@Order(5)
	class Clonacion {

		/** Texto de no coincidencia de tableros. */
		private static final String AMBOS_TABLEROS_NO_COINCIDEN_EN_CONTENIDO = "Ambos tableros no coinciden en contenido (revisar además generación de métodos equals y hashCode).";

		/**
		 * Comprueba que la clonación de un tablero vacío es correcta.
		 */
		@DisplayName("Comprueba la correcta clonación de un tablero vacío.")
		@Test
		void comprobarClonacionDeUnTableroVacio() {
			Tablero tableroClonado = tablero.clonar();
			assertAll("clonación de un tablero vacío",
					() -> assertNotSame(tableroClonado, tablero,
							"Ambas referencias apuntan al mismo objeto, no se ha clonado correctamente el tablero."),
					() -> assertEquals(tableroClonado, tablero,
							AMBOS_TABLEROS_NO_COINCIDEN_EN_CONTENIDO));
		}

		/**
		 * Comprueba la clonación de un tablero con algunas piezas colocadas.
		 * 
		 * @see noventagrados.modelo.TableroTest#colocarNuevePiezasEnAspa()
		 */
		@DisplayName("Comprueba la correcta clonación de un tablero con algunas piezas colocadas.")
		@Test
		void comprobarClonacionDeUnTableroConAlgunosPiezas() {
			// given tablero con nueves piezas colocadas en aspa...
			colocarNuevePiezasEnAspa();
			// when clonamos
			Tablero tableroClonado = tablero.clonar();
			assertAll("clonación de un tablero con varias piezas",
					() -> assertNotSame(tableroClonado, tablero,
							"Ambas referencias apuntan al mismo objeto, no se ha clonado correctamente el tablero."),
					() -> assertEquals(tableroClonado, tablero,
							AMBOS_TABLEROS_NO_COINCIDEN_EN_CONTENIDO));

		}

		/**
		 * Comprueba la clonación profunda de un tablero con algunas jugadas es
		 * correcta.
		 * 
		 * @see noventagrados.modelo.TableroTest#colocarNuevePiezasEnAspa()
		 */
		@DisplayName("Comprueba la correcta clonación profunda de un tablero con algunas piezas.")
		@Test
		void comprobarClonacionProfundaDeUnTableroConAlgunosPiezas() {
			// given tablero con nueve piezas colocadas en aspa...
			colocarNuevePiezasEnAspa();

			// when clonamos
			Tablero tableroClonado = tablero.clonar();
			assertAll("clonación de un tablero con varias piezas",
					() -> assertNotSame(tableroClonado, tablero,
							"Ambas referencias apuntan al mismo objeto, no se ha clonado."),
					() -> assertEquals(tableroClonado, tablero,
							AMBOS_TABLEROS_NO_COINCIDEN_EN_CONTENIDO));

			// Comprobar que las celdas dentro de cada tablero son a su vez clones...
			for (int i = 0; i < tablero.consultarNumeroFilas(); i++) {
				for (int j = 0; j < tablero.consultarNumeroColumnas(); j++) {
					Celda original = tablero.obtenerCelda(new Coordenada(i, j));
					Celda clonada = tableroClonado.obtenerCelda(new Coordenada(i, j));
					assertAll("clonación profunda de la celdas del tablero",
							() -> assertNotSame(clonada, original,
									"Ambas referencias apuntan a la misma celda, no se ha clonado."),
							() -> assertEquals(clonada, original,
									"Ambas celdas no coinciden en contenido (revisar además generación de métodos equals y hashCode)."));
				}
			}
		}
	} // Clonacion

	/**
	 * Validación de argumentos en todos aquellos métodos públicos que definen algún
	 * argumento formal en su signatura.
	 */
	@Nested
	@DisplayName("Validación de argumentos.")
	@Order(6)
	class ValidacionDeArgumentos {

		/**
		 * Comprobación de consultas básicas con argumentos no válidos.
		 * 
		 * @see noventagrados.modelo.Tablero#obtenerCelda(Coordenada)
		 * @see noventagrados.modelo.Tablero#consultarCelda(Coordenada)
		 */
		@Nested
		@DisplayName("Valida argumentos no válidos en consultas básicas.")
		class ConsultasBasicas {

			/**
			 * Comprueba que la obtención de celda en coordenadas incorrectas retorna nulo.
			 * 
			 * @param fila    fila
			 * @param columna columna
			 * @see noventagrados.modelo.Tablero#obtenerCelda(Coordenada)
			 */
			@DisplayName("Comprueba la obtención de celda en coordenadas incorrectas del tablero")
			@ParameterizedTest
			@MethodSource("noventagrados.modelo.TestUtil#proveerCoordenadasIncorrectas")
			void comprobarObtenerCeldasEnPosicionesIncorrectas(int fila, int columna) {
				// given
				Coordenada coordenada = new Coordenada(fila, columna);
				// when
				Celda celda = tablero.obtenerCelda(coordenada);
				// then
				assertNull(celda, "La celda obtenida debería valer nulo para coordenadas incorrectas.");
			}

			/**
			 * Comprueba que la consulta de celda en coordenadas incorrectas retorna nulo.
			 * 
			 * @param fila    fila
			 * @param columna columna
			 * @see noventagrados.modelo.Tablero#consultarCelda(Coordenada)
			 */
			@DisplayName("Comprueba la consulta de celda en coordenadas incorrectas del tablero")
			@ParameterizedTest
			@MethodSource("noventagrados.modelo.TestUtil#proveerCoordenadasIncorrectas")
			void comprobarConsultarCeldasEnPosicionesIncorrectas(int fila, int columna) {
				// given
				Coordenada coordenada = new Coordenada(fila, columna);
				// when
				Celda celda = tablero.consultarCelda(coordenada);
				// then
				assertNull(celda, "La celda consultada debería valer nulo para coordenadas incorrectas.");
			}
		}

		/**
		 * Comprobación de colocar con argumentos no válidos.
		 * 
		 * @see noventagrados.modelo.Tablero#colocar(Pieza, Coordenada)
		 */
		@Nested
		@DisplayName("Validación de argumentos no válidos al colocar.")
		class Colocar {

			/**
			 * Comprueba que se ignora colocar si se invoca con una pieza de valor nulo.
			 * 
			 * @param fila    fila
			 * @param columna columna
			 */
			@ParameterizedTest
			@MethodSource("noventagrados.modelo.TestUtil#proveerCoordenadas")
			@DisplayName("Comprueba que ignora colocar los valores nulos de pieza.")
			void comprobarIgnoraColocarConPiezaNula(int fila, int columna) {
				Coordenada coordenada = new Coordenada(fila, columna);
				Pieza pieza = new Pieza(TipoPieza.PEON, Color.BLANCO); // para el test no debería afectar el tipo ni
																		// color de pieza
				tablero.colocar(pieza, coordenada);
				assertThat("La celda no contiene la pieza añadida.", tablero.obtenerCelda(coordenada).consultarPieza(),
						is(pieza));
				tablero.colocar(null, coordenada);
				assertThat("Debería seguir estando la pieza añadida anteriormente.",
						tablero.obtenerCelda(coordenada).consultarPieza(), is(pieza));
			}

			/**
			 * Comprueba que se ignora colocar si se invoca con una coordenada nula.
			 * 
			 * @param fila    fila
			 * @param columna columna
			 */
			@ParameterizedTest
			@MethodSource("noventagrados.modelo.TestUtil#proveerCoordenadas")
			@DisplayName("Comprueba que ignora colocar con valores nulos de pieza.")
			void comprobarIgnoraColocarConCoordenadaNula(int fila, int columna) {
				Coordenada coordenada = new Coordenada(fila, columna);
				Pieza pieza = new Pieza(TipoPieza.REINA, Color.NEGRO); // para el test no debería afectar el tipo ni
																		// color de pieza
				tablero.colocar(pieza, coordenada);
				assertThat("La celda no contiene la pieza añadida.", tablero.obtenerCelda(coordenada).consultarPieza(),
						is(pieza));
				tablero.colocar(new Pieza(TipoPieza.REINA, Color.NEGRO), null);
				assertThat("Debería seguir estando la pieza añadida anteriormente.",
						tablero.obtenerCelda(coordenada).consultarPieza(), is(pieza));
			}

		}

		/**
		 * Comprobación de eliminar con argumentos no válidos.
		 * 
		 * @see noventagrados.modelo.Tablero#eliminarPieza(Coordenada)
		 */
		@Nested
		@DisplayName("Validación de argumentos no válidos al eliminar.")
		class Eliminar {

			/**
			 * Comprueba que la eliminación de piezas en el tablero no hace nada ante
			 * coordenadas nulas.
			 */
			@DisplayName("Comprueba que al eliminar con coordenada nula no tiene efecto.")
			@Test
			void comprobarQueEliminarConCoordenadaNulaNoTieneEfecto() {
				// given tablero...
				colocarNuevePiezasEnAspa();
				Tablero clon = tablero.clonar();
				// when intentamos eliminar con coordenada valor nulo
				tablero.eliminarPieza(null);
				// then el tablero no ha sufrido cambios y sigue siendo igual que el clon
				assertThat("El tablero original y el clonado deberían ser iguales.", tablero, is(clon));

			}

			/**
			 * Comprueba que la eliminación de piezas en el tablero no hace nada ante
			 * coordenadas incorrectas.
			 * 
			 * @param fila    fila
			 * @param columna columna
			 */
			@DisplayName("Comprueba que al eliminar con coordenada incorrecta no tiene efecto.")
			@ParameterizedTest
			@MethodSource("noventagrados.modelo.TestUtil#proveerCoordenadasIncorrectas")
			void comprobarQueEliminarConCoordenadaIncorrectaNoTieneEfecto(int fila, int columna) {
				// given tablero...
				colocarNuevePiezasEnAspa();
				Tablero clon = tablero.clonar();
				// when intentamos eliminar con coordenada valor nulo
				tablero.eliminarPieza(new Coordenada(fila, columna));
				// then el tablero no ha sufrido cambios y sigue siendo igual que el clon
				assertThat("El tablero original y el clonado deberían ser iguales.", tablero, is(clon));
			}
		}

	} // ValidacionDeArgumentos

	/**
	 * Tests de conversión a texto.
	 * 
	 * @see noventagrados.modelo.Tablero#aTexto()
	 */
	@Nested
	@DisplayName("Conversión a texto.")
	@Order(7)
	class ConversionATexto {

		/** Genera la cadena de texto correcta para un tablero vacío. */
		@DisplayName("Comprueba la generación de la cadena de texto con tablero vacío")
		@Test
		void comprobarCadenaTextoConTableroVacio() {
			String cadenaEsperada = """
					0 -- -- -- -- -- -- --
					1 -- -- -- -- -- -- --
					2 -- -- -- -- -- -- --
					3 -- -- -- -- -- -- --
					4 -- -- -- -- -- -- --
					5 -- -- -- -- -- -- --
					6 -- -- -- -- -- -- --
					  0  1  2  3  4  5  6""";
			cadenaEsperada = cadenaEsperada.replaceAll("\\s", "");
			// eliminamos espacios/tabuladores para simplificar la comparación
			String salida = tablero.aTexto().replaceAll("\\s", "");
			assertEquals(cadenaEsperada, salida, "La cadena de texto generada para un tablero vacío es incorecta.");
		}

		/**
		 * Genera la cadena de texto correcta para un tablero con algunos peones
		 * colocados en las esquinas del tablero.
		 */
		@DisplayName("Comprueba la generación de la cadena de texto con tablero con algunos peones en las esquinas")
		@Test
		void comprobarCadenaTextoConTableroConPeonesEnEsquinas() {
			String cadenaEsperada = """
					0 PB -- -- -- -- -- PN
					1 -- -- -- -- -- -- --
					2 -- -- -- -- -- -- --
					3 -- -- -- -- -- -- --
					4 -- -- -- -- -- -- --
					5 -- -- -- -- -- -- --
					6 PB -- -- -- -- -- PN
					  0  1  2  3  4  5  6""";
			cadenaEsperada = cadenaEsperada.replaceAll("\\s", "");
			tablero.colocar(new Pieza(TipoPieza.PEON, Color.BLANCO), new Coordenada(0, 0));
			tablero.colocar(new Pieza(TipoPieza.PEON, Color.NEGRO), new Coordenada(0, 6));
			tablero.colocar(new Pieza(TipoPieza.PEON, Color.BLANCO), new Coordenada(6, 0));
			tablero.colocar(new Pieza(TipoPieza.PEON, Color.NEGRO), new Coordenada(6, 6));
			// eliminamos espacios/tabuladores para simplificar la comparación
			String salida = tablero.aTexto().replaceAll("\\s", "");
			assertEquals(cadenaEsperada, salida,
					"La cadena de texto generada para un tablero con atacantes en las esquinas es incorrecta.");
		}

		/**
		 * Genera la cadena de texto correcta para un tablero con algunos atacantes,
		 * defensores y un rey.
		 */
		@DisplayName("Comprueba la generación de la cadena de texto con algunos atacantes, defensores y un rey.")
		@Test
		void comprobarCadenaTextoConTableroConPeonesYDamas() {
			String cadenaEsperada = """
					0 PB -- -- -- -- -- PB
					1 -- PN -- -- -- PN --
					2 -- -- -- -- -- -- --
					3 -- -- -- RB -- -- --
					4 -- -- -- -- -- -- --
					5 -- PN -- -- -- PN --
					6 PB -- -- -- -- -- PB
					  0  1  2  3  4  5  6""";
			cadenaEsperada = cadenaEsperada.replaceAll("\\s", "");
			tablero.colocar(new Pieza(TipoPieza.PEON, Color.BLANCO), new Coordenada(0, 0));
			tablero.colocar(new Pieza(TipoPieza.PEON, Color.BLANCO), new Coordenada(0, 6));
			tablero.colocar(new Pieza(TipoPieza.PEON, Color.BLANCO), new Coordenada(6, 0));
			tablero.colocar(new Pieza(TipoPieza.PEON, Color.BLANCO), new Coordenada(6, 6));

			tablero.colocar(new Pieza(TipoPieza.PEON, Color.NEGRO), new Coordenada(1, 1));
			tablero.colocar(new Pieza(TipoPieza.PEON, Color.NEGRO), new Coordenada(1, 5));
			tablero.colocar(new Pieza(TipoPieza.PEON, Color.NEGRO), new Coordenada(5, 1));
			tablero.colocar(new Pieza(TipoPieza.PEON, Color.NEGRO), new Coordenada(5, 5));

			tablero.colocar(new Pieza(TipoPieza.REINA, Color.BLANCO), new Coordenada(3, 3));

			// eliminamos espacios/tabuladores para simplificar la comparación
			String salida = tablero.aTexto().replaceAll("\\s", "");
			assertEquals(cadenaEsperada, salida,
					"La cadena de texto generada para un tablero con peones y reina en aspa es incorrecta.");
		}
	} // ConversionATexto

}
