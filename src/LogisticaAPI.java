/** Ejercicio 6. Proveedor logistico que emite guias XML simuladas. */
public class LogisticaAPI extends IntegracionAPI implements Documentable {
    public LogisticaAPI(String id, String nombre, String endpoint) {
        super(id, nombre, endpoint);
    }

    @Override
    public String consultarEstado() {
        return "Logistica - " + getNombre() + ": "
                + (isActiva() && isConectada() ? "servicio de envios y guias disponible (simulacion)."
                        : "servicio de envios no disponible.")
                + " Activa: " + isActiva() + "; conectada: " + isConectada();
    }

    @Override
    public String generarDocumento(String referencia) {
        if (!isActiva() || !isConectada() || referencia == null || referencia.trim().isEmpty()) {
            return "";
        }
        // XML 1.0 no admite estos caracteres de control, aunque se escapen etiquetas.
        if (referencia.matches("(?s).*[\\x00-\\x08\\x0B\\x0C\\x0E-\\x1F].*")) {
            return "";
        }
        String texto = referencia.trim().replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;").replace("'", "&apos;");
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<guia simulada=\"true\"><referencia>" + texto + "</referencia></guia>";
    }
}
