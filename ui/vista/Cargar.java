package vista;

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

public class Cargar extends VentanaModelo{

	private static final long serialVersionUID = 1L;
	private JPanel panel;
	public JCheckBox chkBoxAcreditacion;
	public JScrollPane scrollTabla;
	public JTable tabla;
	public JLabel msgError;
	public JLabel lblProv;
	public JLabel lblFormPago;
	public JLabel lblTipo;
	public JLabel lblMonto;
	public JLabel lblCotizacion;
	public JLabel lblInfoCot;
	public JLabel lblComentario;
	public JLabel lblAux1;
	public JTextField txtFecha;
	public JTextField txtProv;
	public JTextField txtPago;
	public JTextField txtTipo;
	public JTextField txtMonto;
	public JTextField txtMoneda;
	public JTextField txtCotizacion;
	public JTextField txtComentario;
	public JTextField txtAux1;
	public JComboBox<String> cmbBxPago;
	public JComboBox<String> cmbBxTipo;
	public JComboBox<String> cmbBxMoneda;
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
		contenedor.putConstraint(SpringLayout.EAST, lblFecha, 110, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.SOUTH, lblFecha, 40, SpringLayout.NORTH, panel);
		panel.add(lblFecha);
		
		txtFecha = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtFecha, 0, SpringLayout.NORTH, lblFecha);
		contenedor.putConstraint(SpringLayout.WEST, txtFecha, 10, SpringLayout.EAST, lblFecha);
		panel.add(txtFecha);
		txtFecha.setColumns(6);
		
		chkBoxAcreditacion = new JCheckBox("Acreditación");
		contenedor.putConstraint(SpringLayout.NORTH, chkBoxAcreditacion, 0, SpringLayout.NORTH, lblFecha);
		contenedor.putConstraint(SpringLayout.WEST, chkBoxAcreditacion, 10, SpringLayout.EAST, txtFecha);
		contenedor.putConstraint(SpringLayout.SOUTH, chkBoxAcreditacion, 0, SpringLayout.SOUTH, lblFecha);
		chkBoxAcreditacion.setVisible(false);
		panel.add(chkBoxAcreditacion);
		
		lblProv = new JLabel("Proveedor:");
		contenedor.putConstraint(SpringLayout.NORTH, lblProv, 20, SpringLayout.SOUTH, lblFecha);
		contenedor.putConstraint(SpringLayout.WEST, lblProv, 0, SpringLayout.WEST, lblFecha);
		contenedor.putConstraint(SpringLayout.EAST, lblProv, 0, SpringLayout.EAST, lblFecha);
		contenedor.putConstraint(SpringLayout.SOUTH, lblProv, 40, SpringLayout.SOUTH, lblFecha);
		panel.add(lblProv);
		
		txtProv = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtProv, 0, SpringLayout.NORTH, lblProv);
		contenedor.putConstraint(SpringLayout.WEST, txtProv, 10, SpringLayout.EAST, lblProv);
		panel.add(txtProv);
		txtProv.setColumns(20);
		
		lblFormPago = new JLabel("Forma de pago:");
		contenedor.putConstraint(SpringLayout.NORTH, lblFormPago, 20, SpringLayout.SOUTH, lblProv);
		contenedor.putConstraint(SpringLayout.WEST, lblFormPago, 0, SpringLayout.WEST, lblProv);
		contenedor.putConstraint(SpringLayout.EAST, lblFormPago, 0, SpringLayout.EAST, lblProv);
		contenedor.putConstraint(SpringLayout.SOUTH, lblFormPago, 40, SpringLayout.SOUTH, lblProv);
		panel.add(lblFormPago);		
		
		cmbBxPago = new JComboBox<String>();
		contenedor.putConstraint(SpringLayout.NORTH, cmbBxPago, 0, SpringLayout.NORTH, lblFormPago);
		contenedor.putConstraint(SpringLayout.WEST, cmbBxPago, 10, SpringLayout.EAST, lblFormPago);
		contenedor.putConstraint(SpringLayout.EAST, cmbBxPago, 230, SpringLayout.EAST, lblFormPago);
		contenedor.putConstraint(SpringLayout.SOUTH, cmbBxPago, 20, SpringLayout.NORTH, lblFormPago);
		panel.add(cmbBxPago);
		
		txtPago = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtPago, 0, SpringLayout.NORTH, lblFormPago);
		contenedor.putConstraint(SpringLayout.WEST, txtPago, 10, SpringLayout.EAST, lblFormPago);
		panel.add(txtPago);
		txtPago.setColumns(20);
		txtPago.setVisible(false);
		
		lblTipo = new JLabel("Destino:");
		contenedor.putConstraint(SpringLayout.NORTH, lblTipo, 20, SpringLayout.SOUTH, lblFormPago);
		contenedor.putConstraint(SpringLayout.WEST, lblTipo, 0, SpringLayout.WEST, lblFormPago);
		contenedor.putConstraint(SpringLayout.EAST, lblTipo, 0, SpringLayout.EAST, lblFormPago);
		contenedor.putConstraint(SpringLayout.SOUTH, lblTipo, 40, SpringLayout.SOUTH, lblFormPago);
		panel.add(lblTipo);		
		
		cmbBxTipo = new JComboBox<String>();
		contenedor.putConstraint(SpringLayout.NORTH, cmbBxTipo, 0, SpringLayout.NORTH, lblTipo);
		contenedor.putConstraint(SpringLayout.WEST, cmbBxTipo, 10, SpringLayout.EAST, lblTipo);
		contenedor.putConstraint(SpringLayout.EAST, cmbBxTipo, 230, SpringLayout.EAST, lblTipo);
		contenedor.putConstraint(SpringLayout.SOUTH, cmbBxTipo, 20, SpringLayout.NORTH, lblTipo);
		panel.add(cmbBxTipo);
		
		txtTipo = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtTipo, 0, SpringLayout.NORTH, lblTipo);
		contenedor.putConstraint(SpringLayout.WEST, txtTipo, 10, SpringLayout.EAST, lblTipo);
		panel.add(txtTipo);
		txtTipo.setColumns(20);
		txtTipo.setVisible(false);
		
		lblMonto = new JLabel("Monto:");
		contenedor.putConstraint(SpringLayout.NORTH, lblMonto, 20, SpringLayout.SOUTH, lblTipo);
		contenedor.putConstraint(SpringLayout.WEST, lblMonto, 0, SpringLayout.WEST, lblTipo);
		contenedor.putConstraint(SpringLayout.EAST, lblMonto, 0, SpringLayout.EAST, lblTipo);
		contenedor.putConstraint(SpringLayout.SOUTH, lblMonto, 40, SpringLayout.SOUTH, lblTipo);
		panel.add(lblMonto);
		
		txtMonto = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtMonto, 0, SpringLayout.NORTH, lblMonto);
		contenedor.putConstraint(SpringLayout.WEST, txtMonto, 10, SpringLayout.EAST, lblMonto);
		panel.add(txtMonto);
		txtMonto.setColumns(8);
		
		cmbBxMoneda = new JComboBox<String>(new String[] {"Pesos", "Dólares", "Euros"});
		contenedor.putConstraint(SpringLayout.NORTH, cmbBxMoneda, 0, SpringLayout.NORTH, txtMonto);
		contenedor.putConstraint(SpringLayout.WEST, cmbBxMoneda, 10, SpringLayout.EAST, txtMonto);
		contenedor.putConstraint(SpringLayout.EAST, cmbBxMoneda, 130, SpringLayout.EAST, txtMonto);
		contenedor.putConstraint(SpringLayout.SOUTH, cmbBxMoneda, 20, SpringLayout.NORTH, txtMonto);
		panel.add(cmbBxMoneda);
		
		txtMoneda = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtMoneda, 0, SpringLayout.NORTH, txtMonto);
		contenedor.putConstraint(SpringLayout.WEST, txtMoneda, 10, SpringLayout.EAST, txtMonto);
		panel.add(txtMoneda);
		txtMoneda.setColumns(7);
		txtMoneda.setVisible(false);
		
		lblCotizacion = new JLabel("Cotización:");
		contenedor.putConstraint(SpringLayout.NORTH, lblCotizacion, 20, SpringLayout.SOUTH, lblMonto);
		contenedor.putConstraint(SpringLayout.WEST, lblCotizacion, 0, SpringLayout.WEST, lblMonto);
		contenedor.putConstraint(SpringLayout.EAST, lblCotizacion, 0, SpringLayout.EAST, lblMonto);
		contenedor.putConstraint(SpringLayout.SOUTH, lblCotizacion, 40, SpringLayout.SOUTH, lblMonto);
		panel.add(lblCotizacion);
		
		txtCotizacion = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtCotizacion, 0, SpringLayout.NORTH, lblCotizacion);
		contenedor.putConstraint(SpringLayout.WEST, txtCotizacion, 10, SpringLayout.EAST, lblCotizacion);
		panel.add(txtCotizacion);
		txtCotizacion.setColumns(7);
		txtCotizacion.setEditable(false);
		
		lblInfoCot= new JLabel();
		contenedor.putConstraint(SpringLayout.NORTH, lblInfoCot, 0, SpringLayout.NORTH, txtCotizacion);
		contenedor.putConstraint(SpringLayout.WEST, lblInfoCot, 10, SpringLayout.EAST, txtCotizacion);
		contenedor.putConstraint(SpringLayout.SOUTH, lblInfoCot, 0, SpringLayout.SOUTH, txtCotizacion);
		panel.add(lblInfoCot);
		
		lblComentario = new JLabel("Comentario:");
		contenedor.putConstraint(SpringLayout.NORTH, lblComentario, 20, SpringLayout.SOUTH, lblCotizacion);
		contenedor.putConstraint(SpringLayout.WEST, lblComentario, 0, SpringLayout.WEST, lblCotizacion);
		contenedor.putConstraint(SpringLayout.EAST, lblComentario, 0, SpringLayout.EAST, lblCotizacion);
		contenedor.putConstraint(SpringLayout.SOUTH, lblComentario, 40, SpringLayout.SOUTH, lblCotizacion);
		panel.add(lblComentario);
		
		txtComentario = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtComentario, 0, SpringLayout.NORTH, lblComentario);
		contenedor.putConstraint(SpringLayout.WEST, txtComentario, 10, SpringLayout.EAST, lblComentario);
		panel.add(txtComentario);
		txtComentario.setColumns(20);

		lblAux1 = new JLabel();
		contenedor.putConstraint(SpringLayout.NORTH, lblAux1, 20, SpringLayout.SOUTH, lblComentario);
		contenedor.putConstraint(SpringLayout.WEST, lblAux1, 0, SpringLayout.WEST, lblComentario);
		contenedor.putConstraint(SpringLayout.EAST, lblAux1, 0, SpringLayout.EAST, lblComentario);
		contenedor.putConstraint(SpringLayout.SOUTH, lblAux1, 40, SpringLayout.SOUTH, lblComentario);
		panel.add(lblAux1);
		
		txtAux1 = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtAux1, 0, SpringLayout.NORTH, lblAux1);
		contenedor.putConstraint(SpringLayout.WEST, txtAux1, 10, SpringLayout.EAST, lblAux1);
		panel.add(txtAux1);
		txtAux1.setColumns(20);
		txtAux1.setVisible(false);

		scrollTabla = new JScrollPane();
		contenedor.putConstraint(SpringLayout.NORTH, scrollTabla, 0, SpringLayout.NORTH, lblFecha);
		contenedor.putConstraint(SpringLayout.WEST, scrollTabla, 10, SpringLayout.EAST, txtProv);
		contenedor.putConstraint(SpringLayout.SOUTH, scrollTabla, 0, SpringLayout.SOUTH, lblAux1);
		contenedor.putConstraint(SpringLayout.EAST, scrollTabla, -25, SpringLayout.EAST, panel);
		panel.add(scrollTabla);
		tabla = new JTable();
		scrollTabla.setViewportView(tabla); 

		msgError = new JLabel();
		msgError.setHorizontalAlignment(SwingConstants.CENTER);
		contenedor.putConstraint(SpringLayout.NORTH, msgError, 20, SpringLayout.SOUTH, scrollTabla);
		contenedor.putConstraint(SpringLayout.WEST, msgError, 10, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.EAST, msgError, -50, SpringLayout.EAST, panel);
		contenedor.putConstraint(SpringLayout.SOUTH, msgError, 40, SpringLayout.SOUTH, scrollTabla);
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