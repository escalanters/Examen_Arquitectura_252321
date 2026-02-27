package MVC.interfaces;

import MVC.enums.EstadoVista;
import MVC.domain.DetalleVenta;
import MVC.domain.Producto;
import MVC.domain.Usuario;
import MVC.domain.Venta;

import java.util.List;
import java.util.Map;

public interface IModeloLectura {
    List<Producto> getProductos();
    Map<Producto, Integer> getCarrito();
    Venta getVentaActual();
    Usuario getUsuarioActual();
    EstadoVista getEstadoVista();
    List<DetalleVenta> generarDetallesCarrito();
    double calcularTotal();
}