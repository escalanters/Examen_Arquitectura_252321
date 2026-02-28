package MVC;

import MVC.domain.Producto;
import MVC.enums.EstadoVista;
import MVC.panels.PanelCheckout;
import MVC.panels.PanelRecibo;
import MVC.panels.PanelResumen;

public class Main {
   static void main(String[] args) {
      Modelo modelo = new Modelo();
      Controlador controlador = new Controlador(modelo, modelo);

      modelo.agregarProducto(new Producto("Sabritas", 28.00, 50, true));
      modelo.agregarProducto(new Producto("Galletas", 41.00, 30, true));
      modelo.agregarProducto(new Producto("Pan Dulce", 13.00, 40, true));
      modelo.agregarProducto(new Producto("Cafe", 41.00, 30, true));

      UIViewPrincipal view = new UIViewPrincipal(controlador, modelo);

      view.getUiPanelInformacion().registrarEstrategia(EstadoVista.LISTA_PRODUCTOS, new PanelResumen());
      view.getUiPanelInformacion().registrarEstrategia(EstadoVista.CHECKOUT, new PanelCheckout());
      view.getUiPanelInformacion().registrarEstrategia(EstadoVista.CONFIRMACION_PAGO, new PanelRecibo());

      view.getUiPanelProducto().registrarImagen("Sabritas", "/assets/sabritas.png");
      view.getUiPanelProducto().registrarImagen("Galletas", "/assets/oreos.png");
      view.getUiPanelProducto().registrarImagen("Pan Dulce", "/assets/pan_dulce.png");
      view.getUiPanelProducto().registrarImagen("Cafe", "assets/cafe.png");

      modelo.agregarSuscriptor(view);

      view.actualizar();
      view.setVisible(true);
   }
}