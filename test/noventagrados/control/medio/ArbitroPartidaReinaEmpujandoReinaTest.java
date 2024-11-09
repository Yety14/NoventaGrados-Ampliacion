package noventagrados.control.medio;

import static noventagrados.control.TestUtil.fabricarJugada;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Timeout.ThreadMode.SEPARATE_THREAD;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import noventagrados.control.Arbitro;
import noventagrados.control.TableroConsultor;
import noventagrados.modelo.Pieza;
import noventagrados.modelo.Tablero;
import noventagrados.util.Color;
import noventagrados.util.TipoPieza;

/**
 * Partida simple con pocos movimientos y finalización con victoria empujando
 * una reina a la otra fuera del tablero.
 * 
 * No se comprueba la legalidad de las jugadas por simplificación de los tests
 * asumiendo que se han pasado los tests básicos previos.
 * 
 * @author <a href="rmartico@ubu.es">Raúl Marticorena</a>
 * @since 1.0
 * @version 1.0
 */
@DisplayName("Tests del Arbitro sobre partidas con reina expulsando a reina del tablero.")
@Timeout(value = 2, unit = TimeUnit.SECONDS, threadMode = SEPARATE_THREAD) // Time out global para todos los tests salvo los de ciclo de vida
public class ArbitroPartidaReinaEmpujandoReinaTest {

	/** Árbitro de testing. */
	private Arbitro arbitro;

	/** Tablero de testing. */
	private Tablero tablero;

