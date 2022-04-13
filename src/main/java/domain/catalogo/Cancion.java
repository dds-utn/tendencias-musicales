package domain.catalogo;

import domain.tendencias.Popularidad;
import domain.tendencias.Normal;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class Cancion {
    @Setter @Getter private String titulo;

    @Setter @Getter private Artista artista;

    @Setter @Getter private Album album;

    @Setter @Getter private Integer anio;

    @Getter private Integer cantReproducciones;

    @Setter private Popularidad popularidad;

    @Getter private LocalDateTime ultVezEscuchada;

    public Cancion() {
        this.cantReproducciones = 0;
        this.popularidad = new Normal(this);
    }

    public String detalleCompleto() {
        return this.popularidad.detalle(this);
    }

    private void reproducir() {
        this.cantReproducciones++;
        this.ultVezEscuchada = LocalDateTime.now();
        this.popularidad.reproducir(this);
    }

    public String serEscuchada() {
        this.reproducir();
        return this.detalleCompleto();
    }
}
