package jcolonia.daw2024.e3a;

import static java.lang.System.out;

import java.util.Scanner;

/**
 * Vista: Pantalla de altas para equipos informáticos en un aula.
 * 
 * @see ControladorInventarioAula
 * @see InventarioAula
 * 
 * @version 6.90 (2025426000)
 * @author <a href="mailto:dmartin.jcolonia@gmail.com">David H. Martín</a>
 */
public class VistaDiálogoAltaPuesto extends VistaGeneral {
	/** Control supervisor, de apoyo para completar altas */
	private ControladorInventarioAula control;

	/**
	 * Almacena el nombre o título.
	 * 
	 * @param nombre  el texto deseado
	 * @param control el control de la vista
	 */
	public VistaDiálogoAltaPuesto(String nombre, ControladorInventarioAula control) {
		super(nombre);
		this.control = control;
	}

	/**
	 * Recoge de la entrada estándar el código y datos del equipo y realiza el alta.
	 * Pide confirmación al usuario antes de completar el alta y muestra un error si
	 * los datos introducidos no resultan válidos.
	 * 
	 * @see ControladorInventarioAula#procesarAlta(String, PuestoUsuario)
	 * @return si el alta se ha completado o no
	 */
	public boolean entradaPuestoUsuario() {
		PuestoUsuario nuevo;
		String código, ordenador, nombre, apellidos;
		String mensaje;

		boolean altaFinalizada = false;

		try {
			código = entradaTexto("Introduce código de puesto: ");
			ordenador = entradaTexto("Introduce identificador del equipo: ");
			nombre = entradaTexto("Introduce nombre: ");
			apellidos = entradaTexto("Introduce apellidos: ");

			nuevo = new PuestoUsuario(ordenador, nombre, apellidos);
			mensaje = String.format("%s – %s", código, nuevo);
			if (confirmarAlta(mensaje)) {
				control.procesarAlta(código, nuevo);
			}
			altaFinalizada = true;
		} catch (InventarioException ex) {
			mostrarError(ex.getLocalizedMessage());
		}
		return altaFinalizada;
	}

	/**
	 * Complementa a {@link #entradaPuestoUsuario} para recoger y validar los
	 * nombres de los equipos y el resultado. Elimina espacios al comienzo y fin.
	 * Repite la pregunta mientras no se facilite ningún contenido neto.
	 * 
	 * @param mensaje la indicación previa a mostrar
	 * @return el texto recogido
	 */
	private String entradaTexto(String mensaje) {
		String líneaEntrada;

		Scanner in = getScEntrada();
		boolean entradaAceptable;

		do {
			out.print(mensaje);
			líneaEntrada = in.nextLine().trim();
			entradaAceptable = líneaEntrada.length() > 0;
		} while (!entradaAceptable);

		return líneaEntrada;
	}

	/**
	 * Requiere una confirmación previa al alta definitiva del equipo.
	 * 
	 * @param descripciónAlta el texto descriptivo del equipo introducido
	 * @return si se acepta por el usuario o no
	 */
	public boolean confirmarAlta(String descripciónAlta) {
		String txtInformación;
		String txtPregunta;
		boolean entradaAceptada;

		txtInformación = String.format("%nEntrada completa: %s", descripciónAlta);
		txtPregunta = "¿Desea almacenar el puesto?";

		entradaAceptada = pedirConfirmación(txtInformación, txtPregunta);

		if (!entradaAceptada) {
			out.println("    *** Entrada descartada");
		}
		return entradaAceptada;
	}
}
