import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que representa la ventana de Login del Casino Black Cat.
 * Permite ingresar credenciales para acceder al sistema.
 */
public class VentanaLogin {

    // --- Lista dinámica de usuarios ---
    public static final List<Usuario> USUARIOS = new ArrayList<>();

    // --- UI Componentes ---
    private final JFrame frame = new JFrame("Login Casino Black Cat");
    private final JLabel lblUsuario = new JLabel("Usuario:");
    private final JTextField txtUsuario = new JTextField();
    private final JLabel lblClave = new JLabel("Clave:");
    private final JPasswordField txtClave = new JPasswordField();
    private final JButton btnIngresar = new JButton("Ingresar");
    private final JButton btnRegistrar = new JButton("Registrar");

    /**
     * Constructor que inicializa la ventana de login.
     */
    public VentanaLogin() {
        inicializarUsuarios();
        configurarVentana();
        agregarPosiciones();
        configurarEventos();
    }

    // --- MÉTODOS DE CONFIGURACIÓN (Para mantener métodos cortos) ---

    private void inicializarUsuarios() {
        if (USUARIOS.isEmpty()) {
            USUARIOS.add(new Usuario("benya", "1234", "Don Benya"));
            USUARIOS.add(new Usuario("blackcat", "gato", "Black Cat"));
        }
    }

    private void configurarVentana() {
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null); // Usamos null para posicionar por coordenadas
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
        frame.add(btnIngresar);
        frame.add(btnRegistrar);
    }

    private void configurarEventos() {
        btnIngresar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirRegistro();
            }
        });
    }

    // --- MÉTODOS DE LÓGICA PRINCIPAL ---

    /**
     * Muestra la ventana en pantalla.
     * Debe centrarla y hacerla visible.
     */
    public void mostrarVentana() {
        frame.setLocationRelativeTo(null); // Centra la ventana en la pantalla
        frame.setVisible(true);
    }

    /**
     * Maneja el evento de login al presionar el botón.
     */
    private void login() {
        String u = txtUsuario.getText();
        String p = new String(txtClave.getPassword());

        String nombreUsuario = validarCredenciales(u, p);
        evaluarAcceso(nombreUsuario);
    }

    /**
     * Evalúa si dar acceso o mostrar error
     */
    private void evaluarAcceso(String nombre) {
        if (!nombre.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "¡Bienvenido al Casino, " + nombre + "!");
            frame.dispose(); // Cierra la ventana de login
            Ruleta.menu(); // Abre la ruleta
        } else {
            JOptionPane.showMessageDialog(frame, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Valida las credenciales del usuario contra el arreglo hardcoded.
     */
    private String validarCredenciales(String u, String p) {
        for (Usuario user : USUARIOS) {
            if (user.validarCredenciales(u, p)) {
                return user.getNombre();
            }
        }
        return ""; // Retorna vacío si no encuentra coincidencia
    }

    /**
     * Abre la ventana de registro para crear un nuevo usuario.
     */
    void abrirRegistro() {
        frame.dispose();
        new VentanaRegistro().mostrarVentana();
    }
}