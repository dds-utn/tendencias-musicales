package domain.helpers;

public class Icono {
    public static int[] MUSICAL_NOTE = {0xD83C, 0xDFB5};
    public static int[] ROCKET = {0xD83D, 0xDE80};
    public static int[] FIRE = {0xD83D, 0xDD25};

    public static String textoDelIcono(int[] icono) {
        return new String(icono, 0, icono.length);
    }
}
