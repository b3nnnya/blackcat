package Controlador;

import Modelo.IRepositorioResultados;
import Modelo.Usuario;

public class SessionController {
    private Usuario usuario;
    private final IRepositorioResultados repositorio;

    public SessionController(IRepositorioResultados repositorio) {
        this.repositorio = repositorio;
    }

    public void registrarUsuario(String username, String password, String nombre) {
        this.usuario = new Usuario(username, password, nombre, repositorio);
    }

    public boolean iniciarSesion(String username, String password) {
        if (usuario == null) return false;
        return usuario.validarCredenciales(username, password);
    }

    public boolean hayUsuarioRegistrado() { return usuario != null; }
    public String getNombreUsuario() { return (usuario != null) ? usuario.getNombre() : null; }
    public Usuario getUsuarioActual() { return usuario; }
    public IRepositorioResultados getRepositorio() { return repositorio; }
    public void cerrarSesion() { this.usuario = null; }
}