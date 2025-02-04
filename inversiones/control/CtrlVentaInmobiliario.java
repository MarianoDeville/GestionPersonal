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
import modelo.DtosMercadoInmobiliario;
import vista.Cargar;

public class CtrlVentaInmobiliario  implements ActionListener {
	
	private Cargar ventana;
	private DtosMercadoInmobiliario dtosMercadoInmobiliario;
	private int elemento = -1;

	public CtrlVentaInmobiliario(Cargar vista) {
		
		this.ventana = vista;
		this.dtosMercadoInmobiliario = new DtosMercadoInmobiliario();
		this.ventana.cmbBxMoneda.addActionListener(this);
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
		ventana.lblFormPago.setText("Cobro:");
		
		ventana.lblProv.setText("Propiedad:");
		ventana.lblTipo.setText("Movimiento:");
		ventana.cmbBxPago.setModel(new DefaultComboBoxModel<>(dtosMercadoInmobiliario.getListadoMetPago(false)));		
		ventana.cmbBxTipo.setModel(new DefaultComboBoxModel<>(dtosMercadoInmobiliario.getListaOperaciones()));
		ventana.tabla.setDefaultEditor(Object.class, null);
		actualizar();
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.btnNuevo) {
			
			limpiarCampos();
			return;
		}
		
		if(e.getSource() == ventana.btnGuardar) {
			
			guardarCompra();
			return;
		}
		
		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
		actualizar();
	}
	
	private void actualizar() {
		
		if(ventana.cmbBxMoneda.getSelectedIndex() == 0)
			ventana.txtCotizacion.setEditable(false);
		else
			ventana.txtCotizacion.setEditable(true);

		if(elemento != -1 ) {
			
			ventana.txtProv.setText((String)ventana.tabla.getValueAt(elemento, 0));
			ventana.txtProv.setEditable(false);
			dtosMercadoInmobiliario.setPropiedad(elemento);
			elemento = -1;
		}
		ventana.tabla.setModel(dtosMercadoInmobiliario.getListadoLocalizaciones(ventana.txtProv.getText()));
	}
	
	private void guardarCompra() {

		dtosMercadoInmobiliario.setComentario(ventana.txtComentario.getText());
		dtosMercadoInmobiliario.setMonedaPago((String)ventana.cmbBxMoneda.getSelectedItem());
		dtosMercadoInmobiliario.setOperacion((String)ventana.cmbBxTipo.getSelectedItem());
		
		if(dtosMercadoInmobiliario.setFecha(ventana.txtFecha.getText()) && 
				dtosMercadoInmobiliario.setMetodoPago(ventana.cmbBxPago.getSelectedIndex()) && 
				dtosMercadoInmobiliario.setMonto(ventana.txtMonto.getText()) && 
				dtosMercadoInmobiliario.setCotizacion(ventana.cmbBxMoneda.getSelectedIndex() == 0? "1": ventana.txtCotizacion.getText()) &&
				dtosMercadoInmobiliario.guardarOperacion()) {
			
			ventana.msgError.setForeground(Color.BLUE);
			ventana.msgError.setText(dtosMercadoInmobiliario.getMsgError());
			ventana.btnGuardar.setEnabled(false);
			return;
		}
		ventana.msgError.setForeground(Color.RED);
		ventana.msgError.setText(dtosMercadoInmobiliario.getMsgError());
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
		dtosMercadoInmobiliario.limpiar();
		actualizar();
	}
}