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
}
