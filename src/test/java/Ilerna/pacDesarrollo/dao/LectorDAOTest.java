package Ilerna.pacDesarrollo.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.*;

import Ilerna.pacDesarrollo.model.Lector;

public class LectorDAOTest {

	private static Lector lector1;
	private static Lector lector2;

	@Before
	public void setUp() {
		lector1 = new Lector("nombre1", "apellido1", "email1");
		lector2 = new Lector("nombre2", "apellido2", "email2");

		LectorDAO.crearLector(lector1);
		LectorDAO.crearLector(lector2);
	}

	@After
	public void tearDown() {
		// Eliminar los libros de la base de datos después de cada prueba
		LectorDAO.borrarLector(lector1.getId());
		LectorDAO.borrarLector(lector2.getId());
	}

	@Test
	public void testCrearLector() {
		Lector nuevoLector = new Lector("nombre3", "apellido3", "email3");
		LectorDAO.crearLector(nuevoLector);

		Lector lector = LectorDAO.getLectorPorId(nuevoLector.getId());
		assertNotNull(lector);
		assertEquals("nombre3", lector.getNombre());
		assertEquals("apellido3", lector.getApellidos());
		assertEquals("email3", lector.getEmail());
	}

	@Test
	public void testLeerLector() {
		// Comprobar que el libro se puede leer correctamente
		Lector lector = LectorDAO.getLectorPorId(lector1.getId());
		assertNotNull(lector);
		assertEquals("nombre1", lector.getNombre());
		assertEquals("apellido1", lector.getApellidos());
		assertEquals("email1", lector.getEmail());
	}

	@Test
	public void testActualizarLector() {
		// Obtener el libro1 antes de la actualización
		Lector lectorAntes = LectorDAO.getLectorPorId(lector1.getId());
		System.out.println(lectorAntes);

		// Actualizar el libro1
		LectorDAO.actualizarLector(lector1.getId(), "nombreActualizado", "apellidoActualizado", "emailActualizado");

		// Obtener el libro1 después de la actualización
		Lector lectorDespues = LectorDAO.getLectorPorId(lector1.getId());

		// Comprobar que los atributos del libro se han actualizado correctamente
		assertNotNull(lectorAntes);
		assertNotNull(lectorDespues);
		assertEquals("nombreActualizado", lectorDespues.getNombre());
		assertEquals("apellidoActualizado", lectorDespues.getApellidos());
		assertEquals("emailActualizado", lectorDespues.getEmail());
	}

	@Test
	public void testBorrarLector() {
		// Eliminar el libro2
		LectorDAO.borrarLector(lector2.getId());

		// Comprobar que el libro se ha eliminado correctamente
		Lector lector = LectorDAO.getLectorPorId(lector2.getId());
		assertNull(lector);
	}

	@Test
	public void testGetAllLiector() {
		// Comprobar que se pueden obtener todos los libros
		List<Lector> lectores = LectorDAO.getAllLectores();
		System.out.println(lectores.size());
		assertNotNull(lectores);
		assertEquals(2, lectores.size());		
		assertTrue(lectores.contains(lector1));
		assertTrue(lectores.contains(lector2));
	}

	@Test
	public void testGetAllLectors() {
		// Comprobar que se pueden obtener todos los libros
		List<Lector> lectores = LectorDAO.getAllLectores();
		System.out.println(lectores.size());
		assertNotNull(lectores);
		assertEquals(2, lectores.size());
		System.out.println(lector1);
		System.out.println(lector2);
		for (Lector lector : lectores) {
			System.out.println(lector);
		}
		assertTrue(lectores.contains(lector1));
		assertTrue(lectores.contains(lector2));
	}

}
