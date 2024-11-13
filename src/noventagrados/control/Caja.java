package noventagrados.control;

import noventagrados.util.Color;
import java.util.Arrays;

import noventagrados.modelo.Pieza;
import noventagrados.util.TipoPieza;

/**
 * La caja para guardar las piezas eliminadas
 * 
 * @author Víctor Vidal Vivanco
 * @author Guillermo López de Arechavaleta Zapatero
 * @version 1.0
 * @since 1.0
 */

public class Caja {

	/** Array que almacena las piezas capturadas en esta caja. */
	private Pieza[] piezas;

	/** El color de las piezas que esta caja puede contener. */
	private Color color;

	/** El número actual de piezas en la caja. */
	private int contador;

	/**
	 * Constructor de la Caja. Inicializa una caja para un color específico con
	 * capacidad para 7 piezas.
	 * 
	 * @param color El color de las piezas que contendrá la caja
	 */
	public Caja(Color color) {
		this.color = color;
		this.piezas = new Pieza[7];
		this.contador = 0;
	}

	/**
	 * Añade una pieza a la caja si hay espacio y coincide con el color de la caja.
	 * 
	 * @param pieza La pieza a añadir
	 */
	public void añadir(Pieza pieza) {
		if (contador < 7 && pieza.consultarColor() == this.color) {
			this.piezas[contador] = pieza;
			contador++;
		}
	}

	/**
	 * Crea y devuelve una copia profunda de la caja.
	 * 
	 * @return Una nueva instancia de Caja con las mismas propiedades y piezas
	 */
	public Caja clonar() {
		Caja cajaClon = new Caja(this.color);
		cajaClon.piezas = new Pieza[this.piezas.length];
		for (int i = 0; i < contador; i++) {
			cajaClon.piezas[i] = new Pieza(this.piezas[i].consultarTipoPieza(), this.piezas[i].consultarColor());
		}
		cajaClon.contador = this.contador;
		return cajaClon;
	}

	/**
	 * Consulta el color de la caja.
	 * 
	 * @return El color asociado a la caja
	 */
	public Color consultarColor() {
		return this.color;
	}

	/**
	 * Devuelve un array con las piezas actualmente en la caja.
	 * 
	 * @return Un array de Pieza con las piezas en la caja
	 */
	public Pieza[] consultarPiezas() {
		return Arrays.copyOf(this.piezas, contador);
	}

	/**
	 * Cuenta el número total de piezas en la caja.
	 * 
	 * @return El número de piezas en la caja
	 */
	public int contarPiezas() {
		return contador;
	}

	/**
	 * Cuenta el número de piezas de un tipo específico en la caja.
	 * 
	 * @param tipoPieza El tipo de pieza a contar
	 * @return El número de piezas del tipo especificado
	 */
	public int contarPiezas(TipoPieza tipoPieza) {
		int count = 0;
		for (int i = 0; i < contador; i++) {
			if (piezas[i].consultarTipoPieza() == tipoPieza) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Compara esta caja con otro objeto para determinar si son iguales.
	 * 
	 * @param obj El objeto a comparar con esta caja
	 * @return true si los objetos son iguales, false en caso contrario
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Caja other = (Caja) obj;
		return Arrays.equals(piezas, other.piezas) && color == other.color;
	}

	/**
	 * Calcula el código hash de la caja.
	 * 
	 * @return El código hash calculado
	 */
	@Override
	public int hashCode() {
		int result = Arrays.hashCode(piezas);
		result = 31 * result + (color != null ? color.hashCode() : 0);
		return result;
	}

	/**
	 * Genera una representación en cadena de la caja.
	 * 
	 * @return Una cadena que representa el estado de la caja
	 */
	@Override
	public String toString() {
		return "Caja [piezas=" + Arrays.toString(piezas) + ", color=" + color + "]";
	}
}