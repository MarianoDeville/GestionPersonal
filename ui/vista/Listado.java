package vista;

import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

public class Listado extends VentanaModelo {

	private static final long serialVersionUID = 1L;
	private JPanel panel;
	public JComboBox<String> comboBoxAño;
	public JComboBox<String> comboBoxMes;
	public JComboBox<String> comboBoxTipo;
	public JComboBox<String> comboBoxPago;
	public JTextField txtCant;
	public JTextField txtSuma;
	public JTextField txtBusqueda;
	public JTable tabla;
	public JCheckBox chkBxPesos;
	public JCheckBox chkBxDolares;
	public JCheckBox chkBxEuros;
	public JButton btnNuevo;
	public JButton btnCargar;
	public JButton btnImprimir;
	public JButton btnVolver;
	public JButton btnGuardar;
	
	public Listado(String nombreVentana, int x, int y) {
		
		super(nombreVentana);
		panel = new JPanel();
		setContentPane(panel);
		setLocation(x + 5, y + 5);
		SpringLayout contenedor = new SpringLayout();
		panel.setLayout(contenedor);
		setMinimumSize(new Dimension(950, 600));
		setSize(950, 600);
		
		comboBoxAño = new JComboBox<String>();
		contenedor.putConstraint(SpringLayout.NORTH, comboBoxAño, 10, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, comboBoxAño, 10, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.EAST, comboBoxAño, 75, SpringLayout.WEST, panel);
		panel.add(comboBoxAño);
		
		comboBoxMes = new JComboBox<String>();
		contenedor.putConstraint(SpringLayout.NORTH, comboBoxMes, 0, SpringLayout.NORTH, comboBoxAño);
		contenedor.putConstraint(SpringLayout.WEST, comboBoxMes, 20, SpringLayout.EAST, comboBoxAño);
		contenedor.putConstraint(SpringLayout.EAST, comboBoxMes, 115, SpringLayout.EAST, comboBoxAño);
		panel.add(comboBoxMes);
		
		comboBoxTipo = new JComboBox<String>();
		contenedor.putConstraint(SpringLayout.NORTH, comboBoxTipo, 0, SpringLayout.NORTH, comboBoxAño);
		contenedor.putConstraint(SpringLayout.WEST, comboBoxTipo, 20, SpringLayout.EAST, comboBoxMes);
		panel.add(comboBoxTipo);
			
		comboBoxPago = new JComboBox<String>();
		contenedor.putConstraint(SpringLayout.NORTH, comboBoxPago, 0, SpringLayout.NORTH, comboBoxAño);
		contenedor.putConstraint(SpringLayout.WEST, comboBoxPago, 20, SpringLayout.EAST, comboBoxTipo);
		panel.add(comboBoxPago);
		
		JLabel lblCant = new JLabel("Cant.:");
		lblCant.setHorizontalAlignment(SwingConstants.RIGHT);
		contenedor.putConstraint(SpringLayout.NORTH, lblCant, 0, SpringLayout.NORTH, comboBoxAño);
		contenedor.putConstraint(SpringLayout.EAST, lblCant, -210, SpringLayout.EAST, panel);
		contenedor.putConstraint(SpringLayout.SOUTH, lblCant, 0, SpringLayout.SOUTH, comboBoxAño);
		panel.add(lblCant);
		
		txtCant = new JTextField();
		txtCant.setHorizontalAlignment(SwingConstants.RIGHT);
		contenedor.putConstraint(SpringLayout.NORTH, txtCant, 0, SpringLayout.NORTH, lblCant);
		contenedor.putConstraint(SpringLayout.WEST, txtCant, 2, SpringLayout.EAST, lblCant);
		txtCant.setColumns(3);
		txtCant.setEditable(false);
		panel.add(txtCant);
		
		JLabel lblSuma = new JLabel("$");
		lblCant.setHorizontalAlignment(SwingConstants.RIGHT);
		contenedor.putConstraint(SpringLayout.NORTH, lblSuma, 0, SpringLayout.NORTH, txtCant);
		contenedor.putConstraint(SpringLayout.WEST, lblSuma, 20, SpringLayout.EAST, txtCant);
		contenedor.putConstraint(SpringLayout.SOUTH, lblSuma, 0, SpringLayout.SOUTH, txtCant);
		panel.add(lblSuma);
		
		txtSuma = new JTextField();
		txtSuma.setHorizontalAlignment(SwingConstants.RIGHT);
		contenedor.putConstraint(SpringLayout.NORTH, txtSuma, 0, SpringLayout.NORTH, lblSuma);
		contenedor.putConstraint(SpringLayout.WEST, txtSuma, 2, SpringLayout.EAST, lblSuma);
		txtSuma.setColumns(10);
		txtSuma.setEditable(false);
		panel.add(txtSuma);
		
		JScrollPane scrollTabla = new JScrollPane();
		contenedor.putConstraint(SpringLayout.NORTH, scrollTabla, 50, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, scrollTabla, 10, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.SOUTH, scrollTabla, -10, SpringLayout.SOUTH, panel);
		contenedor.putConstraint(SpringLayout.EAST, scrollTabla, -110, SpringLayout.EAST, panel);
		panel.add(scrollTabla);
		tabla = new JTable();
		scrollTabla.setViewportView(tabla);
		
		btnNuevo = new JButton("Nuevo");
		contenedor.putConstraint(SpringLayout.NORTH, btnNuevo, 50, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.EAST, btnNuevo, -10, SpringLayout.EAST, panel);
		contenedor.putConstraint(SpringLayout.WEST, btnNuevo, -100, SpringLayout.EAST, panel);
		panel.add(btnNuevo);
		
		btnCargar = new JButton("Cargar");
		contenedor.putConstraint(SpringLayout.NORTH, btnCargar, 15, SpringLayout.SOUTH, btnNuevo);
		contenedor.putConstraint(SpringLayout.EAST, btnCargar, 0, SpringLayout.EAST, btnNuevo);
		contenedor.putConstraint(SpringLayout.WEST, btnCargar, 0, SpringLayout.WEST, btnNuevo);
		panel.add(btnCargar);
		btnCargar.setVisible(false);
		
		chkBxPesos = new JCheckBox("Pesos");
		contenedor.putConstraint(SpringLayout.NORTH, chkBxPesos, 15, SpringLayout.SOUTH, btnCargar);
		contenedor.putConstraint(SpringLayout.EAST, chkBxPesos, 0, SpringLayout.EAST, btnNuevo);
		contenedor.putConstraint(SpringLayout.WEST, chkBxPesos, 0, SpringLayout.WEST, btnNuevo);
		panel.add(chkBxPesos);
		chkBxPesos.setSelected(true);

		chkBxDolares = new JCheckBox("Dólares");
		contenedor.putConstraint(SpringLayout.NORTH, chkBxDolares, 15, SpringLayout.SOUTH, chkBxPesos);
		contenedor.putConstraint(SpringLayout.EAST, chkBxDolares, 0, SpringLayout.EAST, btnNuevo);
		contenedor.putConstraint(SpringLayout.WEST, chkBxDolares, 0, SpringLayout.WEST, btnNuevo);
		panel.add(chkBxDolares);
		
		chkBxEuros = new JCheckBox("Euros");
		contenedor.putConstraint(SpringLayout.NORTH, chkBxEuros, 15, SpringLayout.SOUTH, chkBxDolares);
		contenedor.putConstraint(SpringLayout.EAST, chkBxEuros, 0, SpringLayout.EAST, btnNuevo);
		contenedor.putConstraint(SpringLayout.WEST, chkBxEuros, 0, SpringLayout.WEST, btnNuevo);
		panel.add(chkBxEuros);
		
		txtBusqueda = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtBusqueda, 15, SpringLayout.SOUTH, chkBxEuros);
		contenedor.putConstraint(SpringLayout.WEST, txtBusqueda, 0, SpringLayout.WEST, chkBxEuros);
		txtBusqueda.setColumns(7);
		panel.add(txtBusqueda);
		
