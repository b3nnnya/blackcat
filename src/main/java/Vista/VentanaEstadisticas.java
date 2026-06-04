package Vista;

import Controlador.ResultadoController;
import Modelo.Estadisticas;
import Modelo.IRepositorioResultados;
import Modelo.Resultado;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VentanaEstadisticas {
    private final ResultadoController resultadoController;
    private final IRepositorioResultados repositorio;
    private final JFrame    frame           = new JFrame("Estadísticas");
    private final JTextArea txtEstadisticas = new JTextArea(12, 50);
    private final JButton   btnActualizar   = new JButton("Actualizar");

    public VentanaEstadisticas(ResultadoController resultadoController, IRepositorioResultados repositorio) {
        this.resultadoController = resultadoController;
        this.repositorio = repositorio;
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
        frame.setSize(600, 340);
        frame.setLayout(new BorderLayout(8, 8));

        txtEstadisticas.setEditable(false);
        txtEstadisticas.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        JPanel norte = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        norte.add(btnActualizar);

        frame.add(norte, BorderLayout.NORTH);
        frame.add(new JScrollPane(txtEstadisticas), BorderLayout.CENTER);
    }

    private void cablearEventos() {
        btnActualizar.addActionListener(e -> cargar());
    }

    private void cargar() {
        try {
            Estadisticas est = new Estadisticas(repositorio);

            String tipoMas = (est.getTipoMasJugado() == null) ? "-" : est.getTipoMasJugado();

            txtEstadisticas.setText("");
            if (est.getTotalJugadas() == 0) {
                txtEstadisticas.append("No hay jugadas registradas.\n");
                return;
            }

            txtEstadisticas.append(String.format("Total jugadas         : %d\n", est.getTotalJugadas()));
            txtEstadisticas.append(String.format("Victorias             : %d\n", est.getVictorias()));
            txtEstadisticas.append(String.format("Porcentaje victorias  : %.2f%%\n", est.getPorcentajeVictorias()));
            txtEstadisticas.append(String.format("Racha máxima          : %d\n", est.getRachaMaxima()));
            txtEstadisticas.append(String.format("Tipo más jugado       : %s\n", tipoMas));

        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Sesión", JOptionPane.ERROR_MESSAGE);
            txtEstadisticas.setText("No hay usuario autenticado.\n");
        }
    }
}