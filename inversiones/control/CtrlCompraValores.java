package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultComboBoxModel;
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

		ventana.txtFecha.setText(dtosMercadoValores.getFechaActual());
		ventana.lblProv.setText("Designación:");
		ventana.lblFormPago.setText("Custodia:");
		ventana.cmbBxPago.setModel(new DefaultComboBoxModel<>(dtosMercadoValores.getListaCustodias()));
		ventana.lblTipo.setText("Instrumento:");
		ventana.cmbBxTipo.setModel(new DefaultComboBoxModel<>(dtosMercadoValores.getListaInstrumentos()));
		ventana.txtCotizacion.setEditable(true);
		ventana.lblCotizacion.setText("Precio:");
		ventana.lblComentario.setText("Comisión:");
		ventana.txtComentario.setColumns(4);
		ventana.lblAux1.setText("Comentario");
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
			
			guardarCotizaciones();
		}
		
		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
	}
	
	private void actualizar() {
		
		if(elemento != -1 ) {
			
			ventana.txtProv.setText((String)ventana.tabla.getValueAt(elemento, 0));
//			dtosMercadoValores.setFuente(elemento);
			elemento = -1;
		}
//		ventana.tabla.setModel(dtosMercadoValores.getListadoValores(ventana.txtProv.getText()));
	}
	
	private void guardarCotizaciones() {
		
		
		
	}
	
	private void limpiarCampos() {
		
		ventana.btnNuevo.setEnabled(false);
		ventana.btnGuardar.setEnabled(true);
		ventana.txtFecha.setText(dtosMercadoValores.getFechaActual());
		ventana.txtMonto.setText("");
		ventana.txtProv.setText("");
		ventana.txtComentario.setText("");
		ventana.msgError.setText("");
		ventana.cmbBxTipo.setSelectedIndex(0);
//		dtosEgreso.setEgreso(null);
		actualizar();
	}
}