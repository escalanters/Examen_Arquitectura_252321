package MVC.interfaces;

import MVC.enums.EstadoVista;
import MVC.domain.Producto;

/**
 * Interfaz de escritura del modelo.
 *
 * Cambio: incrementar/decrementar ahora reciben String (nombre del producto).
 * El modelo resuelve internamente la entidad de dominio.
 *
 * Principio GRASP Information Expert: el modelo conoce sus productos,
 * por lo tanto él es el experto para buscar un producto por nombre.
 */
public interface IModeloEscritura {
    void agregarProducto(Producto producto);
    void incrementarCantidad(String nombreProducto);
    void decrementarCantidad(String nombreProducto);
    void setEstadoVista(EstadoVista estado);
    void procesarPago(String numeroTarjeta, String bancoEmisor, String ciudad);
    void reiniciarCompra();
}