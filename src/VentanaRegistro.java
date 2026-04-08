import javax.swing.*;
import java.awt.event.ActionEvent;

public class VentanaRegistro {

    // --- UI Componentes ---
    private final JFrame frame = new JFrame("Registro Casino Black Cat");
    private final JLabel lblUsuario = new JLabel("Usuario:");
    private final JTextField txtUsuario = new JTextField();

    private final JLabel lblClave = new JLabel("Clave:");
    private final JPasswordField txtClave = new JPasswordField();

    private final JLabel lblNombre = new JLabel("Nombre:");
    private final JTextField txtNombre = new JTextField();

    private final JButton btnRegistrar = new JButton("Registrar");

    public VentanaRegistro() {
        configurarVentana();
        agregarPosiciones();
        configurarEventos();
    }

    private void configurarVentana() {
        frame.setSize(300, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setResizable(false);
    }

    private void agregarPosiciones() {
        lblUsuario.setBounds(30, 20, 80, 25);
        txtUsuario.setBounds(100, 20, 150, 25);

        lblClave.setBounds(30, 60, 80, 25);
        txtClave.setBounds(100, 60, 150, 25);

        lblNombre.setBounds(30, 100, 80, 25);
        txtNombre.setBounds(100, 100, 150, 25);

        btnRegistrar.setBounds(100, 150, 100, 30);

        frame.add(lblUsuario); frame.add(txtUsuario);
        frame.add(lblClave);   frame.add(txtClave);
        frame.add(lblNombre);  frame.add(txtNombre);
        frame.add(btnRegistrar);
    }

    private void configurarEventos() {
        btnRegistrar.addActionListener(this::registrarUsuario);
    }

    private void registrarUsuario(ActionEvent e) {
        String usuario = txtUsuario.getText().trim();
        String clave = new String(txtClave.getPassword());
        String nombre = txtNombre.getText().trim();

        if (usuario.isEmpty() || clave.isEmpty() || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(frame,
                    "Todos los campos son obligatorios",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        VentanaLogin.USUARIOS.add(new Usuario(usuario, clave, nombre));

        JOptionPane.showMessageDialog(frame,
                "Usuario registrado con éxito",
                "Registro", JOptionPane.INFORMATION_MESSAGE);

        frame.dispose(); // Cierra esta ventana
        new VentanaLogin().mostrarVentana(); // Vuelve a abrir el login
    }

    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}