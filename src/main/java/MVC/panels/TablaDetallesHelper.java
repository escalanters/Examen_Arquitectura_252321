package MVC.panels;

import MVC.dto.DetalleVentaDTO;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public final class TablaDetallesHelper {

    private TablaDetallesHelper() {
    }

    public static JTable crearTabla(List<DetalleVentaDTO> detalles) {
        String[] columnas = {"Producto", "Cantidad", "Total"};
        Object[][] datos = new Object[detalles.size()][3];

        for (int i = 0; i < detalles.size(); i++) {
            DetalleVentaDTO d = detalles.get(i);
            datos[i][0] = d.getNombreProducto();
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
        header.setResizingAllowed(false);
        header.setReorderingAllowed(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        tabla.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        tabla.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);

        return tabla;
    }
}