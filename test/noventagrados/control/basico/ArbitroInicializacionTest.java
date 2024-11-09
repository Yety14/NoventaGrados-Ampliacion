package noventagrados.control.basico;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Timeout.ThreadMode.SEPARATE_THREAD;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.ClassOrderer; // para ordenar la ejecución de las clases anidadas
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import noventagrados.control.Arbitro;
import noventagrados.control.TableroConsultor;
import noventagrados.modelo.Celda;
import noventagrados.modelo.Pieza;
import noventagrados.modelo.Tablero;
import noventagrados.util.Color;
import noventagrados.util.Coordenada;
import noventagrados.util.TipoPieza;

/**
 * Comprobación de inicialización de la partida
 * colocando las piezas sobre el tablero.
 * 
 * @author <a href="rmartico@ubu.es">Raúl Marticorena</a>
 * @since 1.0
 * @version 1.0
 */
@DisplayName("Tests del Arbitro sobre la inicialización de piezas.")
@Timeout(value = 2, unit = TimeUnit.SECONDS, threadMode = SEPARATE_THREAD) // Time out global para todos los tests salvo los de ciclo de vida
@TestClassOrder(ClassOrderer.OrderAnnotation.class) // ordenamos la ejecución por @Order
public class ArbitroInicializacionTest {

	/** Árbitro de testing. */
	private Arbitro arbitro;

	/** Tablero de testing. */
	private Tablero tablero;
	
	/** Texto de número de jugadas cero. */
	private static final String EL_NÚMERO_DE_JUGADAS_DEBERÍA_SER_CERO = "El número de jugadas debería ser cero";

	/** Generación del árbitro para testing. */
	@BeforeEach
	void inicializar() {
		// Inyección de tablero para testing...
		tablero = new Tablero();
		arbitro = new Arbitro(tablero);
	}

	/**
	 * Comprobación del estado inicial del árbitro sin piezas colocadas.
	 * 
	 * Este conjunto de pruebas se centra en verificar el estado del árbitro justo
	 * después de instanciar pero antes de colocar las piezas.
	 * 
	 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
	 * @version 1.0 20230708
	 */
	@DisplayName("Tests sobre el estado inicial del árbitro antes de colocar piezas")
	@Nested
	@Order(1)
	class InicioDePartida {
		
		

		/**
		 * Comprueba la clonacion del tablero vacío inicialmente inyectado.
		 */
		@Test
		void comprobarConsultarTableroVacío() {
			Tablero tableroLocal = arbitro.consultarTablero();
			assertAll(
					() -> assertThat("El tablero clonado no es igual en contenido (debería estar vacío).", tableroLocal, is(tablero)),
					() -> assertThat("El tablero clonado debería ser un objeto con distinta identidad.", tableroLocal != tablero)
			);
		}

		/**
		 * Comprobacion de inicialización correcta del tablero, sin colocar ninguna
		 * pieza, con un tablero vacío y sin turno incialmente.
		 */
		// @formatter:off
		 /* Partimos de un tablero vacío como el que se muestra:	
		 * <pre>  
		 * 	0 -- -- -- -- -- -- --
		 *	1 -- -- -- -- -- -- --
		 *	2 -- -- -- -- -- -- --
		 *	3 -- -- -- -- -- -- --
		 *	4 -- -- -- -- -- -- --
		 *  5 -- -- -- -- -- -- --
		 *	6 -- -- -- -- -- -- --
		 *    0  1  2  3  4  5  6
		 * </pre>   
		 */
		 // @formatter:on
		@DisplayName("Comprueba el estado inicial con un tablero vacío")
		@Test
		void comprobarEstadoInicialAntesDeRellenarTablero() {
			TableroConsultor tableroConsultor = new TableroConsultor(arbitro.consultarTablero());
			assertAll("estado inicial ",
					() -> assertThat(EL_NÚMERO_DE_JUGADAS_DEBERÍA_SER_CERO, arbitro.consultarNumeroJugada(), is(0)),
					() -> assertThat("No debería haber peones negros",
							tableroConsultor.consultarNumeroPiezas(TipoPieza.PEON, Color.NEGRO), is(0)),
					() -> assertThat("No debería haber peones blancos",
							tableroConsultor.consultarNumeroPiezas(TipoPieza.PEON, Color.BLANCO), is(0)),
					() -> assertThat("No debería haber reina negra",
							tableroConsultor.consultarNumeroPiezas(TipoPieza.REINA, Color.NEGRO), is(0)),
					() -> assertThat("No debería haber reina blanca",
							tableroConsultor.consultarNumeroPiezas(TipoPieza.REINA, Color.BLANCO), is(0)),
					() -> assertThat("El turno se ha inicializado cuando debería valer nulo.", arbitro.consultarTurno(), is(nullValue())));
		}
	}

