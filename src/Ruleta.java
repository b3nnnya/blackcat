import java.util.Random;

/**
 * Motor lógico del juego de Ruleta
 */
public class Ruleta {

    // --- Estructuras de Datos ---
    public static final int MAX_HISTORIAL = 100;
    public static int[] historialNumeros = new int[MAX_HISTORIAL];
    public static int[] historialApuestas = new int[MAX_HISTORIAL];
    public static boolean[] historialAciertos = new boolean[MAX_HISTORIAL];
    public static int historialSize = 0;

    // Constantes (Aplicando uso de final)
    public static final Random RNG = new Random();
    public static final int[] NUMEROS_ROJOS = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36};

    // --- LÓGICA PRINCIPAL DEL JUEGO ---

    /**
     * Simula el giro de la ruleta.
     */
    public static int girarRuleta() {
        return RNG.nextInt(37); // Genera de 0 a 36 inclusive
    }

    /**
     * Evalúa si la apuesta realizada fue acertada.
     */
    public static boolean evaluarResultado(int numero, char tipo) {
        if (numero == 0) return false;

        if (tipo == 'R') return esRojo(numero);
        if (tipo == 'N') return !esRojo(numero);
        if (tipo == 'P') return numero % 2 == 0;
        if (tipo == 'I') return numero % 2 != 0;

        return false;
    }

    /**
     * Determina si un número corresponde a color rojo.
     */
    public static boolean esRojo(int n) {
        for (int i = 0; i < NUMEROS_ROJOS.length; i++) {
            if (NUMEROS_ROJOS[i] == n) {
                return true;
            }
        }
        return false;
    }

    /**
     * Registra los resultados de la ronda en el historial.
     */
    public static void registrarResultado(int numero, int apuesta, boolean acierto) {
        if (historialSize >= MAX_HISTORIAL) return; // Evita error si se llena

        historialNumeros[historialSize] = numero;
        historialApuestas[historialSize] = apuesta;
        historialAciertos[historialSize] = acierto;
        historialSize++;
    }

    // --- MÉTODOS PARA OBTENER ESTADÍSTICAS (Sin imprimirlas) ---

    public static int getRondasJugadas() {
        return historialSize;
    }

    public static int getTotalApostado() {
        int total = 0;
        for (int i = 0; i < historialSize; i++) {
            total += historialApuestas[i];
        }
        return total;
    }

    public static int getGananciasNetas() {
        int ganancia = 0;
        for (int i = 0; i < historialSize; i++) {
            if (historialAciertos[i]) {
                ganancia += historialApuestas[i]; // Gana
            } else {
                ganancia -= historialApuestas[i]; // Pierde
            }
        }
        return ganancia;
    }
}