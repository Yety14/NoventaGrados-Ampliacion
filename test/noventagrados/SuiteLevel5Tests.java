package noventagrados;


import org.junit.platform.suite.api.ExcludePackages;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * Suite ejecutando los tests de nivel 5 de la práctica NoventaGrados-1.0 (ver README.txt).
 * 
 * @author <a href="rmartico@ubu.es">Raúl Marticorena</a>
 * @since 1.0
 * @version 1.0
 */
@SelectPackages({
	"noventagrados.control",
	"noventagrados.modelo",
	"noventagrados.util"})
@ExcludePackages({"noventagrados.control.medio", "noventagrados.control.avanzado"})
@Suite
@SuiteDisplayName("Tests de paquetes control (tests basicos), modelo y util completos con inicializaciones, legalidad de movimientos y movimientos sin empujar piezas.")
public class SuiteLevel5Tests {

}
