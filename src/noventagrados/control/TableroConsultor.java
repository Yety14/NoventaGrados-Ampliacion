package noventagrados.control;

import noventagrados.modelo.Tablero;
import noventagrados.modelo.Celda;
import noventagrados.util.Sentido;
import noventagrados.util.Coordenada;
import noventagrados.util.Color;
import noventagrados.util.TipoPieza;
import noventagrados.modelo.Pieza;

/**
 * El tablero consultor usado por el Arbitro
 * 
 * @author Víctor Vidal Vivanco
 * @author Guillermo López de Arechavaleta Zapatero
 * @version 1.0
 * @since 1.0
 */

public class TableroConsultor {

	/** El tablero que este consultor está observando. */
	private Tablero tablero;

	/**
	 * Constructor del TableroConsultor.
	 * 
	 * @param tablero El tablero a consultar
	 */
	public TableroConsultor(Tablero tablero) {
		this.tablero = tablero;
	}

	/**
	 * Calcula el sentido del movimiento entre dos coordenadas.
	 * 
	 * @param origen  Coordenada de origen
	 * @param destino Coordenada de destino
	 * @return El sentido del movimiento, o null si no es un movimiento válido
	 */
	public Sentido calcularSentido(Coordenada origen, Coordenada destino) {
		if (origen.fila() == destino.fila()) {
			return origen.columna() < destino.columna() ? Sentido.HORIZONTAL_E : Sentido.HORIZONTAL_O;
		} else if (origen.columna() == destino.columna()) {
			return origen.fila() < destino.fila() ? Sentido.VERTICAL_S : Sentido.VERTICAL_N;
		}
		return null;
	}

	/**
	 * Consulta la distancia horizontal entre dos coordenadas.
	 * 
	 * @param origen  Coordenada de origen
	 * @param destino Coordenada de destino
	 * @return La distancia horizontal, o -1 si no están en la misma fila
	 */
	public int consultarDistanciaEnHorizontal(Coordenada origen, Coordenada destino) {
		if (origen.fila() == destino.fila()) {
			return Math.abs(origen.columna() - destino.columna());
		}
		return -1;
	}

	/**
	 * Consulta la distancia vertical entre dos coordenadas.
	 * 
	 * @param origen  Coordenada de origen
	 * @param destino Coordenada de destino
	 * @return La distancia vertical, o -1 si no están en la misma columna
	 */
	public int consultarDistanciaEnVertical(Coordenada origen, Coordenada destino) {
		if (origen.columna() == destino.columna()) {
			return Math.abs(origen.fila() - destino.fila());
		}
		return -1;
	}

	/**
	 * Cuenta el número de piezas de un tipo y color específicos en el tablero.
	 * 
	 * @param tipoPieza El tipo de pieza a contar
	 * @param color     El color de las piezas a contar
	 * @return El número de piezas que cumplen los criterios
	 */
	public int consultarNumeroPiezas(TipoPieza tipoPieza, Color color) {
		int count = 0;
		for (int fila = 0; fila < tablero.consultarNumeroFilas(); fila++) {
			for (int columna = 0; columna < tablero.consultarNumeroColumnas(); columna++) {
				Celda celda = tablero.consultarCelda(new Coordenada(fila, columna));
				if (!celda.estaVacia()) {
					Pieza pieza = celda.consultarPieza();
					if (pieza.consultarTipoPieza() == tipoPieza && pieza.consultarColor() == color) {
						count++;
					}
				}
			}
		}
		return count;
	}

	/**
	 * Cuenta el número de piezas en la fila de la coordenada dada.
	 * 
	 * @param coordenada La coordenada de referencia
	 * @return El número de piezas en la fila
	 */
	public int consultarNumeroPiezasEnHorizontal(Coordenada coordenada) {
		int count = 0;
		int fila = coordenada.fila();
		for (int columna = 0; columna < tablero.consultarNumeroColumnas(); columna++) {
			Celda celda = tablero.consultarCelda(new Coordenada(fila, columna));
			if (!celda.estaVacia()) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Cuenta el número de piezas en la columna de la coordenada dada.
	 * 
	 * @param coordenada La coordenada de referencia
	 * @return El número de piezas en la columna
	 */
	public int consultarNumeroPiezasEnVertical(Coordenada coordenada) {
		int count = 0;
		int columna = coordenada.columna();
		for (int fila = 0; fila < tablero.consultarNumeroFilas(); fila++) {
			Celda celda = tablero.consultarCelda(new Coordenada(fila, columna));
			if (!celda.estaVacia()) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Verifica si la reina de un color específico está en el centro del tablero.
	 * 
	 * @param color El color de la reina a verificar
	 * @return true si la reina del color especificado está en el centro, false en
	 *         caso contrario
	 */
	public boolean estaReinaEnElCentro(Color color) {
		Celda celdaCentro = tablero.consultarCelda(
				new Coordenada(tablero.consultarNumeroFilas() / 2, tablero.consultarNumeroColumnas() / 2));
		if (!celdaCentro.estaVacia()) {
			return celdaCentro.consultarPieza().consultarColor() == color
					&& celdaCentro.consultarPieza().consultarTipoPieza() == TipoPieza.REINA;
		}
		return false;
	}

	/**
	 * Verifica si hay una reina de un color específico en el tablero.
	 * 
	 * @param color El color de la reina a buscar
	 * @return true si hay una reina del color especificado en el tablero, false en
	 *         caso contrario
	 */
	public boolean hayReina(Color color) {
		for (int fila = 0; fila < tablero.consultarNumeroFilas(); fila++) {
			for (int columna = 0; columna < tablero.consultarNumeroColumnas(); columna++) {
				Celda celda = tablero.consultarCelda(new Coordenada(fila, columna));
				if (!celda.estaVacia()) {
					Pieza pieza = celda.consultarPieza();
					if (pieza.consultarColor() == color && pieza.consultarTipoPieza() == TipoPieza.REINA) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Genera una representación en cadena del TableroConsultor.
	 * 
	 * @return Una cadena que representa el estado del TableroConsultor
	 */
	@Override
	public String toString() {
		return "TableroConsultor [tablero=" + tablero + "]";
	}

}//