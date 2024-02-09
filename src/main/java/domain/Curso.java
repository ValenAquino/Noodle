package domain;

import domain.adapters.EnviadorDeMails;
import domain.solicitudes.Solicitud;
import domain.solicitudes.SolicitudAlta;
import domain.solicitudes.SolicitudBaja;
import domain.solicitudes.SolicitudCierre;
import domain.tareas.NotificadorDeCambios;
import domain.tareas.Tarea;
import domain.usuarios.Docente;
import domain.usuarios.Estudiante;
import interfacesExternas.MailSender;

import java.util.ArrayList;
import java.util.List;

public class Curso {
  List<Grupo> grupos = new ArrayList<>();
  List<Docente> docentes = new ArrayList<>();
  List<Solicitud> solicitudes = new ArrayList<>();
  List<Tarea> tareas = new ArrayList<>();

  void crearNgruposDeMintegrantes(int n, int m) {
    for (int i = 0; i < n; i++) {
      Grupo grupo = new Grupo(m, tareas);
      grupos.add(grupo);
    }
  }

  void cerrarGrupo(Grupo grupo, Docente docente) {
    try {
      grupo.cerrar();
    } catch (IllegalStateException e) {
      Solicitud solicitudCierre = new SolicitudCierre(docente, grupo);
      solicitudes.add(solicitudCierre);
    }
  }

  void agregarIntegranteAlGrupo(Estudiante estudiante, Grupo grupo) {
    try {
      grupo.inscribir(estudiante);
    } catch (IllegalStateException e) {
      Solicitud solicitudAlta = new SolicitudAlta(estudiante, grupo);
      solicitudes.add(solicitudAlta);
    }
  }

  void quitarIntegrantDelGrupo(Estudiante estudiante, Grupo grupo) {
    try {
      grupo.darDeBaja(estudiante);
    } catch (IllegalStateException e) {
      Solicitud solicitudBaja = new SolicitudBaja(estudiante, grupo);
      solicitudes.add(solicitudBaja);
    }
  }

  void agregarDocente(Docente docente) {
    docentes.add(docente);
  }

  List<Solicitud> getSolicitudes() {
    return solicitudes;
  }

  public void agregarTarea(Tarea tarea) {
    tareas.add(tarea);
  }

  public void removerTarea(Tarea tarea) {
    tareas.remove(tarea);
  }

  public void habilitarEntrega(String nombreEntrega) {
    for (Grupo grupo : grupos) {
      grupo.recibirEntrega(nombreEntrega);
    }
  }
}
