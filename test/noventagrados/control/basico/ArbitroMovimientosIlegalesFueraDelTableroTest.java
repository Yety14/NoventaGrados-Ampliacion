package noventagrados.control.basico;

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
import noventagrados.modelo.Celda;
import noventagrados.modelo.Jugada;
import noventagrados.modelo.Tablero;
import noventagrados.util.Coordenada;

/**
 * Comprobación de movimientos ilegales con jugadas FUERA del tablero.
 * 
 * Se asume que al menos se han colocado correctamente las piezas. En caso
 * contrario estos tests no se pasarán.
 *
 */
@DisplayName("Tests del Arbitro sobre el control de movimientos ilegales fuera del tablero.")
@Timeout(value = 2, unit = TimeUnit.SECONDS, threadMode = SEPARATE_THREAD) // Time out global para todos los tests salvo los de ciclo de vida
public class ArbitroMovimientosIlegalesFueraDelTableroTest {

	/** Texto movimiento ilegal.*/
	private static final String EL_MOVIMIENTO_DEBERÍA_SER_ILEGAL_PARA = "El movimiento debería ser ilegal para ";

	/** Árbitro de testing. */
	private Arbitro arbitro;

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
		Tablero tablero = new Tablero();
		arbitro = new Arbitro(tablero);
		arbitro.colocarPiezasConfiguracionInicial();
	}

	/**
	 * Comprobacion de ilegalidad de intentar mover a una celda que no existe en el tablero.
	 */
	@DisplayName("Comprueba el movimiento ilegal de mover a una celda que no existe en el tablero.")
	@Test
	void comprobaMovimientoImposible() {
		Jugada jugada1 = new Jugada(new Celda(new Coordenada(0,0)), new Celda(new Coordenada(-1,-1)));
		Jugada jugada2 = new Jugada(new Celda(new Coordenada(-1,-1)), new Celda(new Coordenada(0,0)));
		Jugada jugada3 = new Jugada(new Celda(new Coordenada(0,0)), new Celda(new Coordenada(0,-1)));
		Jugada jugada4 = new Jugada(new Celda(new Coordenada(7,0)), new Celda(new Coordenada(0,0)));
		Jugada jugada5 = new Jugada(new Celda(new Coordenada(0,0)), new Celda(new Coordenada(0,7)));
		Jugada jugada6 = new Jugada(new Celda(new Coordenada(0,0)), new Celda(new Coordenada(7,7)));
		assertAll("movimiento ilegal fuera del tablero",
				() -> assertThat(EL_MOVIMIENTO_DEBERÍA_SER_ILEGAL_PARA + jugada1, arbitro.esMovimientoLegal(jugada1), is(false)),
				() -> assertThat(EL_MOVIMIENTO_DEBERÍA_SER_ILEGAL_PARA + jugada2, arbitro.esMovimientoLegal(jugada2), is(false)),
				() -> assertThat(EL_MOVIMIENTO_DEBERÍA_SER_ILEGAL_PARA + jugada3, arbitro.esMovimientoLegal(jugada3), is(false)),
				() -> assertThat(EL_MOVIMIENTO_DEBERÍA_SER_ILEGAL_PARA + jugada4, arbitro.esMovimientoLegal(jugada4), is(false)),
				() -> assertThat(EL_MOVIMIENTO_DEBERÍA_SER_ILEGAL_PARA + jugada5, arbitro.esMovimientoLegal(jugada5), is(false)),
				() -> assertThat(EL_MOVIMIENTO_DEBERÍA_SER_ILEGAL_PARA + jugada6, arbitro.esMovimientoLegal(jugada6), is(false))				
				);
	}
	

}
