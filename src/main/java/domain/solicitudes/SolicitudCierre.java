package domain.solicitudes;

import domain.Grupo;
import domain.usuarios.Docente;

import java.util.Objects;

public class SolicitudCierre extends Solicitud {
  Docente docenteSolicitante;
  Grupo grupo;

  public SolicitudCierre(Docente docenteSolicitante, Grupo grupo) {
    this.docenteSolicitante = docenteSolicitante;
    this.grupo = grupo;
  }

  @Override
  public void aplicar(Docente docente) {
    if (Objects.equals(docente.email, docenteSolicitante.email)) {
      throw new IllegalStateException("El docente solicitante no puede cerrar el grupo");
    }

    grupo.aplicarCierre();
  }
}
