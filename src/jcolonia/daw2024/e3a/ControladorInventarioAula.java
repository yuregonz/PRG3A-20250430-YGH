package jcolonia.daw2024.e3a;

import java.util.List;

/**
 * Controlador: Aplicación de gestión de equipos informáticos en un aula.
 * Gestiona las distintas funciones del menú principal. Manipula de manera única
 * el aula 25, cuyos equipos están identificados como ICXX.
 * 
 * @see InventarioAula
 * 
 * @version 6.90 (2025426000)
 * @author <a href="mailto:dmartin.jcolonia@gmail.com">David H. Martín</a>
 */
public class ControladorInventarioAula {
	/** Nombre del archivo de datos para impotación/exportación. */
	private static final String NOMBRE_ARCHIVO = "Inventario ICXX.txt";
	/** Opciones del menú principal. */
	private static final String[] TXT_MENÚ_PRINCIPAL = { "Alta", "Baja", "Listado", "Exportación", "Importación",
			"Borrado" };
	/** Colección principal de resultados. */
	private InventarioAula aula25;

	/**
	 * Inicializa la lista/colección donde se irán guardando los equipos.
	 */
	public ControladorInventarioAula() {
		try {
			aula25 = InventarioAula.of("IC");
		} catch (InventarioException ex) {
			String mensaje;
			mensaje = String.format("Error fatal: %s", ex.getLocalizedMessage());
			VistaGeneral.mostrarAviso(mensaje);
			System.exit(1);
		}
	}

	/**
	 * Bucle principal ligado al menú de entrada.
	 */
	public void buclePrincipal() {
		VistaMenú menú;
		int opción;
		int n = 0;
		boolean salir = false;

		menú = new VistaMenú("Inventario aula 25", TXT_MENÚ_PRINCIPAL);

		do {
			menú.mostrarTítuloPrincipal();
			menú.mostrarMenú();
			opción = menú.pedirOpción();
			n++;

			switch (opción) {
			case 1: // Alta
				alta();
				break;
			case 2: // Baja
				baja();
				break;
			case 3: // Listado
				listado();
				break;
			case 4: // Exportación
				exportación(NOMBRE_ARCHIVO);
				break;
			case 5: // Importación
				importación(NOMBRE_ARCHIVO);
				break;
			case 6: // Borrado
				// borrado()
				// break;
			case 0:
				finalizar(); // Finalizar programa
				salir = true;
				break;
			default:
				stub(opción, n);
				break;
			}
		} while (!salir);
	}

	/**
	 * Agrega al inventario un nuevo equipo con los datos facilitados por el
	 * usuario. La vista de altas debe pedir al usuario los datos correspondientes y
	 * se apoyará en el método {@link #procesarAlta(String, PuestoUsuario)} para
	 * validar las entradas y completar el alta.
	 */
	private void alta() {
		VistaDiálogoAltaPuesto dlg;
		dlg = new VistaDiálogoAltaPuesto("Alta ICXX", this);

		dlg.mostrarTítulo();
		dlg.entradaPuestoUsuario();
	}

	/**
	 * Incorpora un nuevo equipo al inventario del aula. Actúa de intermediario
	 * forzoso entre la vista y el almacén; evitando así compartir el almacen con la
	 * vista impidiendo potenciales manipulaciones descontroladas.
	 * 
	 * @see InventarioAula#añadir(String, PuestoUsuario)
	 * 
	 * @param código el identificador del puesto
	 * @param nuevo  los datos del equipo
	 * @throws InventarioException si alguna incidencia impide realizar el alta
	 */
	public void procesarAlta(String código, PuestoUsuario nuevo) throws InventarioException {
		aula25.añadir(código, nuevo);
	}

	/**
	 * Borra del inventario un equipo con los datos facilitados por el usuario.
	 */
	private void baja() {
		VistaDiálogoBajaPuesto dlg;
		dlg = new VistaDiálogoBajaPuesto("Baja ICXX", this);

		dlg.bajaPuestoUsuario();
	}

	/**
	 * Incorpora un nuevo equipo al inventario del aula. Actúa de intermediario
	 * forzoso entre la vista y el almacén; evitando así compartir el almacen con la
	 * vista impidiendo potenciales manipulaciones descontroladas.
	 * 
	 * @see InventarioAula#añadir(String, PuestoUsuario)
	 * 
	 * @param código el identificador del puesto
	 * @param nuevo  los datos del equipo
	 * @throws InventarioException si alguna incidencia impide realizar el alta
	 */
	public void procesarBaja(String código) throws InventarioException {
		aula25.borrar(código);
	}

