package domain;

import domain.tareas.Tarea;
import domain.usuarios.Estudiante;

import java.util.ArrayList;
import java.util.List;

public class Grupo {
  public String nombreRepo;
  int limiteIntegrantes;
  public boolean estaCerrado = false;
  List<Tarea> tareas;
  List<String> entregas = new ArrayList<>();

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
    if (estaCerrado) {
      throw new IllegalStateException("El grupo está cerrado");
    }

    if (integrantes.size() >= limiteIntegrantes) {
      throw new IllegalStateException("El grupo está lleno");
    }

    aplicarAlta(estudiante);
  }

  public void darDeBaja(Estudiante estudiante) {
    if (estaCerrado) {
      throw new IllegalStateException("El grupo está cerrado");
    }

    if (!integrantes.contains(estudiante)) {
      throw new IllegalStateException("El estudiante no está inscripto en el grupo");
    }

    aplicarBaja(estudiante);
  }

  public void cerrar() {
    if (estaCerrado) {
      throw new IllegalStateException("El grupo ya está cerrado");
    }

    if (noSePuedeCerrar()) {
      throw new IllegalStateException("El grupo no se puede cerrar");
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
}
