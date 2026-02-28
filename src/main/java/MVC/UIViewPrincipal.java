package MVC;

import MVC.dto.VistaDTO;
import MVC.dto.VistaDTOMapper;
import MVC.enums.EstadoVista;
import MVC.interfaces.IComponent;
import MVC.interfaces.IControlador;
import MVC.interfaces.IModeloLectura;
import MVC.interfaces.ISuscriptor;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class UIViewPrincipal extends JFrame implements ISuscriptor {
    private static final int ANCHO_VENTANA = 1040;
    private static final int ALTO_VENTANA = 680;

    private final IModeloLectura lectura;
    private final IControlador controlador;
    private final List<IComponent> componentesActualizables;
    private final Map<EstadoVista, String[]> titulosPorEstado;

    private JLabel lblTitulo;
    private UIPanelProducto uiPanelProducto;
    private UIPanelInformacion uiPanelInformacion;

    public UIViewPrincipal(IControlador controlador, IModeloLectura lectura) {
        this.controlador = controlador;
        this.lectura = lectura;
        componentesActualizables = new ArrayList<>();
        titulosPorEstado = new EnumMap<>(EstadoVista.class);

        configurarTitulos();
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarTitulos() {
        titulosPorEstado.put(EstadoVista.LISTA_PRODUCTOS,
                new String[]{"ListaProductos", "LISTA DE PRODUCTOS"});
        titulosPorEstado.put(EstadoVista.CHECKOUT,
                new String[]{"Checkout", "CHECKOUT:"});
        titulosPorEstado.put(EstadoVista.CONFIRMACION_PAGO,
                new String[]{"Confirmación Pago", "RECIBO DE PAGO:"});
    }

    private void configurarVentana() {
        setTitle("ListaProductos");
        setSize(ANCHO_VENTANA, ALTO_VENTANA);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);
        setResizable(false);
    }

    private void inicializarComponentes() {
        lblTitulo = new JLabel("LISTA DE PRODUCTOS", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        lblTitulo.setOpaque(true);
        lblTitulo.setBackground(Color.WHITE);
        add(lblTitulo, BorderLayout.NORTH);

        uiPanelProducto = new UIPanelProducto(controlador);
        uiPanelProducto.setPreferredSize(new Dimension(420, 0));

        uiPanelInformacion = new UIPanelInformacion(controlador);

        componentesActualizables.add(uiPanelProducto);
        componentesActualizables.add(uiPanelInformacion);

        JPanel panelCentral = new JPanel(new GridLayout(1, 2, 15, 0));
        panelCentral.setBackground(Color.WHITE);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));
        panelCentral.add(uiPanelProducto);
        panelCentral.add(uiPanelInformacion);

        add(panelCentral, BorderLayout.CENTER);
    }

    public UIPanelProducto getUiPanelProducto() { return uiPanelProducto; }
    public UIPanelInformacion getUiPanelInformacion() { return uiPanelInformacion; }

    @Override
    public void actualizar() {
        VistaDTO datos = VistaDTOMapper.desdeModelo(lectura);

        String[] titulos = titulosPorEstado.get(datos.getEstadoVista());
        if (titulos != null) {
            setTitle(titulos[0]);
            lblTitulo.setText(titulos[1]);
        }

        for (IComponent componente : componentesActualizables) {
            componente.actualizar(datos);
        }
    }
}