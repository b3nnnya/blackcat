package Controlador;

import Modelo.Resultado;
import Modelo.Usuario;
import java.util.List;

public class ResultadoController {

    private final SessionController session;

    public ResultadoController(SessionController session) {
        this.session = session;
    }

    private Usuario obtenerUsuarioActual() {
        Usuario usuario = session.getUsuarioActual();
        if (usuario == null) throw new IllegalStateException("No hay usuario autenticado"); 
        return usuario;
    }

    public List<Resultado> getHistorial() {
        return obtenerUsuarioActual().getHistorial();
    }

    public Resultado getUltimoResultado() {
        List<Resultado> historial = obtenerUsuarioActual().getHistorial();
        if (historial.isEmpty()) { return null;}
        Resultado ultimoResultado = historial.get(historial.size() - 1);
        return ultimoResultado;
    }
}