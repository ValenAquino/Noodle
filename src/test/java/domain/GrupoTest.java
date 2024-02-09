package domain;

import domain.adapters.EnviadorDeMails;
import domain.adapters.Guitab;
import domain.solicitudes.Solicitud;
import domain.solicitudes.SolicitudAlta;
import domain.solicitudes.SolicitudBaja;
import domain.tareas.AdministradorDeRepositorios;
import domain.tareas.NotificadorDeCambios;
import domain.tareas.Tarea;
import domain.usuarios.Docente;
import domain.usuarios.Estudiante;
import interfacesExternas.GuitabSdk;
import interfacesExternas.MailSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class GrupoTest {
  List<Tarea> tareas;
  public MailSender mailSenderMock;
  public GuitabSdk guitabSdkMock;

  @BeforeEach
  public void setUp() {
    mailSenderMock = Mockito.mock(MailSender.class);
    EnviadorDeMails enviadorDeMails = new EnviadorDeMails(mailSenderMock);

    this.guitabSdkMock = mock(GuitabSdk.class);
    Guitab guitab = new Guitab(guitabSdkMock);

    tareas = List.of(
        new NotificadorDeCambios(enviadorDeMails),
        new AdministradorDeRepositorios(guitab)
    );
  }

  // 2)
  //Como estudiante, deseo poder inscribirme o darme de baja instantáneamente de un grupo abierto.
  @Test
  public void inscribirEstudiante() {
    Grupo grupo = new Grupo(4, tareas);
    Estudiante estudiante = new Estudiante("Juan", "juan@gmail.com");

    grupo.inscribir(estudiante);

    assertEquals(1, grupo.integrantes.size());
  }

  @Test
  public void desinscribirEstudiante() {
    Grupo grupo = new Grupo(4, tareas);
    Estudiante estudiante = new Estudiante("Juan", "juan@gmail.com");
    grupo.inscribir(estudiante);

    grupo.darDeBaja(estudiante);

    assertEquals(0, grupo.integrantes.size());
  }

  // 3)
  //Como estudiante, deseo recibir una notificación por correo electrónico cada vez que alguien
  // se inscribe o se da de baja de mi grupo de trabajo.
  @Test
  public void cuandoMeInscriboAUnGrupoSeMeNotifica() {
    Grupo grupo = new Grupo(4, tareas);
    Estudiante estudiante = new Estudiante("Juan", "juan@gmail.com");

    grupo.inscribir(estudiante);

    verify(mailSenderMock, times(1)).send(anyString(), anyString(), anyString());
  }

  @Test
  public void cuandoSeDanDeBajaDeUnGrupoSeMeNotifica() {
    Grupo grupo = new Grupo(4, tareas);
    Estudiante estudiante = new Estudiante("Juan", "juan@gmail.com");
    Estudiante estudiante2 = new Estudiante("Manu", "manu@gmail.com");

    grupo.inscribir(estudiante); // manda 1 mail a 1 persona => 1 send
    grupo.inscribir(estudiante2); // manda 1 mail a 2 personas => 2 send
    grupo.darDeBaja(estudiante); // manda 1 mail a 1 persona => 1 send

    // en total hay 4 sends
    verify(mailSenderMock, times(4)).send(anyString(), anyString(), anyString());
  }

  @Test
  public void cuandoSeIntentaCerrarUnGrupoDeTamanioMenorFalla() {
    Grupo grupo = new Grupo(2, tareas);
    Estudiante estudiante = new Estudiante("Juan", "juan@gmail.com");

    grupo.inscribir(estudiante);

    assertThrows(IllegalStateException.class, grupo::cerrar);
  }

  // 5)
  // Como docente, deseo que cuando un grupo sea cerrado,
  // se cree el repositorio correspondiente en Guitab.
  @Test
  public void seCreaRepositorioEnGuitabAlCerrarGrupo() {
    Grupo grupo = new Grupo(1, tareas);
    Estudiante estudiante = new Estudiante("Juan", "juan@gmail.com");

    grupo.inscribir(estudiante);
    grupo.setNombreRepo("grupo");
    grupo.cerrar();

    verify(guitabSdkMock, times(1))
        .crearRepositoriosConAccesos(anyString(), anyList());
  }

  @Test
  public void cuandoSeIntentaAgregarIntegrantesAunGrupoCerradoFalla() {
    Grupo grupo = new Grupo(1, tareas);
    Estudiante estudiante = new Estudiante("Juan", "juan@gmail.com");
    Estudiante estudiante2 = new Estudiante("Mark", "mark@gmail.com");

    grupo.inscribir(estudiante);
    grupo.cerrar();

    assertThrows(IllegalStateException.class, () -> grupo.inscribir(estudiante2));
  }

  @Test
  public void cuandoSeIntentaQuitarIntegrantesAunGrupoCerradoFalla() {
    Grupo grupo = new Grupo(1, tareas);
    Estudiante estudiante = new Estudiante("Juan", "juan@gmail.com");

    grupo.inscribir(estudiante);
    grupo.cerrar();

    assertThrows(IllegalStateException.class, () -> grupo.darDeBaja(estudiante));
  }

  // 9)
  // Como docente, deseo que al incluir o eliminar un estudiante de un grupo cerrado,
  // este reciba o pierda los accesos al repositorio del equipo.
  @Test
  public void seDaAccesoARepositorioAlInscribirAUnGrupoCerrado() {
    Docente docente = new Docente("Franco", "franco@gmail.com");

    Grupo grupo = new Grupo(0, tareas);
    Estudiante estudiante = new Estudiante("Juan", "juan@gmail.com");

    grupo.setNombreRepo("grupo");
    grupo.cerrar();

    Solicitud alta = new SolicitudAlta(estudiante, grupo);
    alta.aceptar(docente);

    verify(guitabSdkMock, times(1))
        .darAcceso(anyString(), anyString());

    assertTrue(estudiante.tieneRepositorio(grupo.nombreRepo));
  }

  @Test
  public void seQuitaAccesoARepositorioAlInscribirAUnGrupoCerrado() {
    Docente docente = new Docente("Franco", "franco@gmail.com");

    Grupo grupo = new Grupo(1, tareas);
    Estudiante estudiante = new Estudiante("Juan", "juan@gmail.com");

    grupo.setNombreRepo("grupo");
    grupo.inscribir(estudiante);
    grupo.cerrar();

    assertTrue(estudiante.tieneRepositorio(grupo.nombreRepo));

    Solicitud baja = new SolicitudBaja(estudiante, grupo);
    baja.aceptar(docente);

    verify(guitabSdkMock, times(1))
        .quitarAcceso(anyString(), anyString());

    assertFalse(estudiante.tieneRepositorio(grupo.nombreRepo));
  }
}
