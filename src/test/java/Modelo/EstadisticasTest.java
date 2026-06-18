package Modelo;

import Modelo.Apuestas.ApuestaNegro;
import Modelo.Apuestas.ApuestaRojo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Prueba del cálculo de estadísticas sobre un historial mixto (con y sin
 * aciertos y con una apuesta nula). Cubre el caso 5 del Cuadro 1.
 */
class EstadisticasTest {

    @Test
    @DisplayName("Caso 5: calcula total, victorias, racha y tipo más jugado ignorando apuestas null")
    void calculaMetricasIgnorandoApuestasNull() {
        RepositorioEnMemoria repo = new RepositorioEnMemoria();
        // Historial: V(ROJO), V(ROJO), D(NEGRO), V(null), V(ROJO)
        repo.guardarResultado(new Resultado(1, "ROJO",  true,  110, new ApuestaRojo(10)));
        repo.guardarResultado(new Resultado(3, "ROJO",  true,  120, new ApuestaRojo(10)));
        repo.guardarResultado(new Resultado(2, "NEGRO", false, 110, new ApuestaNegro(10)));
        repo.guardarResultado(new Resultado(5, "ROJO",  true,  120, null)); // apuesta null -> ignorada en "tipo"
        repo.guardarResultado(new Resultado(7, "ROJO",  true,  130, new ApuestaRojo(10)));

        Estadisticas est = new Estadisticas(repo);

        assertEquals(5, est.getTotalJugadas(), "total de jugadas");
        assertEquals(4, est.getVictorias(), "victorias");
        assertEquals(2, est.getRachaMaxima(), "racha máxima de victorias consecutivas");
        assertEquals(80.0, est.getPorcentajeVictorias(), 1e-9, "porcentaje de victorias");
        assertEquals("ROJO", est.getTipoMasJugado(), "tipo más jugado (la apuesta null no debe contar)");
    }
}
