package domain.helpers;

public class Icono {
    public static Icono MUSICAL_NOTE = new Icono(new int[]{0xD83C, 0xDFB5});
    public static Icono ROCKET = new Icono(new int[]{0xD83C, 0xDFB5});
    public static Icono FIRE = new Icono(new int[]{0xD83C, 0xDFB5});

    private int[] internalEncoding;

    Icono(int[] internalEncoding){
        this.internalEncoding = internalEncoding;
    }

    public String texto() {
        return new String(internalEncoding, 0, internalEncoding.length);
    }
}
