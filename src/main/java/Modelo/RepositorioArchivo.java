package Modelo;

import Modelo.Apuestas.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class RepositorioArchivo implements IRepositorioResultados {

    private final String rutaArchivo;

    /**
     * @param rutaArchivo ruta del archivo CSV donde se persiste el historial
     */
    public RepositorioArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    @Override
    public void guardarResultado(Resultado resultado) {
        if (resultado == null) throw new IllegalArgumentException("Resultado no puede ser null");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo, true))) {
            String linea = String.format("%d,%s,%b,%d,%s,%d",
                    resultado.getNumero(),
                    resultado.getColor(),
                    resultado.getAcierto(),
                    resultado.getSaldoPosterior(),
                    resultado.getApuesta().getEtiqueta(),
                    resultado.getApuesta().getMonto()
            );
            writer.write(linea);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error al guardar resultado en archivo: " + e.getMessage());
        }
    }

    @Override
    public List<Resultado> obtenerHistorial() {
        List<Resultado> historial = new ArrayList<>();
        File archivo = new File(rutaArchivo);

        if (!archivo.exists()) {
            return Collections.unmodifiableList(historial);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;

                String[] campos = linea.split(",");
                if (campos.length < 6) continue;

                int numero          = Integer.parseInt(campos[0]);
                String color        = campos[1];
                boolean acierto     = Boolean.parseBoolean(campos[2]);
                int saldoPosterior  = Integer.parseInt(campos[3]);
                String tipoApuesta  = campos[4];
                int montoApuesta    = Integer.parseInt(campos[5]);

                ApuestaBase apuesta = crearApuestaDesdeTipo(tipoApuesta, montoApuesta);
                Resultado resultado = new Resultado(numero, color, acierto, saldoPosterior, apuesta);
                historial.add(resultado);
            }
        } catch (IOException e) {
            System.err.println("Error al leer historial desde archivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error de formato en archivo de historial: " + e.getMessage());
        }

        return Collections.unmodifiableList(historial);
    }

    /**
     * Recrea el objeto ApuestaBase a partir de la etiqueta guardada en el CSV.
     */
    private ApuestaBase crearApuestaDesdeTipo(String tipo, int monto) {
        return switch (tipo.toUpperCase()) {
            case "ROJO"  -> new ApuestaRojo(monto);
            case "NEGRO" -> new ApuestaNegro(monto);
            case "PAR"   -> new ApuestaPar(monto);
            case "IMPAR" -> new ApuestaImpar(monto);
            default      -> throw new IllegalArgumentException("Tipo de apuesta desconocido: " + tipo);
        };
    }
}
