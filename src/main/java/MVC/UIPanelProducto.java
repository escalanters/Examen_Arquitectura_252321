package MVC;

import MVC.domain.Producto;
import MVC.interfaces.IComponent;
import MVC.interfaces.IControlador;
import MVC.interfaces.IModeloLectura;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
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

        JScrollPane scrollPane = new JScrollPane(panelContenedor);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void registrarImagen(String nombreProducto, String rutaImagen) {
        imagenesProducto.put(nombreProducto, rutaImagen);
    }

    @Override
    public void actualizar(IModeloLectura modelo) {
        panelContenedor.removeAll();
        Map<Producto, Integer> carrito = modelo.getCarrito();

        for (Producto p : modelo.getProductos()) {
            int cantidad = carrito.getOrDefault(p, 0);
            JPanel tarjeta = crearTarjetaProducto(p, cantidad);
            panelContenedor.add(tarjeta);
            panelContenedor.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        panelContenedor.revalidate();
        panelContenedor.repaint();
    }

    private JPanel crearTarjetaProducto(Producto producto, int cantidad) {
        JPanel tarjeta = new JPanel(new BorderLayout(10, 5));
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 160));
        tarjeta.setPreferredSize(new Dimension(350, 150));

        JLabel lblImagen = new JLabel();
        lblImagen.setPreferredSize(new Dimension(100, 100));
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagen.setBorder(new LineBorder(new Color(230, 230, 230), 1));

        String ruta = imagenesProducto.get(producto.getNombre());
        if (ruta != null) {
            try {
                File archivo = new File(ruta);
                if (archivo.exists()) {
                    BufferedImage img = ImageIO.read(archivo);
                    Image scaled = img.getScaledInstance(90, 90, Image.SCALE_SMOOTH);
                    lblImagen.setIcon(new ImageIcon(scaled));
                }
            } catch (Exception e) {
                lblImagen.setText("IMG");
                lblImagen.setFont(new Font("Arial", Font.BOLD, 12));
                lblImagen.setForeground(Color.GRAY);
            }
        } else {
            lblImagen.setText("IMG");
            lblImagen.setFont(new Font("Arial", Font.BOLD, 12));
            lblImagen.setForeground(Color.GRAY);
        }

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

        JPanel panelCantidad = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        panelCantidad.setBackground(Color.WHITE);
        panelCantidad.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton btnMenos = new JButton("—");
        btnMenos.setFont(new Font("Arial", Font.BOLD, 18));
        btnMenos.setPreferredSize(new Dimension(45, 40));
        btnMenos.setFocusPainted(false);
        btnMenos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnMenos.addActionListener(e -> controlador.decrementarCantidad(producto));

        JLabel lblCantidad = new JLabel(String.valueOf(cantidad), SwingConstants.CENTER);
        lblCantidad.setFont(new Font("Arial", Font.BOLD, 22));
        lblCantidad.setPreferredSize(new Dimension(40, 40));

        JButton btnMas = new JButton("+");
        btnMas.setFont(new Font("Arial", Font.BOLD, 18));
        btnMas.setPreferredSize(new Dimension(45, 40));
        btnMas.setFocusPainted(false);
        btnMas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnMas.addActionListener(e -> controlador.incrementarCantidad(producto));

        panelCantidad.add(btnMenos);
        panelCantidad.add(lblCantidad);
        panelCantidad.add(btnMas);

        panelDerecho.add(panelCantidad);
        tarjeta.add(panelDerecho, BorderLayout.CENTER);

        return tarjeta;
    }
}