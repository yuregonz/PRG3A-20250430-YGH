package jcolonia.daw2024.e3a;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Archivo para importar o exportar texto.
 * 
 * @version 1.1 (20240502000)
 * @author <a href="mailto:dmartin.jcolonia@gmail.com">David H. Martín</a>
 */
public class AccesoArchivo {
	/** El nombre o ruta al archivo. */
	private String rutaArchivo;

	/**
	 * Recoge el nombre o ruta al archivo.
	 * 
	 * @param rutaArchivo el texto correspondiente
	 */
	public AccesoArchivo(String rutaArchivo) {
		this.rutaArchivo = rutaArchivo;
	}

	/**
	 * Realiza el volcado de una lista de líneas de texto al archivo. Si el archivo
	 * existe lo sobreescribe con el nuevo contenido. En caso de producirse algún
	 * error de acceso se envía el mensaje a la salida de error estándar y el
	 * programa continua.
	 * 
	 * @param listaTextos el texto a escribir, línea a línea
	 * @return si se ha completado la operación
	 */
	public boolean escribir(List<String> listaTextos) {
		boolean escritoCorrecto = true;

		// Usando try-with-resources para gestionar el PrintWriter y asegurar el cierre
		// del recurso
		try (PrintWriter writer = new PrintWriter(new FileWriter(new File(rutaArchivo)))) {
			for (String linea : listaTextos) {
				writer.println(linea); // Escribir una nueva línea
			}
		} catch (IOException e) {
			System.err.println("Error al escribir en el archivo: " + e.getMessage());
			escritoCorrecto = false;
		}

		// Devolver el número de líneas efectivamente escritas
		return escritoCorrecto;
	}

	/**
	 * Crea y carga una lista de líneas de texto con el contenido del archivo.. En
	 * caso de producirse algún error de acceso se envía el mensaje a la salida de
	 * error estándar y el programa continua.
	 * 
	 * @return la colección de líneas de texto; <code>null<code> en caso de error de
	 *         acceso o si el archivo está vacío
	 */
	public List<String> leer() {
		List<String> lineas = new ArrayList<>();
		boolean continuarLeyendo = true; // Flag para controlar el bucle de lectura

		// Usando try-with-resources para gestionar el BufferedReader y asegurar el
		// cierre del recurso
		try (BufferedReader reader = new BufferedReader(new FileReader(new File(rutaArchivo)))) {
			String linea;
			// Bucle while con flag para leer línea por línea
			while (continuarLeyendo) {
				linea = reader.readLine();
				if (linea == null) {
					continuarLeyendo = false; // Si no hay más líneas, salimos del bucle
				} else {
					lineas.add(linea); // Agregar la línea a la lista
				}
			}
		} catch (IOException e) {
			System.out.println("Error al leer el archivo: " + e.getMessage());
		}

		// Devolver una lista inmutable
		return Collections.unmodifiableList(lineas); // Lista inmutable
	}
}
