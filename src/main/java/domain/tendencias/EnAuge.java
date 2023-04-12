package domain.tendencias;

import domain.catalogo.Cancion;
import domain.helpers.Icono;

public class EnAuge extends Popularidad {
    public static Integer cantMaxReproduccionesenAuge = 50000;
    public static Integer cantMaxLikesEsperados = 20000;
    public static Integer cantMaxDislikesSoportados = 5000;
    private Integer cantReproduccionesIniciales;
    private Integer cantDislikes;

    public EnAuge(int cantReproducciones) {
        this.cantReproduccionesIniciales = cantReproducciones;
        this.cantDislikes = 0;
    }

    @Override
    public void reproducir(Cancion cancion) {
        if(this.superaReproducciones(cancion) && this.tieneMasDeLosLikesEsperados(cancion)) {
            cancion.setPopularidad(new EnTendencia());
        }
        else if(this.superaCantDislikes()) {
            cancion.setPopularidad(new Normal(cancion.getCantReproducciones()));
        }
    }

    private Boolean superaCantDislikes() {
        return this.cantDislikes > cantMaxDislikesSoportados;
    }

    private Boolean tieneMasDeLosLikesEsperados(Cancion cancion) {
        return cancion.getCantLikes() > cantMaxLikesEsperados;
    }

    private Boolean superaReproducciones(Cancion cancion) {
        return this.cantReproduccionesEnEstaPopularidad(cancion) > cantMaxReproduccionesenAuge;
    }

    private Integer cantReproduccionesEnEstaPopularidad(Cancion cancion) {
        return cancion.getCantReproducciones() - this.cantReproduccionesIniciales;
    }

    @Override
    public void recibirDislike() {
        this.cantDislikes++;
    }

    @Override
    protected String icono() {
        return Icono.ROCKET.texto();
    }

    @Override
    protected String leyenda(Cancion cancion) {
        return armarLeyendaPara(cancion);
    }

    public static String armarLeyendaPara(Cancion cancion) {
        return String.format("%s - %s (%s - %d)", cancion.getArtista().getNombre(), cancion.getTitulo(), cancion.getAlbum().getNombre(), cancion.getAnio());
    }
}
