package test.domain.tendencias;

import domain.catalogo.Album;
import domain.catalogo.Artista;
import domain.catalogo.Cancion;
import domain.tendencias.EnAuge;
import domain.tendencias.Normal;

public class TendenciasMainTest {

    public static void main(String[] args) {
        //VALORES DE PRUEBA
        Normal.cantMaxReproduccionesTendenciaNormal = 2;
        EnAuge.cantMaxLikesEsperados = 3;
        EnAuge.cantMaxReproduccionesenAuge = 3;

        Artista coldplay = new Artista();
        coldplay.setNombre("Coldplay");

        Album aRushOfBloodToTheHead = new Album();
        aRushOfBloodToTheHead.setNombre("A Rush of Blood to the head");

        Cancion theScientist = new Cancion();
        theScientist.setTitulo("The Scientist");

        theScientist.setAnio(2002);
        theScientist.setAlbum(aRushOfBloodToTheHead);
        theScientist.setArtista(coldplay);

        reproducirYMostrarDetalle(theScientist);
        reproducirYMostrarDetalle(theScientist);
        reproducirYMostrarDetalle(theScientist);

        theScientist.recibirLike();
        theScientist.recibirLike();
        theScientist.recibirLike();
        theScientist.recibirLike();

        reproducirYMostrarDetalle(theScientist);
        reproducirYMostrarDetalle(theScientist);
        reproducirYMostrarDetalle(theScientist);
        reproducirYMostrarDetalle(theScientist);

    }

    private static void reproducirYMostrarDetalle(Cancion cancion) {
        cancion.reproducir();
        System.out.println(cancion.detalleCompleto());
    }
}
