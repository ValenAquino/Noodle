package domain.tareas;

import domain.Grupo;
import domain.adapters.Guitab;
import domain.usuarios.Estudiante;

public class AdministradorDeRepositorios implements Tarea {
  Guitab guitab;

  public AdministradorDeRepositorios(Guitab guitab) {
    this.guitab = guitab;
  }

  @Override
  public void notificarAlta(Grupo grupo, Estudiante estudiante) {
    if (!grupo.estaCerrado) return;
    guitab.darAcceso(grupo.nombreRepo, estudiante);
  }


  @Override
  public void notificarBaja(Grupo grupo, Estudiante estudiante) {
    if (!grupo.estaCerrado) return;
    guitab.quitarAcceso(grupo.nombreRepo, estudiante);
  }

  @Override
  public void aplicarCierre(Grupo grupo) {
    guitab.crearRepositoriosConAccesos(grupo.nombreRepo, grupo.integrantes);
  }

  @Override
  public void notificarEntrega(Grupo grupo, String nombreEntrega) {

  }
}
