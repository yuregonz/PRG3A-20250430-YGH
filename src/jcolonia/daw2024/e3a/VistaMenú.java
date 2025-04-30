package jcolonia.daw2024.e3a;

import java.util.Arrays;

/**
 * Vista: Utilidades prototipo de gestión de menú.
 * 
 * @see ControladorInventarioAula
 * 
 * @version 1.2.0 (20250429000)
 * @author <a href="mailto:dmartin.jcolonia@gmail.com">David H. Martín</a>
 */
public class VistaMenú extends VistaGeneral {
	/** Formato común tipo «printf» de las opciones de menú. */
	private static final String FORMATO_OPCIONES_MENÚ = "  %d) %s%n";

	/** Textos de las opciones del menú. */
	private String[] textoOpciones;

	/**
	 * Crea un menú con las opciones indicadas. Incorpora y activa la opción «0»
	 * para salir o finalizar.
	 * 
	 * @param nombre   el nombre o título del menú
	 * @param opciones la lista original de opciones a copiar
	 */
	public VistaMenú(String nombre, String[] opciones) {
		super(nombre);

		textoOpciones = Arrays.copyOf(opciones, opciones.length);
	}

	/**
	 * Envía a la consola de texto –salida estándar– la lista numerada de opciones
	 * disponibles. Incluye la opción «0» para salir o finalizar.
	 */
	public void mostrarMenú() {
		mostrarAviso("PENDIENTE: programador ocupado…");
	}

	/**
	 * Solicita al usuario elegir una opción de menú. En caso de no elegir una
	 * opción válida insiste de manera indefinida hasta obtener una.
	 * 
	 * @return el número de la opción elegida [1..n]
	 */
	public int pedirOpción() {
		mostrarAviso("PENDIENTE: programador ocupado…");
		return 0;
	}
}
