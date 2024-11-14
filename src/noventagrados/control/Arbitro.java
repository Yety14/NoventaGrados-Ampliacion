package noventagrados.control;

import noventagrados.modelo.Tablero;
import noventagrados.modelo.Pieza;
import noventagrados.util.Color;
import noventagrados.util.Coordenada;
import noventagrados.util.TipoPieza;
import noventagrados.modelo.Jugada;
import noventagrados.modelo.Celda;
import noventagrados.util.Sentido;

/**
 * El arbitro del juego.
 * 
 * @author Víctor Vidal Vivanco
 * @author Guillermo López de Arechavaleta Zapatero
 * @version 1.0
 * @since 1.0
 */

public class Arbitro {

	/** El color del jugador que tiene el turno actual. */
	private Color turno;

	/** El tablero de juego actual. */
	private Tablero tablero;

	/** El número total de jugadas realizadas en la partida. */
	private int numeroJugadas;

	/** La caja que contiene las piezas blancas capturadas. */
	private Caja cajaBlanca;

	/** La caja que contiene las piezas negras capturadas. */
	private Caja cajaNegra;

	/**
	 * Constructor del Arbitro. Inicializa el tablero, el número de jugadas y las
	 * cajas para las piezas capturadas.
	 * 
	 * @param tablero El tablero de juego inicial
	 */
	public Arbitro(Tablero tablero) {
		this.tablero = tablero;
		this.numeroJugadas = 0;
		this.cajaBlanca = new Caja(Color.BLANCO);
		this.cajaNegra = new Caja(Color.NEGRO);
	}

	/**
	 * Cambia el turno al color contrario.
	 */
	public void cambiarTurno() {
		turno = turno.consultarContrario();
	}

	/**
	 * Coloca las piezas en el tablero en las coordenadas especificadas y establece
	 * el turno actual.
	 * 
	 * @param piezas      Array de piezas a colocar
	 * @param coordenadas Array de coordenadas donde colocar las piezas
	 * @param turnoActual Color del turno inicial
	 */
	public void colocarPiezas(Pieza[] piezas, Coordenada[] coordenadas, Color turnoActual) {
		this.turno = turnoActual;
		for (int i = 0; i < piezas.length; i++) {
			tablero.colocar(piezas[i], coordenadas[i]);
		}
	}

	/**
	 * Coloca las piezas en la configuración inicial del juego.
	 */
    public void colocarPiezasConfiguracionInicial() {
        colocarPiezas(new Pieza[] { 
                    new Pieza(TipoPieza.REINA, Color.BLANCO),
                    new Pieza(TipoPieza.REINA, Color.NEGRO),
                    new Pieza(TipoPieza.PEON, Color.BLANCO), 
                    new Pieza(TipoPieza.PEON, Color.BLANCO),
                    new Pieza(TipoPieza.PEON, Color.BLANCO), 
                    new Pieza(TipoPieza.PEON, Color.BLANCO),
                    new Pieza(TipoPieza.PEON, Color.BLANCO), 
                    new Pieza(TipoPieza.PEON, Color.BLANCO),
                    new Pieza(TipoPieza.PEON, Color.NEGRO), 
                    new Pieza(TipoPieza.PEON, Color.NEGRO),
                    new Pieza(TipoPieza.PEON, Color.NEGRO), 
                    new Pieza(TipoPieza.PEON, Color.NEGRO),
                    new Pieza(TipoPieza.PEON, Color.NEGRO), 
                    new Pieza(TipoPieza.PEON, Color.NEGRO), },
                new Coordenada[] { 
                    new Coordenada(0, 0),
                    new Coordenada(6, 6),
                    new Coordenada(1, 0), 
                    new Coordenada(2, 0), 
                    new Coordenada(3, 0), 
                    new Coordenada(0, 1), 
                    new Coordenada(0, 2),
                    new Coordenada(0, 3),
                    new Coordenada(3, 6), 
                    new Coordenada(4, 6), 
                    new Coordenada(5, 6), 
                    new Coordenada(6, 3),
                    new Coordenada(6, 4), 
                    new Coordenada(6, 5), },
                Color.BLANCO);
		numeroJugadas = 0;
		this.turno = Color.BLANCO;
	}

	/**
	 * Consulta la caja de piezas capturadas para un color específico.
	 * 
	 * @param color Color de la caja a consultar
	 * @return La caja de piezas capturadas del color especificado
	 */
	public Caja consultarCaja(Color color) {
		return (color == Color.BLANCO) ? cajaBlanca : cajaNegra;
	}

	/**
	 * Consulta el número actual de jugadas realizadas.
	 * 
	 * @return El número de jugadas
	 */
	public int consultarNumeroJugada() {
		return numeroJugadas;
	}

	/**
	 * Consulta el estado actual del tablero.
	 * 
	 * @return Una copia del tablero actual
	 */
	public Tablero consultarTablero() {
		return tablero.clonar();
	}

	/**
	 * Consulta el color del turno actual.
	 * 
	 * @return El color del turno actual
	 */
	public Color consultarTurno() {
		return turno;
	}

	/**
	 * Determina el color ganador si la partida ha finalizado.
	 * 
	 * @return El color ganador, o null si no hay ganador o la partida no ha
	 *         terminado
	 */
	public Color consultarTurnoGanador() {
		Color ganador = null;
		TableroConsultor tableroCons = new TableroConsultor(tablero);

		if (estaFinalizadaPartida()) {
			if (tableroCons.estaReinaEnElCentro(Color.BLANCO)) {
				ganador = Color.BLANCO;
			}
			if (tableroCons.estaReinaEnElCentro(Color.NEGRO)) {
				ganador = Color.NEGRO;
			}
			if (!tableroCons.hayReina(Color.BLANCO)) {
				ganador = Color.NEGRO;
			}
			if (!tableroCons.hayReina(Color.NEGRO)) {
				ganador = Color.BLANCO;
			}
			if (!tableroCons.hayReina(Color.BLANCO) && !tableroCons.hayReina(Color.NEGRO)) {
				ganador = null;
			}
		}
		return ganador;
	}

