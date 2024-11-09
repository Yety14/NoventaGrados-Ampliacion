package noventagrados.util;

/**
 * Enumeración que representa los colores de las piezas en el juego.
 *
 * @author Víctor Vidal Vivanco
 * @author Guillermo López de Arechavaleta Zapatero
 * @version 1.0
 * @since 1.0
 */

public enum Color {

	/** Representa el color Negro. */
	NEGRO('N'),

	/** Representa el color Blanco. */
	BLANCO('B');

	/** Almacena el carácter que representa el color. */
	private char letra;

	/**
	 * Constructor privado para la enumeración Color.
	 *
	 * @param letra El carácter que representa el color
	 */
	private Color(char letra) {

		this.letra = letra;
	}

	/**
	 * Devuelve el color opuesto al actual.
	 *
	 * @return El color contrario (NEGRO si es BLANCO, y viceversa)
	 */
	public Color consultarContrario() {

		return this == BLANCO ? NEGRO : BLANCO;
	}

	/**
	 * Devuelve el carácter que representa este color.
	 *
	 * @return El carácter asociado al color
	 */
	public char toChar() {

		return letra;
	}

}
