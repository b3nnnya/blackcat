package Modelo;

import java.util.List;

public class Usuario {
  private String username;
  private String password;
  private String nombre;

  private final IRepositorioResultados repositorio;

  public Usuario(String username, String password, String nombre, IRepositorioResultados repositorio) {
    this.username    = username;
    this.password    = password;
    this.nombre      = nombre;
    this.repositorio = repositorio;
  }

  public boolean validarCredenciales(String u, String p) {
    // Un usuario con username nulo o un intento con identificador nulo nunca
    // puede autenticarse: se rechaza la operación en lugar de lanzar NPE.
    if (this.username == null || u == null) return false;
    return this.username.equals(u) && this.password.equals(p);
  }

  public String getNombre() {
    return nombre;
  }

  public List<Resultado> getHistorial() {
    return repositorio.obtenerHistorial();
  }

  public void agregarResultado(Resultado r) {
    if (r == null)
      throw new IllegalArgumentException("Resultado requerido");
    repositorio.guardarResultado(r);
  }

  public IRepositorioResultados getRepositorio() {
    return repositorio;
  }
}
