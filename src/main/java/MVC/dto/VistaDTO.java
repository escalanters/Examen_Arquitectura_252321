package MVC.dto;

import MVC.enums.EstadoVista;

import java.util.List;

public class VistaDTO {
    private final EstadoVista estadoVista;
    private final List<ProductoDTO> productos;
    private final List<DetalleVentaDTO> detallesCarrito;
    private final double totalCarrito;
    private final UsuarioDTO usuario;
    private final List<DetalleVentaDTO> detallesVenta;
    private final Double totalVenta;

    public VistaDTO(EstadoVista estadoVista,
                    List<ProductoDTO> productos,
                    List<DetalleVentaDTO> detallesCarrito,
                    double totalCarrito,
                    UsuarioDTO usuario,
                    List<DetalleVentaDTO> detallesVenta,
                    Double totalVenta) {
        this.estadoVista = estadoVista;
        this.productos = productos;
        this.detallesCarrito = detallesCarrito;
        this.totalCarrito = totalCarrito;
        this.usuario = usuario;
        this.detallesVenta = detallesVenta;
        this.totalVenta = totalVenta;
    }

    public EstadoVista getEstadoVista() { return estadoVista; }
    public List<ProductoDTO> getProductos() { return productos; }
    public List<DetalleVentaDTO> getDetallesCarrito() { return detallesCarrito; }
    public double getTotalCarrito() { return totalCarrito; }
    public UsuarioDTO getUsuario() { return usuario; }
    public List<DetalleVentaDTO> getDetallesVenta() { return detallesVenta; }
    public Double getTotalVenta() { return totalVenta; }

    public boolean hayProductosEnCarrito() {
        return !detallesCarrito.isEmpty();
    }
}
