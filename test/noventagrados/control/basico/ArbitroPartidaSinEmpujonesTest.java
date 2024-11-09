package noventagrados.control.basico;

import static noventagrados.control.TestUtil.fabricarJugada;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Timeout.ThreadMode.SEPARATE_THREAD;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import noventagrados.control.Arbitro;
import noventagrados.control.TableroConsultor;
import noventagrados.modelo.Tablero;
import noventagrados.util.Color;
import noventagrados.util.TipoPieza;

/**
 * Partida básica sin empujones entre piezas.
 * No se comprueba la legalidad de las jugadas por simplificación de los tests
 * asumiendo que se han pasado los tests básicos previos.
 * 
 * Demuestra la posibilidad de jugar una partida mínima sin incluir
 * la funcionalidad de empujar piezas ni comprobación de legalidad
 * de la jugada.
 *  
 * @author <a href="rmartico@ubu.es">Raúl Marticorena</a>
 * @since 1.0
 * @version 1.0
 */
@DisplayName("Tests del Arbitro sobre partidas básicas sin empujones.")
@Timeout(value = 2, unit = TimeUnit.SECONDS, threadMode = SEPARATE_THREAD) // Time out global para todos los tests salvo los de ciclo de vida
public class ArbitroPartidaSinEmpujonesTest {

	/** Árbitro de testing. */
	private Arbitro arbitro;

	/** Tablero de testing. */
	private Tablero tablero;

	/** Generación del árbitro para testing. 
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
	@BeforeEach
	void inicializar() {
		// Inyección de tablero para testing...
		tablero = new Tablero();
		arbitro = new Arbitro(tablero);
		arbitro.colocarPiezasConfiguracionInicial();
	}
	
	/**
	 * Comprueba una partida básica sin empujones con victoria de blancas en el centro.
	 */
	@Test
	@DisplayName("Comprueba que hay victoria de blancas en el centro sin empujar una sola pieza.")
	void probarPartidaSinEmpujonesConVictoriaDeBlancasEnElCentro() {

		// given
		arbitro.empujar(fabricarJugada(tablero, 1, 0, 1, 4)); // blancas 1
		arbitro.cambiarTurno(); 
		arbitro.empujar(fabricarJugada(tablero, 5, 6, 5, 2)); // negras 2
		arbitro.cambiarTurno();
		arbitro.empujar(fabricarJugada(tablero, 0, 1, 4, 1)); // blancas 3
		arbitro.cambiarTurno();
		arbitro.empujar(fabricarJugada(tablero, 6, 5, 2, 5)); // negras 4
		arbitro.cambiarTurno();
		arbitro.empujar(fabricarJugada(tablero, 0, 3, 0, 5)); // blancas 5
		arbitro.cambiarTurno();
		arbitro.empujar(fabricarJugada(tablero, 4, 6, 4, 3)); // negras 6
		arbitro.cambiarTurno();		
		arbitro.empujar(fabricarJugada(tablero, 0, 2, 0, 4)); // blancas 7
		arbitro.cambiarTurno();
		arbitro.empujar(fabricarJugada(tablero, 3, 6, 1, 6)); // negras 8
		arbitro.cambiarTurno();
		arbitro.empujar(fabricarJugada(tablero, 0, 0, 0, 3)); // blancas 9
		arbitro.cambiarTurno();
		arbitro.empujar(fabricarJugada(tablero, 5, 2, 5, 1)); // negras 10
		arbitro.cambiarTurno();
		
		// @formatter:off
		/* El estado del tablero antes del último movimiento debería ser:
		 * 0 -- -- -- -- PB PB --  
		 * 1 -- -- -- -- PB -- PN
		 * 2 PB -- -- -- -- PN -- 
		 * 3 PB -- -- RB -- -- -- 
		 * 4 -- PB -- PN -- -- --
		 * 5 -- PN -- -- -- -- -- 
		 * 6 -- -- -- PN PN -- RB
		 *   0  1  2  3  4  5  6   
		 */
		 // @formatter:on

		// when
		arbitro.empujar(fabricarJugada(tablero, 0, 3, 3, 3)); // reina negra alcanza el centro 11
		
		// then
		final String cadenaEsperada = """
				0 -- -- -- -- PB PB --
				1 -- -- -- -- PB -- PN
				2 PB -- -- -- -- PN -- 
				3 PB -- -- RB -- -- -- 
				4 -- PB -- PN -- -- --
				5 -- PN -- -- -- -- --
				6 -- -- -- PN PN -- RN
				  0  1  2  3  4  5  6  """.replaceAll("\\s", "");
		String cadenaObtenida = arbitro.consultarTablero().aTexto().replaceAll("\\s", "");
		TableroConsultor tc = new TableroConsultor(arbitro.consultarTablero());
		assertAll("Victoria de blancas alcanzando la posición central.",
				() -> assertThat("Debería ganar piezas blancas.", arbitro.consultarTurnoGanador(), is(Color.BLANCO)),
				() -> assertThat("Deberían estar todos los peones blancos sobre el tablero.",
						tc.consultarNumeroPiezas(TipoPieza.PEON, Color.BLANCO), is(6)),
				() -> assertThat("Deberían estar todos los peones negros sobre el tablero.",
						tc.consultarNumeroPiezas(TipoPieza.PEON, Color.NEGRO), is(6)),
				() -> assertThat("Deberían estar la reina blanca sobre el tablero.",
						tc.consultarNumeroPiezas(TipoPieza.REINA, Color.BLANCO), is(1)),
				() -> assertThat("Deberían estar la reina negra sobre el tablero.",
						tc.consultarNumeroPiezas(TipoPieza.REINA, Color.NEGRO), is(1)),
				() -> assertThat("La caja blanca debería estar vacía.",
						arbitro.consultarCaja(Color.BLANCO).contarPiezas(), is(0)),
				() -> assertThat("La caja negra debería estar vacía.",
						arbitro.consultarCaja(Color.NEGRO).contarPiezas(), is(0)),			
				() -> assertThat("La partida se desarrolla en un número erróneo de jugadas.",
						arbitro.consultarNumeroJugada(), is(11)),
				() -> assertThat("El turno debería ser del jugador con blancas.", arbitro.consultarTurno(),
						is(Color.BLANCO)),
				() -> assertEquals(cadenaEsperada, cadenaObtenida, "Estado final del tablero en formato texto incorrecto")
				);
	}

