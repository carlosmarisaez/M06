package Ilerna.pacDesarrollo.dao;

import java.time.LocalDate;

import org.hibernate.Session;

import Ilerna.pacDesarrollo.model.Lector;
import Ilerna.pacDesarrollo.model.Libro;
import Ilerna.pacDesarrollo.model.Prestamo;
import Ilerna.pacDesarrollo.util.HibernateSession;

public class PrestamoDAO {
	private static Session session;

	public static void crearPrestamo(Prestamo prestamo) {
		session = HibernateSession.openSession();
		session.beginTransaction();
		session.save(prestamo);
		session.getTransaction().commit();
		System.out.println("Prestamo insertado");
		session.close();

	}

	public static Prestamo leerPrestamo(Long long1) {
		session = HibernateSession.openSession();
		session.beginTransaction();
		Prestamo prestamo = session.get(Prestamo.class, long1);
		session.getTransaction().commit();
		session.close();
		return prestamo;
	}

	public static void actualizarPrestamo(Prestamo prestamo) {
		session = HibernateSession.openSession();
		session.beginTransaction();
		session.update(prestamo);
		session.getTransaction().commit();
		session.close();
	}

	public static void borrarPrestamo(long id) {
		session = HibernateSession.openSession();
		session.beginTransaction();
		Prestamo prestamo = session.get(Prestamo.class, id);
		if (prestamo != null) {
			session.delete(prestamo);
			session.getTransaction().commit();
		}
		session.close();
	}

	public static void actualizarPrestamo(Long id, Lector lector, Libro libro, LocalDate now, LocalDate plusDays) {
		// TODO Auto-generated method stub
		session = HibernateSession.openSession();
		session.beginTransaction();
		Prestamo prestamo = new Prestamo();
		prestamo.setId(id);
		prestamo.setLector(lector);
		prestamo.setLibro(libro);
		prestamo.setFechaPrestamo(now);
		prestamo.setFechaDevolucion(plusDays);
		session.update(prestamo);
		session.getTransaction().commit();
		session.close();
	}

}
