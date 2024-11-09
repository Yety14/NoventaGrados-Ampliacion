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
 * El arbitro del juego
 * 
 * @author Víctor Vidal Vivanco
 * @author Guillermo López de Arechavaleta Zapatero
 * @version 1.0
 * @since 1.0
 */

public class Arbitro {

	private Color turno;
	private Tablero tablero;
	private int numeroJugadas;
	private Caja cajaBlanca;
	private Caja cajaNegra;

	public Arbitro(Tablero tablero) {
		this.tablero = tablero;
		this.numeroJugadas = 0;
		this.cajaBlanca=new Caja(Color.BLANCO);
		this.cajaNegra=new Caja(Color.NEGRO);
	}

	public void cambiarTurno() {
		turno = turno.consultarContrario();
	}

	public void colocarPiezas(Pieza[] piezas, Coordenada[] coordenadas, Color turnoActual) {
		this.turno = turnoActual;
		for (int i = 0; i < piezas.length; i++) {
			tablero.colocar(piezas[i], coordenadas[i]);
		}
	}

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

	public Caja consultarCaja(Color color) {
		return (color == Color.BLANCO) ? cajaBlanca : cajaNegra;
	}

	public int consultarNumeroJugada() {
		return numeroJugadas;
	}

	public Tablero consultarTablero() {
		return tablero.clonar();
	}

	public Color consultarTurno() {
		return turno;
	}

	public Color consultarTurnoGanador() {
		Color ganador = null;
		TableroConsultor tableroCons = new TableroConsultor(tablero);
		
		if (estaFinalizadaPartida()) {
			if(tableroCons.estaReinaEnElCentro(Color.BLANCO)) {
				ganador=Color.BLANCO;
			}
			if(tableroCons.estaReinaEnElCentro(Color.NEGRO)) {
				ganador=Color.NEGRO;
			}
			if(!tableroCons.hayReina(Color.BLANCO)) {
				ganador=Color.NEGRO;
			}
			if(!tableroCons.hayReina(Color.NEGRO)) {
				ganador=Color.BLANCO;
			}
			if(!tableroCons.hayReina(Color.BLANCO) && !tableroCons.hayReina(Color.NEGRO)) {
				ganador=null;
			}
		}
		return ganador;
	}

	public void empujar(Jugada jugada) {
		numeroJugadas++;
		TableroConsultor tableroCons = new TableroConsultor(tablero);
		Celda origen = jugada.origen();
		Celda destino = jugada.destino();

		Coordenada coordOrigen = origen.consultarCoordenada();
		Coordenada coordDestino = destino.consultarCoordenada();
		Coordenada siguienteCoordenada = coordOrigen;

		int origenCol = coordOrigen.columna();
		int destinoCol = coordDestino.columna();
		int origenFil = coordOrigen.fila();
		int destinoFil = coordDestino.fila();

		Pieza pieza = origen.consultarPieza();

		Sentido sentido = tableroCons.calcularSentido(coordOrigen, coordDestino);

		if (!tablero.estanEnTablero(coordDestino) && pieza != null) {
			if (pieza.consultarColor() == Color.BLANCO) {
				cajaBlanca.añadir(pieza);
			} else {
				cajaNegra.añadir(pieza);
			}

		} else {
			if (sentido.consultarDesplazamientoEnColumnas() == 0 
					&& sentido.consultarDesplazamientoEnFilas() != 0) { // Movimiento Vertical
				while (origenFil != destinoFil) {
					tablero.eliminarPieza(siguienteCoordenada); 	// en la primera iteración es la pieza original
					origenFil += sentido.consultarDesplazamientoEnFilas();
					siguienteCoordenada = new Coordenada(origenFil, origenCol);
					Celda siguienteCelda = tablero.consultarCelda(siguienteCoordenada);
					if (siguienteCelda != null) {
						if (!siguienteCelda.estaVacia()) {
							Coordenada coordDestPieza2 = new Coordenada(destinoFil + sentido.consultarDesplazamientoEnFilas(), destinoCol);
							Celda destinoNuevo = new Celda(coordDestPieza2);
							Jugada jugadanueva = new Jugada(siguienteCelda, destinoNuevo);
							numeroJugadas--;
							empujar(jugadanueva);
						}
					}
				}

			} else if (sentido.consultarDesplazamientoEnFilas() == 0
					&& sentido.consultarDesplazamientoEnColumnas() != 0) { // Movimiento Horizontal
				while (origenCol != destinoCol) {
					tablero.eliminarPieza(siguienteCoordenada);
					origenCol += sentido.consultarDesplazamientoEnColumnas();
					siguienteCoordenada = new Coordenada(origenFil, origenCol);
					Celda siguienteCelda = tablero.consultarCelda(siguienteCoordenada);
					if (siguienteCelda != null) {
						if (!siguienteCelda.estaVacia()) {

							Coordenada coordDestPieza2 = new Coordenada(destinoFil,destinoCol + sentido.consultarDesplazamientoEnColumnas());
							Celda destinoNuevo = new Celda(coordDestPieza2);
							Jugada jugadanueva = new Jugada(siguienteCelda, destinoNuevo);
							numeroJugadas--;
							empujar(jugadanueva);
						}
					}
				}
			}
		}
		tablero.colocar(pieza, coordDestino);
	}

	public boolean esMovimientoLegal(Jugada jugada) {
		Coordenada origen = jugada.origen().consultarCoordenada();
		Coordenada destino = jugada.destino().consultarCoordenada();
		TableroConsultor tableroCons = new TableroConsultor(tablero);
		boolean result = false;

		if (tablero.estanEnTablero(origen) && tablero.estanEnTablero(destino) && !origen.equals(destino)) {
			if (!jugada.origen().estaVacia()) {
				if (this.turno == jugada.origen().consultarColorDePieza()) {
					Sentido sentido = tableroCons.calcularSentido(origen, destino);
					if(sentido == null) {
						return false;
					}
					if (sentido.consultarDesplazamientoEnFilas() == 0) { // Movimiento Horizontal
						if (tableroCons.consultarDistanciaEnHorizontal(origen, destino)
								== tableroCons.consultarNumeroPiezasEnVertical(origen)) {
							result = true;
						}						
					}
					if (sentido.consultarDesplazamientoEnColumnas() == 0) { // Movimiento Vertical
						if (tableroCons.consultarDistanciaEnVertical(origen, destino) 
								== tableroCons.consultarNumeroPiezasEnHorizontal(origen)) {
							result = true;
						}
					}
				}
			}
		}
		return result;
	}

	public boolean estaFinalizadaPartida() {
		TableroConsultor tableroCons = new TableroConsultor(tablero);
		return !tableroCons.hayReina(Color.BLANCO) || !tableroCons.hayReina(Color.NEGRO)
				|| tableroCons.estaReinaEnElCentro(Color.BLANCO) || tableroCons.estaReinaEnElCentro(Color.NEGRO);
	}

	@Override
	public String toString() {
		return "Arbitro [turno=" + turno + ", tablero=" + tablero + ", numeroJugadas=" + numeroJugadas + ", cajaBlanca="
				+ cajaBlanca + ", cajaNegra=" + cajaNegra + "]";
	}

}//