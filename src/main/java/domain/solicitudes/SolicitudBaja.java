package domain.solicitudes;

import domain.Grupo;
import domain.usuarios.Docente;
import domain.usuarios.Estudiante;

public class SolicitudBaja extends Solicitud {
  Estudiante estudiante;
  Grupo grupo;

  public SolicitudBaja(Estudiante estudiante, Grupo grupo) {
    this.estudiante = estudiante;
    this.grupo = grupo;
  }

  @Override
  public void aplicar(Docente docente) {
    grupo.aplicarBaja(estudiante);
  }

}
