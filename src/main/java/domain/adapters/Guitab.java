package domain.adapters;

import domain.usuarios.Estudiante;
import interfacesExternas.GuitabSdk;

import java.util.List;

public class Guitab {
  GuitabSdk guitabSdk;

  public Guitab(GuitabSdk guitabSdk) {
    this.guitabSdk = guitabSdk;
  }

  public void crearRepositoriosConAccesos(String nombreRepo, List<Estudiante> estudiantes) {
    List<String> usuarios = estudiantes.stream().map(estudiante -> estudiante.nombre).toList();
    guitabSdk.crearRepositoriosConAccesos(nombreRepo, usuarios);
    estudiantes.forEach(e -> e.agregarRepositorio(nombreRepo));
  }

  public void darAcceso(String nombreRepo, Estudiante estudiante) {
    guitabSdk.darAcceso(nombreRepo, estudiante.nombre);
    estudiante.agregarRepositorio(nombreRepo);
  }

  public void quitarAcceso(String nombreRepo, Estudiante estudiante) {
    guitabSdk.quitarAcceso(nombreRepo, estudiante.nombre);
    estudiante.quitarRepositorio(nombreRepo);
  }
}
