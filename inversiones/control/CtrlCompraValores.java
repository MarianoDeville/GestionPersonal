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
import modelo.DtosMercadoValores;
import vista.Cargar;

public class CtrlCompraValores implements ActionListener {
	
	private Cargar ventana;
	private DtosMercadoValores dtosMercadoValores;
	private int elemento = -1;

	public CtrlCompraValores(Cargar vista) {
		
		this.ventana = vista;
		this.dtosMercadoValores = new DtosMercadoValores();
		this.ventana.btnNuevo.addActionListener(this);
		this.ventana.btnGuardar.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
		this.ventana.chkBoxAcreditacion.addActionListener(this);
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
		ventana.lblProv.setText("Designación:");
		ventana.lblFormPago.setText("Custodia:");
		ventana.cmbBxPago.setModel(new DefaultComboBoxModel<>(dtosMercadoValores.getListaCustodias()));
		ventana.lblTipo.setText("Instrumento:");
		ventana.cmbBxTipo.setModel(new DefaultComboBoxModel<>(dtosMercadoValores.getListaInstrumentos()));
		ventana.txtCotizacion.setEditable(true);
		ventana.lblMonto.setText("Precio:");
		ventana.lblCotizacion.setText("Cantidad:");
		ventana.lblComentario.setText("Comisión:");
		ventana.txtComentario.setColumns(6);
		ventana.lblAux1.setText("Comentario");
		ventana.txtAux1.setVisible(true);
		ventana.chkBoxAcreditacion.setVisible(true);
		ventana.chkBoxAcreditacion.setSelected(false);
		ventana.btnNuevo.setText("Limpiar");
		ventana.tabla.setDefaultEditor(Object.class, null);
		actualizar();
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.btnNuevo) {
			
			limpiarCampos();
		}
		
		if(e.getSource() == ventana.chkBoxAcreditacion) {
			
			actualizar();
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
			dtosMercadoValores.setValor(elemento);
			ventana.cmbBxPago.setSelectedItem(dtosMercadoValores.getCustodiaValor());
			ventana.cmbBxPago.setEnabled(false);
			ventana.cmbBxTipo.setSelectedItem(dtosMercadoValores.getTipoInstrumento());
			ventana.cmbBxTipo.setEnabled(false);
			elemento = -1;
		}
		
		if(ventana.chkBoxAcreditacion.isSelected())
			ventana.chkBoxAcreditacion.setText("Largo plazo");
		else
			ventana.chkBoxAcreditacion.setText("Corto plazo");
		ventana.tabla.setModel(dtosMercadoValores.getListadoValores(ventana.txtProv.getText()));
	}
	
	private void guardarCompra() {
		
		dtosMercadoValores.setMonedaCompra((String)ventana.cmbBxMoneda.getSelectedItem());
		dtosMercadoValores.setComentario(ventana.txtAux1.getText());
		dtosMercadoValores.setTipoOperacion("Compra");
		dtosMercadoValores.setPlazo(ventana.chkBoxAcreditacion.isSelected());
		
		if(dtosMercadoValores.setNombre(ventana.txtProv.getText()) && 
				dtosMercadoValores.setFecha(ventana.txtFecha.getText()) && 
				dtosMercadoValores.setCustodia(ventana.cmbBxPago.getSelectedIndex()) && 
				dtosMercadoValores.setTipoInstrumento(ventana.cmbBxTipo.getSelectedIndex()) && 
				dtosMercadoValores.setPrecio(ventana.txtMonto.getText()) && 
				dtosMercadoValores.setCantidad(ventana.txtCotizacion.getText()) &&
				dtosMercadoValores.setComision(ventana.txtComentario.getText()) &&
				dtosMercadoValores.guardarCompra()) {
			
			ventana.msgError.setForeground(Color.BLUE);
			ventana.msgError.setText(dtosMercadoValores.getMsgError());
			ventana.btnGuardar.setEnabled(false);
			return;
		}
		ventana.msgError.setForeground(Color.RED);
		ventana.msgError.setText(dtosMercadoValores.getMsgError());
	}
	
	private void limpiarCampos() {
		
		ventana.btnGuardar.setEnabled(true);
		ventana.txtProv.setEditable(true);
		ventana.cmbBxPago.setEnabled(true);
		ventana.cmbBxTipo.setEnabled(true);
		ventana.chkBoxAcreditacion.setSelected(false);
		ventana.cmbBxTipo.setSelectedIndex(0);
		ventana.txtFecha.setText(DtosComunes.getFechaActual());
		ventana.txtMonto.setText("");
		ventana.txtProv.setText("");
		ventana.txtCotizacion.setText("");
		ventana.txtComentario.setText("");
		ventana.txtAux1.setText("");
		ventana.msgError.setText("");
		dtosMercadoValores.resetValor();
		actualizar();
	}
}