package cronjobs;

import domain.asignaciones.Asignacion;
import domain.asignaciones.RepositorioDeAsignaciones;

public class CronJobAsignaciones {
  public static void main(String[] args) {
    RepositorioDeAsignaciones repositorio = RepositorioDeAsignaciones.getInstance();

    Asignacion asignacion = repositorio.buscar(args[0]);
    asignacion.habilitarProximaEntrega();
  }
}
