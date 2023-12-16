package vista;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

public class Cargar extends VentanaModelo{

	private static final long serialVersionUID = 1L;
	private JPanel panel;
	public JTable tabla;
	public JLabel msgError;
	public JTextField txtFecha;
	public JTextField txtProv;
	public JTextField txtMonto;
	public JComboBox<String> comboBoxPago;
	public JComboBox<String> comboBoxTipo;
	public JButton btnGuardar;
	public JButton btnNuevo;
	public JButton btnVolver;
		
	public Cargar(String nombreVentana, int x, int y) {
		
		super(nombreVentana);
		setResizable(true);
		setBounds(x + 5, y + 5, 640, 480);
		panel = new JPanel();
		panel.setBorder(null);
		setContentPane(panel);
		SpringLayout contenedor = new SpringLayout();
		panel.setLayout(contenedor);

		JLabel lblFecha = new JLabel("Fecha:");
		contenedor.putConstraint(SpringLayout.NORTH, lblFecha, 20, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, lblFecha, 20, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.EAST, lblFecha, 100, SpringLayout.WEST, panel);
		panel.add(lblFecha);
		
		txtFecha = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtFecha, 0, SpringLayout.NORTH, lblFecha);
		contenedor.putConstraint(SpringLayout.WEST, txtFecha, 10, SpringLayout.EAST, lblFecha);
		panel.add(txtFecha);
		txtFecha.setColumns(20);
		
		JLabel lblProv = new JLabel("Proveedor:");
		contenedor.putConstraint(SpringLayout.NORTH, lblProv, 20, SpringLayout.SOUTH, lblFecha);
		contenedor.putConstraint(SpringLayout.WEST, lblProv, 0, SpringLayout.WEST, lblFecha);
		contenedor.putConstraint(SpringLayout.EAST, lblProv, 0, SpringLayout.EAST, lblFecha);
		panel.add(lblProv);
		
		txtProv = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtProv, 0, SpringLayout.NORTH, lblProv);
		contenedor.putConstraint(SpringLayout.WEST, txtProv, 10, SpringLayout.EAST, lblProv);
		panel.add(txtProv);
		txtProv.setColumns(20);
		
		JLabel lblFormPago = new JLabel("Forma de pago:");
		contenedor.putConstraint(SpringLayout.NORTH, lblFormPago, 20, SpringLayout.SOUTH, lblProv);
		contenedor.putConstraint(SpringLayout.WEST, lblFormPago, 0, SpringLayout.WEST, lblProv);
		contenedor.putConstraint(SpringLayout.EAST, lblFormPago, 0, SpringLayout.EAST, lblProv);
		panel.add(lblFormPago);		
		
		comboBoxPago = new JComboBox<String>();
		contenedor.putConstraint(SpringLayout.NORTH, comboBoxPago, 0, SpringLayout.NORTH, lblFormPago);
		contenedor.putConstraint(SpringLayout.WEST, comboBoxPago, 10, SpringLayout.EAST, lblFormPago);
		contenedor.putConstraint(SpringLayout.EAST, comboBoxPago, 135, SpringLayout.EAST, lblFormPago);
		panel.add(comboBoxPago);
		
		JLabel lblTipo = new JLabel("Destino:");
		contenedor.putConstraint(SpringLayout.NORTH, lblTipo, 20, SpringLayout.SOUTH, lblFormPago);
		contenedor.putConstraint(SpringLayout.WEST, lblTipo, 0, SpringLayout.WEST, lblFormPago);
		contenedor.putConstraint(SpringLayout.EAST, lblTipo, 0, SpringLayout.EAST, lblFormPago);
		panel.add(lblTipo);		
		
		comboBoxTipo = new JComboBox<String>();
		contenedor.putConstraint(SpringLayout.NORTH, comboBoxTipo, 0, SpringLayout.NORTH, lblTipo);
		contenedor.putConstraint(SpringLayout.WEST, comboBoxTipo, 10, SpringLayout.EAST, lblTipo);
		contenedor.putConstraint(SpringLayout.EAST, comboBoxTipo, 135, SpringLayout.EAST, lblTipo);
		panel.add(comboBoxTipo);
		
		JLabel lblMonto = new JLabel("Monto:");
		contenedor.putConstraint(SpringLayout.NORTH, lblMonto, 20, SpringLayout.SOUTH, lblTipo);
		contenedor.putConstraint(SpringLayout.WEST, lblMonto, 0, SpringLayout.WEST, lblTipo);
		contenedor.putConstraint(SpringLayout.EAST, lblMonto, 0, SpringLayout.EAST, lblTipo);
		panel.add(lblMonto);
		
		txtMonto = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtMonto, 0, SpringLayout.NORTH, lblMonto);
		contenedor.putConstraint(SpringLayout.WEST, txtMonto, 10, SpringLayout.EAST, lblMonto);
		panel.add(txtMonto);
		txtMonto.setColumns(7);

		JScrollPane scrollTabla = new JScrollPane();
		contenedor.putConstraint(SpringLayout.NORTH, scrollTabla, 0, SpringLayout.NORTH, txtProv);
		contenedor.putConstraint(SpringLayout.WEST, scrollTabla, 10, SpringLayout.EAST, txtProv);
		contenedor.putConstraint(SpringLayout.SOUTH, scrollTabla, 340, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.EAST, scrollTabla, -25, SpringLayout.EAST, panel);
		panel.add(scrollTabla);
		tabla = new JTable();
		scrollTabla.setViewportView(tabla); 

		msgError = new JLabel();
		msgError.setHorizontalAlignment(SwingConstants.CENTER);
		contenedor.putConstraint(SpringLayout.NORTH, msgError, 10, SpringLayout.SOUTH, scrollTabla);
		contenedor.putConstraint(SpringLayout.WEST, msgError, 10, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.EAST, msgError, -50, SpringLayout.EAST, panel);
		panel.add(msgError);
		
		btnGuardar = new JButton("Guardar");
		contenedor.putConstraint(SpringLayout.SOUTH, btnGuardar, -10, SpringLayout.SOUTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, btnGuardar, 50, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.EAST, btnGuardar, 150, SpringLayout.WEST, panel);
		panel.add(btnGuardar);
		
		btnNuevo = new JButton("Nuevo");
		contenedor.putConstraint(SpringLayout.NORTH, btnNuevo, 0, SpringLayout.NORTH, btnGuardar);
		contenedor.putConstraint(SpringLayout.WEST, btnNuevo, 50, SpringLayout.EAST, btnGuardar);
		contenedor.putConstraint(SpringLayout.EAST, btnNuevo, 150, SpringLayout.EAST, btnGuardar);
		panel.add(btnNuevo);	
		
		btnVolver = new JButton("Volver");
		contenedor.putConstraint(SpringLayout.NORTH, btnVolver, 0, SpringLayout.NORTH, btnGuardar);
		contenedor.putConstraint(SpringLayout.EAST, btnVolver, -50, SpringLayout.EAST, panel);
		contenedor.putConstraint(SpringLayout.WEST, btnVolver, -150, SpringLayout.EAST, panel);
		panel.add(btnVolver);
	}
}