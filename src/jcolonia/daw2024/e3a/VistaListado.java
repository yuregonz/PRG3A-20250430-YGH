package jcolonia.daw2024.e3a;

import static java.lang.System.out;

import java.util.List;

/**
 * Vista para gestión de listados en consola de texto.
 * 
 * @version 1.2 (20250428000)
 * @author <a href="mailto:dmartin.jcolonia@gmail.com">David H. Martín</a>
 */
public class VistaListado extends VistaGeneral {
	/** Número de líneas a mostrar en cada página. */
	private static final int TAMAÑO_PÁGINA = 10;
	/**
	 * Formato común tipo «printf» para cada línea mostrada. Incluye el número y el
	 * propio texto de la línea.
	 */
	private static final String FORMATO_LISTADO = "  %d: %s%n";

	/**
	 * Almacena el nombre o título.
	 * 
	 * @param nombre el texto deseado
	 */
	public VistaListado(String nombre) {
		super(nombre);
	}

	/**
	 * Envía a la consola una lista numerada de textos distribuidos en lotes de
	 * cierta longitud. Al final de cada lote o página pide confirmación para
	 * mostrar las líneas que conformarán el siguiente lote.
	 * 
	 * @param listaTextos los textos a mostrar
	 * @see VistaGeneral#pedirConfirmación(String)
	 */
	public void mostrar(List<String> listaTextos) {
		int contadorImpresos = 0;

		for (String texto : listaTextos) {
			contadorImpresos++;
			out.printf(FORMATO_LISTADO, contadorImpresos, texto);

			if (contadorImpresos % TAMAÑO_PÁGINA == 0) {
				boolean confirmación = pedirConfirmación("¿Quieres pasar la página?");
				if (!confirmación) {
					break;
				}
			}
		}
	}
}
