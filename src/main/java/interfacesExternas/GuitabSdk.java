package interfacesExternas;

import java.util.List;

public interface GuitabSdk {
  public void crearRepositoriosConAccesos(String nombreResp, List<String> usuarios);
  public void darAcceso(String nombreRepo, String usuario);
  public void quitarAcceso(String nombreRepo, String usuario);
}
