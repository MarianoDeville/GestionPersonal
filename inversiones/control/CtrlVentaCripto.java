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

public class CtrlVentaCripto implements ActionListener {
	
	private Cargar ventana;
	private DtosMercadoCripto dtosMercadoCripto;
	private int elemento = -1;

	public CtrlVentaCripto(Cargar vista) {
		
		this.ventana = vista;
		this.dtosMercadoCripto = new DtosMercadoCripto();
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
		ventana.lblFormPago.setText("Forma de cobro");
		ventana.cmbBxPago.setModel(new DefaultComboBoxModel<>(dtosMercadoCripto.getListadoMetPago(true)));
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
			
			guardarVenta();
		}
		
		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
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
	
	private void guardarVenta() {
		
		dtosMercadoCripto.setMonedaCobro((String)ventana.cmbBxMoneda.getSelectedItem());
		dtosMercadoCripto.setComentario(ventana.txtAux1.getText());

		if(dtosMercadoCripto.setFecha(ventana.txtFecha.getText()) && 
				dtosMercadoCripto.setMetodoCobro(ventana.cmbBxPago.getSelectedIndex()) && 
				dtosMercadoCripto.setMoneda(ventana.cmbBxTipo.getSelectedIndex()) && 
				dtosMercadoCripto.setPrecio(ventana.txtMonto.getText()) && 
				dtosMercadoCripto.setCantidad(ventana.txtCotizacion.getText()) && 
				dtosMercadoCripto.setComision(ventana.txtComentario.getText()) && 
				dtosMercadoCripto.guardarVenta()) {
			
			ventana.msgError.setForeground(Color.BLUE);
			ventana.msgError.setText(dtosMercadoCripto.getMsgError());
			ventana.btnNuevo.setEnabled(true);
			ventana.btnGuardar.setEnabled(false);
			return;
		}
		ventana.msgError.setForeground(Color.RED);
		ventana.msgError.setText(dtosMercadoCripto.getMsgError());
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
		ventana.txtAux1.setText("");
		ventana.msgError.setText("");
		dtosMercadoCripto.resetMoneda();
		actualizar();
	}
}