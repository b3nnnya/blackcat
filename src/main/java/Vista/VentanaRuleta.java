package Vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import Controlador.RuletaController;
import Controlador.ResultadoController;
import Modelo.Apuestas.ApuestaBase;
import Modelo.Apuestas.ApuestaImpar;
import Modelo.Apuestas.ApuestaNegro;
import Modelo.Apuestas.ApuestaPar;
import Modelo.Apuestas.ApuestaRojo;
import Modelo.Resultado;

public class VentanaRuleta {

    private final JFrame frame = new JFrame("Ruleta - Jugar");

    private final RuletaController ruletaCtrl;
    private final ResultadoController resultadoCtrl;

    private final JComboBox<String> cboTipo    = new JComboBox<>(new String[]{"Color", "Paridad"});
    private final JComboBox<String> cboColor   = new JComboBox<>(new String[]{"Rojo", "Negro"});
    private final JComboBox<String> cboParidad = new JComboBox<>(new String[]{"Par", "Impar"});
    private final SpinnerNumberModel modeloMonto = new SpinnerNumberModel(100, 1, 1_000_000, 50);
    private final JSpinner          spMonto    = new JSpinner(modeloMonto);
    private final JButton           btnGirar   = new JButton("Girar");
    private final JTextField        txtSaldo   = new JTextField();
    private final JTextArea         txtSalida  = new JTextArea(10, 60);

    public VentanaRuleta(RuletaController ruletaCtrl, ResultadoController resultadoCtrl) {
        this.ruletaCtrl     = ruletaCtrl;
        this.resultadoCtrl  = resultadoCtrl;

        construirUI();
        cablearEventos();
    }

    public void mostrar() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void construirUI() {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(720, 480);
        frame.setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(new EmptyBorder(16, 16, 8, 16));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 6, 6, 6);
        g.fill = GridBagConstraints.HORIZONTAL;

        addRow(form, g, 0, new JLabel("Tipo de apuesta:"), cboTipo);
        addRow(form, g, 1, new JLabel("Seleccione color:"), cboColor);
        addRow(form, g, 2, new JLabel("Seleccione paridad:"), cboParidad);

        g.gridx = 0; g.gridy = 3; g.anchor = GridBagConstraints.LINE_END;
        form.add(new JLabel("Monto:"), g);

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        txtSaldo.setEditable(false);
        txtSaldo.setColumns(12);
        acciones.add(spMonto);
        acciones.add(btnGirar);
        acciones.add(txtSaldo);

        g.gridx = 1; g.anchor = GridBagConstraints.LINE_START;
        form.add(acciones, g);

        txtSalida.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtSalida);
        scroll.setBorder(new EmptyBorder(0, 16, 16, 16));

        frame.add(form, BorderLayout.NORTH);
        frame.add(scroll, BorderLayout.CENTER);

        actualizarControles();
        actualizarSaldoUI();
    }

    private void addRow(JPanel parent, GridBagConstraints g, int row, JComponent label, JComponent field) {
        g.gridx = 0; g.gridy = row; g.anchor = GridBagConstraints.LINE_END;
        parent.add(label, g);
        g.gridx = 1; g.anchor = GridBagConstraints.LINE_START;
        parent.add(field, g);
    }

    private void cablearEventos() {
        cboTipo.addActionListener(e -> actualizarControles());
        btnGirar.addActionListener(e -> onGirar());
    }

    private void actualizarControles() {
        boolean esColor   = "Color".equals(cboTipo.getSelectedItem());
        boolean esParidad = "Paridad".equals(cboTipo.getSelectedItem());
        cboColor.setEnabled(esColor);
        cboParidad.setEnabled(esParidad);
    }

    private void onGirar() {
        try {
            ApuestaBase apuesta = crearApuesta();
            Resultado r = ruletaCtrl.jugar(apuesta);

            txtSalida.append(String.format(
                    "Número %d (%s) | Apuesta=%s | Monto=$%d | %s | Saldo=%d%n",
                    r.getNumero(),
                    r.getColor(),
                    r.getApuesta().getEtiqueta(),
                    r.getApuesta().getMonto(),
                    r.getAcierto() ? "GANASTE" : "PERDISTE",
                    r.getSaldoPosterior()
            ));
            actualizarSaldoUI();

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Sesión", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarSaldoUI() {
        int saldo = ruletaCtrl.getSaldo();
        txtSaldo.setText("Saldo: " + saldo);

        int maximo = Math.max(saldo, 0);
        modeloMonto.setMaximum(maximo);
        if (modeloMonto.getNumber().intValue() > maximo) {
            modeloMonto.setValue(maximo);
        }
        btnGirar.setEnabled(saldo > 0);
    }

    private ApuestaBase crearApuesta() {
        boolean esColor = "Color".equals(cboTipo.getSelectedItem());
        int monto = modeloMonto.getNumber().intValue();
        if (esColor) {
            return "Rojo".equals(cboColor.getSelectedItem()) ? new ApuestaRojo(monto) : new ApuestaNegro(monto);
        }
        return "Par".equals(cboParidad.getSelectedItem()) ? new ApuestaPar(monto) : new ApuestaImpar(monto);
    }
}
