package domain.asignaciones;

import java.util.List;

public class RepositorioDeAsignaciones {
  List<Asignacion> asignaciones;
  static RepositorioDeAsignaciones INSTANCE;

  public static RepositorioDeAsignaciones getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new RepositorioDeAsignaciones();
    }
    return INSTANCE;
  }

  public void agregar(Asignacion asignacion) {
    asignaciones.add(asignacion);
  }

  public void quitar(Asignacion asignacion) {
    asignaciones.remove(asignacion);
  }

  public Asignacion buscar(String titulo) {
    return asignaciones.stream()
        .filter(asignacion -> asignacion.titulo.equals(titulo))
        .findFirst()
        .orElse(null);
  }
}
