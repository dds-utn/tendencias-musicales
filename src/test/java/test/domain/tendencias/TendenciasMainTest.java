package test.domain.tendencias;

import domain.catalogo.Album;
import domain.catalogo.Artista;
import domain.catalogo.Cancion;

public class TendenciasMainTest {

    public static void main(String[] args) {
        Artista coldplay = new Artista();
        coldplay.setNombre("Coldplay");

        Album aRushOfBloodToTheHead = new Album();
        aRushOfBloodToTheHead.setNombre("A Rush of Blood to the head");

        Cancion theScientist = new Cancion();
        theScientist.setTitulo("The Scientist");

        theScientist.setAnio(2002);
        theScientist.setAlbum(aRushOfBloodToTheHead);
        theScientist.setArtista(coldplay);

        System.out.println(theScientist.serEscuchada());
        System.out.println(theScientist.serEscuchada());
        System.out.println(theScientist.serEscuchada());
        System.out.println(theScientist.serEscuchada());
        System.out.println(theScientist.serEscuchada());
        System.out.println(theScientist.serEscuchada());
        System.out.println(theScientist.serEscuchada());
        System.out.println(theScientist.serEscuchada());
    }
}
