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
import modelo.DtosMercadoFiat;
import vista.Cargar;

public class CtrlCompraFiat implements ActionListener {
	
	private Cargar ventana;
	private DtosMercadoFiat dtosMercadoFiat;
	private int elemento = -1;

	public CtrlCompraFiat(Cargar vista) {
		
		this.ventana = vista;
		this.dtosMercadoFiat = new DtosMercadoFiat();
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
		ventana.cmbBxPago.setModel(new DefaultComboBoxModel<>(dtosMercadoFiat.getListadoMetPago(ventana.chkBoxAcreditacion.isSelected())));
		ventana.lblTipo.setText("Moneda:");
		ventana.cmbBxTipo.setModel(new DefaultComboBoxModel<>(dtosMercadoFiat.getListaMonedas()));
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
			dtosMercadoFiat.setLocalizacion(elemento);
			elemento = -1;
		}
		ventana.tabla.setModel(dtosMercadoFiat.getListadoLocalizaciones(ventana.txtProv.getText()));
	}
	
	private void guardarCompra() {
		
		if(ventana.chkBoxAcreditacion.isSelected()) {
			
			dtosMercadoFiat.setMonedaPago("Pesos");
			dtosMercadoFiat.setConcepto((String)ventana.cmbBxMoneda.getSelectedItem());
		} else {
			
			dtosMercadoFiat.setMonedaPago((String)ventana.cmbBxMoneda.getSelectedItem());
		}
		dtosMercadoFiat.setComentario(ventana.txtAux1.getText());
		
		if(dtosMercadoFiat.setFecha(ventana.txtFecha.getText()) && 
				dtosMercadoFiat.setMetodoPago(ventana.cmbBxPago.getSelectedIndex()) && 
				dtosMercadoFiat.setMoneda(ventana.cmbBxTipo.getSelectedIndex()) && 
				dtosMercadoFiat.setPrecio(ventana.txtMonto.getText()) && 
				dtosMercadoFiat.setCantidad(ventana.txtCotizacion.getText()) && 
				dtosMercadoFiat.setComision(ventana.txtComentario.getText()) && 
				dtosMercadoFiat.guardarCompra(ventana.chkBoxAcreditacion.isSelected())) {
			
			ventana.msgError.setForeground(Color.BLUE);
			ventana.msgError.setText(dtosMercadoFiat.getMsgError());
			ventana.btnGuardar.setEnabled(false);
			return;
		}
		ventana.msgError.setForeground(Color.RED);
		ventana.msgError.setText(dtosMercadoFiat.getMsgError());
	}
	
	private void limpiarCampos() {
		
		ventana.btnGuardar.setEnabled(true);
		ventana.txtProv.setEditable(true);
		ventana.cmbBxTipo.setSelectedIndex(0);
		ventana.txtFecha.setText(DtosComunes.getFechaActual());
		ventana.txtMonto.setText("");
		ventana.txtProv.setText("");
		ventana.txtCotizacion.setText("");
		ventana.txtComentario.setText("");
		ventana.txtAux1.setText("");
		ventana.msgError.setText("");
		dtosMercadoFiat.resetMoneda();
		actualizar();
	}
	
	private void configuracion() {
		
		ventana.cmbBxPago.setModel(new DefaultComboBoxModel<>(dtosMercadoFiat.getListadoMetPago(ventana.chkBoxAcreditacion.isSelected())));
		
		if(ventana.chkBoxAcreditacion.isSelected()) {
			
			ventana.lblMonto.setText("Cotización:");
			ventana.txtMonto.setText("");
			ventana.cmbBxMoneda.setModel(new DefaultComboBoxModel<>(dtosMercadoFiat.getListaConceptos()));
		} else {
			
			ventana.lblMonto.setText("Precio:");
			ventana.txtMonto.setText("");
			ventana.cmbBxMoneda.setModel(new DefaultComboBoxModel<>(new String [] {"Pesos", "Dólares", "Euros"}));
		}
	}
}