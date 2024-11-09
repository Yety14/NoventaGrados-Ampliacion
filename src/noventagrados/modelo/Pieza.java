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
	 * Instancia una nueva pieza.
	 *
	 * @param tipoPieza el tipo de la pieza
	 */
	public Pieza (TipoPieza tipoPieza, Color color) {
		this.color = color;
		this.tipoPieza = tipoPieza;
		
	}
	
	/**
	 * Clona en profundida la pieza.
	 *
	 * @return la pieza clonada
	 */
	public Pieza clonar() {
		Pieza nueva = new Pieza(this.tipoPieza, this.color);
		return nueva;
	}
	
	/**
	 * Consultar color.
	 *
	 * @return el color
	 */
	public Color consultarColor() {
		return color;
	}
	
	/**
	 * Consultar tipo pieza.
	 *
	 * @return el tipo de la pieza
	 */
	public TipoPieza consultarTipoPieza() {
		return tipoPieza;
	}

	@Override
	public int hashCode() {
		return Objects.hash(color, tipoPieza);
	}
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pieza other = (Pieza) obj;
        return Objects.equals(color, other.color) && Objects.equals(tipoPieza, other.tipoPieza); // Añadir comparación de atributos
    }
	
	@Override
	public String toString() {
		return "Pieza [color=" + color + ", tipoPieza=" + tipoPieza + "]";
	}
	
}