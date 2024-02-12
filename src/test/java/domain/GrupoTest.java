package domain;

import domain.adapters.EnviadorDeMails;
import domain.adapters.Guitab;
import domain.solicitudes.SolicitudCierre;
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
  public void cuandoSeIntentaCerrarUnGrupoDeTamanioMenorCreaUnaSolicitud() {
    Grupo grupo = new Grupo(2, tareas);
    Estudiante estudiante = new Estudiante("Juan", "juan@gmail.com");
    Docente docente = new Docente("Franco", "franco@gmail.com");

    grupo.inscribir(estudiante);
    grupo.cerrar(docente);

    assertInstanceOf(SolicitudCierre.class, grupo.solicitudes.get(0));
  }

  // 4)
  // Como docente, deseo poder cerrar un grupo que tiene tamaño M
  @Test
  public void sePuedeCerrarUnGrupoDeTamanioM() {
    Docente docente = new Docente("Franco", "franco@gmail.com");

    Grupo grupo = new Grupo(1, tareas);
    Estudiante estudiante = new Estudiante("Juan", "juan@gmail.com");

    grupo.inscribir(estudiante);
    grupo.cerrar(docente);

    assertTrue(grupo.estaCerrado);
  }

  // 6)
  //Como docente, deseo poder aprobar o rechazar el intento de otro docente de cerrar un grupo
  // que no es de tamaño M.
  @Test
  public void sePuedeCerrarUnGrupoQueNoEsDeTamanioMConSolicitud() {
    Docente docente = new Docente("Franco", "franco@gmail.com");
    Docente docente2 = new Docente("Gaston", "gaston@gmail.com");

    Grupo grupo = new Grupo(1, tareas);
    Estudiante estudiante = new Estudiante("Juan", "juan@gmail.com");
    grupo.cerrar(docente);
    grupo.inscribir(estudiante);

    grupo.getSolicitudes().get(0).aceptar(docente2, grupo); // se resuelve la solicitud

    assertTrue(grupo.estaCerrado);
  }

  // 7)
  // Como estudiante, quiero solicitar inscribirme o darme de baja de un grupo cerrado.
  @Test
  public void sePuedeCrearLaSolicitudDeAltaYbajaAunGrupoCerrado() {
    Docente docente = new Docente("Franco", "franco@gmail.com");

    Grupo grupo = new Grupo(1, tareas);
    Estudiante estudiante = new Estudiante("Juan", "juan@gmail.com");
    Estudiante estudiante2 = new Estudiante("Mark", "mark@gmail.com");

    grupo.inscribir(estudiante);
    grupo.cerrar(docente);

    grupo.inscribir(estudiante2); // se crea la solicitud de alta
    grupo.darDeBaja(estudiante); // se crea la solicitud de baja

    assertEquals(2, grupo.getSolicitudes().size());
  }

  // 8)
  // Como docente, quiero poder aprobar o rechazar la solicitud de alta o baja de un estudiante
  // para un grupo cerrado.
  @Test
  public void sePuedeInscribirEnUnGrupoCerradoConSolicitud() {
    Docente docente = new Docente("Franco", "franco@gmail.com");

    Grupo grupo = new Grupo(1, tareas);
    Estudiante estudiante = new Estudiante("Juan", "juan@gmail.com");
    Estudiante estudiante2 = new Estudiante("Mark", "mark@gmail.com");

    grupo.inscribir(estudiante);
    grupo.cerrar(docente);

    grupo.inscribir(estudiante2);
    grupo.getSolicitudes().get(0).aceptar(docente, grupo); // se resuelve la solicitud

    assertTrue(grupo.integrantes.contains(estudiante2));
  }

  @Test
  public void sePuedeRechazarInscribirEnUnGrupoCerradoConSolicitud() {
    Docente docente = new Docente("Franco", "franco@gmail.com");

    Grupo grupo = new Grupo(1, tareas);
    Estudiante estudiante = new Estudiante("Juan", "juan@gmail.com");
    Estudiante estudiante2 = new Estudiante("Mark", "mark@gmail.com");

    grupo.inscribir(estudiante);
    grupo.cerrar(docente);

    grupo.inscribir(estudiante2);
    grupo.getSolicitudes().get(0).rechazar(); // se rechaza la solicitud

    assertFalse(grupo.integrantes.contains(estudiante2));
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
    grupo.aplicarCierre();

    verify(guitabSdkMock, times(1))
        .crearRepositoriosConAccesos(anyString(), anyList());
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
    grupo.cerrar(docente);

    grupo.inscribir(estudiante);
    grupo.getSolicitudes().get(0).aceptar(docente, grupo); // se resuelve la solicitud

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
    grupo.cerrar(docente);

    assertTrue(estudiante.tieneRepositorio(grupo.nombreRepo));

    grupo.darDeBaja(estudiante);
    grupo.getSolicitudes().get(0).aceptar(docente, grupo); // se resuelve la solicitud

    verify(guitabSdkMock, times(1))
        .quitarAcceso(anyString(), anyString());

    assertFalse(estudiante.tieneRepositorio(grupo.nombreRepo));
  }
}
