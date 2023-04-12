package domain.tendencias;

import domain.catalogo.Cancion;
import domain.helpers.Icono;

public class Normal extends Popularidad {
    public static Integer cantMaxReproduccionesTendenciaNormal = 1000;
    private Integer cantReproduccionesIniciales;

    public Normal(int cantReproducciones) {
        this.cantReproduccionesIniciales = cantReproducciones;
    }

    @Override
    public void reproducir(Cancion cancion) {
        if(this.superaReproducciones(cancion)) {
            cancion.setPopularidad(new EnAuge(cancion.getCantReproducciones()));
        }
    }

    private Boolean superaReproducciones(Cancion cancion) {
        return this.cantReproduccionesEnEstaPopularidad(cancion) > cantMaxReproduccionesTendenciaNormal;
    }

    private Integer cantReproduccionesEnEstaPopularidad(Cancion cancion) {
        return cancion.getCantReproducciones() - this.cantReproduccionesIniciales;
    }

    @Override
    protected String icono() {
        return Icono.MUSICAL_NOTE.texto();
    }

    @Override
    protected String leyenda(Cancion cancion) {
        return armarLeyendaPara(cancion);
    }

    public static String armarLeyendaPara(Cancion cancion) {
        return String.format("%s - %s - %s", cancion.getArtista().getNombre(), cancion.getAlbum().getNombre(), cancion.getTitulo());
    }
}
