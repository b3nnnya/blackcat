package Vista;

import javax.swing.*;
import Controlador.SessionController;

public class VentanaRegistro {
    private final SessionController session;
    private final JFrame            frame           = new JFrame("Registro - Casino Black Cat");
    private final JLabel            lblUsuario      = new JLabel("Usuario:");
    private final JTextField        txtUsuario      = new JTextField();
    private final JLabel            lblClave        = new JLabel("Clave:");
    private final JPasswordField    txtClave        = new JPasswordField();
    private final JLabel            lblNombre       = new JLabel("Nombre completo:");
    private final JTextField        txtNombre       = new JTextField();
    private final JButton           btnRegistrar    = new JButton("Registrar");
    private final JButton           btnCancelar     = new JButton("Cancelar");

    public VentanaRegistro(SessionController session) {
        this.session = session;

        frame.setSize(420, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        lblUsuario.setBounds(50, 30, 120, 25);
        txtUsuario.setBounds(180, 30, 160, 25);

        lblClave.setBounds(50, 70, 120, 25);
        txtClave.setBounds(180, 70, 160, 25);

        lblNombre.setBounds(50, 110, 120, 25);
        txtNombre.setBounds(180, 110, 160, 25);

        btnRegistrar.setBounds(80, 160, 100, 30);
        btnCancelar.setBounds(200, 160, 100, 30);

        frame.add(lblUsuario);
        frame.add(txtUsuario);
        frame.add(lblClave);
        frame.add(txtClave);
        frame.add(lblNombre);
        frame.add(txtNombre);
        frame.add(btnRegistrar);
        frame.add(btnCancelar);

        btnRegistrar.addActionListener(e -> registrarUsuario());
        btnCancelar.addActionListener(e -> cancelar());
        frame.getRootPane().setDefaultButton(btnRegistrar);
    }

    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void registrarUsuario() {
        String usuario = txtUsuario.getText().trim();
        String clave   = new String(txtClave.getPassword());
        String nombre  = txtNombre.getText().trim();

        if (usuario.isEmpty() || clave.isEmpty() || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Todos los campos son obligatorios",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            session.registrarUsuario(usuario, clave, nombre);
            JOptionPane.showMessageDialog(frame,
                    "Usuario registrado con éxito:\n" + nombre,
                    "Registro", JOptionPane.INFORMATION_MESSAGE);

            new VentanaLogin(session).mostrarVentana();
            frame.dispose();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelar() {
        new VentanaLogin(session).mostrarVentana();
        frame.dispose();
    }
}