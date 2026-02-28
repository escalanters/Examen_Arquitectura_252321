package MVC.panels;

import MVC.dto.VistaDTO;
import MVC.enums.EstadoVista;
import MVC.interfaces.IComponent;
import MVC.interfaces.IControlador;
import MVC.interfaces.IPanelInfoEstado;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.EnumMap;
import java.util.Map;

public class UIPanelInformacion extends JPanel implements IComponent {

    private final IControlador controlador;
    private final JPanel panelContenido;
    private final Map<EstadoVista, IPanelInfoEstado> estrategias;

    public UIPanelInformacion(IControlador controlador) {
        this.controlador = controlador;
        this.estrategias = new EnumMap<>(EstadoVista.class);
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(60, 60, 60), 2),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        panelContenido = new JPanel(new BorderLayout());
        panelContenido.setBackground(Color.WHITE);
        add(panelContenido, BorderLayout.CENTER);
    }

    public void registrarEstrategia(EstadoVista estado, IPanelInfoEstado estrategia) {
        estrategias.put(estado, estrategia);
    }

    @Override
    public void actualizar(VistaDTO datos) {
        panelContenido.removeAll();

        IPanelInfoEstado estrategia = estrategias.get(datos.getEstadoVista());
        if (estrategia != null) {
            JPanel panel = estrategia.construir(datos, controlador);
            panelContenido.add(panel, BorderLayout.CENTER);
        }

        panelContenido.revalidate();
        panelContenido.repaint();
    }
}