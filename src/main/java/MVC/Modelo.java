package MVC;

import MVC.domain.DetalleVenta;
import MVC.domain.Producto;
import MVC.domain.Usuario;
import MVC.domain.Venta;
import MVC.enums.EstadoVista;
import MVC.interfaces.IModeloEscritura;
import MVC.interfaces.IModeloLectura;
import MVC.interfaces.ISuscriptor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Modelo implements IModeloEscritura, IModeloLectura {
    private final List<ISuscriptor> suscriptores;
    private final List<Producto> productos;
    private final Map<Producto, Integer> carrito;
    private Venta ventaActual;
    private Usuario usuarioActual;
    private EstadoVista estadoVista;

    public Modelo() {
        suscriptores = new ArrayList<>();
        productos = new ArrayList<>();
        carrito = new LinkedHashMap<>();
        estadoVista = EstadoVista.LISTA_PRODUCTOS;
    }

    public void agregarSuscriptor(ISuscriptor suscriptor) {
        suscriptores.add(suscriptor);
    }

    private void notificarCambios() {
        for (ISuscriptor s : suscriptores) {
            s.actualizar();
        }
    }

    private Producto buscarProductoPorNombre(String nombre) {
        for (Producto p : productos) {
            if (p.getNombre().equals(nombre)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public void agregarProducto(Producto producto) {
        productos.add(producto);
        carrito.put(producto, 0);
    }

    @Override
    public void incrementarCantidad(String nombreProducto) {
        Producto producto = buscarProductoPorNombre(nombreProducto);
        if (producto == null) return;

        int actual = carrito.getOrDefault(producto, 0);
        if (actual < producto.getStock()) {
            carrito.put(producto, actual + 1);
            notificarCambios();
        }
    }

    @Override
    public void decrementarCantidad(String nombreProducto) {
        Producto producto = buscarProductoPorNombre(nombreProducto);
        if (producto == null) return;

        int actual = carrito.getOrDefault(producto, 0);
        if (actual > 0) {
            carrito.put(producto, actual - 1);
            notificarCambios();
        }
    }

    @Override
    public void setEstadoVista(EstadoVista estado) {
        this.estadoVista = estado;
        notificarCambios();
    }

    @Override
    public void procesarPago(String numeroTarjeta, String bancoEmisor, String ciudad) {
        if (numeroTarjeta.trim().isEmpty() || bancoEmisor.trim().isEmpty() || ciudad.trim().isEmpty()) {
            return;
        }
        this.usuarioActual = new Usuario(numeroTarjeta, bancoEmisor, ciudad);
        List<DetalleVenta> detalles = generarDetallesCarrito();
        double total = calcularTotal();
        this.ventaActual = new Venta(detalles, total);
        this.estadoVista = EstadoVista.CONFIRMACION_PAGO;
        notificarCambios();
    }

    @Override
    public void reiniciarCompra() {
        for (Producto p : productos) {
            carrito.put(p, 0);
        }
        ventaActual = null;
        usuarioActual = null;
        estadoVista = EstadoVista.LISTA_PRODUCTOS;
        notificarCambios();
    }

    @Override
    public List<Producto> getProductos() { return productos; }

    @Override
    public Map<Producto, Integer> getCarrito() { return carrito; }

    @Override
    public Venta getVentaActual() { return ventaActual; }

    @Override
    public Usuario getUsuarioActual() { return usuarioActual; }

    @Override
    public EstadoVista getEstadoVista() { return estadoVista; }

    @Override
    public List<DetalleVenta> generarDetallesCarrito() {
        List<DetalleVenta> detalles = new ArrayList<>();
        for (Map.Entry<Producto, Integer> entry : carrito.entrySet()) {
            if (entry.getValue() > 0) {
                detalles.add(new DetalleVenta(entry.getKey(), entry.getValue()));
            }
        }
        return detalles;
    }

    @Override
    public double calcularTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<Producto, Integer> entry : carrito.entrySet()) {
            BigDecimal precio = BigDecimal.valueOf(entry.getKey().getPrecio());
            BigDecimal cantidad = BigDecimal.valueOf(entry.getValue());
            total = total.add(precio.multiply(cantidad));
        }
        return total.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}