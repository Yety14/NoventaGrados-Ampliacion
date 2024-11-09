package noventagrados.util;

/**
 *
 * @author Víctor Vidal Vivanco
 * @author Guillermo Guillermo López de Arechavaleta Zapatero
 * @version 1.0
 * @since 1.0
 */

public enum Color {
	
	//Color Negro
	NEGRO ('N'),
	
	//Color blanco
	BLANCO ('B');
	
	
	//Guarda la letra de cada color.
	private char letra;
	
	
	//Instancia un nuevo color.
	private Color (char letra) {
		
		this.letra = letra;
	}
	
	
	//Consulta el color del contrario.
	public Color consultarContrario() {
		
		return this == BLANCO ? NEGRO:BLANCO;
	}
	
	
	//Devuleve el caracter que corresponde al color contrario.
	public char toChar() {
		
		return letra;
	}
	
	
}







 