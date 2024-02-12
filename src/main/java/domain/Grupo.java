package domain;

import domain.solicitudes.Solicitud;
import domain.solicitudes.SolicitudAlta;
import domain.solicitudes.SolicitudBaja;
import domain.solicitudes.SolicitudCierre;
import domain.tareas.Tarea;
import domain.usuarios.Docente;
import domain.usuarios.Estudiante;

import java.util.ArrayList;
import java.util.List;

public class Grupo {
  public String nombreRepo;
  int limiteIntegrantes;
  public boolean estaCerrado = false;
  List<Tarea> tareas;
  List<String> entregas = new ArrayList<>();
  List<Solicitud> solicitudes = new ArrayList<>();

  public List<Estudiante> integrantes = new ArrayList<>();

  public Grupo(int limiteIntegrantes, List<Tarea> tareas) {
    this.limiteIntegrantes = limiteIntegrantes;
    this.tareas = tareas;
  }

  public void aplicarAlta(Estudiante estudiante) {
    integrantes.add(estudiante);
    tareas.forEach(tarea -> tarea.notificarAlta(this, estudiante));
  }

  public void aplicarBaja(Estudiante estudiante) {
    integrantes.remove(estudiante);
    tareas.forEach(tarea -> tarea.notificarBaja(this, estudiante));
  }

  public void aplicarCierre() {
    estaCerrado = true;
    tareas.forEach(tarea -> tarea.aplicarCierre(this));
  }

  public void inscribir(Estudiante estudiante) {
    if (estaCerrado || integrantes.size() >= limiteIntegrantes) {
      solicitudes.add(new SolicitudAlta(estudiante));
      return;
    }

    aplicarAlta(estudiante);
  }

  public void darDeBaja(Estudiante estudiante) {
    if (!integrantes.contains(estudiante)) {
      throw new IllegalStateException("El estudiante no está inscripto en el grupo");
    }

    if (estaCerrado) {
      solicitudes.add(new SolicitudBaja(estudiante));
      return;
    }

    aplicarBaja(estudiante);
  }

  public void cerrar(Docente docente) {
    if (estaCerrado) {
      throw new IllegalStateException("El grupo ya está cerrado");
    }

    if (noSePuedeCerrar()) {
      solicitudes.add(new SolicitudCierre(docente));
      return;
    }

    aplicarCierre();
  }

  public boolean noSePuedeCerrar() {
    return integrantes.size() != limiteIntegrantes;
  }

  public void setNombreRepo(String nombreRepo) {
    this.nombreRepo = nombreRepo;
  }

  public void recibirEntrega(String nombreEntrega) {
    entregas.add(nombreEntrega);
    tareas.forEach(tarea -> tarea.notificarEntrega(this, nombreEntrega));
  }

  public List<Solicitud> getSolicitudes() {
    return solicitudes;
  }
}
