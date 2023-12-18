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

public class CtrlCargarEgreso implements ActionListener {

	private Cargar ventana;
	private DtosEgresos dtosEgreso;
	private int elemento = -1;

	public CtrlCargarEgreso(Cargar vista) {
		
		this.ventana = vista;
		this.dtosEgreso = new DtosEgresos();
		this.ventana.comboBoxTipo.addActionListener(this);
		this.ventana.comboBoxPago.addActionListener(this);
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
		ventana.txtFecha.setText(dtosEgreso.getFechaActual());
		ventana.comboBoxPago.setModel(new DefaultComboBoxModel<String>(dtosEgreso.getFormasPago("Seleccione un m�todo de pago.")));
		ventana.comboBoxPago.setSelectedIndex(0);
		ventana.comboBoxTipo.setModel(new DefaultComboBoxModel<String>(dtosEgreso.getListaDestinos("Seleccione una opci�n.")));
		ventana.comboBoxTipo.setSelectedIndex(0);
		ventana.tabla.setDefaultEditor(Object.class, null);
		actualizar();
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.btnGuardar) {
			
			guardar();
		}
		
		if(e.getSource() == ventana.btnNuevo) {
			
			limpiar();
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
				dtosEgreso.guardarEgreso()) {
			
			ventana.msgError.setForeground(Color.BLUE);
			ventana.msgError.setText(dtosEgreso.getMsgError());
			ventana.btnNuevo.setEnabled(true);
			ventana.btnGuardar.setEnabled(false);
			return;	
		}
		ventana.msgError.setForeground(Color.RED);
		ventana.msgError.setText(dtosEgreso.getMsgError());
	}
	
	private void limpiar() {
		
		ventana.btnNuevo.setEnabled(false);
		ventana.btnGuardar.setEnabled(true);
		ventana.txtFecha.setText(dtosEgreso.getFechaActual());
		ventana.txtMonto.setText("");
		ventana.txtProv.setText("");		
		ventana.msgError.setText("");
		ventana.comboBoxTipo.setSelectedIndex(0);
		dtosEgreso.setEgreso(null);
		actualizar();
	}
}