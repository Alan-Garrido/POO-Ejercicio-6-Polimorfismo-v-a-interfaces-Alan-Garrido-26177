import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

/** Ejercicio 6. Administra la unica lista polimorfica del sistema. */
public class CatalogoAPI {
    private final ArrayList<IntegracionAPI> integraciones;

    public CatalogoAPI() {
        integraciones = new ArrayList<IntegracionAPI>();
    }

    /** La carga es idempotente: solo se ejecuta sobre un catalogo vacio. */
    public void cargarIniciales() {
        if (integraciones.isEmpty()) {
            IntegracionAPI api = new PagoAPI("P01", "Tarjeta A", "https://demo.test/api/p01");
            api.setLatenciaPromedio(120);
            registrar(api);
            api = new PagoAPI("P02", "Tarjeta B", "https://demo.test/api/p02");
            api.setLatenciaPromedio(180);
            registrar(api);
            api = new PagoAPI("P03", "Transferencia A", "https://demo.test/api/p03");
            api.setLatenciaPromedio(240);
            registrar(api);
            api = new PagoAPI("P04", "Billetera A", "https://demo.test/api/p04");
            api.setLatenciaPromedio(90);
            registrar(api);
            api = new ConsultaAPI("C01", "Rastreo A", "https://demo.test/api/c01");
            api.setLatenciaPromedio(70);
            registrar(api);
            api = new ConsultaAPI("C02", "Rastreo B", "https://demo.test/api/c02");
            api.setLatenciaPromedio(300);
            api.setConectada(false);
            registrar(api);
            api = new LogisticaAPI("L01", "Envios A", "https://demo.test/api/l01");
            api.setLatenciaPromedio(200);
            registrar(api);
            api = new LogisticaAPI("L02", "Ultima milla A", "https://demo.test/api/l02");
            api.setLatenciaPromedio(160);
            registrar(api);
            api = new FelAPI("F01", "Certificador A", "https://demo.test/api/f01");
            api.setLatenciaPromedio(150);
            registrar(api);
            api = new FelAPI("F02", "Certificador B", "https://demo.test/api/f02");
            api.setLatenciaPromedio(210);
            registrar(api);
        }
    }

    public boolean registrar(IntegracionAPI api) {
        // contains utiliza el equals por identificador de IntegracionAPI.
        if (api == null || integraciones.contains(api)) {
            return false;
        }
        integraciones.add(api);
        return true;
    }

    public IntegracionAPI buscarPorId(String id) {
        if (id == null) {
            return null;
        }
        String buscado = id.trim().toUpperCase(Locale.ROOT);
        for (IntegracionAPI api : integraciones) {
            if (api.getId().equals(buscado)) {
                return api;
            }
        }
        return null;
    }

    public String listarActivas() {
        StringBuilder listado = new StringBuilder();
        for (IntegracionAPI api : integraciones) {
            if (api.isActiva()) {
                listado.append(api.toString()).append(System.lineSeparator());
            }
        }
        return listado.length() == 0 ? "No hay integraciones activas." : listado.toString().trim();
    }

    public void ordenarPorLatencia() {
        Collections.sort(integraciones);
    }
}
