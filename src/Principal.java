import java.util.NoSuchElementException;
import java.util.Scanner;

/** Ejercicio 6. Vista de consola: unica clase que lee y muestra mensajes. */
public class Principal {
    private final Scanner entrada;
    private final Controlador controlador;

    public Principal(Controlador controlador) {
        if (controlador == null) {
            throw new IllegalArgumentException("El controlador es obligatorio.");
        }
        this.controlador = controlador;
        entrada = new Scanner(System.in);
    }

    public static void main(String[] args) {
        CatalogoAPI catalogo = new CatalogoAPI();
        MotorCheckout motor = new MotorCheckout();
        Controlador controlador = new Controlador(catalogo, motor);
        Principal principal = new Principal(controlador);
        principal.ejecutar();
    }

    public void ejecutar() {
        controlador.iniciar();
        mostrarMensaje("Integraciones de APIs - Ejercicio 6\nTodas las operaciones son simuladas.");
        int opcion = -1;
        try {
            while (opcion != 0) {
                mostrarMenu();
                opcion = leerOpcion();
                if (opcion == 1) {
                    mostrarMensaje(controlador.listarIntegraciones());
                } else if (opcion == 2) {
                    mostrarMensaje(controlador.buscarConfiguracion(leerTexto("ID de la API:")));
                } else if (opcion == 3) {
                    mostrarMensaje(controlador.ordenarIntegraciones());
                } else if (opcion == 4) {
                    mostrarMensaje(controlador.consultarAPI(leerTexto("ID de la API:")));
                } else if (opcion == 5) {
                    String id = leerTexto("ID de la API de pago:");
                    mostrarMensaje(controlador.cobrar(id, leerMonto()));
                } else if (opcion == 6) {
                    String id = leerTexto("ID de la API para solicitar el reintegro:");
                    mostrarMensaje(controlador.reintegrar(id, leerMonto()));
                } else if (opcion == 7) {
                    String id = leerTexto("ID de la API documental:");
                    String referencia = leerTexto("Referencia del documento:");
                    mostrarMensaje(controlador.generarDocumento(id, referencia));
                }
            }
        } catch (NoSuchElementException excepcion) {
            // Fin de entrada (Ctrl+Z en Windows): cerrar sin repetir indefinidamente.
            mostrarMensaje("La entrada de datos ha finalizado.");
        } finally {
            entrada.close();
        }
        mostrarMensaje("Programa finalizado.");
    }

    private void mostrarMenu() {
        mostrarMensaje("\n1. Listar integraciones activas\n2. Buscar configuracion por ID"
                + "\n3. Ordenar por latencia\n4. Consultar estado\n5. Simular cobro"
                + "\n6. Solicitar reintegro\n7. Generar documento XML simulado\n0. Salir");
    }

    private int leerOpcion() {
        int opcion = -1;
        boolean valida = false;
        do {
            String texto = leerTexto("Seleccione una opcion (0 a 7):");
            try {
                opcion = Integer.parseInt(texto);
                valida = opcion >= 0 && opcion <= 7;
            } catch (NumberFormatException excepcion) {
                valida = false;
            }
            if (!valida) {
                mostrarMensaje("Opcion invalida. Ingrese un numero entero entre 0 y 7.");
            }
        } while (!valida);
        return opcion;
    }

    private double leerMonto() {
        double monto = 0;
        boolean valido = false;
        do {
            String texto = leerTexto("Monto mayor que cero (ejemplo: 100.50, sin separador de miles):");
            try {
                monto = Double.parseDouble(texto);
                valido = Double.isFinite(monto) && monto > 0;
            } catch (NumberFormatException excepcion) {
                valido = false;
            }
            if (!valido) {
                mostrarMensaje("Monto invalido. Ingrese un numero finito mayor que cero usando punto decimal.");
            }
        } while (!valido);
        return monto;
    }

    private String leerTexto(String mensaje) {
        String texto;
        do {
            mostrarMensaje(mensaje);
            texto = entrada.nextLine().trim();
            if (texto.isEmpty()) {
                mostrarMensaje("El texto no puede estar vacio.");
            }
        } while (texto.isEmpty());
        return texto;
    }

    private void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
}