	/**
	 * Comprueba la correcta colocación de piezas con
	 * la configuración inicial por defecto del juego.
	 */
	@DisplayName("Tests sobre el estado inicial del árbitro colocando las piezas.")
	@Nested
	@Order(2)
	class ColocacionInicialDePiezas {

		/**
		 * Inicialización de piezas nuevas sobre el tablero.
		 */
		// @formatter:off
		/* Rellenaremos el tablero tal y como se muestra:	
		 * <pre>  
		 * 	0 RB PB PB PB -- -- --
		 *	1 PB -- -- -- -- -- --
		 *	2 PB -- -- -- -- -- --
		 *	3 PB -- -- -- -- -- PN
		 *	4 -- -- -- -- -- -- PN
		 *  5 -- -- -- -- -- -- PN
		 *	6 -- -- -- PN PN PN RN
		 *    0  1  2  3  4  5  6
		 * </pre>  
		 */
		 // @formatter:on
		@BeforeEach
		void colocarPiezas() {
			arbitro.colocarPiezasConfiguracionInicial();
		}

		/**
		 * Comprueba el número de piezas iniciales.
		 */
		@Test
		@DisplayName("Comprueba el número de piezas con la configuración inicial")
		void probarNumeroDePiezas() {
			TableroConsultor tableroConsultor = new TableroConsultor(arbitro.consultarTablero());
			assertAll("estado inicial tras colocar piezas ",
					() -> assertThat(EL_NÚMERO_DE_JUGADAS_DEBERÍA_SER_CERO, arbitro.consultarNumeroJugada(), is(0)),
					() -> assertThat("Debería haber seis peones negros",
							tableroConsultor.consultarNumeroPiezas(TipoPieza.PEON, Color.NEGRO), is(6)),
					() -> assertThat("Debería haber seis peones blancos",
							tableroConsultor.consultarNumeroPiezas(TipoPieza.PEON, Color.BLANCO), is(6)),
					() -> assertThat("Debería haber una reina blanca",
							tableroConsultor.consultarNumeroPiezas(TipoPieza.REINA, Color.BLANCO), is(1)),
					() -> assertThat("Debería haber una reina negra",
							tableroConsultor.consultarNumeroPiezas(TipoPieza.REINA, Color.NEGRO), is(1)),
					
					() -> assertThat("Siempre empiezan blancas.", arbitro.consultarTurno(), is(Color.BLANCO)));
		}

		/**
		 * Comprueba que se colocan peones negros correctamente.
		 * 
		 * @param pieza      pieza
		 * @param coordenada coordenada
		 */
		@DisplayName("Comprueba peones negros")
		@ParameterizedTest
		@MethodSource("noventagrados.control.TestUtil#piezaYCoordenadaDePeonesNegrosProvider")
		void comprobarPeonesNegros(Pieza pieza, Coordenada coordenada) {
			Celda celda = tablero.consultarCelda(coordenada);
			assertAll("comprobar peón negro", () -> assertThat("Celda vacia", celda.estaVacia(), is(false)),
					() -> assertThat("Color de pieza colocada incorrecta", celda.consultarColorDePieza(),
							is(Color.NEGRO)),
					() -> assertThat("Tipo de pieza incorrecto", celda.consultarPieza().consultarTipoPieza(),
							is(TipoPieza.PEON)));
		}
		
