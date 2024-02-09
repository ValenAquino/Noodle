package domain.usuarios;

import java.util.ArrayList;
import java.util.List;

public class Estudiante extends Usuario {
  List<String> repositorios = new ArrayList<>();

  public Estudiante(String nombre, String email) {
    super(nombre, email);
  }

  public void agregarRepositorio(String nombreRepo) {
    repositorios.add(nombreRepo);
  }

  public void quitarRepositorio(String nombreRepo) {
    repositorios.remove(nombreRepo);
  }

  public boolean tieneRepositorio(String nombreRepo) {
    return repositorios.contains(nombreRepo);
  }
}
