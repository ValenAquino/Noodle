package domain.asignaciones;

import domain.Curso;

import java.util.List;

public class TrabajoPractico extends Asignacion {
  public TrabajoPractico(String titulo, List<String> entregas, Curso curso) {
    super(titulo, entregas, curso);
  }
}