	/**
	 * Generación del árbitro para testing.
	 * 
	 * <pre>
	 *   
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
	 * Comprueba una partida con victoria de negras expulsando a reina blanca.
	 */
	@Test
	@DisplayName("Comprueba que hay victoria de negras expulsando a reina blanca.")
	void probarPartidaConVictoriaDeNegrasExpulsandoAReinaBlanca() {

		// given
		arbitro.empujar(fabricarJugada(tablero, 0, 0, 0, 4)); // blancas
		arbitro.cambiarTurno();
		arbitro.empujar(fabricarJugada(tablero, 6, 6, 2, 6)); // negras
		arbitro.cambiarTurno();
		arbitro.empujar(fabricarJugada(tablero, 0, 4, 0, 6)); // blancas
		arbitro.cambiarTurno();

		// @formatter:off
		/* El estado del tablero antes del último movimiento debería ser:
		 * 0 -- -- -- -- -- -- RB  
		 * 1 PB -- -- -- -- -- PN
		 * 2 PB -- -- -- -- -- RN 
		 * 3 PB -- -- -- -- -- -- 
		 * 4 -- -- -- -- -- -- --
		 * 5 -- -- -- -- -- -- -- 
		 * 6 -- -- -- PN PN PN --
		 *   0  1  2  3  4  5  6   
		 */
		 // @formatter:on

		// when
		arbitro.empujar(fabricarJugada(tablero, 2, 6, 0, 6)); // reina negra expulsa reina blanca

		// then
		final String cadenaEsperada = """
				0 -- -- -- -- -- -- RN
				1 PB -- -- -- -- -- --
				2 PB -- -- -- -- -- --
				3 PB -- -- -- -- -- --
				4 -- -- -- -- -- -- --
				5 -- -- -- -- -- -- --
				6 -- -- -- PN PN PN --
				  0  1  2  3  4  5  6  """.replaceAll("\\s", "");
		String cadenaObtenida = arbitro.consultarTablero().aTexto().replaceAll("\\s", "");
		TableroConsultor tc = new TableroConsultor(arbitro.consultarTablero());
		assertAll("Victoria de negras expulsando reina blanca.",
				() -> assertThat("La partida no está finalizada.", arbitro.estaFinalizadaPartida(), is(true)),

				() -> assertThat("Debería ganar piezas negras.", arbitro.consultarTurnoGanador(), is(Color.NEGRO)),
				() -> assertThat("No deberían estar todos los peones blancos sobre el tablero.",
						tc.consultarNumeroPiezas(TipoPieza.PEON, Color.BLANCO), is(3)),
				() -> assertThat("No deberían estar todos los peones negros sobre el tablero.",
						tc.consultarNumeroPiezas(TipoPieza.PEON, Color.NEGRO), is(3)),
				() -> assertThat("No debería estar la reina blanca sobre el tablero.",
						tc.consultarNumeroPiezas(TipoPieza.REINA, Color.BLANCO), is(0)),
				() -> assertThat("Debería estar la reina negra sobre el tablero.",
						tc.consultarNumeroPiezas(TipoPieza.REINA, Color.NEGRO), is(1)),

				() -> assertThat("La caja blanca no debería estar vacía.",
						arbitro.consultarCaja(Color.BLANCO).contarPiezas(), is(4)),
				() -> assertThat("La caja blanca no contiene las cuatro piezas esperadas.",
						Arrays.asList(arbitro.consultarCaja(Color.BLANCO).consultarPiezas()),
						containsInAnyOrder(new Pieza(TipoPieza.REINA, Color.BLANCO),
								new Pieza(TipoPieza.PEON, Color.BLANCO),
								new Pieza(TipoPieza.PEON, Color.BLANCO),
								new Pieza(TipoPieza.PEON, Color.BLANCO)						
						)),
				() -> assertThat("La caja negra no debería estar vacía.",
						arbitro.consultarCaja(Color.NEGRO).contarPiezas(), is(3)),
				() -> assertThat("La caja negra no contiene las tres piezas esperadas.",
						Arrays.asList(arbitro.consultarCaja(Color.NEGRO).consultarPiezas()),
						containsInAnyOrder(
								new Pieza(TipoPieza.PEON, Color.NEGRO),
								new Pieza(TipoPieza.PEON, Color.NEGRO),
								new Pieza(TipoPieza.PEON, Color.NEGRO)						
						)),

				() -> assertThat("La partida se desarrolla en un número erróneo de jugadas.",
						arbitro.consultarNumeroJugada(), is(4)),
				() -> assertThat("El turno debería ser del jugador con negras.", arbitro.consultarTurno(),
						is(Color.NEGRO)),
				() -> assertEquals(cadenaEsperada, cadenaObtenida,
						"Estado final del tablero en formato texto incorrecto"));
	}
	
	
	/**
	 * Comprueba una partida con victoria de blancas expulsando a reina negra.
	 */
	@Test
	@DisplayName("Comprueba que hay victoria de blancas expulsando a reina negra.")
	void probarPartidaConVictoriaDeBlancasExpulsandoAReinaNegra() {

		// given
		arbitro.empujar(fabricarJugada(tablero, 3, 0, 3, 4)); // blancas
		arbitro.cambiarTurno();
		arbitro.empujar(fabricarJugada(tablero, 6, 6, 6, 2)); // negras
		arbitro.cambiarTurno();
		arbitro.empujar(fabricarJugada(tablero, 0, 0, 4, 0)); // blancas
		arbitro.cambiarTurno();
		arbitro.empujar(fabricarJugada(tablero, 6, 2, 6, 0)); // negras
		arbitro.cambiarTurno();

		// @formatter:off
		/* El estado del tablero antes del último movimiento debería ser:
		 * 0 -- PB PB PB -- -- --  
		 * 1 -- -- -- -- -- -- --
		 * 2 -- -- -- -- -- -- -- 
		 * 3 -- -- -- -- PB -- PN 
		 * 4 RB -- -- -- -- -- PN
		 * 5 PN -- -- -- -- -- PN 
		 * 6 RN -- -- -- -- -- --
		 *   0  1  2  3  4  5  6   
		 */
		 // @formatter:on

		// when
		arbitro.empujar(fabricarJugada(tablero, 4, 0, 6, 0)); // reina blanca expulsa reina negra

		// then
		final String cadenaEsperada = """
				0 -- PB PB PB -- -- --  
				1 -- -- -- -- -- -- --
				2 -- -- -- -- -- -- -- 
				3 -- -- -- -- PB -- PN 
				4 -- -- -- -- -- -- PN
				5 -- -- -- -- -- -- PN 
				6 RB -- -- -- -- -- --
				  0  1  2  3  4  5  6  """.replaceAll("\\s", "");
		String cadenaObtenida = arbitro.consultarTablero().aTexto().replaceAll("\\s", "");
		TableroConsultor tc = new TableroConsultor(arbitro.consultarTablero());
		assertAll("Victoria de blancas expulsando reina negra.",
				() -> assertThat("La partida no está finalizada.", arbitro.estaFinalizadaPartida(), is(true)),

				() -> assertThat("Debería ganar piezas blancas.", arbitro.consultarTurnoGanador(), is(Color.BLANCO)),
				() -> assertThat("No deberían estar todos los peones blancos sobre el tablero.",
						tc.consultarNumeroPiezas(TipoPieza.PEON, Color.BLANCO), is(4)),
				() -> assertThat("No deberían estar todos los peones negros sobre el tablero.",
						tc.consultarNumeroPiezas(TipoPieza.PEON, Color.NEGRO), is(3)),
				() -> assertThat("Debería estar la reina blanca sobre el tablero.",
						tc.consultarNumeroPiezas(TipoPieza.REINA, Color.BLANCO), is(1)),
				() -> assertThat("No debería estar la reina negra sobre el tablero.",
						tc.consultarNumeroPiezas(TipoPieza.REINA, Color.NEGRO), is(0)),

				() -> assertThat("La caja blanca no debería estar vacía.",
						arbitro.consultarCaja(Color.BLANCO).contarPiezas(), is(2)),
				() -> assertThat("La caja blanca no contiene las dos piezas esperadas.",
						Arrays.asList(arbitro.consultarCaja(Color.BLANCO).consultarPiezas()),
						containsInAnyOrder(
								new Pieza(TipoPieza.PEON, Color.BLANCO),
								new Pieza(TipoPieza.PEON, Color.BLANCO)						
						)),
				() -> assertThat("La caja negra no debería estar vacía.",
						arbitro.consultarCaja(Color.NEGRO).contarPiezas(), is(4)),
				() -> assertThat("La caja negra no contiene las cuatro piezas esperadas.",
						Arrays.asList(arbitro.consultarCaja(Color.NEGRO).consultarPiezas()),
						containsInAnyOrder(
								new Pieza(TipoPieza.REINA, Color.NEGRO),
								new Pieza(TipoPieza.PEON, Color.NEGRO),
								new Pieza(TipoPieza.PEON, Color.NEGRO),
								new Pieza(TipoPieza.PEON, Color.NEGRO)						
						)),

				() -> assertThat("La partida se desarrolla en un número erróneo de jugadas.",
						arbitro.consultarNumeroJugada(), is(5)),
				() -> assertThat("El turno debería ser del jugador con blancas.", arbitro.consultarTurno(),
						is(Color.BLANCO)),
				() -> assertEquals(cadenaEsperada, cadenaObtenida,
						"Estado final del tablero en formato texto incorrecto"));
	}


}
