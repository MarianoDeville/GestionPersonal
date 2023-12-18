package control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultComboBoxModel;
import modelo.DtosEgresos;
import vista.Cargar;

public class CtrlEditarEgreso implements ActionListener {
	
	private Cargar ventana;
	private DtosEgresos dtosEgreso;
	private int elemento = -1;

	public CtrlEditarEgreso(Cargar vista) {
		
		this.ventana = vista;
		this.dtosEgreso = new DtosEgresos();
		this.ventana.comboBoxTipo.addActionListener(this);
		this.ventana.comboBoxPago.addActionListener(this);
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

		ventana.btnNuevo.setVisible(false);
		ventana.txtFecha.setText(dtosEgreso.getFecha());
		ventana.txtProv.setText(dtosEgreso.getProveedor());
		ventana.comboBoxPago.setModel(new DefaultComboBoxModel<String>(dtosEgreso.getFormasPago("Seleccione un método de pago.")));
		ventana.comboBoxPago.setSelectedItem(dtosEgreso.getFormaPagoSeleccionada());
		ventana.comboBoxTipo.setModel(new DefaultComboBoxModel<String>(dtosEgreso.getListaDestinos("Seleccione una opción.")));
		ventana.comboBoxTipo.setSelectedItem(dtosEgreso.getDestinoConsumo());
		ventana.txtMonto.setText(dtosEgreso.getMonto());
		
		
		ventana.tabla.setDefaultEditor(Object.class, null);
		actualizar();
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.btnGuardar) {
			
			guardar();
		}
		
		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
		
		if(e.getSource() == ventana.txtProv) {
			
			actualizar();
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
	
	private void guardar() {
		
		if(dtosEgreso.setFecha(ventana.txtFecha.getText()) && 
				dtosEgreso.setDestino(ventana.comboBoxTipo.getSelectedIndex()) && 
				dtosEgreso.setFormaPago(ventana.comboBoxPago.getSelectedIndex()) && 
				dtosEgreso.setMonto(ventana.txtMonto.getText()) && 
				dtosEgreso.actualizarEgreso()) {
			
			ventana.msgError.setForeground(Color.BLUE);
			ventana.msgError.setText(dtosEgreso.getMsgError());
			ventana.btnGuardar.setEnabled(false);
			return;	
		}
		ventana.msgError.setForeground(Color.RED);
		ventana.msgError.setText(dtosEgreso.getMsgError());
	}
}