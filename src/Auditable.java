/** Ejercicio 6. Contrato para conservar el ultimo evento de una integracion. */
public interface Auditable {
    void registrarEvento(String evento);
}
