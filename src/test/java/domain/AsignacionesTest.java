package domain;

import domain.asignaciones.Asignacion;
import domain.asignaciones.TrabajoPractico;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AsignacionesTest {

  //11)
  // Como docente, quiero poder crear asignaciones con una o varias entregas.
  @Test
  public void crearAsignacionConUnaEntrega() {
    Curso curso = new Curso();
    Asignacion asignacion = new TrabajoPractico("TP1", List.of("entrega1"), curso);

    assertEquals("TP1", asignacion.titulo);
    assertEquals(List.of("entrega1"), asignacion.entregas);
    assertEquals(curso, asignacion.curso);
  }

  // 12)
  // Como docente, deseo que el sistema le dé automáticamente acceso a los estudiantes
  // a cada entrega a medida que las semanas de cursada transcurren
  @Test
  public void habilitarProximaEntrega() {
    Curso curso = new Curso();
    Asignacion asignacion = new TrabajoPractico("TP1", List.of("entrega1", "entrega2"), curso);

    asignacion.habilitarProximaEntrega();

    assertEquals(List.of("entrega2"), asignacion.entregas);
  }
}
