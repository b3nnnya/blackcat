package Modelo.Apuestas;

public final class ApuestaNegro extends ApuestaBase {

    public ApuestaNegro(int monto) {
        super(monto, "NEGRO");
    }

    @Override
    public boolean acierta(int numero, String color) {
        return numero != 0 && "NEGRO".equalsIgnoreCase(color);
    }
}