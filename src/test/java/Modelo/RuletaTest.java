package Modelo;

import Modelo.Apuestas.ApuestaRojo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Pruebas de la lógica central de la ruleta.
 * Cubre los casos 1, 2, 3 y 4 del Cuadro 1 (Iteración 09).
 */
class RuletaTest {

    private IRepositorioResultados repo() {
        return new RepositorioEnMemoria();
    }

    @Test
    @DisplayName("Caso 1: el constructor rechaza un saldo inicial negativo")
    void constructorRechazaSaldoInicialNegativo() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> new Ruleta(-1, repo()));
        assertEquals("Saldo inicial inválido", ex.getMessage());
    }

    @Test
    @DisplayName("Caso 2: un depósito válido incrementa el saldo")
    void depositoValidoIncrementaSaldo() {
        Ruleta ruleta = new Ruleta(100, repo());
        ruleta.depositar(50);
        assertEquals(150, ruleta.getSaldo());
    }

    @Test
    @DisplayName("Caso 3: jugar con una apuesta nula es rechazado")
    void apuestaNulaEsRechazada() {
        Ruleta ruleta = new Ruleta(100, repo());
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> ruleta.jugar(null));
        assertEquals("Apuesta requerida", ex.getMessage());
    }

    @Test
    @DisplayName("Caso 4: no se puede apostar un monto mayor al saldo")
    void apuestaConMontoMayorAlSaldoEsRechazada() {
        Ruleta ruleta = new Ruleta(100, repo());
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> ruleta.jugar(new ApuestaRojo(200)));
        assertEquals("Saldo insuficiente", ex.getMessage());
    }
}
