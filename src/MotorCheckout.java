/** Ejercicio 6. Motor sin estado que depende de capacidades, no de proveedores. */
public class MotorCheckout {
    public MotorCheckout() { }

    // Sobrecarga: mismo nombre, diferentes contratos y parametros.
    public boolean procesar(Transaccional servicio, double monto) {
        return servicio != null && servicio.cobrar(monto);
    }

    public String procesar(Documentable servicio, String referencia) {
        return servicio == null ? "" : servicio.generarDocumento(referencia);
    }

    public String solicitarReintegro(IntegracionAPI api, double monto) {
        if (api == null) {
            return "No se encontro la API solicitada.";
        }
        if (!(api instanceof Transaccional)) {
            return "OMITIDO: la API no admite reintegros";
        }
        // La conversion es segura porque primero se verifica la interfaz.
        Transaccional servicio = (Transaccional) api;
        if (servicio.reintegrar(monto)) {
            return "EXITO: reintegro simulado de " + monto + " en " + api.getId() + ".";
        }
        return "RECHAZADO: reintegro no realizado; revise el monto y la disponibilidad de la API.";
    }

    public void auditar(Auditable servicio, String evento) {
        if (servicio != null) {
            servicio.registrarEvento(evento);
        }
    }
}
