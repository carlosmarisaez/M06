package Ilerna.pacDesarrollo.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.hibernate.query.Query;
import org.hibernate.Session;

import Ilerna.pacDesarrollo.dao.LectorDAO;
import Ilerna.pacDesarrollo.dao.LibroDAO;
import Ilerna.pacDesarrollo.dao.PrestamoDAO;
import Ilerna.pacDesarrollo.model.Lector;
import Ilerna.pacDesarrollo.model.Libro;
import Ilerna.pacDesarrollo.model.Prestamo;

public class Main {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		menu();
		
	}

	private static void prestarLibro(long lectorId, long libroId, LocalDate fechaPrestamo) {
		Libro libro = LibroDAO.getLibroPorId( libroId);
		if (libro == null) {
			System.out.println("No se encontró un libro con el ID proporcionado.");
			return;
		}
		System.out.println("Libro: " + libro.getId());
		Lector lector = LectorDAO.getLectorPorId(lectorId);
		if (lector == null) {
			System.out.println("No se encontró un lector con el ID proporcionado.");
			return;
		}
		System.out.println("Lector: " + lector);
		Prestamo prestamo = new Prestamo();
		prestamo.setLector(lector);
		prestamo.setLibro(libro);
		prestamo.setFechaPrestamo(fechaPrestamo);
		if (libro.isDisponible()) {
			PrestamoDAO.crearPrestamo(prestamo);
			LibroDAO.actualizarLibro(libroId, libro.getAutor(), libro.getTitulo(), libro.getFechaPublicacion(), false);
		} else
			System.out.println("No se puede prestar el Libro");
	}

	private static void devolverLibro(long libroId, LocalDate fechaDevolucion) {
		Libro libro = LibroDAO.getLibroPorId(libroId);
		if (libro == null) {
			System.out.println("No se encontró un libro con el ID proporcionado.");
			return;
		}
		Session session = HibernateSession.openSession();
		session.beginTransaction();
		Prestamo prestamo;
		String hql = "SELECT p FROM Prestamo p JOIN p.libro lib WHERE lib.id = :idLibro AND p.fechaDevolucion is null AND lib.disponible = false";
		Query query = session.createQuery(hql);
		query.setParameter("idLibro", libroId);
		List<Prestamo> prestamos = query.list();
		if (prestamos.isEmpty()) {
			System.out.println("El libro no esta prestado");
			session.close();
		} else {
			prestamo = prestamos.get(0);
			prestamo.setFechaDevolucion(fechaDevolucion);
			PrestamoDAO.actualizarPrestamo(prestamo);
			LibroDAO.actualizarLibro( libro.getId(), libro.getAutor(), libro.getTitulo(), libro.getFechaPublicacion(),
					true);
			System.out.println("Devolucion realizada");
		}
	}

	private static List<Libro> librosPrestadosLector(long idLector) {
		Lector lector = LectorDAO.getLectorPorId(idLector);
		if (lector == null) {
			System.out.println("No se encontró un lector con el ID proporcionado.");
			return Collections.emptyList();
		}
		Session session = HibernateSession.openSession();
		session.beginTransaction();
		String hql = "SELECT p.libro FROM Prestamo p JOIN p.lector lec WHERE lec.id = :idLector AND p.libro.disponible = false";
		Query query = session.createQuery(hql);
		query.setParameter("idLector", idLector);
		List<Libro> libros = query.list();
		List<Libro> librosSinRepetir = null;
		if (!libros.isEmpty()) {
			session.getTransaction().commit();
			session.close();
			Set<Libro> libroSet = new HashSet<Libro>(libros);
			librosSinRepetir = new ArrayList<Libro>(libroSet);
		} else {
			System.out.println("Lista Vacia");
			return Collections.emptyList();
		}
		return librosSinRepetir;

	}

	private static List<Libro> librosDisponibles() {
		Session session = HibernateSession.openSession();
		session.beginTransaction();
		String hql = "FROM Libro l WHERE l.disponible = true";
		Query query = session.createQuery(hql);
		List<Libro> libros = query.list();
		if (!libros.isEmpty()) {
			session.getTransaction().commit();
			session.close();
		} else {
			System.out.println("Lista Vacia");
			return Collections.emptyList();
		}
		return libros;
	}

	private static List<Prestamo> obtenerHistorialPrestamosPorLector(Long idLector) {
		Lector lector = LectorDAO.getLectorPorId(idLector);
		if (lector == null) {
			System.out.println("No se encontró un lector con el ID proporcionado.");
			return Collections.emptyList();
		}
		Session session = HibernateSession.openSession();
		session.beginTransaction();
		String hql = "FROM Prestamo p WHERE p.lector.id = :idLector ORDER BY p.fechaPrestamo DESC";
		Query query = session.createQuery(hql);
		query.setParameter("idLector", idLector);
		List<Prestamo> prestamos = query.list();
		if (!prestamos.isEmpty()) {
			session.getTransaction().commit();
			session.close();
			return prestamos;
		} else {
			System.out.println("Lista Vacia");
			session.getTransaction().commit();
			session.close();
			return Collections.emptyList();
		}

	}

	private static void mensaje() {
		System.out.println("--------------------------------\n" + "           BIBLIOTECA             \n"
				+ "--------------------------------\n" + "1-  Insertar libro\n" + "2-  Insertar lector\n"
				+ "3-  Listado de libros\n" + "4-  Listado de lectores\n" + "5-  Prestar Libro\n"
				+ "6-  Devolver Libro\n" + "7-  Ver libro por ID\n" + "8-  Ver lector por ID\n"
				+ "9-  Ver libros actualmente prestados a un Lector\n" + "10- Ver libros disponibles para Préstamo\n"
				+ "11- Ver Historial de prestamos\n" + "12- Salir\n" + "--------------------------------");
	}

	private static void menu() {
		int opcion = 0;
		while (opcion != 12) {
			mensaje();
			Scanner sc = new Scanner(System.in);

			while (!sc.hasNextInt()) {
				System.out.println("Valor introducido no valido\n" + "--------------------------------");
				sc.nextLine();
			}
			opcion = sc.nextInt();

			switch (opcion) {
			case 1:
				insertarLibro();
				break;
			case 2:
				insertarLector();
				break;
			case 3:
				listadoLibros();
				break;
			case 4:
				listadoLectores();
				break;
			case 5:
				prestarLibroLector();
				break;
			case 6:
				devolverLibroLector();
				break;
			case 7:
				verLibroPorID();
				break;
			case 8:
				verLectroPorId();
				break;
			case 9:
				verlibrosPrestadosLector();
				break;
			case 10:
				verlibrosDisponibles();
				break;
			case 11:
				verobtenerHistorialPrestamosPorLector();
				break;
			case 12:
				System.out.println("--------------------------------\n         PROGRAMA CERRADO");
				break;
			default:
				System.out.println("Valor inválido. Ingrese un número entero entre 1 y 12");
				break;
			}

		}
		/*
		 * while (opcion < 1 || opcion > 12) { if (sc.hasNextInt()) { opcion =
		 * sc.nextInt(); if (opcion < 1 || opcion > 12) {
		 * System.out.println("Valor inválido. Ingrese un número entero entre 1 y 12\n"
		 * + "--------------------------------"); } } else { System.out.println(
		 * "Valor inválido. Ingrese un número entero entre 1 y 12\n" +
		 * "--------------------------------"); sc.nextLine(); // Limpiamos el buffer }
		 * } sc.nextLine(); // Limpiamos el buffer
		 */
		if (opcion != 12)
			System.out.println("Seleccione una opción: " + opcion + "\n--------------------------------");

	}

	private static void insertarLibro() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Ingrese el título: ");
		String titulo = sc.nextLine();
		System.out.print("\nIngrese un autor: ");
		String autor = sc.nextLine();
		System.out.print("\nIngrese la fecha de publicación: ");
		while (!sc.hasNextInt()) {
			System.out.println("Valor introducido no valido");
			sc.nextLine();
		}
		int fecha = sc.nextInt();
		Libro libro = new Libro();
		libro.setTitulo(titulo);
		libro.setAutor(autor);
		libro.setFechaPublicacion(fecha);
		libro.setDisponible(true);
		LibroDAO.crearLibro(libro);
	}

	private static void insertarLector() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Ingrese el nombre: ");
		String nombre = sc.nextLine();
		System.out.print("\nIngrese el apellido: ");
		String apellido = sc.nextLine();
		System.out.print("\nIngrese el email: ");
		String email = sc.nextLine();
		Lector lector = new Lector();
		lector.setNombre(nombre);
		lector.setApellidos(apellido);
		lector.setEmail(email);
		LectorDAO.crearLector(lector);
	}

	private static void listadoLibros() {
		List<Libro> libros = LibroDAO.getAllLibros();
		if (!libros.isEmpty()) {
			for (Libro libro : libros) {

				System.out.println(libro.toString());
			}
		} else
			System.out.println("No hay libros en la BBDD");
	}

	private static void listadoLectores() {
		List<Lector> lectores = LectorDAO.getAllLectores();
		if (!lectores.isEmpty()) {
			for (Lector lector : lectores) {
				System.out.println(lector.toString());
			}
		} else
			System.out.println("No hay lectores en la BBDD");
	}

	private static void prestarLibroLector() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Introduce ID libro: ");
		while (!sc.hasNextLong()) {
			System.out.println("Valor introducido no valido");
			sc.nextLine();
		}
		Long libro = sc.nextLong();
		sc.nextLine();
		Libro libro1 = LibroDAO.getLibroPorId(libro);
		if (libro1 == null) {
			System.out.println("No se encontró un libro con el ID proporcionado.");
			return;
		}
		System.out.print("Introduce ID lector: ");
		while (!sc.hasNextLong()) {
			System.out.println("Valor introducido no valido");
			sc.nextLine();
		}
		long lector = sc.nextLong();
		sc.nextLine();
		Lector lector1 = LectorDAO.getLectorPorId(lector);
		if (lector1 == null) {
			System.out.println("No se encontró un lector con el ID proporcionado.");
			return;
		}
		System.out.print("Introduce fecha (formato yyyy-MM-dd):");
		String strfecha = sc.nextLine();
		LocalDate fechaPrestamo = null;
		// Expresión regular para validar el formato de la fecha
		String regex = "^\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$";
		// Validamos la fecha con la expresión regular
		while (!strfecha.matches(regex)) {
			System.out.println("Formato de fecha no válido. Introduce fecha (formato yyyy-MM-dd):");
			strfecha = sc.nextLine();
		}
		System.out.println();
		// Convertimos la fecha de string a LocalDate
		fechaPrestamo = LocalDate.parse(strfecha);
		prestarLibro(libro, lector, fechaPrestamo);

	}

	private static void devolverLibroLector() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Introduce ID libro: ");
		while (!sc.hasNextLong()) {
			System.out.println("Valor introducido no valido");
			sc.nextLine();
		}
		Long libro = sc.nextLong();
		sc.nextLine();
		Libro libro1 = LibroDAO.getLibroPorId(libro);
		if (libro1 == null) {
			System.out.println("No se encontró un libro con el ID proporcionado.");
			return;
		}
		System.out.println("Introduce fecha (formato yyyy-MM-dd):");
		String strfecha = sc.nextLine();
		LocalDate fechaPrestamo = null;
		// Expresión regular para validar el formato de la fecha
		String regex = "^\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$";
		// Validamos la fecha con la expresión regular
		while (!strfecha.matches(regex)) {
			System.out.println("Formato de fecha no válido. Introduce fecha (formato yyyy-MM-dd):");
			strfecha = sc.nextLine();
		}
		// Convertimos la fecha de string a LocalDate
		fechaPrestamo = LocalDate.parse(strfecha);
		devolverLibro(libro, fechaPrestamo);
	}

	private static void verLibroPorID() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Intorduzca ID del libro: ");
		System.out.println();
		while (!sc.hasNextLong()) {
			System.out.println("Valor introducido no valido");
			sc.nextLine();
		}
		long idlibro = sc.nextLong();
		Libro libro = LibroDAO.getLibroPorId( idlibro);
		if (libro == null) {
			System.out.println("No se encontró un libro con el ID proporcionado.");
			return;
		} else
			System.out.println(libro.toString());

	}

	private static void verLectroPorId() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Intorduzca ID del lector: ");
		System.out.println();
		while (!sc.hasNextLong()) {
			System.out.println("Valor introducido no valido");
			sc.nextLine();
		}
		long idlector = sc.nextLong();
		Lector lector = LectorDAO.getLectorPorId(idlector);
		if (lector == null) {
			System.out.println("No se encontró un lector con el ID proporcionado.");
			return;
		} else
			System.out.println(lector.toString());

	}

	private static void verlibrosPrestadosLector() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Intorduzca ID del lector: ");
		System.out.println();
		long idlector = 0;
		while (!sc.hasNextLong()) {
			System.out.println("Valor introducido no valido");
			sc.nextLine();
		}
		idlector = sc.nextLong();
		Lector lector = LectorDAO.getLectorPorId(idlector);
		if (lector == null) {
			System.out.println("No se encontró un lector con el ID proporcionado.");
			return;
		}
		List<Libro> libros = librosPrestadosLector(idlector);
		for (Libro libro : libros) {
			System.out.println(libro.toString());
		}
	}

	private static void verlibrosDisponibles() {
		List<Libro> libros = librosDisponibles();
		for (Libro libro : libros) {
			System.out.println(libro.toString());
		}
	}

	private static void verobtenerHistorialPrestamosPorLector() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Intorduzca ID del lector: ");
		System.out.println();
		long idlector = 0;
		while (!sc.hasNextLong()) {
			System.out.println("Valor introducido no valido");
			sc.nextLine();
		}
		idlector = sc.nextLong();

		List<Prestamo> prestamos = obtenerHistorialPrestamosPorLector(idlector);
		for (Prestamo prestamo : prestamos) {
			System.out.println(prestamo.toString());
		}
	}
}
