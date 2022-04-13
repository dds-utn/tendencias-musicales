package domain.tendencias;

import domain.catalogo.Cancion;
import domain.helpers.Icono;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class EnAuge extends Popularidad {
    public static Integer cantMaxReproduccionesenAuge = 50000;
    public static Integer cantHorasMinParaTrascender = 48;
    public static Integer horasDeToleranciaEnAuge = 72;
    private Integer cantReproduccionesIniciales;
    private LocalDateTime fechaHoraIngresoEnAuge;

    public EnAuge(Cancion cancion) {
        this.cantReproduccionesIniciales = cancion.getCantReproducciones();
        this.fechaHoraIngresoEnAuge = LocalDateTime.now();
    }

    @Override
    public void reproducir(Cancion cancion) {
        if(this.superaReproduccionesEnTiempoRecord(cancion)) {
            cancion.setPopularidad(new EnTendencia());
        }
        else if (this.fuePocoEscuchada(cancion)) {
            cancion.setPopularidad(new Normal(cancion));
        }
    }

    private Boolean fuePocoEscuchada(Cancion cancion) {
        return ChronoUnit.HOURS.between(cancion.getUltVezEscuchada(), LocalDateTime.now()) >= horasDeToleranciaEnAuge;
    }

    private Boolean superaReproduccionesEnTiempoRecord(Cancion cancion) {
        return this.cantReproduccionesEnEstaPopularidad(cancion) > cantMaxReproduccionesenAuge
                && ChronoUnit.HOURS.between(cancion.getUltVezEscuchada(), this.fechaHoraIngresoEnAuge) < cantHorasMinParaTrascender;
    }

    private Integer cantReproduccionesEnEstaPopularidad(Cancion cancion) {
        return cancion.getCantReproducciones() - this.cantReproduccionesIniciales;
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
