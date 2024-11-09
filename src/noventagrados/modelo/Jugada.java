package noventagrados.modelo;

/**
 * Describe una jugada.
 * 
 * @author Víctor Vidal Vivanco
 * @author Guillermo López de Arechavaleta Zapatero
 * @version 1.0
 * @since 1.0
 */



public record Jugada (Celda origen, Celda destino) {
	
	 public String aTexto() {
	        return String.format("%d%d-%d%d",
	                origen.consultarCoordenada().fila(), origen.consultarCoordenada().columna(),
	                destino.consultarCoordenada().fila(), destino.consultarCoordenada().columna());
	    }
	
}




