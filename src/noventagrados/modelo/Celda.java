package noventagrados.modelo;

import noventagrados.util.Coordenada;
import noventagrados.util.TipoPieza;
import noventagrados.util.Color;
import java.util.Objects;


/**
 * Una celda del tablero
 * 
 * @author Víctor Vidal Vivanco
 * @author Guillermo López de Arechavaleta Zapatero
 * @version 1.0
 * @since 1.0
 */

public class Celda {

	/** La coordenada. */
	private Coordenada coordenada;
	
	/** El tipo de la pieza. */
	private TipoPieza tipoPieza;
	
	/** La pieza. */
	private Pieza pieza;

	/**
	 * Instancia una nueva celda.
	 *
	 * @param coordenada de la celda
	 */
	public Celda(Coordenada coordenada) {
        this.coordenada = new Coordenada(coordenada.fila(), coordenada.columna());
        this.pieza = null;
    }

	/**
	 * Crea un clon en profundidad de la celda.
	 *
	 * @return clon de la celda
	 */
	public Celda clonar() {
	    Celda nueva = new Celda(new Coordenada(coordenada.fila(), coordenada.columna()));
	    if (this.pieza != null) {
	        nueva.colocar(new Pieza(this.pieza.consultarTipoPieza(), this.pieza.consultarColor()));
	    }
	    return nueva;
	}

	/**
	 * Colocar una pieza en la celda.
	 *
	 * @param pieza en la celda
	 */
	public void colocar(Pieza pieza) {
		this.pieza = pieza;
	}
	
	/**
	 * Consultar color de pieza.
	 *
	 * @return El color de la pieza
	 */
	public Color consultarColorDePieza() {
		if(!estaVacia()) {
			return pieza.consultarColor();
		}
		return null;
	}
	
	/**
	 * Consultar coordenada.
	 *
	 * @return La coordenada de la celda
	 */
	public Coordenada consultarCoordenada() {
        return new Coordenada(coordenada.fila(), coordenada.columna());
    }
	
	/**
	 * Consultar pieza.
	 *
	 * @return La pieza en la celda, null = no hay pieza
	 */
	public Pieza consultarPieza() {
		if(!estaVacia()) {
			return pieza.clonar();
		}
		return null;
	}
	
	/**
     * Elimina la pieza de la celda, dejándola vacía.
     */
	public void eliminarPieza() {
		this.pieza = null;
	}
	
	 /**
     * Verifica si la celda está vacía (sin pieza).
     *
     * @return true si la celda está vacía, false en caso contrario
     */
	public boolean estaVacia() {
		
		return pieza == null;
	}
	
	/**
     * Compara esta celda con otro objeto.
     *
     * @param obj el objeto a comparar
     * @return true si son iguales, false en caso contrario
     */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Celda other = (Celda) obj;
		return Objects.equals(coordenada, other.coordenada) && Objects.equals(pieza, other.pieza)
				&& tipoPieza == other.tipoPieza;
	}
	
	/**
     * Genera un hash code para la celda.
     *
     * @return el hash code de la celda
     */
	public int hashCode() {
        return Objects.hash(coordenada, pieza);
    }

	/**
     * Devuelve una representación textual de la celda.
     *
     * @return un String que describe la celda
     */
	@Override
	public String toString() {
        return "Celda [coordenada=" + coordenada + ", pieza=" + (pieza != null ? pieza : "vacía") + "]";
    }
	
	
}
