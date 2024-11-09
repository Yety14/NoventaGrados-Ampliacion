package noventagrados.control;

import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import noventagrados.modelo.Jugada;
import noventagrados.modelo.Pieza;
import noventagrados.modelo.Tablero;
import noventagrados.util.Color;
import noventagrados.util.Coordenada;
import noventagrados.util.TipoPieza;

/**
 * Clase de utilidad para generar jugadas en los tests.
 * 
 * @author <a href="rmartico@ubu.es">Raúl Marticorena</a>
 */
public class TestUtil {
	
	/** 
	 * Ocultamos constructor.
	 */
	private TestUtil() {}
	
	/**
	 * Fabrica una instancia de jugada a partir de las coordenadas de origen y destino.
	 * 
	 * @param tablero tablero
	 * @param filaOrigen fila origen
	 * @param columnaOrigen columna origen
	 * @param filaDestino fila destino
	 * @param columnaDestino columan destino
	 * @return jugada
	 */
	public static Jugada fabricarJugada(Tablero tablero, int filaOrigen, int columnaOrigen, int filaDestino, int columnaDestino) {
		return new  Jugada(
				tablero.consultarCelda(	new Coordenada(filaOrigen, columnaOrigen)), 
				tablero.consultarCelda(new Coordenada(filaDestino, columnaDestino)));
	}

	/**
	 * Proveedor de peones negros.
	 * 
	 * @return pieza y coordenada de peones negros
	 */
	static Stream<Arguments> piezaYCoordenadaDePeonesNegrosProvider() {
		return Stream.of(
				arguments(new Pieza(TipoPieza.PEON, Color.NEGRO), new Coordenada(3,6)),
				arguments(new Pieza(TipoPieza.PEON, Color.NEGRO), new Coordenada(4,6)), 
				arguments(new Pieza(TipoPieza.PEON, Color.NEGRO), new Coordenada(5,6)), 
				arguments(new Pieza(TipoPieza.PEON, Color.NEGRO), new Coordenada(6,3)), 
				arguments(new Pieza(TipoPieza.PEON, Color.NEGRO), new Coordenada(6,4)), 
				arguments(new Pieza(TipoPieza.PEON, Color.NEGRO), new Coordenada(6,5))); 
	}
	
	/**
	 * Proveedor de peones blancos.
	 * 
	 * @return pieza y coordenada de peones blancos
	 */
	static Stream<Arguments> piezaYCoordenadaDePeonesBlancosProvider() {
		return Stream.of(
				arguments(new Pieza(TipoPieza.PEON, Color.BLANCO), new Coordenada(0,1)),
				arguments(new Pieza(TipoPieza.PEON, Color.BLANCO), new Coordenada(0,2)), 
				arguments(new Pieza(TipoPieza.PEON, Color.BLANCO), new Coordenada(0,3)), 
				arguments(new Pieza(TipoPieza.PEON, Color.BLANCO), new Coordenada(1,0)), 
				arguments(new Pieza(TipoPieza.PEON, Color.BLANCO), new Coordenada(2,0)), 
				arguments(new Pieza(TipoPieza.PEON, Color.BLANCO), new Coordenada(3,0))); 
	}


	
}
