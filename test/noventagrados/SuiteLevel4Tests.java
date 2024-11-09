package noventagrados;


import org.junit.platform.suite.api.ExcludePackages;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * Suite ejecutando los tests de nivel 4 de la práctica NoventaGrados-1.0 (ver README.txt).
 * 
 * @author <a href="rmartico@ubu.es">Raúl Marticorena</a>
 * @since 1.0
 * @version 1.0
 */
@SelectPackages({
	"noventagrados.control",
	"noventagrados.modelo",
	"noventagrados.util"})
@ExcludePackages({"noventagrados.control.basico", "noventagrados.control.avanzado", "noventagrados.control.medio"})
@Suite
@SuiteDisplayName("Tests de paquetes control sobre el TableroConsultor, Caja, paquetes modelo y util, sin integracion del Arbitro para partidas completas.")
public class SuiteLevel4Tests {

}
