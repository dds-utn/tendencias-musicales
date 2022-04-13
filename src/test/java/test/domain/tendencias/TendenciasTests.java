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
        Normal.cantHorasClaveParaTrascender = 24;

        EnAuge.cantMaxReproduccionesenAuge = 4;
        EnAuge.horasDeToleranciaEnAuge = 72;
        EnAuge.cantHorasMinParaTrascender = 48;

        EnTendencia.cantHorasSinEscucharParaBajarPopularidad = 24;
    }

    @Test
    @DisplayName("The Scientist recién se lanza (tiene popularidad normal)")
    public void cancionMuestraDetalleDeTendenciaNormal() {
        Boolean tienePopularidadNormal = cancion.serEscuchada().contains(Normal.armarLeyendaPara(this.cancion));

        Assertions.assertTrue(tienePopularidadNormal);
        Assertions.assertEquals(1, this.cancion.getCantReproducciones());
    }

    @Test
    @DisplayName("The Scientist está en auge por superar el mínimo de reproducciones esperadas")
    public void cancionMuestraDetalleEnAugeTest() {
        cancion.serEscuchada();
        cancion.serEscuchada();

        Boolean estaEnAuge = cancion.serEscuchada().contains(EnAuge.armarLeyendaPara(this.cancion));

        Assertions.assertTrue(estaEnAuge);
        Assertions.assertEquals(3, this.cancion.getCantReproducciones());
    }

    @Test
    @DisplayName("The Scientist baja del auge por no ser reproducida")
    public void cancionVuelveASerNormalPorBajasReproduccionesTest() {
        EnAuge.horasDeToleranciaEnAuge = 0;
        EnAuge.cantMaxReproduccionesenAuge = 5;

        cancion.serEscuchada();
        cancion.serEscuchada();

        Boolean estaEnAuge = cancion.serEscuchada().contains(EnAuge.armarLeyendaPara(this.cancion));
        Assertions.assertTrue(estaEnAuge);

        Boolean tienePopularidadNormal = cancion.serEscuchada().contains(Normal.armarLeyendaPara(this.cancion));
        Assertions.assertTrue(tienePopularidadNormal);
    }

    @Test
    @DisplayName("The Scientist es tendencia por record de reproducciones en poco tiempo")
    public void cancionMuestraDetalleEnTendenciaTest() {
        cancion.serEscuchada();
        cancion.serEscuchada();

        /** A LA TERCER REPRODUCCIÓN DEBERÍA PASAR A ESTAR EN AUGE */
        Boolean estaEnAuge = cancion.serEscuchada().contains(EnAuge.armarLeyendaPara(this.cancion));
        Assertions.assertTrue(estaEnAuge);

        /** A LA OCTAVA REPRODUCCIÓN (TOTAL), Ó QUINTA DENTRO DE 'EN AUGE', DEBERÍA PASAR A ESTAR EN TENDENCIA */
        cancion.serEscuchada();
        cancion.serEscuchada();
        cancion.serEscuchada();
        cancion.serEscuchada();

        Boolean esTendencia = cancion.serEscuchada().contains(EnTendencia.armarLeyendaPara(this.cancion));

        Assertions.assertTrue(esTendencia);
        Assertions.assertEquals(8, this.cancion.getCantReproducciones());
    }

    @Test
    @DisplayName("The Scientist era tendencia pero vuelve a ser normal por no ser escuchada en las últimas horas")
    public void cancionTendenciaVuelveASerNormal() {
        EnTendencia.cantHorasSinEscucharParaBajarPopularidad = 0;

        cancion.serEscuchada();
        cancion.serEscuchada();

        /** A LA TERCER REPRODUCCIÓN DEBERÍA PASAR A ESTAR EN AUGE */
        Boolean estaEnAuge = cancion.serEscuchada().contains(EnAuge.armarLeyendaPara(this.cancion));
        Assertions.assertTrue(estaEnAuge);

        /** A LA OCTAVA REPRODUCCIÓN (TOTAL), Ó QUINTA DENTRO DE 'EN AUGE', DEBERÍA PASAR A ESTAR EN TENDENCIA */
        cancion.serEscuchada();
        cancion.serEscuchada();
        cancion.serEscuchada();
        cancion.serEscuchada();

        Boolean esTendencia = cancion.serEscuchada().contains(EnTendencia.armarLeyendaPara(this.cancion));
        Assertions.assertTrue(esTendencia);

        /** A LA NOVENA REPRODUCCIÓN (TOTAL), Ó PRIMERA DENTRO DE 'EN TENDENCIA', DEBERÍA VOLVER A LA NORMALIDAD */
        Boolean tienePopularidadNormal = cancion.serEscuchada().contains(Normal.armarLeyendaPara(this.cancion));
        Assertions.assertTrue(tienePopularidadNormal);
    }
}
