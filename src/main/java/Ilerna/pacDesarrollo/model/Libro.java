package Ilerna.pacDesarrollo.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity

public class Libro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "ID")
	private Long id;
	@Column (name = "TITULO")
	private String titulo;
	@Column (name = "AUTOR")
	private String autor;
	@Column(name = "FECHA_PUBLICACION")
	private int fechaPublicacion;
	@Column (name = "DISPONIBLE")
	private boolean disponible;

	@OneToMany(mappedBy = "libro")
	private List<Prestamo> prestamos;

	public Libro() {
		super();
	}

	public Libro(String titulo, String autor, int fechaPublicacion, boolean disponible) {
		super();
		this.titulo = titulo;
		this.autor = autor;
		this.fechaPublicacion = fechaPublicacion;
		this.disponible = disponible;
	}



	// Getters y setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public int getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(int fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}


	public List<Prestamo> getPrestamos() {
		return prestamos;
	}

	public void setPrestamos(List<Prestamo> prestamos) {
		this.prestamos = prestamos;
	}

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    Libro libro = (Libro) o;
	    return id == libro.id &&
	            Objects.equals(titulo, libro.titulo) &&
	            Objects.equals(autor, libro.autor) &&
	            fechaPublicacion == libro.fechaPublicacion &&
	            disponible == libro.disponible;
	}

	@Override
	public int hashCode() {
	    return Objects.hash(id, titulo, autor, fechaPublicacion, disponible);
	}
	@Override
	public String toString() {
		return "Libro [id=" + id + ", titulo=" + titulo + ", autor=" + autor + ", fechaPublicacion=" + fechaPublicacion
				+ ", disponible=" + disponible + "]";
	}
}
