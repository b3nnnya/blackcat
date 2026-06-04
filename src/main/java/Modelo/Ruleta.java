package Modelo;

import Modelo.Apuestas.ApuestaBase;

import java.util.List;
import java.util.Random;

public class Ruleta {

    private static final int[] NUMEROS_ROJOS = {
            1,3,5,7,9,12,14,16,18,19,21,23,25,27,30,32,34,36
    };

    private int saldo;
    private final Random                    rng         = new Random();
    private final IRepositorioResultados    repositorio;

    public Ruleta(IRepositorioResultados repositorio) {
        this(0, repositorio);
    }

    public Ruleta(int saldoInicial, IRepositorioResultados repositorio) {
        if (saldoInicial < 0) throw new IllegalArgumentException("Saldo inicial inválido");
        if (repositorio == null) throw new IllegalArgumentException("Repositorio requerido");
        this.saldo       = saldoInicial;
        this.repositorio = repositorio;
    }

    public int getSaldo() { return saldo; }

    public void depositar(int monto) {
        if (monto <= 0) throw new IllegalArgumentException("Monto inválido");
        saldo += monto;
    }

    public int girar() {
        return rng.nextInt(37);
    }

    public String colorDe(int numero) {
        if (numero == 0) return "VERDE";
        return esRojo(numero) ? "ROJO" : "NEGRO";
    }

    public Resultado jugar(ApuestaBase apuesta) {
        if (apuesta == null) throw new IllegalArgumentException("Apuesta requerida");

        int monto = apuesta.getMonto();
        if (monto <= 0) throw new IllegalArgumentException("Monto inválido");
        if (monto > saldo) throw new IllegalArgumentException("Saldo insuficiente");

        int numero      = girar();
        String color    = colorDe(numero);
        boolean acierto = apuesta.acierta(numero, color);

        saldo += acierto ? monto : -monto;

        Resultado resultado = new Resultado(numero, color, acierto, saldo, apuesta);
        repositorio.guardarResultado(resultado);
        return resultado;
    }

    public List<Resultado> getResultados() {
        return repositorio.obtenerHistorial();
    }

    private boolean esRojo(int n) {
        for (int r : NUMEROS_ROJOS) if (r == n) return true;
        return false;
    }
}