package domain.tendencias;

import domain.catalogo.Cancion;
import domain.helpers.Icono;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class EnTendencia extends Popularidad {
    public static Integer cantHorasSinEscucharParaBajarPopularidad = 24;
    @Setter private LocalDateTime fechaAComparar;

    public LocalDateTime getFechaAComparar() {
        return fechaAComparar == null? LocalDateTime.now() : this.fechaAComparar;
    }

    @Override
    public void reproducir(Cancion cancion) {
        if(this.hanPasadoMasDeHsDesde( cancion.getUltVezEscuchada(), cantHorasSinEscucharParaBajarPopularidad)) {
            cancion.setPopularidad(new Normal(cancion.getCantReproducciones()));
        }
    }

    private Boolean hanPasadoMasDeHsDesde(LocalDateTime fechaHora, int horas) {
        return ChronoUnit.HOURS.between(fechaHora, this.getFechaAComparar()) >= horas;
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
