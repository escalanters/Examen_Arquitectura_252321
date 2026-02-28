package MVC.interfaces;

import MVC.dto.VistaDTO;

import javax.swing.JPanel;

public interface IPanelInfoEstado {
    JPanel construir(VistaDTO datos, IControlador controlador);
}