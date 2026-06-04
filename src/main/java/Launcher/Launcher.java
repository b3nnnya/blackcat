package Launcher;

import Vista.VentanaLogin;
import Controlador.SessionController;
import Modelo.IRepositorioResultados;
import Modelo.RepositorioEnMemoria;
// import Modelo.RepositorioArchivo;
import com.formdev.flatlaf.intellijthemes.FlatDarkPurpleIJTheme;
import javax.swing.*;

public class Launcher {
    public static void main(String[] args) {
        // ----- APARIENCIA -----
        try {
            UIManager.setLookAndFeel(new FlatDarkPurpleIJTheme());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        IRepositorioResultados repositorio = new RepositorioEnMemoria();

        SessionController session = new SessionController(repositorio);
        VentanaLogin login = new VentanaLogin(session);
        login.mostrarVentana();
    }
}