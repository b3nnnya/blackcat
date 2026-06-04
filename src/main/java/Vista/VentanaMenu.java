package Vista;

import javax.swing.*;
import java.awt.*;
import Controlador.SessionController;
import Controlador.ResultadoController;
import Controlador.RuletaController;
import Modelo.IRepositorioResultados;
import Modelo.Ruleta;

public class VentanaMenu {
    private static final String TITULO_APP = "Casino Black Cat - Menú";
    private static final String VISTA_INICIO = "INICIO";

    private final SessionController   session;
    private final ResultadoController resultadoController;
    private final RuletaController    ruletaController;

    private final String  nombreUsuario;
    private final JFrame  frame             = new JFrame(TITULO_APP);
    private final JPanel  panelCards        = new JPanel(new CardLayout());
    private final JButton btnInicio         = new JButton("Inicio");
    private final JButton btnJugar          = new JButton("Jugar");
    private final JButton btnHistorial      = new JButton("Historial");
    private final JButton btnEstadisticas   = new JButton("Estadísticas");
    private final JButton btnSalir          = new JButton("Salir");
    private final SpinnerNumberModel modeloRecarga = new SpinnerNumberModel(1000, 1, 1_000_000, 100);
    private final JSpinner spRecarga        = new JSpinner(modeloRecarga);
    private final JButton btnAgregarSaldo   = new JButton("Agregar saldo");
    private final JLabel  lblSaldoInicio    = new JLabel();

    public VentanaMenu(SessionController session) {
        this.session                = session;
        IRepositorioResultados repo = session.getRepositorio();
        this.resultadoController    = new ResultadoController(session);          
        this.ruletaController       = new RuletaController(new Ruleta(1000, repo), session);
        this.nombreUsuario          = session.getNombreUsuario();

        configurarFrame();
        sidebar();
        construirVistaInicio();
        configurarEventos();
        mostrarVista(VISTA_INICIO);
    }

    public void mostrar() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void configurarFrame() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(840, 520);
        frame.setLayout(new BorderLayout());
        frame.add(panelCards, BorderLayout.CENTER);
    }

    private void sidebar() {
        JPanel side = new JPanel();
        side.setLayout(new BoxLayout(side, BoxLayout.Y_AXIS));
        side.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 16));
        JLabel lblUser = new JLabel(nombreUsuario);

        for (JButton b : new JButton[]{btnInicio, btnJugar, btnHistorial, btnEstadisticas, btnSalir}) {
            b.setAlignmentX(Component.LEFT_ALIGNMENT);
            b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
            side.add(b);
            side.add(Box.createVerticalStrut(8));
        }
        side.add(Box.createVerticalGlue());
        side.add(lblUser);

        frame.add(side, BorderLayout.WEST);
    }

    private void construirVistaInicio() {
        JPanel p = new JPanel(new BorderLayout(12, 12));
        p.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        JLabel titulo = new JLabel("RULETA — Casino Black Cat", SwingConstants.LEFT);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 22f));

        JTextArea info = new JTextArea("""
                Bienvenido/a al menú principal.
                • Jugar: abre la ventana de juego.
                • Historial: muestra tus jugadas.
                • Salir: cierra sesión y vuelve al login.
                """);
        info.setEditable(false);
        info.setLineWrap(true);
        info.setWrapStyleWord(true);

        JPanel recarga = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        recarga.add(new JLabel("Recargar saldo:"));
        recarga.add(spRecarga);
        recarga.add(btnAgregarSaldo);
        recarga.add(lblSaldoInicio);

        p.add(titulo, BorderLayout.NORTH);
        p.add(new JScrollPane(info), BorderLayout.CENTER);
        p.add(recarga, BorderLayout.SOUTH);

        panelCards.add(p, VISTA_INICIO);
        actualizarSaldoInicio();
    }

    private void configurarEventos() {
        btnInicio.addActionListener(e -> mostrarVista(VISTA_INICIO));
        btnJugar.addActionListener(e -> new VentanaRuleta(ruletaController, resultadoController).mostrar());
        btnHistorial.addActionListener(e -> new VentanaHistorial(resultadoController).mostrar());
        btnEstadisticas.addActionListener(e -> new VentanaEstadisticas(resultadoController, session.getRepositorio()).mostrar());

        btnAgregarSaldo.addActionListener(e -> onAgregarSaldo());
        btnSalir.addActionListener(e -> cerrarSesion());
    }

    private void onAgregarSaldo() {
        try {
            int monto = modeloRecarga.getNumber().intValue();
            ruletaController.depositar(monto);
            actualizarSaldoInicio();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void actualizarSaldoInicio() {
        lblSaldoInicio.setText("Saldo: " + ruletaController.getSaldo());
    }

    private void mostrarVista(String nombre) {
        ((CardLayout) panelCards.getLayout()).show(panelCards, nombre);
    }

    private void cerrarSesion() {
        frame.dispose();
        new VentanaLogin(session).mostrarVentana();
    }
}
