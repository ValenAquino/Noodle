package domain.solicitudes;

import domain.Grupo;
import domain.usuarios.Docente;

import java.util.Objects;

public class SolicitudCierre extends Solicitud {
  Docente docenteSolicitante;

  public SolicitudCierre(Docente docenteSolicitante) {
    this.docenteSolicitante = docenteSolicitante;
  }

  @Override
  public void aplicar(Docente docente, Grupo grupo) {
    if (Objects.equals(docente.email, docenteSolicitante.email)) {
      throw new IllegalStateException("El docente solicitante no puede cerrar el grupo");
    }

    grupo.aplicarCierre();
  }
}
