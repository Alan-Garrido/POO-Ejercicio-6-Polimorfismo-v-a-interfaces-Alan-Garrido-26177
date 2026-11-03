/** Ejercicio 6. Adaptador simulado de tarjetas, transferencias o billeteras. */
public class PagoAPI extends IntegracionAPI implements Transaccional {
    public PagoAPI(String id, String nombre, String endpoint) {
        super(id, nombre, endpoint);
    }

    @Override
    public String consultarEstado() {
        return "Pagos - " + getNombre() + ": "
                + (isActiva() && isConectada() ? "disponible para cobros y reintegros simulados."
                        : "no disponible para operaciones financieras.")
                + " Activa: " + isActiva() + "; conectada: " + isConectada();
    }

    @Override
    public boolean cobrar(double monto) {
        return isActiva() && isConectada() && Double.isFinite(monto) && monto > 0;
    }

    @Override
    public boolean reintegrar(double monto) {
        return isActiva() && isConectada() && Double.isFinite(monto) && monto > 0;
    }
}
