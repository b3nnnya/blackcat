package Vista;

import Controlador.SessionController;

import javax.swing.*;
import java.awt.*;

public class VentanaLogin {
    private VentanaRegistro ventanaRegistro;
    private final SessionController session;
    private final JFrame            frame           = new JFrame("Login - Casino Black Cat");
    private final JTextField        txtUsuario      = new JTextField(18);
    private final JPasswordField    txtClave        = new JPasswordField(18);
    private final JButton           btnIngresar     = new JButton("Ingresar");
    private final JButton           btnRegistrar    = new JButton("Registrar");

    public VentanaLogin(SessionController session) {
        this.session = session;

        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Usuario
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Usuario:"), gbc);
        gbc.gridx = 1;
        panel.add(txtUsuario, gbc);

        // Clave
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Clave:"), gbc);
        gbc.gridx = 1;
        panel.add(txtClave, gbc);

        // Botones
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        Dimension tamBoton = new Dimension(120, 32);
        btnIngresar.setPreferredSize(tamBoton);
        btnRegistrar.setPreferredSize(tamBoton);
        botones.add(btnIngresar);
        botones.add(btnRegistrar);

        gbc.gridx = 1; gbc.gridy = 2; gbc.gridwidth = 1;
        panel.add(botones, gbc);

        frame.add(panel);

        btnIngresar .addActionListener(e -> login());
        btnRegistrar.addActionListener(e -> abrirRegistro());
        frame.getRootPane().setDefaultButton(btnIngresar);
    }

    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void login() {
        String usuario      = txtUsuario.getText().trim();
        String contrasena   = new String(txtClave.getPassword());

        boolean ok = session.iniciarSesion(usuario, contrasena);
        if (ok) {
            new VentanaMenu(session).mostrar();
            frame.dispose();
        } else {
            JOptionPane.showMessageDialog(frame,
                    "Credenciales incorrectas",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            txtClave.setText("");
            txtClave.requestFocus();
        }
    }

    private void abrirRegistro() {
        if (ventanaRegistro == null) {
            ventanaRegistro = new VentanaRegistro(session);
        }
        ventanaRegistro.mostrarVentana();
        frame.dispose();
    }
}