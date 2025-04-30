package jcolonia.daw2024.e3a;

/**
 * Vista: Pantalla de bajas para equipos informáticos en un aula.
 * 
 * @see ControladorInventarioAula
 * @see InventarioAula
 * 
 * @version 6.90 (2025426000)
 * @author <a href="mailto:dmartin.jcolonia@gmail.com">David H. Martín</a>
 */
public class VistaDiálogoBajaPuesto extends VistaGeneral {
	/** Control supervisor, de apoyo para completar bajas */
	private ControladorInventarioAula control;

	/**
	 * Almacena el nombre o título.
	 * 
	 * @param nombre  el texto deseado
	 * @param control el control de la vista
	 */
	public VistaDiálogoBajaPuesto(String nombre, ControladorInventarioAula control) {
		super(nombre);
		this.control = control;
	}

	/**
	 * Recoge de la entrada estándar el código y datos del equipo y realiza la baja.
	 * Pide confirmación al usuario antes de completar la baja y muestra un error si
	 * los datos introducidos no resultan válidos.
	 * 
	 * @see ControladorInventarioAula#procesarAlta(String, PuestoUsuario)
	 * @return si la baja se ha completado o no
	 */
	public boolean bajaPuestoUsuario() {
		String código;
		boolean confirmación;

		System.out.print("Introduzca el código: ");
		código = getScEntrada().nextLine();

		confirmación = pedirConfirmación("¿Desea borrar del inventario este puesto?");
		if (confirmación) {
			try {
				control.procesarBaja(código);
				return true;
			} catch (InventarioException e) {
				mostrarError("Los datos introducidos son incorrectos" + e.getLocalizedMessage());
				return false;
			}
		} else {
			return false;
		}
	}

}
