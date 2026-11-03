/** Ejercicio 6. Coordina solicitudes y devuelve resultados sin entrada ni salida. */
public class Controlador {
    private final CatalogoAPI catalogo;
    private final MotorCheckout motor;

    public Controlador(CatalogoAPI catalogo, MotorCheckout motor) {
        if (catalogo == null || motor == null) {
            throw new IllegalArgumentException("El catalogo y el motor son obligatorios.");
        }
        this.catalogo = catalogo;
        this.motor = motor;
    }

    public void iniciar() { catalogo.cargarIniciales(); }
    public String listarIntegraciones() { return catalogo.listarActivas(); }

    public String buscarConfiguracion(String id) {
        IntegracionAPI api = catalogo.buscarPorId(id);
        if (api == null) {
            return "No se encontro la API solicitada.";
        }
        return "ID: " + api.getId() + "\nNombre: " + api.getNombre()
                + "\nEndpoint: " + api.getEndpoint() + "\nActiva: " + api.isActiva()
                + "\nConectada: " + api.isConectada()
                + "\nLatencia promedio: " + api.getLatenciaPromedio() + " ms"
                + "\nUltimo evento: " + api.getUltimoEvento();
    }

    public String ordenarIntegraciones() {
        catalogo.ordenarPorLatencia();
        return "Integraciones activas ordenadas de menor a mayor latencia:\n" + catalogo.listarActivas();
    }

    public String consultarAPI(String id) {
        IntegracionAPI api = catalogo.buscarPorId(id);
        if (api == null) {
            return "No se encontro la API solicitada.";
        }
        String resultado = api.consultarEstado();
        motor.auditar(api, resultado);
        return resultado;
    }

    public String cobrar(String id, double monto) {
        IntegracionAPI api = catalogo.buscarPorId(id);
        if (api == null) {
            return "No se encontro la API solicitada.";
        }
        String resultado;
        if (!(api instanceof Transaccional)) {
            resultado = "OMITIDO: la API no admite cobros.";
        } else if (motor.procesar((Transaccional) api, monto)) {
            resultado = "EXITO: cobro simulado de " + monto + " en " + api.getId() + ".";
        } else {
            resultado = "RECHAZADO: cobro no realizado; revise el monto y la disponibilidad de la API.";
        }
        motor.auditar(api, resultado);
        return resultado;
    }

    public String reintegrar(String id, double monto) {
        IntegracionAPI api = catalogo.buscarPorId(id);
        String resultado = motor.solicitarReintegro(api, monto);
        motor.auditar(api, resultado);
        return resultado;
    }

    public String generarDocumento(String id, String referencia) {
        IntegracionAPI api = catalogo.buscarPorId(id);
        if (api == null) {
            return "No se encontro la API solicitada.";
        }
        String resultado;
        if (!(api instanceof Documentable)) {
            resultado = "OMITIDO: la API no admite documentos.";
        } else {
            String documento = motor.procesar((Documentable) api, referencia);
            if (documento.isEmpty()) {
                resultado = "RECHAZADO: documento no generado; revise la referencia y la disponibilidad de la API.";
            } else {
                resultado = "EXITO: documento simulado generado por " + api.getId() + ".\n" + documento;
            }
        }
        motor.auditar(api, resultado);
        return resultado;
    }
}
