package domain.solicitudes;

import domain.usuarios.Docente;

public abstract class Solicitud {
  public EstadoSolicitud estado = EstadoSolicitud.PENDIENTE;

  public void aceptar(Docente docente) {
    if (this.estado != EstadoSolicitud.PENDIENTE)
      throw new RuntimeException("La solicitud ya fue aceptada o rechazada");

    this.aplicar(docente);
    this.estado = EstadoSolicitud.ACEPTADA;
  }

  void rechazar(Docente docente) {
    if (this.estado != EstadoSolicitud.PENDIENTE)
      throw new RuntimeException("La solicitud ya fue aceptada o rechazada");

    this.estado = EstadoSolicitud.RECHAZADA;
  }

  public abstract void aplicar(Docente docente);
}
