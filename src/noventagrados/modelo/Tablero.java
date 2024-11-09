package noventagrados.modelo;

/**
 * Clase del tipo Tablero.
 * 
 * @author Víctor Vidal Vivanco
 * @author Guillermo López de Arechavaleta Zapatero
 * @version 1.0
 * @since 1.0
 */

import noventagrados.util.Coordenada;

import java.util.Arrays;


public class Tablero {
	 private static final int FILAS = 7;
	    private static final int COLUMNAS = 7;
	    private final Celda[][] celdas;

	    public Tablero() {
	        celdas = new Celda[FILAS][COLUMNAS];
	        for (int fila = 0; fila < FILAS; fila++) {
	            for (int columna = 0; columna < COLUMNAS; columna++) {
	                celdas[fila][columna] = new Celda(new Coordenada(fila, columna));
	            }
	        }
	    }
	    
	    public int consultarNumeroFilas() {
	        return FILAS;
	    }
	    
	    public int consultarNumeroColumnas() {
	        return COLUMNAS;
	    }
	    
    

    public void colocar(Pieza pieza, Coordenada coordenada) {
        if (pieza != null && coordenada != null && estanEnTablero(coordenada)) {
            celdas[coordenada.fila()][coordenada.columna()].colocar(pieza);
        }
    }

    public Celda[] consultarCeldas() {
        Celda[] celdasArray = new Celda[FILAS * COLUMNAS];
        int indice = 0;
        for (int fila = 0; fila < FILAS; fila++) {
            for (int columna = 0; columna < COLUMNAS; columna++) {
                celdasArray[indice++] = celdas[fila][columna].clonar(); // Clona cada celda antes de añadir
            }
        }
        return celdasArray;
    }


    public Celda consultarCelda(Coordenada coordenada) {
    	if (estanEnTablero(coordenada)) {
            return celdas[coordenada.fila()][coordenada.columna()].clonar(); // devuelve un clon en profundidad
        }
        return null;
    }

    public void eliminarPieza(Coordenada coordenada) {
    	//Primero de debe comprobar que la coordenada no es nulo antes intentar eliminarlo
        if (coordenada != null && estanEnTablero(coordenada)) {
            obtenerCelda(coordenada).eliminarPieza();
        }
    }
    
    public boolean estanEnTablero(Coordenada coordenada) {
        int fila = coordenada.fila();
        int columna = coordenada.columna();
        return fila >= 0 && fila < FILAS && columna >= 0 && columna < COLUMNAS;
    }
    
    Celda obtenerCelda(Coordenada coordenada) {
        return estanEnTablero(coordenada) ? celdas[coordenada.fila()][coordenada.columna()] : null;
    }
    

    public String aTexto() {
        StringBuilder sb = new StringBuilder();
        String tipoPieza;
        char colorPieza=' ';
        sb.append("\n");
        for (int fila = 0; fila < FILAS; fila++) {
        	sb.append(fila + " ");
            for (int columna = 0; columna < COLUMNAS; columna++) {
                Celda celda = celdas[fila][columna];
                //En vez de mostrar todos los datos correspondientes de la pieza, hay que mostrar
                //la concatenación de los chars correspondientes
                tipoPieza = celda.estaVacia() ? " -- " : " " + celda.consultarPieza().consultarTipoPieza().toChar();
                colorPieza = celda.estaVacia() ? ' ' : celda.consultarColorDePieza().toChar();
                sb.append(celda.estaVacia() ? " -- " : tipoPieza + colorPieza + " ");
            }
            sb.append("\n");
            //Imprimir el número de las columnas por debajo de la ultima fila
            if(fila == FILAS-1) {
            	for(int i = 0; i < FILAS; i++) {
            		sb.append("   " + i);
            	}
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public Tablero clonar() {
        Tablero clon = new Tablero();
        for (int fila = 0; fila < FILAS; fila++) {
            for (int columna = 0; columna < COLUMNAS; columna++) {
                clon.celdas[fila][columna] = this.celdas[fila][columna].clonar();
            }
        }
        return clon;
    }
  
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Tablero otroTablero = (Tablero) obj;
        return Arrays.deepEquals(celdas, otroTablero.celdas);
    }
    
    public int hashCode() {
        return Arrays.deepHashCode(celdas);
    }
    
    
    public String toString() {
        return aTexto();
    }
    
}