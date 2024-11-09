package noventagrados.control.medio;

import static noventagrados.control.TestUtil.fabricarJugada;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Timeout.ThreadMode.SEPARATE_THREAD;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import noventagrados.control.Arbitro;
import noventagrados.control.TableroConsultor;
import noventagrados.modelo.Jugada;
import noventagrados.modelo.Pieza;
import noventagrados.modelo.Tablero;
import noventagrados.util.Color;
import noventagrados.util.Coordenada;
import noventagrados.util.TipoPieza;

/**
 * Comprobación de los movimientos de piezas sobre el tablero empujando las
 * piezas.
 * 
 * @author <a href="rmartico@ubu.es">Raúl Marticorena</a>
 */
@DisplayName("Tests del Arbitro sobre el empuje de piezas.")
@Timeout(value = 2, unit = TimeUnit.SECONDS, threadMode = SEPARATE_THREAD) // Time out global para todos los tests salvo los de ciclo de vida
public class ArbitroEmpujarPiezasTest {

	/** Texto de partida acabada. */
	private static final String LA_PARTIDA_NO_DEBERÍA_ESTAR_ACABADADA = "La partida no debería estar acabadada";

	/** Texto de número de peones blancos no correcto. */
	private static final String EL_NÚMERO_DE_PEONES_BLANCOS_SOBRE_EL_TABLERO_NO_ES_CORRECTO = "El número de peones blancos sobre el tablero no es correcto";

	/** Texto de número de peones negros no correcto. */
	private static final String EL_NÚMERO_DE_PEONES_NEGROS_SOBRE_EL_TABLERO_NO_ES_CORRECTO = "El número de peones negros sobre el tablero no es correcto";

	/** Texto turno no debería cambiar. */
	private static final String EL_TURNO_NO_DEBERÍA_CAMBIAR = "El turno no debería cambiar.";

	/** Texto de número de jugadas incorrectas. */
	private static final String EL_NÚMERO_DE_JUGADAS_ES_INCORRECTO = "El número de jugadas es incorrecto.";

	/** Texto de color ganador incorrecto. */
	private static final String EL_COLOR_DEL_GANADOR_ES_INCORRECTO = "El color del ganador es incorrecto.";
	
