package noventagrados.control.basico;

import static noventagrados.control.TestUtil.fabricarJugada;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Timeout.ThreadMode.SEPARATE_THREAD;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import noventagrados.control.Arbitro;
import noventagrados.modelo.Jugada;
import noventagrados.modelo.Tablero;

/**
 * Comprobación de movimientos legales del árbitro.
 * 
 * Se asume que al menos se han colocado correctamente las piezas. En caso
 * contrario estos tests no se pasarán.
 * 
 * @see ArbitroInicializacionTest
 */
@DisplayName("Tests del Arbitro sobre el control de  movimientos legales.")
@Timeout(value = 2, unit = TimeUnit.SECONDS, threadMode = SEPARATE_THREAD) // Time out global para todos los tests salvo los de ciclo de vida
public class ArbitroMovimientosLegalesTest {

	/** Texto de inicio para movimiento ilegal. */
	private static final String EL_MOVIMIENTO_DEBERÍA_SER_ILEGAL_PARA = "El movimiento debería ser ilegal para ";

	/** Árbitro de testing. */
	private Arbitro arbitro;

	/** Tablero de testing. */
	private Tablero tablero;

	/** Generación del árbitro para testing. */
	// @formatter:off
	/* Partimos de un tablero con las piezas ya colocadas como el que se muestra:	
	 * 
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
	void inicializar() {
		// Inyección de tablero para testing...
		tablero = new Tablero();
		arbitro = new Arbitro(tablero);
		arbitro.colocarPiezasConfiguracionInicial();
	}
	
	/**
	 * Comprobacion de legalidad de intentar mover en horizontal una pieza
	 * blanca un número correcto de celdas.
	 * 
	 * @see noventagrados.control.TestUtil#fabricarJugada
	 */
	@DisplayName("Comprueba el movimiento legal de pieza blanca en horizontal.")
	@Test
	void comprobarPiezaBlancaEnHorizontal() {
		Jugada jugada1 = fabricarJugada(tablero,0,0,0,4);
		Jugada jugada2 = fabricarJugada(tablero,0,1,0,2);
		Jugada jugada3 = fabricarJugada(tablero,0,2,0,3);
		Jugada jugada4 = fabricarJugada(tablero,0,3,0,5);
		
		Jugada jugada5 = fabricarJugada(tablero,1,0,1,4);
		Jugada jugada6 = fabricarJugada(tablero,2,0,2,4);
		Jugada jugada7 = fabricarJugada(tablero,3,0,3,4);
	
		
		assertAll("mover pieza blanca en horizontal",
				() -> assertThat(EL_MOVIMIENTO_DEBERÍA_SER_ILEGAL_PARA + jugada1, arbitro.esMovimientoLegal(jugada1), is(true)),
				() -> assertThat(EL_MOVIMIENTO_DEBERÍA_SER_ILEGAL_PARA + jugada2, arbitro.esMovimientoLegal(jugada2), is(true)),
				() -> assertThat(EL_MOVIMIENTO_DEBERÍA_SER_ILEGAL_PARA + jugada3, arbitro.esMovimientoLegal(jugada3), is(true)),
				() -> assertThat(EL_MOVIMIENTO_DEBERÍA_SER_ILEGAL_PARA + jugada4, arbitro.esMovimientoLegal(jugada4), is(true)),
				() -> assertThat(EL_MOVIMIENTO_DEBERÍA_SER_ILEGAL_PARA + jugada5, arbitro.esMovimientoLegal(jugada5), is(true)),	
				() -> assertThat(EL_MOVIMIENTO_DEBERÍA_SER_ILEGAL_PARA + jugada6, arbitro.esMovimientoLegal(jugada6), is(true)),	
				() -> assertThat(EL_MOVIMIENTO_DEBERÍA_SER_ILEGAL_PARA + jugada7, arbitro.esMovimientoLegal(jugada7), is(true))
				);
		
	}
	
	
	/**
	 * Comprobacion de legalidad de intentar mover en vertical una pieza
	 * blanca un número correcto de celdas.
	 * 
	 * @see noventagrados.control.TestUtil#fabricarJugada
	 */
	@DisplayName("Comprueba el movimiento legal de pieza blanca en vertical.")
	@Test
	void comprobarPiezaBlancaEnVertical() {
		Jugada jugada1 = fabricarJugada(tablero,0,0,4,0);
		Jugada jugada2 = fabricarJugada(tablero,0,1,4,1);
		Jugada jugada3 = fabricarJugada(tablero,0,2,4,2);
		Jugada jugada4 = fabricarJugada(tablero,0,3,4,3);
		
		Jugada jugada5 = fabricarJugada(tablero,1,0,2,0);
		Jugada jugada6 = fabricarJugada(tablero,2,0,3,0);
		Jugada jugada7 = fabricarJugada(tablero,3,0,5,0);	
		
		assertAll("mover pieza blanca en vertical",
				() -> assertThat(EL_MOVIMIENTO_DEBERÍA_SER_ILEGAL_PARA + jugada1, arbitro.esMovimientoLegal(jugada1), is(true)),
				() -> assertThat(EL_MOVIMIENTO_DEBERÍA_SER_ILEGAL_PARA + jugada2, arbitro.esMovimientoLegal(jugada2), is(true)),
				() -> assertThat(EL_MOVIMIENTO_DEBERÍA_SER_ILEGAL_PARA + jugada3, arbitro.esMovimientoLegal(jugada3), is(true)),
				() -> assertThat(EL_MOVIMIENTO_DEBERÍA_SER_ILEGAL_PARA + jugada4, arbitro.esMovimientoLegal(jugada4), is(true)),
				() -> assertThat(EL_MOVIMIENTO_DEBERÍA_SER_ILEGAL_PARA + jugada5, arbitro.esMovimientoLegal(jugada5), is(true)),	
				() -> assertThat(EL_MOVIMIENTO_DEBERÍA_SER_ILEGAL_PARA + jugada6, arbitro.esMovimientoLegal(jugada6), is(true)),	
				() -> assertThat(EL_MOVIMIENTO_DEBERÍA_SER_ILEGAL_PARA + jugada7, arbitro.esMovimientoLegal(jugada7), is(true))
				);		
	}
	

