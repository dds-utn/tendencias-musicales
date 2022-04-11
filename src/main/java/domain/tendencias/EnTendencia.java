package domain.tendencias;

import domain.catalogo.Cancion;
import domain.helpers.Icono;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class EnTendencia extends Popularidad {
    public static Integer cantHorasSinEscucharParaBajarPopularidad = 24;

    @Override
    public void reproducir(Cancion cancion) {
        if(this.bajoPopularidadDrasticamente(cancion)) {
            cancion.setEstado(new Normal(cancion));
        }
    }

    private Boolean bajoPopularidadDrasticamente(Cancion cancion) {
        return ChronoUnit.HOURS.between(cancion.getUltVezEscuchada(), LocalDateTime.now()) >= cantHorasSinEscucharParaBajarPopularidad;
    }

    @Override
    protected String icono() {
        return Icono.FIRE.texto();
    }

    @Override
    protected String leyenda(Cancion cancion) {
        return armarLeyendaPara(cancion);
    }

    public static String armarLeyendaPara(Cancion cancion) {
        return String.format("%s - %s (%s - %d)", cancion.getTitulo(), cancion.getArtista().getNombre(), cancion.getAlbum().getNombre(), cancion.getAnio());
    }
}
