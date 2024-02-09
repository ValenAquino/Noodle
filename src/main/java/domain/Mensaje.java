package domain;

import domain.usuarios.Estudiante;

public class Mensaje {
  public Estudiante destinatario;
  public String asunto;
  public String cuerpo;

  public Mensaje(Estudiante destinatario, String asunto, String cuerpo) {
    this.destinatario = destinatario;
    this.asunto = asunto;
    this.cuerpo = cuerpo;
  }
}