		/**
		 * Comprueba que se colocan peones blancos correctamente.
		 * 
		 * @param pieza      pieza
		 * @param coordenada coordenada
		 */
		@DisplayName("Comprueba peones negros")
		@ParameterizedTest
		@MethodSource("noventagrados.control.TestUtil#piezaYCoordenadaDePeonesBlancosProvider")
		void comprobarPeonesBlancos(Pieza pieza, Coordenada coordenada) {
			Celda celda = tablero.consultarCelda(coordenada);
			assertAll("comprobar peón blanco", () -> assertThat("Celda vacia", celda.estaVacia(), is(false)),
					() -> assertThat("Color de pieza colocada incorrecta", celda.consultarColorDePieza(),
							is(Color.BLANCO)),
					() -> assertThat("Tipo de pieza incorrecto", celda.consultarPieza().consultarTipoPieza(),
							is(TipoPieza.PEON)));
		}

	

		/**
		 * Comprueba que la reina blanca está bien colocada.
		 */
		@DisplayName("Comprueba que la reina blanca está bien colocada.")
		@Test
		void comprobarColocacionDeReinaBlanca() {
			Celda celda = tablero.consultarCelda(new Coordenada(0, 0));
			assertAll(
					() -> assertThat("La reina blanca no está bien colocada.", 
							celda.consultarPieza().consultarTipoPieza(), is(TipoPieza.REINA)),
					() -> assertThat("La reina blanca no está bien colocada.", 
							celda.consultarPieza().consultarColor(), is(Color.BLANCO))
			);
		}
		
		/**
		 * Comprueba que la reina negra está bien colocada.
		 */
		@DisplayName("Comprueba que la reina blanca está bien colocada.")
		@Test
		void comprobarColocacionDeReinaNegra() {
			Celda celda = tablero.consultarCelda(new Coordenada(6, 6));
			assertAll(
					() -> assertThat("La reina negra no está bien colocada.", 
							celda.consultarPieza().consultarTipoPieza(), is(TipoPieza.REINA)),
					() -> assertThat("La reina negra no está bien colocada.", 
							celda.consultarPieza().consultarColor(), is(Color.NEGRO))
			);
		}
	}

	/**
	 * Comprueba la colocación de piezas ad-hoc, sin tener que seguir 
	 * la configuración por defecto inicial del juego.
	 * 
	 */
	@DisplayName("Tests sobre el estado inicial del árbitro colocando las piezas ad-hoc.")
	@Nested
	@Order(3)
	class ColocacionAdHocDePiezas {
		/** Texto reina negra bien colocada. */
		private static final String LA_REINA_NEGRA_ESTÁ_BIEN_COLOCADA = "La reina negra está bien colocada.";
		/** Texto reina blanca bien colocada. */
		private static final String LA_REINA_BLANCA_ESTÁ_BIEN_COLOCADA = "La reina blanca está bien colocada.";
		/** Texto peón blanco bien colocado. */
		private static final String EL_PEÓN_BLANCO_ESTÁ_BIEN_COLOCADO = "El peón blanco está bien colocado.";
		/** Texto peón negro bien colocado.*/
		private static final String EL_PEÓN_NEGRO_ESTÁ_BIEN_COLOCADO = "El peón negro está bien colocado.";