	/**
	 * Comprueba una partida básica sin empujones con victoria de negras en el centro.
	 */
	@Test
	@DisplayName("Comprueba que hay victoria de negras en el centro sin empujar una sola pieza.")
	void probarPartidaSinEmpujonesConVictoriaDeNegrasEnElCentro() {

		// given
		arbitro.empujar(fabricarJugada(tablero, 0, 2, 4, 2)); // blancas 1
		arbitro.cambiarTurno(); 
		arbitro.empujar(fabricarJugada(tablero, 6, 3, 2, 3)); // negras 2
		arbitro.cambiarTurno();
		arbitro.empujar(fabricarJugada(tablero, 0, 1, 3, 1)); // blancas 3
		arbitro.cambiarTurno();
		arbitro.empujar(fabricarJugada(tablero, 3, 6, 3, 2)); // negras 4
		arbitro.cambiarTurno();
		arbitro.empujar(fabricarJugada(tablero, 3, 1, 0, 1)); // blancas 5
		arbitro.cambiarTurno();
		arbitro.empujar(fabricarJugada(tablero, 6, 4, 3, 4)); // negras 6
		arbitro.cambiarTurno();		
		arbitro.empujar(fabricarJugada(tablero, 0, 1, 3, 1)); // blancas 7
		arbitro.cambiarTurno();
		arbitro.empujar(fabricarJugada(tablero, 6, 5, 4, 5)); // negras 8
		arbitro.cambiarTurno();
		arbitro.empujar(fabricarJugada(tablero, 1, 0, 1, 4)); // blancas 9
		arbitro.cambiarTurno();
		arbitro.empujar(fabricarJugada(tablero, 6, 6, 6, 3)); // negras 10
		arbitro.cambiarTurno();
		arbitro.empujar(fabricarJugada(tablero, 0, 3, 0, 6)); // blancas 11
		arbitro.cambiarTurno();
		arbitro.empujar(fabricarJugada(tablero, 6, 3, 5, 3)); // negras 12
		arbitro.cambiarTurno();
		arbitro.empujar(fabricarJugada(tablero, 0, 6, 0, 3)); // blancas 13
		arbitro.cambiarTurno();


		// @formatter:off
		/* El estado del tablero antes del último movimiento debería ser:
		 * 0 RB -- -- PB -- -- --  
		 * 1 -- -- -- -- PB -- --
		 * 2 PB -- -- PN -- -- -- 
		 * 3 PB PB PN RN PN -- -- 
		 * 4 -- -- PB -- -- PN PN
		 * 5 -- -- -- -- -- -- PN 
		 * 6 -- -- -- -- -- -- --
		 *   0  1  2  3  4  5  6   
		 */
		 // @formatter:on

		// when
		arbitro.empujar(fabricarJugada(tablero, 5, 3, 3, 3)); // reina negra alcanza el centro 14
		
		// then
		final String cadenaEsperada = """
				0 RB -- -- PB -- -- -- 
				1 -- -- -- -- PB -- --
				2 PB -- -- PN -- -- -- 
				3 PB PB PN RN PN -- -- 
				4 -- -- PB -- -- PN PN
				5 -- -- -- -- -- -- PN
				6 -- -- -- -- -- -- --
				  0  1  2  3  4  5  6  """.replaceAll("\\s", "");
		String cadenaObtenida = arbitro.consultarTablero().aTexto().replaceAll("\\s", "");
		TableroConsultor tc = new TableroConsultor(arbitro.consultarTablero());
		assertAll("Victoria de negras alcanzando la posición central.",
				() -> assertThat("Debería ganar piezas negras.", arbitro.consultarTurnoGanador(), is(Color.NEGRO)),
				() -> assertThat("Deberían estar todos los peones blancos sobre el tablero.",
						tc.consultarNumeroPiezas(TipoPieza.PEON, Color.BLANCO), is(6)),
				() -> assertThat("Deberían estar todos los peones negros sobre el tablero.",
						tc.consultarNumeroPiezas(TipoPieza.PEON, Color.NEGRO), is(6)),
				() -> assertThat("Deberían estar la reina blanca sobre el tablero.",
						tc.consultarNumeroPiezas(TipoPieza.REINA, Color.BLANCO), is(1)),
				() -> assertThat("Deberían estar la reina negra sobre el tablero.",
						tc.consultarNumeroPiezas(TipoPieza.REINA, Color.NEGRO), is(1)),
				() -> assertThat("La caja blanca debería estar vacía.",
						arbitro.consultarCaja(Color.BLANCO).contarPiezas(), is(0)),
				() -> assertThat("La caja negra debería estar vacía.",
						arbitro.consultarCaja(Color.NEGRO).contarPiezas(), is(0)),			
				() -> assertThat("La partida se desarrolla en un número erróneo de jugadas.",
						arbitro.consultarNumeroJugada(), is(14)),
				() -> assertThat("El turno debería ser del jugador con negras.", arbitro.consultarTurno(),
						is(Color.NEGRO)),
				() -> assertEquals(cadenaEsperada, cadenaObtenida, "Estado final del tablero en formato texto incorrecto")
				);
	}
	
	

	
}
