package control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import modelo.DtosEgresos;
import vista.Cargar;

public class CtrlEditarEgreso implements ActionListener {
	
	private Cargar ventana;
	private DtosEgresos dtosEgreso;
	private int elemento = -1;

	public CtrlEditarEgreso(Cargar vista) {
		
		this.ventana = vista;
		this.dtosEgreso = new DtosEgresos();
		this.ventana.cmbBxTipo.addActionListener(this);
		this.ventana.cmbBxPago.addActionListener(this);
		this.ventana.cmbBxMoneda.addActionListener(this);
		this.ventana.btnGuardar.addActionListener(this);
		this.ventana.btnNuevo.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
		this.ventana.txtProv.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				actualizar();
			}
		});
		this.ventana.tabla.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        if(e.getClickCount() == 2) {
		        	
		        	elemento = ventana.tabla.getSelectedRow();
		        	actualizar();
		        }
		    }
		});
	}
	
	public void iniciar() {

		ventana.btnNuevo.setText("Borrar");
		ventana.txtFecha.setText(dtosEgreso.getFecha());
		ventana.txtProv.setText(dtosEgreso.getProveedor());
		ventana.cmbBxPago.setModel(new DefaultComboBoxModel<String>(dtosEgreso.getFormasPago("Seleccione un método de pago.")));
		ventana.cmbBxPago.setSelectedItem(dtosEgreso.getFormaPagoSeleccionada());
		ventana.cmbBxTipo.setModel(new DefaultComboBoxModel<String>(dtosEgreso.getListaDestinos("Seleccione una opción.")));
		ventana.cmbBxTipo.setSelectedItem(dtosEgreso.getDestinoConsumo());
		ventana.txtMonto.setText(dtosEgreso.getMonto());
		ventana.cmbBxMoneda.setSelectedItem(dtosEgreso.getMoneda());
		ventana.txtComentario.setText(dtosEgreso.getComentario());
		
		if(!dtosEgreso.getMoneda().equals("Pesos"))
			ventana.txtCotizacion.setText(dtosEgreso.getCotizacion());
		ventana.tabla.setDefaultEditor(Object.class, null);
		actualizar();
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		
		if(e.getSource() == ventana.txtProv) {
			
			actualizar();
		}
		
		if(e.getSource() == ventana.cmbBxMoneda) {

			moneda();
		}
		
		if(e.getSource() == ventana.btnGuardar) {
			
			guardar();
		}
		
		if(e.getSource() == ventana.btnNuevo) {
			
			borrar();
		}
		
		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
	}
	
	private void actualizar() {

		if(elemento != -1 ) {
			
			ventana.txtProv.setText((String)ventana.tabla.getValueAt(elemento, 0));
			dtosEgreso.setProveedor(elemento);
			elemento = -1;
		}
		ventana.tabla.setModel(dtosEgreso.getListaProveedores(ventana.txtProv.getText()));
	}
	
	private void moneda() {

		if(ventana.cmbBxMoneda.getSelectedItem().equals("Pesos"))
			ventana.txtCotizacion.setEditable(false);
		else
			ventana.txtCotizacion.setEditable(true);
	}
	
	private void guardar() {
		
		dtosEgreso.setMoneda((String)ventana.cmbBxMoneda.getSelectedItem());
		dtosEgreso.setComentario(ventana.txtComentario.getText());
		
		if(dtosEgreso.setFecha(ventana.txtFecha.getText()) && 
				dtosEgreso.setDestino(ventana.cmbBxTipo.getSelectedIndex()) && 
				dtosEgreso.setFormaPago(ventana.cmbBxPago.getSelectedIndex()) && 
				dtosEgreso.setMonto(ventana.txtMonto.getText()) && 
				dtosEgreso.setCotizacion(ventana.txtCotizacion.getText()) && 
				dtosEgreso.actualizarEgreso()) {
			
			ventana.msgError.setForeground(Color.BLUE);
			ventana.msgError.setText(dtosEgreso.getMsgError());
			ventana.btnGuardar.setEnabled(false);
			ventana.btnNuevo.setEnabled(false);
			return;	
		}
		ventana.msgError.setForeground(Color.RED);
		ventana.msgError.setText(dtosEgreso.getMsgError());
	}
	
	private void borrar() {
		
		if(JOptionPane.showConfirmDialog(ventana, "esta seguro?", "Se borrará definitivamente de la base de datos", JOptionPane.YES_NO_OPTION) != 1) {
			
			if(dtosEgreso.borrarEgreso()) {
				
				ventana.msgError.setForeground(Color.BLUE);
				ventana.msgError.setText(dtosEgreso.getMsgError());
				ventana.btnGuardar.setEnabled(false);
				ventana.btnNuevo.setEnabled(false);
				return;	
			}
			ventana.msgError.setForeground(Color.RED);
			ventana.msgError.setText(dtosEgreso.getMsgError());
		}
	}
}