package MVC.interfaces;

import MVC.enums.EstadoVista;
import MVC.domain.Producto;


public interface IModeloEscritura {
    void agregarProducto(Producto producto);
    void incrementarCantidad(String nombreProducto);
    void decrementarCantidad(String nombreProducto);
    void setEstadoVista(EstadoVista estado);
    void procesarPago(String numeroTarjeta, String bancoEmisor, String ciudad);
    void reiniciarCompra();
}