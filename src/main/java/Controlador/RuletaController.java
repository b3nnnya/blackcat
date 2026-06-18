package Controlador;

import Modelo.Ruleta;
import Modelo.Resultado;
import Modelo.Usuario;
import Modelo.Apuestas.ApuestaBase;

public class RuletaController {

    private final Ruleta            ruleta;
    private final SessionController session; // Para acceder al usuario actual

    public RuletaController(Ruleta ruleta, SessionController session) {
        this.ruleta     = ruleta;
        this.session    = session;
    }

    public int getSaldo() { return ruleta.getSaldo(); }

    public void depositar(int monto) {
        ruleta.depositar(monto);
    }

    public boolean puedeApostar(ApuestaBase apuesta) {
        if (apuesta == null) return false;
        int monto = apuesta.getMonto();
        return monto > 0 && monto <= ruleta.getSaldo();
    }

    public Resultado jugar(ApuestaBase apuesta) {
        if (!puedeApostar(apuesta)) throw new IllegalArgumentException("Monto inválido o saldo insuficiente");
        Resultado resultado = ruleta.jugar(apuesta);
        Usuario usuario = session.getUsuarioActual();
        if (usuario != null) {
            usuario.agregarResultado(resultado);
        }
        return resultado;
    }
}