	/**
	 * Genera una pantalla con el listado completo de equipos del aula.
	 */
	private void listado() {
		VistaListado dlg;
		dlg = new VistaListado("Lista de Resultados");

		dlg.mostrar(aula25.generarListadoTexto());
		VistaGeneral.preguntaSeguir();
	}

	/**
	 * Realiza el volcado de todas las equipos almacenados a un archivo de texto.
	 * Emplea un formato propio –de estilo CSV con separador «#» u otro– que puede
	 * ser recuperado posteriormente (ver {@link #importación(String)}). El formato
	 * concreto de cada línea así como la lógica de importación/exportación de los
	 * puestos depende del inventario. En caso de producirse algún error de acceso
	 * se envía el mensaje a la salida de error estándar y el programa continua.
	 * 
	 * @see InventarioAula#generarListadoCSV()
	 * @param rutaArchivo el nombre o ruta al archivo
	 */
	private void exportación(String rutaArchivo) {
		AccesoArchivo archivo;
		List<String> contenido;
		boolean exportadoCorrecto;

		archivo = new AccesoArchivo(rutaArchivo);
		contenido = aula25.generarListadoCSV();
		exportadoCorrecto = archivo.escribir(contenido);
		if (!exportadoCorrecto) {
			VistaGeneral.mostrarError("Error en la exportación");
		}

	}

	/**
	 * Importa equipos almacenados en un archivo de texto reemplazando el contenido
	 * actual del programa. Emplea un formato propio –de estilo CSV con separador
	 * «#» u otro– producido por una exportación previa (ver
	 * {@link #exportación(String)}). El formato concreto de cada línea así como la
	 * lógica de importación/exportación de los puestos depende del inventario. En
	 * caso de producirse algún error de acceso o por el propio formato del archivo,
	 * se envía el mensaje a la salida de error estándar y el programa continúa sin
	 * perder el contenido anterior.
	 * 
	 * @see InventarioAula#of(List)
	 * 
	 * @param rutaArchivo el nombre o ruta al archivo
	 */
	private void importación(String rutaArchivo) {
		AccesoArchivo archivo;
		InventarioAula nuevaLista;
		List<String> contenido;
		int númElementos;
		String mensaje;

		try {
			archivo = new AccesoArchivo(rutaArchivo);
			contenido = archivo.leer();
			númElementos = contenido.size() - 1; // 1 línea de cabecera, el prefijo

			if (númElementos < 1) {
				VistaGeneral.mostrarAviso("No hay ningún elemento que importar");
			} else {
				nuevaLista = InventarioAula.of(contenido);
				númElementos = nuevaLista.getNúmElementos();

				if (númElementos > 0) {
					aula25 = nuevaLista;
					mensaje = String.format("%d equipos importados", númElementos);
					VistaGeneral.mostrarTexto(mensaje);
					listado();
				} else {
					mensaje = String.format("%d equipos importados, se conservará la relación previa", númElementos);
					VistaGeneral.mostrarAviso(mensaje);
				}
			}
		} catch (InventarioException ex) {
			mensaje = String.format("Error de importación: %s", ex.getLocalizedMessage());
			VistaGeneral.mostrarAviso(mensaje);
		}
	}
	
	/**
	 * Borra todos los puestos del aula.
	 */
	public void borrado() {
		VistaReseteo reset = new VistaReseteo(NOMBRE_ARCHIVO);

		
		VistaGeneral.preguntaSeguir();
	}

	/**
	 * Muestra un mensaje temporal, de relleno, para opciones pendientes de
	 * implementar.
	 * 
	 * @param entrada la opción elegida
	 * @param n       el número de secuencia en el historial de opciones cursadas
	 */
	private void stub(int entrada, int n) {
		String mensaje;
		mensaje = String.format("(%02d) → %d [Opción sin desarrollar]", n, entrada);
		VistaGeneral.mostrarAviso(mensaje);
	}

	/**
	 * Finaliza el programa. Muestra un mensaje final y cierra la conexión con la
	 * entrada estándar.
	 */
	private void finalizar() {
		VistaGeneral.mostrarTexto("*** FIN ***");
		VistaGeneral.close();
	}
}
