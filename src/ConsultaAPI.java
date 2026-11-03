/** Ejercicio 6. Servicio de consulta sin capacidades financieras ni documentales. */
public class ConsultaAPI extends IntegracionAPI {
    public ConsultaAPI(String id, String nombre, String endpoint) {
        super(id, nombre, endpoint);
    }

    @Override
    public String consultarEstado() {
        return "Rastreo - " + getNombre() + ": "
                + (isActiva() && isConectada() ? "paquete en transito (simulacion)."
                        : "no disponible para consultar el paquete.")
                + " Activa: " + isActiva() + "; conectada: " + isConectada();
    }
}
