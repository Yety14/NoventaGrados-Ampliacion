package noventagrados;


import org.junit.platform.suite.api.ExcludePackages;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * Suite ejecutando los tests de nivel 6 de la práctica NoventaGrados-1.0 (ver README.txt).
 * 
 * @author <a href="rmartico@ubu.es">Raúl Marticorena</a>
 * @since 1.0
 * @version 1.0
 */
@SelectPackages({
	"noventagrados.control",
	"noventagrados.modelo",
	"noventagrados.util"})
@ExcludePackages({"noventagrados.control.avanzado"})
@Suite
@SuiteDisplayName("Tests de paquetes control (tests basicos), modelo y util completos con partidas simples, empujando piezas y expulsando piezas del tablero.")
public class SuiteLevel6Tests {

}
