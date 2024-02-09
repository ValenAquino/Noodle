package domain.adapters;

import domain.Mensaje;
import interfacesExternas.MailSender;

public class EnviadorDeMails {
  MailSender mailSender;

  public EnviadorDeMails(MailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void enviarMail(Mensaje mensaje) {
    mailSender.send(mensaje.destinatario.email, mensaje.asunto, mensaje.cuerpo);
  }
}
