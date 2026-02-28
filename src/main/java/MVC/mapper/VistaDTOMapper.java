package MVC.mapper;

import MVC.domain.DetalleVenta;
import MVC.domain.Producto;
import MVC.domain.Usuario;
import MVC.domain.Venta;
import MVC.dto.DetalleVentaDTO;
import MVC.dto.ProductoDTO;
import MVC.dto.UsuarioDTO;
import MVC.dto.VistaDTO;
import MVC.interfaces.IModeloLectura;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class VistaDTOMapper {

    private VistaDTOMapper() {
    }

    public static VistaDTO desdeModelo(IModeloLectura modelo) {
        List<ProductoDTO> productosDTO = mapearProductos(modelo);
        List<DetalleVentaDTO> detallesCarritoDTO = mapearDetalles(modelo.generarDetallesCarrito());
        double totalCarrito = modelo.calcularTotal();

        UsuarioDTO usuarioDTO = mapearUsuario(modelo.getUsuarioActual());

        List<DetalleVentaDTO> detallesVentaDTO = null;
        Double totalVenta = null;
        Venta venta = modelo.getVentaActual();
        if (venta != null) {
            detallesVentaDTO = mapearDetalles(venta.getDetalles());
            totalVenta = venta.getTotal();
        }

        return new VistaDTO(
                modelo.getEstadoVista(),
                productosDTO,
                detallesCarritoDTO,
                totalCarrito,
                usuarioDTO,
                detallesVentaDTO,
                totalVenta
        );
    }

    private static List<ProductoDTO> mapearProductos(IModeloLectura modelo) {
        List<ProductoDTO> lista = new ArrayList<>();
        Map<Producto, Integer> carrito = modelo.getCarrito();
        for (Producto p : modelo.getProductos()) {
            int cantidad = carrito.getOrDefault(p, 0);
            lista.add(new ProductoDTO(
                    p.getNombre(),
                    p.getPrecio(),
                    p.getStock(),
                    p.isDisponible(),
                    cantidad
            ));
        }
        return lista;
    }

    private static List<DetalleVentaDTO> mapearDetalles(List<DetalleVenta> detalles) {
        List<DetalleVentaDTO> lista = new ArrayList<>();
        for (DetalleVenta d : detalles) {
            lista.add(new DetalleVentaDTO(
                    d.getProducto().getNombre(),
                    d.getCantidad(),
                    d.getSubtotal()
            ));
        }
        return lista;
    }

    private static UsuarioDTO mapearUsuario(Usuario usuario) {
        if (usuario == null) return null;
        return new UsuarioDTO(
                usuario.getNumeroTarjeta(),
                usuario.getBancoEmisor(),
                usuario.getCiudad()
        );
    }
}