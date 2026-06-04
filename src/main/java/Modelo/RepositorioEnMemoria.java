package Modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class RepositorioEnMemoria implements IRepositorioResultados {

    private final List<Resultado> historial = new ArrayList<>();

    @Override
    public void guardarResultado(Resultado resultado) {
        if (resultado == null) throw new IllegalArgumentException("Resultado no puede ser null");
        historial.add(resultado);
    }

    @Override
    public List<Resultado> obtenerHistorial() {
        return Collections.unmodifiableList(historial);
    }
}
