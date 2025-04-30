package jcolonia.daw2024.e3a;

/**
 * Lanzador de la aplicación de gestión de equipos informáticos en un aula.
 * 
 * @see ControladorInventarioAula
 * 
 * @version 6.90 (2025426000)
 * @author <a href="mailto:dmartin.jcolonia@gmail.com">David H. Martín</a>
 */
public class LanzadorInventario {
	/**
	 * Inicia el programa creando una instancia de la clase y activando el bucle
	 * principal de opciones.
	 * 
	 * @param argumentos opciones de ejcución -no se usan-
	 * 
	 */
	public static void main(String[] argumentos) {
		ControladorInventarioAula control = new ControladorInventarioAula();
		control.buclePrincipal();
	}
}
