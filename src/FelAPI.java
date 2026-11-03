/** Ejercicio 6. Certificador que produce facturas XML sin validez fiscal. */
public class FelAPI extends IntegracionAPI implements Documentable {
    public FelAPI(String id, String nombre, String endpoint) {
        super(id, nombre, endpoint);
    }

    @Override
    public String consultarEstado() {
        return "FEL - " + getNombre() + ": "
                + (isActiva() && isConectada() ? "certificador disponible para facturas de demostracion."
                        : "certificador no disponible.")
                + " Activa: " + isActiva() + "; conectada: " + isConectada();
    }

    @Override
    public String generarDocumento(String referencia) {
        if (!isActiva() || !isConectada() || referencia == null || referencia.trim().isEmpty()) {
            return "";
        }
        if (referencia.matches("(?s).*[\\x00-\\x08\\x0B\\x0C\\x0E-\\x1F].*")) {
            return "";
        }
        String texto = referencia.trim().replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;").replace("'", "&apos;");
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<factura simulada=\"true\"><referencia>" + texto + "</referencia></factura>";
    }
}