	/**
	 * Comprobacion de legalidad de intentar mover en horizontal una pieza
	 * negra un número correcto de celdas.
	 * 
	 * @see noventagrados.control.TestUtil#fabricarJugada
	 */
	@DisplayName("Comprueba el movimiento legal de pieza negra en horizontal.")
	@Test
	void comprobarPiezaNegraEnHorizontal() {
		Jugada jugada1 = fabricarJugada(tablero,3,6,3,2);
		Jugada jugada2 = fabricarJugada(tablero,4,6,4,2);
		Jugada jugada3 = fabricarJugada(tablero,5,6,5,2);
		Jugada jugada4 = fabricarJugada(tablero,6,6,6,2);
		
		Jugada jugada5 = fabricarJugada(tablero,6,3,6,1);
		Jugada jugada6 = fabricarJugada(tablero,6,4,6,3);
		Jugada jugada7 = fabricarJugada(tablero,6,5,6,4);
		
		arbitro.cambiarTurno(); // cambiamos turno para que pueda mover negras
		
		assertAll("mover pieza negra en horizontal",
				() -> assertThat(EL_MOVIMIENTO_DEBERÍA_SER_ILEGAL_PARA + jugada1, arbitro.esMovimientoLegal(jugada1), is(true)),
				() -> assertThat(EL_MOVIMIENTO_DEBERÍA_SER_ILEGAL_PARA + jugada2, arbitro.esMovimientoLegal(jugada2), is(true)),
				() -> assertThat(EL_MOVIMIENTO_DEBERÍA_SER_ILEGAL_PARA + jugada3, arbitro.esMovimientoLegal(jugada3), is(true)),
				() -> assertThat(EL_MOVIMIENTO_DEBERÍA_SER_ILEGAL_PARA + jugada4, arbitro.esMovimientoLegal(jugada4), is(true)),
				() -> assertThat(EL_MOVIMIENTO_DEBERÍA_SER_ILEGAL_PARA + jugada5, arbitro.esMovimientoLegal(jugada5), is(true)),	
				() -> assertThat(EL_MOVIMIENTO_DEBERÍA_SER_ILEGAL_PARA + jugada6, arbitro.esMovimientoLegal(jugada6), is(true)),	
				() -> assertThat(EL_MOVIMIENTO_DEBERÍA_SER_ILEGAL_PARA + jugada7, arbitro.esMovimientoLegal(jugada7), is(true))
				);
		
	}
	
	
	/**
	 * Comprobacion de legalidad de intentar mover en vertical una pieza
	 * negra un número correcto de celdas.
	 * 
	 * @see noventagrados.control.TestUtil#fabricarJugada
	 */
	@DisplayName("Comprueba el movimiento legal de pieza negra en vertical.")
	@Test
	void comprobarPiezaNegraEnVertical() {
		Jugada jugada1 = fabricarJugada(tablero,3,6,1,6);
		Jugada jugada2 = fabricarJugada(tablero,4,6,3,6);
		Jugada jugada3 = fabricarJugada(tablero,5,6,4,6);
		Jugada jugada4 = fabricarJugada(tablero,6,6,2,6);
		
		Jugada jugada5 = fabricarJugada(tablero,6,3,2,3);
		Jugada jugada6 = fabricarJugada(tablero,6,4,2,4);
		Jugada jugada7 = fabricarJugada(tablero,6,5,2,5);
		
		arbitro.cambiarTurno(); // cambiamos turno para que pueda mover negras
		
		assertAll("mover pieza negra en vertical",
				() -> assertThat(EL_MOVIMIENTO_DEBERÍA_SER_ILEGAL_PARA + jugada1, arbitro.esMovimientoLegal(jugada1), is(true)),
				() -> assertThat(EL_MOVIMIENTO_DEBERÍA_SER_ILEGAL_PARA + jugada2, arbitro.esMovimientoLegal(jugada2), is(true)),
				() -> assertThat(EL_MOVIMIENTO_DEBERÍA_SER_ILEGAL_PARA + jugada3, arbitro.esMovimientoLegal(jugada3), is(true)),
				() -> assertThat(EL_MOVIMIENTO_DEBERÍA_SER_ILEGAL_PARA + jugada4, arbitro.esMovimientoLegal(jugada4), is(true)),
				() -> assertThat(EL_MOVIMIENTO_DEBERÍA_SER_ILEGAL_PARA + jugada5, arbitro.esMovimientoLegal(jugada5), is(true)),	
				() -> assertThat(EL_MOVIMIENTO_DEBERÍA_SER_ILEGAL_PARA + jugada6, arbitro.esMovimientoLegal(jugada6), is(true)),	
				() -> assertThat(EL_MOVIMIENTO_DEBERÍA_SER_ILEGAL_PARA + jugada7, arbitro.esMovimientoLegal(jugada7), is(true))
				);
		
	}
}
