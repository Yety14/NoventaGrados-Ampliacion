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

	private Tablero tablero;

	public TableroConsultor(Tablero tablero) {
		this.tablero = tablero;
	}

	public Sentido calcularSentido(Coordenada origen, Coordenada destino) {
		if (origen.fila() == destino.fila()) {
			return origen.columna() < destino.columna() ? Sentido.HORIZONTAL_E : Sentido.HORIZONTAL_O;
		} else if (origen.columna() == destino.columna()) {
			return origen.fila() < destino.fila() ? Sentido.VERTICAL_S : Sentido.VERTICAL_N;
		}
		return null;
	}

	public int consultarDistanciaEnHorizontal(Coordenada origen, Coordenada destino) {
		if (origen.fila() == destino.fila()) {
			return Math.abs(origen.columna() - destino.columna());
		}
		return -1;
	}

	public int consultarDistanciaEnVertical(Coordenada origen, Coordenada destino) {
		if (origen.columna() == destino.columna()) {
			return Math.abs(origen.fila() - destino.fila());
		}
		return -1;
	}

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

	public boolean estaReinaEnElCentro(Color color) {
		Celda celdaCentro = tablero.consultarCelda(new Coordenada(tablero.consultarNumeroFilas()/2, tablero.consultarNumeroColumnas()/2));
		if (!celdaCentro.estaVacia()) {
			return celdaCentro.consultarPieza().consultarColor() == color
					&& celdaCentro.consultarPieza().consultarTipoPieza() == TipoPieza.REINA;
		}
		return false;
	}

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

	@Override
	public String toString() {
		return "TableroConsultor [tablero=" + tablero + "]";
	}

}//