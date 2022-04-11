package domain.tendencias;

import domain.catalogo.Cancion;
import domain.helpers.Icono;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class EnAuge extends Popularidad {
    public static Integer cantMaximaReproduccionesEnAuge = 50000;
    public static Integer cantHorasMinParaTrascender = 48;
    public static Integer cantHorasMaxSinSerEscuchada = 72;

    @Override
    public void reproducir(Cancion cancion) {
        if(this.superaReproduccionesEnTiempoRecord(cancion)) {
            cancion.setEstado(new EnTendencia());
        }
        else if (this.fuePocoEscuchada(cancion)) {
            cancion.setEstado(new Normal(cancion));
        }
    }

    private Boolean fuePocoEscuchada(Cancion cancion) {
        return ChronoUnit.HOURS.between(cancion.getUltVezEscuchada(), LocalDateTime.now()) >= cantHorasMaxSinSerEscuchada;
    }

    private Boolean superaReproduccionesEnTiempoRecord(Cancion cancion) {
        return cancion.getCantReproducciones() > cantMaximaReproduccionesEnAuge
                && ChronoUnit.HOURS.between(cancion.getUltVezEscuchada(), LocalDateTime.now()) < cantHorasMinParaTrascender;
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
