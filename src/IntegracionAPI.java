import java.util.Locale;

/** Ejercicio 6. Modelo abstracto con configuracion, disponibilidad y auditoria. */
public abstract class IntegracionAPI implements Auditable, Comparable<IntegracionAPI> {
    private final String id;
    private final String nombre;
    private final String endpoint;
    private boolean activa;
    private boolean conectada;
    private double latenciaPromedio;
    private String ultimoEvento;

    protected IntegracionAPI(String id, String nombre, String endpoint) {
        if (id == null || id.trim().isEmpty()
                || nombre == null || nombre.trim().isEmpty()
                || endpoint == null || endpoint.trim().isEmpty()) {
            throw new IllegalArgumentException("Id, nombre y endpoint son obligatorios.");
        }
        this.id = id.trim().toUpperCase(Locale.ROOT);
        this.nombre = nombre.trim();
        this.endpoint = endpoint.trim();
        activa = true;
        conectada = true;
        latenciaPromedio = 0.0;
        ultimoEvento = "Sin eventos";
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEndpoint() { return endpoint; }
    public boolean isActiva() { return activa; }
    public boolean isConectada() { return conectada; }
    public double getLatenciaPromedio() { return latenciaPromedio; }
    public String getUltimoEvento() { return ultimoEvento; }

    public void setActiva(boolean activa) { this.activa = activa; }
    public void setConectada(boolean conectada) { this.conectada = conectada; }

    /** Un valor invalido no modifica la medicion anterior. */
    public boolean setLatenciaPromedio(double latenciaPromedio) {
        if (!Double.isFinite(latenciaPromedio) || latenciaPromedio < 0) {
            return false;
        }
        this.latenciaPromedio = latenciaPromedio;
        return true;
    }

    @Override
    public void registrarEvento(String evento) {
        if (evento != null && !evento.trim().isEmpty()) {
            ultimoEvento = evento.trim();
        }
    }

    public abstract String consultarEstado();

    /** El orden representa rendimiento; equals representa identidad por id. */
    @Override
    public int compareTo(IntegracionAPI otra) {
        int comparacion = Double.compare(latenciaPromedio, otra.latenciaPromedio);
        if (comparacion == 0) {
            comparacion = id.compareTo(otra.id);
        }
        return comparacion;
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }
        if (!(objeto instanceof IntegracionAPI)) {
            return false;
        }
        IntegracionAPI otra = (IntegracionAPI) objeto;
        return id.equals(otra.id);
    }

    @Override
    public int hashCode() { return id.hashCode(); }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "%s | %s | %s | %.2f ms", id, nombre,
                conectada ? "Conectada" : "Desconectada", latenciaPromedio);
    }
}
