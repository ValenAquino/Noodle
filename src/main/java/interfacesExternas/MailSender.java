package interfacesExternas;

public class MailSender {
  public void send(String addres, String subject, String body) {
    System.out.printf("Sending mail to %s with subject %s and body %s%n", addres, subject, body);
  }
}