	/** Texto de movimiento incorrecto. */
	private static final String PIEZA_DEBERIA_HABERSE_MOVIDO_A_COORDENADA ="La pieza debería haberse movido en coordenada ";

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
	 * Comprobacion de empujar reina blanca en horizontal echando un peón blanco del tablero.
	 * 
	 * @see noventagrados.control.TestUtil#fabricarJugada
	 */
	@DisplayName("Comprueba el empuje de la reina blanca en horizontal.")
	@Test
	void comprobarEmpujeDeReinaBlancaEnHorizontal() {
		Jugada jugada1 = fabricarJugada(tablero, 0, 0, 0, 4);
		arbitro.empujar(jugada1);
		TableroConsultor tc = new TableroConsultor(arbitro.consultarTablero());
		// @formatter:off
		/* 
		 * <pre>  
		 * 	0 -- -- -- -- RB PB PB
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
		assertAll("empujar con reina blanca en horizontal",
				() -> assertThat(PIEZA_DEBERIA_HABERSE_MOVIDO_A_COORDENADA + "(0,0)",
						arbitro.consultarTablero().consultarCelda(new Coordenada(0, 0)).estaVacia(), is(true)),
				() -> assertThat(PIEZA_DEBERIA_HABERSE_MOVIDO_A_COORDENADA + "(0,1)",
						arbitro.consultarTablero().consultarCelda(new Coordenada(0, 1)).estaVacia(), is(true)),
				() -> assertThat(PIEZA_DEBERIA_HABERSE_MOVIDO_A_COORDENADA + "(0,2)",
						arbitro.consultarTablero().consultarCelda(new Coordenada(0, 2)).estaVacia(), is(true)),
				() -> assertThat(PIEZA_DEBERIA_HABERSE_MOVIDO_A_COORDENADA + "(0,3)",
						arbitro.consultarTablero().consultarCelda(new Coordenada(0, 2)).estaVacia(), is(true)),
				() -> assertThat("La reina debería estar en (0,4)",
						arbitro.consultarTablero().consultarCelda(new Coordenada(0, 4)).consultarPieza().consultarTipoPieza(), is(TipoPieza.REINA)),
				() -> assertThat("La reina en (0,4) tiene un color incorrecto",
						arbitro.consultarTablero().consultarCelda(new Coordenada(0, 4)).consultarPieza().consultarColor(), is(Color.BLANCO)),
				() -> assertThat("El siguiente peon blanco debería desplazarse a (0,5)",
						arbitro.consultarTablero().consultarCelda(new Coordenada(0, 5)).estaVacia(), is(false)),
				() -> assertThat("El siguiente peon blanco debería desplazarse a (0,6)",
						arbitro.consultarTablero().consultarCelda(new Coordenada(0, 6)).estaVacia(), is(false)),
				() -> assertThat("La caja blanca debería tener un peón",
						arbitro.consultarCaja(Color.BLANCO).contarPiezas(), is(1)),
				() -> assertThat("La caja blanca no contiene el peón esperado.", Arrays.asList(arbitro.consultarCaja(Color.BLANCO).consultarPiezas()), containsInAnyOrder(
						new Pieza(TipoPieza.PEON, Color.BLANCO))),
				() -> assertThat(EL_NÚMERO_DE_PEONES_BLANCOS_SOBRE_EL_TABLERO_NO_ES_CORRECTO,
						tc.consultarNumeroPiezas(TipoPieza.PEON, Color.BLANCO), is(5)),	
				() -> assertThat(EL_NÚMERO_DE_PEONES_NEGROS_SOBRE_EL_TABLERO_NO_ES_CORRECTO,
						tc.consultarNumeroPiezas(TipoPieza.PEON, Color.NEGRO), is(6)),	
				() -> assertNull(arbitro.consultarTurnoGanador(),LA_PARTIDA_NO_DEBERÍA_ESTAR_ACABADADA),
				() -> assertThat(EL_TURNO_NO_DEBERÍA_CAMBIAR, arbitro.consultarTurno(), is(Color.BLANCO)),
				() -> assertThat(EL_NÚMERO_DE_JUGADAS_ES_INCORRECTO, arbitro.consultarNumeroJugada(), is(1)));
	}
	
	/**
	 * Comprobacion de empujar reina negra en horizontal echando un peón negro del tablero.
	 * 
	 * @see noventagrados.control.TestUtil#fabricarJugada
	 */
	@DisplayName("Comprueba el empuje de la reina negra en horizontal.")
	@Test
	void comprobarEmpujeDeReinaNegraEnHorizontal() {
		Jugada jugada1 = fabricarJugada(tablero, 6, 6, 6, 2);
		arbitro.empujar(jugada1); // no es necesario cambiar turno, no comprobamos legalidad
		TableroConsultor tc = new TableroConsultor(arbitro.consultarTablero());
		// @formatter:off
		/* 
		 * <pre>  
		 * 	0 RB PB PB PB -- -- --
		 *	1 PB -- -- -- -- -- --
		 *	2 PB -- -- -- -- -- --
		 *	3 PB -- -- -- -- -- PN
		 *	4 -- -- -- -- -- -- PN
		 *  5 -- -- -- -- -- -- PN
		 *	6 PN PN RN -- -- -- --
		 *    0  1  2  3  4  5  6
		 * </pre>  
		 */
		 // @formatter:on
		assertAll("empujar con reina negra en horizontal",
				() -> assertThat(PIEZA_DEBERIA_HABERSE_MOVIDO_A_COORDENADA + "(6,6)",
						arbitro.consultarTablero().consultarCelda(new Coordenada(6, 6)).estaVacia(), is(true)),
				() -> assertThat(PIEZA_DEBERIA_HABERSE_MOVIDO_A_COORDENADA + "(6,5)",
						arbitro.consultarTablero().consultarCelda(new Coordenada(6, 5)).estaVacia(), is(true)),
				() -> assertThat(PIEZA_DEBERIA_HABERSE_MOVIDO_A_COORDENADA + "(6,4)",
						arbitro.consultarTablero().consultarCelda(new Coordenada(6, 4)).estaVacia(), is(true)),
				() -> assertThat(PIEZA_DEBERIA_HABERSE_MOVIDO_A_COORDENADA + "(6,3)",
						arbitro.consultarTablero().consultarCelda(new Coordenada(6, 3)).estaVacia(), is(true)),
				() -> assertThat("La reina debería estar en (6,2)",
						arbitro.consultarTablero().consultarCelda(new Coordenada(6, 2)).consultarPieza().consultarTipoPieza(), is(TipoPieza.REINA)),
				() -> assertThat("La reina en (6,2) tiene un color incorrecto",
						arbitro.consultarTablero().consultarCelda(new Coordenada(6, 2)).consultarPieza().consultarColor(), is(Color.NEGRO)),
				() -> assertThat("El siguiente peon negro debería desplazarse a (6,1)",
						arbitro.consultarTablero().consultarCelda(new Coordenada(6, 1)).estaVacia(), is(false)),
				() -> assertThat("El siguiente peon negro debería desplazarse a (6,0)",
						arbitro.consultarTablero().consultarCelda(new Coordenada(6, 0)).estaVacia(), is(false)),
				() -> assertThat("La caja negra debería tener un peón",
						arbitro.consultarCaja(Color.NEGRO).contarPiezas(), is(1)),
				() -> assertThat("La caja negra no contiene el peón esperado.", Arrays.asList(arbitro.consultarCaja(Color.NEGRO).consultarPiezas()), containsInAnyOrder(
						new Pieza(TipoPieza.PEON, Color.NEGRO))),
				() -> assertThat(EL_NÚMERO_DE_PEONES_NEGROS_SOBRE_EL_TABLERO_NO_ES_CORRECTO,
						tc.consultarNumeroPiezas(TipoPieza.PEON, Color.NEGRO), is(5)),
				() -> assertThat(EL_NÚMERO_DE_PEONES_BLANCOS_SOBRE_EL_TABLERO_NO_ES_CORRECTO,
						tc.consultarNumeroPiezas(TipoPieza.PEON, Color.BLANCO), is(6)),
				() -> assertNull(arbitro.consultarTurnoGanador(),LA_PARTIDA_NO_DEBERÍA_ESTAR_ACABADADA),
				() -> assertThat(EL_TURNO_NO_DEBERÍA_CAMBIAR, arbitro.consultarTurno(), is(Color.BLANCO)),
				() -> assertThat(EL_NÚMERO_DE_JUGADAS_ES_INCORRECTO, arbitro.consultarNumeroJugada(), is(1)));
	}
	
	
	
	/**
	 * Comprobacion de empujar peon blanco en horizontal echando a la reina blanca.
	 * 
	 * @see noventagrados.control.TestUtil#fabricarJugada
	 */
	@DisplayName("Comprueba el empuje del peón blanco de su propia reina blanca en horizontal.")
	@Test
	void comprobarEmpujeDePeonAReinaBlancaEnHorizontal() {
		Jugada jugada1 = fabricarJugada(tablero, 0, 3, 0, 1);
		arbitro.empujar(jugada1);
		TableroConsultor tc = new TableroConsultor(arbitro.consultarTablero());
		// @formatter:off
		/* 
		 * <pre>  
		 * 	0 PB PB -- -- -- -- --
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
		
		
		assertAll("empujar con peón blanco en horizontal",
				() -> assertThat(PIEZA_DEBERIA_HABERSE_MOVIDO_A_COORDENADA + "(0,3)",
						arbitro.consultarTablero().consultarCelda(new Coordenada(0, 3)).estaVacia(), is(true)),
				() -> assertThat(PIEZA_DEBERIA_HABERSE_MOVIDO_A_COORDENADA + "(0,2)",
						arbitro.consultarTablero().consultarCelda(new Coordenada(0, 2)).estaVacia(), is(true)),
				() -> assertThat(PIEZA_DEBERIA_HABERSE_MOVIDO_A_COORDENADA + "(0,1)",
						arbitro.consultarTablero().consultarCelda(new Coordenada(0, 1)).estaVacia(), is(false)),
				() -> assertThat(PIEZA_DEBERIA_HABERSE_MOVIDO_A_COORDENADA + "(0,0)",
						arbitro.consultarTablero().consultarCelda(new Coordenada(0, 0)).estaVacia(), is(false)),

				() -> assertThat("La caja blanca debería tener un peón y una reina",
						arbitro.consultarCaja(Color.BLANCO).contarPiezas(), is(2)),
				
				() -> assertThat("La caja blanca no contiene las dos piezas esperadas.", Arrays.asList(arbitro.consultarCaja(Color.BLANCO).consultarPiezas()), containsInAnyOrder(
						new Pieza(TipoPieza.REINA,Color.BLANCO), new Pieza(TipoPieza.PEON, Color.BLANCO))),
				
				
				() -> assertThat(EL_NÚMERO_DE_PEONES_BLANCOS_SOBRE_EL_TABLERO_NO_ES_CORRECTO,
						tc.consultarNumeroPiezas(TipoPieza.PEON, Color.BLANCO), is(5)),	
				() -> assertThat("El número de reina blanca sobre el tablero no es correcto",
						tc.consultarNumeroPiezas(TipoPieza.REINA, Color.BLANCO), is(0)),	
				() -> assertThat(EL_NÚMERO_DE_PEONES_NEGROS_SOBRE_EL_TABLERO_NO_ES_CORRECTO,
						tc.consultarNumeroPiezas(TipoPieza.PEON, Color.NEGRO), is(6)),	
				() -> assertThat(EL_COLOR_DEL_GANADOR_ES_INCORRECTO, arbitro.consultarTurnoGanador(),is(Color.NEGRO)),
				() -> assertThat(EL_TURNO_NO_DEBERÍA_CAMBIAR, arbitro.consultarTurno(), is(Color.BLANCO)),
				() -> assertThat(EL_NÚMERO_DE_JUGADAS_ES_INCORRECTO, arbitro.consultarNumeroJugada(), is(1)));
	}
	
	
	/**
	 * Comprobacion de empujar peon negro en horizontal echando a la reina negra.
	 * 
	 * @see noventagrados.control.TestUtil#fabricarJugada
	 */
	@DisplayName("Comprueba el empuje del peón negro de su propia reina negra en horizontal.")
	@Test
	void comprobarEmpujeDePeonAReinaNegraEnHorizontal() {
		Jugada jugada1 = fabricarJugada(tablero, 6, 3, 6, 5);
		arbitro.empujar(jugada1);
		TableroConsultor tc = new TableroConsultor(arbitro.consultarTablero());
		// @formatter:off
		/* 
		 * <pre>  
		 * 	0 RB PB PB PB -- -- --
		 *	1 PB -- -- -- -- -- --
		 *	2 PB -- -- -- -- -- --
		 *	3 PB -- -- -- -- -- PN
		 *	4 -- -- -- -- -- -- PN
		 *  5 -- -- -- -- -- -- PN
		 *	6 -- -- -- -- -- PN PN
		 *    0  1  2  3  4  5  6
		 * </pre>  
		 */
		 // @formatter:on
		
		
		assertAll("empujar con peón negro en horizontal",
				() -> assertThat(PIEZA_DEBERIA_HABERSE_MOVIDO_A_COORDENADA + "(6,3)",
						arbitro.consultarTablero().consultarCelda(new Coordenada(6, 3)).estaVacia(), is(true)),
				() -> assertThat(PIEZA_DEBERIA_HABERSE_MOVIDO_A_COORDENADA + "(6,4)",
						arbitro.consultarTablero().consultarCelda(new Coordenada(6, 4)).estaVacia(), is(true)),
				() -> assertThat(PIEZA_DEBERIA_HABERSE_MOVIDO_A_COORDENADA + "(6,5)",
						arbitro.consultarTablero().consultarCelda(new Coordenada(6, 5)).estaVacia(), is(false)),
				() -> assertThat(PIEZA_DEBERIA_HABERSE_MOVIDO_A_COORDENADA + "(6,6)",
						arbitro.consultarTablero().consultarCelda(new Coordenada(6, 6)).estaVacia(), is(false)),

				() -> assertThat("La caja negra debería tener un peón y una reina",
						arbitro.consultarCaja(Color.NEGRO).contarPiezas(), is(2)),
				
				() -> assertThat("La caja negra no contiene las dos piezas esperadas.", Arrays.asList(arbitro.consultarCaja(Color.NEGRO).consultarPiezas()), containsInAnyOrder(
						new Pieza(TipoPieza.REINA,Color.NEGRO), new Pieza(TipoPieza.PEON, Color.NEGRO))),
				
				
				() -> assertThat(EL_NÚMERO_DE_PEONES_NEGROS_SOBRE_EL_TABLERO_NO_ES_CORRECTO,
						tc.consultarNumeroPiezas(TipoPieza.PEON, Color.NEGRO), is(5)),	
				() -> assertThat("El número de reina negra sobre el tablero no es correcto",
						tc.consultarNumeroPiezas(TipoPieza.REINA, Color.NEGRO), is(0)),	
				() -> assertThat(EL_NÚMERO_DE_PEONES_NEGROS_SOBRE_EL_TABLERO_NO_ES_CORRECTO,
						tc.consultarNumeroPiezas(TipoPieza.PEON, Color.BLANCO), is(6)),	
				() -> assertThat(EL_COLOR_DEL_GANADOR_ES_INCORRECTO, arbitro.consultarTurnoGanador(),is(Color.BLANCO)),
				() -> assertThat(EL_TURNO_NO_DEBERÍA_CAMBIAR, arbitro.consultarTurno(), is(Color.BLANCO)),
				() -> assertThat(EL_NÚMERO_DE_JUGADAS_ES_INCORRECTO, arbitro.consultarNumeroJugada(), is(1)));
	}
	
	
	/**
	 * Comprobacion de empujar reina blanca en vertical echando un peón blanco del tablero.
	 * 
	 * @see noventagrados.control.TestUtil#fabricarJugada
	 */
	@DisplayName("Comprueba el empuje de la reina blanca en vertical.")
	@Test
	void comprobarEmpujeDeReinaBlancaEnVertical() {
		Jugada jugada1 = fabricarJugada(tablero, 0, 0, 4, 0);
		arbitro.empujar(jugada1);
		TableroConsultor tc = new TableroConsultor(arbitro.consultarTablero());
		// @formatter:off
		/* 
		 * <pre>  
		 * 	0 -- PB PB -- -- -- -- 
		 *	1 -- -- -- -- -- -- --
		 *	2 -- -- -- -- -- -- --
		 *	3 -- -- -- -- -- -- PN
		 *	4 RB -- -- -- -- -- PN
		 *  5 PB -- -- -- -- -- PN
		 *	6 PB -- -- PN PN PN RN
		 *    0  1  2  3  4  5  6
		 * </pre>  
		 */
		 // @formatter:on
		assertAll("empujar con reina blanca en vertical",
				() -> assertThat("La pieza debería haberse movido en coordenada (0,0)",
						arbitro.consultarTablero().consultarCelda(new Coordenada(0, 0)).estaVacia(), is(true)),
				() -> assertThat("La pieza debería haberse movido en coordenada (1,0)",
						arbitro.consultarTablero().consultarCelda(new Coordenada(1, 0)).estaVacia(), is(true)),
				() -> assertThat("La pieza debería haberse movido en coordenada (2,0)",
						arbitro.consultarTablero().consultarCelda(new Coordenada(2, 0)).estaVacia(), is(true)),
				() -> assertThat("La pieza debería haberse movido en coordenada (3,0)",
						arbitro.consultarTablero().consultarCelda(new Coordenada(3, 0)).estaVacia(), is(true)),
				() -> assertThat("La reina debería estar en (4,0)",
						arbitro.consultarTablero().consultarCelda(new Coordenada(4, 0)).consultarPieza().consultarTipoPieza(), is(TipoPieza.REINA)),
				() -> assertThat("La reina en (4,0) tiene un color incorrecto",
						arbitro.consultarTablero().consultarCelda(new Coordenada(4, 0)).consultarPieza().consultarColor(), is(Color.BLANCO)),
				() -> assertThat("El siguiente peon blanco debería desplazarse a (5,0)",
						arbitro.consultarTablero().consultarCelda(new Coordenada(5,0)).estaVacia(), is(false)),
				() -> assertThat("El siguiente peon blanco debería desplazarse a (6,0)",
						arbitro.consultarTablero().consultarCelda(new Coordenada(6, 0)).estaVacia(), is(false)),
				() -> assertThat("La caja blanca debería tener un peón",
						arbitro.consultarCaja(Color.BLANCO).contarPiezas(), is(1)),
				() -> assertThat("La caja blanca no contiene el peón esperado.", Arrays.asList(arbitro.consultarCaja(Color.BLANCO).consultarPiezas()), containsInAnyOrder(
						new Pieza(TipoPieza.PEON, Color.BLANCO))),
				() -> assertThat(EL_NÚMERO_DE_PEONES_BLANCOS_SOBRE_EL_TABLERO_NO_ES_CORRECTO,
						tc.consultarNumeroPiezas(TipoPieza.PEON, Color.BLANCO), is(5)),	
				() -> assertThat(EL_NÚMERO_DE_PEONES_NEGROS_SOBRE_EL_TABLERO_NO_ES_CORRECTO,
						tc.consultarNumeroPiezas(TipoPieza.PEON, Color.NEGRO), is(6)),	
				
				() -> assertThat("La partida no debería estar finalizada.", arbitro.estaFinalizadaPartida(), is(false)),

				() -> assertNull(arbitro.consultarTurnoGanador(),LA_PARTIDA_NO_DEBERÍA_ESTAR_ACABADADA),
				() -> assertThat(EL_TURNO_NO_DEBERÍA_CAMBIAR, arbitro.consultarTurno(), is(Color.BLANCO)),
				() -> assertThat(EL_NÚMERO_DE_JUGADAS_ES_INCORRECTO, arbitro.consultarNumeroJugada(), is(1)));
	}

	
	/**
	 * Comprobacion de empujar reina negra en vertical echando un peón negro del tablero.
	 * 
	 * @see noventagrados.control.TestUtil#fabricarJugada
	 */
	@DisplayName("Comprueba el empuje de la reina negra en vertical.")
	@Test
	void comprobarEmpujeDeReinaNegraEnVertical() {
		Jugada jugada1 = fabricarJugada(tablero, 6, 6, 2, 6);
		arbitro.empujar(jugada1); // no es necesario cambiar turno, no comprobamos legalidad
		TableroConsultor tc = new TableroConsultor(arbitro.consultarTablero());
		// @formatter:off
		/* 
		 * <pre>  
		 * 	0 RB PB PB PB -- -- PN
		 *	1 PB -- -- -- -- -- PN
		 *	2 PB -- -- -- -- -- RN
		 *	3 PB -- -- -- -- -- --
		 *	4 -- -- -- -- -- -- --
		 *  5 -- -- -- -- -- -- --
		 *	6 -- -- -- PN PN PN --
		 *    0  1  2  3  4  5  6
		 * </pre>  
		 */
		 // @formatter:on
		assertAll("empujar con reina negra en vertical",
				() -> assertThat("La pieza debería haberse movido en coordenada (6,6)",
						arbitro.consultarTablero().consultarCelda(new Coordenada(6, 6)).estaVacia(), is(true)),
				() -> assertThat("La pieza debería haberse movido en coordenada (5,6)",
						arbitro.consultarTablero().consultarCelda(new Coordenada(5, 6)).estaVacia(), is(true)),
				() -> assertThat("La pieza debería haberse movido en coordenada (4,6)",
						arbitro.consultarTablero().consultarCelda(new Coordenada(4, 6)).estaVacia(), is(true)),
				() -> assertThat("La pieza debería haberse movido en coordenada (3,6)",
						arbitro.consultarTablero().consultarCelda(new Coordenada(3, 6)).estaVacia(), is(true)),
				() -> assertThat("La reina debería estar en (2,6)",
						arbitro.consultarTablero().consultarCelda(new Coordenada(2, 6)).consultarPieza().consultarTipoPieza(), is(TipoPieza.REINA)),
				() -> assertThat("La reina en (2,6) tiene un color incorrecto",
						arbitro.consultarTablero().consultarCelda(new Coordenada(2, 6)).consultarPieza().consultarColor(), is(Color.NEGRO)),
				() -> assertThat("El siguiente peon negro debería desplazarse a (1,6)",
						arbitro.consultarTablero().consultarCelda(new Coordenada(1, 6)).estaVacia(), is(false)),
				() -> assertThat("El siguiente peon negro debería desplazarse a (0,6)",
						arbitro.consultarTablero().consultarCelda(new Coordenada(0, 6)).estaVacia(), is(false)),
				() -> assertThat("La caja negra debería tener un peón",
						arbitro.consultarCaja(Color.NEGRO).contarPiezas(), is(1)),
				() -> assertThat("La caja negra no contiene el peón esperado.", Arrays.asList(arbitro.consultarCaja(Color.NEGRO).consultarPiezas()), containsInAnyOrder(
						new Pieza(TipoPieza.PEON, Color.NEGRO))),
				() -> assertThat(EL_NÚMERO_DE_PEONES_NEGROS_SOBRE_EL_TABLERO_NO_ES_CORRECTO,
						tc.consultarNumeroPiezas(TipoPieza.PEON, Color.NEGRO), is(5)),
				() -> assertThat(EL_NÚMERO_DE_PEONES_BLANCOS_SOBRE_EL_TABLERO_NO_ES_CORRECTO,
						tc.consultarNumeroPiezas(TipoPieza.PEON, Color.BLANCO), is(6)),
				
				() -> assertThat("La partida no debería estar finalizada.", arbitro.estaFinalizadaPartida(), is(false)),

				() -> assertNull(arbitro.consultarTurnoGanador(),LA_PARTIDA_NO_DEBERÍA_ESTAR_ACABADADA),
				() -> assertThat(EL_TURNO_NO_DEBERÍA_CAMBIAR, arbitro.consultarTurno(), is(Color.BLANCO)),
				() -> assertThat(EL_NÚMERO_DE_JUGADAS_ES_INCORRECTO, arbitro.consultarNumeroJugada(), is(1)));
	}
	
	
	/**
	 * Comprobacion de empujar peon blanco en vertical echando a la reina blanca.
	 * 
	 * @see noventagrados.control.TestUtil#fabricarJugada
	 */
	@DisplayName("Comprueba el empuje del peón blanco de su propia reina blanca en horizontal.")
	@Test
	void comprobarEmpujeDePeonAReinaBlancaEnVertical() {
		Jugada jugada1 = fabricarJugada(tablero, 3, 0, 1, 0);
		arbitro.empujar(jugada1);
		TableroConsultor tc = new TableroConsultor(arbitro.consultarTablero());
		// @formatter:off
		/* 
		 * <pre>  
		 * 	0 PB PB PB PB -- -- --
		 *	1 PB -- -- -- -- -- --
		 *	2 -- -- -- -- -- -- --
		 *	3 -- -- -- -- -- -- PN
		 *	4 -- -- -- -- -- -- PN
		 *  5 -- -- -- -- -- -- PN
		 *	6 -- -- -- PN PN PN RN
		 *    0  1  2  3  4  5  6
		 * </pre>  
		 */
		 // @formatter:on
		
		
		assertAll("empujar con peón blanco en vertical",
				() -> assertThat("La pieza debería haberse movido en coordenada (3,0)",
						arbitro.consultarTablero().consultarCelda(new Coordenada(3, 0)).estaVacia(), is(true)),
				() -> assertThat("La pieza debería haberse movido en coordenada (2,0)",
						arbitro.consultarTablero().consultarCelda(new Coordenada(2, 0)).estaVacia(), is(true)),
				() -> assertThat("La pieza debería haberse movido en coordenada (1,0)",
						arbitro.consultarTablero().consultarCelda(new Coordenada(1, 0)).estaVacia(), is(false)),
				() -> assertThat("La pieza debería haberse movido en coordenada (0,0)",
						arbitro.consultarTablero().consultarCelda(new Coordenada(0, 0)).estaVacia(), is(false)),

				() -> assertThat("La caja blanca debería tener un peón y una reina",
						arbitro.consultarCaja(Color.BLANCO).contarPiezas(), is(2)),
				
				() -> assertThat("La caja blanca no contiene las dos piezas esperadas.", Arrays.asList(arbitro.consultarCaja(Color.BLANCO).consultarPiezas()), containsInAnyOrder(
						new Pieza(TipoPieza.REINA,Color.BLANCO), new Pieza(TipoPieza.PEON, Color.BLANCO))),
				
				
				() -> assertThat(EL_NÚMERO_DE_PEONES_BLANCOS_SOBRE_EL_TABLERO_NO_ES_CORRECTO,
						tc.consultarNumeroPiezas(TipoPieza.PEON, Color.BLANCO), is(5)),	
				() -> assertThat("El número de reina blanca sobre el tablero no es correcto",
						tc.consultarNumeroPiezas(TipoPieza.REINA, Color.BLANCO), is(0)),	
				() -> assertThat(EL_NÚMERO_DE_PEONES_NEGROS_SOBRE_EL_TABLERO_NO_ES_CORRECTO,
						tc.consultarNumeroPiezas(TipoPieza.PEON, Color.NEGRO), is(6)),	
				
				() -> assertThat("La partida no está finalizada.", arbitro.estaFinalizadaPartida(), is(true)),

				() -> assertThat(EL_COLOR_DEL_GANADOR_ES_INCORRECTO, arbitro.consultarTurnoGanador(),is(Color.NEGRO)),
				() -> assertThat(EL_TURNO_NO_DEBERÍA_CAMBIAR, arbitro.consultarTurno(), is(Color.BLANCO)),
				() -> assertThat(EL_NÚMERO_DE_JUGADAS_ES_INCORRECTO, arbitro.consultarNumeroJugada(), is(1)));
	}
	
	/**
	 * Comprobacion de empujar peon negro en vertical echando a la reina negra.
	 * 
	 * @see noventagrados.control.TestUtil#fabricarJugada
	 */
	@DisplayName("Comprueba el empuje del peón negro de su propia reina negra en vertical.")
	@Test
	void comprobarEmpujeDePeonAReinaNegraEnVertical() {
		Jugada jugada1 = fabricarJugada(tablero, 3, 6, 5, 6);
		arbitro.empujar(jugada1);
		TableroConsultor tc = new TableroConsultor(arbitro.consultarTablero());
		// @formatter:off
		/* 
		 * <pre>  
		 * 	0 RB PB PB PB -- -- --
		 *	1 PB -- -- -- -- -- --
		 *	2 PB -- -- -- -- -- --
		 *	3 PB -- -- -- -- -- --
		 *	4 -- -- -- -- -- -- --
		 *  5 -- -- -- -- -- -- PN
		 *	6 -- -- -- PN PN PN PN
		 *    0  1  2  3  4  5  6
		 * </pre>  
		 */
		 // @formatter:on
		
		
		assertAll("empujar con peón negro en vertical",
				() -> assertThat(PIEZA_DEBERIA_HABERSE_MOVIDO_A_COORDENADA + "(3,6)",
						arbitro.consultarTablero().consultarCelda(new Coordenada(3, 6)).estaVacia(), is(true)),
				() -> assertThat(PIEZA_DEBERIA_HABERSE_MOVIDO_A_COORDENADA + "(4,6)",
						arbitro.consultarTablero().consultarCelda(new Coordenada(4, 6)).estaVacia(), is(true)),
				() -> assertThat(PIEZA_DEBERIA_HABERSE_MOVIDO_A_COORDENADA + "(5,6)",
						arbitro.consultarTablero().consultarCelda(new Coordenada(5, 6)).estaVacia(), is(false)),
				() -> assertThat(PIEZA_DEBERIA_HABERSE_MOVIDO_A_COORDENADA + "(6,6)",
						arbitro.consultarTablero().consultarCelda(new Coordenada(6, 6)).estaVacia(), is(false)),

				() -> assertThat("La caja negra debería tener un peón y una reina",
						arbitro.consultarCaja(Color.NEGRO).contarPiezas(), is(2)),
				
				() -> assertThat("La caja negra no contiene las dos piezas esperadas.", Arrays.asList(arbitro.consultarCaja(Color.NEGRO).consultarPiezas()), containsInAnyOrder(
						new Pieza(TipoPieza.REINA,Color.NEGRO), new Pieza(TipoPieza.PEON, Color.NEGRO))),
				
				
				() -> assertThat(EL_NÚMERO_DE_PEONES_NEGROS_SOBRE_EL_TABLERO_NO_ES_CORRECTO,
						tc.consultarNumeroPiezas(TipoPieza.PEON, Color.NEGRO), is(5)),	
				() -> assertThat("El número de reina negra sobre el tablero no es correcto",
						tc.consultarNumeroPiezas(TipoPieza.REINA, Color.NEGRO), is(0)),	
				() -> assertThat(EL_NÚMERO_DE_PEONES_NEGROS_SOBRE_EL_TABLERO_NO_ES_CORRECTO,
						tc.consultarNumeroPiezas(TipoPieza.PEON, Color.BLANCO), is(6)),	
				
				() -> assertThat("La partida no está finalizada.", arbitro.estaFinalizadaPartida(), is(true)),

				() -> assertThat(EL_COLOR_DEL_GANADOR_ES_INCORRECTO, arbitro.consultarTurnoGanador(),is(Color.BLANCO)),
				() -> assertThat(EL_TURNO_NO_DEBERÍA_CAMBIAR, arbitro.consultarTurno(), is(Color.BLANCO)),
				() -> assertThat(EL_NÚMERO_DE_JUGADAS_ES_INCORRECTO, arbitro.consultarNumeroJugada(), is(1)));
	}
	
}
