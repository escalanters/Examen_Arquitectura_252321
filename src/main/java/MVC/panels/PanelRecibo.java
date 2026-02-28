package MVC.panels;

import MVC.dto.DetalleVentaDTO;
import MVC.dto.UsuarioDTO;
import MVC.dto.VistaDTO;
import MVC.interfaces.IControlador;
import MVC.interfaces.IPanelInfoEstado;
import MVC.styles.Button;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

public class PanelRecibo implements IPanelInfoEstado {

    @Override
    public JPanel construir(VistaDTO datos, IControlador controlador) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Ticket:");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(titulo);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        UsuarioDTO usuario = datos.getUsuario();
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

        List<DetalleVentaDTO> detallesVenta = datos.getDetallesVenta();
        Double totalVenta = datos.getTotalVenta();

        if (detallesVenta != null && totalVenta != null) {
            JTable tabla = TablaDetallesHelper.crearTabla(detallesVenta);
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

            JLabel lblTotal = new JLabel(String.format("%.2fMXN", totalVenta));
            lblTotal.setFont(new Font("Arial", Font.BOLD, 16));
            lblTotal.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(lblTotal);
        }

        panel.add(Box.createVerticalGlue());

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.setBackground(Color.WHITE);
        panelBoton.setAlignmentX(Component.LEFT_ALIGNMENT);

        Button btnAceptar = new Button("Aceptar", Color.black);
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
}