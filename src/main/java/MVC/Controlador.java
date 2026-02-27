package MVC;

import MVC.domain.Producto;
import MVC.enums.EstadoVista;
import MVC.interfaces.IControlador;
import MVC.interfaces.IModeloEscritura;
import MVC.interfaces.IModeloLectura;

public class Controlador implements IControlador {
    private final IModeloEscritura escritura;
    private final IModeloLectura lectura;

    public Controlador(IModeloEscritura escritura, IModeloLectura lectura) {
        this.escritura = escritura;
        this.lectura = lectura;
    }

    @Override
    public void incrementarCantidad(Producto producto) {
        escritura.incrementarCantidad(producto);
    }

    @Override
    public void decrementarCantidad(Producto producto) {
        escritura.decrementarCantidad(producto);
    }

    @Override
    public void checkout() {
        if (!lectura.generarDetallesCarrito().isEmpty()) {
            escritura.setEstadoVista(EstadoVista.CHECKOUT);
        }
    }

    @Override
    public void regresar() {
        escritura.setEstadoVista(EstadoVista.LISTA_PRODUCTOS);
    }

    @Override
    public void pagar(String numeroTarjeta, String bancoEmisor, String ciudad) {
        if (numeroTarjeta.isBlank() || bancoEmisor.isBlank() || ciudad.isBlank()) {
            return;
        }
        escritura.procesarPago(numeroTarjeta, bancoEmisor, ciudad);
    }

    @Override
    public void aceptar() {
        escritura.reiniciarCompra();
    }
}