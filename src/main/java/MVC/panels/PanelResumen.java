package MVC.panels;

import MVC.dto.DetalleVentaDTO;
import MVC.dto.VistaDTO;
import MVC.interfaces.IControlador;
import MVC.interfaces.IPanelInfoEstado;
import MVC.styles.Button;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class PanelResumen implements IPanelInfoEstado {

    @Override
    public JPanel construir(VistaDTO datos, IControlador controlador) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Resumen de Compra:");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(titulo);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        List<DetalleVentaDTO> detalles = datos.getDetallesCarrito();
        JTable tabla = crearTablaDetalles(detalles);
        JScrollPane scrollTabla = new JScrollPane(tabla);
        scrollTabla.setPreferredSize(new Dimension(300, 120));
        scrollTabla.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        scrollTabla.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollTabla.setBorder(new LineBorder(Color.BLACK, 1));
        panel.add(scrollTabla);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        JLabel lblTotalTitulo = new JLabel("Total a Pagar:");
        lblTotalTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        lblTotalTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lblTotalTitulo);

        JLabel lblTotal = new JLabel(String.format("%.2fMXN", datos.getTotalCarrito()));
        lblTotal.setFont(new Font("Arial", Font.BOLD, 18));
        lblTotal.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lblTotal);
        panel.add(Box.createVerticalGlue());

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.setBackground(Color.WHITE);
        panelBoton.setAlignmentX(Component.LEFT_ALIGNMENT);

        Button btnCheckout = new Button("Checkout", Color.black);
        btnCheckout.setEnabled(datos.hayProductosEnCarrito());
        btnCheckout.addActionListener(e -> controlador.checkout());
        panelBoton.add(btnCheckout);

        panel.add(panelBoton);
        return panel;
    }

    private JTable crearTablaDetalles(List<DetalleVentaDTO> detalles) {
        return TablaDetallesHelper.crearTabla(detalles);
    }
}