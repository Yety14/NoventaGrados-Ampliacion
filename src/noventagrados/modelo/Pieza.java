package noventagrados.modelo;

import java.util.Objects;

import noventagrados.modelo.Pieza;
import noventagrados.util.Color;
import noventagrados.util.TipoPieza;

/**
 * Clase que representa una pieza.
 * 
 * @author Víctor Vidal Vivanco
 * @author Guillermo López de Arechavaleta Zapatero
 * @version 1.0
 * @since 1.0
 */

public class Pieza {

	/** Color de la pieza. */
	private Color color;

	/** Tipo de la pieza. */
	private TipoPieza tipoPieza;

	/**
	 * Construye una nueva pieza con el tipo y color especificados.
	 *
	 * @param tipoPieza el tipo de la pieza
	 * @param color     el color de la pieza
	 */
	public Pieza(TipoPieza tipoPieza, Color color) {
		this.color = color;
		this.tipoPieza = tipoPieza;

	}

	/**
	 * Crea y devuelve una copia profunda de la pieza.
	 *
	 * @return una nueva instancia de Pieza con las mismas propiedades que esta
	 */
	public Pieza clonar() {
		Pieza nueva = new Pieza(this.tipoPieza, this.color);
		return nueva;
	}

	/**
	 * Consulta el color de la pieza.
	 *
	 * @return el color de la pieza
	 */
	public Color consultarColor() {
		return color;
	}

	/**
	 * Consulta el tipo de la pieza.
	 *
	 * @return el tipo de la pieza
	 */
	public TipoPieza consultarTipoPieza() {
		return tipoPieza;
	}

	/**
	 * Genera un código hash para la pieza.
	 *
	 * @return el código hash basado en el color y tipo de la pieza
	 */
	@Override
	public int hashCode() {
		return Objects.hash(color, tipoPieza);
	}

	/**
	 * Compara esta pieza con otro objeto para determinar si son iguales. Dos piezas
	 * son consideradas iguales si tienen el mismo color y tipo.
	 *
	 * @param obj el objeto a comparar con esta pieza
	 * @return true si son iguales, false en caso contrario
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pieza other = (Pieza) obj;
		return Objects.equals(color, other.color) && Objects.equals(tipoPieza, other.tipoPieza); // Añadir comparación
																									// de atributos
	}

	/**
	 * Devuelve una representación en cadena de la pieza.
	 *
	 * @return una cadena que describe la pieza, incluyendo su color y tipo
	 */
	@Override
	public String toString() {
		return "Pieza [color=" + color + ", tipoPieza=" + tipoPieza + "]";
	}

}