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

		ventana.txtFecha.setText(DtosComunes.getFechaActual());
		ventana.lblProv.setText("Localización:");
		ventana.cmbBxPago.setModel(new DefaultComboBoxModel<>(dtosMercadoFiat.getListadoMetPago()));
		ventana.lblTipo.setText("Moneda:");
		ventana.cmbBxTipo.setModel(new DefaultComboBoxModel<>(dtosMercadoFiat.getListaMonedas()));
		ventana.txtCotizacion.setEditable(true);
		ventana.lblMonto.setText("Precio:");
		ventana.lblCotizacion.setText("Cantidad:");
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
		
		dtosMercadoFiat.setMonedaPago((String)ventana.cmbBxMoneda.getSelectedItem());
		dtosMercadoFiat.setComentario(ventana.txtComentario.getText());
		dtosMercadoFiat.setTipoOperacion("Compra");

		if(dtosMercadoFiat.setFecha(ventana.txtFecha.getText()) && 
				dtosMercadoFiat.setMetodoPago(ventana.cmbBxPago.getSelectedIndex()) && 
				dtosMercadoFiat.setMoneda(ventana.cmbBxTipo.getSelectedIndex()) && 
				dtosMercadoFiat.setPrecio(ventana.txtMonto.getText()) && 
				dtosMercadoFiat.setCantidad(ventana.txtCotizacion.getText()) && 
				dtosMercadoFiat.guardarCompra()) {
			
			ventana.msgError.setForeground(Color.BLUE);
			ventana.msgError.setText(dtosMercadoFiat.getMsgError());
			ventana.btnNuevo.setEnabled(true);
			ventana.btnGuardar.setEnabled(false);
			return;
		}
		ventana.msgError.setForeground(Color.RED);
		ventana.msgError.setText(dtosMercadoFiat.getMsgError());
	}
	
	private void limpiarCampos() {
		
		ventana.btnNuevo.setEnabled(false);
		ventana.btnGuardar.setEnabled(true);
		ventana.txtProv.setEditable(true);
		ventana.cmbBxTipo.setSelectedIndex(0);
		ventana.txtFecha.setText(DtosComunes.getFechaActual());
		ventana.txtMonto.setText("");
		ventana.txtProv.setText("");
		ventana.txtCotizacion.setText("");
		ventana.txtComentario.setText("");
		ventana.msgError.setText("");
		dtosMercadoFiat.resetMoneda();
		actualizar();
	}
}