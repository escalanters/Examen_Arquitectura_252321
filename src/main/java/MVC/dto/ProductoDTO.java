package MVC.dto;

public class ProductoDTO {
    private final String nombre;
    private final double precio;
    private final int stock;
    private final boolean disponible;
    private final int cantidadEnCarrito;

    public ProductoDTO(String nombre, double precio, int stock, boolean disponible, int cantidadEnCarrito) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.disponible = disponible;
        this.cantidadEnCarrito = cantidadEnCarrito;
    }

    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public int getCantidadEnCarrito() { return cantidadEnCarrito; }
}