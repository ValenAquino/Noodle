package domain.solicitudes;

import domain.Grupo;
import domain.usuarios.Docente;
import domain.usuarios.Estudiante;

public class SolicitudBaja extends Solicitud {
  Estudiante estudiante;

  public SolicitudBaja(Estudiante estudiante) {
    this.estudiante = estudiante;
  }

  @Override
  public void aplicar(Docente docente, Grupo grupo) {
    grupo.aplicarBaja(estudiante);
  }

}
