package Ilerna.pacDesarrollo.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Ilerna.pacDesarrollo.model.Libro;

public class LibroDAOTest {

	private static Libro libro1;
	private static Libro libro2;

	@Before
	public void setUp() {

		libro1 = new Libro("Titulo1", "Autor1", 2000, true);
		libro2 = new Libro("Titulo2", "Autor2", 2010, true);

		LibroDAO.crearLibro(libro1);
		LibroDAO.crearLibro(libro2);
	}

	@After
	public void tearDown() {
		// Eliminar los libros de la base de datos después de cada prueba
		LibroDAO.borrarLibro(libro1.getId());
		LibroDAO.borrarLibro(libro2.getId());
	}

	@Test
	public void testCrearLibro() {
		// Crear un nuevo libro
		Libro nuevoLibro = new Libro("Titulo3", "Autor3", 2020, true);
		LibroDAO.crearLibro(nuevoLibro);

		// Comprobar que el libro se ha creado correctamente
		Libro libro = LibroDAO.getLibroPorId(nuevoLibro.getId());
		assertNotNull(libro);
		assertEquals("Titulo3", libro.getTitulo());
		assertEquals("Autor3", libro.getAutor());
		assertEquals(2020, libro.getFechaPublicacion());
		assertTrue(libro.isDisponible());

		// Eliminar el libro creado en la prueba
		LibroDAO.borrarLibro(nuevoLibro.getId());
	}

	@Test
	public void testLeerLibro() {
		// Comprobar que el libro se puede leer correctamente
		Libro libro = LibroDAO.getLibroPorId(libro1.getId());
		assertNotNull(libro);
		assertEquals("Titulo1", libro.getTitulo());
		assertEquals("Autor1", libro.getAutor());
		assertEquals(2000, libro.getFechaPublicacion());
		assertTrue(libro.isDisponible());
	}

	@Test
	public void testActualizarLibro() {
		// Obtener el libro1 antes de la actualización
		Libro libroAntes = LibroDAO.getLibroPorId(libro1.getId());
		System.out.println(libroAntes);

		// Actualizar el libro1
		LibroDAO.actualizarLibro(libro1.getId(), "Autor1Actualizado", "Titulo1Actualizado", 2022, false);

		// Obtener el libro1 después de la actualización
		Libro libroDespues = LibroDAO.getLibroPorId(libro1.getId());

		// Comprobar que los atributos del libro se han actualizado correctamente
		assertNotNull(libroAntes);
		assertNotNull(libroDespues);
		assertEquals("Titulo1Actualizado", libroDespues.getTitulo());
		assertEquals("Autor1Actualizado", libroDespues.getAutor());
		assertEquals(2022, libroDespues.getFechaPublicacion());
		assertFalse(libroDespues.isDisponible());
	}

	@Test
	public void testBorrarLibro() {
		// Eliminar el libro2
		LibroDAO.borrarLibro(libro2.getId());

		// Comprobar que el libro se ha eliminado correctamente
		Libro libro = LibroDAO.getLibroPorId(libro2.getId());
		assertNull(libro);
	}

	@Test
	public void testGetLibroPorId() {
		// Comprobar que el libro se puede obtener correctamente por su ID
		Libro libro = LibroDAO.getLibroPorId(libro1.getId());
		assertNotNull(libro);
		assertEquals("Titulo1", libro.getTitulo());
		assertEquals("Autor1", libro.getAutor());
		assertEquals(2000, libro.getFechaPublicacion());
		assertTrue(libro.isDisponible());
	}

	@Test
	public void testGetAllLibros() {
		// Comprobar que se pueden obtener todos los libros
		List<Libro> libros = LibroDAO.getAllLibros();		
		assertNotNull(libros);
		assertEquals(2, libros.size());		
		assertTrue(libros.contains(libro1));
		assertTrue(libros.contains(libro2));
	}
}
