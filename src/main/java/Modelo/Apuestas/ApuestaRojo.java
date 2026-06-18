package Modelo.Apuestas;

public final class ApuestaRojo extends ApuestaBase {

    public ApuestaRojo(int monto) {
        super(monto, "ROJO");
    }

    @Override
    public boolean acierta(int numero, String color) {
        return numero != 0 && "ROJO".equalsIgnoreCase(color);
    }
}