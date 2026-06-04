package Modelo;

import Modelo.Apuestas.ApuestaBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Estadisticas {
    private int     totalJugadas        = 0;
    private int     victorias           = 0;
    private double  porcentajeVictorias = 0.0;
    private int     rachaMaxima         = 0;
    private String  tipoMasJugado       = null;


    public Estadisticas(IRepositorioResultados repositorio) {
        List<Resultado> historial = (repositorio != null) ? repositorio.obtenerHistorial() : List.of();
        this.totalJugadas           = historial.size();
        this.victorias              = countVictorias(historial);
        this.rachaMaxima            = countRachaMaxima(historial);
        this.porcentajeVictorias    = (this.totalJugadas == 0) ? 0.0 : (this.victorias * 100.0) / this.totalJugadas;
        this.tipoMasJugado          = countTipoMasJugado(historial);
    }

    private int countVictorias(List<Resultado> historial) {
        int wins = 0;
        for (Resultado r : historial) {
            if (r.getAcierto()) wins++;
        }
        return wins;
    }

    private int countRachaMaxima(List<Resultado> historial) {
        int max     = 0;
        int current = 0;
        for (Resultado r : historial) {
            if (r.getAcierto()) {
                current++;
                if (current > max) max = current;
            } else {
                current = 0;
            }
        }
        return max;
    }

    private String countTipoMasJugado(List<Resultado> historial) {
        Map<String, Integer> contador = new HashMap<>();
        for (Resultado r : historial) {
            ApuestaBase apuesta = r.getApuesta();
            if (apuesta == null) continue;
            contador.merge(apuesta.getEtiqueta(), 1, Integer::sum);
        }

        String bestLabel = null;
        int bestCount = 0;
        for (Map.Entry<String, Integer> entry : contador.entrySet()) {
            if (entry.getValue() > bestCount) {
                bestLabel = entry.getKey();
                bestCount = entry.getValue();
            }
        }

        return (bestCount == 0) ? null : bestLabel;
    }

    public int getTotalJugadas() { return totalJugadas; }
    public int getVictorias() { return victorias; }
    public double getPorcentajeVictorias() { return porcentajeVictorias; }
    public int getRachaMaxima() { return rachaMaxima; }
    public String getTipoMasJugado() { return tipoMasJugado; }
}