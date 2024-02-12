package domain.solicitudes;

import domain.Grupo;
import domain.usuarios.Docente;
import domain.usuarios.Estudiante;

public class SolicitudAlta extends Solicitud {
  Estudiante estudiante;

  public SolicitudAlta(Estudiante estudiante) {
    this.estudiante = estudiante;
  }

  @Override
  public void aplicar(Docente docente, Grupo grupo) {
    grupo.aplicarAlta(estudiante);
  }
}
