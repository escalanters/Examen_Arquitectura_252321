package MVC.panels;

import MVC.domain.DetalleVenta;
import MVC.interfaces.IControlador;
import MVC.interfaces.IModeloLectura;
import MVC.interfaces.IPanelInfoEstado;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class PanelResumen implements IPanelInfoEstado {

    @Override
    public JPanel construir(IModeloLectura modelo, IControlador controlador) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Resumen de Compra:");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(titulo);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        List<DetalleVenta> detalles = modelo.generarDetallesCarrito();
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

        JLabel lblTotal = new JLabel(String.format("%.2fMXN", modelo.calcularTotal()));
        lblTotal.setFont(new Font("Arial", Font.BOLD, 18));
        lblTotal.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lblTotal);
        panel.add(Box.createVerticalGlue());

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.setBackground(Color.WHITE);
        panelBoton.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton btnCheckout = new JButton("Checkout");
        btnCheckout.setFont(new Font("Arial", Font.PLAIN, 14));
        btnCheckout.setPreferredSize(new Dimension(120, 35));
        btnCheckout.setFocusPainted(false);
        btnCheckout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCheckout.addActionListener(e -> controlador.checkout());
        panelBoton.add(btnCheckout);

        panel.add(panelBoton);
        return panel;
    }

    private JTable crearTablaDetalles(List<DetalleVenta> detalles) {
        return getjTable(detalles);
    }

    static JTable getjTable(List<DetalleVenta> detalles) {
        String[] columnas = {"Producto", "Cantidad", "Total"};
        Object[][] datos = new Object[detalles.size()][3];

        for (int i = 0; i < detalles.size(); i++) {
            DetalleVenta d = detalles.get(i);
            datos[i][0] = d.getProducto().getNombre();
            datos[i][1] = d.getCantidad();
            datos[i][2] = String.format("%.2f", d.getSubtotal());
        }

        DefaultTableModel tableModel = new DefaultTableModel(datos, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        JTable tabla = new JTable(tableModel);
        tabla.setFont(new Font("Arial", Font.PLAIN, 13));
        tabla.setRowHeight(24);
        tabla.setShowGrid(true);
        tabla.setGridColor(Color.BLACK);

        JTableHeader header = tabla.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 13));
        header.setBackground(Color.WHITE);
        header.setBorder(new LineBorder(Color.BLACK, 1));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        tabla.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        tabla.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);

        return tabla;
    }
}