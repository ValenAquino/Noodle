package domain.tareas;

import domain.Grupo;
import domain.usuarios.Estudiante;

public interface Tarea {
  void notificarAlta(Grupo grupo, Estudiante estudiante);

  void notificarBaja(Grupo grupo, Estudiante estudiante);

  void aplicarCierre(Grupo grupo);

  void notificarEntrega(Grupo grupo, String nombreEntrega);
}
