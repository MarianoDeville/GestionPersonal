package control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultComboBoxModel;
import modelo.DtosComunes;
import modelo.DtosIngresos;
import vista.Cargar;

public class CtrlCargarIngreso implements ActionListener {

	private Cargar ventana;
	private DtosIngresos dtosIngreso;
	private int elemento = -1;

	public CtrlCargarIngreso(Cargar vista) {
		
		this.ventana = vista;
		this.dtosIngreso = new DtosIngresos();
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

		ventana.btnNuevo.setEnabled(false);
		ventana.lblProv.setText("Fuente:");
		ventana.lblTipo.setText("Concepto:");
		ventana.txtFecha.setText(DtosComunes.getFechaActual());
		ventana.cmbBxPago.setModel(new DefaultComboBoxModel<String>(dtosIngreso.getFormasCobro("Seleccione un método de pago.")));
		ventana.cmbBxPago.setSelectedIndex(0);
		ventana.cmbBxTipo.setModel(new DefaultComboBoxModel<String>(dtosIngreso.getListaConceptos("Seleccione una opción.")));
		ventana.cmbBxTipo.setSelectedIndex(0);
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
			
			limpiar();
		}
		
		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
	}
	
	private void actualizar() {

		if(elemento != -1 ) {
			
			ventana.txtProv.setText((String)ventana.tabla.getValueAt(elemento, 0));
			dtosIngreso.setFuente(elemento);
			elemento = -1;
		}
		ventana.tabla.setModel(dtosIngreso.getListaFuentes(ventana.txtProv.getText()));
	}
	
	private void moneda() {

		if(ventana.cmbBxMoneda.getSelectedItem().equals("Pesos"))
			ventana.txtCotizacion.setEditable(false);
		else
			ventana.txtCotizacion.setEditable(true);
	}
	
	private void guardar() {
		
		dtosIngreso.setMoneda((String)ventana.cmbBxMoneda.getSelectedItem());
		dtosIngreso.setComentario(ventana.txtComentario.getText());
			
		if(dtosIngreso.setFecha(ventana.txtFecha.getText()) && 
				dtosIngreso.setDestino(ventana.cmbBxTipo.getSelectedIndex()) && 
				dtosIngreso.setFormaPago(ventana.cmbBxPago.getSelectedIndex()) && 
				dtosIngreso.setMonto(ventana.txtMonto.getText()) &&
				dtosIngreso.setCotizacion(ventana.txtCotizacion.getText()) && 
				dtosIngreso.guardarIngreso()) {
			
			ventana.msgError.setForeground(Color.BLUE);
			ventana.msgError.setText(dtosIngreso.getMsgError());
			ventana.btnNuevo.setEnabled(true);
			ventana.btnGuardar.setEnabled(false);
			return;	
		}
		ventana.msgError.setForeground(Color.RED);
		ventana.msgError.setText(dtosIngreso.getMsgError());
	}
	
	private void limpiar() {
		
		ventana.btnNuevo.setEnabled(false);
		ventana.btnGuardar.setEnabled(true);
		ventana.txtFecha.setText(DtosComunes.getFechaActual());
		ventana.txtMonto.setText("");
		ventana.txtProv.setText("");		
		ventana.msgError.setText("");
		ventana.cmbBxTipo.setSelectedIndex(0);
		dtosIngreso.setIngreso(null);
		actualizar();
	}
}