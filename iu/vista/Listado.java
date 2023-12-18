package vista;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class Listado extends VentanaModelo {

	private static final long serialVersionUID = 1L;
	private JPanel panel;
	public JComboBox<String> comboBoxAño;
	public JComboBox<String> comboBoxMes;
	public JComboBox<String> comboBoxTipo;
	public JComboBox<String> comboBoxPago;
	public JTextField txtCant;
	public JTextField txtSuma;
	public JTable tabla;
	public JButton btnNuevo;
	public JButton btnImprimir;
	public JButton btnVolver;
	
	public Listado(String nombreVentana, int x, int y) {
		
		super(nombreVentana);
		panel = new JPanel();
		setContentPane(panel);
		setLocation(x + 5, y + 5);
		setSize(1000, 600);
		SpringLayout contenedor = new SpringLayout();
		panel.setLayout(contenedor);
		
		comboBoxAño = new JComboBox<String>();
		contenedor.putConstraint(SpringLayout.NORTH, comboBoxAño, 10, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, comboBoxAño, 20, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.EAST, comboBoxAño, 80, SpringLayout.WEST, panel);
		panel.add(comboBoxAño);
		
		comboBoxMes = new JComboBox<String>();
		contenedor.putConstraint(SpringLayout.NORTH, comboBoxMes, 0, SpringLayout.NORTH, comboBoxAño);
		contenedor.putConstraint(SpringLayout.WEST, comboBoxMes, 20, SpringLayout.EAST, comboBoxAño);
		contenedor.putConstraint(SpringLayout.EAST, comboBoxMes, 110, SpringLayout.EAST, comboBoxAño);
		panel.add(comboBoxMes);
		
		comboBoxTipo = new JComboBox<String>();
		contenedor.putConstraint(SpringLayout.NORTH, comboBoxTipo, 0, SpringLayout.NORTH, comboBoxAño);
		contenedor.putConstraint(SpringLayout.WEST, comboBoxTipo, 20, SpringLayout.EAST, comboBoxMes);
		panel.add(comboBoxTipo);
			
		comboBoxPago = new JComboBox<String>();
		contenedor.putConstraint(SpringLayout.NORTH, comboBoxPago, 0, SpringLayout.NORTH, comboBoxAño);
		contenedor.putConstraint(SpringLayout.WEST, comboBoxPago, 20, SpringLayout.EAST, comboBoxTipo);
		panel.add(comboBoxPago);
		
		txtCant = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtCant, 0, SpringLayout.NORTH, comboBoxAño);
		contenedor.putConstraint(SpringLayout.EAST, txtCant, -210, SpringLayout.EAST, panel);
		txtCant.setColumns(10);
		txtCant.setEditable(false);
		panel.add(txtCant);
		
		txtSuma = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtSuma, 0, SpringLayout.NORTH, comboBoxAño);
		contenedor.putConstraint(SpringLayout.EAST, txtSuma, -50, SpringLayout.EAST, panel);
		txtSuma.setColumns(10);
		txtSuma.setEditable(false);
		panel.add(txtSuma);
		
		JScrollPane scrollTabla = new JScrollPane();
		contenedor.putConstraint(SpringLayout.NORTH, scrollTabla, 50, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, scrollTabla, 10, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.SOUTH, scrollTabla, -50, SpringLayout.SOUTH, panel);
		contenedor.putConstraint(SpringLayout.EAST, scrollTabla, -10, SpringLayout.EAST, panel);
		panel.add(scrollTabla);
		tabla = new JTable();
		scrollTabla.setViewportView(tabla);
		
		btnNuevo = new JButton("Nuevo");
		contenedor.putConstraint(SpringLayout.SOUTH, btnNuevo, -10, SpringLayout.SOUTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, btnNuevo, 50, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.EAST, btnNuevo, 150, SpringLayout.WEST, panel);
		panel.add(btnNuevo);	
		
		btnImprimir = new JButton("Imprimir");
		contenedor.putConstraint(SpringLayout.NORTH, btnImprimir, 0, SpringLayout.NORTH, btnNuevo);
		contenedor.putConstraint(SpringLayout.WEST, btnImprimir, 50, SpringLayout.EAST, btnNuevo);
		contenedor.putConstraint(SpringLayout.EAST, btnImprimir, 150, SpringLayout.EAST, btnNuevo);
		panel.add(btnImprimir);	
		
		btnVolver = new JButton("Volver");
		contenedor.putConstraint(SpringLayout.NORTH, btnVolver, 0, SpringLayout.NORTH, btnNuevo);
		contenedor.putConstraint(SpringLayout.EAST, btnVolver, -50, SpringLayout.EAST, panel);
		contenedor.putConstraint(SpringLayout.WEST, btnVolver, -150, SpringLayout.EAST, panel);
		panel.add(btnVolver);
	}
}