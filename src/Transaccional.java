/** Ejercicio 6. Capacidad de ejecutar operaciones financieras simuladas. */
public interface Transaccional {
    boolean cobrar(double monto);
    boolean reintegrar(double monto);
}
