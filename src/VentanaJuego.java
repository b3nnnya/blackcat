import javax.swing.*;
import java.awt.event.ActionEvent;

public class VentanaJuego {

    // --- Componentes UI ---
    private final JFrame frame = new JFrame("Ruleta - Casino Black Cat");

    private final JLabel lblTipo = new JLabel("Tipo de apuesta:");
    private final JComboBox<String> cbTipo = new JComboBox<>(new String[]{"Color", "Paridad"});

    private final JLabel lblSeleccion = new JLabel("Selección:");
    private final JComboBox<String> cbSeleccion = new JComboBox<>(new String[]{"Rojo", "Negro"});

    private final JLabel lblMonto = new JLabel("Monto:");
    private final JTextField txtMonto = new JTextField("100");

    private final JButton btnGirar = new JButton("Girar");
    private final JLabel lblSaldo = new JLabel("Saldo: $1000"); // Saldo inicial simulado
    private final JLabel lblResultado = new JLabel("¡Haz tu apuesta!");
    private final JButton btnVolver = new JButton("Volver al Menú");

    private int saldo = 1000; // Simulamos un saldo para el jugador

    public VentanaJuego() {
        configurarVentana();
        agregarPosiciones();
        configurarEventos();
    }

    private void configurarVentana() {
        frame.setSize(480, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setResizable(false);
    }

    private void agregarPosiciones() {
        lblTipo.setBounds(20, 20, 120, 25);
        cbTipo.setBounds(140, 20, 120, 25);
        lblSeleccion.setBounds(20, 60, 120, 25);
        cbSeleccion.setBounds(140, 60, 120, 25);

        lblMonto.setBounds(20, 100, 50, 25);
        txtMonto.setBounds(70, 100, 60, 25);
        btnGirar.setBounds(140, 100, 80, 25);
        lblSaldo.setBounds(230, 100, 150, 25);

        lblResultado.setBounds(20, 140, 430, 25);
        btnVolver.setBounds(310, 20, 130, 25);

        añadirAlFrame();
    }

    private void añadirAlFrame() {
        frame.add(lblTipo); frame.add(cbTipo);
        frame.add(lblSeleccion); frame.add(cbSeleccion);
        frame.add(lblMonto); frame.add(txtMonto); frame.add(btnGirar);
        frame.add(lblSaldo); frame.add(lblResultado); frame.add(btnVolver);
    }

    private void configurarEventos() {
        // Actualiza el segundo menú si cambias el primero
        cbTipo.addActionListener(e -> actualizarOpciones());

        // Ejecuta el giro al presionar el botón
        btnGirar.addActionListener(this::ejecutarGiro);

        // Vuelve al menú principal
        btnVolver.addActionListener(e -> {
            frame.dispose();
            // TODO: new VentanaMenu().mostrarVentana();
        });
    }

    private void actualizarOpciones() {
        cbSeleccion.removeAllItems(); // Limpia las opciones
        if (cbTipo.getSelectedItem().equals("Color")) {
            cbSeleccion.addItem("Rojo");
            cbSeleccion.addItem("Negro");
        } else {
            cbSeleccion.addItem("Par");
            cbSeleccion.addItem("Impar");
        }
    }

    // --- AQUÍ SE COMUNICA LA VISTA CON LA LÓGICA ---
    private void ejecutarGiro(ActionEvent e) {
        try {
            int monto = Integer.parseInt(txtMonto.getText());
            if (monto > saldo || monto <= 0) {
                JOptionPane.showMessageDialog(frame, "Monto inválido o saldo insuficiente.");
                return;
            }
            procesarApuesta(monto);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Ingrese un monto numérico válido.");
        }
    }

    private void procesarApuesta(int monto) {
        char tipo = obtenerCodigoApuesta();
        int numero = Ruleta.girarRuleta(); // Llama al motor lógico
        boolean gano = Ruleta.evaluarResultado(numero, tipo); // Evalúa en el motor

        Ruleta.registrarResultado(numero, monto, gano); // Guarda en el historial
        actualizarInterfaz(numero, tipo, monto, gano);
    }

    private char obtenerCodigoApuesta() {
        String sel = (String) cbSeleccion.getSelectedItem();
        if (sel.equals("Rojo")) return 'R';
        if (sel.equals("Negro")) return 'N';
        if (sel.equals("Par")) return 'P';
        return 'I'; // Si no es ninguno de los anteriores, es Impar
    }

    private void actualizarInterfaz(int numero, char tipo, int monto, boolean gano) {
        String colorNum = Ruleta.esRojo(numero) ? "(Rojo)" : (numero == 0 ? "(Verde)" : "(Negro)");

        if (gano) {
            saldo += monto;
        } else {
            saldo -= monto;
        }

        lblSaldo.setText("Saldo: $" + saldo);
        lblResultado.setText("Número: " + numero + " " + colorNum + " | Apuesta: " + tipo +
                " | Monto: $" + monto + " | " + (gano ? "¡GANASTE!" : "PERDISTE"));
    }

    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}