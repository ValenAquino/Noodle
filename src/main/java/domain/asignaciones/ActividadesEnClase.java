package domain.asignaciones;

import domain.Curso;

import java.util.List;

public class ActividadesEnClase extends Asignacion {
  ActividadesEnClase(String titulo, List<String> entregas, Curso curso) {
    super(titulo, entregas, curso);
  }
}
