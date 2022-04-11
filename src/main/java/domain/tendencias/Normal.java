package domain.tendencias;

import domain.catalogo.Cancion;
import domain.helpers.Icono;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Normal extends Popularidad {
    public static Integer cantMaxReproduccionesTendenciaNormal = 100;
    public static Integer cantHorasClaveParaTrascender = 24;
    private Integer cantReproduccionesIniciales;


    public Normal(Cancion cancion) {
        this.cantReproduccionesIniciales = cancion.getCantReproducciones();
    }

    @Override
    public void reproducir(Cancion cancion) {
        if(this.superoLasPrimerasReproducciones(cancion) || this.vuelveASuperarReproducciones(cancion)) {
            cancion.setEstado(new EnAuge());
        }
    }

    private Boolean superoLasPrimerasReproducciones(Cancion cancion) {
        return cancion.getCantReproducciones() == 0 && cancion.getCantReproducciones() > cantMaxReproduccionesTendenciaNormal;
    }

    private Boolean vuelveASuperarReproducciones(Cancion cancion) {
        return cancion.getCantReproducciones() != 0
                && (cancion.getCantReproducciones() - this.cantReproduccionesIniciales) > cantMaxReproduccionesTendenciaNormal
                && ChronoUnit.HOURS.between(cancion.getUltVezEscuchada(), LocalDateTime.now()) < cantHorasClaveParaTrascender;
    }

    @Override
    protected String icono() {
        return Icono.textoDelIcono(Icono.MUSICAL_NOTE);
    }

    @Override
    protected String leyenda(Cancion cancion) {
        return armarLeyendaPara(cancion);
    }

    public static String armarLeyendaPara(Cancion cancion) {
        return String.format("%s - %s - %s", cancion.getArtista().getNombre(), cancion.getAlbum().getNombre(), cancion.getTitulo());
    }
}
