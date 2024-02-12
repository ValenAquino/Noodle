package domain;

import domain.tareas.Tarea;
import domain.usuarios.Docente;

import java.util.ArrayList;
import java.util.List;

public class Curso {
  List<Grupo> grupos = new ArrayList<>();
  List<Docente> docentes = new ArrayList<>();
  List<Tarea> tareas = new ArrayList<>();

  void crearNgruposDeMintegrantes(int n, int m) {
    for (int i = 0; i < n; i++) {
      Grupo grupo = new Grupo(m, tareas);
      grupos.add(grupo);
    }
  }

  void agregarDocente(Docente docente) {
    docentes.add(docente);
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
