package MVC.domain;

import java.util.List;

public class Venta {
    private List<DetalleVenta> detalles;
    private double total;

    public Venta(List<DetalleVenta> detalles, double total) {
        this.detalles = detalles;
        this.total = total;
    }

    public List<DetalleVenta> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleVenta> detalles) { this.detalles = detalles; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
}