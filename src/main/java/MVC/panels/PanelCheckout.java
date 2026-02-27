package MVC.panels;

import MVC.interfaces.IControlador;
import MVC.interfaces.IModeloLectura;
import MVC.interfaces.IPanelInfoEstado;
import MVC.styles.Button;

import javax.swing.*;
import java.awt.*;

public class PanelCheckout implements IPanelInfoEstado {

    @Override
    public JPanel construir(IModeloLectura modelo, IControlador controlador) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Datos para proceder pago:");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(titulo);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        panel.add(crearEtiqueta("Número de tarjeta"));
        panel.add(Box.createRigidArea(new Dimension(0, 4)));
        JTextField txtTarjeta = crearCampoTexto();
        panel.add(txtTarjeta);
        panel.add(Box.createRigidArea(new Dimension(0, 12)));

        panel.add(crearEtiqueta("Banco Emisor"));
        panel.add(Box.createRigidArea(new Dimension(0, 4)));
        JTextField txtBanco = crearCampoTexto();
        panel.add(txtBanco);
        panel.add(Box.createRigidArea(new Dimension(0, 12)));

        panel.add(crearEtiqueta("Ciudad"));
        panel.add(Box.createRigidArea(new Dimension(0, 4)));
        JTextField txtCiudad = crearCampoTexto();
        panel.add(txtCiudad);

        panel.add(Box.createVerticalGlue());
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        panelBotones.setBackground(Color.WHITE);
        panelBotones.setAlignmentX(Component.LEFT_ALIGNMENT);

        Button btnRegresar = new Button("Regresar", Color.GRAY);
        btnRegresar.addActionListener(e -> controlador.regresar());

        Button btnPagar = new Button("Pagar", Color.BLUE);
        btnPagar.addActionListener(e -> controlador.pagar(
                txtTarjeta.getText().trim(),
                txtBanco.getText().trim(),
                txtCiudad.getText().trim()
        ));

        panelBotones.add(btnRegresar);
        panelBotones.add(btnPagar);
        panel.add(panelBotones);

        return panel;
    }

    private JLabel crearEtiqueta(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Arial", Font.PLAIN, 12));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    private JTextField crearCampoTexto() {
        JTextField txt = new JTextField();
        txt.setFont(new Font("Arial", Font.PLAIN, 14));
        txt.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        txt.setAlignmentX(Component.LEFT_ALIGNMENT);
        return txt;
    }


}
