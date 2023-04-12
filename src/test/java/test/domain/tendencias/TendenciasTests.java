package test.domain.tendencias;

import domain.catalogo.Album;
import domain.catalogo.Artista;
import domain.catalogo.Cancion;
import domain.tendencias.EnAuge;
import domain.tendencias.EnTendencia;
import domain.tendencias.Normal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TendenciasTests {
    private Cancion cancion;
    private Artista artista;
    private Album album;

    @BeforeEach
    public void init() {
        this.artista = new Artista();
        this.artista.setNombre("Coldplay");

        this.album = new Album();
        this.album.setAnio(2002);
        this.album.setNombre("A Rush of Blood to the head");

        this.cancion = new Cancion();
        this.cancion.setTitulo("The Scientist");
        this.cancion.setArtista(this.artista);
        this.cancion.setAnio(2002);
        this.cancion.setAlbum(this.album);

        Normal.cantMaxReproduccionesTendenciaNormal = 2;
        EnAuge.cantMaxReproduccionesenAuge = 3;
        EnAuge.cantMaxLikesEsperados = 3;
        EnAuge.cantMaxDislikesSoportados = 5;
        EnTendencia.cantHorasSinEscucharParaBajarPopularidad = 24;
    }

    @Test
    @DisplayName("(Normal) The Scientist recién se lanza (tiene popularidad normal)")
    public void cancionMuestraDetalleDeTendenciaNormal() {
        cancion.reproducir();
        String detalle = cancion.detalleCompleto();

        Assertions.assertTrue(detalle.contains(Normal.armarLeyendaPara(this.cancion)));
        Assertions.assertEquals(1, this.cancion.getCantReproducciones());
    }

    @Test
    @DisplayName("(Normal -> Auge) The Scientist está en auge por superar el mínimo de reproducciones esperadas")
    public void cancionMuestraDetalleEnAugeTest() {
        cancion.reproducir();
        cancion.reproducir();
        cancion.reproducir();

        Assertions.assertTrue(cancion.detalleCompleto().contains(EnAuge.armarLeyendaPara(this.cancion)));
        Assertions.assertEquals(3, this.cancion.getCantReproducciones());
    }

    @Test
    @DisplayName("(Auge -> Normal) The Scientist está en auge pero vuelve a ser Normal por muchos dislikes")
    public void cancionVuelveANormalPorDislikes() {
        EnAuge.cantMaxDislikesSoportados = 5;

        /** A LA TERCER REPRODUCCIÓN DEBERÍA PASAR A ESTAR EN AUGE */
        cancion.reproducir();
        cancion.reproducir();
        cancion.reproducir();

        Assertions.assertEquals(EnAuge.class.getName(), cancion.getPopularidad().getClass().getName());

        /** LUEGO DE RECIBIR 6 DISLIKES, VUELVE A SER NORMAL */
        cancion.recibirDislike();
        cancion.recibirDislike();
        cancion.recibirDislike();
        cancion.recibirDislike();
        cancion.recibirDislike();
        cancion.recibirDislike();

        cancion.reproducir();

        Assertions.assertEquals(Normal.class.getName(), cancion.getPopularidad().getClass().getName());
    }

    @Test
    @DisplayName("(Auge -> Tendencia) The Scientist es tendencia por record de reproducciones y likes")
    public void cancionMuestraDetalleEnTendenciaTest() {
        /** A LA TERCER REPRODUCCIÓN DEBERÍA PASAR A ESTAR EN AUGE */
        cancion.reproducir();
        cancion.reproducir();
        cancion.reproducir();

        Assertions.assertTrue(cancion.detalleCompleto().contains(EnAuge.armarLeyendaPara(this.cancion)));

        /** A LA SEPTIMA REPRODUCCIÓN (TOTAL) + 4 Likes DEBERÍA PASAR A ESTAR EN TENDENCIA */
        cancion.recibirLike();
        cancion.recibirLike();
        cancion.recibirLike();
        cancion.recibirLike();

        cancion.reproducir();
        cancion.reproducir();
        cancion.reproducir();
        cancion.reproducir();

        Boolean esTendencia = cancion.detalleCompleto().contains(EnTendencia.armarLeyendaPara(this.cancion));

        Assertions.assertTrue(esTendencia);
        Assertions.assertEquals(7, this.cancion.getCantReproducciones());
    }

    @Test
    @DisplayName("(Tendencia -> Normal) The Scientist era tendencia pero vuelve a ser normal por no ser escuchada en las últimas horas")
    public void cancionTendenciaVuelveASerNormal() {
        EnTendencia.cantHorasSinEscucharParaBajarPopularidad = 24;
        /** A LA TERCER REPRODUCCIÓN DEBERÍA PASAR A ESTAR EN AUGE */
        cancion.reproducir();
        cancion.reproducir();
        cancion.reproducir();

        Assertions.assertEquals(EnAuge.class.getName(), cancion.getPopularidad().getClass().getName());

        /** A LA SEPTIMA REPRODUCCIÓN (TOTAL) + 4 Likes DEBERÍA PASAR A ESTAR EN TENDENCIA */
        cancion.recibirLike();
        cancion.recibirLike();
        cancion.recibirLike();
        cancion.recibirLike();

        cancion.reproducir();
        cancion.reproducir();
        cancion.reproducir();
        cancion.reproducir();

        Assertions.assertEquals(EnTendencia.class.getName(), cancion.getPopularidad().getClass().getName());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime fechaFutura = LocalDateTime.parse("2030-04-20 12:00:00", formatter);

        EnTendencia estadoActualCancion = (EnTendencia) cancion.getPopularidad();
        estadoActualCancion.setFechaAComparar(fechaFutura);

        /** A LA NOVENA OCTAVA REPRODUCCIÓN (TOTAL), Ó PRIMERA DENTRO DE 'EN TENDENCIA', DEBERÍA VOLVER A LA NORMALIDAD */
        cancion.reproducir();

        Assertions.assertEquals(Normal.class.getName(), cancion.getPopularidad().getClass().getName());
    }
}