		btnImprimir = new JButton("Imprimir");
		contenedor.putConstraint(SpringLayout.NORTH, btnImprimir, 15, SpringLayout.SOUTH, txtBusqueda);
		contenedor.putConstraint(SpringLayout.EAST, btnImprimir, 0, SpringLayout.EAST, btnNuevo);
		contenedor.putConstraint(SpringLayout.WEST, btnImprimir, 0, SpringLayout.WEST, btnNuevo);
		panel.add(btnImprimir);	
		
		btnVolver = new JButton("Volver");
		contenedor.putConstraint(SpringLayout.SOUTH, btnVolver, -10, SpringLayout.SOUTH, panel);
		contenedor.putConstraint(SpringLayout.EAST, btnVolver, 0, SpringLayout.EAST, btnNuevo);
		contenedor.putConstraint(SpringLayout.WEST, btnVolver, 0, SpringLayout.WEST, btnNuevo);
		panel.add(btnVolver);
		
		btnGuardar = new JButton("Guardar");
		contenedor.putConstraint(SpringLayout.SOUTH, btnGuardar, -15, SpringLayout.NORTH, btnVolver);
		contenedor.putConstraint(SpringLayout.EAST, btnGuardar, 0, SpringLayout.EAST, btnNuevo);
		contenedor.putConstraint(SpringLayout.WEST, btnGuardar, 0, SpringLayout.WEST, btnNuevo);
		panel.add(btnGuardar);
		btnGuardar.setVisible(false);
	}
}