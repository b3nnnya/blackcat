package Modelo.Apuestas;

public abstract class ApuestaBase {
    private final int       monto;
    private final String    etiqueta;

    protected ApuestaBase(int monto, String etiqueta) {
        this.monto      = monto;
        this.etiqueta   = etiqueta;
    }

    public int getMonto() {
        return monto;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public abstract boolean acierta(int numero, String color);
}