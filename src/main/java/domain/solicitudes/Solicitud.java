package domain.solicitudes;

import domain.Grupo;
import domain.usuarios.Docente;

public abstract class Solicitud {
  public EstadoSolicitud estado = EstadoSolicitud.PENDIENTE;

  public void aceptar(Docente docente, Grupo grupo) {
    if (this.estado != EstadoSolicitud.PENDIENTE)
      throw new RuntimeException("La solicitud ya fue aceptada o rechazada");

    this.aplicar(docente, grupo);
    this.estado = EstadoSolicitud.ACEPTADA;
  }

  public void rechazar() {
    if (this.estado != EstadoSolicitud.PENDIENTE)
      throw new RuntimeException("La solicitud ya fue aceptada o rechazada");

    this.estado = EstadoSolicitud.RECHAZADA;
  }

  public abstract void aplicar(Docente docente, Grupo grupo);
}
