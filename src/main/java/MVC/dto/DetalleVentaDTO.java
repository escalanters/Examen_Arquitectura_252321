package MVC.dto;

public class DetalleVentaDTO {
    private final String nombreProducto;
    private final int cantidad;
    private final double subtotal;

    public DetalleVentaDTO(String nombreProducto, int cantidad, double subtotal) {
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    public String getNombreProducto() { return nombreProducto; }
    public int getCantidad() { return cantidad; }
    public double getSubtotal() { return subtotal; }
}