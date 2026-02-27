package MVC.interfaces;

import javax.swing.JPanel;

public interface IPanelInfoEstado {
    JPanel construir(IModeloLectura modelo, IControlador controlador);
}