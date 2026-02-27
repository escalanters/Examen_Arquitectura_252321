package MVC.interfaces;

import MVC.domain.Producto;

public interface IControlador {
    void incrementarCantidad(Producto producto);
    void decrementarCantidad(Producto producto);
    void checkout();
    void regresar();
    void pagar(String numeroTarjeta, String bancoEmisor, String ciudad);
    void aceptar();
}