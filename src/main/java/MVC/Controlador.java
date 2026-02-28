package MVC;

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
    public void incrementarCantidad(String nombreProducto) {
        escritura.incrementarCantidad(nombreProducto);
    }

    @Override
    public void decrementarCantidad(String nombreProducto) {
        escritura.decrementarCantidad(nombreProducto);
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