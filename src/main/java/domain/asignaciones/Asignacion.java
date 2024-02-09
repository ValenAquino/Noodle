package domain.asignaciones;

import domain.Curso;

import java.util.ArrayList;
import java.util.List;

public class Asignacion {
  public String titulo;
  public List<String> entregas;
  public Curso curso;

  public Asignacion(String titulo, List<String> entregas, Curso curso) {
    this.titulo = titulo;
    this.entregas = new ArrayList<>(entregas);
    this.curso = curso;
  }

  public void habilitarProximaEntrega() {
    String entrega = entregas.get(0);
    entregas.remove(entrega);
    curso.habilitarEntrega(entrega);
  }
}
