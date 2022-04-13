package domain.tendencias;

import domain.catalogo.Cancion;
import domain.helpers.Icono;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Normal extends Popularidad {
    public static Integer cantMaxReproduccionesTendenciaNormal = 100;
    public static Integer cantHorasClaveParaTrascender = 24;
    private Integer cantReproduccionesIniciales;
    private LocalDateTime fechaHoraIngresoEnNormal;


    public Normal(Cancion cancion) {
        this.cantReproduccionesIniciales = cancion.getCantReproducciones();
        this.fechaHoraIngresoEnNormal = LocalDateTime.now();
    }

    @Override
    public void reproducir(Cancion cancion) {
        if(this.superaReproducciones(cancion)) {
            cancion.setPopularidad(new EnAuge(cancion));
        }
    }

    private Boolean superaReproducciones(Cancion cancion) {
        return this.cantReproduccionesEnEstaPopularidad(cancion) > cantMaxReproduccionesTendenciaNormal
                && ChronoUnit.HOURS.between(this.fechaHoraIngresoEnNormal, cancion.getUltVezEscuchada()) < cantHorasClaveParaTrascender;
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
