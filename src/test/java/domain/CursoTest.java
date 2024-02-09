package domain;

import domain.usuarios.Docente;
import domain.usuarios.Estudiante;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CursoTest {
  // 1)
  //  Como docente, deseo poder crear N grupos de trabajo de un tamaño ideal M, vacíos,
  //  para permitir la inscripción de estudiantes a cada grupo
  @Test
  public void crear3gruposDe4integrantes() {
    Curso curso = new Curso();
    curso.crearNgruposDeMintegrantes(3, 4);

    assertEquals(3, curso.grupos.size());
  }

  @Test
  public void crear4gruposDe3integrantes() {
    Curso curso = new Curso();
    curso.crearNgruposDeMintegrantes(4, 3);

    assertEquals(4, curso.grupos.size());
  }

  // 4)
  // Como docente, deseo poder cerrar un grupo que tiene tamaño M
  @Test
  public void sePuedeCerrarUnGrupoDeTamanioM() {
    Curso curso = new Curso();
    Docente docente = new Docente("Franco", "franco@gmail.com");
    curso.crearNgruposDeMintegrantes(1, 1);
    curso.agregarDocente(docente);

    Grupo grupo = curso.grupos.get(0);
    Estudiante estudiante = new Estudiante("Juan", "juan@gmail.com");

    grupo.inscribir(estudiante);
    curso.cerrarGrupo(grupo, docente);

    assertTrue(grupo.estaCerrado);
  }

  @Test
  public void seCreaLaSolicitudDeCierreDeUnGrupoDeTamanioMenor() {
    Curso curso = new Curso();
    Docente docente = new Docente("Franco", "franco@gmail.com");
    curso.crearNgruposDeMintegrantes(1, 2);
    curso.agregarDocente(docente);

    Grupo grupo = curso.grupos.get(0);
    Estudiante estudiante = new Estudiante("Juan", "juan@gmail.com");

    grupo.inscribir(estudiante);
    curso.cerrarGrupo(grupo, docente);

    assertFalse(grupo.estaCerrado);
    assertEquals(1, curso.solicitudes.size());
  }

  // 6)
  //Como docente, deseo poder aprobar o rechazar el intento de otro docente de cerrar un grupo
  // que no es de tamaño M.
  @Test
  public void sePuedeCerrarUnGrupoDeTamanioMConSolicitud() {
    Curso curso = new Curso();
    Docente docente = new Docente("Franco", "franco@gmail.com");
    Docente docente2 = new Docente("Gaston", "gaston@gmail.com");

    curso.agregarDocente(docente);
    curso.agregarDocente(docente2);
    curso.crearNgruposDeMintegrantes(1, 2);

    Grupo grupo = curso.grupos.get(0);
    Estudiante estudiante = new Estudiante("Juan", "juan@gmail.com");
    grupo.inscribir(estudiante);

    curso.cerrarGrupo(grupo, docente); // se crea la solicitud
    curso.getSolicitudes().get(0).aceptar(docente2); // se resuelve la solicitud

    assertTrue(grupo.estaCerrado);
  }

  // 7)
  // Como estudiante, quiero solicitar inscribirme o darme de baja de un grupo cerrado.
  @Test
  public void sePuedeCrearLaSolicitudDeAltaYbajaAunGrupoCerrado() {
    Curso curso = new Curso();
    Docente docente = new Docente("Franco", "franco@gmail.com");

    curso.agregarDocente(docente);
    curso.crearNgruposDeMintegrantes(1, 1);

    Grupo grupo = curso.grupos.get(0);
    Estudiante estudiante = new Estudiante("Juan", "juan@gmail.com");
    Estudiante estudiante2 = new Estudiante("Mark", "mark@gmail.com");

    grupo.inscribir(estudiante);
    curso.cerrarGrupo(grupo, docente);

    curso.agregarIntegranteAlGrupo(estudiante2, grupo); // se crea la solicitud de alta
    curso.quitarIntegrantDelGrupo(estudiante2, grupo); // se crea la solicitud de baja

    assertEquals(2, curso.solicitudes.size());
  }

  // 8)
  // Como docente, quiero poder aprobar o rechazar la solicitud de alta o baja de un estudiante
  // para un grupo cerrado.
  @Test
  public void sePuedeInscribirAUnGrupoCerradoConSolicitud() {
    Curso curso = new Curso();
    Docente docente = new Docente("Franco", "franco@gmail.com");

    curso.agregarDocente(docente);
    curso.crearNgruposDeMintegrantes(1, 1);

    Grupo grupo = curso.grupos.get(0);
    Estudiante estudiante = new Estudiante("Juan", "juan@gmail.com");
    Estudiante estudiante2 = new Estudiante("Mark", "mark@gmail.com");

    grupo.inscribir(estudiante);
    curso.cerrarGrupo(grupo, docente);

    curso.agregarIntegranteAlGrupo(estudiante2, grupo); // se crea la solicitud
    curso.getSolicitudes().get(0).aceptar(docente); // se resuelve la solicitud

    assertTrue(grupo.integrantes.contains(estudiante2));
  }

}
