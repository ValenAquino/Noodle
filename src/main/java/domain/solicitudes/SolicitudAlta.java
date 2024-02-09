package domain.solicitudes;

import domain.Grupo;
import domain.usuarios.Docente;
import domain.usuarios.Estudiante;

public class SolicitudAlta extends Solicitud {
  Estudiante estudiante;
  Grupo grupo;

  public SolicitudAlta(Estudiante estudiante, Grupo grupo) {
    this.estudiante = estudiante;
    this.grupo = grupo;
  }

  @Override
  public void aplicar(Docente docente) {
    grupo.aplicarAlta(estudiante);
  }
}
