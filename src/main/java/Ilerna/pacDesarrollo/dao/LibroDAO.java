package Ilerna.pacDesarrollo.dao;


import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.hibernate.Session;

import org.hibernate.Transaction;

import Ilerna.pacDesarrollo.model.Libro;
import Ilerna.pacDesarrollo.util.HibernateSession;

public class LibroDAO {
	
	private static Session session;
	private static Transaction tx;
	
    
	public static void crearLibro(Libro libro) {
		
		session = HibernateSession.openSession();
		tx = null;

		try {
			tx = session.beginTransaction();			
			session.save(libro);
			tx.commit();
			System.out.println("Libro Insertado Correctamente");
		} catch (Exception e) {
			// TODO: handle exception
			if (tx != null)
				tx.rollback();
			System.out.println("Error al insertar Libro");
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

	public static void leerLibro(long idLibro) {	
		session = HibernateSession.openSession();
		session.beginTransaction();
		tx = null;
		try {
			Libro libro = (Libro) session.load(Libro.class, idLibro);
			System.out.println(libro.toString());
		} catch (HibernateException e) {
			// TODO: handle exception
			if (tx != null)
				tx.rollback();
			System.out.println("No se ha podido actualizar Libro");
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public static void actualizarLibro(long idLibro, String autor, String titulo, int fechaPublicacion, boolean disponible) {
		session = HibernateSession.openSession();
		
		tx = null;
		try {
			tx = session.beginTransaction();
			Libro libro = (Libro) session.load(Libro.class, idLibro);
			if (libro != null) {
				libro.setTitulo(titulo);
				libro.setAutor(autor);
				libro.setFechaPublicacion(fechaPublicacion);
				libro.setDisponible(disponible);
				session.update(libro);
				tx.commit();
				System.out.println("Libro actualizado");
			}
		} catch (HibernateException e) {
			// TODO: handle exception
			if (tx != null)
				tx.rollback();
			System.out.println("No se ha podido actualizar Libro");
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

	public static void borrarLibro(long idLibro) {
		session = HibernateSession.openSession();
		
		tx = null;
		try {
			tx = session.beginTransaction();
			Libro libro = (Libro) session.get(Libro.class, idLibro);
			if (libro != null) {
				session.delete(libro);
				tx.commit();
				System.out.println("Libro borrado");
			}
		} catch (HibernateException e) {
			// TODO: handle exception
			if (tx != null)
				tx.rollback();
			System.out.println("No se ha podido actualizar Libro");
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	public static Libro getLibroPorId(long id) {		
		session = HibernateSession.openSession();
		session.beginTransaction();
	    Libro libro = session.get(Libro.class, id);	    
	    session.getTransaction().commit();
	    session.close();
	    return libro;
	}

	public static List<Libro> getAllLibros(){
		session = HibernateSession.openSession();
	    session.beginTransaction();
	    String hql = "FROM Libro";
		Query query = session.createQuery(hql);
		List<Libro> libros = query.list();
		session.getTransaction().commit();
		session.close();
		return libros;
	}
	
}