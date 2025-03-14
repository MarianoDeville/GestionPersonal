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
import modelo.DtosEgresos;
import vista.Cargar;

public class CtrlCargarEgreso implements ActionListener {

	private Cargar ventana;
	private DtosEgresos dtosEgreso;
	private int elemento = -1;

	public CtrlCargarEgreso(Cargar vista) {
		
		this.ventana = vista;
		this.dtosEgreso = new DtosEgresos();
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

		ventana.chkBoxAcreditacion.setVisible(true);
		ventana.chkBoxAcreditacion.setText("Gasto fijo");
		ventana.txtFecha.setText(DtosComunes.getFechaActual());
		ventana.cmbBxPago.setModel(new DefaultComboBoxModel<String>(dtosEgreso.getFormasPago("Seleccione un m�todo de pago.")));
		ventana.cmbBxPago.setSelectedIndex(0);
		ventana.cmbBxTipo.setModel(new DefaultComboBoxModel<String>(dtosEgreso.getListaDestinos("Seleccione una opci�n.")));
		ventana.cmbBxTipo.setSelectedIndex(0);
		ventana.lblAux1.setText("Cuotas:");
		ventana.txtAux1.setColumns(2);
		ventana.txtAux1.setVisible(true);
		ventana.tabla.setDefaultEditor(Object.class, null);
		actualizar();
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.txtProv) {
			
			actualizar();
		}
		
		if(e.getSource() == ventana.cmbBxPago) {

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
			dtosEgreso.setProveedor(elemento);
			ventana.txtProv.setEnabled(false);
			elemento = -1;
		}
		ventana.tabla.setModel(dtosEgreso.getListaProveedores(ventana.txtProv.getText()));
		ventana.txtAux1.setEnabled(dtosEgreso.isFinanciado(ventana.cmbBxPago.getSelectedIndex()));
		ventana.txtAux1.setText("1");
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
		dtosEgreso.setGastoFijo(ventana.chkBoxAcreditacion.isSelected());
		
		if(dtosEgreso.setFecha(ventana.txtFecha.getText()) && 
				dtosEgreso.setDestino(ventana.cmbBxTipo.getSelectedIndex()) && 
				dtosEgreso.setFormaPago(ventana.cmbBxPago.getSelectedIndex()) && 
				dtosEgreso.setMonto(ventana.txtMonto.getText()) &&
				dtosEgreso.setCotizacion(ventana.txtCotizacion.getText()) && 
				dtosEgreso.setCuotas(ventana.txtAux1.getText()) &&
				dtosEgreso.guardarEgreso()) {
			
			ventana.msgError.setForeground(Color.BLUE);
			ventana.msgError.setText(dtosEgreso.getMsgError());
			ventana.btnGuardar.setEnabled(false);
			return;	
		}
		ventana.msgError.setForeground(Color.RED);
		ventana.msgError.setText(dtosEgreso.getMsgError());
	}
	
	private void limpiar() {
		
		ventana.txtProv.setEnabled(true);
		ventana.btnGuardar.setEnabled(true);
		ventana.txtFecha.setText(DtosComunes.getFechaActual());
		ventana.txtMonto.setText("");
		ventana.txtProv.setText("");
		ventana.txtComentario.setText("");
		ventana.msgError.setText("");
		ventana.cmbBxTipo.setSelectedIndex(0);
		dtosEgreso.setEgreso(null);
		actualizar();
	}
}