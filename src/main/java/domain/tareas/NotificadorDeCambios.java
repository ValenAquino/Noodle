package domain.tareas;

import domain.Grupo;
import domain.Mensaje;
import domain.adapters.EnviadorDeMails;
import domain.usuarios.Estudiante;

public class NotificadorDeCambios implements Tarea {
  EnviadorDeMails enviadorDeMails;

  public NotificadorDeCambios(EnviadorDeMails enviadorDeMails) {
    this.enviadorDeMails = enviadorDeMails;
  }

  @Override
  public void notificarAlta(Grupo grupo, Estudiante estudiante) {
    grupo.integrantes.forEach(e -> enviadorDeMails.enviarMail(this.mensajeAlta(e)));
  }

  @Override
  public void notificarBaja(Grupo grupo, Estudiante estudiante) {
    grupo.integrantes.forEach(e -> enviadorDeMails.enviarMail(this.mensajeBaja(e)));
  }

  @Override
  public void aplicarCierre(Grupo grupo) {
  }

  @Override
  public void notificarEntrega(Grupo grupo, String nombreEntrega) {
    grupo.integrantes.forEach(e ->
        enviadorDeMails.enviarMail(this.mensajeEntregaNueva(e, nombreEntrega))
    );
  }

  Mensaje mensajeAlta(Estudiante estudiante) {
    return new Mensaje(
        estudiante,
        "Nueva Inscripcion",
        "Se ha inscripto a " + estudiante.nombre + " en el grupo"
    );
  }

  Mensaje mensajeBaja(Estudiante estudiante) {
    return new Mensaje(
        estudiante,
        "Nueva baja",
        "Se ha dado de baja a " + estudiante.nombre + " del grupo"
    );
  }

  Mensaje mensajeEntregaNueva(Estudiante estudiante, String nombreEntrega) {
    return new Mensaje(
        estudiante,
        "Nueva entrega",
        "Link: " + nombreEntrega
    );
  }
}
