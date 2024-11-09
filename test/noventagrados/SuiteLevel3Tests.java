package noventagrados;


import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * Suite ejecutando los tests de nivel 3 de la práctica NoventaGrados-1.0 (ver README.txt).
 * 
 * @author <a href="rmartico@ubu.es">Raúl Marticorena</a>
 * @since 1.0
 * @version 1.0
 */
@SelectPackages({
	"noventagrados.modelo",
	"noventagrados.util"})
@Suite
@SuiteDisplayName("Tests de paquetes modelo y util completos.")
public class SuiteLevel3Tests {

}