	/**
	 * Realiza un movimiento de empuje en el tablero.
	 * 
	 * @param jugada La jugada a realizar
	 */
	public void empujar(Jugada jugada) {
		numeroJugadas++;
		TableroConsultor tableroCons = new TableroConsultor(tablero);
		Celda origen = jugada.origen();
		Celda destino = jugada.destino();

		Coordenada coordOrigen = origen.consultarCoordenada();
		Coordenada coordDestino = destino.consultarCoordenada();
		Pieza pieza = origen.consultarPieza();

		Sentido sentido = tableroCons.calcularSentido(coordOrigen, coordDestino);

		if (!tablero.estanEnTablero(coordDestino) && pieza != null) {
			(pieza.consultarColor() == Color.BLANCO ? cajaBlanca : cajaNegra).añadir(pieza);
		} else {
			if (sentido.consultarDesplazamientoEnColumnas() == 0) {
				moverPieza(coordOrigen, coordDestino, sentido, true);
			} else if (sentido.consultarDesplazamientoEnFilas() == 0) {
				moverPieza(coordOrigen, coordDestino, sentido, false);
			}
		}
		tablero.colocar(pieza, coordDestino);
	}

	/**
	 * Método privado auxiliar para mover una pieza en el tablero.
	 * 
	 * @param origen     Coordenada de origen
	 * @param destino    Coordenada de destino
	 * @param sentido    Sentido del movimiento
	 * @param esVertical Indica si el movimiento es vertical
	 */
	private void moverPieza(Coordenada origen, Coordenada destino, Sentido sentido, boolean esVertical) {
		int origenPos = esVertical ? origen.fila() : origen.columna();
		int destinoPos = esVertical ? destino.fila() : destino.columna();
		int desplazamiento = esVertical ? sentido.consultarDesplazamientoEnFilas()
				: sentido.consultarDesplazamientoEnColumnas();

		Coordenada siguienteCoordenada = origen;

		while (origenPos != destinoPos) {
			tablero.eliminarPieza(siguienteCoordenada);
			origenPos += desplazamiento;
			siguienteCoordenada = esVertical ? new Coordenada(origenPos, origen.columna())
					: new Coordenada(origen.fila(), origenPos);
			Celda siguienteCelda = tablero.consultarCelda(siguienteCoordenada);
			if (siguienteCelda != null && !siguienteCelda.estaVacia()) {
				Coordenada coordDestPieza2 = esVertical
						? new Coordenada(destino.fila() + desplazamiento, destino.columna())
						: new Coordenada(destino.fila(), destino.columna() + desplazamiento);
				Celda destinoNuevo = new Celda(coordDestPieza2);
				Jugada jugadanueva = new Jugada(siguienteCelda, destinoNuevo);
				numeroJugadas--;
				empujar(jugadanueva);
			}
		}
	}

	/**
	 * Verifica si un movimiento es legal según las reglas del juego.
	 * 
	 * @param jugada La jugada a verificar
	 * @return true si el movimiento es legal, false en caso contrario
	 */
	public boolean esMovimientoLegal(Jugada jugada) {
		Coordenada origen = jugada.origen().consultarCoordenada();
		Coordenada destino = jugada.destino().consultarCoordenada();
		TableroConsultor tableroCons = new TableroConsultor(tablero);
		boolean result = false;

		if (tablero.estanEnTablero(origen) && tablero.estanEnTablero(destino) && !origen.equals(destino)) {
			if (!jugada.origen().estaVacia()) {
				if (this.turno == jugada.origen().consultarColorDePieza()) {
					Sentido sentido = tableroCons.calcularSentido(origen, destino);
					if (sentido == null) {
						return false;
					}
					if (sentido.consultarDesplazamientoEnFilas() == 0) { // Movimiento Horizontal
						if (tableroCons.consultarDistanciaEnHorizontal(origen, destino) == tableroCons
								.consultarNumeroPiezasEnVertical(origen)) {
							result = true;
						}
					}
					if (sentido.consultarDesplazamientoEnColumnas() == 0) { // Movimiento Vertical
						if (tableroCons.consultarDistanciaEnVertical(origen, destino) == tableroCons
								.consultarNumeroPiezasEnHorizontal(origen)) {
							result = true;
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * Verifica si la partida ha finalizado.
	 * 
	 * @return true si la partida ha finalizado, false en caso contrario
	 */
	public boolean estaFinalizadaPartida() {
		TableroConsultor tableroCons = new TableroConsultor(tablero);
		return !tableroCons.hayReina(Color.BLANCO) || !tableroCons.hayReina(Color.NEGRO)
				|| tableroCons.estaReinaEnElCentro(Color.BLANCO) || tableroCons.estaReinaEnElCentro(Color.NEGRO);
	}

	/**
	 * Genera una representación en cadena del estado actual del Arbitro.
	 * 
	 * @return Una cadena que representa el estado del Arbitro
	 */
	@Override
	public String toString() {
		return "Arbitro [turno=" + turno + ", tablero=" + tablero + ", numeroJugadas=" + numeroJugadas + ", cajaBlanca="
				+ cajaBlanca + ", cajaNegra=" + cajaNegra + "]";
	}

}//