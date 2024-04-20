package Ilerna.pacDesarrollo.dao;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Ilerna.pacDesarrollo.model.Lector;
import Ilerna.pacDesarrollo.model.Libro;
import Ilerna.pacDesarrollo.model.Prestamo;


public class PrestamoDAOTest {

	private static Prestamo prestamo1;
	private static Prestamo prestamo2;
	private static Lector lector;
	private static Libro libro1;
	private static Libro libro2;

	@Before
	public void setUp() {

		lector = new Lector("John", "Doe", "johndoe@example.com");
		LectorDAO.crearLector(lector);

		libro1 = new Libro("Titulo1", "Autor1", 2000, true);
		libro2 = new Libro("Titulo2", "Autor2", 2010, true);
		LibroDAO.crearLibro(libro1);
		LibroDAO.crearLibro(libro2);

		prestamo1 = new Prestamo(lector, libro1, LocalDate.now(), LocalDate.now().plusDays(7));
		prestamo2 = new Prestamo(lector, libro2, LocalDate.now(), LocalDate.now().plusDays(7));
		PrestamoDAO.crearPrestamo(prestamo1);
		PrestamoDAO.crearPrestamo(prestamo2);
	}

	@After
	public void tearDown() {
		// Eliminar los prestamos, libros y lectores de la base de datos después de cada
		// prueba
		PrestamoDAO.borrarPrestamo(prestamo1.getId());
		PrestamoDAO.borrarPrestamo(prestamo2.getId());
		LibroDAO.borrarLibro(libro1.getId());
		LibroDAO.borrarLibro(libro2.getId());
		LectorDAO.borrarLector(lector.getId());
	}

	@Test
	public void testCrearPrestamo() {
		// Crear un nuevo prestamo
		Prestamo nuevoPrestamo = new Prestamo(lector, libro1, LocalDate.now(), LocalDate.now().plusDays(7));
		PrestamoDAO.crearPrestamo(nuevoPrestamo);

		// Comprobar que el prestamo se ha creado correctamente
		Prestamo prestamo = PrestamoDAO.leerPrestamo(nuevoPrestamo.getId());
		assertNotNull(prestamo);
		assertEquals(lector.getId(), prestamo.getLector().getId());
		assertEquals(libro1.getId(), prestamo.getLibro().getId());
		assertEquals(LocalDate.now(), prestamo.getFechaPrestamo());
		assertEquals(LocalDate.now().plusDays(7), prestamo.getFechaDevolucion());

		// Eliminar el prestamo creado en la prueba
		PrestamoDAO.borrarPrestamo(nuevoPrestamo.getId());
	}

	@Test
	public void testLeerPrestamo() {
		// Comprobar que el prestamo se puede leer correctamente
		Prestamo prestamo = PrestamoDAO.leerPrestamo(prestamo1.getId());
		assertNotNull(prestamo);
		assertEquals(lector.getId(), prestamo.getLector().getId());
		assertEquals(libro1.getId(), prestamo.getLibro().getId());
		assertEquals(LocalDate.now(), prestamo.getFechaPrestamo());
		assertEquals(LocalDate.now().plusDays(7), prestamo.getFechaDevolucion());

	}

	@Test
	public void testActualizarPrestamo() {
		// Actualizar el prestamo1
		PrestamoDAO.actualizarPrestamo(prestamo1.getId(), lector, libro2, LocalDate.now(),
				LocalDate.now().plusDays(14));
		// Comprobar que los atributos del prestamo se han actualizado correctamente
		Prestamo prestamo = PrestamoDAO.leerPrestamo(prestamo1.getId());
		assertNotNull(prestamo);
		assertEquals(lector.getId(), prestamo.getLector().getId());
		assertEquals(libro2.getId(), prestamo.getLibro().getId());
		assertEquals(LocalDate.now(), prestamo.getFechaPrestamo());
		assertEquals(LocalDate.now().plusDays(14), prestamo.getFechaDevolucion());
	}

	@Test
	public void testBorrarPrestamo() {
		// Eliminar el prestamo2
		PrestamoDAO.borrarPrestamo(prestamo2.getId());
		// Comprobar que el prestamo se ha eliminado correctamente
		Prestamo prestamo = PrestamoDAO.leerPrestamo(prestamo2.getId());
		assertNull(prestamo);
	}
}
