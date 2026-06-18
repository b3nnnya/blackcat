package Modelo;

import Modelo.Apuestas.ApuestaBase;

public class Resultado {
    private final int           numero;
    private final String        color;
    private final boolean       acierto;
    private final int           saldoPosterior;
    private final ApuestaBase apuesta;

    public Resultado(int numero, String color, boolean acierto, int saldoPosterior, ApuestaBase apuesta) {
        this.numero         = numero;
        this.color          = color;
        this.acierto        = acierto;
        this.saldoPosterior = saldoPosterior;
        this.apuesta        = apuesta;
    }

    public int getNumero() { return numero; }
    public String getColor() { return color; }
    public boolean getAcierto() { return acierto; }
    public int getSaldoPosterior() { return saldoPosterior; }
    public ApuestaBase getApuesta() { return apuesta; }
}