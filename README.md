Proyecto pacDesarrollo

Este proyecto es una aplicación de gestión de préstamos de libros para una biblioteca. Utiliza Hibernate como ORM para la persistencia de datos en una base de datos MySQL.

Tecnologías utilizadas

Java

Maven

Hibernate

MySQL

JUnit

Configuración

Para configurar el proyecto, se deben seguir los siguientes pasos:

1. Clonar el repositorio del proyecto.
2. Importar el proyecto en Eclipse como un proyecto Maven existente.
3. Configurar la base de datos MySQL con el nombre "pacDesarrollo" y el usuario y contraseña "root".
4. Actualizar el archivo "hibernate.cfg.xml" con la configuración adecuada para la base de datos.
5. Ejecutar el proyecto como una aplicación Java.

Archivos importantes

- pom.xml: archivo de configuración de Maven que define las dependencias del proyecto.

- hibernate.cfg.xml: archivo de configuración de Hibernate que define la configuración de la conexión a la base de datos y las clases de mapeo.

- Ilerna/pacDesarrollo/model: paquete que contiene las clases de modelo de la aplicación.

- Ilerna/pacDesarrollo/dao: paquete que contiene las clases de acceso a datos de la aplicación.

- Ilerna/pacDesarrollo/util: paquete que contiene las clases utilitarias de la aplicación.

Clases importantes

- Libro: clase que representa un libro en la aplicación.
- Lector: clase que representa un lector en la aplicación.
- Prestamo: clase que representa un préstamo de un libro a un lector en la aplicación.
- LibroDAO: clase que proporciona métodos para el acceso a datos de la clase Libro.
- LectorDAO: clase que proporciona métodos para el acceso a datos de la clase Lector.
- PrestamoDAO: clase que proporciona métodos para el acceso a datos de la clase Prestamo.
- HibernateSession: clase utilitaria que proporciona métodos para la gestión de sesiones de Hibernate.

Pruebas

El proyecto incluye pruebas unitarias con JUnit para las clases DAO. Para ejecutar las pruebas, se debe ejecutar la clase de prueba correspondiente como una prueba JUnit en Eclipse.

