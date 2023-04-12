package domain.tendencias;

import domain.catalogo.Cancion;

public abstract class Popularidad {

    public abstract void reproducir(Cancion cancion);

    protected abstract String icono();
    protected abstract String leyenda(Cancion cancion);

    public String detalle(Cancion cancion) {
        String titulo = this.icono();
        titulo += this.leyenda(cancion);
        return titulo;
    }

    public void recibirDislike() {

    }
}
