package Modelo;

import java.util.List;


public interface IRepositorioResultados {

    /**
     * Guarda un resultado en el repositorio.
     * @param resultado el resultado a guardar
     */
    void guardarResultado(Resultado resultado);

    /**
     * Obtiene la lista completa del historial de resultados.
     * @return lista de resultados almacenados
     */
    List<Resultado> obtenerHistorial();
}
