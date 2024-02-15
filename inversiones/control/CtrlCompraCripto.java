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
import modelo.DtosMercadoCripto;
import vista.Cargar;

public class CtrlCompraCripto  implements ActionListener {
	
	private Cargar ventana;
	private DtosMercadoCripto dtosMercadoCripto;
	private int elemento = -1;

	public CtrlCompraCripto(Cargar vista) {
		
		this.ventana = vista;
		this.dtosMercadoCripto = new DtosMercadoCripto();
		this.ventana.chkBoxAcreditacion.addActionListener(this);
		this.ventana.btnNuevo.addActionListener(this);
		this.ventana.btnGuardar.addActionListener(this);
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
		ventana.txtFecha.setText(DtosComunes.getFechaActual());
		ventana.lblProv.setText("Localización:");
		ventana.cmbBxPago.setModel(new DefaultComboBoxModel<>(dtosMercadoCripto.getListadoMetPago(ventana.chkBoxAcreditacion.isSelected())));
		ventana.lblTipo.setText("Moneda:");
		ventana.cmbBxTipo.setModel(new DefaultComboBoxModel<>(dtosMercadoCripto.getListaMonedas()));
		ventana.txtCotizacion.setEditable(true);
		ventana.lblMonto.setText("Precio:");
		ventana.lblCotizacion.setText("Cantidad:");
		ventana.lblComentario.setText("Comisión:");
		ventana.txtComentario.setColumns(6);
		ventana.lblAux1.setVisible(true);
		ventana.lblAux1.setText("Comentario:");
		ventana.txtAux1.setVisible(true);
		ventana.tabla.setDefaultEditor(Object.class, null);
		actualizar();
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.btnNuevo) {
			
			limpiarCampos();
		}
		
		if(e.getSource() == ventana.btnGuardar) {
			
			guardarCompra();
		}
		
		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
		
		if(e.getSource() == ventana.chkBoxAcreditacion) {
			
			configuracion();
		}
	}
	
	private void actualizar() {
		
		if(elemento != -1 ) {
			
			ventana.txtProv.setText((String)ventana.tabla.getValueAt(elemento, 0));
			ventana.txtProv.setEditable(false);
			dtosMercadoCripto.setLocalizacion(elemento);
			elemento = -1;
		}
		ventana.tabla.setModel(dtosMercadoCripto.getListadoLocalizaciones(ventana.txtProv.getText()));
	}
	
	private void guardarCompra() {
		
		if(ventana.chkBoxAcreditacion.isSelected()) {
			
			dtosMercadoCripto.setMonedaPago("Pesos");
			dtosMercadoCripto.setConcepto((String)ventana.cmbBxMoneda.getSelectedItem());
		} else {
			
			dtosMercadoCripto.setMonedaPago((String)ventana.cmbBxMoneda.getSelectedItem());
		}
		dtosMercadoCripto.setComentario(ventana.txtAux1.getText());
		
		if(dtosMercadoCripto.setFecha(ventana.txtFecha.getText()) && 
				dtosMercadoCripto.setMetodoPago(ventana.cmbBxPago.getSelectedIndex()) && 
				dtosMercadoCripto.setMoneda(ventana.cmbBxTipo.getSelectedIndex()) && 
				dtosMercadoCripto.setPrecio(ventana.txtMonto.getText()) && 
				dtosMercadoCripto.setCantidad(ventana.txtCotizacion.getText()) && 
				dtosMercadoCripto.setComision(ventana.txtComentario.getText()) && 
				dtosMercadoCripto.guardarCompra(ventana.chkBoxAcreditacion.isSelected())) {
			
			ventana.msgError.setForeground(Color.BLUE);
			ventana.msgError.setText(dtosMercadoCripto.getMsgError());
			ventana.btnGuardar.setEnabled(false);
			return;
		}
		ventana.msgError.setForeground(Color.RED);
		ventana.msgError.setText(dtosMercadoCripto.getMsgError());
	}
	
	private void limpiarCampos() {
		
		ventana.btnGuardar.setEnabled(true);
		ventana.txtProv.setEditable(true);
		ventana.cmbBxTipo.setSelectedIndex(0);
		ventana.txtFecha.setText(DtosComunes.getFechaActual());
		ventana.txtMonto.setText(ventana.chkBoxAcreditacion.isSelected()? "0": "");
		ventana.txtProv.setText("");
		ventana.txtCotizacion.setText("");
		ventana.txtComentario.setText(ventana.chkBoxAcreditacion.isSelected()? "0": "");
		ventana.txtAux1.setText("");
		ventana.msgError.setText("");
		dtosMercadoCripto.resetMoneda();
		actualizar();
	}
	
	private void configuracion() {
		
		ventana.cmbBxPago.setModel(new DefaultComboBoxModel<>(dtosMercadoCripto.getListadoMetPago(ventana.chkBoxAcreditacion.isSelected())));
		
		if(ventana.chkBoxAcreditacion.isSelected()) {
			
			ventana.lblMonto.setText("Cotización:");
			ventana.txtMonto.setText("");
			ventana.cmbBxMoneda.setModel(new DefaultComboBoxModel<>(dtosMercadoCripto.getListaConceptos()));
			ventana.lblComentario.setVisible(false);
			ventana.txtComentario.setText("0");
			ventana.txtComentario.setVisible(false);
			ventana.lblMonto.setText("Concepto:");
			ventana.txtMonto.setColumns(0);
			ventana.txtMonto.setText("0");
			ventana.txtMonto.setVisible(false);
		} else {
			
			ventana.lblMonto.setText("Precio:");
			ventana.txtMonto.setText("");
			ventana.cmbBxMoneda.setModel(new DefaultComboBoxModel<>(new String [] {"Pesos", "Dólares", "Euros"}));
			ventana.lblComentario.setVisible(true);
			ventana.txtComentario.setText("");
			ventana.txtComentario.setVisible(true);
			ventana.lblMonto.setText("Precio:");
			ventana.txtMonto.setColumns(5);
			ventana.txtMonto.setText("");
			ventana.txtMonto.setVisible(true);
		}
	}
}