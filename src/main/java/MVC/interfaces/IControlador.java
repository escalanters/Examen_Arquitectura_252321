package MVC.interfaces;

public interface IControlador {
    void incrementarCantidad(String nombreProducto);
    void decrementarCantidad(String nombreProducto);
    void checkout();
    void regresar();
    void pagar(String numeroTarjeta, String bancoEmisor, String ciudad);
    void aceptar();
}