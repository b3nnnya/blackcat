import java.util.Random;
import java.util.Scanner;

public class Ruleta {

    // Constantes y variables globales (Arreglos unidimensionales)
    public static final int MAX_HISTORIAL = 100;
    public static int[] historialNumeros = new int[MAX_HISTORIAL];
    public static int[] historialApuestas = new int[MAX_HISTORIAL];
    public static boolean[] historialAciertos = new boolean[MAX_HISTORIAL];
    public static int historialSize = 0;

    public static Random rng = new Random();
    public static int[] numerosRojos = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36};

  /**
     * Controla el flujo principal del programa mostrando un menú en consola.
     */
    public static void menu() {
        Scanner in = new Scanner(System.in);
        int opcion;
        do {
            mostrarMenu();
            opcion = leerOpcion(in);
            ejecutarOpcion(opcion, in);
        } while (opcion != 3);
        in.close();
    }

    /**
     * Muestra en consola las opciones disponibles del menú.
     */
    public static void mostrarMenu() {
        System.out.println("\n=== CASINO BLACK CAT: RULETA ===");
        System.out.println("1. Iniciar una ronda");
        System.out.println("2. Ver estadísticas");
        System.out.println("3. Salir");
        System.out.print("Elija una opción: ");
    }

    /**
     * Lee la opción elegida por el usuario desde teclado.
     */
    public static int leerOpcion(Scanner in) {
        return in.nextInt();
    }

    /**
     * Ejecuta la acción correspondiente a la opción del menú.
     */
    public static void ejecutarOpcion(int opcion, Scanner in) {
        if (opcion == 1) {
            iniciarRonda(in);
        } else if (opcion == 2) {
            mostrarEstadisticas();
        } else if (opcion == 3) {
            System.out.println("¡Gracias por jugar en Casino Black Cat!");
        } else {
            System.out.println("Opción no válida. Intente de nuevo.");
        }
    }

    /**
     * Inicia una ronda de la ruleta: leer apuesta, girar, evaluar y mostrar resultado.
     */
    public static void iniciarRonda(Scanner in) {
        if (historialSize >= MAX_HISTORIAL) {
            System.out.println("Se alcanzó el límite del historial.");
            return;
        }
        char tipo = leerTipoApuesta(in);
        System.out.print("Ingrese monto a apostar: $");
        int monto = in.nextInt();

        int numero = girarRuleta();
        boolean acierto = evaluarResultado(numero, tipo);

        registrarResultado(numero, monto, acierto);
        mostrarResultado(numero, tipo, monto, acierto);
    }

    /**
     * Permite al usuario seleccionar el tipo de apuesta (R/N/P/I).
     */
    public static char leerTipoApuesta(Scanner in) {
        System.out.print("Tipo de apuesta (R=Rojo, N=Negro, P=Par, I=Impar): ");
        // Lee el primer caracter ingresado y lo convierte a mayúscula
        return in.next().toUpperCase().charAt(0);
    }

    /**
     * Simula el giro de la ruleta generando un número aleatorio de 0 a 36.
     */
    public static int girarRuleta() {
        return rng.nextInt(37); // Genera de 0 a 36 inclusive
    }

    /**
     * Evalúa si la apuesta realizada por el jugador fue acertada.
     */
    public static boolean evaluarResultado(int numero, char tipo) {
        if (numero == 0) return false; // El 0 hace perder estas apuestas básicas

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
        for (int i = 0; i < numerosRojos.length; i++) {
            if (numerosRojos[i] == n) {
                return true;
            }
        }
        return false;
    }

    /**
     * Registra los resultados de la ronda en los arreglos de historial.
     */
    public static void registrarResultado(int numero, int apuesta, boolean acierto) {
        historialNumeros[historialSize] = numero;
        historialApuestas[historialSize] = apuesta;
        historialAciertos[historialSize] = acierto;
        historialSize++;
    }

    /**
     * Muestra en consola el resultado de la ronda.
     */
    public static void mostrarResultado(int numero, char tipo, int monto, boolean acierto) {
        System.out.println("\n--- RESULTADO ---");
        System.out.println("Número ganador: " + numero + (esRojo(numero) ? " (Rojo)" : (numero == 0 ? " (Verde)" : " (Negro)")));
        if (acierto) {
            System.out.println("¡Felicidades! Ganaste $" + monto);
        } else {
            System.out.println("Lo siento, perdiste $" + monto);
        }
    }

    /**
     * Muestra estadísticas generales de todas las rondas jugadas.
     */
    public static void mostrarEstadisticas() {
        if (historialSize == 0) {
            System.out.println("No hay rondas jugadas aún.");
            return;
        }

        int totalApostado = 0;
        int totalAciertos = 0;
        int gananciaNeta = 0;

        for (int i = 0; i < historialSize; i++) {
            totalApostado += historialApuestas[i];
            if (historialAciertos[i]) {
                totalAciertos++;
                gananciaNeta += historialApuestas[i]; // Gana lo apostado (1 a 1)
            } else {
                gananciaNeta -= historialApuestas[i]; // Pierde lo apostado
            }
        }

        double porcentajeAcierto = ((double) totalAciertos / historialSize) * 100;

        System.out.println("\n=== ESTADÍSTICAS ===");
        System.out.println("Rondas jugadas: " + historialSize);
        System.out.println("Total apostado: $" + totalApostado);
        System.out.println("Total de aciertos: " + totalAciertos);
        System.out.println("% de acierto: " + String.format("%.2f", porcentajeAcierto) + "%");
        System.out.println("Ganancia/Pérdida neta: $" + gananciaNeta);
    }
}