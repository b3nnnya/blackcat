package Vista;

import javax.swing.*;
import java.awt.Font;

public class VentanaMenu {

    // --- Componentes UI ---
    private final JFrame frame = new JFrame("Menú - Casino Black Cat");

    private final JButton btnInicio = new JButton("Inicio");
    private final JButton btnJugar = new JButton("Jugar");
    private final JButton btnHistorial = new JButton("Historial");
    private final JButton btnSalir = new JButton("Salir");

    private final JLabel lblTitulo = new JLabel("RULETA — Casino Black Cat");
    private final JLabel lblUsuario = new JLabel();
    private final JTextArea txtInfo = new JTextArea();

    private final String nombreUsuario;

    /**
     * Constructor que recibe el nombre del usuario logueado.
     */
    public VentanaMenu(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
        configurarVentana();
        configurarTextos();
        agregarPosiciones();
        configurarEventos();
    }

    private void configurarVentana() {
        frame.setSize(500, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setResizable(false);
    }

    private void configurarTextos() {
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16)); // Texto más grande
        lblUsuario.setText("Usuario actual: " + nombreUsuario);

        // El texto descriptivo del menú
        txtInfo.setText("Bienvenido/a al menú principal.\n\nA la izquierda tienes:\n" +
                "• Jugar: abre la ventana de juego.\n" +
                "• Historial: abre la ventana de historial (próximamente).\n" +
                "• Salir: cierra sesión y vuelve al login.");
        txtInfo.setEditable(false); // Para que el usuario no pueda borrar el texto
        txtInfo.setOpaque(false);   // Para que el fondo sea transparente
    }

    private void agregarPosiciones() {
        // Botones a la izquierda
        btnInicio.setBounds(20, 20, 100, 30);
        btnJugar.setBounds(20, 60, 100, 30);
        btnHistorial.setBounds(20, 100, 100, 30);
        btnSalir.setBounds(20, 140, 100, 30);
        lblUsuario.setBounds(20, 270, 200, 25);

        // Textos a la derecha
        lblTitulo.setBounds(150, 20, 300, 25);
        txtInfo.setBounds(150, 60, 320, 150);

        añadirComponentes();
    }

    private void añadirComponentes() {
        frame.add(btnInicio); frame.add(btnJugar);
        frame.add(btnHistorial); frame.add(btnSalir);
        frame.add(lblTitulo); frame.add(txtInfo);
        frame.add(lblUsuario);
    }

    private void configurarEventos() {
        // Le damos vida a los botones
        btnJugar.addActionListener(e -> abrirJuego());
        btnSalir.addActionListener(e -> cerrarSesion());
        btnHistorial.addActionListener(e -> mostrarMensajeHistorial());
    }

    private void abrirJuego() {
        frame.dispose(); // Cierra el menú
        new VentanaJuego().mostrarVentana(); // Abre la ruleta gráfica
    }

    private void cerrarSesion() {
        frame.dispose(); // Cierra el menú
        new VentanaLogin().mostrarVentana(); // Vuelve a pedir contraseña
    }

    private void mostrarMensajeHistorial() {
        JOptionPane.showMessageDialog(frame, "Esta función se implementará más adelante.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}