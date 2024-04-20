package Ilerna.pacDesarrollo.dao;

import java.util.List;

import org.hibernate.HibernateException;

import org.hibernate.Session;

import org.hibernate.Transaction;
import org.hibernate.query.Query;

import Ilerna.pacDesarrollo.model.Lector;

import Ilerna.pacDesarrollo.util.HibernateSession;

public class LectorDAO {

	private static Session session;
	private static Transaction tx;

	public static void crearLector(Lector lector) {

		session = HibernateSession.openSession();
		tx = null;

		try {
			tx = session.beginTransaction();
			session.save(lector);
			tx.commit();
			System.out.println("Lector Insertado Correctamente");
		} catch (Exception e) {
			// TODO: handle exception
			if (tx != null)
				tx.rollback();
			System.out.println("Error al insertar Lector");
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

	public static void leerLector(long idLector) {
		session = HibernateSession.openSession();
		tx = null;
		try {
			Lector lector = (Lector) session.load(Lector.class, idLector);
			System.out.println(lector.toString());
		} catch (HibernateException e) {
			// TODO: handle exception
			if (tx != null)
				tx.rollback();
			System.out.println("No se ha podido leer Lector");
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public static void actualizarLector(long idLector, String nombre, String apellido, String email) {
		session = HibernateSession.openSession();
		tx = null;
		try {
			tx = session.beginTransaction();
			Lector lector = (Lector) session.load(Lector.class, idLector);
			if (lector != null) {
				lector.setNombre(nombre);
				lector.setApellidos(apellido);
				lector.setEmail(email);
				session.update(lector);
				tx.commit();
				System.out.println("Lector actualizado");
			}
		} catch (HibernateException e) {
			// TODO: handle exception
			if (tx != null)
				tx.rollback();
			System.out.println("No se ha podido actualizar Lector");
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

	public static void borrarLector(long idLector) {
		session = HibernateSession.openSession();
		tx = null;
		try {
			tx = session.beginTransaction();
			Lector lector = (Lector) session.get(Lector.class, idLector);
			if (lector != null) {
				session.delete(lector);
				tx.commit();
				System.out.println("Lector borrado");
			}
		} catch (HibernateException e) {
			// TODO: handle exception
			if (tx != null)
				tx.rollback();
			System.out.println("No se ha podido actualizar Lector");
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public static Lector getLectorPorId(Long id) {
		session = HibernateSession.openSession();
		Transaction transaction = session.beginTransaction();
		Lector lector = session.get(Lector.class, id);
		transaction.commit();
		session.close();
		return lector;
	}

	public static List<Lector> getAllLectores() {
		session = HibernateSession.openSession();
		session.beginTransaction();
		String hql = "FROM Lector";
		Query query = session.createQuery(hql);
		List<Lector> lector = query.list();
		session.getTransaction().commit();
		session.close();
		return lector;
	}
}