package noventagrados.control.avanzado;

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
import noventagrados.modelo.Jugada;
import noventagrados.modelo.Tablero;
import noventagrados.util.Color;
import noventagrados.util.TipoPieza;

/**
 * Partida simple con pocos movimientos y finalización con victoria. En este
 * caso sí se comprueba la legalidad de las jugadas.
 * 
 * Demuestra la posibilidad de jugar una partida mínima sin incluir la
 * funcionalidad de expulsar piezas fuera del tabblero.
 * 
 * Básicamente son los mismos tests de
 * noventagrados.control.medio.ArbitroPartidaSimpleTest añadiendo comprobaciones
 * de legalidad.
 * 
 * @author <a href="rmartico@ubu.es">Raúl Marticorena</a>
 * @since 1.0
 * @version 1.0
 */
@DisplayName("Tests del Arbitro sobre partidas simples con comprobación de legalidad.")
@Timeout(value = 2, unit = TimeUnit.SECONDS, threadMode = SEPARATE_THREAD) // Time out global para todos los tests salvo los de ciclo de vida
public class ArbitroPartidaConLegalidadTest {

	/** Texto de jugada legal. */
	private static final String LA_JUGADA_DEBERÍA_SER_LEGAL = "La jugada debería ser legal: ";

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
	 * Comprueba una partida simple con victoria de blancas en el centro sin
	 * expulsiones del tablero.
	 */
	@Test
	@DisplayName("Comprueba que hay victoria de blancas en el centro sin expulsar del tablero a ninguna pieza.")
	void probarPartidaSimpleConVictoriaDeBlancasEnElCentroSinExpulsarPiezas() {

		// given
		Jugada jugada = fabricarJugada(tablero, 0, 3, 4, 3);
		assertThat(LA_JUGADA_DEBERÍA_SER_LEGAL + jugada, arbitro.esMovimientoLegal(jugada), is(true));
		arbitro.empujar(jugada); // blancas
		arbitro.cambiarTurno();
		jugada = fabricarJugada(tablero, 6, 4, 2, 4);
		assertThat(LA_JUGADA_DEBERÍA_SER_LEGAL + jugada, arbitro.esMovimientoLegal(jugada), is(true));
		arbitro.empujar(jugada); // negras
		arbitro.cambiarTurno();
		jugada = fabricarJugada(tablero, 0, 0, 3, 0);
		assertThat(LA_JUGADA_DEBERÍA_SER_LEGAL + jugada, arbitro.esMovimientoLegal(jugada), is(true));
		arbitro.empujar(jugada); // blancas
		arbitro.cambiarTurno();
		jugada = fabricarJugada(tablero, 6, 5, 2, 5);
		assertThat(LA_JUGADA_DEBERÍA_SER_LEGAL + jugada, arbitro.esMovimientoLegal(jugada), is(true));
		arbitro.empujar(jugada); // negras
		arbitro.cambiarTurno();
		jugada = fabricarJugada(tablero, 5, 0, 5, 4);
		assertThat(LA_JUGADA_DEBERÍA_SER_LEGAL + jugada, arbitro.esMovimientoLegal(jugada), is(true));
		arbitro.empujar(jugada); // blancas
		arbitro.cambiarTurno();
		jugada = fabricarJugada(tablero, 2, 5, 0, 5);
		assertThat(LA_JUGADA_DEBERÍA_SER_LEGAL + jugada, arbitro.esMovimientoLegal(jugada), is(true));
		arbitro.empujar(jugada); // negras
		arbitro.cambiarTurno();

		// @formatter:off
		/* El estado del tablero antes del último movimiento debería ser:
		 * 0 -- PB PB -- -- PN --  
		 * 1 -- -- -- -- -- -- --
		 * 2 -- -- -- -- PN -- -- 
		 * 3 RB -- -- -- -- -- PN 
		 * 4 PB -- -- PB -- -- PN
		 * 5 -- -- -- -- PB -- PN 
		 * 6 PB -- -- PN -- -- RN
		 *   0  1  2  3  4  5  6   
		 */
		 // @formatter:on

		// when
		jugada = fabricarJugada(tablero, 3, 0, 3, 3);
		assertThat(LA_JUGADA_DEBERÍA_SER_LEGAL + jugada, arbitro.esMovimientoLegal(jugada), is(true));
		arbitro.empujar(jugada); // reina blanca alcanza el centro

		// then
		final String cadenaEsperada = """
				0 -- PB PB -- -- PN --
				1 -- -- -- -- -- -- --
				2 -- -- -- -- PN -- --
				3 -- -- -- RB -- -- PN
				4 PB -- -- PB -- -- PN
				5 -- -- -- -- PB -- PN
				6 PB -- -- PN -- -- RN
				  0  1  2  3  4  5  6  """.replaceAll("\\s", "");
		String cadenaObtenida = arbitro.consultarTablero().aTexto().replaceAll("\\s", "");
		TableroConsultor tc = new TableroConsultor(arbitro.consultarTablero());
		assertAll("Victoria de blancas alcanzando la posición central.",
				() -> assertThat("La partida no está finalizada.", arbitro.estaFinalizadaPartida(), is(true)),

				() -> assertThat("Debería ganar piezas blancas.", arbitro.consultarTurnoGanador(), is(Color.BLANCO)),
				() -> assertThat("Deberían estar todos los peones blancos sobre el tablero.",
						tc.consultarNumeroPiezas(TipoPieza.PEON, Color.BLANCO), is(6)),
				() -> assertThat("Deberían estar todos los peones negros sobre el tablero.",
						tc.consultarNumeroPiezas(TipoPieza.PEON, Color.NEGRO), is(6)),
				() -> assertThat("Debería estar la reina blanca sobre el tablero.",
						tc.consultarNumeroPiezas(TipoPieza.REINA, Color.BLANCO), is(1)),
				() -> assertThat("Debería estar la reina negra sobre el tablero.",
						tc.consultarNumeroPiezas(TipoPieza.REINA, Color.NEGRO), is(1)),
				() -> assertThat("La caja blanca debería estar vacía.",
						arbitro.consultarCaja(Color.BLANCO).contarPiezas(), is(0)),
				() -> assertThat("La caja negra debería estar vacía.",
						arbitro.consultarCaja(Color.NEGRO).contarPiezas(), is(0)),
				() -> assertThat("La partida se desarrolla en un número erróneo de jugadas.",
						arbitro.consultarNumeroJugada(), is(7)),
				() -> assertThat("El turno debería ser del jugador con blancas.", arbitro.consultarTurno(),
						is(Color.BLANCO)),
				() -> assertEquals(cadenaEsperada, cadenaObtenida,
						"Estado final del tablero en formato texto incorrecto"));
	}

