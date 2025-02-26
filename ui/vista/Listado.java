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
	private SpringLayout contenedor = new SpringLayout();
	private JScrollPane scrollTabla;
	private JScrollPane scrollTabla1;
	public JComboBox<String> comboBoxAño;
	public JComboBox<String> comboBoxMes;
	public JComboBox<String> comboBoxTipo;
	public JComboBox<String> comboBoxPago;
	public JLabel lblCant;
	public JLabel lblSuma;
	public JLabel lblCotizacion;
	public JLabel lblDolar;
	public JLabel lblEuro;
	public JLabel lblTabla;
	public JLabel lblTabla1;
	public JTextField txtDolar;
	public JTextField txtEuro;
	public JTextField txtCant;
	public JTextField txtSuma;
	public JTextField txtBusqueda;
	public JTable tabla;
	public JTable tabla1;
	public JCheckBox chkBxFijo;
	public JCheckBox chkBxPesos;
	public JCheckBox chkBxDolares;
	public JCheckBox chkBxEuros;
	public JCheckBox chkBxDiferido;
	public JButton btnNuevo;
	public JButton btnCargar;
	public JButton btnCotizar;
	public JButton btnImprimir;
	public JButton btnVolver;
	public JButton btnGuardar;
	
	public Listado(String nombreVentana, int x, int y) {
		
		super(nombreVentana);
		panel = new JPanel();
		setContentPane(panel);
		setLocation(x + 10, y + 10);
		panel.setLayout(contenedor);
		setMinimumSize(new Dimension(1000, 600));
		setSize(1000, 600);
		
		comboBoxAño = new JComboBox<String>();
		contenedor.putConstraint(SpringLayout.NORTH, comboBoxAño, 10, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, comboBoxAño, 10, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.EAST, comboBoxAño, 70, SpringLayout.WEST, panel);
		panel.add(comboBoxAño);
		
		comboBoxMes = new JComboBox<String>();
		contenedor.putConstraint(SpringLayout.NORTH, comboBoxMes, 0, SpringLayout.NORTH, comboBoxAño);
		contenedor.putConstraint(SpringLayout.WEST, comboBoxMes, 10, SpringLayout.EAST, comboBoxAño);
		contenedor.putConstraint(SpringLayout.EAST, comboBoxMes, 110, SpringLayout.EAST, comboBoxAño);
		panel.add(comboBoxMes);
		
		comboBoxTipo = new JComboBox<String>();
		contenedor.putConstraint(SpringLayout.NORTH, comboBoxTipo, 0, SpringLayout.NORTH, comboBoxAño);
		contenedor.putConstraint(SpringLayout.WEST, comboBoxTipo, 20, SpringLayout.EAST, comboBoxMes);
		panel.add(comboBoxTipo);
			
		lblCotizacion = new JLabel("Cotización");
		contenedor.putConstraint(SpringLayout.NORTH, lblCotizacion, 0, SpringLayout.NORTH, comboBoxAño);
		contenedor.putConstraint(SpringLayout.WEST, lblCotizacion, 20, SpringLayout.EAST, comboBoxMes);
		contenedor.putConstraint(SpringLayout.SOUTH, lblCotizacion, 0, SpringLayout.SOUTH, comboBoxAño);
		panel.add(lblCotizacion);
		lblCotizacion.setVisible(false);
		
		lblDolar = new JLabel("U$S:");
		contenedor.putConstraint(SpringLayout.NORTH, lblDolar, 0, SpringLayout.NORTH, comboBoxAño);
		contenedor.putConstraint(SpringLayout.WEST, lblDolar, 20, SpringLayout.EAST, lblCotizacion);
		contenedor.putConstraint(SpringLayout.SOUTH, lblDolar, 0, SpringLayout.SOUTH, comboBoxAño);
		panel.add(lblDolar);
		lblDolar.setVisible(false);
		
		txtDolar = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtDolar, 0, SpringLayout.NORTH, comboBoxAño);
		contenedor.putConstraint(SpringLayout.WEST, txtDolar, 5, SpringLayout.EAST, lblDolar);
		contenedor.putConstraint(SpringLayout.SOUTH, txtDolar, 0, SpringLayout.SOUTH, comboBoxAño);
		txtDolar.setColumns(6);
		panel.add(txtDolar);
		txtDolar.setVisible(false);

		lblEuro = new JLabel("EURO:");
		contenedor.putConstraint(SpringLayout.NORTH, lblEuro, 0, SpringLayout.NORTH, comboBoxAño);
		contenedor.putConstraint(SpringLayout.WEST, lblEuro, 20, SpringLayout.EAST, txtDolar);
		contenedor.putConstraint(SpringLayout.SOUTH, lblEuro, 0, SpringLayout.SOUTH, comboBoxAño);
		panel.add(lblEuro);
		lblEuro.setVisible(false);
		
		txtEuro = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtEuro, 0, SpringLayout.NORTH, comboBoxAño);
		contenedor.putConstraint(SpringLayout.WEST, txtEuro, 5, SpringLayout.EAST, lblEuro);
		contenedor.putConstraint(SpringLayout.SOUTH, txtEuro, 0, SpringLayout.SOUTH, comboBoxAño);
		txtEuro.setColumns(6);
		panel.add(txtEuro);
		txtEuro.setVisible(false);

		comboBoxPago = new JComboBox<String>();
		contenedor.putConstraint(SpringLayout.NORTH, comboBoxPago, 0, SpringLayout.NORTH, comboBoxAño);
		contenedor.putConstraint(SpringLayout.WEST, comboBoxPago, 20, SpringLayout.EAST, comboBoxTipo);
		panel.add(comboBoxPago);

		chkBxFijo = new JCheckBox("Fijo");
		contenedor.putConstraint(SpringLayout.NORTH, chkBxFijo, 0, SpringLayout.NORTH, comboBoxAño);
		contenedor.putConstraint(SpringLayout.WEST, chkBxFijo, 20, SpringLayout.EAST, comboBoxPago);
		panel.add(chkBxFijo);
		chkBxFijo.setSelected(false);
		chkBxFijo.setVisible(false);
		
		lblCant = new JLabel("Cant.:");
		lblCant.setHorizontalAlignment(SwingConstants.RIGHT);
		contenedor.putConstraint(SpringLayout.NORTH, lblCant, 0, SpringLayout.NORTH, comboBoxAño);
		contenedor.putConstraint(SpringLayout.EAST, lblCant, -210, SpringLayout.EAST, panel);
		contenedor.putConstraint(SpringLayout.SOUTH, lblCant, 0, SpringLayout.SOUTH, comboBoxAño);
		panel.add(lblCant);
		
		txtCant = new JTextField();
		txtCant.setHorizontalAlignment(SwingConstants.RIGHT);
		contenedor.putConstraint(SpringLayout.NORTH, txtCant, 0, SpringLayout.NORTH, lblCant);
		contenedor.putConstraint(SpringLayout.WEST, txtCant, 2, SpringLayout.EAST, lblCant);
		contenedor.putConstraint(SpringLayout.SOUTH, txtCant, 0, SpringLayout.SOUTH, comboBoxAño);
		txtCant.setColumns(3);
		txtCant.setEditable(false);
		panel.add(txtCant);
		
		lblSuma = new JLabel("$");
		lblCant.setHorizontalAlignment(SwingConstants.RIGHT);
		contenedor.putConstraint(SpringLayout.NORTH, lblSuma, 0, SpringLayout.NORTH, txtCant);
		contenedor.putConstraint(SpringLayout.WEST, lblSuma, 20, SpringLayout.EAST, txtCant);
		contenedor.putConstraint(SpringLayout.SOUTH, lblSuma, 0, SpringLayout.SOUTH, txtCant);
		panel.add(lblSuma);
		
		txtSuma = new JTextField();
		txtSuma.setHorizontalAlignment(SwingConstants.RIGHT);
		contenedor.putConstraint(SpringLayout.NORTH, txtSuma, 0, SpringLayout.NORTH, lblSuma);
		contenedor.putConstraint(SpringLayout.WEST, txtSuma, 2, SpringLayout.EAST, lblSuma);
		contenedor.putConstraint(SpringLayout.SOUTH, txtSuma, 0, SpringLayout.SOUTH, comboBoxAño);
		txtSuma.setColumns(10);
		txtSuma.setEditable(false);
		panel.add(txtSuma);
		
		lblTabla = new JLabel("Movimientos");
		lblTabla.setHorizontalAlignment(SwingConstants.CENTER);
		contenedor.putConstraint(SpringLayout.NORTH, lblTabla, 50, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, lblTabla, 10, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.EAST, lblTabla, -110, SpringLayout.EAST, panel);
		panel.add(lblTabla);
		lblTabla.setVisible(false);
		
		scrollTabla = new JScrollPane();
		contenedor.putConstraint(SpringLayout.NORTH, scrollTabla, 50, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, scrollTabla, 10, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.SOUTH, scrollTabla, -10, SpringLayout.SOUTH, panel);
		contenedor.putConstraint(SpringLayout.EAST, scrollTabla, -110, SpringLayout.EAST, panel);
		panel.add(scrollTabla);
		tabla = new JTable();
		scrollTabla.setViewportView(tabla);
		
		lblTabla1 = new JLabel("Cotizaciones");
		lblTabla1.setHorizontalAlignment(SwingConstants.CENTER);
		contenedor.putConstraint(SpringLayout.NORTH, lblTabla1, 15, SpringLayout.SOUTH, scrollTabla);
		contenedor.putConstraint(SpringLayout.WEST, lblTabla1, 10, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.EAST, lblTabla1, -110, SpringLayout.EAST, panel);
		panel.add(lblTabla1);
		lblTabla1.setVisible(false);
		
		scrollTabla1 = new JScrollPane();
		contenedor.putConstraint(SpringLayout.NORTH, scrollTabla1, 10, SpringLayout.SOUTH, lblTabla1);
		contenedor.putConstraint(SpringLayout.WEST, scrollTabla1, 10, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.SOUTH, scrollTabla1, -10, SpringLayout.SOUTH, panel);
		contenedor.putConstraint(SpringLayout.EAST, scrollTabla1, -110, SpringLayout.EAST, panel);
		panel.add(scrollTabla1);
		tabla1 = new JTable();
		scrollTabla1.setViewportView(tabla1);
		scrollTabla1.setVisible(false);
		
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
		
		chkBxDiferido = new JCheckBox("Diferido");
		contenedor.putConstraint(SpringLayout.NORTH, chkBxDiferido, 15, SpringLayout.SOUTH, chkBxEuros);
		contenedor.putConstraint(SpringLayout.EAST, chkBxDiferido, 0, SpringLayout.EAST, btnNuevo);
		contenedor.putConstraint(SpringLayout.WEST, chkBxDiferido, 0, SpringLayout.WEST, btnNuevo);
		panel.add(chkBxDiferido);
		chkBxDiferido.setVisible(false);
		
		txtBusqueda = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtBusqueda, 15, SpringLayout.SOUTH, chkBxDiferido);
		contenedor.putConstraint(SpringLayout.WEST, txtBusqueda, 0, SpringLayout.WEST, chkBxDiferido);
		txtBusqueda.setColumns(7);
		panel.add(txtBusqueda);
	
		btnCotizar = new JButton("Cargar");
		contenedor.putConstraint(SpringLayout.NORTH, btnCotizar, 15, SpringLayout.SOUTH, chkBxDiferido);
		contenedor.putConstraint(SpringLayout.EAST, btnCotizar, 0, SpringLayout.EAST, btnNuevo);
		contenedor.putConstraint(SpringLayout.WEST, btnCotizar, 0, SpringLayout.WEST, btnNuevo);
		panel.add(btnCotizar);	
		btnCotizar.setVisible(false);
		
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
	
	public void setVisibleSegundaTabla() {

		contenedor.putConstraint(SpringLayout.NORTH, scrollTabla, 1, SpringLayout.SOUTH, lblTabla);
		contenedor.putConstraint(SpringLayout.SOUTH, scrollTabla, -200, SpringLayout.SOUTH, panel);
		lblTabla.setVisible(true);
		lblTabla1.setVisible(true);
		scrollTabla1.setVisible(true);
	}
	
	public void irFinalTabla() {
		
		scrollTabla.getHorizontalScrollBar().setValue(scrollTabla.getHorizontalScrollBar().getMaximum());
	}
	
	public void irFinalTabla1() {
		
		scrollTabla1.getHorizontalScrollBar().setValue(scrollTabla1.getHorizontalScrollBar().getMaximum());
	}
}