		/**
		 * Comprueba la colocación de todas las piezas en posición diferente.
		 */
		// @formatter:off
		/* Rellenaremos el tablero tal y como se muestra:	
		 * <pre>  
		 * 	0 -- PB -- PB -- -- --
		 *	1 PB -- -- -- -- -- --
		 *	2 -- -- RB -- -- -- --
		 *	3 PB -- -- -- -- -- PN
		 *	4 -- -- -- -- RN -- --
		 *  5 -- -- -- -- -- -- PN
		 *	6 -- -- -- PN -- PN --
		 *    0  1  2  3  4  5  6
		 * </pre>  
		 */
		 // @formatter:on
		@Test
		@DisplayName("Comprueba la colocación de todas las piezas en posición diferente y con turno cambiado.")
		void probarColocandoTodasLasPiezasAdHoc() {
			arbitro.colocarPiezas(new Pieza[] { new Pieza(TipoPieza.PEON, Color.BLANCO),
					new Pieza(TipoPieza.PEON, Color.BLANCO),
					new Pieza(TipoPieza.PEON, Color.BLANCO),
					new Pieza(TipoPieza.PEON, Color.BLANCO),
					new Pieza(TipoPieza.PEON, Color.NEGRO),
					new Pieza(TipoPieza.PEON, Color.NEGRO),
					new Pieza(TipoPieza.PEON, Color.NEGRO),
					new Pieza(TipoPieza.PEON, Color.NEGRO),
					new Pieza(TipoPieza.REINA, Color.BLANCO),
					new Pieza(TipoPieza.REINA, Color.NEGRO) }, 
					
					new Coordenada[] {
						new Coordenada(0,1),
						new Coordenada(0,3),
						new Coordenada(1,0),
						new Coordenada(3,0),
						new Coordenada(3,6),
						new Coordenada(5,6),
						new Coordenada(6,3),
						new Coordenada(6,5),
						new Coordenada(2,2),
						new Coordenada(4,4),
						
					},
					Color.NEGRO);
			
			TableroConsultor tableroConsultor = new TableroConsultor(arbitro.consultarTablero());
			assertAll("estado inicial tras colocar piezas sin seguir el orden habitual ",
					() -> assertThat(EL_NÚMERO_DE_JUGADAS_DEBERÍA_SER_CERO, arbitro.consultarNumeroJugada(), is(0)),
					() -> assertThat("Debería haber cuatro peones blancos",
							tableroConsultor.consultarNumeroPiezas(TipoPieza.PEON, Color.BLANCO), is(4)),
					() -> assertThat("Debería haber cuatro peones blancos",
							tableroConsultor.consultarNumeroPiezas(TipoPieza.PEON, Color.NEGRO), is(4)),
					() -> assertThat("Debería haber una reina blanca",
							tableroConsultor.consultarNumeroPiezas(TipoPieza.REINA, Color.BLANCO), is(1)),
					() -> assertThat("Debería haber una reina negra",
							tableroConsultor.consultarNumeroPiezas(TipoPieza.REINA, Color.BLANCO), is(1)),					
					
					() -> assertThat(EL_PEÓN_BLANCO_ESTÁ_BIEN_COLOCADO, tablero.consultarCelda(new Coordenada(0,1)).consultarPieza().consultarTipoPieza(), is(TipoPieza.PEON)),
					() -> assertThat(EL_PEÓN_BLANCO_ESTÁ_BIEN_COLOCADO, tablero.consultarCelda(new Coordenada(0,3)).consultarPieza().consultarTipoPieza(), is(TipoPieza.PEON)),
					() -> assertThat(EL_PEÓN_BLANCO_ESTÁ_BIEN_COLOCADO, tablero.consultarCelda(new Coordenada(1,0)).consultarPieza().consultarTipoPieza(), is(TipoPieza.PEON)),
					() -> assertThat(EL_PEÓN_BLANCO_ESTÁ_BIEN_COLOCADO, tablero.consultarCelda(new Coordenada(3,0)).consultarPieza().consultarTipoPieza(), is(TipoPieza.PEON)),
					() -> assertThat(EL_PEÓN_BLANCO_ESTÁ_BIEN_COLOCADO, tablero.consultarCelda(new Coordenada(0,1)).consultarPieza().consultarColor(), is(Color.BLANCO)),
					() -> assertThat(EL_PEÓN_BLANCO_ESTÁ_BIEN_COLOCADO, tablero.consultarCelda(new Coordenada(0,3)).consultarPieza().consultarColor(), is(Color.BLANCO)),
					() -> assertThat(EL_PEÓN_BLANCO_ESTÁ_BIEN_COLOCADO, tablero.consultarCelda(new Coordenada(1,0)).consultarPieza().consultarColor(), is(Color.BLANCO)),
					() -> assertThat(EL_PEÓN_BLANCO_ESTÁ_BIEN_COLOCADO, tablero.consultarCelda(new Coordenada(3,0)).consultarPieza().consultarColor(), is(Color.BLANCO)),
					
					
					() -> assertThat(EL_PEÓN_NEGRO_ESTÁ_BIEN_COLOCADO, tablero.consultarCelda(new Coordenada(3,6)).consultarPieza().consultarTipoPieza(), is(TipoPieza.PEON)),
					() -> assertThat(EL_PEÓN_NEGRO_ESTÁ_BIEN_COLOCADO, tablero.consultarCelda(new Coordenada(5,6)).consultarPieza().consultarTipoPieza(), is(TipoPieza.PEON)),
					() -> assertThat(EL_PEÓN_NEGRO_ESTÁ_BIEN_COLOCADO, tablero.consultarCelda(new Coordenada(6,3)).consultarPieza().consultarTipoPieza(), is(TipoPieza.PEON)),
					() -> assertThat(EL_PEÓN_NEGRO_ESTÁ_BIEN_COLOCADO, tablero.consultarCelda(new Coordenada(6,5)).consultarPieza().consultarTipoPieza(), is(TipoPieza.PEON)),
					() -> assertThat(EL_PEÓN_NEGRO_ESTÁ_BIEN_COLOCADO, tablero.consultarCelda(new Coordenada(3,6)).consultarPieza().consultarColor(), is(Color.NEGRO)),
					() -> assertThat(EL_PEÓN_NEGRO_ESTÁ_BIEN_COLOCADO, tablero.consultarCelda(new Coordenada(5,6)).consultarPieza().consultarColor(), is(Color.NEGRO)),
					() -> assertThat(EL_PEÓN_NEGRO_ESTÁ_BIEN_COLOCADO, tablero.consultarCelda(new Coordenada(6,3)).consultarPieza().consultarColor(), is(Color.NEGRO)),
					() -> assertThat(EL_PEÓN_NEGRO_ESTÁ_BIEN_COLOCADO, tablero.consultarCelda(new Coordenada(6,5)).consultarPieza().consultarColor(), is(Color.NEGRO)),

					() -> assertThat(LA_REINA_BLANCA_ESTÁ_BIEN_COLOCADA, tablero.consultarCelda(new Coordenada(2,2)).consultarPieza().consultarTipoPieza(), is(TipoPieza.REINA)),
					() -> assertThat(LA_REINA_BLANCA_ESTÁ_BIEN_COLOCADA, tablero.consultarCelda(new Coordenada(2,2)).consultarPieza().consultarColor(), is(Color.BLANCO)),
					
					() -> assertThat(LA_REINA_NEGRA_ESTÁ_BIEN_COLOCADA, tablero.consultarCelda(new Coordenada(4,4)).consultarPieza().consultarTipoPieza(), is(TipoPieza.REINA)),
					() -> assertThat(LA_REINA_NEGRA_ESTÁ_BIEN_COLOCADA, tablero.consultarCelda(new Coordenada(4,4)).consultarPieza().consultarColor(), is(Color.NEGRO)),
					
					() -> assertThat("En esta caso empiezan negras.", arbitro.consultarTurno(), is(Color.NEGRO)));
			
		}
	}
}
