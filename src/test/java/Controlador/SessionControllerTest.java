package Controlador;

import Modelo.IRepositorioResultados;
import Modelo.RepositorioEnMemoria;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Pruebas de la lógica de autenticación.
 * Cubre los casos 6 y 7 del Cuadro 1 (casos negativos).
 */
class SessionControllerTest {

    private SessionController nuevaSesion() {
        IRepositorioResultados repo = new RepositorioEnMemoria();
        return new SessionController(repo);
    }

    @Test
    @DisplayName("Caso 6: el inicio de sesión con un usuario no registrado es denegado")
    void loginUsuarioNoRegistradoEsDenegado() {
        SessionController session = nuevaSesion();
        assertFalse(session.iniciarSesion("fantasma", "1234"),
                "no debe permitir iniciar sesión sin usuario registrado");
    }

    @Test
    @DisplayName("Caso 7: el inicio de sesión con username nulo es rechazado")
    void loginConUsernameNuloEsRechazado() {
        SessionController session = nuevaSesion();
        session.registrarUsuario(null, "1234", "Sin Nombre");
        assertFalse(session.iniciarSesion(null, "1234"),
                "un username nulo no es un identificador válido");
    }
}