	/**
	 * Comprueba una partida simple con victoria de negras en el centro sin
	 * expulsiones del tablero.
	 * 
	 * Algunos de los movimientos implican empujes más complejos con huecos entre
	 * piezas.
	 */
	@Test
	@DisplayName("Comprueba que hay victoria de negras en el centro sin expulsar del tablero a ninguna pieza.")
	void probarPartidaSimpleConVictoriaDeNegrasEnElCentroSinExpulsarPiezas() {

		// given
		Jugada jugada = fabricarJugada(tablero, 0, 1, 4, 1);
		assertThat(LA_JUGADA_DEBERÍA_SER_LEGAL + jugada, arbitro.esMovimientoLegal(jugada), is(true));
		arbitro.empujar(jugada); // blancas
		arbitro.cambiarTurno();
		jugada = fabricarJugada(tablero, 6, 4, 2, 4);
		assertThat(LA_JUGADA_DEBERÍA_SER_LEGAL + jugada, arbitro.esMovimientoLegal(jugada), is(true));
		arbitro.empujar(jugada); // negras
		arbitro.cambiarTurno();
		jugada = fabricarJugada(tablero, 3, 0, 3, 4);
		assertThat(LA_JUGADA_DEBERÍA_SER_LEGAL + jugada, arbitro.esMovimientoLegal(jugada), is(true));
		arbitro.empujar(jugada); // blancas
		arbitro.cambiarTurno();
		jugada = fabricarJugada(tablero, 3, 6, 3, 2);
		assertThat(LA_JUGADA_DEBERÍA_SER_LEGAL + jugada, arbitro.esMovimientoLegal(jugada), is(true));
		arbitro.empujar(jugada); // negras
		arbitro.cambiarTurno();
		jugada = fabricarJugada(tablero, 2, 0, 2, 3);
		assertThat(LA_JUGADA_DEBERÍA_SER_LEGAL + jugada, arbitro.esMovimientoLegal(jugada), is(true));
		arbitro.empujar(jugada); // blancas
		arbitro.cambiarTurno();
		jugada = fabricarJugada(tablero, 6, 6, 6, 3);
		assertThat(LA_JUGADA_DEBERÍA_SER_LEGAL + jugada, arbitro.esMovimientoLegal(jugada), is(true));
		arbitro.empujar(jugada); // negras
		arbitro.cambiarTurno();
		jugada = fabricarJugada(tablero, 4, 1, 4, 4);
		assertThat(LA_JUGADA_DEBERÍA_SER_LEGAL + jugada, arbitro.esMovimientoLegal(jugada), is(true));
		arbitro.empujar(jugada); // blancas
		arbitro.cambiarTurno();

		// @formatter:off
		/* El estado del tablero antes del último movimiento debería ser:
		 * 0 RB -- PB PB -- -- --  
		 * 1 PB -- -- -- -- -- --
		 * 2 -- -- -- PB PN -- -- 
		 * 3 -- PB PN -- -- -- -- 
		 * 4 -- -- -- -- PB -- PN
		 * 5 -- -- -- -- -- -- PN 
		 * 6 -- PN PN RN -- -- --
		 *   0  1  2  3  4  5  6   
		 */
		 // @formatter:on

		// when
		jugada = fabricarJugada(tablero, 6, 3, 3, 3);
		assertThat(LA_JUGADA_DEBERÍA_SER_LEGAL + jugada, arbitro.esMovimientoLegal(jugada), is(true));
		arbitro.empujar(jugada); // reina negra alcanza el centro
		// then
		final String cadenaEsperada = """
				0 RB -- PB PB -- -- --
				1 PB -- -- -- -- -- --
				2 -- -- -- PB PN -- --
				3 -- PB PN RN -- -- --
				4 -- -- -- -- PB -- PN
				5 -- -- -- -- -- -- PN
				6 -- PN PN -- -- -- --
				  0  1  2  3  4  5  6  """.replaceAll("\\s", "");
		String cadenaObtenida = arbitro.consultarTablero().aTexto().replaceAll("\\s", "");
		TableroConsultor tc = new TableroConsultor(arbitro.consultarTablero());
		assertAll("Victoria de negras alcanzando la posición central.",
				() -> assertThat("La partida no está finalizada.", arbitro.estaFinalizadaPartida(), is(true)),

				() -> assertThat("Debería ganar piezas negras.", arbitro.consultarTurnoGanador(), is(Color.NEGRO)),
				() -> assertThat("Deberían estar todos los peones blancos sobre el tablero.",
						tc.consultarNumeroPiezas(TipoPieza.PEON, Color.BLANCO), is(6)),
				() -> assertThat("Deberían estar todos los peones negros sobre el tablero.",
						tc.consultarNumeroPiezas(TipoPieza.PEON, Color.NEGRO), is(6)),
				() -> assertThat("Debería estar la reina blanca sobre el tablero.",
						tc.consultarNumeroPiezas(TipoPieza.REINA, Color.BLANCO), is(1)),
				() -> assertThat("Debería estar la reina negra sobre el tablero.",
						tc.consultarNumeroPiezas(TipoPieza.REINA, Color.NEGRO), is(1)),
				() -> assertThat("La caja blanca debería estar vacía.",
						arbitro.consultarCaja(Color.BLANCO).contarPiezas(), is(0)),
				() -> assertThat("La caja negra debería estar vacía.",
						arbitro.consultarCaja(Color.NEGRO).contarPiezas(), is(0)),
				() -> assertThat("La partida se desarrolla en un número erróneo de jugadas.",
						arbitro.consultarNumeroJugada(), is(8)),
				() -> assertThat("El turno debería ser del jugador con negras.", arbitro.consultarTurno(),
						is(Color.NEGRO)),
				() -> assertEquals(cadenaEsperada, cadenaObtenida,
						"Estado final del tablero en formato texto incorrecto"));
	}

}
