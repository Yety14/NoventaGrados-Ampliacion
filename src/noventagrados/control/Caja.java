package noventagrados.control;

import noventagrados.util.Color;
import java.util.Arrays;
import noventagrados.modelo.Pieza;
import noventagrados.util.TipoPieza;

/**
 * Una caja
 * 
 * @author Víctor Vidal Vivanco
 * @author Guillermo López de Arechavaleta Zapatero
 * @version 1.0
 * @since 1.0
 */

public class Caja {

	private Pieza[] piezas;
	private Color color;
	private int contador;

	public Caja(Color color) {
		this.color = color;
		this.piezas = new Pieza[7];
		this.contador = 0;
	}

	public void añadir(Pieza pieza) {
		if (contador < 7 && pieza.consultarColor() == this.color) {
			this.piezas[contador] = pieza;
			contador++;
		}
	}

	public Caja clonar() {
		Caja cajaClon = new Caja(this.color);
		cajaClon.piezas = new Pieza[this.piezas.length];
		for (int i = 0; i < contador; i++) {
			cajaClon.piezas[i] = new Pieza(this.piezas[i].consultarTipoPieza(), this.piezas[i].consultarColor());
		}
		cajaClon.contador = this.contador;
		return cajaClon;
	}

	public Color consultarColor() {
		return this.color;
	}

	public Pieza[] consultarPiezas() {
		return Arrays.copyOf(this.piezas, contador);
	}

	public int contarPiezas() {
		return contador;
	}

	public int contarPiezas(TipoPieza tipoPieza) {
		int count = 0;
		for (int i = 0; i < contador; i++) {
			if (piezas[i].consultarTipoPieza() == tipoPieza) {
				count++;
			}
		}
		return count;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Caja other = (Caja) obj;
		return Arrays.equals(piezas, other.piezas) && color == other.color;
	}

	@Override
	public int hashCode() {
		int result = Arrays.hashCode(piezas);
		result = 31 * result + (color != null ? color.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Caja [piezas=" + Arrays.toString(piezas) + ", color=" + color + "]";
	}
}