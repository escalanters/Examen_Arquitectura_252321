package MVC;

import MVC.dto.ProductoDTO;
import MVC.dto.VistaDTO;
import MVC.enums.EstadoVista;
import MVC.interfaces.IComponent;
import MVC.interfaces.IControlador;
import MVC.styles.Button;
import MVC.styles.CustomScrollPane;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class UIPanelProducto extends JPanel implements IComponent {
    private final IControlador controlador;
    private final JPanel panelContenedor;
    private final Map<String, String> imagenesProducto;

    public UIPanelProducto(IControlador controlador) {
        this.controlador = controlador;
        this.imagenesProducto = new HashMap<>();
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        panelContenedor = new JPanel();
        panelContenedor.setLayout(new BoxLayout(panelContenedor, BoxLayout.Y_AXIS));
        panelContenedor.setBackground(Color.WHITE);
        JScrollPane scrollPane = construirScrollPane();
        add(scrollPane, BorderLayout.CENTER);
    }

    private CustomScrollPane construirScrollPane() {
        CustomScrollPane scrollPane = new CustomScrollPane(
                panelContenedor,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        scrollPane.getHorizontalScrollBar().setUnitIncrement(200);
        return scrollPane;
    }

    public void registrarImagen(String nombreProducto, String rutaImagen) {
        imagenesProducto.put(nombreProducto, rutaImagen);
    }

    @Override
    public void actualizar(VistaDTO datos) {
        panelContenedor.removeAll();

        boolean permitirEdicion = datos.getEstadoVista() == EstadoVista.LISTA_PRODUCTOS;

        for (ProductoDTO producto : datos.getProductos()) {
            JPanel tarjeta = crearTarjetaProducto(producto, permitirEdicion);
            panelContenedor.add(tarjeta);
            panelContenedor.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        panelContenedor.revalidate();
        panelContenedor.repaint();
    }

    private JPanel crearTarjetaProducto(ProductoDTO producto, boolean permitirEdicion) {
        JPanel tarjeta = new JPanel(new BorderLayout(10, 5));
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 160));
        tarjeta.setPreferredSize(new Dimension(350, 150));

        JLabel lblImagen = crearLabelImagen(producto.getNombre());
        tarjeta.add(lblImagen, BorderLayout.WEST);

        JPanel panelDerecho = new JPanel();
        panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
        panelDerecho.setBackground(Color.WHITE);

        JLabel lblNombre = new JLabel(producto.getNombre());
        lblNombre.setFont(new Font("Arial", Font.BOLD, 16));
        lblNombre.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblPrecio = new JLabel(String.format("%.2fMXN", producto.getPrecio()));
        lblPrecio.setFont(new Font("Arial", Font.PLAIN, 13));
        lblPrecio.setForeground(new Color(100, 100, 100));
        lblPrecio.setAlignmentX(Component.LEFT_ALIGNMENT);

        panelDerecho.add(lblNombre);
        panelDerecho.add(Box.createRigidArea(new Dimension(0, 4)));
        panelDerecho.add(lblPrecio);
        panelDerecho.add(Box.createVerticalGlue());

        int cantidad = producto.getCantidadEnCarrito();

        JPanel panelCantidad = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        panelCantidad.setBackground(Color.WHITE);
        panelCantidad.setAlignmentX(Component.LEFT_ALIGNMENT);

        // La UI envía el NOMBRE del producto al controlador, no el objeto de dominio
        Button btnMenos = new Button(" - ", Color.gray);
        btnMenos.addActionListener(e -> controlador.decrementarCantidad(producto.getNombre()));
        btnMenos.setEnabled(permitirEdicion && cantidad > 0);

        JLabel lblCantidad = new JLabel(String.valueOf(cantidad), SwingConstants.CENTER);
        lblCantidad.setFont(new Font("Arial", Font.BOLD, 22));
        lblCantidad.setPreferredSize(new Dimension(40, 40));

        Button btnMas = new Button(" + ", Color.gray);
        btnMas.addActionListener(e -> controlador.incrementarCantidad(producto.getNombre()));
        btnMas.setEnabled(permitirEdicion);

        panelCantidad.add(btnMenos);
        panelCantidad.add(lblCantidad);
        panelCantidad.add(btnMas);

        panelDerecho.add(panelCantidad);
        tarjeta.add(panelDerecho, BorderLayout.CENTER);

        return tarjeta;
    }

    private JLabel crearLabelImagen(String nombreProducto) {
        JLabel lblImagen = new JLabel();
        lblImagen.setPreferredSize(new Dimension(100, 100));
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagen.setBorder(new LineBorder(new Color(230, 230, 230), 1));

        String ruta = imagenesProducto.get(nombreProducto);
        if (ruta != null) {
            try {
                java.net.URL imgURL = getClass().getResource(ruta);
                if (imgURL != null) {
                    BufferedImage img = ImageIO.read(imgURL);
                    Image scaled = img.getScaledInstance(90, 90, Image.SCALE_SMOOTH);
                    lblImagen.setIcon(new ImageIcon(scaled));
                } else {
                    lblImagen.setText("IMG NOT FOUND");
                    lblImagen.setFont(new Font("Arial", Font.BOLD, 10));
                    lblImagen.setForeground(Color.RED);
                }
            } catch (Exception e) {
                lblImagen.setText("ERR");
                lblImagen.setFont(new Font("Arial", Font.BOLD, 12));
                lblImagen.setForeground(Color.GRAY);
            }
        } else {
            lblImagen.setText("IMG");
            lblImagen.setFont(new Font("Arial", Font.BOLD, 12));
            lblImagen.setForeground(Color.GRAY);
        }

        return lblImagen;
    }
}