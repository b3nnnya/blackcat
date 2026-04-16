package Vista;

import Modelo.Usuario;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class VentanaLogin {

    // --- Lista dinámica de usuarios ---
    public static final List<Usuario> USUARIOS = new ArrayList<>();

    // --- Componentes UI ---
    private final JFrame frame = new JFrame("Login Casino Black Cat");
    private final JLabel lblUsuario = new JLabel("Usuario:");
    private final JTextField txtUsuario = new JTextField();
    private final JLabel lblClave = new JLabel("Clave:");
    private final JPasswordField txtClave = new JPasswordField();

    // Botones
    private final JButton btnIngresar = new JButton("Ingresar");
    private final JButton btnRegistrar = new JButton("Registrar");

    public VentanaLogin() {
        inicializarUsuarios();
        configurarVentana();
        agregarPosiciones();
        configurarEventos();
    }

    private void inicializarUsuarios() {
        if (USUARIOS.isEmpty()) {
            USUARIOS.add(new Usuario("admin", "1234", "Don Donnie"));
            USUARIOS.add(new Usuario("jugador1", "gato", "Invitado Estrella"));
        }
    }

    private void configurarVentana() {
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setResizable(false);
    }

    private void agregarPosiciones() {
        lblUsuario.setBounds(30, 20, 80, 25);
        txtUsuario.setBounds(100, 20, 150, 25);
        lblClave.setBounds(30, 60, 80, 25);
        txtClave.setBounds(100, 60, 150, 25);

        btnIngresar.setBounds(40, 110, 100, 30);
        btnRegistrar.setBounds(150, 110, 100, 30);

        frame.add(lblUsuario); frame.add(txtUsuario);
        frame.add(lblClave); frame.add(txtClave);
        frame.add(btnIngresar); frame.add(btnRegistrar);
    }

    private void configurarEventos() {
        // Usamos expresiones lambda (->) que hacen el código más corto y limpio
        btnIngresar.addActionListener(e -> login());
        btnRegistrar.addActionListener(e -> abrirRegistro());
    }

    private void login() {
        String u = txtUsuario.getText().trim();
        String p = new String(txtClave.getPassword());

        String nombreUsuario = validarCredenciales(u, p);
        evaluarAcceso(nombreUsuario);
    }

    private void evaluarAcceso(String nombre) {
        if (!nombre.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "¡Bienvenido al Casino, " + nombre + "!");
            frame.dispose(); // Cierra el login

            // Abre el Menú Principal
            new VentanaMenu(nombre).mostrarVentana();
        } else {
            JOptionPane.showMessageDialog(frame, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String validarCredenciales(String u, String p) {
        for (Usuario user : USUARIOS) {
            if (user.validarCredenciales(u, p)) {
                return user.getNombre();
            }
        }
        return "";
    }

    private void abrirRegistro() {
        frame.dispose(); // Cierra el login
        new VentanaRegistro().mostrarVentana(); // Abre la ventana para crear cuenta
    }

    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}