package vista;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;

public class Resumen extends VentanaModelo {

	private static final long serialVersionUID = 1L;
	private JPanel panel;
	public JTable tabla;
	public JButton btnImprimir;
	public JButton btnVolver;
	
	public Resumen(String nombreVentana, int x, int y) {
		
		super(nombreVentana);
		setResizable(true);
		setMinimumSize(new Dimension(350, 480));
		setBounds(x + 5, y + 5, 400, 480);
		panel = new JPanel();
		panel.setBorder(null);
		setContentPane(panel);
		SpringLayout contenedor = new SpringLayout();
		panel.setLayout(contenedor);
		
		JScrollPane scrollTabla = new JScrollPane();
		contenedor.putConstraint(SpringLayout.NORTH, scrollTabla, 50, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, scrollTabla, 10, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.SOUTH, scrollTabla, -50, SpringLayout.SOUTH, panel);
		contenedor.putConstraint(SpringLayout.EAST, scrollTabla, -10, SpringLayout.EAST, panel);
		panel.add(scrollTabla);
		tabla = new JTable();
		scrollTabla.setViewportView(tabla);
		
		btnImprimir = new JButton("Imprimir");
		contenedor.putConstraint(SpringLayout.SOUTH, btnImprimir, -10, SpringLayout.SOUTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, btnImprimir, 50, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.EAST, btnImprimir, 150, SpringLayout.WEST, panel);
		panel.add(btnImprimir);
		
		btnVolver = new JButton("Volver");
		contenedor.putConstraint(SpringLayout.SOUTH, btnVolver, -10, SpringLayout.SOUTH, panel);
		contenedor.putConstraint(SpringLayout.EAST, btnVolver, -50, SpringLayout.EAST, panel);
		contenedor.putConstraint(SpringLayout.WEST, btnVolver, -150, SpringLayout.EAST, panel);
		panel.add(btnVolver);
	}
}