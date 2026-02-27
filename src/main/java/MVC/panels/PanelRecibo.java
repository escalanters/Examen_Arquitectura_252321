package MVC.panels;

import MVC.domain.DetalleVenta;
import MVC.domain.Usuario;
import MVC.domain.Venta;
import MVC.interfaces.IControlador;
import MVC.interfaces.IModeloLectura;
import MVC.interfaces.IPanelInfoEstado;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;
import static MVC.panels.PanelResumen.getjTable;

public class PanelRecibo implements IPanelInfoEstado {

    @Override
    public JPanel construir(IModeloLectura modelo, IControlador controlador) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Ticket:");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(titulo);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        Usuario usuario = modelo.getUsuarioActual();
        if (usuario != null) {
            panel.add(crearCampoRecibo("Número de tarjeta", usuario.getNumeroTarjeta()));
            panel.add(Box.createRigidArea(new Dimension(0, 8)));
            panel.add(crearCampoRecibo("Banco Emisor", usuario.getBancoEmisor()));
            panel.add(Box.createRigidArea(new Dimension(0, 8)));
            panel.add(crearCampoRecibo("Ciudad", usuario.getCiudad()));
        }

        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        JLabel lblResumen = new JLabel("Resumen de Compra:");
        lblResumen.setFont(new Font("Arial", Font.BOLD, 14));
        lblResumen.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lblResumen);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));

        Venta venta = modelo.getVentaActual();
        if (venta != null) {
            JTable tabla = crearTablaDetalles(venta.getDetalles());
            JScrollPane scrollTabla = new JScrollPane(tabla);
            scrollTabla.setPreferredSize(new Dimension(300, 100));
            scrollTabla.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
            scrollTabla.setAlignmentX(Component.LEFT_ALIGNMENT);
            scrollTabla.setBorder(new LineBorder(Color.BLACK, 1));
            panel.add(scrollTabla);

            panel.add(Box.createRigidArea(new Dimension(0, 10)));

            JLabel lblTotalTitulo = new JLabel("Total a Pagar:");
            lblTotalTitulo.setFont(new Font("Arial", Font.BOLD, 14));
            lblTotalTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(lblTotalTitulo);

            JLabel lblTotal = new JLabel(String.format("%.2fMXN", venta.getTotal()));
            lblTotal.setFont(new Font("Arial", Font.BOLD, 16));
            lblTotal.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(lblTotal);
        }

        panel.add(Box.createVerticalGlue());

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.setBackground(Color.WHITE);
        panelBoton.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnAceptar.setPreferredSize(new Dimension(120, 35));
        btnAceptar.setFocusPainted(false);
        btnAceptar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnAceptar.addActionListener(e -> controlador.aceptar());
        panelBoton.add(btnAceptar);

        panel.add(panelBoton);
        return panel;
    }

    private JPanel crearCampoRecibo(String etiqueta, String valor) {
        JPanel campoPanel = new JPanel();
        campoPanel.setLayout(new BoxLayout(campoPanel, BoxLayout.Y_AXIS));
        campoPanel.setBackground(Color.WHITE);
        campoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(new Font("Arial", Font.PLAIN, 11));
        lbl.setForeground(new Color(100, 100, 100));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField txt = new JTextField(valor);
        txt.setFont(new Font("Arial", Font.PLAIN, 13));
        txt.setEditable(false);
        txt.setBackground(new Color(80, 80, 80));
        txt.setForeground(Color.WHITE);
        txt.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        txt.setAlignmentX(Component.LEFT_ALIGNMENT);

        campoPanel.add(lbl);
        campoPanel.add(Box.createRigidArea(new Dimension(0, 2)));
        campoPanel.add(txt);
        return campoPanel;
    }

    private JTable crearTablaDetalles(List<DetalleVenta> detalles) {
        return getjTable(detalles);
    }
}
