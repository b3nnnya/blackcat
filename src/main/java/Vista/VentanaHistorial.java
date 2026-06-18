package Vista;

import Controlador.ResultadoController;
import Modelo.Resultado;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VentanaHistorial {
    private final ResultadoController resultadoController;
    private final JFrame    frame           = new JFrame("Historial");
    private final JTextArea txtHistorial    = new JTextArea(16, 60);
    private final JButton   btnActualizar   = new JButton("Actualizar");

    public VentanaHistorial(ResultadoController resultadoController) {
        this.resultadoController = resultadoController;
        construirUI();
        cablearEventos();
        cargar();
    }

    public void mostrar() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void construirUI() {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(700, 420);
        frame.setLayout(new BorderLayout(8, 8));

        txtHistorial.setEditable(false);
        txtHistorial.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        JPanel norte = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        norte.add(btnActualizar);

        frame.add(norte, BorderLayout.NORTH);
        frame.add(new JScrollPane(txtHistorial), BorderLayout.CENTER);
    }

    private void cablearEventos() {
        btnActualizar.addActionListener(e -> cargar());
    }

    private void cargar() {
        try {
            List<Resultado> historial = resultadoController.getHistorial();
            txtHistorial.setText("");
            if (historial.isEmpty()) {
                txtHistorial.append("No hay jugadas registradas.\n");
                return;
            }
            for (int i = 0; i < historial.size(); i++) {
                Resultado r = historial.get(i);
                txtHistorial.append(
                    String.format(
                        "%02d) Número=%d  Color=%s  Tipo=%s  Resultado=%s  Saldo=%d%n",
                        i + 1,
                        r.getNumero(),
                        r.getColor(),
                        r.getApuesta().getEtiqueta(),
                        r.getAcierto() ? "GANÓ" : "PERDIÓ",
                        r.getSaldoPosterior()
                    )
                );
            }
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Sesión", JOptionPane.ERROR_MESSAGE);
            txtHistorial.setText("No hay usuario autenticado.\n");
        }
    }
}